package View;

import Model.IModel;
import Model.MyModel;
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

public class MyViewController extends AView implements Initializable, Observer { //NEED TO COMPARE WITH ROTEM
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("starting.fxml"));
        Parent root = fxmlLoader.load();
        //root = FXMLLoader.load(getClass().getResource("starting.fxml"));
        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,1280, 720);
        stage.setScene(scene);
        stage.show();
        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        StartController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
