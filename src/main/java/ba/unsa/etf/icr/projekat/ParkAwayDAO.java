package ba.unsa.etf.icr.projekat;

import ba.unsa.etf.icr.projekat.model.Grad;
import ba.unsa.etf.icr.projekat.model.Korisnik;
import ba.unsa.etf.icr.projekat.model.Lokacija;
import ba.unsa.etf.icr.projekat.model.Parking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalTime;

public class ParkAwayDAO {
    private Connection con;
    private static ParkAwayDAO instance;
    private PreparedStatement getUsers,getCity,getLocation, getParking, getFree;
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
                Korisnik korisnik = new Korisnik(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),l, resultSet.getInt(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9));
                korisnici.add(korisnik);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return korisnici;
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
}
