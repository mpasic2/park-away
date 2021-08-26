package ba.unsa.etf.icr.projekat;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistarcijaUplata implements Initializable {

    public TextField fldImeKartica;
    public TextField fldBrojCard;
    public TextField fldCvc;
    public JFXCheckBox checkPaypal;
    public JFXCheckBox checkMaster;
    public ChoiceBox choiceGodin;
    public ChoiceBox choiceMjesec;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void back(ActionEvent actionEvent) {
    }

    public void nastavi(ActionEvent actionEvent) {
    }
}
