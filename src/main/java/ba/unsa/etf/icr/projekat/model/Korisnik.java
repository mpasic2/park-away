package ba.unsa.etf.icr.projekat.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Korisnik {
    SimpleIntegerProperty korisnikId;
    SimpleStringProperty ime;
    SimpleStringProperty prezime;
    SimpleStringProperty brojTelefona;
    SimpleObjectProperty<Lokacija> adresaStanovanja;
    SimpleIntegerProperty brojRacuna;
    SimpleStringProperty slika;
    SimpleStringProperty email;
    SimpleStringProperty lozinka;

    public Korisnik(Integer korisnikId, String ime, String prezime, String brojTelefona, Lokacija adresaStanovanja, Integer brojRacuna, String slika, String email, String lozinka) {
        this.korisnikId = new SimpleIntegerProperty(korisnikId);
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.brojTelefona = new SimpleStringProperty(brojTelefona);
        this.adresaStanovanja = new SimpleObjectProperty<Lokacija>(adresaStanovanja);
        this.brojRacuna = new SimpleIntegerProperty(brojRacuna);
        this.slika = new SimpleStringProperty(slika);
        this.email = new SimpleStringProperty(email);
        this.lozinka = new SimpleStringProperty(lozinka);
    }

    public int getKorisnikId() {
        return korisnikId.get();
    }

    public SimpleIntegerProperty korisnikIdProperty() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId.set(korisnikId);
    }

    public String getIme() {
        return ime.get();
    }

    public SimpleStringProperty imeProperty() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public String getPrezime() {
        return prezime.get();
    }

    public SimpleStringProperty prezimeProperty() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime.set(prezime);
    }

    public String getBrojTelefona() {
        return brojTelefona.get();
    }

    public SimpleStringProperty brojTelefonaProperty() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona.set(brojTelefona);
    }

    public Lokacija getAdresaStanovanja() {
        return adresaStanovanja.get();
    }

    public SimpleObjectProperty<Lokacija> adresaStanovanjaProperty() {
        return adresaStanovanja;
    }

    public void setAdresaStanovanja(Lokacija adresaStanovanja) {
        this.adresaStanovanja.set(adresaStanovanja);
    }

    public int getBrojRacuna() {
        return brojRacuna.get();
    }

    public SimpleIntegerProperty brojRacunaProperty() {
        return brojRacuna;
    }

    public void setBrojRacuna(int brojRacuna) {
        this.brojRacuna.set(brojRacuna);
    }

    public String getSlika() {
        return slika.get();
    }

    public SimpleStringProperty slikaProperty() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika.set(slika);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getLozinka() {
        return lozinka.get();
    }

    public SimpleStringProperty lozinkaProperty() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka.set(lozinka);
    }
}

