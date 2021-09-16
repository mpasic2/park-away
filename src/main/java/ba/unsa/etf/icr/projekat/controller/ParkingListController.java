package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
import com.jfoenix.controls.*;
import javafx.application.Platform;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.Nullable;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ParkingListController implements Initializable {
    private ObservableList<Parking> listParking = FXCollections.observableArrayList();;
    private ParkAwayDAO dao;
    public TableView<Parking> tableViewParkinzi;
    public TableColumn colNaziv;
    public TableColumn<Parking, String> colLokacija;
    public TableColumn<Parking, Integer> colBrojDostupnihMjesta;
    public TextField fldSearch;
    public String pretraga = "";
    public JFXSlider minCijenaFilter;
    public JFXCheckBox stalniFilter;
    public JFXCheckBox nadkriveniFilter;
    public JFXTimePicker pocetakRadaFilter;
    public JFXTimePicker krajRadaFilter;
    public JFXSlider ocjenaFilter;
    public JFXSlider maxCijenaFilter;
    public Button btnRefresh;
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;


    GridPane gp = null;
    AnchorPane ap = null;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger dugmeMenuMap;


    public ParkingListController(String text) {
        this.pretraga = text;
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
        try {

            gp = FXMLLoader.load(getClass().getResource("/fxml/mapFilter.fxml"));
            drawer.setSidePane(gp);
            btnRefresh = (Button) gp.getChildren().get(gp.getChildren().size()-1);
            for(int i = 0; i<gp.getChildren().size()-1; i++) {
                ap = (AnchorPane) gp.getChildren().get(i);
                for(int j=0; j< ap.getChildren().size(); j++) {
                    Node node= ap.getChildren().get(j);
                    //System.out.println(j + " : " + ap.getChildren().get(j).toString() + "\n");
                    if(node.getId()!=null){
                        if (node.getId().equals("ocjenaFilter")) ocjenaFilter = (JFXSlider) node;
                        else if (node.getId().equals("pocetakRadaFilter")) pocetakRadaFilter = (JFXTimePicker) node;
                        else if (node.getId().equals("krajRadaFilter")) krajRadaFilter = (JFXTimePicker) node;
                        else if (node.getId().equals("stalniFilter")) stalniFilter = (JFXCheckBox) node;
                        else if (node.getId().equals("nadkriveniFilter")) nadkriveniFilter = (JFXCheckBox) node;
                        else if (node.getId().equals("minCijenaFilter")) minCijenaFilter = (JFXSlider) node;
                        else if (node.getId().equals("maxCijenaFilter")) maxCijenaFilter = (JFXSlider) node;
                    }
                }
            }

        } catch (IOException e) {
            Logger.getLogger(mapController.class.getName()).log(Level.SEVERE, null, e);
        }
        fldSearch.textProperty().set(pretraga);
        fldSearch.setFocusTraversable(false);


        btnRefresh.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            search();
        });
        dugmeMenuMap.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (drawer.isOpened()) {
                drawer.close();
                tableViewParkinzi.setEffect(null);
                try {
                    TimeUnit.MICROSECONDS.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                drawer.setVisible(false);
            } else {
                drawer.setVisible(true);
                drawer.open();
                BoxBlur effect = new BoxBlur();
                tableViewParkinzi.setEffect(effect);
            }
        });

        tableViewParkinzi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(() -> {
                Stage stage = new Stage();
                Stage close = (Stage) tableViewParkinzi.getScene().getWindow();
                ParkingDetailsController cont = new ParkingDetailsController(newSelection, close);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/parkingDetails.fxml"));
                loader.setController(cont);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setTitle(newSelection.getNaziv());
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
                stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                    if (KeyCode.ESCAPE == event.getCode()) {
                        stage.close();
                    }
                });
                tableViewParkinzi.getSelectionModel().clearSelection();
                close.hide();
            });
            }

        });

    }



    @FXML private void search() {
        String keyword = fldSearch.getText().toLowerCase();
            ObservableList<Parking> filteredData = FXCollections.observableArrayList();

            for (Parking parking : listParking) {
                boolean ok=true;
                if (parking.getNaziv().toLowerCase().contains(keyword) ||
                        parking.getLokacija().getUlica().toLowerCase().contains(keyword)){
                    if (nadkriveniFilter.isSelected()) {
                        if(parking.getOpis()==null || !parking.getOpis().toLowerCase().contains("nadkr")) {
                            ok = false;
                            continue;
                        }
                    }
                    if(parking.getCijena()<minCijenaFilter.getValue() || parking.getCijena()>maxCijenaFilter.getValue()){
                        System.out.println(parking.getCijena() + " - " +minCijenaFilter.getValue() + " - " + maxCijenaFilter.getValue());
                        ok = false;
                        continue;
                    }
                    if(stalniFilter.isSelected()){
                        if(parking.getStalniParking()!=1){
                            ok = false;
                            continue;
                        }
                    }
                    if(krajRadaFilter.getValue()!=null && pocetakRadaFilter.getValue() !=null && !parking.getPocetakRadnogVremena().equals(LocalTime.parse("00:00"))  &&
                            !((parking.getPocetakRadnogVremena().isBefore(pocetakRadaFilter.getValue()) ||
                                    (parking.getPocetakRadnogVremena().equals(pocetakRadaFilter.getValue())))
                                    && (parking.getKrajRadnogVremena().isAfter(krajRadaFilter.getValue()) ||
                                    parking.getKrajRadnogVremena().equals(krajRadaFilter.getValue())))){
                        ok= false;
                        System.out.println(krajRadaFilter.getValue() + "("+ parking.getKrajRadnogVremena() +")" + "\n" +
                                pocetakRadaFilter.getValue() + "(" + parking.getPocetakRadnogVremena() + ")");
                        continue;
                    }
                    if(parking.getOcjena()<ocjenaFilter.getValue()){
                        ok = false;
                    }
                }else
                    ok= false;
                if(ok)
                    filteredData.add(parking);
            }
            tableViewParkinzi.setItems(filteredData);
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
        Label lb = new Label("Dodatno pretražite i odaberite parking kako biste vidjeli \nsve pojedinosti o njemu");
        lb.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informativni ekran");
        alert.setHeaderText("Tabela parkinga");
        alert.getDialogPane().setContent(lb);
        alert.showAndWait();

    }
}