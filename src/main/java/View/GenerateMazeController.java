package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class GenerateMazeController extends AView  {
    public Button GenerateB;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public Button CancleB;
    public MazeDisplayer mazeDisplayer;



    public void Exit(ActionEvent actionEvent) {
        Stage stage = (Stage)CancleB.getScene().getWindow();
        //maybe safe exit
        stage.close();
    }

    public void Generate(ActionEvent actionEvent){ //check inputs
        if(viewModel.generateMaze(textField_mazeRows.getText(), textField_mazeColumns.getText()))
            Exit(actionEvent);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
