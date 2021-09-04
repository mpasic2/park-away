package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.model.Kartica;
import ba.unsa.etf.icr.projekat.model.Korisnik;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistracijaLogInInfoController implements Initializable {
    public TextField fldLozinka2;
    public TextField fldLozinka1;
    public TextField fldMail;
    public ProgressBar progessPass;
    public Label statusLabel;
    private Korisnik korisnik;
    private Kartica kartica;
    private ParkAwayDAO dao = new ParkAwayDAO();

    public RegistracijaLogInInfoController(Korisnik korisnik, Kartica kartica) {
        this.korisnik = korisnik;
        this.kartica = kartica;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fldLozinka1.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldLozinka2.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldMail.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldLozinka1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                progessPass.setProgress(t1.length()/7.);
                if(t1.length()<4){
                    progessPass.getStyleClass().removeAll("zeleniProgress");
                    progessPass.getStyleClass().removeAll("zutiProgress");
                    progessPass.getStyleClass().add("crveniProgress");
                    statusLabel.setText("Slaba");
                }
                else if(t1.length()<7){
                    progessPass.getStyleClass().removeAll("crveniProgress");
                    progessPass.getStyleClass().removeAll("zeleniProgress");
                    progessPass.getStyleClass().add("zutiProgress");
                    statusLabel.setText("Srednja");
                }else{
                    progessPass.getStyleClass().removeAll("crveniProgress");
                    progessPass.getStyleClass().add("zeleniProgress");
                    progessPass.getStyleClass().removeAll("zutiProgress");
                    statusLabel.setText("Jaka");
                }
            }
        });

    }

    public void nazad(ActionEvent actionEvent) {
    }

    public void dalje(ActionEvent actionEvent) {
        if(validacijaLog()){
            korisnik.setEmail(fldMail.getText());
            korisnik.setLozinka(fldLozinka1.getText());
            Integer idKard = dao.addCard(kartica);
            korisnik.setBrojRacuna(idKard);
            korisnik.getAdresaStanovanja().setLokacijaId(dao.addLocation(korisnik.getAdresaStanovanja()));
            dao.addKorisnik(korisnik);
        }
    }

    Boolean validacijaLog(){
        String mail = fldMail.getText();
        String pass1= fldLozinka1.getText();
        String pass2 = fldLozinka2.getText();
        Boolean validno = true;
        if(mail.length()==0){
            validno = false;
            fldMail.getStyleClass().add("neValid");
        }
        else{
            fldMail.getStyleClass().remove("neValid");
        }
        if(pass1.length() == 0){
            validno = false;
            fldLozinka1.getStyleClass().add("neValid");
        }else{
            fldLozinka1.getStyleClass().remove("neValid");
        }
        if(pass2.length() == 0 || !pass1.equals(pass2)){
            validno = false;
            fldLozinka2.getStyleClass().add("neValid");
        }else{
            fldLozinka2.getStyleClass().remove("neValid");
        }

        return validno;
    }
}
