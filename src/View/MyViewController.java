package View;

import ViewModel.MyViewModel;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import algorithms.mazeGenerators.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements IView, Initializable, Observer { //NEED TO COMPARE WITH ROTEM
    public MyViewModel viewModel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Button startB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void handleBtnStart(ActionEvent mouseEvent)throws Exception{
        root = FXMLLoader.load(getClass().getResource("starting.fxml"));
        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
