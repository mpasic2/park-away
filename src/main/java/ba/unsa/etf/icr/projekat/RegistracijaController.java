package ba.unsa.etf.icr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class RegistracijaController implements Initializable {
    public TextField fldIme;
    public TextField fldTelefon;
    public TextField fldAdresa;
    public Button btnQuit;
    public Button btnNext;
    public ComboBox choiceGrad;
    public TextField fldPrezime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceGrad.setPromptText("Grad stanovanja");
        fldAdresa.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldIme.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldPrezime.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldTelefon.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
    }

    public void odustani(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda");
        alert.setHeaderText("Ovom radnjom ćete prekinuti kreiranje računa");
        alert.setContentText("Da li ste sigurni da želite odustati od registracije");

        ButtonType buttonDa = new ButtonType("Da");
        ButtonType buttonNe = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonDa,buttonNe);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonDa){
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            stage.setTitle("Registracija");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            Stage close=(Stage)fldTelefon.getScene().getWindow();
            close.close();
        }
    }

    public void Dalje(ActionEvent actionEvent) throws IOException {


    }
}