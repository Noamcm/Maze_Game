package View;

import Model.*;
import ViewModel.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("SuperMaze");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

/*        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        StartController view = fxmlLoader.getController();
        view.setViewModel(viewModel);*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
