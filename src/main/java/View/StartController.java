package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;

import java.util.*;

public class StartController extends AView {
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Maze Files", "*.maze");
    public MazeDisplayer mazeDisplayer;
    public MediaView media;
    public MediaPlayer mediaPlayer;
    public Button SolveB;
    public ToggleButton MuteB;
    public MenuItem SaveFile;
    public static final String MEDIA_URL = "/images/backgroundVid.mp4";
    public AnchorPane thisPane;


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
        media.setMediaPlayer(mediaPlayer);
        media.setFitHeight(thisPane.getHeight());
        media.setFitWidth(thisPane.getWidth());
        media.setSmooth(true);
        SolveB.setDisable(true);
        thisPane.scaleXProperty().bind(currScale);
        thisPane.scaleYProperty().bind(currScale);
    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change){
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            case "goal reached" -> goalReached();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void goalReached() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Solved.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage=(Stage)((Node)mazeDisplayer).getScene().getWindow();
        scene = new Scene(root,1280, 720);
        stage.setScene(scene);
        mediaPlayer.stop();
        stage.show();
    }

    private void mazeGenerated() {
        mazeDisplayer.drawMaze(viewModel.getMaze());
        SolveB.setDisable(false);
        SaveFile.setDisable(false);
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

    public void Mute(ActionEvent actionEvent) {
        if(mediaPlayer.isMute())
            mediaPlayer.setMute(false);
        else
            mediaPlayer.setMute(true);
    }


    public void setOnDragDetected(MouseEvent mouseEvent) {
        mazeDisplayer.setOnMouseReleased(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                mazeDisplayer.setMouseTransparent(false);
            }
        });

        mazeDisplayer.setOnMouseDragged(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                double indexX = ((event.getX()-mazeDisplayer.getCellWidth()/2)/mazeDisplayer.getCellWidth());
                double indexY = ((event.getY()-mazeDisplayer.getCellHeight()/2)/mazeDisplayer.getCellHeight());
                event.consume();
                viewModel.DragPlayer(indexX,indexY);
                mazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
            }
        });
    }

     public void MouseScrolling(ScrollEvent event) {
         HandleScroll(event, thisPane);
     }
}
