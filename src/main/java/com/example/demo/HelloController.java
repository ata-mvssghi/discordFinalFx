package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField email;

    @FXML
    public void setUsername(){
        username.getText();
    }


 //   protected void onHelloButtonClick() {
    //    welcomeText.setText("Welcome to JavaFX Application!");}
}