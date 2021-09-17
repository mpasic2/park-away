package ba.unsa.etf.icr.projekat;

import ba.unsa.etf.icr.projekat.model.Korisnik;
import ba.unsa.etf.icr.projekat.model.Parking;
import ba.unsa.etf.icr.projekat.model.Racun;
import ba.unsa.etf.icr.projekat.model.Vozilo;

public class PrijavljeniKorisnik {
    public static Korisnik getKorisnik() {
        return korisnik;
    }

    public static void setKorisnik(Korisnik korisnik) {
        PrijavljeniKorisnik.korisnik = korisnik;
    }

    public static Korisnik korisnik;
    public static Racun trenutniRacun;

    public static Parking getTrenutniParking() {
        return trenutniParking;
    }

    public static void setTrenutniParking(Parking trenutniParking) {
        PrijavljeniKorisnik.trenutniParking = trenutniParking;
    }

    public static Parking trenutniParking;
    public static Racun getTrenutniRacun() { return trenutniRacun; }

    public static void setTrenutniRacun(Racun trenutniRacun) { PrijavljeniKorisnik.trenutniRacun = trenutniRacun; }
}
