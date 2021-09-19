package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
import ba.unsa.etf.icr.projekat.model.Racun;
import ba.unsa.etf.icr.projekat.model.Vozilo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class HistorijaPlacanjaController implements Initializable {

    public TableView<Racun> listaRacuna;
    public TextField fldSearch;
    Navigation navigation= new Navigation();
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;
    public TableColumn<Racun, String> colNaziv;
    public TableColumn<Racun, String> colVozilo;
    public TableColumn<Racun, Integer> colLokacija;
    private ParkAwayDAO dao = ParkAwayDAO.getInstance();
    ObservableList<Racun> racuniZaListu = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Racun> racuni = FXCollections.observableArrayList();
            racuni = dao.dajRacune();
            ObservableList<Vozilo> vozilaOdKorisnika = FXCollections.observableArrayList();
            vozilaOdKorisnika = dao.dajKorisnikovaAuta(PrijavljeniKorisnik.getKorisnik().getKorisnikId());

            for (Racun racun : racuni)
                for (Vozilo vozilo : vozilaOdKorisnika)
                    if (racun.getVoziloId() == vozilo.getVozilo_id()){
                        racuniZaListu.add(racun);
                    }


            listaRacuna.setItems(racuniZaListu);
            colNaziv.setCellValueFactory(entry -> new SimpleObjectProperty(dao.dajParkigByParkingMjesto(entry.getValue().getParkingMjestoId()).getNaziv()));
            colVozilo.setCellValueFactory( entry -> new SimpleObjectProperty(dao.dajVozilo(entry.getValue().getVoziloId()).getModel()));
            colLokacija.setCellValueFactory(entry -> new SimpleObjectProperty(dao.dajParkigByParkingMjesto(entry.getValue().getParkingMjestoId()).getLokacija().getUlica()));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        fldSearch. textProperty().addListener((obs, oldText, newText) -> {
            search();
        });

    }

    private void search() {
        String keyword = fldSearch.getText().toLowerCase();
        ObservableList<Racun> filteredData = FXCollections.observableArrayList();
        if(keyword.equals("")){
            listaRacuna.setItems(racuniZaListu);
            return;
        }
        for(Racun racun: racuniZaListu){
            if(dao.dajParkigByParkingMjesto(racun.getParkingMjestoId()).getNaziv().toLowerCase().contains(keyword.toLowerCase())){
                filteredData.add(racun);
            }
            else if(dao.dajParkigByParkingMjesto(racun.getParkingMjestoId()).getLokacija().getUlica().toLowerCase().contains(keyword.toLowerCase())){
                filteredData.add(racun);
            }
        }
        listaRacuna.setItems(filteredData);
    }


    public void logOut(ActionEvent actionEvent) throws IOException {
        navigation.logOut(actionEvent);
    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        navigation.profileAction(actionEvent,null);
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
        tooltip.setText("Nazad");
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
    public void helpAction(MouseEvent mouseEvent) {
        Label lb = new Label("Ukoliko prvi put koristite apliakciju pritisnite dugme za registraciju,\n ukoliko ste već kreirali račun nastavite sa prijavom unoseći podatke u polja.");
        lb.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informativni ekran");
        alert.setHeaderText("Prijava");
        alert.getDialogPane().setContent(lb);
        alert.showAndWait();

    }


    public void actionSearch(ActionEvent actionEvent) {
        search();
    }
}
