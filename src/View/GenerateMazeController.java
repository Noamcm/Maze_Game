package View;

import Model.IModel;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class GenerateMazeController  {
    public MyViewModel viewModel;
    public Button GenerateB;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public Button CancleB;
    public MazeDisplayer mazeDisplayer;

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
       // this.viewModel.addObserver(this);
    }

    public void Exit(ActionEvent actionEvent) {
        Stage stage = (Stage)CancleB.getScene().getWindow();
        //maybe safe exit
        stage.close();
    }

    public void Generate(ActionEvent actionEvent){ //check inputs
        int rows = Integer.valueOf(textField_mazeRows.getText());
        int cols = Integer.valueOf(textField_mazeColumns.getText());
        viewModel.generateMaze(rows, cols);
        Exit(actionEvent);
    }



}
