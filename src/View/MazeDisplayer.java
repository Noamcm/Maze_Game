package View;
import algorithms.mazeGenerators.*;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    private Solution solution;
    // player position:
    private int playerRow;
    private int playerCol;
    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNameRoad = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameSol = new SimpleStringProperty();



    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        draw();
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }
    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameSol() {
        return imageFileNameSol.get();
    }
    public void setImageFileNameSol(String imageFileNameSol) {
        this.imageFileNameSol.set(imageFileNameSol);
    }


    public String getImageFileNameRoad() {
        return imageFileNameRoad.get();
    }
    public void setImageFileNameRoad(String imageFileNameRoad) {
        this.imageFileNameRoad.set(imageFileNameRoad);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }
    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public void drawMaze(Maze maze) {
        this.maze = maze;
        draw();
    }

    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.getDimensions()[0];
            int cols = maze.getDimensions()[1];

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            if(solution != null)
                drawSolution(graphicsContext, cellHeight, cellWidth);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        Image solImage = null;
        graphicsContext.setFill(Color.DARKKHAKI);
        try {
            solImage = new Image(new FileInputStream(getImageFileNameSol()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no solution image file");
        }
    for (AState as : solution.getSolutionPath()) {
        MazeState mazeState = (MazeState)as;
        Position pos = mazeState.getPosition();
        double x = pos.getColumnIndex() * cellWidth;
        double y = pos.getRowIndex() * cellHeight;
        if (solImage == null) {
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        }
        else
            graphicsContext.drawImage(solImage, x, y, cellWidth, cellHeight);
    }

}


    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        Image wallImage = null;
        Image roadImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }
        try{
            roadImage = new Image(new FileInputStream(getImageFileNameRoad()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no road image file");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x = j * cellWidth;
                double y = i * cellHeight;
                if(!maze.isPath(new Position(i,j))){
                    //if it is a wall:
                    if(wallImage == null){
                        graphicsContext.setFill(Color.GREEN);
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);}
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
                else {
                    if(roadImage == null){
                        graphicsContext.setFill(Color.BROWN);
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);}
                    else
                        graphicsContext.drawImage(roadImage, x, y, cellWidth, cellHeight);
                }
            }
        }
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.PINK);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }
}
