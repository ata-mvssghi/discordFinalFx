package ServerChat;

import Group.Group;
import Role.Role;
import client.Client;
import client.ClientMenu;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ServerChat implements Serializable {
    @FXML
   public ImageView picture;
    ArrayList<Group> channels=new ArrayList<>();
    ArrayList<Client> users=new ArrayList<>();
    ArrayList<Role> roles=new ArrayList<>();
   public  HashMap<String ,ArrayList<String> > permissions = new HashMap<>();
    Client owner=null;
   public int id;
   public String imageAddress;
    private String ServerName;
    public ServerChat(Client owner, String serverName) {
        this.owner = owner;
        ServerName = serverName;
    }

    public void AddClient(Client client){
        users.add(client);
    }
    public void setServerName(String serverName) {
        ServerName = serverName;
    }
    public ArrayList<Group> getChannels() {
        return channels;
    }

    public ArrayList<Client> getUsers() {
        return users;
    }

    public Client getOwner() {
        return owner;
    }

    public String getServerName() {
        return ServerName;
    }
    public void serverChatMenu(){
        System.out.println(ANSI_YELLOW+"1-CREATE A NEW CHANNEL\n2-SHOW CHANNELS\n3-SET ROLE\n4-DELETE A CHANNEL\n5-DELETE A USER FROM SERVER\n6-BAN A USER\n7-CHANGE SERVER NAME\n8-Play a music\n9-go back to main menu"+ANSI_RESET);
    }
    public void addClients(Client client){
        this.users.add(client);
    }
    public void addToChannels(Group newChannel){
        channels.add(newChannel);
    }
    public void printChannels(){
        int i=0;
        for(Group channel:channels){
            System.out.println(i+"---" +channel.getName());
            i++;
        }
    }
    public void showServerChatClients(){
        int i=0;
        for(Client client:users){
            System.out.println(i+"--"+client.getUsername());
            i++;
        }
    }
    public void addRole(Role role){
        roles.add(role);
    }
    public  void addPermission(String name,ArrayList<String> permissionsOfIt){
        permissions.put(name,permissionsOfIt);
    }
    public boolean checkPermission(String  name ,String  permission){
       ArrayList<String> currPermissions= permissions.get(name);
       int flag=0;
       for(String temp:currPermissions){
           if(temp.equals(permission)){
               flag=1;
           }
       }
       if(flag==1){
           return true;
       }
       else return false;
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
