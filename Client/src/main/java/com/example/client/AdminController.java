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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController {

    private Connect connect = MainController.connect;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane glavnyPane;


    @FXML
    private Tab u1;

    @FXML
    private Tab u2;

    @FXML
    private Tab u3;


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


    @FXML
    private TableView<Client> usersTableView;

    @FXML
    private TableColumn<Client, String> fioTableColumn;

    @FXML
    private TableColumn<Client, String> codeClientTableColumn;

    @FXML
    private TableColumn<Client, String> passportIdTableColumn;

    @FXML
    private TableColumn<Client, String> mailTableColumn;

    @FXML
    private TableColumn<Client, String> mobileNumberTableColumn;

    @FXML
    private TableColumn<Client, String> loginTableColumn;

    @FXML
    private TableColumn<Client, Integer> flagTableColumn;


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

    /**
     * edit table
     */

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


    @FXML
    void initialize() {

        usersBtn.setOnAction(actionEvent -> {
            glavnyPane.getSelectionModel().select(u1);
        });

        toursBtn.setOnAction(actionEvent -> {
            glavnyPane.getSelectionModel().select(u2);
        });

        ticketsBtn.setOnAction(actionEvent -> {
            glavnyPane.getSelectionModel().select(u3);
        });

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

        addUserBtn.setOnAction(actionEvent -> {

            Client client = new Client();
            Shake shakeFIO = new Shake(signUpFIOField);
            Shake shakeClientCode = new Shake(signUpClientCodeField);
            Shake shakeMobileNumber = new Shake(signUpMobileNumberField);
            Shake shakePassword = new Shake(signUpPasswordField);
            Shake shakeMail = new Shake(signUpMailField);
            Shake shakeLogin = new Shake(signUpLoginField);
            Shake shakePassportId = new Shake(signUpPassportIdField);
            Shake shakeFlag = new Shake(signUpFlagField);

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
                        outPutErrorAddUserLabel.setText("Ползователь не добавлен в базу пожалуйста обратитесь к администрации");
                    } else {
                        outPutErrorAddUserLabel.setText(" ошибка дабавления обратитесь к администрации ");
                    }
                    outPutErrorAddUserLabel.setText("");

                } catch (IOException e) {
                    new MyException(e);
                }

            } else {
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

            }
        });

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
                    Client client;

                    if (flagSearchUser.equals("true")) {
                        client = (Client) connect.readObj();
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
                /*signUpSearchFIOField.setText("");
                signUpSearchLoginField.setText("");
                signUpSearchPasswordField.setText("");
                signUpEditFIOField.setText("");
                signUpEditClientCodeField.setText("");
                signUpEditPassportIdField.setText("");
                signUpEditMailField.setText("");
                signUpEditPasswordField.setText("");
                signUpEditLoginField.setText("");
                signUpEditMobileNumberField.setText("");
                signUpEditFlagField.setText("");*/
            } catch (Exception e) {
                new MyException(e);
            }
        });


        //TODO надо закончить отправку send and и проверку коректности и надо дописать UPDATE in database in sever tomorrow fixing
        editBtn.setOnAction(actionEvent -> {
            Client client = new Client();
            client.setFIO(signUpEditFIOField.getText().trim());
            client.setClientCode(signUpEditClientCodeField.getText().trim());
            client.setPassportId(signUpEditPassportIdField.getText().trim());
            client.setMail(signUpEditMailField.getText().trim());
            client.setPassword(signUpEditPasswordField.getText().trim());
            client.setLogin(signUpEditLoginField.getText().trim());
            client.setMobileNumber(signUpEditMobileNumberField.getText().trim());
            client.setFlag(Integer.parseInt(signUpEditFlagField.getText().trim()));
        });
    }
}