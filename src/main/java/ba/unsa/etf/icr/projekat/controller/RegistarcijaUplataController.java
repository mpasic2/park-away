package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.model.Kartica;
import ba.unsa.etf.icr.projekat.model.Korisnik;
import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class RegistarcijaUplataController implements Initializable {

    public TextField fldImeKartica;
    public TextField fldBrojCard;
    public TextField fldCvc;
    public JFXCheckBox checkPaypal;
    public JFXCheckBox checkMaster;
    public ComboBox<Integer> choiceGodin;
    public ComboBox<Integer> choiceMjesec;
    private Korisnik korisnik;
    private Kartica kartica;
    public RegistarcijaUplataController(Korisnik k, Kartica kar) {
        korisnik = k;
        kartica = kar;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fldImeKartica.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldBrojCard.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        fldCvc.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        checkPaypal.selectedProperty().addListener((obs, wasOn, isNowOn) -> {
            if (isNowOn == true) {
                checkMaster.selectedProperty().set(false);
            }
        });
        checkMaster.selectedProperty().addListener((obs, wasOn, isNowOn) -> {
            if (isNowOn == true) {
                checkPaypal.selectedProperty().set(false);
            }
        });
        ObservableList<Integer> godine = FXCollections.observableArrayList();
        for(int i = LocalDate.now().getYear();i < LocalDate.now().getYear() + 10;i++){
            godine.add(i);
        }
        choiceGodin.setItems(godine);

        ObservableList<Integer> mjeseci = FXCollections.observableArrayList();
        for(int i = 1;i <= 12;i++){
            mjeseci.add(i);
        }
        choiceMjesec.setItems(mjeseci);
        choiceMjesec.setPromptText("Mjesec isticanja");
        choiceGodin.setPromptText("Godina isticanja");
        if(kartica!=null){
            fldImeKartica.setText(kartica.getVlasnik());
            fldBrojCard.setText(kartica.getBrojKartice());
            fldCvc.setText(String.valueOf(kartica.getCwc()));
            choiceGodin.getSelectionModel().select(kartica.getGodinaIsticanja());
            choiceMjesec.getSelectionModel().select(kartica.getMjesecIsticanja());
            if(kartica.getTip()==0){
                checkMaster.selectedProperty().set(true);
            }else{
                checkPaypal.selectedProperty().set(true);
            }
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda");
        alert.setHeaderText("Ovom radnjom ćete se vratiti korak unazad");
        alert.setContentText("Da li ste sigurni da se želite vratiti? Unesena polja se neće spasiti!");

        ButtonType buttonDa = new ButtonType("Da");
        ButtonType buttonNe = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonDa,buttonNe);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonDa){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija_korisnik_personal.fxml"));
            loader.setController(new RegistracijaPersonalController(korisnik));
            Parent root = loader.load();
            Stage stage=new Stage();
            stage.setTitle("Registracija");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    stage.close();
                }
            });
            stage.show();
            Stage close=(Stage)choiceGodin.getScene().getWindow();
            close.close();
        }
    }

    public void nastavi(ActionEvent actionEvent) throws IOException {
        if(validacijaUplata()){
            Integer tip = -1;
            if(checkMaster.selectedProperty().get()){
                tip = 0;// 0 = Master Card
            }else if(checkPaypal.selectedProperty().get()){
                tip = 1;
            }
            kartica = new Kartica(0,fldImeKartica.getText(),tip,fldBrojCard.getText(),choiceGodin.getValue(),choiceMjesec.getValue(),Integer.parseInt(fldCvc.getText()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registracija_login_data.fxml"));
            loader.setController(new RegistracijaLogInInfoController(korisnik,kartica));
            Parent root = loader.load();
            Stage stage=new Stage();
            stage.setTitle("Registracija");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    stage.close();
                }
            });
            stage.show();
            Stage close=(Stage)fldBrojCard.getScene().getWindow();
            close.close();
        };
    }

    Boolean validacijaUplata(){
        String imeKartia = fldImeKartica.getText();
        Integer godina = choiceGodin.getValue();
        Integer mjesec = choiceMjesec.getValue();
        String broj = fldBrojCard.getText();
        String cvc = fldCvc.getText();
        Integer tip = -1;
        if(checkMaster.selectedProperty().get()){
            tip = 0;// 0 = Master Card
        }else if(checkPaypal.selectedProperty().get()){
            tip = 1;
        }
        Boolean validno = true;
       if(imeKartia.length()==0){
            validno = false;
            fldImeKartica.getStyleClass().add("neValid");
        }
        else{
            fldImeKartica.getStyleClass().remove("neValid");
        }
        if(godina == null){
            validno = false;
            choiceGodin.getStyleClass().add("neValid");
        }else{
            choiceGodin.getStyleClass().remove("neValid");
        }
        if(mjesec == null){
            validno = false;
            choiceMjesec.getStyleClass().add("neValid");
        }else{
            choiceMjesec.getStyleClass().remove("neValid");
        }
        if(tip == -1){
            validno = false;
            checkMaster.getStyleClass().add("neValid");
            checkPaypal.getStyleClass().add("neValid");
        }else{
            checkMaster.getStyleClass().remove("neValid");
            checkPaypal.getStyleClass().remove("neValid");
        }
        if(broj.length()==0){
            validno = false;
            fldBrojCard.getStyleClass().add("neValid");
        }else {
            fldBrojCard.getStyleClass().remove("neValid");
        }
        if(cvc.length()==0){
            validno = false;
            fldCvc.getStyleClass().add("neValid");
        }else{
            fldCvc.getStyleClass().remove("neValid");
        }
        return validno;
    }
}
