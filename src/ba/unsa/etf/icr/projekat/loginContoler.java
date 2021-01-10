package ba.unsa.etf.icr.projekat;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class loginContoler implements Initializable {
    public ImageView imgAbout;
    public PasswordField fldPassword;
    public TextField fldUser;
    public Label fldGreska;
    private ParkAwayDAO  dao = new ParkAwayDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("/img/logo.png");
        imgAbout.setImage(image);

    }

    public void prijava(ActionEvent actionEvent) throws IOException {
        ObservableList<Korisnik> k = dao.dajKorisnike();
        String mail = fldUser.getText();
        String password = fldPassword.getText();
        int j = 0;
        for(int i = 0;i < k.size();i++){
            if(mail.equals(k.get(i).getEmail())  && password.equals(k.get(i).getLozinka())){
                j++;
                break;
            }
        }
        if(j == 0){
            fldGreska.setText("Unesene informacije nisu tačne, pokušajte ponovo");
            fldUser.requestFocus();
        }else{
            Stage stage=new Stage();
            mapController cont=new mapController(mail);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/map.fxml"));
            loader.setController(cont);
            Parent root = loader.load();
            stage.setTitle("Mapa");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            Stage close=(Stage)fldGreska.getScene().getWindow();
            close.close();
        }
    }
}
