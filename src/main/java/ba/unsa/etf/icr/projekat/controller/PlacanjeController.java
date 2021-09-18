package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlacanjeController{
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;
    public TextField fldPin;
    public Button B7;
    public Button B9;
    public Button B4;
    public Button B5;
    public Button B8;
    public Button B6;
    public Button B2;
    public Button B1;
    public Button B3;
    public Button B0;
    private ParkAwayDAO dao = ParkAwayDAO.getInstance();



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

    public void odustaniAction(ActionEvent actionEvent) {
    }

    public void potvrdiAction(ActionEvent actionEvent) {
        if(fldPin.getText().equals("1234")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informativni ekran");
            alert.setHeaderText("Plaćanje");
            alert.getDialogPane().setContentText("Uspješno ste platili parking");
            alert.showAndWait();

            dao.izmijeniRacun(PrijavljeniKorisnik.getTrenutniRacun());
        }


    }

    public void Napisi7(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"7");
    }

    public void Napisi9(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"9");
    }

    public void Napisi4(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"4");
    }

    public void Napisi5(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"5");
    }

    public void Napisi8(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"8");
    }

    public void Napisi6(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"6");
    }

    public void Napisi2(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"2");
    }

    public void Napisi1(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"1");
    }

    public void Napisi3(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"3");
    }

    public void Napisi0(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText()+"0");
    }
}
