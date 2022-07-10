package Group;

import MessagePack.Message;
import client.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Group  implements Serializable {
     private ArrayList<Client> clients=new ArrayList<>();
     ArrayList<Message> messages=new ArrayList<>();
    private String name;
    private int id;
    private int serverChatId;
    transient FileOutputStream fileOutputStream;
    transient ObjectOutputStream objectOutputStream;
  transient   FileInputStream fileInputStream;
   transient ObjectInputStream objectInputStream;
   private ArrayList<Message> pinnedMessages=new ArrayList<>();

   public enum Type{
       privateChat,
       channel
   }
    private Type type;

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Group(String name, int id)  {
        this.name = name;
        this.id = id;

    }
    public void addClients(ArrayList<Client> receivedClients){
        for(Client client:receivedClients ){
            clients.add(client);
        }
    }
    public ArrayList<Client> getClients() {
        return clients;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    public ArrayList<Message> getMessages() {
        return messages;
    }
    public void addMessage(Message message){
        messages.add(message);
    }
    public void seeMessages(){
        for(Message message:messages){
            message.setSeenOrNot();
        }
    }
    public void reactToAMessage(){
        Scanner scanner=new Scanner(System.in);
        int i=0;
        for(Message message:messages){
            System.out.println(i+"--"+message.getSender()+" : "+message.getText());
            i++;
        }
        String reactChoice=scanner.nextLine();
        System.out.println("WHAT IS YOUR REACTION\n1-like\n2-dislike\n3-laughter");
        String react=scanner.nextLine();
            messages.get(Integer.valueOf(reactChoice)).setReact(Integer.valueOf(react));
    }
    public void pinAMessage(){
        Scanner scanner=new Scanner(System.in);
        int i=0;
        for(Message message:messages){
            System.out.println(i+"--"+message.getSender()+" : "+message.getText());
            i++;
        }
        String pinChoice=scanner.nextLine();
        messages.get(Integer.valueOf(pinChoice)).setPinned(true);
        System.out.println("Message pinned successfully");
    }
    public void showPinnedMessages() {
        int i = 1;
        for (Message message : messages) {
            if (message.isPinned()) {
                System.out.println(ANSI_BLUE+i+"----->"+message.getText()+ANSI_RESET);
            }
        }
    }
    public int countOfNotSeenMessages() {
        int count = 0;
        for (Message message : messages) {
            if (message.getSeenOrNot() == false)
                count++;
        }
        return count;
    }
    public synchronized void setServerChatId(int serverChatId) {
        this.serverChatId = serverChatId;
    }
    public void addToPinned(Message message){
        pinnedMessages.add(message);
    }
    public ArrayList<Message> getPinnedMessages(){
        return pinnedMessages;
    }
    public void setPinnedMessages(ArrayList<Message> pinnedMessages){
        this.pinnedMessages=pinnedMessages;
    }
    public int getServerChatId() {
        return serverChatId;
    }
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
