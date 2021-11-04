package com.example.model.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class InputDialog {

    public InputDialog(ActionEvent actionEvent) {

        Stage stage = new Stage();
        Parent root = null;
        try {
            root = load(getClass().getResource("../com/example/client/sign-up-ui.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Новое окно");
        stage.setScene(new Scene(root, 300, 300));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }
}