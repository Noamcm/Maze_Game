package View;

import Model.IModel;
import ViewModel.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.util.*;


import java.io.File;

public abstract class AView implements IView , Initializable, Observer {
    public static MyViewModel viewModel;
    protected Stage stage;
    protected Scene scene;
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Maze Files", "*.maze");
    private boolean CtrlPressed = false;

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    public void Load(ActionEvent actionEvent) {
        if (!fileChooser.getExtensionFilters().contains(extFilter))
            fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        viewModel.loadMaze(file);
    }


    public void New(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GenerateMaze.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        GenerateMazeController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        stage.showAndWait();
    }
    public void Save(ActionEvent actionEvent) {
        if (!fileChooser.getExtensionFilters().contains(extFilter))
            fileChooser.getExtensionFilters().add(extFilter);
        //Set extension filter for text files
        File file = fileChooser.showSaveDialog(stage);
        if (file != null)
            viewModel.saveMaze(file);
    }

    public void OpenProperties(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("properties.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        PropertiesController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        view.setProperties();
        stage.showAndWait();
    }


   /* public void MouseScrolling(MouseEvent mouseEvent) {
        if (mouseEvent.isControlDown()) {
            mouseEvent.consume();
        }
    }*/

}
