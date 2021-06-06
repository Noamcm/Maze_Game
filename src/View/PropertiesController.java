package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class PropertiesController extends AView{
    public Button CancleB;
    public Button SavePropB;
    public TextField textField_threads;
    public ChoiceBox GenAlgoB;
    public ChoiceBox SolveAlgoB;


    public void Exit(ActionEvent actionEvent) {
        Stage stage = (Stage)CancleB.getScene().getWindow();
        //maybe safe exit
        stage.close();
    }

    public void SavePropB(ActionEvent actionEvent){ //check inputs
        if(viewModel.changeProp(textField_threads.getText(),GenAlgoB.getValue() ,SolveAlgoB.getValue() ))
            Exit(actionEvent);
    }


    public void setProperties() {
        String[] prop = viewModel.getProp();
        textField_threads.setText(prop[0]);
        GenAlgoB.getItems().addAll("MyMazeGenerator" ,"SimpleMazeGenerator" , "EmptyMazeGenerator");
        GenAlgoB.setValue(prop[1]);
        SolveAlgoB.getItems().addAll("BestFirstSearch" ,"BreadthFirstSearch" , "DepthFirstSearch");
        SolveAlgoB.setValue(prop[2]);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
