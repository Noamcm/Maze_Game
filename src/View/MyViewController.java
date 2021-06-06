package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class MyViewController extends AView { //NEED TO COMPARE WITH ROTEM
    @FXML
    Button startB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        view.setViewModel(viewModel);
        stage.show();
    }




    @Override
    public void update(Observable o, Object arg) {

    }
}
