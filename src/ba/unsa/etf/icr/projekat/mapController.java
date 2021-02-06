package ba.unsa.etf.icr.projekat;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class mapController implements Initializable {
        public ImageView imgAbout;

        public TextField fldSearch;
        private String user;
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

            GridPane gp = null;
            try {
                gp = FXMLLoader.load(getClass().getResource("/fxml/mapFilter.fxml"));
                drawer.setSidePane(gp);
            } catch (IOException e) {
                Logger.getLogger(mapController.class.getName()).log(Level.SEVERE,null,e);
            }



            dugmeMenuMap.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {


                if (drawer.isOpened()) {
                    drawer.close();
                    String style= "-fx-min-width: 350px;";
                    fldSearch.setStyle(style);
                }
                else {
                    drawer.open();
                    String style = "-fx-min-width: 162px;";
                    fldSearch.setStyle(style);
                }
            });
        }

        public void logOut(ActionEvent actionEvent) throws IOException {
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            stage.setTitle("Login");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            Stage close=(Stage)fldSearch.getScene().getWindow();
            close.close();
        }

    }


