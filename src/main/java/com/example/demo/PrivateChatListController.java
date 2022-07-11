package com.example.demo;

import Group.Group;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrivateChatListController implements Initializable {
    @FXML
    VBox chatsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("26");
        for(Group group:signInController.client.groups){
            System.out.println("28");
            if(group.getType()== Group.Type.privateChat){
                HBox hBox=new HBox();
                Label label=new Label();
                label.setText(group.getName());
                hBox.getChildren().add(label);
                hBox.setId(group.getId()+"");
                System.out.println("got wwe see asla");
                HBox.setMargin(label,new Insets(10,10,10,10));
                chatsList.getChildren().add(hBox);
                hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("chat.fxml")));
                            AnchorPane pane=loader.load();
                            MainSceneController.currentChatId=Integer.valueOf(hBox.getId());
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
    }
}
