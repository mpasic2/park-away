package ba.unsa.etf.icr.projekat.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalTime;

public class Racun {
    SimpleIntegerProperty racunId;
    SimpleIntegerProperty voziloId;
    SimpleIntegerProperty parkingMjestoId;
    SimpleIntegerProperty placeno;
    SimpleObjectProperty<LocalTime> prijava;
    SimpleObjectProperty<LocalTime> odjava;

    public Racun(int racunId, int voziloId, int parkingMjestoId, int placeno, LocalTime prijava, LocalTime odjava) {
        this.racunId = new  SimpleIntegerProperty(racunId);
        this.voziloId = new  SimpleIntegerProperty(voziloId);
        this.parkingMjestoId = new  SimpleIntegerProperty(parkingMjestoId);
        this.placeno = new  SimpleIntegerProperty(placeno);
        this.prijava = new SimpleObjectProperty<LocalTime>(prijava);
        this.odjava = new SimpleObjectProperty<LocalTime>(odjava);
    }

    public int getRacunId() {
        return racunId.get();
    }

    public SimpleIntegerProperty racunIdProperty() {
        return racunId;
    }

    public void setRacunId(int racunId) {
        this.racunId.set(racunId);
    }

    public int getVoziloId() {
        return voziloId.get();
    }

    public SimpleIntegerProperty voziloIdProperty() {
        return voziloId;
    }

    public void setVoziloId(int voziloId) {
        this.voziloId.set(voziloId);
    }

    public int getParkingMjestoId() {
        return parkingMjestoId.get();
    }

    public SimpleIntegerProperty parkingMjestoIdProperty() {
        return parkingMjestoId;
    }

    public void setParkingMjestoId(int parkingMjestoId) {
        this.parkingMjestoId.set(parkingMjestoId);
    }

    public int getPlaceno() {
        return placeno.get();
    }

    public SimpleIntegerProperty placenoProperty() {
        return placeno;
    }

    public void setPlaceno(int placeno) {
        this.placeno.set(placeno);
    }

    public LocalTime getPrijava() {
        return prijava.get();
    }

    public SimpleObjectProperty<LocalTime> prijavaProperty() {
        return prijava;
    }

    public void setPrijava(LocalTime prijava) {
        this.prijava.set(prijava);
    }

    public LocalTime getOdjava() {
        return odjava.get();
    }

    public SimpleObjectProperty<LocalTime> odjavaProperty() {
        return odjava;
    }

    public void setOdjava(LocalTime odjava) {
        this.odjava.set(odjava);
    }
}
