package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
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
    private Label outPutErrorAddUserLabel;

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
                for (Client c : clientArrayList) {
                    System.out.println(c.toString());
                }
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


            if (flag.equals("1") || flag.equals("2")) {
                signUpFlagField.setText("");
            } else {
                shakeFlag.playAnim();
            }

            if (Check.isNumber(mobileNumber)) {
                signUpMobileNumberField.setText("");
            } else {
                shakeMobileNumber.playAnim();
            }

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


        });


    }
}