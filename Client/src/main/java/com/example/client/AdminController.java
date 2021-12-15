package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.example.model.animation.Shake;
import com.example.model.check.Check;
import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.dialog.InputDialog;
import com.example.model.myexception.MyException;
import com.example.model.order.Order;
import com.example.model.ticket.Ticket;
import com.example.model.tour.Tour;
import eu.hansolo.tilesfx.chart.SunburstChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**@author Ataeyv I.M. (ataewisma@gmail.com)*/

public class AdminController {

    private Client clientSearch;
    private String flagSearchClient = "false";
    private Connect connect = MainController.connect;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private TabPane glavnyPane;
    @FXML
    private Label putText4;
    @FXML
    private Tab u1;
    @FXML
    private Tab u2;
    @FXML
    private Tab u3;
    @FXML
    private Tab u4;
    @FXML
    private Button closeBtn;

    /**
     * Main components
     */
    @FXML
    private Button usersBtn;
    @FXML
    private Button toursBtn;
    @FXML
    private Button ticketsBtn;
    @FXML
    private Button viewUsersBtn;
    @FXML
    private Button deleteUsersBtn;
    @FXML
    private Button addUserBtn;

    /**
     * Ticket
     */
    @FXML
    private Label errorTicketDeleteLabel;
    @FXML
    private TextField ticketIdDeleteField;
    @FXML
    private Button ticketDeleteBtn;
    @FXML
    private Button ticketViewBtn;
    @FXML
    private TableView<Ticket> ticketTableColumn;

    /**
     * Order
     */
    @FXML
    private Label errorOrderPane;
    @FXML
    private TextField searchClientCodeField;
    @FXML
    private TextField searchTourCodeField;
    @FXML
    private Button searchOrderClientCodeBtn;
    @FXML
    private Button searchOrderTourCodeBtn;
    @FXML
    private TableView<Order> tabViewOrdersSearch;
    @FXML
    private TableView<Order> tabViewOrders;
    private ArrayList<Order> orderArrayList = new ArrayList<>();


    /**Tour*/
    @FXML
    private Button searchTourIdBtn;
    @FXML
    private Button editTourBtn;
    @FXML
    private Label errorMsgEditTourLabel;
    @FXML
    private TextField searchIdTourEditFiled;

    /**Edit*/
    @FXML
    private TextField idTourEditFiled;
    @FXML
    private TextField countryTourEditField;
    @FXML
    private TextField cityTourEditField;
    @FXML
    private TextField priceTourEditField;
    @FXML
    private TextField durationTourEditField;
    @FXML
    private TextField codeTourEditField;
    @FXML
    private TextField nameTourEditField;
    @FXML
    private TextField typeTourEditField;
    @FXML
    private DatePicker dateTourEditField;



    @FXML
    private Button orderBtn;
    @FXML
    private Label errorTourDeleteId;
    @FXML
    private TextField deleteIdTourField;
    @FXML
    private Button viewOrderBtn;
    @FXML
    private Button viewToursBtn;
    @FXML
    private Button deleteTourBtn;
    @FXML
    private Button addTourBtn;
    @FXML
    private TableView<Tour> tabViewTours;

    /**Client and User*/
    @FXML
    private TableView<Client> usersTableView;
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
    @FXML
    private TextField signUpFlagField;
    @FXML
    private TextField loginDeleteField;
    @FXML
    private TextField passwordDeleteField;
    @FXML
    private TextField clientCodeDeleteField;

    @FXML
    private Label outPutErrorAddUserLabel;
    @FXML
    private Label errorDeleteUserLabel;
    @FXML
    private Label errorSearchLabel;
    @FXML
    private Label errorEditLabel;

    /**Edit table*/
    @FXML
    private TextField signUpSearchFIOField;
    @FXML
    private TextField signUpSearchLoginField;
    @FXML
    private Button searchBtn;
    @FXML
    private TextField signUpSearchPasswordField;
    @FXML
    private TextField signUpEditFlagField;
    @FXML
    private TextField signUpEditPasswordField;
    @FXML
    private TextField signUpEditMailField;
    @FXML
    private TextField signUpEditFIOField;
    @FXML
    private TextField signUpEditClientCodeField;
    @FXML
    private TextField signUpEditPassportIdField;
    @FXML
    private TextField signUpEditMobileNumberField;
    @FXML
    private TextField signUpEditLoginField;
    @FXML
    private Button editBtn;

