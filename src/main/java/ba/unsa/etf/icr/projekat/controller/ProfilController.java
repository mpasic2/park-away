package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.model.Korisnik;
import ba.unsa.etf.icr.projekat.model.Vozilo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.sql.SQLException;
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
    public Label fldSasija;
    public ComboBox chocideAuto;
    private Korisnik korisnik;
    Navigation navigation= new Navigation();
    private ParkAwayDAO dao = new ParkAwayDAO();
    public ProfilController(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fldMail.setText(korisnik.getEmail());
        fldImePrezime.setText(korisnik.getIme() + " " + korisnik.getPrezime());
        fldTelefon.setText(korisnik.getBrojTelefona());
        fldAdresa.setText(korisnik.getAdresaStanovanja().getUlica() + ", " + korisnik.getAdresaStanovanja().getGrad().getNaziv());
        try {
            chocideAuto.setItems(dao.dajKorisnikovaAuta(korisnik.getKorisnikId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        chocideAuto.valueProperty().addListener(new ChangeListener<Vozilo>() {

            @Override
            public void changed(ObservableValue<? extends Vozilo> observableValue, Vozilo vozilo, Vozilo t1) {
                fldModel.setText(t1.getModel());
                fldRregistracija.setText(t1.getRegistracijska_oznaka());
                fldSasija.setText(t1.getSasija());
            }
        });
        chocideAuto.getSelectionModel().select(0);
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



    public void dodajNovo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija_vozilo.fxml"));
        loader.setController(new RegistracijaVozilaController(korisnik,1));
        Parent root = loader.load();
        Stage stage=new Stage();
        stage.setTitle("Registracija");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Stage close=(Stage)fldAdresa.getScene().getWindow();
        close.close();
    }
}
