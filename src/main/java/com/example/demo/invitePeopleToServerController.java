package com.example.demo;

import MessagePack.Message;
import ServerChat.ServerChat;
import client.Client;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class invitePeopleToServerController implements Initializable {
    @FXML
    VBox vBox;
    @FXML
    Parent root;
    @FXML
    Stage stage;
    public static int serverIndex;
ArrayList<Client> chosenFriends=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            signInController.client.outputStream.writeObject(new Message(signInController.client.username,"dwf", Message.Type.allClients));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Client friend: signInController.client.friends){
            HBox hBox=new HBox(60);
            ImageView profilePic=new ImageView();
//            if(friend.image!=null){
//                profilePic.setImage(new Image(friend.image));
//            }
//            else {
//                profilePic.setImage(new Image("C:\\Users\\SPINO.SHOP\\Desktop\\the last version\\games_oop_javafx-master\\demo\\src\\main\\resources\\pics\\nopic.png"));
//            }
            hBox.getChildren().add(profilePic);
            Label name=new Label();
            name.setText(friend.username);
            hBox.getChildren().add(name);
            Button button=new Button("invite");
            button.setId(friend.username);
            hBox.getChildren().add(button);
            vBox.getChildren().add(hBox);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for(Client client:signInController.client.serverClients){
                        if(client.getUsername().equals(button.getId())){
                            chosenFriends.add(client);
                        }
                    }
                }
            });
        }
    }
    public void confirm(Event event){
        for(ServerChat temp: Client.serversChats){
            if(temp.id==serverIndex){
                for(Client client:chosenFriends){
                    temp.addClients(client);
                }
                try {
                    signInController.client.outputStream.writeObject(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "mainScene!.fxml"));
            root = (Parent) loader.load();
            signInController.client.setMainSceneController(loader.getController());
            System.out.println("haaa::: "+loader.getController().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
