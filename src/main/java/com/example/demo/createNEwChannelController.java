package com.example.demo;

import Group.Group;
import MessagePack.Message;
import ServerChat.ServerChat;
import client.Client;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class createNEwChannelController {
    public static int serverChat;
    public static ArrayList<Client> chosenClients=new ArrayList<>();
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    TextField channelName;

    public void create(Event event) throws IOException {
        signInController.client.outputStream.writeObject(new Message(signInController.client.username, "groupIndex", Message.Type.groupIndex));
        signInController.client.outputStream.writeObject(new Message(signInController.client.username, channelName.getText(), Message.Type.createNewGroupRequest));
        try {
            Thread.sleep(2000);
            Group newGroup = new Group(channelName.getText(), signInController.client.serverGroupIndex);
            newGroup.setType(Group.Type.channel);
            newGroup.setServerChatId(serverChat);
            for (ServerChat temp : Client.serversChats) {
                if (temp.id == serverChat) {
                    for (Client client : temp.getUsers()) {
                        chosenClients.add(client);
                    }
                }
            }
            newGroup.addClients(chosenClients);
            System.out.println("channel created successfully");
            signInController.client.outputStream.writeObject(newGroup);
            back(event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void back(Event event) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "mainScene!.fxml"));
            root = (Parent) loader.load();
            signInController.client.setMainSceneController(loader.getController());
        } catch(
                IOException e)
        {
            e.printStackTrace();
        }
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
