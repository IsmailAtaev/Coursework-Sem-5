package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private TableView<Client> usersTableView;

    @FXML
    private TableColumn<Client, String> fioTableColumn;

    @FXML
    private TableColumn<Client, String>  codeClientTableColumn;

    @FXML
    private  TableColumn<Client, String>  passportIdTableColumn;



    @FXML
    private Button viewUsersBtn;

    @FXML
    private Button deleteUsersBtn;

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

            } catch (IOException | ClassNotFoundException e) {
                new MyException(e);
            }
        });


    }
}