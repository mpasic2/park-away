package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
import ba.unsa.etf.icr.projekat.model.Vozilo;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class prijavaParkingaController implements Initializable {
    public Label lblAdresa;
    public Label lblSlobMjesta;
    public ComboBox<Vozilo> cboxzlo;
    public JFXTimePicker vrijemePrijave;
    public ComboBox cmboxMjestaParking;
    public Button dugmeOdustani;
    public Button dugmePrijava;
    public Label lblCijena;
    public Parking parking;
    private ParkAwayDAO dao = ParkAwayDAO.getInstance();


    public prijavaParkingaController(Parking parking) {
        this.parking=parking;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblAdresa.setText(parking.getLokacija().getUlica());
        lblCijena.setText(String.valueOf(parking.getCijena()));
        lblSlobMjesta.setText(String.valueOf(dao.dajBrojSlobodnihMjesta(parking.getParkingId())));
    }


}
