package com.example.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.model.animation.Shake;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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


    /**Button*/
    @FXML
    private Button authSignInButton;

    @FXML
    private Button xBtn;

    @FXML
    private Button loginSignUpButton;



    static {
        connect = new Connect("127.0.0.1", 8189);
    }

    @FXML
    void initialize() {

      xBtn.setOnAction(actionEvent -> {System.exit(1);});
      authSignInButton.setOnAction(actionEvent -> {
          try {
              String login = login_field.getText().trim();
              String pass = password_field.getText().trim();
              connect.writeLine(login);
              connect.writeLine(pass);
              String flag  = connect.readLine();
              if(flag.equals(true)){
                  System.out.println(flag);
              }else {
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
}
