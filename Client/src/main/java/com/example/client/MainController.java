package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.model.animation.Shake;
import com.example.model.connect.Connect;
import com.example.model.dialog.InputDialog;
import com.example.model.myexception.MyException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {

    public static Connect connect;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;


    /**
     * Button
     */
    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;


    static {
        connect = new Connect("127.0.0.1", 1024);
    }

    @FXML
    void initialize() {

        authSignInButton.setOnAction(actionEvent -> {
            try {
                String login = login_field.getText().trim();
                String pass = password_field.getText().trim();

                connect.writeLine("signIn");
                connect.writeLine(login);
                connect.writeLine(pass);

                String flag = connect.readLine();
                String flagAdminOrClient = connect.readLine();

                if (flag.equals("true")) {
                    System.out.println(flag);
                    if (flagAdminOrClient.equals("adminUI")) {
                        openNewScene("admin-ui.fxml");
                        System.out.println(flagAdminOrClient);
                    } else if (flagAdminOrClient.equals("clientUI")) {
                        openNewScene("client-ui.fxml");
                        System.out.println(flagAdminOrClient);
                    } else {

                    }
                } else if (flag.equals("false")) {
                    Shake shakeLogin = new Shake(login_field);
                    Shake shakePass = new Shake(password_field);
                    shakeLogin.playAnim();
                    shakePass.playAnim();
                }

            } catch (IOException e) {
                new MyException(e);
            }
        });
    }

    public void openNewScene(String window) {
        loginSignUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


    public void getOpenSignUp(ActionEvent actionEvent) {
        try {
            connect.writeLine("signUp");
            new InputDialog(actionEvent, "sign-up-ui.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}