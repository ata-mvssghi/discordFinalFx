package server;

import MessagePack.Message;
import client.Client;

//
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Save {
    public static ArrayList<Message>savedMessages=new ArrayList<>();
    public static void receiveMessagesFromServer(){
       // savedMessages=Server.getMessages();
    }


    public  static void save(){
        Save.receiveMessagesFromServer();
        System.out.println("saving");
        for (Message message:savedMessages){
            System.out.println("save:");
            System.out.println(message.getSender()+" : "+message.getText());
        }
        FileOutputStream fileOutputStream= null;
        try {
            fileOutputStream = new FileOutputStream("messages.bin");
            ObjectOutputStream outputStream=new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(savedMessages);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
