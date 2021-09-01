package ba.unsa.etf.icr.projekat.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Kartica {
    SimpleIntegerProperty karticaId;
    SimpleStringProperty vlasnik;
    SimpleIntegerProperty tip;
    SimpleStringProperty brojKartice;
    SimpleIntegerProperty godinaIsticanja;
    SimpleIntegerProperty mjesecIsticanja;
    SimpleIntegerProperty cwc;

    public Kartica(Integer karticaId, String vlasnik, Integer tip, String brojKartice, Integer godinaIsticanja, Integer mjesecIsticanja, Integer cwc) {
        this.karticaId = new SimpleIntegerProperty(karticaId);
        this.vlasnik = new SimpleStringProperty(vlasnik);
        this.tip = new SimpleIntegerProperty(tip);
        this.brojKartice = new SimpleStringProperty(brojKartice);
        this.godinaIsticanja = new SimpleIntegerProperty(godinaIsticanja);
        this.mjesecIsticanja = new SimpleIntegerProperty(mjesecIsticanja);
        this.cwc = new SimpleIntegerProperty(cwc);
    }

    public int getKarticaId() {
        return karticaId.get();
    }

    public SimpleIntegerProperty karticaIdProperty() {
        return karticaId;
    }

    public void setKarticaId(int karticaId) {
        this.karticaId.set(karticaId);
    }

    public String getVlasnik() {
        return vlasnik.get();
    }

    public SimpleStringProperty vlasnikProperty() {
        return vlasnik;
    }

    public void setVlasnik(String vlasnik) {
        this.vlasnik.set(vlasnik);
    }

    public int getTip() {
        return tip.get();
    }

    public SimpleIntegerProperty tipProperty() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip.set(tip);
    }

    public String getBrojKartice() {
        return brojKartice.get();
    }

    public SimpleStringProperty brojKarticeProperty() {
        return brojKartice;
    }

    public void setBrojKartice(String brojKartice) {
        this.brojKartice.set(brojKartice);
    }

    public int getGodinaIsticanja() {
        return godinaIsticanja.get();
    }

    public SimpleIntegerProperty godinaIsticanjaProperty() {
        return godinaIsticanja;
    }

    public void setGodinaIsticanja(int godinaIsticanja) {
        this.godinaIsticanja.set(godinaIsticanja);
    }

    public int getMjesecIsticanja() {
        return mjesecIsticanja.get();
    }

    public SimpleIntegerProperty mjesecIsticanjaProperty() {
        return mjesecIsticanja;
    }

    public void setMjesecIsticanja(int mjesecIsticanja) {
        this.mjesecIsticanja.set(mjesecIsticanja);
    }

    public int getCwc() {
        return cwc.get();
    }

    public SimpleIntegerProperty cwcProperty() {
        return cwc;
    }

    public void setCwc(int cwc) {
        this.cwc.set(cwc);
    }
}
