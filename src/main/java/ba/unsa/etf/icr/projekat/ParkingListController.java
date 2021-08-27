package ba.unsa.etf.icr.projekat;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ParkingListController implements Initializable {
    private ObservableList<Parking> listParking;
    private ParkAwayDAO dao;
    public TableView<Parking> tableViewParkinzi;
    public TableColumn colNaziv;
    public TableColumn<Parking, String> colLokacija;
    public TableColumn<Parking, Integer> colBrojDostupnihMjesta;
    public ParkingListController() {
        dao = ParkAwayDAO.getInstance();
        listParking = FXCollections.observableArrayList(dao.dajParkinge());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewParkinzi.setItems(listParking);
        colNaziv.setCellValueFactory(new PropertyValueFactory("naziv"));
        colLokacija.setCellValueFactory( entry -> new SimpleObjectProperty(entry.getValue().lokacija.getValue().getUlica()));
        colBrojDostupnihMjesta.setCellValueFactory(entry -> new SimpleObjectProperty(dao.dajBrojSlobodnihMjesta(entry.getValue().getParkingId())));
    }

    Navigation navigation= new Navigation();

    public void logOut(ActionEvent actionEvent) throws IOException {
        navigation.logOut(actionEvent);
    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        navigation.profileAction(actionEvent);
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


}
