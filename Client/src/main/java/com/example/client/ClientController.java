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
import com.example.model.order.Order;
import com.example.model.ticket.Ticket;
import com.example.model.tour.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    /**Main components*/
    @FXML
    private Label putTextULabel;
    @FXML
    private Button closeBtn;
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
    private Button toursBtn;
    @FXML
    private Button myOrderBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button myTicketBtn;


    /**Order*/
    @FXML
    private Label errorOrderClientLabel;
    @FXML
    private TextField cancelOrderClientIdField;
    @FXML
    private Button cancelOrderClientBtn;
    @FXML
    private Button viewOrderClientBtn;
    @FXML
    private TableView<Order> tabViewOrdersClient;


    /**Ticket*/
    @FXML
    private Button ticketClientViewBtn;
    @FXML
    private Button ticketViewBtn;
    @FXML
    private TableView<Ticket> ticketTableColumn;


    /**User*/
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

    /**Tour*/
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
    void initialize() {
        this.profile = MainController.client;

        closeBtn.setOnAction(ActionEvent -> {
            try {
                connect.writeLine("close");
                tabViewTours = null;
                ticketTableColumn = null;
                connect.close();
                connect = null;
                profile = null;
                System.exit(2);
            } catch (IOException e) {
                new MyException(e);
            }
        });

        toursBtn.setOnAction(ActionEvent -> {
            errorOrderTour.setText("");
            putTextULabel.setText("Туры");
            glvTabPane.getSelectionModel().select(u1);
        });

        myTicketBtn.setOnAction(ActionEvent -> {
            putTextULabel.setText("Мои Билеты");
            glvTabPane.getSelectionModel().select(u3);
        });

        myOrderBtn.setOnAction(ActionEvent -> {
            errorOrderClientLabel.setText("");
            cancelOrderClientIdField.setText("");
            putTextULabel.setText("Мои Заказы");
            glvTabPane.getSelectionModel().select(u4);
        });


        /**Профил пользователя*/
        profileBtn.setOnAction(ActionEvent -> {
            errorOrderTour.setText("");
            putTextULabel.setText("Профил");
            glvTabPane.getSelectionModel().select(u2);
            signUpFIOField.setText(profile.getFIO());
            signUpClientCodeField.setText(profile.getClientCode());
            signUpPassportIdField.setText(profile.getPassportId());
            signUpMailField.setText(profile.getMail());
            signUpMobileNumberField.setText(profile.getMobileNumber());
            signUpLoginField.setText(profile.getLogin());
            signUpPasswordField.setText(profile.getPassword());
        });

        /**Броноруем тур*/
        makeOrderTourBtn.setOnAction(ActionEvent -> {
            try {
                String inputTourCode = inputTourCodeMakeOrderField.getText().trim();
                if (Check.isString(inputTourCode)) {

                    Client client = new Client();
                    client.setClientCode(profile.getClientCode());
                    client.setId(profile.getId());
                    client.setFlag(profile.getFlag());
                    client.setPassword(profile.getPassword());
                    client.setLogin(profile.getLogin());
                    client.setMail(profile.getMail());
                    client.setFIO(profile.getFIO());
                    client.setPassportId(profile.getPassportId());

                    connect.writeLine("add");
                    connect.writeLine("orderTour");
                    connect.writeLine(inputTourCode);
                    connect.writeObj(client);
                    String flagOrderAddOrNot = connect.readLine();

                    if (flagOrderAddOrNot.equals("true")) {
                        errorOrderTour.setText("Тур забронирован");
                    } else if (flagOrderAddOrNot.equals("false")) {
                        errorOrderTour.setText("Тур не забронирован");
                    } else {
                        errorOrderTour.setText("ошибка в работе");
                    }
                    flagOrderAddOrNot = "";
                } else {
                    Shake shakeOrderTour = new Shake(inputTourCodeMakeOrderField);
                    shakeOrderTour.playAnim();
                    errorOrderTour.setText("Ведите код тура");
                }
            } catch (IOException e) {
                new MyException(e);
            } finally {
                inputTourCodeMakeOrderField.setText("");
            }
        });

        /**Билеты*/
        ticketClientViewBtn.setOnAction(ActionEvent -> {
            try {
                connect.writeLine("view");
                connect.writeLine("viewTicket");
                ArrayList<Ticket> ticketArrayList = (ArrayList<Ticket>) connect.readObjList().clone();
                ArrayList<Ticket> tickets = new ArrayList<>();

                String clientCode = profile.getClientCode();

                for (Ticket t : ticketArrayList) {
                    if (clientCode.equals(t.getUserCode())) {
                        tickets.add(t);
                    }
                }

                if (tickets.isEmpty()) {
                    ticketTableColumn.getItems().clear();
                } else {
                    ObservableList<Ticket> observableList = FXCollections.observableArrayList(tickets);
                    ticketTableColumn.setItems(observableList);
                    ticketTableColumn.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
                    ticketTableColumn.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("ticketCode"));
                    ticketTableColumn.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("userCode"));
                    ticketTableColumn.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("transportType"));
                    ticketTableColumn.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("departurePoint"));
                    ticketTableColumn.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("arrivalPoint"));
                    ticketTableColumn.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("departureData"));
                }
            } catch (IOException | ClassNotFoundException e) {
                new MyException(e);
            }

        });

        /**Туры*/
        tourViewBtn.setOnAction(ActionEvent -> {
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
        });

        /**Заказы*/
        viewOrderClientBtn.setOnAction(ActionEvent -> {
            try {
                connect.writeLine("view");
                connect.writeLine("viewOrder");
                connect.writeLine(profile.getClientCode());
                final String flagOrder = connect.readLine();
                if (flagOrder.equals("true")) {
                    ArrayList<Order> orders = (ArrayList<Order>) connect.readObjList().clone();
                    ObservableList<Order> orderObservableList = FXCollections.observableArrayList(orders);
                    tabViewOrdersClient.setItems(orderObservableList);
                    tabViewOrdersClient.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
                    tabViewOrdersClient.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("clientCode"));
                    tabViewOrdersClient.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("tourCode"));
                } else if (flagOrder.equals("false")) {
                    tabViewOrdersClient.getItems().clear();
                    errorOrderClientLabel.setText("У вас нет заказов");
                } else {
                    tabViewOrdersClient.getItems().clear();
                }
            } catch (IOException | ClassNotFoundException e) {
                new MyException(e);
            }
        });

        /**Отменить заказа*/
        cancelOrderClientBtn.setOnAction(ActionEvent -> {
            try {
                String cancelOrderId = cancelOrderClientIdField.getText().trim();
                if (Check.isNumber(cancelOrderId)) {
                    connect.writeLine("view");
                    connect.writeLine("viewOrder");
                    connect.writeLine(profile.getClientCode());
                    final String flagOrder = connect.readLine();
                    if (flagOrder.equals("true")) {

                        connect.readObjList();
                        connect.writeLine("delete");
                        connect.writeLine("deleteOrder");
                        connect.writeLine(cancelOrderId);
                        String msg = connect.readLine();

                        if (msg.equals("true")) {
                            errorOrderClientLabel.setText("Заказ успешно удалён");
                        } else {
                            cancelOrderClientIdField.setText("");
                            errorOrderClientLabel.setText("Заказ не удалён");
                        }
                    } else {
                        cancelOrderClientIdField.setText("");
                        errorOrderClientLabel.setText("У вас нету такого заказа");
                    }
                } else {
                    Shake shakeOrderId = new Shake(cancelOrderClientIdField);
                    shakeOrderId.playAnim();
                    cancelOrderClientIdField.setText("");
                    errorOrderClientLabel.setText("Введите id заказа ");
                }
            } catch (IOException e) {
                new MyException(e);
            } catch (ClassNotFoundException e) {
                new MyException(e);
            } finally {
                cancelOrderClientIdField.setText("");
            }
        });
    }
}