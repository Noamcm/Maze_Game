package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
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

    public void generateMaze(int rows, int cols){
        model.generateMaze(rows, cols);
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
}

