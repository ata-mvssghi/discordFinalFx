package com.example.demo;

import Group.Group;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class serverSettingsController implements Initializable {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    ScrollPane sp;
    @FXML
    VBox channels;
    public void switchTOInvitePeople(Event event){
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("inviteNewPeopleToServer.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
//public void setChat(String  groupname,){
//
//}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Group group:signInController.client.groups) {
            HBox hBox = new HBox();
            Label channelName = new Label();
            channelName.setText(group.getName());
            channelName.setId(group.getId()+"");
            hBox.getChildren().add(channelName);
            chatController.chatLName=channelName.getText();
            hBox.setId(group.getId()+"");
            Label notReadMessages=new Label();
            if(group.countOfNotSeenMessages()!=0) {
                notReadMessages.setText(group.countOfNotSeenMessages() + "");
            }
                hBox.getChildren().add(notReadMessages);
            channels.getChildren().add(hBox);
            hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    try {
                        FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("chat.fxml")));
                        AnchorPane pane=loader.load();
                        MainSceneController.currentChatId=Integer.parseInt(hBox.getId());
                        signInController.client.setChatController1(loader.getController());
                        chatController.chatID=Integer.parseInt(hBox.getId());
                        signInController.client.mainSceneController.setChatPane(pane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
    public void createNewChannel(Event event){
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("createNewChannel.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
