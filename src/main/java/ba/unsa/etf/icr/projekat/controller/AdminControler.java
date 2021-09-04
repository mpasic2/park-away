package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

import javax.swing.text.html.ListView;
import java.io.IOException;

public class AdminControler {
    public ListView listaParkingaId;

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

    public void dodajParking(ActionEvent actionEvent) {
    }

    public void izmijeniParking(ActionEvent actionEvent) {
    }

    public void obrisiParking(ActionEvent actionEvent) {
    }
}
