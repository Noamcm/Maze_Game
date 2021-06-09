package View;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class AboutController  extends AView{

    public Button BackB;

    public void BackButton(ActionEvent mouseEvent){
        Stage stage = (Stage)BackB.getScene().getWindow();
        //maybe safe exit
        stage.close();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

