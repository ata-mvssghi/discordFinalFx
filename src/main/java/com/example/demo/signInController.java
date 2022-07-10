package com.example.demo;

import Request.Request;
import client.Client;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class signInController implements Initializable {
    @FXML
    Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Label usernameRes;
    @FXML
    Label passwordRes;
   boolean infoCheck;
//چطوری بیرون متد به کلاینت دسترسی داشته باشم
    //چطوری وقتی سین مختلف داریم وکنترل های مخلف هم داریم چطوری بیام کلاینت رو بینشون پاس بدم؟
    //چطوری چت ها رو تغییر بدیم
    public static Client client = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SavedScene.initialize();
        client = new Client("127.0.0.1", 6090, "tempClient", "123456", "test@gmail.com");
        client.startClient();
       SavedScene.initialize();

    }
    public void infoCheck() {
        try {
            client.outputStream.writeObject(new Request(Request.Type.signIn, "sign in request"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String username1;
            System.out.println("enter the username");
            username1 = usernameTextField.getText();
               /* while (true) {
                    if (checkUsername(username1)) {
                        break;
                    }
                    else if (!checkUsername(username1)) {
                        System.out.println("invalid input try again (username must consist of English letters and numbers\n and at least with length of 6");
                        username1 = scanner.nextLine();
                    }
                }
                */

            try {
                client.outputStream.writeObject(username1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String contain = client.getServerResponseToUsername();
            if (contain.equals("exists")) {
                usernameRes.setText("approved");
            }
            if (contain.equals("notExist")) {
                usernameRes.setText("didn't find the user try again");
                System.out.println("username is not exist try again");
            }
        String password1;
        client.setUsername(username1);
            System.out.println("enter the password");
            password1 = passwordTextField.getText();
               /*while (true) {
                    if (checkPassword(password1)) {
                        break;
                    } else if (!checkPassword(password1)) {
                        System.out.println("invalid input try again");
                        password1 = scanner.nextLine();
                    }
                }
                */
            try {
                client.outputStream.writeObject(password1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
              String  contain1 = client.getServerResponseToPassword();
            if (contain1.equals("exists")) {
                passwordRes.setText("approved");
            }
            if (contain1.equals("notExist")) {
                passwordRes.setText("wrong password try again");
                System.out.println("password does not exist");
            }
        client.setPassword(password1);
        String path = "C:\\Users\\SPINO.SHOP\\Desktop\\the last version\\games_oop_javafx-master\\demo\\src\\main\\java\\ClientSave\\";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path + client.getUsername() + ".bin");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Client client = null;
        try {
            ObjectInputStream objectInput = new ObjectInputStream(fileInputStream);
            client = (Client) objectInput.readObject();
            set(client);
            infoCheck=true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void set(Client client){
        signInController.client.username = client.getUsername();
        signInController.client.password= client.getPassword();
        signInController.client.email = client.getEmail();
        signInController.client.friends = client.friends;
        signInController.client.friendShipReqs = client.friendShipReqs;
       signInController. client.groups = client.groups;
       signInController.client.phoneNumber=client.phoneNumber;
    }
    public void switchToMainScene(Event event) {
        if (infoCheck) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScene!.fxml"));
                root = (Parent) loader.load();
signInController.client.setMainSceneController(loader.getController());
                System.out.println(loader
                        .getController().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }
    public void switchToSignUpScene(Event event) {
            try {
                root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

}

