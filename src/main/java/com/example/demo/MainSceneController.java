package com.example.demo;

import MessagePack.Message;
import ServerChat.ServerChat;
import client.Client;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainSceneController implements Serializable,Initializable {
    @FXML
   transient VBox servers;
    @FXML
   transient Parent root;
    @FXML
   transient Stage stage;
    @FXML
   transient BorderPane borderPane;
    @FXML
   transient Scene scene;
    @FXML
    transient  VBox  serverList;
    @FXML
   transient BorderPane directs;
    @FXML
    transient BorderPane chatPane;
    public static int currentChatId;
    public void setChatPane(Pane pane){
        chatPane.setCenter(pane);
    }
    public void sdf(Event event){
        try {
            signInController.client.outputStream.writeObject(new Message(signInController.client.username,"fgh", Message.Type.join));
           AnchorPane pane=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("chat.fxml")));
           borderPane.setRight(pane);
            signInController.client.sendJoin();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        public void addNewServer(){
        HBox hBox=new HBox();
        hBox.getChildren().add(new TextField("eaSAFDSGF"));
        servers.setSpacing(15);
        servers.getChildren().add(hBox);
        }
        public void switchToSettingScene(Event event){
            try {
                root = FXMLLoader.load(getClass().getResource("personalSettings.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    public void switchToFriend(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("friend.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToCreateNewServer(Event event)
    {
        try {
            root = FXMLLoader.load(getClass().getResource("CreateNewServer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setServer(HBox hBox){
        System.out.println("dsgdggdg");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serverList.getChildren().add(hBox);
            }
        });

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ImageView profile=null;
//        if(signInController.client.image!=null) {
//           profile= new ImageView(new Image(signInController.client.image));
//            serverList.getChildren().add(profile);
//        }
//        else {
//            profile=new ImageView();
//        serverList.getChildren().add(profile);
//        }
        for( ServerChat serverChat: Client.serversChats){
            HBox hBox=new HBox();
            ImageView serverPic=new ImageView(new Image(serverChat.imageAddress));
            serverPic.setPreserveRatio(true);
            serverPic.setFitWidth(72);
            serverPic.setFitHeight(72);
            hBox.setId(serverChat.id+"");
            hBox.getChildren().add(serverPic);
            HBox.setMargin(serverPic, new Insets(10, 10, 10, 20));
            Label serverNAme=new Label();
            hBox.getChildren().add(serverNAme);
            serverList.getChildren().add(hBox);
            hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    AnchorPane pane = null;
                    try {
                        pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("serverSettings.fxml")));
                        directs.setTop(null);
                        directs.setBottom(null);
                        directs.setCenter(pane);
                        invitePeopleToServerController.serverIndex=Integer.valueOf(hBox.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }            });
           serverPic.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    serverNAme.setText(serverChat.getServerName());
                }
            });
            serverPic.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    hBox.getChildren().clear();
                    hBox.getChildren().add(serverPic);
                }
            });
        }
    }
}

