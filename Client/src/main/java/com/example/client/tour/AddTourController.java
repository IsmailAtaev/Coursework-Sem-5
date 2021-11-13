package com.example.client.tour;

import com.example.client.MainController;
import com.example.model.animation.Shake;
import com.example.model.check.Check;
import com.example.model.connect.Connect;
import com.example.model.tour.Tour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 *
 * */


public class AddTourController {

    private Connect connect = MainController.connect;

    @FXML
    private Button addTourPaneBtn;

    @FXML
    private Button closeTourPaneBtn;

    @FXML
    private TextField countryNameField;

    @FXML
    private TextField cityNameField;

    @FXML
    private TextField priceTourField;

    @FXML
    private TextField durationTourField;

    @FXML
    private TextField tourNameField;

    @FXML
    private TextField tourTypeField;

    @FXML
    private DatePicker dateTourField;

    @FXML
    void addTourPane(ActionEvent event) {
        Tour tour = new Tour();
        String price = priceTourField.getText().trim();
        String duration = durationTourField.getText().trim();
        LocalDate dateTour = dateTourField.getValue();
        if (Check.isFloat(price) && Check.isNumber(duration)) {

            tour.setPrice(Float.parseFloat(price));
            tour.setDuration(duration);
            tour.setTourDate(dateTour.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            tour.setTourName(tourNameField.getText().trim());
            tour.setTourType(tourTypeField.getText().trim());
            tour.setCountryName(countryNameField.getText().trim());
            tour.setCityName(cityNameField.getText().trim());
            System.out.println(tour.toString());

        } else {

            Shake shakePrice = new Shake(priceTourField);
            Shake shakeDuration = new Shake(durationTourField);

            shakeDuration.playAnim();
            shakePrice.playAnim();
        }
    }

    @FXML
    void closeTourPane(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}