package ba.unsa.etf.icr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Grad {
        SimpleIntegerProperty gradId;
        SimpleStringProperty naziv;
        public Grad(Integer grad_id, String naziv) {
                this.gradId =new SimpleIntegerProperty(grad_id);
                this.naziv = new SimpleStringProperty(naziv);
        }

        public int getGrad_id() {
                return gradId.get();
        }

        public SimpleIntegerProperty grad_idProperty() {
                return gradId;
        }

        public void setGrad_id(int grad_id) {
                this.gradId.set(grad_id);
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

        @Override
        public String toString() {
                return getNaziv();
        }
}
