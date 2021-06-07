package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("SuperMaze");
        primaryStage.setScene(new Scene(root, 1280, 720));
        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MyViewController view = fxmlLoader.getController();
        view.setStage(primaryStage);
        view.setViewModel(viewModel);
        //primaryStage.getIcons().add(new Image("../images/pawprints.png"));
        primaryStage.getIcons().add(new Image("/images/pawprints.png"));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->view.CloseEvent());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

