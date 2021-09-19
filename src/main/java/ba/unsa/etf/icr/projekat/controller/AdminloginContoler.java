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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class AdminloginContoler implements Initializable {
    public ImageView imgAbout;
    public Label fldGreska;
    public TextField fldAdmin;
    public PasswordField fldAdminPassword;
    private ParkAwayDAO dao = new ParkAwayDAO();
    public Korisnik korisnik;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("/img/logo.png");
        imgAbout.setImage(image);
        fldAdmin.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldAdminPassword.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

    }


    public void prijavaAdmina(ActionEvent actionEvent) throws IOException {
        
        String mail = fldAdmin.getText();
        String password = fldAdminPassword.getText();
        if(mail.equals("admin@gmail.com") && password.equals("admin")){

            Stage noviProzor = new Stage();
            Parent roditelj = FXMLLoader.load(getClass().getResource("/fxml/administrator.fxml"));
            noviProzor.setTitle("Administrator");
            noviProzor.initStyle(StageStyle.UNDECORATED);
            Scene scene = new  Scene(roditelj, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
            noviProzor.setScene(scene);
            noviProzor.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    noviProzor.close();
                }
            });
            noviProzor.show();
            Stage zatvaranjePoruka=(Stage)fldGreska.getScene().getWindow();
            zatvaranjePoruka.close();
        }
        else{
            fldGreska.setText("Unesene informacije nisu tačne, pokušajte ponovo");
            fldAdmin.requestFocus();
        }
    }

    public void actionOdustani(ActionEvent actionEvent) throws IOException {

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Prijava");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                primaryStage.close();
            }
        });
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();


        Stage zatvaranjePoruka=(Stage)fldGreska.getScene().getWindow();
        zatvaranjePoruka.close();
    }
}
