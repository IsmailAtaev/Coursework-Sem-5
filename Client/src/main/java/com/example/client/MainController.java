package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.model.animation.Shake;
import com.example.model.client.Client;
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

    public static Client client;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;


    static {
        connect = new Connect("127.0.0.1", 1232);
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
                    System.out.println(flagAdminOrClient);

                    if (flagAdminOrClient.equals("adminUI")) {

                        System.out.println("admin");
                        openNewScene("admin-ui.fxml");
                        //handleButtonClick("admin-ui.fxml");
                        System.out.println(flagAdminOrClient);

                    } else if (flagAdminOrClient.equals("clientUI")) {
                        Client c = (Client) connect.readObj();
                        client = c;
                        System.out.println("client");
                        //handleButtonClick("client-ui.fxml");
                        openNewScene("client-ui.fxml");
                        System.out.println(flagAdminOrClient);

                    } else {
                        System.out.println("do not user ");
                    }
                } else if (flag.equals("false")) {
                    Shake shakeLogin = new Shake(login_field);
                    Shake shakePass = new Shake(password_field);
                    shakeLogin.playAnim();
                    shakePass.playAnim();
                }

            } catch (IOException e) {
                new MyException(e);
            } catch (ClassNotFoundException e) {
                new MyException(e);
            }
        });
    }

    public void openNewScene(String window) {
        try {
            loginSignUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(window));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            new MyException(e);
        }
    }

    public void getOpenSignUp(ActionEvent actionEvent) {
        new InputDialog(actionEvent, "sign-up-ui.fxml");
    }


    public void handleButtonClick(String win) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(win));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 600, 700);
            Stage stage = new Stage();
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }
}