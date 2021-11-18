package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.model.animation.Shake;
import com.example.model.check.Check;
import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    private Connect connect = MainController.connect;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField singUpFIO;

    @FXML
    private TextField singUpPhoneNumber;

    @FXML
    private PasswordField singUpPassword;

    @FXML
    private Button singUpButton;

    @FXML
    private Button closeSignUp;

    @FXML
    private TextField singUpMail;

    @FXML
    private TextField singUpLogin;

    @FXML
    private TextField singUpPassportId;

    @FXML
    private Label lableErroAddClient;


    @FXML
    void clickOnSignUpBtn(ActionEvent event) {
        try {
            Shake shakeFIO = new Shake(singUpFIO);
            Shake shakeMobileNumber = new Shake(singUpPhoneNumber);
            Shake shakePassword = new Shake(singUpPassword);
            Shake shakeMail = new Shake(singUpMail);
            Shake shakeLogin = new Shake(singUpLogin);
            Shake shakePassportId = new Shake(singUpPassportId);

            String fio = singUpFIO.getText().trim();
            String phoneNumber = singUpPhoneNumber.getText().trim();
            String password = singUpPassword.getText().trim();
            String mail = singUpMail.getText().trim();
            String login = singUpLogin.getText().trim();
            String passportId = singUpPassportId.getText().trim();
            if (Check.isNumber(phoneNumber) && !fio.equals("") && !password.equals("") && !mail.equals("") && !login.equals("") && !passportId.equals("")) {
                Client client = new Client();
                int a = 100;
                int b = 1000;
                int x = a + (int) (Math.random() * ((b - a) + 1));
                client.setFIO(fio);
                client.setClientCode("C" + String.valueOf(x));
                client.setMobileNumber(phoneNumber);
                client.setPassword(password);
                client.setMail(mail);
                client.setLogin(login);
                client.setPassportId(passportId);
                client.setFlag(2);
                System.out.println(client);

                connect.writeLine("signUp");
                connect.writeObj(client);
                String flag = connect.readLine();

                if (flag.equals("false")) {
                    lableErroAddClient.setText(" ошибка при регистрации вы не зарегистрированы, Обратитесь админу !!! ");
                } else if (flag.equals("true")) {
                    lableErroAddClient.setText("Регистрация прошла успешна.");
                }

            } else {
                shakeFIO.playAnim();
                shakeLogin.playAnim();
                shakeMail.playAnim();
                shakeMobileNumber.playAnim();
                shakePassword.playAnim();
                shakePassportId.playAnim();
            }
        } catch (IOException e) {
           new MyException(e);
        }
    }

    @FXML
    void initialize() {

    }

    @FXML
    void closeSignUp(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
