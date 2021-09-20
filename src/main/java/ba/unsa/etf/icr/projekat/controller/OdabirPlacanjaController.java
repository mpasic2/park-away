package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class OdabirPlacanjaController implements Initializable {
    public Label lblNaziv;
    public Label lblAdresa;
    public Label lblCijena;
    public Label lblVrijeme;
    public Label lblUkupnaCijena;
    Navigation navigation= new Navigation();
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;
    public Parking parking;
    long ukupno;
    long period;
    private Stage backScene;
    private ParkAwayDAO dao = ParkAwayDAO.getInstance();

    public OdabirPlacanjaController(Parking parking,Stage backScene,long ukupno, long period) {
        this.backScene=backScene;
        this.parking=parking;
        this.ukupno=ukupno;
        this.period=period;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblNaziv.setText(parking.getNaziv());
        lblAdresa.setText(parking.getLokacija().getUlica());
        lblCijena.setText(parking.getCijena()+" KM/h");
        lblVrijeme.setText(period+" min");
        lblUkupnaCijena.setText(ukupno + " KM");
    }
    public void backAction(ActionEvent actionEvent) throws IOException {
        backScene.show();
        Stage close = (Stage) lblAdresa.getScene().getWindow();
        close.close();
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

    public void helpAction(MouseEvent mouseEvent) {
        Label lb = new Label("Prikazano je trenutno stanje Vašeg vozila \n (lokacija, dosadašna cijena i naziv vozila)");
        lb.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informativni ekran");
        alert.setHeaderText("Status vozila");
        alert.getDialogPane().setContent(lb);
        alert.showAndWait();

    }

    public void actionGotovina(ActionEvent actionEvent) throws IOException {
        PrijavljeniKorisnik.setTrenutniParking(null);
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Informativni ekran");
        alert1.setHeaderText("Plaćanje pakringa");
        alert1.getDialogPane().setContentText("Prilikom izlaska sa parkinga dužni ste \n portiru platiti parking u iznosu od " + ukupno +" KM");
        alert1.showAndWait();


        dao.izmijeniRacun(PrijavljeniKorisnik.getTrenutniRacun());
        PrijavljeniKorisnik.setTrenutniRacun(null);

        Stage stage=new Stage();
        mapController cont=new mapController(PrijavljeniKorisnik.getKorisnik());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/map.fxml"));
        loader.setController(cont);
        Parent root = loader.load();
        stage.setTitle("Mapa");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        stage.show();
        Node node = (Node) actionEvent.getSource();
        Stage close=(Stage)node.getScene().getWindow();
        close.close();

    }
    public void actionKartica(ActionEvent actionEvent) throws IOException {
        Stage noviProzor = new Stage();
        Stage zatvaranjePoruka=(Stage)lblNaziv.getScene().getWindow();
        PlacanjeController cont= new PlacanjeController(zatvaranjePoruka);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/placanje.fxml"));
        loader.setController(cont);
        Parent root = loader.load();
        noviProzor.setTitle("Plaćanje");
        noviProzor.initStyle(StageStyle.UNDECORATED);
        Scene scene = new  Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        noviProzor.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                noviProzor.close();
            }
        });
        noviProzor.setScene(scene);
        noviProzor.show();
        zatvaranjePoruka.hide();

    }



}
