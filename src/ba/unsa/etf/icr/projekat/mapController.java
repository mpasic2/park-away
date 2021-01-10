package ba.unsa.etf.icr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class mapController implements Initializable {
        public ImageView imgAbout;
        public Button dugmeMenuMap;
        public TextField fldSearch;
        private String user;
        public mapController(String user) {
            this.user = user;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            Image image = new Image("/img/logo.png");
            imgAbout.setImage(image);

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


