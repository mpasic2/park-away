package ba.unsa.etf.icr.projekat;

import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class loginContoler implements Initializable {
    public ImageView imgAbout;
    public PasswordField fldPassword;
    public TextField fldUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("/img/logo.png");
        imgAbout.setImage(image);

    }
}
