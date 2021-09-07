package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.model.Grad;
import ba.unsa.etf.icr.projekat.model.Korisnik;
import ba.unsa.etf.icr.projekat.model.Vozilo;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class RegistracijaVozilaController implements Initializable {
    public TextField fldModel;
    public TextField fldRegistration;
    public TextField fldSasija;
    private Korisnik k;
    private Integer vozila;
    private ParkAwayDAO dao = new ParkAwayDAO();
    public RegistracijaVozilaController(Korisnik korisnik,Integer i) {
        vozila = i;
        k = korisnik;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fldModel.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldRegistration.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldSasija.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
    }

    public void odustani(ActionEvent actionEvent) throws IOException {
        if(vozila == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upozorenje");
            alert.setContentText("Ne možete napustiti kreiranje vozila bez da imate ijedno vozilo");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda");
            alert.setHeaderText("Ovom radnjom ćete prekinuti kreiranje novog vozila");
            alert.setContentText("Da li ste sigurni da se prestati? Unesena polja se neće spasiti!");

            ButtonType buttonDa = new ButtonType("Da");
            ButtonType buttonNe = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonDa,buttonNe);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonDa){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profil.fxml"));
                loader.setController(new ProfilController(k));
                Parent root = loader.load();
                Stage stage=new Stage();
                stage.setTitle("Registracija");
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
                Stage close=(Stage)fldSasija.getScene().getWindow();
                close.close();
            }
        }
    }

    public void Zavrsi(ActionEvent actionEvent) throws IOException {
        if(validacijaVozilo()){
            Vozilo voz =  new Vozilo(0,fldRegistration.getText(),fldModel.getText(),k,fldSasija.getText());
            dao.addVozilo(voz);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Čestitamo");
            alert.setContentText("Uspješno ste dodali svoje vozilo");
            alert.showAndWait();
            if(vozila != 0){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profil.fxml"));
                loader.setController(new ProfilController(k));
                Parent root = loader.load();
                Stage stage=new Stage();
                stage.setTitle("Profil");
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/map.fxml"));
                loader.setController(new mapController(k));
                Parent root = loader.load();
                Stage stage=new Stage();
                stage.setTitle("Mapa");
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
            }
            Stage close=(Stage)fldSasija.getScene().getWindow();
            close.close();
        }
    }

    Boolean validacijaVozilo(){
        String model = fldModel.getText();
        String regi = fldRegistration.getText();
        String saasija = fldSasija.getText();
        Boolean validno = true;
        if(model.length()==0){
            validno = false;
            fldModel.getStyleClass().add("neValid");
        }
        else{
            fldModel.getStyleClass().remove("neValid");
        }

        if(regi.length() == 0){
            validno = false;
            fldRegistration.getStyleClass().add("neValid");
        }else{
            fldRegistration.getStyleClass().remove("neValid");
        }
        if(saasija.length() == 0){
            validno = false;
            fldSasija.getStyleClass().add("neValid");
        }else{
            fldSasija.getStyleClass().remove("neValid");
        }
        return validno;
    }
}
