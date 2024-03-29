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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class prijavaParkingaController implements Initializable {
    public Label lblNaziv;
    public Label lblAdresa;
    public Label lblSlobMjesta;
    public ComboBox<String> cbvozilo;
    public ComboBox cmboxMjestaParking;
    public Button dugmeOdustani;
    public Button dugmePrijava;
    public Label lblCijena;
    public Label lblPopunjen;
    public Parking parking;
    public Label lblVecPrijavljen;
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;
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
        if(PrijavljeniKorisnik.getTrenutniParking()!=null){
            lblVecPrijavljen.visibleProperty().set(true);
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
        cmboxMjestaParking.getSelectionModel().selectFirst();
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

        LocalTime vrijemPrijave = LocalTime.now();

        int idMjesta=0;
        ObservableList<Pair<Integer,Integer>> slobodnaMjesta=dao.dajSlobodnaMjestaId(parking.getParkingId());
        for(Pair<Integer,Integer> mjesto: slobodnaMjesta){
            if(mjesto.getValue().equals(cmboxMjestaParking.getSelectionModel().getSelectedItem())){
                idMjesta=mjesto.getKey();
            }
        }
        if(idVozila!=0 && idMjesta!=0) {
            int idRacuna = dao.addRacun(idMjesta, idVozila, vrijemPrijave);
            Racun racun = new Racun(idRacuna,idVozila,idMjesta,0,vrijemPrijave, null);
            PrijavljeniKorisnik.setTrenutniRacun(racun);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informativni ekran");
            alert.setHeaderText("Prijava na parking");
            alert.getDialogPane().setContentText("Uspješno ste se prijavili na parking\nPrvih 15 minuta je besplatno.");
            alert.showAndWait();

            PrijavljeniKorisnik.setTrenutniParking(parking);
            Stage stage = new Stage();
            Stage close = (Stage) lblNaziv.getScene().getWindow();
            StatusController statusController = new StatusController(parking);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/statusVozila.fxml"));
            loader.setController(statusController);
            Parent root = loader.load();
            stage.setTitle("Status vozila");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    stage.close();
                }
            });
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            close.close();
        }

    }


    Navigation navigation= new Navigation();

    public void logOut(ActionEvent actionEvent) throws IOException {
        navigation.logOut(actionEvent);
    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        navigation.profileAction(actionEvent,PrijavljeniKorisnik.getKorisnik());
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

    public void mousePopupBack(MouseEvent mouseEvent) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Odjava");
        dugmeIzlazMap.setTooltip(tooltip);
    }
    public void mousePopupProfil(MouseEvent mouseEvent) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Profil");
        dugmeProfilMap.setTooltip(tooltip);
    }
    public void mousePopupMapa(MouseEvent mouseEvent) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Mapa");
        dugmeLokacijaMap.setTooltip(tooltip);
    }
    public void mousePopupAuta(MouseEvent mouseEvent) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Automobili");
        dugmeCarMap.setTooltip(tooltip);
    }
    public void mousePopupIzlaz(MouseEvent mouseEvent) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Izlaz");
        dugmePorukaMap.setTooltip(tooltip);
    }



}
