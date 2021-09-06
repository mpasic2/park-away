package ba.unsa.etf.icr.projekat;

import ba.unsa.etf.icr.projekat.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalTime;

public class ParkAwayDAO {
    private Connection con;
    private static ParkAwayDAO instance;
    private PreparedStatement getUsers,getCity,getLocation, getParking, getFree, addCard, addUser,addLokacija, addVozilo, getParkingImages;
    public static ParkAwayDAO getInstance() {
        if (instance == null) instance = new ParkAwayDAO();
        return instance;
    }
    public ParkAwayDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:parkAwayDB.db");
            getCity = con.prepareStatement("Select * from grad");
            getLocation = con.prepareStatement("SELECT * FROM Lokacja");
            getParking = con.prepareStatement("SELECT * FROM Parking");
            getFree=con.prepareStatement("SELECT count(parking_mjesto_id) from Parking_mjesto where parking_id=? and vozilo_id is NULL");
            getUsers = con.prepareStatement("Select * from korisnik");
            getParkingImages = con.prepareStatement("Select * from slike where parking_id=?");
            addCard =  con.prepareStatement("Insert into kartica values (?,?,?,?,?,?,?)");
            addUser =  con.prepareStatement("Insert into korisnik values (?,?,?,?,?,?,?,?)");
            addLokacija =  con.prepareStatement("Insert into lokacja values (?,?,?)");
            addVozilo =  con.prepareStatement("Insert into vozilo values (?,?,?,?,?)");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
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
            addVozilo.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
