package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.example.model.animation.Shake;
import com.example.model.check.Check;
import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import com.example.model.ticket.Ticket;
import com.example.model.tour.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientController {

    private Connect connect = MainController.connect;
    private Client profile = new Client();

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private TabPane glvTabPane;
    @FXML
    private Tab u1;
    @FXML
    private Tab u2;
    @FXML
    private Tab u3;
    @FXML
    private Tab u4;


    @FXML
    private TabPane secondTabPane;
    @FXML
    private Tab uu1;
    @FXML
    private Tab uu2;


    @FXML
    private Button toursBtn;
    @FXML
    private Button myOrderBtn;
    @FXML
    private Button profileBtn;



    /**
     *Ticket
     * */
    @FXML
    private Button ticketClientViewBtn;
    @FXML
    private Button ticketViewBtn;
    @FXML
    private TableView<Ticket> ticketTableColumn;
    @FXML
    private TableColumn<Ticket, Integer> idTicketTableColumn;
    @FXML
    private TableColumn<Ticket, String> ticketCodeTicketTableColumn;
    @FXML
    private TableColumn<Ticket, String> clientCodeTicketTableColumn;
    @FXML
    private TableColumn<Ticket, String> typeTransportTicketTableColumn;
    @FXML
    private TableColumn<Ticket, String> departurePointTicketTableColumn;
    @FXML
    private TableColumn<Ticket, String> arrivalPointTicketTableColumn;
    @FXML
    private TableColumn<Ticket, String> departureDateTicketTableColumn;



    /**
     * User
     */
    @FXML
    private TextField signUpFIOField;
    @FXML
    private TextField signUpClientCodeField;
    @FXML
    private TextField signUpPassportIdField;
    @FXML
    private TextField signUpMailField;
    @FXML
    private TextField signUpMobileNumberField;
    @FXML
    private TextField signUpLoginField;
    @FXML
    private TextField signUpPasswordField;

    /**
     * Tour
     */

    @FXML
    private Label errorOrderTour;
    @FXML
    private TextField inputTourCodeMakeOrderField;
    @FXML
    private Button makeOrderTourBtn;
    @FXML
    private Button tourViewBtn;
    @FXML
    private TableView<Tour> tabViewTours;
    @FXML
    private TableColumn<Tour, String> countryTourTabColumn;
    @FXML
    private TableColumn<Tour, String> cityTourTabColumn;
    @FXML
    private TableColumn<Tour, Float> priceTourTabColumn;
    @FXML
    private TableColumn<Tour, String> durationTourTabColumn;
    @FXML
    private TableColumn<Tour, String> tourCodeTabColumn;
    @FXML
    private TableColumn<Tour, String> dateTourTabColumn;
    @FXML
    private TableColumn<Tour, String> nameTourTabColumn;
    @FXML
    private TableColumn<Tour, String> typeTourTabColumn;


    @FXML
    void initialize() {
        this.profile = MainController.client;
        System.out.println(profile.toString());

        toursBtn.setOnAction(ActionEvent -> {
            errorOrderTour.setText("");
            glvTabPane.getSelectionModel().select(u1);
        });

        /**
         * Профил пользователя
         * */
        profileBtn.setOnAction(ActionEvent -> {
            errorOrderTour.setText("");
            glvTabPane.getSelectionModel().select(u2);

            signUpFIOField.setText(profile.getFIO());
            signUpClientCodeField.setText(profile.getClientCode());
            signUpPassportIdField.setText(profile.getPassportId());
            signUpMailField.setText(profile.getMail());
            signUpMobileNumberField.setText(profile.getMobileNumber());
            signUpLoginField.setText(profile.getLogin());
            signUpPasswordField.setText(profile.getPassword());
        });

        /**
         * Броноруем тур
         * @throws IOException
         * */
        makeOrderTourBtn.setOnAction(ActionEvent -> {
            try {
                errorOrderTour.setText("");
                String inputTourCode = inputTourCodeMakeOrderField.getText().trim();
                if (Check.isString(inputTourCode)) {
                    connect.writeLine("add");
                    connect.writeLine("orderTour");
                    connect.writeLine(inputTourCode);
                    connect.writeObj(profile);
                    String flagOrderAddOrNot = connect.readLine();
                    System.out.println(flagOrderAddOrNot + " i am flag order");
                    if (flagOrderAddOrNot.equals("true")) {
                        errorOrderTour.setText("Тур забронирован");
                    } else if (flagOrderAddOrNot.equals("false")) {
                        errorOrderTour.setText("Тур не забронирован");
                    } else {
                        errorOrderTour.setText("ошибка в работе");
                    }
                } else {
                    Shake shakeOrderTour = new Shake(inputTourCodeMakeOrderField);
                    shakeOrderTour.playAnim();
                    errorOrderTour.setText("ведите код тура");
                }
            } catch (IOException e) {
                new MyException(e);
            }
            inputTourCodeMakeOrderField.setText("");
        });

        ticketClientViewBtn.setOnAction(ActionEvent -> {
            try {
                connect.writeLine("view");
                connect.writeLine("viewTicket");
                connect.writeObj(profile);
                String msg = connect.readLine();
                if(msg.equals("true")){
                    ArrayList<Ticket> ticketArrayList = (ArrayList<Ticket>) connect.readObjList().clone();
                    ObservableList<Ticket> observableList = FXCollections.observableArrayList(ticketArrayList);
                    ticketTableColumn.setItems(observableList);
                    ticketTableColumn.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
                    ticketTableColumn.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("ticketCode"));
                    ticketTableColumn.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("userCode"));
                    ticketTableColumn.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("transportType"));
                    ticketTableColumn.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("departurePoint"));
                    ticketTableColumn.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("arrivalPoint"));
                    ticketTableColumn.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("departureData"));
                } else {
                    System.out.println("do not ticket your profile");
                }

            } catch (IOException | ClassNotFoundException e) {
                new MyException(e);
            }

        });


    }


    @FXML
    void tourViewMethod(ActionEvent event) {
        try {
            connect.writeLine("view");
            connect.writeLine("viewTour");
            ArrayList<Tour> tourArrayList = (ArrayList<Tour>) connect.readObjList().clone();
            ObservableList<Tour> observableList = FXCollections.observableArrayList(tourArrayList);
            tabViewTours.setItems(observableList);
            tabViewTours.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("countryName"));
            tabViewTours.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("cityName"));
            tabViewTours.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("price"));
            tabViewTours.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("duration"));
            tabViewTours.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("tourCode"));
            tabViewTours.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("tourDate"));
            tabViewTours.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("tourName"));
            tabViewTours.getColumns().get(7).setCellValueFactory(new PropertyValueFactory("tourType"));
        } catch (IOException | ClassNotFoundException e) {
            new MyException(e);
        }
    }

    @FXML
    void myTicket(ActionEvent event) {
        glvTabPane.getSelectionModel().select(u3);
    }

    @FXML
    void myOrder(ActionEvent event) {
        glvTabPane.getSelectionModel().select(u4);
    }
}