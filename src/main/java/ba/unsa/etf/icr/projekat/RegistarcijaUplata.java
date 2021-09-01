package ba.unsa.etf.icr.projekat;

import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class RegistarcijaUplata implements Initializable {

    public TextField fldImeKartica;
    public TextField fldBrojCard;
    public TextField fldCvc;
    public JFXCheckBox checkPaypal;
    public JFXCheckBox checkMaster;
    public ComboBox<Integer> choiceGodin;
    public ComboBox<Integer> choiceMjesec;
    private Korisnik korisnik;
    public RegistarcijaUplata(Korisnik k) {
        korisnik = k;
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
            stage.show();
            Stage close=(Stage)choiceGodin.getScene().getWindow();
            close.close();
        }
    }

    public void nastavi(ActionEvent actionEvent) {
        validacijaUplata();
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
