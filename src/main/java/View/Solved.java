package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class Solved extends AView {
    private static final String MEDIA_URL = "/images/SolvedSound.mp3";
    public Button BackB;
    public AnchorPane thisPane;


    public void BackButton(ActionEvent mouseEvent)throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MyView.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,1280, 720);
        stage.setScene(scene);
//        IModel model = new MyModel();
//        MyViewModel viewModel = new MyViewModel(model);
        MyViewController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AudioClip winning = new AudioClip(this.getClass().getResource(MEDIA_URL).toExternalForm());
        winning.play();
        thisPane.scaleXProperty().bind(currScale);
        thisPane.scaleYProperty().bind(currScale);
    }
    public void MouseScrolling(ScrollEvent event) {
        HandleScroll(event, thisPane);
    }
}
