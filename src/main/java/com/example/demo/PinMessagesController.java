package com.example.demo;

import Group.Group;
import MessagePack.Message;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PinMessagesController implements Initializable {
    public static int currentChatId;
    @FXML
    VBox messagesVbox;
    public static ArrayList<String > pinnedMessages=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       for(Group group:signInController.client.groups){
           if(group.getId()==currentChatId){
               for(Message message:group.getMessages()){
                   HBox hBox=new HBox();
                   Label label=new Label();
                   label.setText(message.getSender()+" : "+message.getText());
                   label.setStyle("-fx-text-fill:WHITE; -fx-font-size: 15;");
                   hBox.getChildren().add(label);
                   HBox.setMargin(label,new Insets(10,10,10,10));
                   messagesVbox.getChildren().add(hBox);
                   hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                       @Override
                       public void handle(MouseEvent mouseEvent) {
                           pinnedMessages.add(((Label)hBox.getChildren().get(0)).getText());
                           System.out.println("haa let's see : "+(((Label) hBox.getChildren().get(0)).getText()));
                           FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("chat.fxml")));
                           AnchorPane pane= null;
                           try {
                               pane = loader.load();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                           signInController.client.setChatController1(loader.getController());
                           signInController.client.mainSceneController.setChatPane(pane);
                       }
                   });
               }
           }
       }
    }
}
