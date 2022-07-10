package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SavedScene {
    public static Scene mainScene;
    public static void initialize(){
        FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader(MainSceneController.class.getResource("mainScene!.fxml"));
            try {
                mainScene = new Scene(fxmlLoader.load(), 605, 487);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
