package com.example.demo;

import FriendShipRequest.FriendShipReq;
import client.Client;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class pendingController implements Initializable {
    @FXML
    VBox list;




/*
    @FXML
    public void addReq(){
        for (FriendShipReq friendShipReq : signInController.client.friendShipReqs) {
            if (friendShipReq != null){
                HBox hBox= new HBox();
                Label name= new Label();
                name.setText(friendShipReq.getTargetClient());
                hBox.setClip(name);
            }

        //friendShipReq.getTargetClient();
        }

    }
*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = 1;
        for (FriendShipReq friendShipReq : signInController.client.friendShipReqs) {
            HBox hBox = new HBox(60);
            /*ImageView imageView = new ImageView();
            for (Client client : signInController.client.serverClients) {
                if (client.getUsername().equals(friendShipReq.getOriginClient())) {
                    if (client.image == null) {
                        Image image = new Image("C:\\Users\\mhdsa\\Desktop\\New folder (4)\\games_oop_javafx-master\\demo\\src\\main\\resources\\pics");
                        System.out.println("57");
                        imageView.setImage(image);
                    }
                } else {
                    imageView.setImage(client.image);
                    System.out.println(61);
                }
            }*/

            Button yes = new Button("YES");
            Button no = new Button("NO");
            yes.setId(friendShipReq.getOriginClient());
            no.setId(friendShipReq.getOriginClient());
            Label name1 = new Label();
            name1.setText(i + "--->" + friendShipReq.getOriginClient());
           // hBox.getChildren().add(imageView);
            hBox.getChildren().add(name1);
            hBox.getChildren().add(yes);
            hBox.getChildren().add(no);
            list.getChildren().add(hBox);
            i++;
            yes.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        signInController.client.outputStream.writeObject(new FriendShipReq(yes.getId(), signInController.client.getUsername(), "accepted"));
                        System.out.println(yes.getId() + "sent this");
                        signInController.client.friendShipReqs.remove(friendShipReq);
                        hBox.getChildren().clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            no.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        signInController.client.outputStream.writeObject(new FriendShipReq(no.getId(), signInController.client.getUsername(), "rejected"));
                        System.out.println(no.getId() + "sent this");
                        signInController.client.friendShipReqs.remove(friendShipReq);
                        hBox.getChildren().clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }


    }


}
