package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.Navigation;
import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.model.Grad;
import ba.unsa.etf.icr.projekat.model.Korisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class mapController implements Initializable {
    public ImageView imgAbout;
    public HBox ButtonBarMap;
    private Korisnik user;
    public WebView mapView;
    public TextField fldPretraga;
    public ObservableList<Grad> nadjeniGradovi= FXCollections.observableArrayList();
    ParkAwayDAO dao = new ParkAwayDAO();
    public ObservableList<Grad> gradovi= dao.dajGradove();
    public Button dugmeIzlazMap;
    public Button dugmeProfilMap;
    public Button dugmeLokacijaMap;
    public Button dugmeCarMap;
    public Button dugmePorukaMap;
    public mapController(Korisnik user) {
        this.user = user;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(user);
        Image image = new Image("/img/logo.png");
        imgAbout.setImage(image);
        mapView.getEngine().load(getClass().getResource("/HTML/googlemap.html").toString());

        fldPretraga.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
    }

    public void openListParking(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/parking.fxml"));
        stage.setTitle("Lista parkinga");
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Stage close = (Stage) mapView.getScene().getWindow();
        close.close();
    }

    Navigation navigation= new Navigation();

    public void logOut(ActionEvent actionEvent) throws IOException {
        navigation.logOut(actionEvent);
    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        navigation.profileAction(actionEvent,user);
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
    
    public void actionPretraga(ActionEvent actionEvent) throws IOException {
        ObservableList<Parking> listParking = FXCollections.observableArrayList();

        for (Grad grad : gradovi)
            if (grad.getNaziv().toLowerCase().contains(fldPretraga.getText().toLowerCase()))
                nadjeniGradovi.add(grad);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        ParkingListController pklc = new ParkingListController(fldPretraga.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/parking.fxml"));
        loader.setController(pklc);
        Parent root = loader.load();
        stage.setTitle("Lista parkinga");
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Stage close = (Stage) mapView.getScene().getWindow();
        close.close();
    }

    public void helpAction(MouseEvent mouseEvent) {
        Label lb = new Label("Unesite naziv ili lokaciju parkinga koji tražite.\nNakon toga pritisnite dugme za pretragu.\nUkoliko želite vidjeti listu svih parkinga ostavite\nprazno polje i pritisnite dugme za pretragu.");
        lb.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informativni ekran");
        alert.setHeaderText("Prijava");
        alert.getDialogPane().setContent(lb);
        alert.showAndWait();

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



}


