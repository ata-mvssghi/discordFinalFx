package com.example.demo;
import FriendShipRequest.FriendShipReq;
import MessagePack.Message;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
public class friendController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    BorderPane temp;

    public void switchToFriendReq(Event event) {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("friendReq.fxml")));

            temp.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void wwww(){
        try {
            signInController.client.outputStream.writeObject(new Message(signInController.client.username,"de", Message.Type.allClients));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void search() {
        FriendShipReq friendShipReq = new FriendShipReq(signInController.client.getUsername(), "q", "request");
        try {
            System.out.println("21");
            signInController.client.outputStream.writeObject(friendShipReq);
            System.out.println("23");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void switchToAllFriends(Event event){
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("allFriends.fxml")));
            temp.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void switchToPending(Event event) {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pending.fxml")));
            temp.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void switchToMainMenu(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "mainScene!.fxml"));
            root = (Parent) loader.load();
            signInController.client.setMainSceneController(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
