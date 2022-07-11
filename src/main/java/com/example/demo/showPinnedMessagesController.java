package com.example.demo;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class showPinnedMessagesController implements Initializable {
    @FXML
    VBox showPinned;
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = 1;
        for (String pinned : PinMessagesController.pinnedMessages) {
            System.out.println("222222222222222222222222222222222222222222");
            HBox hBox = new HBox();
            Label messages = new Label();
            messages.setStyle("-fx-text-fill:WHITE; -fx-font-size: 15;");
            messages.setText(i + "----->" + pinned);
            hBox.getChildren().add(messages);
            HBox.setMargin(messages, new Insets(10, 10, 10, 10));
            showPinned.getChildren().add(hBox);
        }
    }
    public void back(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "mainScene!.fxml"));
            root = (Parent) loader.load();
            signInController.client.setMainSceneController(loader.getController());
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

