package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import com.sun.javafx.css.FontFaceImpl;
import javafx.scene.control.Alert;
import javafx.scene.input.*;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this); //Observe the Model for it's changes
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public Maze getMaze(){
        return model.getMaze();
    }

    public void loadMaze(File file){
        if (file!=null)
            model.loadMaze(file);
    }

    public int getPlayerRow(){
        return model.getPlayerRow();
    }

    public int getPlayerCol(){
        return model.getPlayerCol();
    }


    public Solution getSolution(){
        return model.getSolution();
    }

    public boolean generateMaze(String rows, String cols){
        int int_rows,int_cols;
        try {
            int_rows = Integer.parseInt(rows);
            int_cols = Integer.parseInt(cols);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Parameters");
            alert.setContentText("Please enter numbers!");
            alert.show();
            return false;
        }
        if ((int_rows > 1) && (int_cols > 1)) {
            model.generateMaze(int_rows, int_cols);
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please choose valid numbers!");
            alert.show();
        }
        return false;
    }


    public void movePlayer(MouseEvent mouseEvent){


    }

    public void movePlayer(KeyEvent keyEvent){
        MovementDirection direction;
        switch (keyEvent.getCode()){
            case UP:{
                direction = MovementDirection.UP;
                break;
            }
            case DOWN: {
                direction = MovementDirection.DOWN;
                break;
            }
            case RIGHT: {
                direction = MovementDirection.RIGHT;
                break;
            }
            case LEFT: {
                direction = MovementDirection.LEFT;
                break;
            }
            case NUMPAD9: {
                direction = MovementDirection.UPRIGHT;
                break;
            }
            case NUMPAD7: {
                direction = MovementDirection.UPLEFT;
                break;
            }
            case NUMPAD3: {
                direction = MovementDirection.DOWNRIGHT;
                break;
            }
            case NUMPAD1: {
                direction = MovementDirection.DOWNLEFT;
                break;
            }
            case NUMPAD8: {
                direction = MovementDirection.UP;
                break;
            }
            case  NUMPAD2: {
                direction = MovementDirection.DOWN;
                break;
            }
            case  NUMPAD4: {
                direction = MovementDirection.LEFT;
                break;
            }
            case  NUMPAD6 : {
                direction = MovementDirection.RIGHT;
                break;
            }
            default : {
                // no need to move the player...
                return;
            }
        }
        model.updatePlayerLocation(direction);
    }

    public void solveMaze(){
/*        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();*/
        model.solveMaze();
    }


    public void saveMaze(File file) {
        model.saveMaze(file);
    }

    public boolean changeProp(String threads, Object MazeGenAlgo, Object MazeSolveAlgo) {
        int num_threads;
        String genAlgo,SolAlgo;
        try {
            num_threads = Integer.parseInt(threads);
            genAlgo = (String)MazeGenAlgo;
            SolAlgo = (String)MazeSolveAlgo;
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Parameters");
            alert.setContentText("Please enter threads number!");
            alert.show();
            return false;
        }
        if (num_threads > 1) {
            model.changeProp(num_threads, genAlgo, SolAlgo);
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please choose positive number of threads!");
            alert.show();
        }
        return false;
    }

    public String[] getProp() {
        return model.getProp();
    }
}

