package com.example.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class AdminController {

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


    }
}
