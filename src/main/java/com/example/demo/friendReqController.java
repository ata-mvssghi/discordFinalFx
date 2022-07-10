package com.example.demo;

import FriendShipRequest.FriendShipReq;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class friendReqController {
    @FXML
    TextField name;
    @FXML
    Button search;


    @FXML
    public void search() {
        FriendShipReq friendShipReq = new FriendShipReq(signInController.client.getUsername(), name.getText(), "request");
        try {
            signInController.client.outputStream.writeObject(friendShipReq);
            System.out.println("23");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
