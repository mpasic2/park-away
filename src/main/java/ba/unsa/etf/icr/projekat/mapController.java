package ba.unsa.etf.icr.projekat;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    public Button btnListaParkinga;
    private String user;
    public WebView mapView;

    public mapController(String user) {
        this.user = user;
    }

    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger dugmeMenuMap;
    @FXML
    private JFXTimePicker Timer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("/img/logo.png");
        imgAbout.setImage(image);
        mapView.getEngine().load(getClass().getResource("/HTML/googlemap.html").toString());
        GridPane gp = null;
        try {

            gp = FXMLLoader.load(getClass().getResource("/fxml/mapFilter.fxml"));
            drawer.setSidePane(gp);
        } catch (IOException e) {
            Logger.getLogger(mapController.class.getName()).log(Level.SEVERE, null, e);
        }


        dugmeMenuMap.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (drawer.isOpened()) {
                drawer.close();
                btnListaParkinga.setVisible(true);
                mapView.setEffect(null);
            } else {
                drawer.open();
                btnListaParkinga.setVisible(false);
                BoxBlur effect = new BoxBlur();
                mapView.setEffect(effect);
            }
        });
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
        navigation.profileAction(actionEvent);
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


