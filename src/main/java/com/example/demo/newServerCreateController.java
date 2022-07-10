package com.example.demo;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class newServerCreateController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    TextField serverName;
    @FXML
    ImageView imageView;
    String  imageAddress;
    FileChooser fileChooser=new FileChooser();
    public  void chooseImageFile(){
        fileChooser.setTitle("image picker");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files","*.png","*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        imageAddress=file.toURI().toString();
        imageView.setImage(new Image(imageAddress));
    }
    public void switchToMainMenu(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("mainScene!.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    public  void createNewServer(Event event){
        String serverN=serverName.getText();
        ServerChat serverChat=new ServerChat(signInController.client ,serverN);
        try {
            serverChat.addClients(signInController.client);
            signInController.client.outputStream.writeObject(new Message(signInController.client.username,"attemping to get server index", Message.Type.servetChatIndex));
            serverChat.id=signInController.client.serverChatIndex;
            serverChat.imageAddress=imageAddress;
            signInController.client.outputStream.writeObject(serverChat);
            createNEwChannelController.serverChat=serverChat.id;
        } catch (IOException e) {
            e.printStackTrace();
        }
        switchToMainMenu(event);
    }
}
