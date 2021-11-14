package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import com.example.model.check.Check;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import com.example.model.rand.Rand;
import com.example.model.ticket.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckAndCreateTicketController {

    private Connect connect = MainController.connect;

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
        try {
            LocalDate dateTour = departureDataFiled.getValue();
            String idOrder = idOrderField.getText().trim();
            String transportType = transportTypeField.getText().trim();
            String departurePoint = departurePointField.getText().trim();
            String arrivalPoint = arrivalPointField.getText().trim();

            if (Check.isString(dateTour.toString()) && Check.isString(transportType)
                    && Check.isString(departurePoint) && Check.isString(arrivalPoint)) {

                if (Check.isNumber(idOrder)) {
                    Ticket ticket = new Ticket();
                    ticket.setTransportType(transportType);
                    ticket.setDeparturePoint(departurePoint);
                    ticket.setArrivalPoint(arrivalPoint);
                    ticket.setTicketCode( "B" + String.valueOf(Rand.random(10,10000)));
                    ticket.setDepartureData(dateTour.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
                    //ticket.setUserCode();
                    connect.writeLine("add");
                    connect.writeLine("addTicket");
                    connect.writeLine(idOrder);
                    connect.writeObj(ticket);


                } else {
                    errorIdOrderLabel.setText("Введите число в поле id заказов!!!");
                }
            } else {
                errorIdOrderLabel.setText("Запольните поля!!!");
            }


        } catch (IOException e) {
            new MyException(e);
        }
    }

    @FXML
    void initialize() {

    }
}