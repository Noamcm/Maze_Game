package View;
import algorithms.mazeGenerators.*;
import javafx.event.ActionEvent;

public interface IView {
void New(ActionEvent actionEvent);
void Load(ActionEvent actionEvent);
void Save(ActionEvent actionEvent);
}
