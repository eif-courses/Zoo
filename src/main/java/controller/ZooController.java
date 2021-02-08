package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ZooController implements Initializable {

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Button button;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    void click(ActionEvent event) {
        button.setStyle("-fx-background-color: #ffc455");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button.setStyle("-fx-background-color: #55acff");

    }
}
