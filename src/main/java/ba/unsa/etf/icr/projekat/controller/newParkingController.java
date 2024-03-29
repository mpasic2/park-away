package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.model.Grad;
import ba.unsa.etf.icr.projekat.model.Lokacija;
import ba.unsa.etf.icr.projekat.model.Parking;
import com.jfoenix.controls.JFXTimePicker;
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
import java.time.LocalTime;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class newParkingController implements Initializable {
    public TextField fldNazivParkinga;
    public Label fldGreska;
    public TextArea fldOpis;
    public ChoiceBox<Lokacija> cbLokacija;
    public TextField fldCijena;
    public CheckBox cbStalni;
    public Slider sldOcjena;
    public JFXTimePicker sldPocetak;
    public JFXTimePicker sldKraj;
    private ParkAwayDAO dao = ParkAwayDAO.getInstance();
    public ObservableList<Lokacija> lokacije = FXCollections.observableArrayList();


    public void dodajAction(ActionEvent actionEvent) throws IOException {
        int vel = dao.dajParkinge().size();
        LocalTime t1 = sldPocetak.valueProperty().get();
        LocalTime t2 = sldKraj.valueProperty().get();
        Lokacija lok = dao.dajLokacije().get(1);
        int isSelected = 0;
        if(cbStalni.isSelected()) isSelected=1;

        Parking parking = new Parking(vel+1,fldNazivParkinga.getText(),lok,Integer.parseInt(fldCijena.getText()),t1,t2,isSelected,(int) sldOcjena.getValue(),fldOpis.getText());

        dao.addParking(parking);


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("POTVRDA");
        alert.setHeaderText("Uspješno dodan parking");
        alert.setContentText("Čestitamo!");
        alert.showAndWait();
        Stage zatvaranjePoruka = (Stage) cbStalni.getScene().getWindow();
        zatvaranjePoruka.close();

        Stage noviProzor1 = new Stage();
        Parent roditelj1 = FXMLLoader.load(getClass().getResource("/fxml/administrator.fxml"));
        noviProzor1.setTitle("Administrator");
        noviProzor1.initStyle(StageStyle.UNDECORATED);
        Scene scene1 = new  Scene(roditelj1, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        noviProzor1.setScene(scene1);
        noviProzor1.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbLokacija.getSelectionModel().selectFirst();

        lokacije=dao.dajLokacije();
        cbLokacija.setItems(lokacije);

    }

    public void backAction(ActionEvent actionEvent) throws IOException {
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
}
