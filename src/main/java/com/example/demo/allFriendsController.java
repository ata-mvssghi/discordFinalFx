package com.example.demo;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class allFriendsController implements Initializable {
    @FXML
    VBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i=1;
        for(Client friend:signInController.client.friends){
            HBox hBox=new HBox();
            Label name=new Label();
            name.setText(i+"------->"+friend.username);
            hBox.getChildren().add(name);
            vBox.getChildren().add(hBox);
            i++;
        }
    }
}
