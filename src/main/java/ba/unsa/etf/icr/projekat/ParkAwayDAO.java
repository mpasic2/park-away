package ba.unsa.etf.icr.projekat;

import ba.unsa.etf.icr.projekat.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.*;
import java.time.LocalTime;
import java.util.Scanner;

public class ParkAwayDAO {
    private Connection con;
    private static ParkAwayDAO instance;
    private PreparedStatement getUsers,getCity,getLocation,addParking, getParking, getFree, addCard, addUser,obrisiParking, addLokacija,
            addVozilo, getParkingImages, izmijeniParking, getUserCars, getAllFreeBroj, getAllFreeId, dodajRacun, dajRacun, updateRacun,
            getParkingByParkingMjesto, dajVoziloById;
    public static ParkAwayDAO getInstance() {
        if (instance == null) instance = new ParkAwayDAO();
        return instance;
    }
    public ParkAwayDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:parkAwayDB.db");
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        }

        try {
            getUsers = con.prepareStatement("Select * from korisnik");
        } catch (SQLException  e) {
            regenerisiBazu();
            try {
                getUsers = con.prepareStatement("Select * from korisnik");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {

            getCity = con.prepareStatement("Select * from grad");
            getLocation = con.prepareStatement("SELECT * FROM Lokacja");
            getParking = con.prepareStatement("SELECT * FROM Parking");
            getFree = con.prepareStatement("SELECT count(parking_mjesto_id) from Parking_mjesto where parking_id=? and vozilo_id is NULL");
            getAllFreeBroj = con.prepareStatement("SELECT broj_parking_mjesta from Parking_mjesto where parking_id=? and vozilo_id is NULL");
            getAllFreeId = con.prepareStatement("SELECT parking_mjesto_id, broj_parking_mjesta from Parking_mjesto where parking_id=? and vozilo_id is NULL");
            getUsers = con.prepareStatement("Select * from korisnik");
            getParkingImages = con.prepareStatement("Select * from slike where parking_id=?");
            getParkingByParkingMjesto = con.prepareStatement("SELECT p.parking_id, naziv, lokacija_id, cijena, pocetak_radnog_vremena, kraj_radnog_vremena, stalni_parking, ocjena, opis  from parking p, parking_mjesto pm where pm.parking_id=p.parking_id and parking_mjesto_id=?");
            addCard =  con.prepareStatement("Insert into kartica values (?,?,?,?,?,?,?)");
            addUser =  con.prepareStatement("Insert into korisnik values (?,?,?,?,?,?,?,?)");
            addLokacija =  con.prepareStatement("Insert into lokacja values (?,?,?)");
            addVozilo =  con.prepareStatement("Insert into vozilo values (?,?,?,?,?)");
            addParking = con.prepareStatement("INSERT INTO parking values (?,?,?,?,?,?,?,?,?)");
            obrisiParking = con.prepareStatement("DELETE FROM parking where parking_id=?");
            izmijeniParking = con.prepareStatement("UPDATE parking SET naziv=?, lokacija_id=?, cijena=?, pocetak_radnog_vremena=?, kraj_radnog_vremena=?, stalni_parking=?, ocjena=?, opis=? WHERE parking_id=? ");
            getUserCars = con.prepareStatement("SELECT * FROM vozilo where korisnik_id=?");
            dodajRacun = con.prepareStatement("Insert into racun values (?,?,?,?,?,?)");
            dajRacun = con.prepareStatement("SELECT * FROM Racun");
            dajVoziloById= con.prepareStatement("SELECT * From vozilo WHERE vozilo_id=?");

            updateRacun = con.prepareStatement("UPDATE racun SET placeno=?, vrijeme_odjave=? WHERE racun_id=?");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeBase() throws SQLException {
        con.close();
        con = null;
    }
    private void regenerisiBazu() {
        Scanner ulaz = null;
        ulaz = new Scanner(getClass().getResourceAsStream("/db/parkAwayDB.db.sql"));
        String sqlUpit = "";
        while (ulaz.hasNext()) {
            sqlUpit += ulaz.nextLine();
            if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                try {
                    Statement stmt = con.createStatement();
                    stmt.execute(sqlUpit);
                    sqlUpit = "";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        ulaz.close();
    }
    public ObservableList<Grad> dajGradove(){
        ObservableList<Grad> gradovi = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = getCity.executeQuery();
            while(resultSet.next()) {
                Grad grad = new Grad(resultSet.getInt(1), resultSet.getString(2));
                gradovi.add(grad);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return gradovi;
    }
    public Grad pronadjiGrad(int id){
        ObservableList<Grad> gradovi = dajGradove();
        for(int i = 0; i< gradovi.size();i++){
            if(gradovi.get(i).getGrad_id() == id) return gradovi.get(i);
        }
        return new Grad(null,null);
    }
    public ObservableList<Lokacija> dajLokacije(){
        ObservableList<Lokacija> lokacije = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = getLocation.executeQuery();
            while(resultSet.next()) {
                Grad g = pronadjiGrad(resultSet.getInt(2));
                Lokacija adresa = new Lokacija(resultSet.getInt(1),g, resultSet.getString(3));
                lokacije.add(adresa);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lokacije;
    }
    public Lokacija pronadjiUlicu(int id){
        ObservableList<Lokacija> l = dajLokacije();
        for(int i = 0; i< l.size();i++){
            if(l.get(i).getLokacijaId()== id) return l.get(i);
        }
        return new Lokacija(null,null,null);
    }
    public ObservableList<Korisnik> dajKorisnike(){
        ObservableList<Korisnik> korisnici = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = getUsers.executeQuery();
            while(resultSet.next()) {
                Lokacija l = pronadjiUlicu(resultSet.getInt(5));
                Korisnik korisnik = new Korisnik(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),l, resultSet.getInt(6),resultSet.getString(7),resultSet.getString(8));
                korisnici.add(korisnik);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return korisnici;
    }
    public Korisnik pronadjiKorisnika(int id){
        ObservableList<Korisnik> k = dajKorisnike();
        for(int i = 0; i< k.size();i++){
            if(k.get(i).getKorisnikId()== id) return k.get(i);
        }
        return null;
    }

    public ObservableList<Parking> dajParkinge(){
        ObservableList<Parking> parkinzi = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = getParking.executeQuery();
            while(resultSet.next()) {
                Lokacija l = pronadjiUlicu(resultSet.getInt(3));
                Parking parking = new Parking(resultSet.getInt(1),resultSet.getString(2),l,
                        resultSet.getInt(4),LocalTime.parse(resultSet.getString(5)), LocalTime.parse(resultSet.getString(6)),
                        resultSet.getInt(7),resultSet.getInt(8),resultSet.getString(9));
                parkinzi.add(parking);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return parkinzi;
    }
    public Parking dajParkigByParkingMjesto(int id){
        try {
            getParkingByParkingMjesto.setInt(1,id);
            ResultSet resultSet = getParkingByParkingMjesto.executeQuery();
            Lokacija l = pronadjiUlicu(resultSet.getInt(3));
            Parking parking= new Parking(resultSet.getInt(1),resultSet.getString(2),l,
                    resultSet.getInt(4),LocalTime.parse(resultSet.getString(5)), LocalTime.parse(resultSet.getString(6)),
                    resultSet.getInt(7),resultSet.getInt(8),resultSet.getString(9));
            return parking;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public Vozilo dajVozilo(int id){

        try {
            dajVoziloById.setInt(1,id);
            ResultSet resultSet = dajVoziloById.executeQuery();

            Korisnik k= pronadjiKorisnika(resultSet.getInt(4));
            return new Vozilo(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3), k,resultSet.getString(5));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public Integer dajBrojSlobodnihMjesta(int id) {
        try {
            getFree.setInt(1, id);
            ResultSet rs = getFree.executeQuery();
            if (!rs.next()) return null;
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ObservableList<Integer> dajSlobodnaMjesta(int id) {
        ObservableList<Integer> slobodnaMjesta = FXCollections.observableArrayList();
        try {
            getAllFreeBroj.setInt(1, id);
            ResultSet rs = getAllFreeBroj.executeQuery();
            while(rs.next()) {
                slobodnaMjesta.add(rs.getInt(1));
            }
            return slobodnaMjesta;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String dajSliku(int id) {
        try {
            getParkingImages.setInt(1, id);
            ResultSet rs = getParkingImages.executeQuery();
            if (!rs.next()) return null;
            return rs.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer addCard(Kartica k) {
        Integer i = 1;
        try {
            PreparedStatement newRiderStatement=con.prepareStatement("Select MAX(id)+1 from kartica ");
            ResultSet result = newRiderStatement.executeQuery();
            if(result.next()){
                i = result.getInt(1);
                k.setKarticaId(result.getInt(1));
            }else{
                k.setKarticaId(1);
            }
            addCard.setInt(1,k.getKarticaId());
            addCard.setString(2,k.getVlasnik());
            addCard.setInt(3,k.getTip());
            addCard.setString(4, k.getBrojKartice());
            addCard.setInt(5, k.getGodinaIsticanja());
            addCard.setInt(6,k.getMjesecIsticanja());
            addCard.setInt(7,k.getCwc());
            addCard.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public void addKorisnik(Korisnik k) {
        try {
            PreparedStatement newRiderStatement=con.prepareStatement("Select MAX(korisnik_id)+1 from korisnik ");
            ResultSet result = newRiderStatement.executeQuery();
            if(result.next()){
                k.setKorisnikId(result.getInt(1));
            }else{
                k.setKorisnikId(1);
            }
            addUser.setInt(1,k.getKorisnikId());
            addUser.setString(2,k.getIme());
            addUser.setString(3,k.getPrezime());
            addUser.setString(4, k.getBrojTelefona());
            addUser.setInt(5, k.getAdresaStanovanja().getLokacijaId());
            addUser.setInt(6,k.getBrojRacuna());
            addUser.setString(7,k.getEmail());
            addUser.setString(8,k.getLozinka());
            addUser.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer addLocation(Lokacija l) {
        try {
            PreparedStatement newRiderStatement=con.prepareStatement("Select MAX(lokacija_id)+1 from lokacja ");
            ResultSet result = newRiderStatement.executeQuery();
            if(result.next()){
                l.setLokacijaId(result.getInt(1));
            }else{
                l.setLokacijaId(1);
            }
            addLokacija.setInt(1,l.getLokacijaId());
            addLokacija.setInt(2,l.getGrad().getGrad_id());
            addLokacija.setString(3,l.getUlica());

            addLokacija.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l.getLokacijaId();
    }

    public void addVozilo(Vozilo v) {
        try {
            PreparedStatement newRiderStatement=con.prepareStatement("Select MAX(vozilo_id)+1 from vozilo ");
            ResultSet result = newRiderStatement.executeQuery();
            if(result.next()){
                v.setVozilo_id(result.getInt(1));
            }else{
                v.setVozilo_id(1);
            }
            addVozilo.setInt(1,v.getVozilo_id());
            addVozilo.setString(2,v.getRegistracijska_oznaka());
            addVozilo.setString(3,v.getModel());
            addVozilo.setInt(4, v.getKorisnik().getKorisnikId());
            addVozilo.setString(5, v.getSasija());
            addVozilo.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addParking(Parking p){
        try {
            PreparedStatement newParkingStatement=con.prepareStatement("Select MAX(parking_id)+1 from parking ");
            ResultSet result = newParkingStatement.executeQuery();
            if(result.next()){
                p.setParkingId(result.getInt(1));
            }else{
                p.setParkingId(1);
            }
            addParking.setInt(1,p.getParkingId());
            addParking.setString(2,p.getNaziv());
            addParking.setInt(3,p.getLokacija().getLokacijaId());
            addParking.setInt(4,p.getCijena());
            addParking.setString(5,p.getPocetakRadnogVremena().toString());
            addParking.setString(6,p.getKrajRadnogVremena().toString());
            addParking.setInt(7,p.getStalniParking());
            addParking.setInt(8,p.getOcjena());
            addParking.setString(9,p.getOpis());
            addParking.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiPaarking(Parking parking) {
        try {
            obrisiParking.setInt(1, parking.getParkingId());
            obrisiParking.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniParking(Parking parking){
        try{
            izmijeniParking.setString(1,parking.getNaziv());
            izmijeniParking.setInt(2,parking.getLokacija().getLokacijaId());
            izmijeniParking.setInt(3,parking.getCijena());
            izmijeniParking.setString(4,parking.getPocetakRadnogVremena().toString());
            izmijeniParking.setString(5,parking.getKrajRadnogVremena().toString());
            izmijeniParking.setInt(6,parking.getStalniParking());
            izmijeniParking.setInt(7,parking.getOcjena());
            izmijeniParking.setString(8,parking.getOpis());
            izmijeniParking.setInt(9,parking.getParkingId());
            izmijeniParking.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Vozilo> dajKorisnikovaAuta(Integer korisnikId) throws SQLException {
        getUserCars.setInt(1,korisnikId);
        ObservableList<Vozilo> vozila = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = getUserCars.executeQuery();
            while(resultSet.next()) {
                Korisnik l = pronadjiKorisnika(resultSet.getInt(4));
                Vozilo auto = new Vozilo(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),l,resultSet.getString(5));
                vozila.add(auto);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vozila;
    }

    public ObservableList<Racun> dajRacune(){
        ObservableList<Racun> racuni = FXCollections.observableArrayList();
        try {
            ResultSet rs = dajRacun.executeQuery();
            while(rs.next()) {

                Racun racun = new Racun(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        LocalTime.parse(rs.getString(5)),
                        LocalTime.parse(rs.getString(5)));
                racuni.add(racun);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return racuni;
    }


    public ObservableList<Pair<Integer, Integer>> dajSlobodnaMjestaId(int parkingId) {
        ObservableList<Pair<Integer, Integer>> slobodnaMjesta = FXCollections.observableArrayList();
        try {
            getAllFreeId.setInt(1, parkingId);
            ResultSet rs = getAllFreeId.executeQuery();
            while(rs.next()) {
                slobodnaMjesta.add(new Pair(rs.getInt(1), rs.getInt(2)));
            }
            return slobodnaMjesta;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Integer addRacun(int idMjesta,int idVozila,LocalTime vrijemePrijave) {
        try {
            PreparedStatement newRiderStatement=con.prepareStatement("Select MAX(racun_id)+1 from Racun");
            ResultSet result = newRiderStatement.executeQuery();
            dodajRacun.setInt(1,result.getInt(1));
            dodajRacun.setInt(2, idVozila);
            dodajRacun.setInt(3, idMjesta);
            dodajRacun.setInt(4, 0);
            dodajRacun.setString(5, LocalTime.now().toString());
            dodajRacun.setString(6,null);
            dodajRacun.execute();
            return result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void izmijeniRacun(Racun racun){
        try {
            updateRacun.setInt(1, 1);
            updateRacun.setString(2, LocalTime.now().toString());
            updateRacun.setInt(3, racun.getRacunId());
            updateRacun.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







}
