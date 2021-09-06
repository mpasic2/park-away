package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.model.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ProfilController implements Initializable {
    public ImageView profileImg;
    public Label fldMail;
    public Label fldImePrezime;
    public Label fldTelefon;
    public Label fldAdresa;
    public Label fldModel;
    public Label fldRregistracija;
    public ComboBox chocideAuto;
    private Korisnik korisnik;
    Navigation navigation= new Navigation();
    public ProfilController(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fldMail.setText(korisnik.getEmail());
        fldImePrezime.setText(korisnik.getIme() + " " + korisnik.getPrezime());
        fldTelefon.setText(korisnik.getBrojTelefona());
        fldAdresa.setText(korisnik.getAdresaStanovanja().getUlica() + ", " + korisnik.getAdresaStanovanja().getGrad().getNaziv());

    }


    public void logOut(ActionEvent actionEvent) throws IOException {
       navigation.logOut(actionEvent);
    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        navigation.profileAction(actionEvent,korisnik);
    }

    public void locationAction(ActionEvent actionEvent) throws IOException {
        navigation.locationAction(actionEvent);
    }

    public void carMapAction(ActionEvent actionEvent) throws IOException {
        navigation.carMapAction(actionEvent);
    }

    public void messageAction(ActionEvent actionEvent) throws IOException {
        navigation.messageAction(actionEvent);
    }

    public void historijaPlacanja(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/historijaPlacanja.fxml"));
        stage.setTitle("Historija placanja");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Node node = (Node) actionEvent.getSource();
        Stage close=(Stage)node.getScene().getWindow();
        close.close();
    }



    public void dodajNovo(ActionEvent actionEvent) {
    }
}
