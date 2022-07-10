package com.example.demo;

import MessagePack.Message;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class editInfoController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    TextField userNameTextField;
    @FXML
    TextField emailTextField;
    @FXML
    TextField phoneTextField;
    @FXML
     TextField currentPass;
    @FXML
    TextField toBeChangedPass;
    @FXML
    Label passRes;
    public void confirmUserNAme(Event event){
        String newUserName=userNameTextField.getText();
        try {
            signInController.client.outputStream.writeObject(new Message(signInController.client.username,newUserName, Message.Type.changeUserName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path oldFile
                = Paths.get("C:\\Users\\SPINO.SHOP\\Desktop\\games_oop_javafx-master\\demo\\src\\main\\java\\ClientSave\\"+signInController.client.username+".bin");

        try {
            Files.move(oldFile, oldFile.resolveSibling(
                    newUserName+".bin"));
            System.out.println("File Successfully Rename");
        }
        catch (IOException e) {
            System.out.println("operation failed");
        }
        signInController.client.setUsername(newUserName);
        try {
            root = FXMLLoader.load(getClass().getResource("personalSettings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void confirmEmail(Event event){
        String newEmail=emailTextField.getText();
        try {
            signInController.client.outputStream.writeObject(new Message(signInController.client.username,newEmail, Message.Type.changeEmail));
            signInController.client.outputStream.writeObject(signInController.client.email);
        } catch (IOException e) {
            e.printStackTrace();
        }
        signInController.client.setEmail(newEmail);
        try {
            root = FXMLLoader.load(getClass().getResource("personalSettings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void addNumber(Event event){
        String newNum=phoneTextField.getText();
        try {
            signInController.client.outputStream.writeObject(new Message(signInController.client.username,newNum, Message.Type.addPhoneNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
        signInController.client.phoneNumber=newNum;
        try {
            root = FXMLLoader.load(getClass().getResource("personalSettings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void changePassword(Event event){
        String currentPassText=currentPass.getText();
        String newPass=toBeChangedPass.getText();
        try {
            signInController.client.outputStream.writeObject(new Message(signInController.client.username,currentPassText, Message.Type.changePasswordConfirm));
            try {
                passRes.setText("processing...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!signInController.client.confirmPass) {
                passRes.setText("wrong password");
                return;
            }
            signInController.client.outputStream.writeObject(toBeChangedPass.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //signInController.client.setEmail(newEmail);
        try {
            root = FXMLLoader.load(getClass().getResource("personalSettings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
