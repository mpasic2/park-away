package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.PrijavljeniKorisnik;
import ba.unsa.etf.icr.projekat.model.Korisnik;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class loginContoler implements Initializable {
    public ImageView imgAbout;
    public PasswordField fldPassword;
    public TextField fldUser;
    public Label fldGreska;
    private ParkAwayDAO dao = new ParkAwayDAO();
    public Korisnik korisnik;
    public static Korisnik trenutniKorisnik;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("/img/logo.png");
        imgAbout.setImage(image);
        fldUser.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldPassword.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

    }

    public void prijava(ActionEvent actionEvent) throws IOException {
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
            Stage stage=new Stage();
            PrijavljeniKorisnik.setKorisnik(korisnik);
            mapController cont=new mapController(korisnik);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/map.fxml"));
            loader.setController(cont);
            Parent root = loader.load();
            stage.setTitle("Mapa");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    stage.close();
                }
            });
            stage.show();
            Stage close=(Stage)fldGreska.getScene().getWindow();
            close.close();
        }
    }

    public void registar(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija_korisnik_personal.fxml"));
        loader.setController(new RegistracijaPersonalController(null));
        Parent root = loader.load();
        stage.setTitle("Registracija");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        stage.show();
        Stage close=(Stage)fldGreska.getScene().getWindow();
        close.close();
    }

    public void prijavaAdmina(ActionEvent actionEvent) throws IOException {
        Stage noviProzor = new Stage();
        Parent roditelj = FXMLLoader.load(getClass().getResource("/fxml/administratorLogin.fxml"));
        noviProzor.setTitle("Prijava administratora");
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

    public void helpAction(MouseEvent mouseEvent) {
        Label lb = new Label("Ukoliko prvi put koristite apliakciju pritisnite dugme za registraciju,\n ukoliko ste već kreirali račun nastavite sa prijavom unoseći podatke u polja.");
        lb.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informativni ekran");
        alert.setHeaderText("Prijava");
        alert.getDialogPane().setContent(lb);
        alert.showAndWait();

    }
}
