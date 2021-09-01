package ba.unsa.etf.icr.projekat.model;

import ba.unsa.etf.icr.projekat.model.Grad;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Lokacija {
    SimpleIntegerProperty lokacijaId;
    SimpleObjectProperty<Grad> grad;
    SimpleStringProperty ulica;

    public Lokacija(Integer lokacijaId, Grad grad, String ulica) {
        this.lokacijaId = new SimpleIntegerProperty(lokacijaId) ;
        this.grad = new SimpleObjectProperty<Grad>(grad);
        this.ulica = new SimpleStringProperty(ulica);
    }

    public int getLokacijaId() {
        return lokacijaId.get();
    }

    public SimpleIntegerProperty lokacijaIdProperty() {
        return lokacijaId;
    }

    public void setLokacijaId(int lokacijaId) {
        this.lokacijaId.set(lokacijaId);
    }

    public Grad getGrad() {
        return grad.get();
    }

    public SimpleObjectProperty<Grad> gradProperty() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad.set(grad);
    }

    public String getUlica() {
        return ulica.get();
    }

    public SimpleStringProperty ulicaProperty() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica.set(ulica);
    }
}

