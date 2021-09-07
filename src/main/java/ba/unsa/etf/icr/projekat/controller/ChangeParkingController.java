package ba.unsa.etf.icr.projekat.controller;

import ba.unsa.etf.icr.projekat.ParkAwayDAO;
import ba.unsa.etf.icr.projekat.model.Lokacija;
import ba.unsa.etf.icr.projekat.model.Parking;
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
import java.time.LocalTime;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ChangeParkingController implements Initializable {

    public TextField fldNazivParkinga;
    public Label fldGreska;
    public TextField fldOpis;
    public ChoiceBox cbLokacija;
    public TextField fldCijena;
    public CheckBox cbStalni;
    public Slider sldOcjena;
    public Slider sldPocetak;
    public Slider sldKraj;
    private Parking parking;
    private ParkAwayDAO dao;
    private int id;
    public ObservableList<Lokacija> lokacije = FXCollections.observableArrayList();
    public ObservableList<Parking> parkinzi = FXCollections.observableArrayList();
    public ChangeParkingController(Parking parking){
        dao = ParkAwayDAO.getInstance();
        this.parking=parking;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i=0;i<parkinzi.size();i++){
            if(parkinzi.get(i).getNaziv().equals(fldNazivParkinga.getText()))
                id=parkinzi.get(i).getParkingId();
        }

        lokacije=dao.dajLokacije();
        cbLokacija.setItems(lokacije);
        Lokacija lok = new Lokacija(dao.dajLokacije().get(parking.getLokacija().getLokacijaId()));


        fldNazivParkinga.setText(parking.getNaziv());
        fldCijena.setText(String.valueOf(parking.getCijena()));
        fldOpis.setText(parking.getOpis());
        sldPocetak.adjustValue(parking.getPocetakRadnogVremena().getHour());
        sldKraj.adjustValue(parking.getKrajRadnogVremena().getHour());
        sldOcjena.adjustValue(parking.getOcjena());
        cbStalni.setSelected(parking.getStalniParking() != 0);
        cbLokacija.getSelectionModel().select(lok);
    }

    public void izmijeniAction(ActionEvent actionEvent) {
        parkinzi.addAll(dao.dajParkinge());
        LocalTime t1 = LocalTime.of((int) sldPocetak.getValue(),0);
        LocalTime t2 = LocalTime.of((int) sldKraj.getValue(),0);
        Lokacija lok = dao.dajLokacije().get(1);
        int isSelected = 0;
        if(cbStalni.isSelected()) isSelected=1;

        Parking parking = new Parking(id,fldNazivParkinga.getText(),lok,Integer.parseInt(fldCijena.getText()),t1,t2,isSelected,(int) sldOcjena.getValue(),fldOpis.getText());

        dao.izmijeniParking(parking);


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("POTVRDA");
        alert.setHeaderText("Uspješno izmijenjen parking");
        alert.setContentText("Čestitamo!");
        alert.showAndWait();
        Stage zatvaranjePoruka = (Stage) sldKraj.getScene().getWindow();
        zatvaranjePoruka.close();
    }

    public void backAction(ActionEvent actionEvent) throws IOException {
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
