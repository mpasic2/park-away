package ba.unsa.etf.icr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ParkAwayDAO {
    private Connection con;
    private PreparedStatement getUsers,getCity,getLocation;
    public ParkAwayDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:parkAwayDB.db");
            getCity = con.prepareStatement("Select * from grad");
            getLocation = con.prepareStatement("SELECT * FROM Lokacja");
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
}
