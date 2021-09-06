package ba.unsa.etf.icr.projekat;

import ba.unsa.etf.icr.projekat.model.Korisnik;

public class PrijavljeniKorisnik {
    public static Korisnik getKorisnik() {
        return korisnik;
    }

    public static void setKorisnik(Korisnik korisnik) {
        PrijavljeniKorisnik.korisnik = korisnik;
    }

    public static Korisnik korisnik;

}
