package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.model.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class AdminControler implements Initializable {

    public javafx.scene.control.ListView listaParking;
    public ObservableList<Parking> parkinzi = FXCollections.observableArrayList();
    private ParkAwayDAO dao;
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;

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

    public void dodajParking(ActionEvent actionEvent) throws IOException {

        Stage noviProzor = new Stage();
        Parent roditelj = FXMLLoader.load(getClass().getResource("/fxml/newParking.fxml"));
        noviProzor.setTitle("Administrator");
        noviProzor.initStyle(StageStyle.UNDECORATED);
        Scene scene = new  Scene(roditelj, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        noviProzor.setScene(scene);
        noviProzor.show();
        Stage zatvaranjePoruka=(Stage)listaParking.getScene().getWindow();
        zatvaranjePoruka.close();


    }

    public void izmijeniParking(ActionEvent actionEvent) throws IOException {

        if(listaParking.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informativni ekran");
            alert.setHeaderText("Izmjena parkinga");
            alert.getDialogPane().setContentText("Niste odabrali parking");
            alert.showAndWait();
        }
        else {


            String sve = listaParking.getSelectionModel().getSelectedItem().toString();
            int idParkinga = Integer.parseInt(sve.split(" ")[0]);


            Stage noviProzor = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/changeParking.fxml"));
            ChangeParkingController cpc = new ChangeParkingController(dao.dajParkinge().get(idParkinga - 1));
            loader.setController(cpc);
            Parent roditelj = loader.load();
            noviProzor.setTitle("Administrator");
            noviProzor.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(roditelj, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            noviProzor.setScene(scene);
            noviProzor.show();

            Stage zatvaranjePoruka = (Stage) listaParking.getScene().getWindow();
            zatvaranjePoruka.close();
        }

    }

    public void obrisiParking(ActionEvent actionEvent) {


            String sve = listaParking.getSelectionModel().getSelectedItem().toString();
            int idParkinga = Integer.parseInt(sve.split(" ")[0]);

            dao.obrisiPaarking(dao.dajParkinge().get(idParkinga - 1));

        listaParking.refresh();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dao=ParkAwayDAO.getInstance();
        parkinzi=dao.dajParkinge();

        listaParking.setItems(parkinzi);

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

    
}
