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
import javafx.stage.Stage;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.scene.layout.BorderPane;


public class MyViewController extends AView{ //NEED TO COMPARE WITH ROTEM

    public Button startB;
    public MenuBar mBar;
    public BorderPane thisPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thisPane.scaleXProperty().bind(myScale);
        thisPane.scaleYProperty().bind(myScale);
    }

    public void handleBtnStart(ActionEvent mouseEvent)throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("starting.fxml"));
        Parent root = fxmlLoader.load();
        //root = FXMLLoader.load(getClass().getResource("starting.fxml"));
        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,1280, 720);
        stage.setScene(scene);
        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        StartController view = fxmlLoader.getController();
        view.setStage(stage);
        view.setViewModel(viewModel);
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
    public void MouseScrolling(ScrollEvent event) {
        HandleScroll(event, thisPane);
    }

}
