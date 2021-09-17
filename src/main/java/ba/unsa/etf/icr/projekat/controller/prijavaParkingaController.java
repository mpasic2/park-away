package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
import ba.unsa.etf.icr.projekat.model.Vozilo;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class prijavaParkingaController implements Initializable {
    public Label lblNaziv;
    public Label lblAdresa;
    public Label lblSlobMjesta;
    public ComboBox<String> cbvozilo;
    public JFXTimePicker vrijemePrijave;
    public ComboBox cmboxMjestaParking;
    public Button dugmeOdustani;
    public Button dugmePrijava;
    public Label lblCijena;
    public Label lblPopunjen;
    public Parking parking;
    private Stage backScene;
    private ParkAwayDAO dao = ParkAwayDAO.getInstance();


    public prijavaParkingaController(Parking parking, Stage backScene) {
        this.parking=parking;
        this.backScene=backScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(dao.dajBrojSlobodnihMjesta(parking.getParkingId())==0) {
            lblPopunjen.visibleProperty().set(true);
            dugmePrijava.disableProperty().set(true);
        }
        lblAdresa.setText(parking.getLokacija().getUlica());
        lblCijena.setText(String.valueOf(parking.getCijena())+" KM");
        lblSlobMjesta.setText(String.valueOf(dao.dajBrojSlobodnihMjesta(parking.getParkingId())));
        lblNaziv.setText(parking.getNaziv());
        try {
            ObservableList<String> vozilaString =  FXCollections.observableArrayList();
            ObservableList<Vozilo> vozila = dao.dajKorisnikovaAuta(PrijavljeniKorisnik.getKorisnik().getKorisnikId());
            for(Vozilo vozilo: vozila){
                vozilaString.add(vozilo.getModel());
            }
            cbvozilo.setItems(vozilaString);
            cbvozilo.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cmboxMjestaParking.setItems(dao.dajSlobodnaMjesta(parking.getParkingId()));
    }

    public void backParking(ActionEvent actionEvent) throws IOException {
        backScene.show();
        Stage close = (Stage) lblAdresa.getScene().getWindow();
        close.close();
    }
}
