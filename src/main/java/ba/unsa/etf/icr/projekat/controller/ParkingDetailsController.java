package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ParkingDetailsController implements Initializable {
    public ImageView slikaParkinga;
    public ImageView imgOcjena;
    public Label lblNazivParkinga;
    public Label lblLokacija;
    public Label lblCijena;
    public Label lblBrSlobodnih;
    public Label lblRadnoVrijeme;
    public Label lblOpis;
    private Parking parking;
    private ParkAwayDAO dao;
    private Stage backScene;
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;
    public Button prijavaDugme;
    public Label lblPopunjen;
    public Label lblZatvoreno;

    public ParkingDetailsController(Parking parking, Stage backScene) {
        dao = ParkAwayDAO.getInstance();
        this.parking=parking;
        this.backScene=backScene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!parking.getPocetakRadnogVremena().equals(LocalTime.parse("00:00"))  &&
                !((parking.getPocetakRadnogVremena().isBefore(LocalTime.now()) ||
                        (parking.getPocetakRadnogVremena().equals(LocalTime.now())))
                        && (parking.getKrajRadnogVremena().isAfter(LocalTime.now()) ||
                        parking.getKrajRadnogVremena().equals(LocalTime.now())))){
            lblZatvoreno.setVisible(true);
        }
        else if(dao.dajBrojSlobodnihMjesta(parking.getParkingId())==0) {
            lblPopunjen.visibleProperty().set(true);
            prijavaDugme.disableProperty().set(true);
        }
        lblNazivParkinga.setText(parking.getNaziv());
        lblLokacija.setText(parking.getLokacija().getUlica());
        lblCijena.setText(Integer.toString(parking.getCijena()));
        lblBrSlobodnih.setText(Integer.toString(dao.dajBrojSlobodnihMjesta(parking.getParkingId())));
        lblRadnoVrijeme.setText("od "+parking.getPocetakRadnogVremena().getHour()+" : "+parking.getPocetakRadnogVremena().getMinute()
                                +" do "+parking.getKrajRadnogVremena().getHour()+" : "+parking.getKrajRadnogVremena().getMinute());
        if(Objects.equals(parking.getOpis(), "") || parking.getOpis() !=null)
            lblOpis.setText(parking.getOpis());
        String imagePath = dao.dajSliku(parking.getParkingId());
        if(getClass().getResourceAsStream(imagePath) != null){
            slikaParkinga.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        }
        System.out.println(parking.getCijena());
        if(getClass().getResourceAsStream("/img/"+parking.getOcjena()+".png")!=null)
        imgOcjena.setImage(new Image(getClass().getResourceAsStream("/img/"+parking.getOcjena()+".png")));

        if(dao.dajBrojSlobodnihMjesta(parking.getParkingId())==0) prijavaDugme.setDisable(true);
    }
    public void backParking(ActionEvent actionEvent) throws IOException {
        backScene.show();
        Stage close = (Stage) lblOpis.getScene().getWindow();
        close.close();
    }
    public void actionPrijava(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Stage close = (Stage) lblNazivParkinga.getScene().getWindow();
        prijavaParkingaController cont = new prijavaParkingaController(parking, close);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/prijavaParkinga.fxml"));
        loader.setController(cont);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(parking.getNaziv());
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        close.hide();
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
        Label lb = new Label("Ukoliko prvi put koristite apliakciju pritisnite dugme za registraciju,\n ukoliko ste već kreirali račun nastavite sa prijavom unoseći podatke u polja.");
        lb.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informativni ekran");
        alert.setHeaderText("Detalji o parkingu");
        alert.getDialogPane().setContent(lb);
        alert.showAndWait();

    }



}
