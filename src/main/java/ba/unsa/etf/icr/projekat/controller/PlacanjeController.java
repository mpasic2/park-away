package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class PlacanjeController implements Initializable{
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;
    public PasswordField fldPin;
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
    public Label lblIznos;
    Stage backScene;

    private ParkAwayDAO dao = ParkAwayDAO.getInstance();



    public PlacanjeController(Stage backScene) {
        this.backScene=backScene;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        long ukupno;
        long protekloVrijeme = PrijavljeniKorisnik.getTrenutniRacun().getPrijava().until(LocalTime.now(), ChronoUnit.MINUTES);
        if(protekloVrijeme>15 && protekloVrijeme<60)
            ukupno=PrijavljeniKorisnik.getTrenutniParking().getCijena();
        else
            ukupno=PrijavljeniKorisnik.getTrenutniRacun().getPrijava().until(LocalTime.now(), ChronoUnit.HOURS)*PrijavljeniKorisnik.getTrenutniParking().getCijena();
        this.lblIznos.setText("Iznos: " +ukupno+" KM");
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
        Label lb = new Label("Radi pojadnostavljenja aplikacije dodan je samo jedan ispravan pin \n i to je sljedeca kombinacija: 1234.");
        lb.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informativni ekran");
        alert.setHeaderText("Plaćanje pakringa");
        alert.getDialogPane().setContent(lb);
        alert.showAndWait();

    }

    public void odustaniAction(ActionEvent actionEvent) throws IOException {
        backScene.show();
        Stage close = (Stage) lblIznos.getScene().getWindow();
        close.close();
    }

    public void potvrdiAction(ActionEvent actionEvent) {
        if(fldPin.getText().equals("1234")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informativni ekran");
            alert.setHeaderText("Plaćanje");
            alert.getDialogPane().setContentText("Uspješno ste platili parking");
            alert.showAndWait();

            dao.izmijeniRacun(PrijavljeniKorisnik.getTrenutniRacun());
            PrijavljeniKorisnik.setTrenutniRacun(null);
            PrijavljeniKorisnik.setTrenutniParking(null);
            Stage stage=new Stage();
            mapController cont=new mapController(PrijavljeniKorisnik.getKorisnik());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/map.fxml"));
            loader.setController(cont);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public void obrisi(ActionEvent actionEvent) {
        fldPin.setText(fldPin.getText().substring(0, fldPin.getText().length()-1));
        System.out.println(fldPin.getText());
    }

    public void obrisiSve(ActionEvent actionEvent) {
        fldPin.setText("");
    }
}
