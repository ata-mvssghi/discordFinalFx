package com.example.demo;

import MessagePack.Message;
import client.Client;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class StatusController {
    @FXML
    ImageView online;
    @FXML
    ImageView idle;
    @FXML
    ImageView doNotDisturb;
    @FXML
    ImageView invisible;
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
public void setStatus(Event event){
    try {
        if (((ImageView) event.getSource()).getId().equals("online")) {
            signInController.client.setStatus(Client.Status.online);
            signInController.client.outputStream.writeObject(new Message(signInController.client.username, "online", Message.Type.statusReq));
        } else if (((ImageView) event.getSource()).getId().equals("idle")) {
            signInController.client.setStatus(Client.Status.Idle);
            signInController.client.outputStream.writeObject(new Message(signInController.client.username, "IDLE", Message.Type.statusReq));
        } else if (((ImageView) event.getSource()).getId().equals("doNotDisturb")) {
            signInController.client.setStatus(Client.Status.doNotDisturb);
            signInController.client.outputStream.writeObject(new Message(signInController.client.username, "doNotDisturb", Message.Type.statusReq));
        } else if (((ImageView) event.getSource()).getId().equals("invisible")) {
            signInController.client.setStatus(Client.Status.Invisible);
            signInController.client.outputStream.writeObject(new Message(signInController.client.username, "INVISIBLE", Message.Type.statusReq));
        }
    }
    catch (Exception e){
        e.printStackTrace();
    }
    try {
        root = FXMLLoader.load(getClass().getResource("personalSettings.fxml"));
    } catch (IOException e) {
        e.printStackTrace();
    }
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene =new Scene(root);
    stage.setScene(scene);
    stage.show();
}
}
