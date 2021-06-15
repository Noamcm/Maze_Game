package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.Console;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentSkipListMap;

import javafx.scene.layout.BorderPane;
import javafx.util.Duration;


public class MyViewController extends AView{ //NEED TO COMPARE WITH ROTEM

    private static final String MEDIA_URL = "/images/FirstSound.mp3";
    public Button startB;
    public ToggleButton MuteB;
    public MenuBar mBar;
    public BorderPane thisPane;
    private MediaPlayer intro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intro= new MediaPlayer(new Media(this.getClass().getResource(MEDIA_URL).toExternalForm()));
        intro.setAutoPlay(true);
        intro.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                intro.seek(Duration.ZERO);
                intro.play();
            }
        });

        thisPane.scaleXProperty().bind(currScale);
        thisPane.scaleYProperty().bind(currScale);
    }

    public void handleBtnStart(ActionEvent mouseEvent)throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(); ///getClass().getResource("/starting.fxml"));//.getClassLoader()
/*        File f = new File ("./src/main/resources/starting.fxml");
        System.out.println(f.exists());
        System.out.println(f.getAbsolutePath());*/
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResourceAsStream("/Starting.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //root = FXMLLoader.load(getClass().getResource("starting.fxml"));
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        StartController view = fxmlLoader.getController();
        view.setStage(stage);
        view.setViewModel(viewModel);
        intro.stop();
        stage.show();
    }
    public void Mute(ActionEvent actionEvent) {
        if(intro.isMute())
            intro.setMute(false);
        else
            intro.setMute(true);
    }
    @Override
    public void update(Observable o, Object arg) {

    }
    public void MouseScrolling(ScrollEvent event) {
        HandleScroll(event, thisPane);
    }

}
