package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.model.Korisnik;
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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class AdminloginContoler implements Initializable {
    public ImageView imgAbout;
    public PasswordField fldPassword;
    public TextField fldUser;
    public Label fldGreska;
    private ParkAwayDAO dao = new ParkAwayDAO();
    public Korisnik korisnik;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("/img/logo.png");
        imgAbout.setImage(image);
        fldUser.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldPassword.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

    }


    public void prijavaAdmina(ActionEvent actionEvent) throws IOException {
        ObservableList<Korisnik> k = dao.dajKorisnike();
        String mail = fldUser.getText();
        String password = fldPassword.getText();
        int j = 0;
        korisnik = k.get(0);
        for(int i = 0;i < k.size();i++){
            if(mail.equals(k.get(i).getEmail())  && password.equals(k.get(i).getLozinka())){
                j++;
                korisnik = k.get(i);
                break;
            }
        }
        if(j == 0){
            fldGreska.setText("Unesene informacije nisu tačne, pokušajte ponovo");
            fldUser.requestFocus();
        }else{

            Stage noviProzor = new Stage();
            Parent roditelj = FXMLLoader.load(getClass().getResource("/fxml/administrator.fxml"));
            noviProzor.setTitle("Administrator");
            noviProzor.initStyle(StageStyle.UNDECORATED);
            Scene scene = new  Scene(roditelj, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            noviProzor.setScene(scene);
            noviProzor.show();
            Stage zatvaranjePoruka=(Stage)fldGreska.getScene().getWindow();
            zatvaranjePoruka.close();
        }
    }
}
