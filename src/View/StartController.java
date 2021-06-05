package View;

import IO.MyDecompressorInputStream;
import Model.IModel;
import Model.MyModel;
import ViewModel.*;
import algorithms.mazeGenerators.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;

import java.util.*;

public class StartController extends AView  {
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Maze Files", "*.maze");
    public MazeDisplayer mazeDisplayer;
    public MediaView media;
    private MediaPlayer mediaPlayer;
    public Button SolveB;
    public ToggleButton MuteB;
    private static final String MEDIA_URL = "../images/backgroundVid.mp4";

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaPlayer= new MediaPlayer(new Media(this.getClass().getResource(MEDIA_URL).toExternalForm()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        media.setFitHeight(720); //to change
        media.setFitWidth(1280); //to change
        media.setMediaPlayer(mediaPlayer);
        SolveB.setDisable(true);

    }



    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change){
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void mazeGenerated() {
        mazeDisplayer.drawMaze(viewModel.getMaze());
        SolveB.setDisable(false);
    }
    private void mazeSolved() {
        mazeDisplayer.setSolution(viewModel.getSolution());
    }
    private void playerMoved() {
        mazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    public void Solve(ActionEvent actionEvent){
        viewModel.solveMaze();
    }

    public void keyPressed(KeyEvent keyEvent) {
        viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }


    public void setOnDragDetected(MouseEvent mouseEvent) {
        //viewModel.movePlayer(mouseEvent);
        // TOOOOOOOO DOOOOOOOOOOOOOO **********************************************************************
    }

    public void Mute(ActionEvent actionEvent) {
        if(mediaPlayer.isMute())
            mediaPlayer.setMute(false);
        else
            mediaPlayer.setMute(true);
    }
}
