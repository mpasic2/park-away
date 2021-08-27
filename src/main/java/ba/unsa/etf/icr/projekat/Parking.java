package ba.unsa.etf.icr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Parking {
    SimpleIntegerProperty parkingId;
    SimpleStringProperty naziv;
    SimpleObjectProperty<Lokacija> lokacija;
    SimpleIntegerProperty cijena;
    SimpleObjectProperty<LocalTime> pocetakRadnogVremena;
    SimpleObjectProperty<LocalTime> krajRadnogVremena;
    SimpleIntegerProperty stalniParking;
    SimpleIntegerProperty ocjena;
    SimpleStringProperty opis;

    public Parking(int parkingId, String naziv, Lokacija lokacija, int cijena, LocalTime pocetakRadnogVremena, LocalTime krajRadnogVremena, int stalniParking, int ocjena, String opis) {
        this.parkingId = new  SimpleIntegerProperty(parkingId);
        this.naziv = new SimpleStringProperty(naziv);
        this.lokacija = new SimpleObjectProperty<Lokacija>(lokacija);
        this.cijena = new SimpleIntegerProperty(cijena);
        this.pocetakRadnogVremena = new SimpleObjectProperty<LocalTime>(pocetakRadnogVremena);
        this.krajRadnogVremena = new SimpleObjectProperty<LocalTime>(krajRadnogVremena);
        this.stalniParking = new SimpleIntegerProperty(stalniParking);
        this.ocjena = new SimpleIntegerProperty(ocjena);
        this.opis = new SimpleStringProperty(opis);
    }

    public LocalTime getPocetakRadnogVremena() {
        return pocetakRadnogVremena.get();
    }

    public SimpleObjectProperty<LocalTime> pocetakRadnogVremenaProperty() {
        return pocetakRadnogVremena;
    }

    public void setPocetakRadnogVremena(LocalTime pocetakRadnogVremena) {
        this.pocetakRadnogVremena.set(pocetakRadnogVremena);
    }

    public LocalTime getKrajRadnogVremena() {
        return krajRadnogVremena.get();
    }

    public SimpleObjectProperty<LocalTime> krajRadnogVremenaProperty() {
        return krajRadnogVremena;
    }

    public void setKrajRadnogVremena(LocalTime krajRadnogVremena) {
        this.krajRadnogVremena.set(krajRadnogVremena);
    }
    public int getParkingId() {
        return parkingId.get();
    }

    public SimpleIntegerProperty parkingIdProperty() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId.set(parkingId);
    }

    public String getNaziv() {
        return naziv.get();
    }

    public SimpleStringProperty nazivProperty() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv.set(naziv);
    }

    public Lokacija getLokacija() {
        return lokacija.get();
    }

    public SimpleObjectProperty<Lokacija> lokacijaProperty() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija.set(lokacija);
    }

    public int getCijena() {
        return cijena.get();
    }

    public SimpleIntegerProperty cijenaProperty() {
        return cijena;
    }

    public void setCijena(int cijena) {
        this.cijena.set(cijena);
    }

    public int getStalniParking() {
        return stalniParking.get();
    }

    public SimpleIntegerProperty stalniParkingProperty() {
        return stalniParking;
    }

    public void setStalniParking(int stalniParking) {
        this.stalniParking.set(stalniParking);
    }

    public int getOcjena() {
        return ocjena.get();
    }

    public SimpleIntegerProperty ocjenaProperty() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena.set(ocjena);
    }

    public String getOpis() {
        return opis.get();
    }

    public SimpleStringProperty opisProperty() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis.set(opis);
    }
}
