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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class StatusController implements Initializable {

    public Label lblNaziv;
    public Label lblAdresa;
    public Label lblCijena;
    public Label lblMjesto;
    public Label lblVrijeme;
    public Label lblUkupnaCijena;
    Navigation navigation= new Navigation();
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;
    public Parking parking;
    private Stage backScene;
    private ParkAwayDAO dao = ParkAwayDAO.getInstance();

    public StatusController(Parking parking, Stage backScene) {
        if(parking==null){
            Stage stage=new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/statusVozila2.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initStyle(StageStyle.UNDECORATED);
            stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    stage.close();
                }
            });
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            Stage close=(Stage)lblNaziv.getScene().getWindow();
            close.close();



        }
        this.parking=parking;
        this.backScene=backScene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblNaziv.setText(parking.getNaziv());
        lblAdresa.setText(parking.getLokacija().getUlica());
        lblCijena.setText(parking.getCijena()+" KM/h");

        ObservableList<Pair<Integer, Integer>> p= dao.dajSlobodnaMjestaId(parking.getParkingId());
        int brojMjesta = 0;
        for(Pair<Integer, Integer> mjesto: p){
            if(mjesto.getKey().equals(PrijavljeniKorisnik.getTrenutniRacun().getParkingMjestoId())) {
                brojMjesta = mjesto.getValue();
            }
        }
        if(brojMjesta!=0)
            lblMjesto.setText(String.valueOf(brojMjesta));
        long protekloVrijeme = PrijavljeniKorisnik.getTrenutniRacun().getPrijava().until(LocalTime.now(), ChronoUnit.MINUTES);
        lblVrijeme.setText("Vaše vozilo je parkirano "+ protekloVrijeme +" minuta");
        long ukupno=PrijavljeniKorisnik.getTrenutniRacun().getPrijava().until(LocalTime.now(), ChronoUnit.HOURS)*parking.getCijena();
        lblUkupnaCijena.setText("Trenutna cijena parkinga je " + ukupno + "KM");
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



}
