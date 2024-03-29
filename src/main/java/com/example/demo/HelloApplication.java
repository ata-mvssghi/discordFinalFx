package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//اگر کاربره تو اد فرند نبود پیام بده
//اون اسم گروه باید برای دو طرف اسم طرق مقابل باشه
//اسم روه لیبلش رو تنظیم کنم برا ....
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 500);
        stage.setTitle("sign in");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}