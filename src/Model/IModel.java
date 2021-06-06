package Model;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.File;
import java.util.Observer;

public interface IModel {
    void generateMaze(int rows, int cols);
    Maze getMaze();
    void updatePlayerLocation(MovementDirection direction);
    int getPlayerRow();
    int getPlayerCol();
    void assignObserver(Observer o);
    void solveMaze();
    Solution getSolution();
    void loadMaze(File file);

    void saveMaze(File file);

    void changeProp(int num_threads, String genAlgo, String solAlgo);

    String[] getProp();
}
