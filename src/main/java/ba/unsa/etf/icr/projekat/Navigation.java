package ba.unsa.etf.icr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.event.ActionEvent;
import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Navigation {
    public Navigation(){};
    public void logOut(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        Node node = (Node) actionEvent.getSource();
        Stage close=(Stage)node.getScene().getWindow();
        close.close();
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });

    }

    public void profileAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/profil.fxml"));
        stage.setTitle("Profil");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        Node node = (Node) actionEvent.getSource();
        Stage close=(Stage)node.getScene().getWindow();
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
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        stage.show();
        Node node = (Node) actionEvent.getSource();
        Stage close=(Stage)node.getScene().getWindow();
        close.close();
    }

    public void carMapAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/statusVozila.fxml"));
        stage.setTitle("Status vozila");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Node node = (Node) actionEvent.getSource();
        Stage close=(Stage)node.getScene().getWindow();
        close.close();
    }

    public void messageAction(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/historijaPlacanja.fxml"));
        stage.setTitle("Historija placanja");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
        });
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        Node node = (Node) actionEvent.getSource();
        Stage close=(Stage)node.getScene().getWindow();
        close.close();
    }


}
