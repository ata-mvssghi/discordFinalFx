package com.example.demo;
//لود کردن عکس و شماره تلفن تو ساین این مونده
import MessagePack.Message;
import client.Client;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

    public class personalSettingsController implements Initializable {
        @FXML
        Label userNameNextToImage;
    @FXML
    Label username;
    @FXML
    Label email;
    @FXML
    Label phoneNumber;
    @FXML
        ImageView profilePic;
    @FXML
        Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    ImageView status;
    final FileChooser fileChooser=new FileChooser();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    userNameNextToImage.setText(signInController.client.username);
    email.setText(signInController.client.email);
    username.setText(signInController.client.username);
    if(signInController.client.phoneNumber!=null)
    phoneNumber.setText(signInController.client.phoneNumber);
    if(signInController.client.image!=null)
    profilePic.setImage(new Image(signInController.client.image));
    if(signInController.client.getStatus()== Client.Status.online){
        Image image= null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\SPINO.SHOP\\Desktop\\me my own\\the last version\\games_oop_javafx-master\\demo\\src\\main\\resources\\pics\\online.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        status.setImage(image);
    }
    if(signInController.client.getStatus()== Client.Status.doNotDisturb){
        Image image= null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\SPINO.SHOP\\Desktop\\me my own\\the last version\\games_oop_javafx-master\\demo\\src\\main\\resources\\pics\\do not disturb.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        status.setImage(image);
    }
    else if(signInController.client.getStatus()== Client.Status.Invisible){
        Image image= null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\SPINO.SHOP\\Desktop\\me my own\\the last version\\games_oop_javafx-master\\demo\\src\\main\\resources\\pics\\invisiable.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        status.setImage(image);
    }
    else if(signInController.client.getStatus()== Client.Status.Idle){
        Image image= null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\SPINO.SHOP\\Desktop\\me my own\\the last version\\games_oop_javafx-master\\demo\\src\\main\\resources\\pics\\idle.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        status.setImage(image);
    }
    }
    public  void chooseImageFile(){
        fileChooser.setTitle("image picker");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files","*.png","*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        Image newImage=new Image(file.toURI().toString());
        profilePic.setImage(newImage);
        signInController.client.image=file.toURI().toString();
        try {
            signInController.client.outputStream.writeObject(new Message(signInController.client.username,file.toURI().toString(), Message.Type.setImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void switchToMainScene(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("mainScene!.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void changeUsername(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("newUserNameScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void changeEmail(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("changeEmail.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public  void  changePass(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("PasswordChange.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public  void addPhoneNum(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("addPhoneNumber.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToSignInScene(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setStatus(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("status.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

