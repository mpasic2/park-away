package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ParkingListController implements Initializable {
    private ObservableList<Parking> listParking;
    private ParkAwayDAO dao;
    public TableView<Parking> tableViewParkinzi;
    public TableColumn colNaziv;
    public TableColumn<Parking, String> colLokacija;
    public TableColumn<Parking, Integer> colBrojDostupnihMjesta;
    public TextField fldSearch;

    public ParkingListController() {
        dao = ParkAwayDAO.getInstance();
        listParking = FXCollections.observableArrayList(dao.dajParkinge());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewParkinzi.setItems(listParking);
        colNaziv.setCellValueFactory(new PropertyValueFactory("naziv"));
        colLokacija.setCellValueFactory( entry -> new SimpleObjectProperty(entry.getValue().getLokacija().getUlica()));
        colBrojDostupnihMjesta.setCellValueFactory(entry -> new SimpleObjectProperty(dao.dajBrojSlobodnihMjesta(entry.getValue().getParkingId())));
        fldSearch. textProperty().addListener((obs, oldText, newText) -> {
            search();
        });
        tableViewParkinzi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Stage stage=new Stage();
                ParkingDetailsController cont = new ParkingDetailsController(newSelection);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/parkingDetails.fxml"));
                loader.setController(cont);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setTitle("Profil");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
                stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                    if (KeyCode.ESCAPE == event.getCode()) {
                        stage.close();
                    }
                });
                Stage close=(Stage)tableViewParkinzi.getScene().getWindow();
                close.close();
            }
        });
    }



    @FXML private void search() {
        String keyword = fldSearch.getText().toLowerCase();
        if (keyword.equals("")) {
            tableViewParkinzi.setItems(listParking);
        } else {
            ObservableList<Parking> filteredData = FXCollections.observableArrayList();
            for (Parking parking : listParking) {
                if (parking.getNaziv().toLowerCase().contains(keyword) ||
                    parking.getLokacija().getUlica().toLowerCase().contains(keyword))
                    filteredData.add(parking);
            }

            tableViewParkinzi.setItems(filteredData);
        }
    }
    public void pretraziListu(ActionEvent actionEvent){
        search();
    }
    Navigation navigation= new Navigation();

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


}
