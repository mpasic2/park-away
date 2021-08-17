package ba.unsa.etf.icr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ProfilController {
    public ImageView profileImg;


    public void logOut(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        loginContoler lgn = new loginContoler();
        String ime = lgn.name;
        mapController cont=new mapController(ime);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/map.fxml"));
        loader.setController(cont);
        Parent root = loader.load();
        stage.setTitle("Mapa");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Stage close=(Stage)profileImg.getScene().getWindow();
        close.close();


    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/profil.fxml"));
        stage.setTitle("Profil");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Stage close=(Stage)profileImg.getScene().getWindow();
        close.close();
    }

    public void locationAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        loginContoler lgn = new loginContoler();
        String ime = lgn.name;
        mapController cont=new mapController(ime);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/map.fxml"));
        loader.setController(cont);
        Parent root = loader.load();
        stage.setTitle("Mapa");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Stage close=(Stage)profileImg.getScene().getWindow();
        close.close();
    }

    public void carMapAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/parkingDetails.fxml"));
        stage.setTitle("Status vozila");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Stage close=(Stage)profileImg.getScene().getWindow();
        close.close();
    }

    public void messageAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/statusVozila.fxml"));
        stage.setTitle("Status vozila");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Stage close=(Stage)profileImg.getScene().getWindow();
        close.close();
    }
}
