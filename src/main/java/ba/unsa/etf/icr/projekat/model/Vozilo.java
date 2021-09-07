package ba.unsa.etf.icr.projekat.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Vozilo {
    SimpleIntegerProperty vozilo_id;
    SimpleStringProperty registracijska_oznaka;
    SimpleStringProperty model;
    SimpleObjectProperty<Korisnik> korisnik;
    SimpleStringProperty sasija;

    public Vozilo(Integer vozilo_id, String registracijska_oznaka, String model, Korisnik korisnik, String sasija) {
        this.vozilo_id = new SimpleIntegerProperty(vozilo_id);
        this.registracijska_oznaka = new SimpleStringProperty(registracijska_oznaka);
        this.model = new SimpleStringProperty(model);
        this.korisnik = new SimpleObjectProperty<Korisnik>(korisnik);
        this.sasija =  new SimpleStringProperty(sasija);
    }

    public int getVozilo_id() {
        return vozilo_id.get();
    }

    public SimpleIntegerProperty vozilo_idProperty() {
        return vozilo_id;
    }

    public void setVozilo_id(int vozilo_id) {
        this.vozilo_id.set(vozilo_id);
    }

    public String getRegistracijska_oznaka() {
        return registracijska_oznaka.get();
    }

    public SimpleStringProperty registracijska_oznakaProperty() {
        return registracijska_oznaka;
    }

    public void setRegistracijska_oznaka(String registracijska_oznaka) {
        this.registracijska_oznaka.set(registracijska_oznaka);
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public Korisnik getKorisnik() {
        return korisnik.get();
    }

    public SimpleObjectProperty<Korisnik> korisnikProperty() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik.set(korisnik);
    }

    public String getSasija() {
        return sasija.get();
    }

    public SimpleStringProperty sasijaProperty() {
        return sasija;
    }

    public void setSasija(String sasija) {
        this.sasija.set(sasija);
    }

    @Override
    public String toString() {
        return getModel();
    }
}
