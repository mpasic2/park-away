package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
import ba.unsa.etf.icr.projekat.model.Racun;
import ba.unsa.etf.icr.projekat.model.Vozilo;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

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

    public void prijavaAction(ActionEvent actionEvent) throws IOException, SQLException {
        int idVozila=0;
        ObservableList<Vozilo> vozila = dao.dajKorisnikovaAuta(PrijavljeniKorisnik.getKorisnik().getKorisnikId());
        for(int i=0;i<vozila.size();i++)
            if(vozila.get(i).getModel().equals(cbvozilo.getSelectionModel().getSelectedItem()))
                idVozila=vozila.get(i).getVozilo_id();

        LocalTime vrijemPrijave = vrijemePrijave.getValue();

        int idMjesta=0;
        ObservableList<Pair<Integer,Integer>> slobodnaMjesta=dao.dajSlobodnaMjestaId(parking.getParkingId());
        for(Pair<Integer,Integer> mjesto: slobodnaMjesta){
            if(mjesto.getValue().equals(cmboxMjestaParking.getSelectionModel().getSelectedItem())){
                idMjesta=mjesto.getKey();
            }
        }
        if(idVozila!=0 && idMjesta!=0) {
            int idRacuna = dao.addRacun(idMjesta, idVozila, vrijemPrijave);
            for (int i = 0; i < dao.dajRacune().size();i++){
                if(dao.dajRacune().get(i).getRacunId()==idRacuna)
                    PrijavljeniKorisnik.setTrenutniRacun(dao.dajRacune().get(i));
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informativni ekran");
            alert.setHeaderText("Prijava na parking");
            alert.getDialogPane().setContentText("Uspješno ste se prijavili na parking");
            alert.showAndWait();


            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/statusVozila.fxml"));
            stage.setTitle("Status vozila");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    stage.close();
                }
            });
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            Stage close = (Stage) lblCijena.getScene().getWindow();
            close.close();
        }



    }

}
