package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Scale;
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
    public static final String MEDIA_URL = "../images/backgroundVid.mp4";
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
        media.setFitHeight(720); //to change
        media.setFitWidth(1280); //to change
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
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void goalReached() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Solved.fxml"));
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
/*        if (!SolveB.isDisable())
        {
            int indexX = (int)(mouseEvent.getX()/mazeDisplayer.getCellWidth());
            int indexY = (int)(mouseEvent.getY()/mazeDisplayer.getCellHeight());
            viewModel.DragPlayer(indexX,indexY);
            mazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
        }*/



/*        mazeDisplayer.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                mazeDisplayer.setMouseTransparent(true);
                System.out.println("Event on Source: mouse pressed");
                event.setDragDetect(true);
            }
        });*/

        mazeDisplayer.setOnMouseReleased(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                mazeDisplayer.setMouseTransparent(false);
                //System.out.println("Event on Source: mouse released");
            }
        });

        mazeDisplayer.setOnMouseDragged(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
/*              System.out.println("Event on Source: "+ "x:  " +event.getScreenX() +"  Y:  " +event.getScreenY());
                System.out.println(mazeDisplayer.getCellWidth() +" "+ mazeDisplayer.getCellHeight());

                System.out.println("Event on Source: "+ "x:  " +event.getScreenX()/mazeDisplayer.getCellWidth() +"  Y:  " +event.getScreenY()/mazeDisplayer.getCellHeight());
                System.out.println("Event on Source: "+ "x:  " +event.getSceneX()/mazeDisplayer.getCellWidth() +"  Y:  " +event.getSceneY()/mazeDisplayer.getCellHeight());

                System.out.println("mouse:  " +event.getSceneX() +"  getCellWidth:  " +mazeDisplayer.getCellWidth());
                mazeDisplayer.getPlayerCol()*/
                double indexX = ((event.getX()-mazeDisplayer.getCellWidth()/2)/mazeDisplayer.getCellWidth());
                double indexY = ((event.getY()-mazeDisplayer.getCellHeight()/2)/mazeDisplayer.getCellHeight());
                event.consume();
                viewModel.DragPlayer(indexX,indexY);
                mazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
                //event.setDragDetect(false);
            }
        });

/*        mazeDisplayer.setOnDragDetected(new EventHandler <MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                mazeDisplayer.startFullDrag();
                System.out.println("Event on Source: drag detected" + "x:  " +event.getScreenX() +"  Y:  " +event.getScreenY());
            }
        });

        // Add mouse event handlers for the target
        mazeDisplayer.setOnMouseDragEntered(new EventHandler <MouseDragEvent>()
        {
            public void handle(MouseDragEvent event)
            {
                System.out.println("Event on Target: mouse dragged");
            }
        });

        mazeDisplayer.setOnMouseDragOver(new EventHandler <MouseDragEvent>()
        {
            public void handle(MouseDragEvent event)
            {
                System.out.println("Event on Target: mouse drag over");
            }
        });

        mazeDisplayer.setOnMouseDragReleased(new EventHandler <MouseDragEvent>()
        {
            public void handle(MouseDragEvent event)
            {
                System.out.println("Event on Target: mouse drag released");
            }
        });

        mazeDisplayer.setOnMouseDragExited(new EventHandler <MouseDragEvent>()
        {
            public void handle(MouseDragEvent event)
            {
                System.out.println("Event on Target: mouse drag exited");
            }
        });*/
        }
 /*   public void MouseScrolling(ScrollEvent scrollEvent) {   //ZOOM
        double SCALE_DELTA = 1.1;
        System.out.println("mazeDisplayer.getScaleX()  :" + mazeDisplayer.getScaleX());
        System.out.println("mazeDisplayer.getScaleY()  :" + mazeDisplayer.getScaleY());
        if ((scrollEvent.isControlDown())&&(mazeDisplayer.getScaleY() + SCALE_DELTA<MAX_SCALE)&&(mazeDisplayer.getScaleY() + SCALE_DELTA>MIN_SCALE)) {
            if (scrollEvent.getDeltaY() < 0)
                SCALE_DELTA = -1.1;
            Scale thisScale = new Scale();
            mazeDisplayer.setScaleX(mazeDisplayer.getScaleX() + SCALE_DELTA);
            mazeDisplayer.setScaleY(mazeDisplayer.getScaleY() + SCALE_DELTA);
            thisScale.setX(mazeDisplayer.getScaleX() + SCALE_DELTA);
            thisScale.setY(mazeDisplayer.getScaleY() + SCALE_DELTA);
            thisScale.setPivotX(mazeDisplayer.getScaleX());
            thisScale.setPivotY(mazeDisplayer.getScaleY());
            mazeDisplayer.getTransforms().add(thisScale);
            scrollEvent.consume();
        }
        else
            System.out.println("NO CTRL");
    }*/
     public void MouseScrolling(ScrollEvent event) {
         HandleScroll(event, thisPane);
     }
}
