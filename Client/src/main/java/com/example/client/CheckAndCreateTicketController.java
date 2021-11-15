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

/**
 * @author Ataeyv Ismayyl
 *
 * it is class create Ticket get user, tour code and create ticket.
 * */

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
            String idOrder = idOrderField.getText().trim();
            String transportType = transportTypeField.getText().trim();
            String departurePoint = departurePointField.getText().trim();

            if (Check.isString(transportType) && Check.isString(departurePoint)) {
                if (Check.isNumber(idOrder)) {

                    Ticket ticket = new Ticket();
                    ticket.setTransportType(transportType);
                    ticket.setDeparturePoint(departurePoint);
                    ticket.setTicketCode("B" + String.valueOf(Rand.random(10, 10000)));

                    connect.writeLine("add");
                    connect.writeLine("addTicket");
                    connect.writeLine(idOrder);
                    connect.writeObj(ticket);
                    String flagAddTicketAndCheckOrder = connect.readLine();

                    System.out.println(flagAddTicketAndCheckOrder);

                    if (flagAddTicketAndCheckOrder.equals("CreateTicket")) {
                        errorIdOrderLabel.setText("Билет создан");

                    } else if (flagAddTicketAndCheckOrder.equals("NoCreateTicket")) {
                        errorIdOrderLabel.setText("Билет не создан обратитесь к администрации ");
                    } else if (flagAddTicketAndCheckOrder.equals("NoIdOrder")) {
                        errorIdOrderLabel.setText("Нету такого id введите правильные значение");

                    } else if (flagAddTicketAndCheckOrder.equals("NoOrder")) {
                        errorIdOrderLabel.setText("Нету заказов для создание билета");

                    } else if (flagAddTicketAndCheckOrder.equals("false")) {
                        errorIdOrderLabel.setText("Ошибка обратитесь к администрации");
                    } else {
                        errorIdOrderLabel.setText("Фатальная Ошибка обратитесь к администрации");
                    }

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
        //errorIdOrderLabel.setText("");
    }
}