    private ArrayList<Tour> tourArrayList = new ArrayList<>();




    @FXML
    void initialize() {

        closeBtn.setOnAction(ActionEvent -> {
            try {
                connect.writeLine("close");
                connect.close();
                System.exit(1);
            } catch (IOException e) {
                new MyException(e);
            }
        });

        usersBtn.setOnAction(actionEvent -> {
            putText4.setText("Пользователи");
            glavnyPane.getSelectionModel().select(u1);
        });

        toursBtn.setOnAction(actionEvent -> {
            putText4.setText("Туры");
            //errorTourDeleteId.setText("");
            glavnyPane.getSelectionModel().select(u2);
        });

        ticketsBtn.setOnAction(actionEvent -> {
            putText4.setText("Билеты");
            glavnyPane.getSelectionModel().select(u3);
        });

        orderBtn.setOnAction(ActionEvent -> {
            errorOrderPane.setText("");
            searchTourCodeField.setText("");
            searchClientCodeField.setText("");
            putText4.setText("Заказы");
            glavnyPane.getSelectionModel().select(u4);
        });

        /**Просмотр пользователя*/
        viewUsersBtn.setOnAction(actionEvent -> {
            try {
                connect.writeLine("view");
                connect.writeLine("viewUser");
                ArrayList<Client> clientArrayList = (ArrayList<Client>) connect.readObjList().clone();
                ObservableList<Client> observableList = FXCollections.observableArrayList(clientArrayList);
                usersTableView.setItems(observableList);
                usersTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("FIO"));
                usersTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("clientCode"));
                usersTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("passportId"));
                usersTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("mail"));
                usersTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("mobileNumber"));
                usersTableView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("login"));
                usersTableView.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("password"));
                usersTableView.getColumns().get(7).setCellValueFactory(new PropertyValueFactory("flag"));
            } catch (IOException | ClassNotFoundException e) {
                new MyException(e);
            }
        });

        /**Добавить пользователя*/
        addUserBtn.setOnAction(actionEvent -> {
            String fio = signUpFIOField.getText().trim();
            String clientCode = signUpClientCodeField.getText().trim();
            String passportId = signUpPassportIdField.getText().trim();
            String mail = signUpMailField.getText().trim();
            String mobileNumber = signUpMobileNumberField.getText().trim();
            String login = signUpLoginField.getText().trim();
            String password = signUpPasswordField.getText().trim();
            String flag = signUpFlagField.getText().trim();

            if (flag.equals("1") || flag.equals("2") && Check.isNumber(mobileNumber)
                    && Check.isString(fio) && Check.isString(clientCode) && Check.isString(passportId)
                    && Check.isString(mail) && Check.isString(login) && Check.isString(password)) {

                signUpFlagField.setText("");
                signUpFIOField.setText("");
                signUpMobileNumberField.setText("");
                signUpClientCodeField.setText("");
                signUpPassportIdField.setText("");
                signUpMailField.setText("");
                signUpLoginField.setText("");
                signUpPasswordField.setText("");

                Client client = new Client();
                client.setFIO(fio);
                client.setClientCode(clientCode);
                client.setPassportId(passportId);
                client.setMail(mail);
                client.setMobileNumber(mobileNumber);
                client.setLogin(login);
                client.setPassword(password);
                client.setFlag(Integer.parseInt(flag));

                try {
                    connect.writeLine("add");
                    connect.writeLine("addUser");
                    connect.writeObj(client);
                    String flagAddClient = connect.readLine();

                    if (flagAddClient.equals("true")) {
                        outPutErrorAddUserLabel.setText("Ползователь добавлен в базу");
                    } else if (flagAddClient.equals("false")) {
                        outPutErrorAddUserLabel.setText("Ползователь не добавлен");
                    } else {
                        outPutErrorAddUserLabel.setText(" ошибка дабавления обратитесь к администрации ");
                    }
                    outPutErrorAddUserLabel.setText("");

                } catch (IOException e) {
                    new MyException(e);
                }

            } else {
                Shake shakeFIO = new Shake(signUpFIOField);
                Shake shakeClientCode = new Shake(signUpClientCodeField);
                Shake shakeMobileNumber = new Shake(signUpMobileNumberField);
                Shake shakePassword = new Shake(signUpPasswordField);
                Shake shakeMail = new Shake(signUpMailField);
                Shake shakeLogin = new Shake(signUpLoginField);
                Shake shakePassportId = new Shake(signUpPassportIdField);
                Shake shakeFlag = new Shake(signUpFlagField);

                shakeFlag.playAnim();
                shakeClientCode.playAnim();
                shakeFIO.playAnim();
                shakeLogin.playAnim();
                shakeMail.playAnim();
                shakeMobileNumber.playAnim();
                shakePassportId.playAnim();
                shakePassword.playAnim();
                outPutErrorAddUserLabel.setText("неправелный ввод данныз проверти поля");
            }
        });

        /**Удалить пользователя*/
        deleteUsersBtn.setOnAction(actionEvent -> {
            try {
                String loginDelete = loginDeleteField.getText().trim();
                String passwordDelete = passwordDeleteField.getText().trim();
                String clientCodeDelete = clientCodeDeleteField.getText().trim();

                Shake shakeLogin = new Shake(loginDeleteField);
                Shake shakePassword = new Shake(passwordDeleteField);
                Shake shakeClientCode = new Shake(clientCodeDeleteField);

                if (Check.isString(loginDelete) && Check.isString(passwordDelete) && Check.isString(clientCodeDelete)) {

                    connect.writeLine("delete");
                    connect.writeLine("deleteUser");
                    connect.writeLine(loginDelete);
                    connect.writeLine(passwordDelete);
                    connect.writeLine(clientCodeDelete);
                    String flagDelete = connect.readLine();

                    if (flagDelete.equals("true")) {
                        loginDeleteField.setText("");
                        passwordDeleteField.setText("");
                        clientCodeDeleteField.setText("");
                        errorDeleteUserLabel.setText("пользователя успешно удален !!!");
                    } else {
                        shakeClientCode.playAnim();
                        shakeLogin.playAnim();
                        shakePassword.playAnim();
                        errorDeleteUserLabel.setText(" нету такого пользователя!!!");
                    }

                } else {
                    shakeClientCode.playAnim();
                    shakeLogin.playAnim();
                    shakePassword.playAnim();
                    errorDeleteUserLabel.setText(" введите соответствуюшие данные !!!");
                }
                errorDeleteUserLabel.setText("");
            } catch (Exception e) {
                new MyException(e);
            }
        });

        /**Поиск пользователя*/
        searchBtn.setOnAction(actionEvent -> {
            try {
                errorSearchLabel.setText("");

                String searchFio = signUpSearchFIOField.getText().trim();
                String searchLogin = signUpSearchLoginField.getText().trim();
                String searchPassword = signUpSearchPasswordField.getText().trim();

                Shake shakeSearchFio = new Shake(signUpSearchFIOField);
                Shake shakeSearchLogin = new Shake(signUpSearchLoginField);
                Shake shakeSearchPassword = new Shake(signUpSearchPasswordField);

                if (Check.isString(searchFio) && Check.isString(searchLogin) && Check.isString(searchPassword)) {
                    connect.writeLine("search");
                    connect.writeLine("searchUser");
                    connect.writeLine(searchFio);
                    connect.writeLine(searchLogin);
                    connect.writeLine(searchPassword);
                    String flagSearchUser = connect.readLine();
                    this.flagSearchClient = flagSearchUser;
                    Client client;

                    if (flagSearchUser.equals("true")) {
                        this.clientSearch = null;
                        client = (Client) connect.readObj();
                        this.clientSearch = client;
                        if (client != null) {
                            signUpEditFIOField.setText(client.getFIO());
                            signUpEditClientCodeField.setText(client.getClientCode());
                            signUpEditPassportIdField.setText(client.getPassportId());
                            signUpEditMailField.setText(client.getMail());
                            signUpEditPasswordField.setText(client.getPassword());
                            signUpEditLoginField.setText(client.getLogin());
                            signUpEditMobileNumberField.setText(client.getMobileNumber());
                            signUpEditFlagField.setText(String.valueOf(client.getFlag()));
                        }
                    } else if (flagSearchUser.equals("false")) {
                        signUpEditFIOField.setText("");
                        signUpEditClientCodeField.setText("");
                        signUpEditPassportIdField.setText("");
                        signUpEditMailField.setText("");
                        signUpEditPasswordField.setText("");
                        signUpEditLoginField.setText("");
                        signUpEditMobileNumberField.setText("");
                        signUpEditFlagField.setText("");
                        errorSearchLabel.setText("нету такого пользователя");
                    } else {
                        errorSearchLabel.setText("проблема с сервером обратитесь к администрации");
                    }
                } else {
                    signUpEditFIOField.setText("");
                    signUpEditClientCodeField.setText("");
                    signUpEditPassportIdField.setText("");
                    signUpEditMailField.setText("");
                    signUpEditPasswordField.setText("");
                    signUpEditLoginField.setText("");
                    signUpEditMobileNumberField.setText("");
                    signUpEditFlagField.setText("");
                    shakeSearchFio.playAnim();
                    shakeSearchLogin.playAnim();
                    shakeSearchPassword.playAnim();
                    errorSearchLabel.setText("заполните пустые поля для поиска!!!");
                }
            } catch (Exception e) {
                new MyException(e);
            }
            signUpSearchFIOField.setText("");
            signUpSearchLoginField.setText("");
            signUpSearchPasswordField.setText("");
        });

        /**Изменение пользователя*/
        editBtn.setOnAction(actionEvent -> {
            Shake shakeFIO = new Shake(signUpEditFIOField);
            Shake shakeClientCode = new Shake(signUpEditClientCodeField);
            Shake shakeMobileNumber = new Shake(signUpEditMobileNumberField);
            Shake shakePassword = new Shake(signUpEditPasswordField);
            Shake shakeMail = new Shake(signUpEditMailField);
            Shake shakeLogin = new Shake(signUpEditLoginField);
            Shake shakePassportId = new Shake(signUpEditPassportIdField);
            Shake shakeFlag = new Shake(signUpEditFlagField);

            Client client = new Client();
            String fio = signUpEditFIOField.getText().trim();
            String clientCode = signUpEditClientCodeField.getText().trim();
            String passportId = signUpEditPassportIdField.getText().trim();
            String mail = signUpEditMailField.getText().trim();
            String pass = signUpEditPasswordField.getText().trim();
            String login = signUpEditLoginField.getText().trim();
            String mobileNumber = signUpEditMobileNumberField.getText().trim();
            String flag = signUpEditFlagField.getText().trim();

            if (this.flagSearchClient.equals("true") && clientSearch != null
                    && Check.isString(fio) && Check.isString(clientCode)
                    && Check.isString(passportId) && Check.isString(mail)
                    && Check.isString(pass) && Check.isString(login)
                    && Check.isString(mobileNumber) && Check.isNumber(flag)) {
                client.setId(this.clientSearch.getId());
                client.setFIO(fio);
                client.setClientCode(clientCode);
                client.setPassportId(passportId);
                client.setMail(mail);
                client.setPassword(pass);
                client.setLogin(login);
                client.setMobileNumber(mobileNumber);
                client.setFlag(Integer.parseInt(flag));
                try {
                    connect.writeLine("edit");
                    connect.writeLine("editUser");
                    connect.writeObj(client);
                    String flagEdit = connect.readLine();
                    System.out.println(flagEdit + " i am flagEdit ");
                    if (flagEdit.equals("true")) {
                        signUpEditFIOField.setText("");
                        signUpEditClientCodeField.setText("");
                        signUpEditPassportIdField.setText("");
                        signUpEditMailField.setText("");
                        signUpEditPasswordField.setText("");
                        signUpEditLoginField.setText("");
                        signUpEditMobileNumberField.setText("");
                        signUpEditFlagField.setText("");
                        this.errorEditLabel.setText("пользователь успешно обнавлён.");
                    } else {
                        shakeFIO.playAnim();
                        shakeClientCode.playAnim();
                        shakeMobileNumber.playAnim();
                        shakePassword.playAnim();
                        shakeMail.playAnim();
                        shakeLogin.playAnim();
                        shakePassportId.playAnim();
                        shakeFlag.playAnim();
                        this.errorEditLabel.setText("пользователь не изменён ошибка на сервере ");
                    }
                } catch (IOException e) {
                    new MyException(e);
                }
            } else {
                shakeFIO.playAnim();
                shakeClientCode.playAnim();
                shakeMobileNumber.playAnim();
                shakePassword.playAnim();
                shakeMail.playAnim();
                shakeLogin.playAnim();
                shakePassportId.playAnim();
                shakeFlag.playAnim();
            }
            this.errorEditLabel.setText("");
        });

        /**Просмотр билетов*/
        ticketViewBtn.setOnAction(actionEvent -> {
            try {
                connect.writeLine("view");
                connect.writeLine("viewTicket");
                ArrayList<Ticket> ticketAdminArrayList = (ArrayList<Ticket>) connect.readObjList().clone();
                ObservableList<Ticket> observableList = FXCollections.observableArrayList(ticketAdminArrayList);
                ticketTableColumn.setItems(observableList);
                ticketTableColumn.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
                ticketTableColumn.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("ticketCode"));
                ticketTableColumn.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("userCode"));
                ticketTableColumn.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("transportType"));
                ticketTableColumn.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("departurePoint"));
                ticketTableColumn.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("arrivalPoint"));
                ticketTableColumn.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("departureData"));
            } catch (IOException | ClassNotFoundException e) {
                new MyException(e);
            }
        });

        /**Удалить билет*/
        ticketDeleteBtn.setOnAction(ActionEvent -> {
            try {
                String idTicket = ticketIdDeleteField.getText().trim();
                if (Check.isNumber(idTicket)) {
                    connect.writeLine("delete");
                    connect.writeLine("ticketDelete");
                    connect.writeLine(idTicket);
                    final String flag = connect.readLine();
                    if (flag.equals("true")) {
                        errorTicketDeleteLabel.setText("БИЛЕТ УСПЕШНО УДАЛЁН");
                    } else if (flag.equals("false")) {
                        errorTicketDeleteLabel.setText("НЕТУ ТАКОГО БИЛЕТ С ТАКИМ ID");
                    }
                } else {
                    Shake shake = new Shake(ticketIdDeleteField);
                    shake.playAnim();
                    errorTicketDeleteLabel.setText("ВВЕДИТЕ ЧИСЛО");
                }
            } catch (IOException e) {
                new MyException(e);
            } finally {
                ticketIdDeleteField.setText("");
            }
        });

        /**Поиск заказов по клиенту*/
        searchOrderClientCodeBtn.setOnAction(ActionEvent -> {
            try {
                String clientCode = searchClientCodeField.getText().trim();
                if (Check.isString(clientCode)) {

                    connect.writeLine("view");
                    connect.writeLine("viewOrder");
                    ArrayList<Order> orderArrayList = (ArrayList<Order>) connect.readObjList().clone();
                    ArrayList<Order> orders = new ArrayList<>();

                    for (Order o : orderArrayList) {
                        if (clientCode.equals(o.getClientCode())) {
                            orders.add(o);
                        }
                    }

                    if (orders.isEmpty()) {
                        errorOrderPane.setText("Нету стаким заказом клиент");
                        tabViewOrdersSearch.getItems().clear();
                    } else {
                        ObservableList<Order> observableList = FXCollections.observableArrayList(orders);
                        tabViewOrdersSearch.setItems(observableList);
                        tabViewOrdersSearch.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
                        tabViewOrdersSearch.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("clientCode"));
                        tabViewOrdersSearch.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("tourCode"));
                        errorOrderPane.setText("");
                    }
                } else {
                    Shake shakeSearch = new Shake(searchClientCodeField);
                    shakeSearch.playAnim();
                    errorOrderPane.setText("Введите код клиента");
                    tabViewOrdersSearch.getItems().clear();
                }
            } catch (IOException | ClassNotFoundException e) {
                new MyException(e);
            } finally {
                searchTourCodeField.setText("");
                searchClientCodeField.setText("");
            }
        });

        /**Поиск заказов по туру*/
        searchOrderTourCodeBtn.setOnAction(ActionEvent -> {
            try {
                String tourCode = searchTourCodeField.getText().trim();
                if (Check.isString(tourCode)) {

                    connect.writeLine("view");
                    connect.writeLine("viewOrder");
                    ArrayList<Order> orderArrayList = (ArrayList<Order>) connect.readObjList().clone();
                    ArrayList<Order> orders = new ArrayList<>();

                    for (Order o : orderArrayList) {
                        if (tourCode.equals(o.getTourCode())) {
                            orders.add(o);
                        }
                    }

                    if (orders.isEmpty()) {
                        errorOrderPane.setText("Нету стаким заказом тура");
                        tabViewOrdersSearch.getItems().clear();
                    } else {
                        ObservableList<Order> observableList = FXCollections.observableArrayList(orders);
                        tabViewOrdersSearch.setItems(observableList);
                        tabViewOrdersSearch.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
                        tabViewOrdersSearch.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("clientCode"));
                        tabViewOrdersSearch.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("tourCode"));
                        searchTourCodeField.setText("");
                    }
                } else {
                    Shake shakeSearch = new Shake(searchTourCodeField);
                    shakeSearch.playAnim();
                    errorOrderPane.setText("Введите код тура");
                    tabViewOrdersSearch.getItems().clear();
                }
            } catch (IOException | ClassNotFoundException e) {
                new MyException(e);
            } finally {
                searchTourCodeField.setText("");
                searchClientCodeField.setText("");
            }
        });

        /**Поиск тура*/
        searchTourIdBtn.setOnAction(ActionEvent -> {
            try {
                String id = searchIdTourEditFiled.getText().trim();
                if (Check.isNumber(id)) {
                    int idTour = Integer.parseInt(id);
                    connect.writeLine("view");
                    connect.writeLine("viewTour");
                    ArrayList<Tour> tours = (ArrayList<Tour>) connect.readObjList().clone();
                    int i = 0;

                    for (Tour t : tours) {
                        if (idTour == t.getId()) {
                            ++i;
                            idTourEditFiled.setText(String.valueOf(t.getId()));
                            countryTourEditField.setText(t.getCountryName());
                            cityTourEditField.setText(t.getCityName());
                            priceTourEditField.setText(String.valueOf(t.getPrice()));
                            durationTourEditField.setText(t.getDuration());
                            codeTourEditField.setText(t.getTourCode());
                            nameTourEditField.setText(t.getTourName());
                            typeTourEditField.setText(t.getTourType());
                        }
                    }
                    if (i == 0) {
                        errorMsgEditTourLabel.setText("Нету тура с таким Id");
                    }
                } else {
                    Shake shake = new Shake(searchIdTourEditFiled);
                    shake.playAnim();
                    errorMsgEditTourLabel.setText("Введите Id тура");
                }
            } catch (IOException | ClassNotFoundException e) {
                new MyException(e);
            } finally {
                searchIdTourEditFiled.setText("");
            }
        });

        /**Изменение тура*/
        editTourBtn.setOnAction(ActionEvent -> {
            try {
                String idTour = idTourEditFiled.getText().trim();
                String countryTour = countryTourEditField.getText().trim();
                String cityTour = cityTourEditField.getText().trim();
                String priceTour = priceTourEditField.getText().trim();
                String durationTour = durationTourEditField.getText().trim();
                String codeTour = codeTourEditField.getText().trim();
                String nameTour = nameTourEditField.getText().trim();
                String typeTour = typeTourEditField.getText().trim();
                LocalDate localDate = dateTourEditField.getValue();
                dateTourEditField.setAccessibleText("hi ella");

                if (Check.isNumber(idTour) && Check.isNumber(durationTour) && Check.isFloat(priceTour) && Check.isString(countryTour)
                        && Check.isString(cityTour) && Check.isString(codeTour) && Check.isString(nameTour) && Check.isString(typeTour) && Check.isString(localDate.toString())) {

                    Tour t = new Tour();
                    t.setId(Integer.parseInt(idTour));
                    t.setCountryName(countryTour);
                    t.setCityName(cityTour);
                    t.setPrice(Float.parseFloat(priceTour));
                    t.setDuration(durationTour);
                    t.setTourCode(codeTour);
                    t.setTourName(nameTour);
                    t.setTourType(typeTour);
                    t.setTourDate(localDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));

                    connect.writeLine("edit");
                    connect.writeLine("editTour");
                    connect.writeObj(t);
                    String flag = connect.readLine();

                    if (flag.equals("true")) {
                        errorMsgEditTourLabel.setText("Тур успешно редактировано");
                    } else {
                        errorMsgEditTourLabel.setText("Тур не редактировано");
                    }

                } else {
                    errorMsgEditTourLabel.setText("Нажмите кнопку поиск");
                }
            } catch (IOException e) {
                new MyException(e);
            } finally {
                idTourEditFiled.setText("");
                countryTourEditField.setText("");
                cityTourEditField.setText("");
                priceTourEditField.setText("");
                durationTourEditField.setText("");
                codeTourEditField.setText("");
                nameTourEditField.setText("");
                typeTourEditField.setText("s");
            }
        });


    }

    /**Удаление тура*/
    @FXML
    void deleteTour(ActionEvent event) {
        try {
            String flagDeleteTourServer = null;
            String id = deleteIdTourField.getText().trim();
            if (Check.isNumber(id)) {
                connect.writeLine("delete");
                connect.writeLine("deleteTour");
                connect.writeLine(id);
                flagDeleteTourServer = connect.readLine();

                if (flagDeleteTourServer.equals("true")) {
                    errorTourDeleteId.setText("Тур успешно удален");
                } else {
                    errorTourDeleteId.setText("Тур не удалён");
                    System.out.println("Тур не удалён");
                }
            } else {
                Shake shakeTourDelete = new Shake(deleteIdTourField);
                shakeTourDelete.playAnim();
               // errorTourDeleteId.setText("");
                System.out.println("Введите число");
            }
        } catch (IOException e) {
            new MyException(e);
        } finally {
            deleteIdTourField.setText("");
        }
    }

    /**Просмотр Туров*/
    @FXML
    void getToursView(ActionEvent event) {
        try {
            connect.writeLine("view");
            connect.writeLine("viewTour");
            ArrayList<Tour> tourArrayList = (ArrayList<Tour>) connect.readObjList().clone();
            this.tourArrayList = tourArrayList;
            ObservableList<Tour> observableList = FXCollections.observableArrayList(tourArrayList);
            tabViewTours.setItems(observableList);
            tabViewTours.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
            tabViewTours.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("countryName"));
            tabViewTours.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("cityName"));
            tabViewTours.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("price"));
            tabViewTours.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("duration"));
            tabViewTours.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("tourCode"));
            tabViewTours.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("tourDate"));
            tabViewTours.getColumns().get(7).setCellValueFactory(new PropertyValueFactory("tourName"));
            tabViewTours.getColumns().get(8).setCellValueFactory(new PropertyValueFactory("tourType"));
        } catch (IOException | ClassNotFoundException e) {
            new MyException(e);
        }
    }

    /**Просмотр Заказов*/
    @FXML
    void getOrderView(ActionEvent event) {
        try {
            connect.writeLine("view");
            connect.writeLine("viewOrder");
            ArrayList<Order> orderArrayList = (ArrayList<Order>) connect.readObjList().clone();
            this.orderArrayList = orderArrayList;
            ObservableList<Order> observableList = FXCollections.observableArrayList(orderArrayList);
            tabViewOrders.setItems(observableList);
            tabViewOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
            tabViewOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("clientCode"));
            tabViewOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("tourCode"));
        } catch (IOException | ClassNotFoundException e) {
            new MyException(e);
        }
    }

    /**Добавить тур*/
    @FXML
    void addTour(ActionEvent event) {
        new InputDialog(event, "add-tour.fxml", 530, 475);
    }

    /**Создать билет*/
    @FXML
    void checkAndCreateTicket(ActionEvent event) {
        new InputDialog(event, "check-create-ticket.fxml", 400, 400);
    }

}