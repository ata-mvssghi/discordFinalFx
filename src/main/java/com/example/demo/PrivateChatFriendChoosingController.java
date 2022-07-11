package com.example.demo;

import Group.Group;
import MessagePack.Message;
import client.Client;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrivateChatFriendChoosingController  implements Initializable {
@FXML
    VBox vBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Client friend:signInController.client.friends){
            HBox hBox=new HBox();
            Label name=new Label();
            name.setText(friend.username);
            Button button=new Button("confirm");
            button.setId(friend.username);
            hBox.getChildren().add(name);
            hBox.getChildren().add(button);
            HBox.setMargin(name, new Insets(10, 10, 10, 20));
            HBox.setMargin(button, new Insets(10, 10, 10, 20));
            vBox.getChildren().add(hBox);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        signInController.client. outputStream.writeObject(new Message(signInController.client.username, "groupIndex", Message.Type.groupIndex));
                        signInController.client. outputStream.writeObject(new Message(signInController.client.username, friend.getUsername(), Message.Type.createNewGroupRequest));
                        ArrayList<Client> chosenClients = new ArrayList<>();
                        for(Client temp:signInController.client.friends){
                            if(temp.getUsername().equals(button.getId())){
                                chosenClients.add(temp);
                            }
                        }
                        chosenClients.add(signInController.client);
                        Group newGroup = new Group(friend.username, signInController.client.serverGroupIndex);
                        newGroup.setType(Group.Type.privateChat);
                        newGroup.addClients(chosenClients);
                        signInController.client.outputStream.writeObject(newGroup);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
