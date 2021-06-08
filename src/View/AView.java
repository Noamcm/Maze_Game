package View;

import ViewModel.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.*;


import java.io.File;

public abstract class AView implements IView , Initializable, Observer {
    public static MyViewModel viewModel;
    protected Stage stage;
    protected Scene scene;
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Maze Files", "*.maze");
    private boolean CtrlPressed = false;
    protected Group plot = new Group();
    protected static final double ZOOM_FACTOR = 1.2;
    final double MAX_SCALE = 10.0;
    final double MIN_SCALE = 0.7;
    DoubleProperty myScale = new SimpleDoubleProperty(1.0);

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    public void setStage(Stage NewStage) {
        this.stage = NewStage;
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
        stage.getIcons().add(new Image("/images/pawprints.png"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Properties.fxml"));
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
        stage.getIcons().add(new Image("/images/pawprints.png"));
        PropertiesController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        view.setProperties();
        stage.showAndWait();
    }

    public void OpenHelp(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Help.fxml"));
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
        stage.getIcons().add(new Image("/images/pawprints.png"));
        HelpController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        stage.showAndWait();
    }

    public void OpenAbout(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About.fxml"));
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
        stage.getIcons().add(new Image("/images/pawprints.png"));
        AboutController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        stage.showAndWait();
    }

    public void CloseEvent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView(this.getClass().getResource("/images/SadDogs.png").toString()));
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are You Sure You Want To Exit?");
        alert.setContentText("We will miss you :( ");

        ButtonType buttonTypeOk = new ButtonType("Yes, Bye!");
        ButtonType buttonTypeCancel = new ButtonType("Ok, I'll Stay", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk) {
            //System.out.println("Entered close function");
            Platform.exit();
            System.exit(0);
        } else {
            alert.close();
        }
    }
    public static double clamp(double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }


    public void setPivot( double x, double y, Pane currentPane) {
        currentPane.setTranslateX(currentPane.getTranslateX()-x);
        currentPane.setTranslateY(currentPane.getTranslateY()-y);
    }

    public void setCenterPivot(Pane currentPane){
        currentPane.setTranslateX(currentPane.getTranslateX()/50);
        currentPane.setTranslateY(currentPane.getTranslateY()/50);
    }

    public double getScale() {
        return myScale.get();
    }

    public void setScale( double scale, Pane currentPane) {
        myScale.set(scale);
    }

    
    public void HandleScroll(ScrollEvent event, Pane currentPane) {
        if(event.isControlDown()) {
            boolean flag = false;
            double delta = 1.2;

            double scale = getScale();
            double oldScale = scale;

            if (event.getDeltaY() < 0) {
                scale /= delta;
                flag = true;
            } else {
                scale *= delta;
            }
            scale = clamp(scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale) - 1;

            double dx = (event.getSceneX() - (currentPane.getBoundsInParent().getWidth() / 2 + currentPane.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (currentPane.getBoundsInParent().getHeight() / 2 + currentPane.getBoundsInParent().getMinY()));

            setScale(scale,currentPane);

            if (!flag)
                setPivot(f * dx, f * dy,currentPane);
            else {
                setCenterPivot(currentPane);
            }
            event.consume();
        }
    }
}



