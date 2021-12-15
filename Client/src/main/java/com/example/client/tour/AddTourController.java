package com.example.client.tour;

import com.example.client.MainController;
import com.example.model.animation.Shake;
import com.example.model.check.Check;
import com.example.model.connect.Connect;
import com.example.model.tour.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddTourController {

    private Connect connect = MainController.connect;

    @FXML
    private Button addTourPaneBtn;

    @FXML
    private Label flagTourAddLabel;

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
    private ComboBox<String> boxTourType;

    ObservableList<String> boxTourTypeObservableList = FXCollections.observableArrayList(
            "На море", "Экскурсия", "Горные лыжи", "Экскурсия с отдам на море",
            "Новый год", "Оздоровление", "Проезд", "Детский отдых");

    @FXML
    void addTourPane(ActionEvent event) {
        Tour tour = new Tour();
        String price = priceTourField.getText().trim();
        String duration = durationTourField.getText().trim();
        LocalDate dateTour = dateTourField.getValue();
        if (Check.isFloat(price) && Check.isNumber(duration) ) {
            tour.setPrice(Float.parseFloat(price));
            tour.setDuration(duration);
            tour.setTourDate(dateTour.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            tour.setTourName(tourNameField.getText().trim());
            tour.setTourType(boxTourType.getValue());
            tour.setCountryName(countryNameField.getText().trim());
            tour.setCityName(cityNameField.getText().trim());
            int a = 10;
            int b = 10000;
            int x = a + (int) (Math.random() * ((b - a) + 1));
            tour.setTourCode("T" + String.valueOf(x));
            try {
                connect.writeLine("add");
                connect.writeLine("addTour");
                connect.writeObj(tour);
                String flagAddTout = connect.readLine();
                if(flagAddTout.equals("true")){
                    flagTourAddLabel.setText("Тур успешно добавлен!");
                }else {
                    flagTourAddLabel.setText("Тур не добавлен!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @FXML
    void initialize() {
        boxTourType.setItems(boxTourTypeObservableList);
    }
}