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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ParkingDetailsController implements Initializable {
    public ImageView slikaParkinga;
    public Label lblNazivParkinga;
    public Label lblLokacija;
    public Label lblCijena;
    public Label lblBrSlobodnih;
    public Label lblRadnoVrijeme;
    public Label lblOpis;
    private Parking parking;
    private ParkAwayDAO dao;
    private Stage backScene;

    public ParkingDetailsController(Parking parking, Stage backScene) {
        dao = ParkAwayDAO.getInstance();
        this.parking=parking;
        this.backScene=backScene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    }
    public void backParking(ActionEvent actionEvent) throws IOException {
        backScene.show();
        Stage close = (Stage) lblOpis.getScene().getWindow();
        close.close();
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
