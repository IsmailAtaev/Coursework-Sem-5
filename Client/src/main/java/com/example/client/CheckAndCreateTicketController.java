package com.example.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckAndCreateTicketController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createTicketBtn;

    @FXML
    private TextField idOrderField;
    @FXML
    private TextField transportTypeField;
    @FXML
    private TextField departurePointField;
    @FXML
    private TextField arrivalPointField;

    @FXML
    private DatePicker departureDataFiled;

    @FXML
    private Label errorIdOrderLabel;




    @FXML
    void closeCreateTicketPane(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void createTicket(ActionEvent event) {






        ///
    }

    @FXML
    void initialize() {

    }
}
