package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.Configurations;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.security.auth.login.Configuration;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private int playerRow;
    private int playerCol;
    private Solution solution;
    private ServerStrategyGenerateMaze generator;
    private ServerStrategySolveSearchProblem solver;
    private Configurations config=Configurations.getInstance();
    private static final Logger LOG = LogManager.getRootLogger();
    //private MazeGenerator generator;
//    public AMazeGenerator generator;
    Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
    Server solveSearchProblemServer = new Server(5402, 1000, new ServerStrategySolveSearchProblem());

    public MyModel() {
            config.SetConfigPath("/resources/config.properties");
            //config.getProperties().load(getClass().getResourceAsStream("./resources/config.properties"));
            generator = new ServerStrategyGenerateMaze();

    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void generateMaze(int rows, int cols) {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        mazeGeneratingServer.start();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new  IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[])fromServer.readObject();//read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[(rows*cols)+24]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze 25 | P a g e with bytes
                        maze = new Maze(decompressedMaze);
                        //maze.print();
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }
                }
            });
            client.communicateWithServer();
            //LOG.info("TEST");
            LOG.info("Client accepted: " + client.toString() + " requested to generate a maze with " + config.readMazeGeneratingAlgorithm() +" algorithm, in dimensions: {"+ maze.getDimensions()[0]+","+ maze.getDimensions()[1]+"}");

        } catch (UnknownHostException e) {
            LOG.error(e.getMessage());
        }
        setChanged();
        notifyObservers("maze generated");
        // start position:
        movePlayer(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());
        mazeGeneratingServer.stop();
    }

    @Override
    public void updatePlayerLocation(MovementDirection direction) {
        Position current;
        switch (direction) {
            case UP -> {
                if (playerRow > 0){
                    current = new Position(playerRow - 1, playerCol);
                    if ((maze.isPath(current)||(maze.getGoalPosition().equals(current))))
                        movePlayer(playerRow - 1, playerCol);
                }
            }
            case DOWN -> {
                if (playerRow < maze.getDimensions()[0] - 1){
                    current =new Position(playerRow + 1, playerCol);
                     if((maze.isPath(current)||(maze.getGoalPosition().equals(current))))
                        movePlayer(playerRow + 1, playerCol);
                }
            }
            case LEFT -> {
                if (playerCol > 0){
                    current =new Position(playerRow , playerCol-1);
                    if((maze.isPath(current)||(maze.getGoalPosition().equals(current))))
                        movePlayer(playerRow, playerCol - 1);
                }
            }
            case RIGHT -> {
                if (playerCol < maze.getDimensions()[1] - 1){
                    current =new Position(playerRow , playerCol+ 1);
                    if((maze.isPath(current)||(maze.getGoalPosition().equals(current))))
                        movePlayer(playerRow, playerCol + 1);
                }
            }
            case UPRIGHT -> {
                if ((playerRow > 0) && (playerCol<maze.getDimensions()[1] - 1)) {
                    current = new Position(playerRow - 1, playerCol + 1);
                    if ((maze.isPath(current) || (maze.getGoalPosition().equals(current))))
                        movePlayer(playerRow - 1, playerCol + 1);
                }
            }
            case UPLEFT -> {
                if ((playerRow > 0) && (playerCol>0)) {
                    current = new Position(playerRow - 1, playerCol - 1);
                    if ((maze.isPath(current) || (maze.getGoalPosition().equals(current))))
                        movePlayer(playerRow - 1, playerCol - 1);
                }
            }
            case DOWNRIGHT -> {
                if ((playerRow < maze.getDimensions()[0] - 1)&&(playerCol < maze.getDimensions()[1] - 1)) {
                    current = new Position(playerRow + 1, playerCol + 1);
                    if((maze.isPath(current) || (maze.getGoalPosition().equals(current))))
                    movePlayer(playerRow + 1, playerCol + 1);
                }
            }
            case DOWNLEFT -> {
                if ((playerRow < maze.getDimensions()[0] - 1)&&(playerCol > 0)){
                    current =new Position(playerRow+1 , playerCol- 1);
                    if((maze.isPath(current)||(maze.getGoalPosition().equals(current))))
                        movePlayer(playerRow + 1, playerCol - 1);
                }
            }
        }

    }

    private void movePlayer(int row, int col){
        this.playerRow = row;
        this.playerCol = col;
        setChanged();
        if ((row==maze.getGoalPosition().getRowIndex())&&(col==maze.getGoalPosition().getColumnIndex()))
            notifyObservers("goal reached");
        else
            notifyObservers("player moved");
    }

    @Override
    public int getPlayerRow() {
        return playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void solveMaze() {
        //solve the maze
        solveSearchProblemServer = new Server(5402, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        solution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }
                }
            });
            client.communicateWithServer();
            LOG.info("Client accepted: " + client.toString() + " requested to solve a maze with " + config.readMazeSearchingAlgorithm() +" algorithm, solution's length is: "+solution.getSolutionPath().size());
        } catch (UnknownHostException e) {
            LOG.error(e.getMessage());
        }
        setChanged();
        notifyObservers("maze solved");
        solveSearchProblemServer.stop();
    }

    @Override
    public Solution getSolution() {
        return solution;
    }

    @Override
    public void loadMaze(File file) {
        byte savedMazeBytes[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(file));
            savedMazeBytes = new byte[(int)file.length()*Integer.SIZE];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        maze = new Maze(savedMazeBytes);
        setChanged();
        notifyObservers("maze generated");
        // start position:
        movePlayer(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());
    }

    @Override
    public void saveMaze(File file) {
        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(file));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeProp(int num_threads, String genAlgo, String solAlgo) {
        config.writeThreadPoolSize(String.valueOf(num_threads));
        config.writeMazeGeneratingAlgorithm(genAlgo);
        config.writeMazeSearchingAlgorithm(solAlgo);
    }

    @Override
    public String[] getProp() {
        String[] PropertiesArray = {config.readThreadPoolSize(),config.readMazeGeneratingAlgorithm(), config.readMazeSearchingAlgorithm()};
        return PropertiesArray;
    }
}