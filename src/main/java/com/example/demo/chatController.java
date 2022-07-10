package com.example.demo;

import Group.Group;
import MessagePack.Message;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class chatController implements Initializable {
    @FXML
    VBox messagesVbox;
    @FXML
    Label chatName;
    @FXML
    TextField text;
    public static int chatID;
    public static String chatLName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatName.setText(chatLName);
        for( Group group: signInController.client.groups){
            if(group.getId()==chatID){
                for(Message message:group.getMessages()){
                    HBox hBox=new HBox();
                    Label text=new Label();
                    text.setFont(new Font(15));
                    text.setText(message.getSender()+" : "+message.getText());
                    hBox.getChildren().add(text);
                    messagesVbox.getChildren().add(hBox);
                }
            }
        }
    }
    public void send(Event event){
        String message=text.getText();
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);
        Label line=new Label();
        line.setFont(new Font(15));
        line.setText(signInController.client.username+" : "+message);
        hBox.getChildren().add(line);
        messagesVbox.getChildren().add(hBox);
        try {
            signInController.client.sendMSG(chatID,message);
            text.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void setMessagesVbox(HBox hBox){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messagesVbox.getChildren().add(hBox);
            }
        });

    }
}
