package ba.unsa.etf.icr.projekat.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class mapFilterController implements Initializable {
    @FXML
    public JFXSlider minCijenaFilter;
    @FXML
    public JFXCheckBox stalniFilter;
    public JFXCheckBox nadkriveniFilter;
    public JFXTimePicker pocetakRadaFilter;
    public JFXTimePicker krajRadaFilter;
    public JFXSlider ocjenaFilter;
    public JFXSlider maxCijenaFilter;
    public Button btnRefresh;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.minCijenaFilter.setValue(0);
        this.ocjenaFilter.setValue(1);

    }
    public mapFilterController(JFXSlider minCijenaFilter, JFXCheckBox stalniFilter, JFXCheckBox nadkriveniFilter, JFXTimePicker pocetakRadaFilter, JFXTimePicker krajRadaFilter, JFXSlider ocjenaFilter, JFXSlider maxCijenaFilter) {
        this.minCijenaFilter=minCijenaFilter;
        this.stalniFilter=stalniFilter;
        this.nadkriveniFilter=nadkriveniFilter;
        this.pocetakRadaFilter=pocetakRadaFilter;
        this.krajRadaFilter=krajRadaFilter;
        this.ocjenaFilter=ocjenaFilter;
        this.maxCijenaFilter=maxCijenaFilter;
    }

    public mapFilterController() {
    }

    public JFXSlider getMinCijenaFilter() {
        return minCijenaFilter;
    }

    public void setMinCijenaFilter(JFXSlider minCijenaFilter) {
        this.minCijenaFilter = minCijenaFilter;
    }

    public JFXCheckBox getStalniFilter() {
        return stalniFilter;
    }

    public void setStalniFilter(JFXCheckBox stalniFilter) {
        this.stalniFilter = stalniFilter;
    }

    public JFXCheckBox getNadkriveniFilter() {
        return nadkriveniFilter;
    }

    public void setNadkriveniFilter(JFXCheckBox nadkriveniFilter) {
        this.nadkriveniFilter = nadkriveniFilter;
    }

    public JFXTimePicker getPocetakRadaFilter() {
        return pocetakRadaFilter;
    }

    public void setPocetakRadaFilter(JFXTimePicker pocetakRadaFilter) {
        this.pocetakRadaFilter = pocetakRadaFilter;
    }

    public JFXTimePicker getKrajRadaFilter() {
        return krajRadaFilter;
    }

    public void setKrajRadaFilter(JFXTimePicker krajRadaFilter) {
        this.krajRadaFilter = krajRadaFilter;
    }

    public JFXSlider getOcjenaFilter() {
        return ocjenaFilter;
    }

    public void setOcjenaFilter(JFXSlider ocjenaFilter) {
        this.ocjenaFilter = ocjenaFilter;
    }

    public JFXSlider getMaxCijenaFilter() {
        return maxCijenaFilter;
    }

    public void setMaxCijenaFilter(JFXSlider maxCijenaFilter) {
        this.maxCijenaFilter = maxCijenaFilter;
    }


}
