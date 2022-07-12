package com.example.demo;

import Group.Group;
import MessagePack.Message;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class chatController implements Initializable {
    private FileChooser fileChooser=new FileChooser();
    private  int messageIndex=0;
    private  int loadMessageIndex=0;
    private int hboxIndex=0;
    private int loadHboxIndex=0;
    @FXML
    VBox messagesVbox;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Label chatName;
    @FXML
    TextField text;
    public static int chatID;
    public static String chatLName;
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatName.setText(chatLName);
        for( Group group: signInController.client.groups){
            if(group.getId()==chatID){
                for(Message message:group.getMessages()){
                    HBox hBox=new HBox();
                    Label text=new Label();
                    text.setFont(new Font(15));
                    text.setText(message.getSender()+" : "+message.getText());
                    hBox.getChildren().add(text);
                    loadHboxIndex++;
                    Image image= null;
                    try {
                        image = new Image(new FileInputStream("C:\\Users\\SPINO.SHOP\\Desktop\\me my own\\the last version\\games_oop_javafx-master\\demo\\src\\main\\resources\\pics\\dots.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ImageView imageView=new ImageView();
                   imageView.setImage(image);
                   imageView.setFitHeight(10);
                   imageView.setFitWidth(5);
                    hBox.getChildren().add(imageView);
                    loadMessageIndex++;
                    messagesVbox.getChildren().add(hBox);
                    hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("reaction.fxml")));
                            try {
                                System.out.println("ssssssssss");
                                AnchorPane pane=loader.load();
                                pane.setLayoutX(mouseEvent.getX());
                                pane.setLayoutY(mouseEvent.getSceneY());
                                hboxIndex++;
                                loadHboxIndex++;
                                messagesVbox.getChildren().add(pane);
                                System.out.println("2222222");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }

    }
    public void send(Event event){
        String message=text.getText();
        HBox hBox=new HBox(15);
        Label line=new Label();
        line.setFont(new Font(15));
        line.setText(signInController.client.username+" : "+message);
        hBox.getChildren().add(line);
        Image image= null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\SPINO.SHOP\\Desktop\\me my own\\the last version\\games_oop_javafx-master\\demo\\src\\main\\resources\\pics\\dots.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView=new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(10);
        imageView.setFitWidth(5);
        hBox.getChildren().add(imageView);
        messagesVbox.getChildren().add(hBox);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("reaction.fxml")));
                try {
                    ReactionController.messageIndex=messageIndex;
                    for(Group group:signInController.client.groups){
                        if(group.getId()==chatID){
                            ReactionController.messages=group.getMessages();
                        }
                    }
                    AnchorPane pane=loader.load();
                    pane.setLayoutY(mouseEvent.getY());
                    pane.setLayoutX(mouseEvent.getX());
                    messagesVbox.getChildren().add(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            signInController.client.sendMSG(chatID,message);
            text.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void setMessagesVbox(HBox hBox){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messagesVbox.getChildren().add(hBox);
            }
        });

    }
    public void sendFile(Event event) throws IOException {
        fileChooser.setTitle("file picker");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files","*.*"));
        File file = fileChooser.showOpenDialog(null);
        if(file==null)  return;
        System.out.println("goy:"+file.getPath());
        try {
            signInController.client.outputStream.writeObject(new Message(signInController.client.username, file.getPath(), Message.Type.sendFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName ;
        String  temp=file.getPath();
        int last=temp.lastIndexOf('\\');
       fileName= temp.substring(last,temp.length());
        System.out.println("file aname;:     "+fileName);
        try {
            signInController.client.outputStream.writeObject(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
       HBox hBox=new HBox();
        hBox.setId(file.getPath());
        Label fileNameLabel=new Label();
        fileNameLabel.setText(fileName);
        ImageView filePic=new ImageView();
        Image image = new Image(new FileInputStream("C:\\Users\\SPINO.SHOP\\Desktop\\me my own\\the last version\\games_oop_javafx-master\\demo\\src\\main\\resources\\pics\\file.png"));
        filePic.setImage(image);
        filePic.setFitWidth(60);
        filePic.setFitHeight(60);
        hBox.getChildren().add(fileNameLabel);
        hBox.getChildren().add(filePic);
        HBox.setMargin(filePic,new Insets(5,5,5,5));
        HBox.setMargin(fileNameLabel,new Insets(5,5,5,10));
        messagesVbox.getChildren().add(hBox);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        File myFile = new File(file.getPath());
        byte[] mybytearray = new byte[(int) myFile.length()];
        fis = new FileInputStream(myFile);
        bis = new BufferedInputStream(fis);
        bis.read(mybytearray, 0, mybytearray.length);
        System.out.println("Sending " + "(" + mybytearray.length + " bytes)");
        signInController.client.outputStream.write(mybytearray, 0, mybytearray.length);
        signInController.client.outputStream.flush();
        System.out.println("Done.");
        filePic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    signInController.client. outputStream.writeObject(new Message(signInController.client.username, "", Message.Type.receiveFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    signInController.client.outputStream.writeObject(hBox.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("enter the path of that you want to receive file");
                String path="H:\\saved files\\"+fileName;
                String FILE_TO_RECEIVED = path;
                int FILE_SIZE = 6022386;
                int SOCKET_PORT = 7000;
                String SERVER = "127.0.0.1";
                int bytesRead;
                int current = 0;
                FileOutputStream fos = null;
                BufferedOutputStream bos = null;
                Socket sock = null;
                try {
                    try {
                        sock = new Socket(SERVER, SOCKET_PORT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Connecting...");

                    // receive file
                    byte[] mybytearray = new byte[FILE_SIZE];
                    InputStream is = null;
                    try {
                        is = sock.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos = new FileOutputStream(FILE_TO_RECEIVED);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bos = new BufferedOutputStream(fos);
                    try {
                        bytesRead = is.read(mybytearray, 0, mybytearray.length);
                        current = bytesRead;
                    do {
                        System.out.println(mybytearray);
                        System.out.println(current);
                        System.out.println(mybytearray.length - current);
                        try {
                            bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (bytesRead >= 0) current += bytesRead;
                    } while (bytesRead > -1);
                }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        bos.write(mybytearray, 0, current);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        bos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("File " + FILE_TO_RECEIVED
                            + " downloaded (" + current + " bytes read)");
                } finally {
                    try {
                        if (fos != null) fos.close();
                        if (bos != null) bos.close();
                        if (sock != null) sock.close();
                    }
                   catch (Exception e){
                        e.printStackTrace();
                   }
                }
            }
        });
    }
    public void pin(Event event){
    PinMessagesController.currentChatId=chatID;
            FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("pinnedMessages.fxml")));
            AnchorPane pane= null;
            try {
                pane = loader.load();
                signInController.client.mainSceneController.setChatPane(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void showPinned(Event event){
        try {
            root = FXMLLoader.load(getClass().getResource("showPinnded.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void react(Event event){
    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            System.out.println("goy we say");
            messagesVbox.getChildren().remove(hboxIndex);
            hboxIndex--;
            loadHboxIndex--;
        }
    });
    }
}
