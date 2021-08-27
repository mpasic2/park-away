package ba.unsa.etf.icr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class RegistracijaPersonalController implements Initializable {
    public TextField fldIme;
    public TextField fldTelefon;
    public TextField fldAdresa;
    public Button btnQuit;
    public Button btnNext;
    public ComboBox<Grad> choiceGrad;
    public TextField fldPrezime;
    private ParkAwayDAO dao = new ParkAwayDAO();
    private Korisnik korisnik;
    public RegistracijaPersonalController(Korisnik k) {
        korisnik = k;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(korisnik!=null){
            fldTelefon.setText(korisnik.getBrojTelefona());
            fldIme.setText(korisnik.getIme());
            fldPrezime.setText(korisnik.getPrezime());
            fldAdresa.setText(korisnik.getAdresaStanovanja().getUlica());
            choiceGrad.getSelectionModel().select(korisnik.getAdresaStanovanja().getGrad());
        }
        choiceGrad.setPromptText("Grad stanovanja");
        fldAdresa.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldIme.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldPrezime.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldTelefon.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        choiceGrad.setItems(dao.dajGradove());
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
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            Stage close=(Stage)fldTelefon.getScene().getWindow();
            close.close();
        }
    }

    public void Dalje(ActionEvent actionEvent) throws IOException {
        if(validacijaPersonal()==true){
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/registracija_uplata.fxml"));
            stage.setTitle("Registracija");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            Stage close=(Stage)fldTelefon.getScene().getWindow();
            close.close();
        }

    }

    Boolean validacijaPersonal(){
        String ime = fldIme.getText();
        String prezime = fldPrezime.getText();
        String adresa = fldAdresa.getText();
        String telefon = fldTelefon.getText();
        Grad gr = choiceGrad.getValue();
        Boolean validno = true;
        if(ime.length()==0){
            validno = false;
            fldIme.getStyleClass().add("neValid");
        }
        else{
            fldIme.getStyleClass().remove("neValid");
        }

        if(prezime.length() == 0){
            validno = false;
            fldPrezime.getStyleClass().add("neValid");
        }else{
            fldPrezime.getStyleClass().remove("neValid");
        }
        if(adresa.length() == 0){
            validno = false;
            fldAdresa.getStyleClass().add("neValid");
        }else{
            fldAdresa.getStyleClass().remove("neValid");
        }
        if(telefon.length() == 0){
            validno = false;
            fldTelefon.getStyleClass().add("neValid");
        }else{
            fldTelefon.getStyleClass().remove("neValid");
        }
        if(gr == null){
            validno = false;
            choiceGrad.getStyleClass().add("neValid");
        }else{
            choiceGrad.getStyleClass().remove("neValid");
        }
        return validno;
    }
}
