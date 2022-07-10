package server;

import MessagePack.Message;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Load {
    public static ArrayList<Message>loadedMessages=new ArrayList<>();

    public static ArrayList<Message> getMessages() {
        return loadedMessages;
    }

    public static void load(){
        try {
            FileInputStream fileInputStream = new FileInputStream("messages.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
           Load.loadedMessages = (ArrayList<Message>) objectInputStream.readObject();
           objectInputStream.close();
        }
        catch (Exception e){
            Save.save();
            Load.load();
        }

    }
}
