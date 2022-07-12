package com.example.demo;

import ServerChat.ServerChat;
import client.Client;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SetRollController {
    @FXML
    CheckBox createANewChannel;
    @FXML
    CheckBox deleteAChannel;
    @FXML
    CheckBox deleteAUserFromServer;
    @FXML
    CheckBox limitTheAccess;
    @FXML
    CheckBox ban;
    @FXML
    CheckBox changeServerName;
    @FXML
    CheckBox seePreviousMessages;
    @FXML
    CheckBox pin;
    @FXML
    Parent root;
    @FXML
    Scene scene;
    @FXML
    Stage stage;
    public static int serverIndex;
    public static String targetClient;
public void setRole(Event event){
    ArrayList<String  >permissions=new ArrayList<>();
if(createANewChannel.isSelected()){
    permissions.add("createANewChannel");
}
else if(deleteAChannel.isSelected()){
    permissions.add("deleteAChannel");
}
else if(deleteAUserFromServer.isSelected()){
    permissions.add("deleteAUserFromServer");
}
else if(limitTheAccess.isSelected()){
    permissions.add("limitTheAccess");
}
else if(ban.isSelected()){
    permissions.add("ban");
}
else if(changeServerName.isSelected()){
    permissions.add("changeServerName");
}
else if(seePreviousMessages.isSelected()){
    permissions.add("seePreviousMessages");
}
for(ServerChat serverChat: Client.serversChats){
    if(serverChat.id==serverIndex){
        serverChat.addPermission(targetClient,permissions);
    }
}
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScene!.fxml"));
        root = (Parent) loader.load();
        signInController.client.setMainSceneController(loader.getController());
        System.out.println(loader
                .getController().toString());
    } catch (IOException e) {
        e.printStackTrace();
    }
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
}

}
