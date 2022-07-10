package com.example.demo;

import MessagePack.Message;
import Request.Request;
import client.Client;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class signUpController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField emailTextField;
    @FXML
    Label usernameRes;
    @FXML
    Label passwordRes;
    @FXML
   Label emailRes;
    @FXML
    public void signup(Event event){
            try {
                signInController.client.outputStream.writeObject(new Request(Request.Type.signUp, "sign up request"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String username1;
            while (true) {
                username1 = usernameTextField.getText();
             /*  while (true) {
                    if (checkUsername(username1)) {
                        break;
                    } else if (!checkUsername(username1)) {
                        System.out.println("invalid input try again(username must consist of both lower and upper case letter and number");
                        username1 = scanner.nextLine();
                    }
                }
                */
                try {
                    signInController.client.outputStream.writeObject(username1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String contain = signInController.client.getServerResponseToUsername();
                if (contain.equals("exist")) {
                    System.out.println("username is exist(tekrari)");
                    usernameRes.setText("username exists try another");
                }
                if (contain.equals("notExist")) {
                    usernameRes.setText("approved");
                    break;
                }
            }
            System.out.println("enter the password");
            String password1 = passwordTextField.getText();
          /*  while (true) {
                if (checkPassword(password1)) {
                    break;
                } else if (!checkPassword(password1)) {
                    System.out.println("invalid input try again");
                    password1 = scanner.nextLine();
                }
            }
            */
            System.out.println("enter the email");
            String email1 = emailTextField.getText();
          /* while (true) {
                if (emailCheck(email1)) {
                    break;
                } else if (!emailCheck(email1)) {
                    System.out.println("invalid input try again");
                    email1 = scanner.nextLine();
                }
            }
            */

            try {
                signInController.client.setUsername(username1);
                signInController.client.setPassword(password1);
               signInController.client.setEmail(email1);
               signInController.client. outputStream.writeObject(new Client("127.0.0.1", 6060, username1, password1, email1));
                signInController.client.outputStream.writeObject(new Message(username1, "", Message.Type.join));
                String path = "C:\\Users\\SPINO.SHOP\\Desktop\\the last version\\games_oop_javafx-master\\demo\\src\\main\\java\\ClientSave\\";
                FileOutputStream fileOutputStream = new FileOutputStream(path + signInController.client.username + ".bin");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(signInController.client);
                switchToSignInScene(event);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    public void switchToSignInScene(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
