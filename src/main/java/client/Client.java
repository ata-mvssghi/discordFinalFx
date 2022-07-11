package client;
//show server moshkel dare chand ta neshoon mideh ke yekish tempclient hastesh:|
//بعد انتخاب سرور گیر میکنه
//برای بقیه تمپ کلاینت میده تو اونر سرور4
//برای بقیه اد نمیشه چنله


import FriendShipRequest.FriendShipReq;
import Group.Group;
import MessagePack.Message;
import Request.Request;
import Response.Response;
import ServerChat.ServerChat;
import com.example.demo.newServerCreateController;
import com.example.demo.MainSceneController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import com.example.demo.chatController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import com.example.demo.signInController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import server.*;

//import javax.print.attribute.standard.Media;
//import javax.swing.*;
//import java.beans.Transient;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements Serializable {
    transient Scanner scanner = new Scanner(System.in);
    transient Scanner scanner1 = new Scanner(System.in);
    private String ip;
    @FXML
     public static HBox newServer;
    @FXML
  transient public String image;
   public boolean confirmPass;
   public MainSceneController mainSceneController;
   public chatController chatController1;
   public void setMainSceneController(MainSceneController mainSceneController){
       this.mainSceneController=mainSceneController;
   }
   public void setChatController1(chatController chatController1){
       this.chatController1=chatController1;
   }
    public String phoneNumber;
    private int port;
     public String username;
    public String email;
    public String password;
    public static String imageAddress;
    private ArrayList<Message> exMessages = new ArrayList<>();
   public ArrayList<Client> clients = new ArrayList<>();
   public ArrayList<Client> friends = new ArrayList<>();
   public ArrayList<Client> serverClients = new ArrayList<>();
  public   ArrayList<FriendShipReq> friendShipReqs = new ArrayList<>();
   public  static ArrayList<ServerChat> serversChats=new ArrayList<>();
//    Media media = new Media(Paths.get(path).toUri().toString());
//    MediaPlayer mediaPlayer = new MediaPlayer(media);
    boolean signedIn = false;
    boolean signedUp = false;
    public int serverGroupIndex;
    public int serverChatIndex;
    ClientMenu clientMenu = new ClientMenu();
  public   ArrayList<Group> groups = new ArrayList<>();
    ClientMenu menu = new ClientMenu();
    ArrayList<String> linkOfFile = new ArrayList<>();
    public transient ObjectOutputStream outputStream;
    public transient ObjectInputStream objectInputStream;
    String ServerResponseToUsername;
    String ServerResponseToPassword;
    String ServerResponseToEmail;
    Client ServerResponseToClient;
    int serverPort;
    Group currentGroup=null;
    private Status status;
    public boolean creatChannel=false;
    public boolean deleteChannel=false;
    public boolean deleteClientFromServer=false;
    public boolean limitateAccess=false;
    public boolean banning=false;
    public boolean changeServerName=false;
    public boolean observeHistory=false;
    public boolean pin=false;
    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public Client getServerResponseToClient() {
        return ServerResponseToClient;
    }

    public void setServerResponseToUsername(String serverResponseToUsername) {
        ServerResponseToUsername = serverResponseToUsername;
    }

    public void setServerResponseToClient(Client serverResponseToClient) {
        ServerResponseToClient = serverResponseToClient;
    }

    public void setServerResponseToPassword(String serverResponseToPassword) {
        ServerResponseToPassword = serverResponseToPassword;
    }
    public void setServerResponseToEmail(String getServerResponseToEmail) {
        this.ServerResponseToEmail = getServerResponseToEmail;
    }
    public enum Status {
        online,
        offline,
        doNotDisturb,
        Invisible,
        Idle
    }

    public Client(String ip, int port, String username, String password, String email) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    //    public void loadExMessages() {
//        Load.load();
//        exMessages = Load.getMessages();
//    }
    public void printPreviousMessages() {
        for (Message message : exMessages) {
            System.out.println(message.getSender() + " : " + message.getText());
        }
    }

    public ArrayList<Client> loadClient(ArrayList<Client> c) {
        try {
            FileInputStream fileInputStream = new FileInputStream("clients.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            c = (ArrayList<Client>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (Exception e) {
            Save.save();
            Load.load();
        }


        for (Client client : c
        ) {
            System.out.println(client.getUsername());

        }
        return c;
    }


    public void startClient() {
        try {
            Socket socket = new Socket(ip, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            Listener listener = new Listener(objectInputStream);
            Thread listen = new Thread(listener);
            listen.start();
            printPreviousMessages();
            Scanner in = new Scanner(System.in);
            this.setStatus(Status.online);
            outputStream.writeObject(new Message(username,"online",Message.Type.statusReq));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendJoin(){
        try {
            outputStream.writeObject(new Message(username,"sdf", Message.Type.join));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getServerResponseToUsername() {
        return ServerResponseToUsername;
    }

    public String getServerResponseToPassword() {
        return ServerResponseToPassword;
    }

    public String getServerResponseToEmail() {
        return ServerResponseToEmail;
    }

    public void initialMenu() {
        System.out.println("1-sign in\n2-sign up");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        scanner.nextLine();
        if (input == 1) {
            try {
                outputStream.writeObject(new Request(Request.Type.signIn, "sign in request"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String username1;
            while (true) {
                System.out.println("enter the username");
                 username1 = scanner.nextLine();
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
                    outputStream.writeObject(username1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String contain = getServerResponseToUsername();
                if (contain.equals("exists")) {
                    break;
                }
                if (contain.equals("notExist")) {
                    System.out.println("username is not exist try again");
                }
            }
            String password1;
            setUsername(username1);
            while (true) {
                System.out.println("enter the password");
                 password1 = scanner.nextLine();
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
                    outputStream.writeObject(password1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String contain = getServerResponseToPassword();
                if (contain.equals("exists")) {
                    break;
                }
                if (contain.equals("notExist")) {
                    System.out.println("password is not exist");
                    initialMenu();
                }
            }
            while (true) {
                System.out.println("enter the email");
                String email1 = scanner.nextLine();
               /* while (true) {
                    if (emailCheck(email1)) {
                        break;
                    } else if (!emailCheck(email1)) {
                        System.out.println("invalid input try again");
                        email1 = scanner.nextLine();
                    }
                }*/
                try {
                    outputStream.writeObject(email1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String contain = getServerResponseToEmail();
                if (contain.equals("exists")) {
                    break;
                }
                if (contain.equals("notExist")) {
                    System.out.println("email is not exist");
                    initialMenu();
                }
            }
            String path = "C:\\Users\\SPINO.SHOP\\Desktop\\DiscorMHDMLAst - Laterversion\\New-Discord\\src\\ClientSave\\";
            FileInputStream fileInputStream = null;
            System.out.println(username);
            try {
                fileInputStream = new FileInputStream(path + username + ".bin");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Client client = null;
            try {
                ObjectInputStream objectInput = new ObjectInputStream(fileInputStream);
                client = (Client) objectInput.readObject();
                set(client);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            this.signedIn = true;
            //////////////////////////////////SIGN UP////////////////////////////////////////
        } else if (input == 2) {
            try {
                outputStream.writeObject(new Request(Request.Type.signUp, "sign up request"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String username1;
            while (true) {
                System.out.println("enter the username");
                username1 = scanner.nextLine();
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
                    outputStream.writeObject(username1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String contain = getServerResponseToUsername();
                if (contain.equals("exist")) {
                    System.out.println("username is exist(tekrari)");
                }
                if (contain.equals("notExist")) {
                    break;
                }
            }

            System.out.println("enter the password");
            String password1 = scanner.nextLine();
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
            String email1 = scanner.nextLine();
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
                setUsername(username1);
                setPassword(password1);
                setEmail(email1);
                outputStream.writeObject(new Client("127.0.0.1", 6050, username1, password1, email1));
                outputStream.writeObject(new Message(username1, "", Message.Type.join));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public void setUsername(String  username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setEmail(String email){
        this.email=email;
    }
    private class Listener implements Runnable {
        ObjectInputStream inputStream;

        public Listener(ObjectInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object respons = inputStream.readObject();
                    if (respons instanceof Message) {
                        Message message = (Message) respons;
//                        if (message.getType() == Message.Type.join) {
//                            System.out.println(ANSI_BLUE + message.getSender() + " " + message.getText() + ANSI_RESET);
//                        }
                       if (message.getType() == Message.Type.exit) {
                            System.out.println(ANSI_RED + message.getSender() + " " + message.getText() + ANSI_RESET);
                        } else if (message.getType() == Message.Type.text) {
                           Group tobeAdded=null;
                           for(Group group:signInController.client.groups){
                               if(group.getId()==message.getGroupIndex()){
                                   tobeAdded=group;
                               }
                           }
                            tobeAdded.addMessage(message);
                            //group.save();
                           if(!(message.getSender().equals(username))) {
                               HBox hBox = new HBox();
                               hBox.setAlignment(Pos.BASELINE_LEFT);
                               Label label = new Label("Red");
                               label.setFont(new Font(15));
                               label.setText(message.getSender() + " : " + message.getText());
                               hBox.getChildren().add(label);
                               signInController.client.chatController1.setMessagesVbox(hBox);
                           }
                            if (!(message.getSender().equals(username))) {
                                if(currentGroup!=null&&currentGroup.getId()==tobeAdded.getId())
                                System.out.println(message.getSender() + " : " + message.getText());
                                else
                                    System.out.println(ANSI_PURPLE+" NOTIFICATION!!"+ANSI_RESET);
                            }
                        } else if (message.getType() == Message.Type.groupIndex) {
                            serverGroupIndex = Integer.valueOf(message.getText());
                        }
                       else if(message.getType()== Message.Type.passChangeRes){
                           outputStream.writeObject(new Message(username,"kjhgfd", Message.Type.join));
                           System.out.println("hgfdsasdfghjkl;11111111");
                           if(message.getText().equals("true")){
                               confirmPass=true;
                           }
                           else confirmPass=false;
                       }

                       else if(message.getType()== Message.Type.servetChatIndex){
                            System.out.println("got the server chat index: "+message.getText());
                         signInController.client.serverChatIndex=Integer.valueOf(message.getText());
                        }
                        else if (message.getType() == Message.Type.friendReqAccepted) {
                            for (Client client : serverClients) {
                                if (client.getUsername().equals(message.getText())) {
                                     signInController.client.friends.add(client);
                                }
                            }
                        } else if (message.getType() == Message.Type.friendReq) {
                            showServerClients();
                            System.out.println(username);
                            System.out.println(ANSI_BLUE + message.getText() + " WANTS TO BE YOUR FRIEND" + ANSI_RESET);
                            Client originc = null;
                            Client targetc = null;
                            for (Client client : serverClients) {
                                if (client.getUsername().equals(message.getText())) {
                                    originc = client;
                                } else if (client.getUsername().equals(username)) {
                                    targetc = client;
                                }
                            }
                            System.out.println("target"+targetc.getUsername());
                            System.out.println("origin c "+originc.getUsername()+"target"+targetc.getUsername());
                           signInController.client.friendShipReqs.add(new FriendShipReq(message.getText(), username, "request"));
                           System.out.println("successfully added to saves firends");
                           for(FriendShipReq friendShipReq:friendShipReqs){
                               System.out.println(friendShipReq.toString());
                           }
                        }
                        else if (message.getType() == Message.Type.serverPort) {
                            serverPort = Integer.parseInt(message.getText());
                        }
                        else  if(message.getType()== Message.Type.tag){
                           System.out.println(ANSI_GREEN+message.getText()+" MENTIONED YOU IN A CHAT"+ANSI_RESET);
                       }
                        else if(message.getType()== Message.Type.deleteAChannelRes){
                            Group temp=(Group) inputStream.readObject();
                           Iterator<Group> groupIterator=groups.iterator();
                           while (groupIterator.hasNext()){
                               Group currentGroup=groupIterator.next();
                               if(currentGroup.getId()==temp.getId()){
                                   groupIterator.remove();
                               }
                           }
                           Iterator<ServerChat> serverChatIterator=serversChats.iterator();
                           ServerChat targetServer=null;
                           while (serverChatIterator.hasNext()){
                               ServerChat currentServerChat=serverChatIterator.next();
                               if(currentServerChat.id==temp.getServerChatId()){
                                  targetServer=currentServerChat;
                               }
                           }
                           Iterator<Group> groupsOfThisServer=targetServer.getChannels().iterator();
                           while(groupsOfThisServer.hasNext()){
                               Group currentGroup =groupsOfThisServer.next();
                               if(currentGroup.getId()==temp.getId()){
                                   groupsOfThisServer.remove();
                                   System.out.println(ANSI_RED+"YOU WERE DELETED FROM A CHAT"+ANSI_RESET);
                               }
                           }
                        }
                        else if(message.getType()== Message.Type.serverNewName){
                            ServerChat target=(ServerChat) inputStream.readObject();
                            for(ServerChat serverChat:serversChats){
                                if(serverChat.getServerName().equals(target.getServerName())){
                                    serverChat.setServerName(message.getText());
                                    System.out.println(ANSI_CYAN+"server's name changed successfully"+ANSI_RESET);
                                    break;
                                }
                            }
                       }
                        else if(message.getType()== Message.Type.pin){
                            int targetId=(Integer)inputStream.readObject();
                           System.out.println("558");
                            for(Group group:signInController.client.groups){
                                System.out.println("560");
                                if (group.getId()==targetId){
                                    System.out.println("562");
                                    for(Message message1:group.getMessages()){
                                        System.out.println("564");
                                        if(message1.getText().equals(message.getText())){
                                            System.out.println("566");
                                            message1.setPinned(true);
                                            break;
                                        }
                                    }
                                }
                            }
                       }
                        else if(message.getType()== Message.Type.react){
                            int targetGroupId=(Integer)inputStream.readObject();
                            String  reaction=(String)inputStream.readObject();
                            for(Group group:groups){
                                if(group.getId()==targetGroupId){
                                    for(Message message1:group.getMessages()){
                                        if(message1.getText().equals(message.getText())){
                                            message1.setReact(Integer.valueOf(Integer.valueOf(reaction)));
                                        }
                                    }
                                }
                           }
                       }
                        else if(message.getType()== Message.Type.linkOfDownload){
                            linkOfFile=(ArrayList<String>) inputStream.readObject();
                       }
                        else if(message.getType()== Message.Type.deleteAUserRes){
                            Client clientToBeDeleted=(Client) inputStream.readObject();
                            ServerChat targetServerChat=(ServerChat) inputStream.readObject();
                            ServerChat temp=null;
                            for(ServerChat serverChat:serversChats){
                                if(serverChat.id==targetServerChat.id){
                                    temp=serverChat;
                                }
                            }
                            if(clientToBeDeleted.getUsername().equals(username)){
                                System.out.println("you are removed from the server : "+targetServerChat.getServerName());
                                Iterator<ServerChat> serverChatIterator = serversChats.iterator();
                                while (serverChatIterator.hasNext()){
                                  ServerChat serverChatToBeDeleted=  (ServerChat) serverChatIterator.next();
                                  if(serverChatToBeDeleted.getServerName().equals(targetServerChat.getServerName())){
                                      serverChatIterator.remove();
                                  }
                                }
                            }
                           Iterator<Client> clientIterator=temp.getUsers().iterator();
                            while (clientIterator.hasNext()){
                                Client target=(Client) clientIterator.next();
                                if(target.getUsername().equals(clientToBeDeleted.getUsername())){
                                    clientIterator.remove();
                                    System.out.println("a client is deleted");
                                }
                            }
                       }
                    } else if (respons instanceof ArrayList<?>) {
                        System.out.println("adaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        ArrayList<Client> temp = (ArrayList<Client>) respons;
                        signInController.client.setServerClients(temp);
                        setServerClients(temp);
                    } else if (respons instanceof Client) {
                        setServerResponseToClient((Client) respons);
                        System.out.println("client port" + ((Client) respons).serverPort);
                    } else if (respons instanceof Response) {
                        Response responseFromServer = (Response) respons;
                        if (responseFromServer.getType() == Response.Type.signIn) {
                            System.out.println("haaaaa darukl./");
                            if (responseFromServer.getText().equals("username exists")) {
                                setServerResponseToUsername("exists");
                            } else if (responseFromServer.getText().equals("username doesn't exist")) {
                                setServerResponseToUsername("notExist");
                            } else if (responseFromServer.getText().equals("password exists")) {
                                setServerResponseToPassword("exists");
                            } else if (responseFromServer.getText().equals("password doesn't exists")) {
                                setServerResponseToPassword("notExist");
                            } else if (responseFromServer.getText().equals("email exists")) {
                                setServerResponseToEmail("exists");
                            } else if (responseFromServer.getText().equals("email doesn't exist")) {
                                setServerResponseToEmail("notExist");
                            }


                        } else if (responseFromServer.getType() == Response.Type.signup) {
                            if (responseFromServer.getText().equals("username exists")) {

                                setServerResponseToUsername("exists");
                            } else if (responseFromServer.getText().equals("username doesn't exist")) {
                                setServerResponseToUsername("notExist");
                            }
                        }

                    } else if (respons instanceof Group) {
                        System.out.println("YOU ARE ADDED TO A NEW CHAT");
                        if(((Group) respons).getType()== Group.Type.channel){
                            for(ServerChat serverChat:serversChats){
                                if(serverChat.id==((Group)respons).getId()){
                                    serverChat.addToChannels((Group)respons);
                                    System.out.println("you are added to a new channel");
                                }
                            }
                        }
                        signInController.client.groups.add((Group) respons);
                    }
                    else if(respons instanceof ServerChat){
                        System.out.println("YOU ARE ADDED TO A NEW SERVER CHAT WITH NAME "+((ServerChat)respons).getServerName());
                        int flag=0;
                        for(ServerChat serverChat:serversChats){
                            if(serverChat.id==((ServerChat)respons).id){
                                flag=1;
                            }
                        }
                        if (flag==0)
                        signInController.client.serversChats.add((ServerChat) respons);
                    }
                } catch (SocketException e) {
                    System.out.println("server lost!!");
                    System.exit(0);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getUsername() {
        return username;
    }
    public boolean emailCheck(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            System.out.println("invalid input");
            return false;
        } else return true;
    }

    public static boolean checkPassword(String password) {
        String pattern = "\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\"";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(password);
        if (matcher.matches()) return true;
        else return false;
    }

    public boolean checkUsername(String username) {
        String pattern = "\"^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$\"";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(username);
        if (matcher.matches()) return true;
        else return false;
    }

    public void secondMenu() throws IOException, ClassNotFoundException {
        menu.secondaryMenue();
        System.out.println("enter your choice");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            try {
                createNewGroup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (choice.equals("2")) {
            showGroupList();
            String groupChoice = scanner.nextLine();
            try {
                ArrayList<Message> groupMessages = groups.get(Integer.valueOf(groupChoice)).getMessages();
                for (Message message : groupMessages) {
                    System.out.println(message.toString());
                }
                currentGroup=groups.get(Integer.valueOf(groupChoice));
               // sendMSG(groups.get(Integer.valueOf(groupChoice)).getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (choice.equals("3")) {
            showFriends();
            secondMenu();
        } else if (choice.equals("4")) {
            try {
                createFriendShipRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (choice.equals("5")) {
            showFriendShipReqs();
            secondMenu();
        } else if (choice.equals("6")) {
            System.out.println("1-do not disturb\n2-IDL\n3-Invisible\n");
            String statusChoice = scanner.nextLine();
            if (statusChoice.contains("1")) {
                setStatus(Status.doNotDisturb);
                outputStream.writeObject(new Message(username, "doNotDisturb", Message.Type.statusReq));
            }

            else if (statusChoice.contains("2")){
                setStatus(Status.Idle);
                outputStream.writeObject(new Message(username, "IDLE", Message.Type.statusReq));
            }
            else if (statusChoice.contains("3")) {
                setStatus(Status.Invisible);
                outputStream.writeObject(new Message(username, "INVISIBLE", Message.Type.statusReq));
            }
            secondMenu();

        }
        else if(choice.equals("7")){
            thirdMenu();
        }
        else if (choice.equals("8")) {
            String path = "C:\\Users\\SPINO.SHOP\\Desktop\\DiscorMHDMLAst - Laterversion\\New-Discord\\src\\ClientSave\\";
            FileOutputStream fileOutputStream = new FileOutputStream(path + username + ".bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            System.out.println("if you want to close program enter 1");
            System.out.println("if you want to sign in other account enter 2");
            int input= scanner.nextInt();
            if(input==1){
                System.exit(0);
            }else if (input==2){
                initialMenu();
                secondMenu();
            }
        }
        else {
            System.out.println("invalid input");
            secondMenu();
        }

    }
    public void thirdMenu(){
        serverChatMenu();
        String menuC=scanner.nextLine();
        if(menuC.equals("1")){
        createNewServer();
        thirdMenu();
        }
        else if(menuC.equals("2")) {
            showServerChats();
            System.out.println("WHICH?(-1 to go back to the main menu)");
            String serverChoice = scanner.nextLine();
            if (serverChoice.equals("-1")) {
                try {
                    secondMenu();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            ServerChat serverChat = serversChats.get(Integer.valueOf(serverChoice));
            serverChat.serverChatMenu();
            System.out.println("WHICH?");
            String serverMenuCh = scanner.nextLine();
            if (serverMenuCh.equals("1")) {
                try {
                    try {
                        //if(serverChat.checkPermission(username,"create a new channel")){
                        createNewChannel(serverChat);
                        thirdMenu();
                      //  }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (serverMenuCh.equals("2")) {
                serverChat.printChannels();
                System.out.println("WHICH CHANNEL?");
                String groupChoice = scanner.nextLine();
                try {
                    ArrayList<Message> groupMessages = serverChat.getChannels().get(Integer.valueOf(groupChoice)).getMessages();
                    for (Message message : groupMessages) {
                        System.out.println(message.toString());
                    }
                    currentGroup=groups.get(Integer.valueOf(groupChoice));
                   // sendMSG(serverChat.getChannels().get(Integer.valueOf(groupChoice)).getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(serverMenuCh.equals("3")){
                if(serverChat.getOwner().getUsername().equals(username)){
                    forthMenu(serverChat);
                }
                else System.out.println(ANSI_RED+"YOU AIN'T GOT THE PERMISSION TO SET ROLES CAUSE YOU RE NOT THE ADMIN"+ANSI_RESET);
            }
            else if(serverMenuCh.equals("4")){
                int i=0;
                for(Group channel:serverChat.getChannels()){
                    System.out.println("-----------------------");
                    System.out.println(i+"--->"+channel.getName());
                    System.out.println("------------------------");
                }
                System.out.println("which channel to delete?");
                String deleteChoice=scanner.nextLine();
                try {
                    outputStream.writeObject(new Message(username,"want to delete a channel", Message.Type.deleteAChannelReq));
                    outputStream.writeObject(serverChat.getChannels().get(Integer.valueOf(deleteChoice)));
                    System.out.println("733");
                    serverChat.getChannels().remove(Integer.valueOf(deleteChoice));
                    try {
                        secondMenu();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(serverMenuCh.equals("5")){
                int i=0;
                for(Client client:serverChat.getUsers()){
                    System.out.println("--------------------------");
                    System.out.println(i+"--->"+client.getUsername());
                    System.out.println("---------------------------");
                    i++;
                }
                System.out.println("which?");
                String clientDelChoice=scanner.nextLine();
                serverChat.getUsers().get(Integer.valueOf(clientDelChoice));
                try {
                    outputStream.writeObject(new Message(username,"delete a username req", Message.Type.deleteAUserReq));
                   outputStream.writeObject(serverChat.getUsers().get(Integer.valueOf(clientDelChoice)));
                   outputStream.writeObject(serverChat);
                    try {
                        secondMenu();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(serverMenuCh.equals("8")){
                String mainPath="C:\\Users\\SPINO.SHOP\\Desktop\\DiscorMHDMLAst - Laterversion\\New-Discord\\src\\Musics";
                File file=new File(mainPath);
                String[] pathes=file.list();
                for(int i=0;i<pathes.length;i++){
                    System.out.println("****************");
                    System.out.println(i+"--->"+pathes[i]);
                    System.out.println("****************");
                }
                System.out.println("which?");
                String choice=scanner.nextLine();
                String path=mainPath+pathes[Integer.valueOf(choice)];
                try {
                    outputStream.writeObject(new Message(username,path, Message.Type.playMusic));
                    outputStream.writeObject(serverChat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(serverMenuCh.equals("7")){
                System.out.println("ENTER THE NEW NAME FOR THE SERVER");
                String newName=scanner.nextLine();
                try {
                    outputStream.writeObject(new Message(username,newName, Message.Type.serverNewName));
                    outputStream.writeObject(serverChat);
                        secondMenu();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (serverMenuCh.equals("9")){
                try {
                    secondMenu();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void deleteAUser(ServerChat serverChat){
        int i=0;
        for(Client client:serverChat.getUsers()){
            System.out.println("******************");
            System.out.println(i+"---->"+client.getUsername());
            System.out.println("******************");
        }
        System.out.println("WHICH USER TO DELETE?");
        String delC=scanner.nextLine();
    }
    public void forthMenu(ServerChat serverChat){
        int i=0;
                for(Client client:serverChat.getUsers()){
                    System.out.println("*********************");
                    System.out.println(i+"---> "+client.getUsername());
                    System.out.println("**********************");
                    i++;
                }
        System.out.println("WHICH?");
                String choice=scanner.nextLine();
                String user=serverChat.getUsers().get(Integer.valueOf(choice)).getUsername();
                roleMenu();
                ArrayList<String> permissions=new ArrayList<>();
                String permissionChoice;
                while (true){
                    System.out.println("Which?(-1 to finish)");
                    permissionChoice=scanner.nextLine();
                    if(permissionChoice.equals("1")){
                        permissions.add("create a new channel");
                    }
                    else if(permissionChoice.equals("2")){
                        permissions.add("delete a channel");
                    }
                    else if(permissionChoice.equals("3")){
                        permissions.add("delete a user from server");
                    }
                    else if(permissionChoice.equals("4")){
                        permissions.add("limit the access");
                    }
                    else if(permissionChoice.equals("5")){
                        permissions.add("ban");
                    }
                    else if(permissionChoice.equals("6")){
                        permissions.add("change server name");
                    }
                    else if(permissionChoice.equals("7")){
                        System.out.println("see previous messages");
                    }
                    else if(permissionChoice.equals("8")){
                        System.out.println("pin");
                    }
                    else if(permissionChoice.equals("-1")){
                        break;
                    }
                }
                serverChat.addPermission(user,permissions);
        try {
            secondMenu();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
           public void roleMenu(){
               System.out.println(ANSI_YELLOW+"1-Create new Channel \n2-DELETE A CHANNEL\n3-DELETE A MEMMBER FROM SERVER\n4-DELETE A MEMMBER FROM CHANNEL\n6-CHANGE SERVER NAME\n7-SEE PREVIOUS MESSAGES\n8-PIN A MESSAGE");
           }
    public void createNewServer(){
        System.out.println("ENTER A NAME FOR SERVER");
        String serverN=scanner.nextLine();
        System.out.println("ENTER THE TYPE OF THE SERVER\n1-TEXT\n2-VOICE");
        String type=scanner.nextLine();
        if(type.equals("1")){
            ServerChat serverChat=new ServerChat(this  ,serverN);
//            serversChats.add(serverChat);
            try {
                outputStream.writeObject(new Message(username,"'nothing", Message.Type.allClients));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i=0;
            for(Client client:serverClients){
                System.out.println("----------------------" );
                System.out.println(i+"---"+client.getUsername());
                System.out.println("-----------------------");
                i++;
            }
            ArrayList<Client> serverChatClients=new ArrayList<>();
            while (true){
                System.out.println("CHOOSE(-1 TO FINISH)");
                String index=scanner.nextLine();
                if(index.equals("-1")){
                    break;
                }
                serverChatClients.add(serverClients.get(Integer.valueOf(index)));
            }
            //serverChat.addClients(serverChatClients);
            try {
                outputStream.writeObject(new Message(username,"attemping to get server index", Message.Type.servetChatIndex));
                serverChat.id=serverChatIndex;
                System.out.println("700 index: "+serverChatIndex);
                outputStream.writeObject(serverChat);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(ANSI_BLUE+"NEW SERVER CREATED WITH NAME--> "+serverChat.getServerName()+ANSI_RESET);
        }
        else if(type.equals("2")){
            try {
                secondMenu();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void showServerChats(){
        int i=0;
        for(ServerChat serverChat:serversChats){
            System.out.println("********************");
            System.out.println(i+" --- "+serverChat.getServerName()+"   OWNER: "+serverChat.getOwner().getUsername());
            System.out.println("**********************");
            i++;
        }
    }
    public void createNewGroup() throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("enter a name for group");
        String groupName = scanner.nextLine();
        outputStream.writeObject(new Message(username,"getting all clients", Message.Type.allClients));
        outputStream.writeObject(new Message(username, "groupIndex", Message.Type.groupIndex));
        outputStream.writeObject(new Message(username, groupName, Message.Type.createNewGroupRequest));
        int i = 0;
        Thread.sleep(2000);
        for (Client client : serverClients) {
            System.out.println(i + "- " + client.toString());
            i++;
        }
        ArrayList<Client> chosenClients = new ArrayList<>();
        while (true) {
            System.out.println("choose(-1 to end)");
            int temp = scanner.nextInt();
            if (temp == -1) {
                scanner.nextLine();
                break;
            }
            chosenClients.add(serverClients.get(temp));
        }
        Group newGroup = new Group(groupName, serverGroupIndex);
        newGroup.setType(Group.Type.privateChat);
        System.out.println("goy we see the index" + serverGroupIndex);
        newGroup.addClients(chosenClients);
        System.out.println(ANSI_BLUE + "group created successfully" + ANSI_RESET);
        outputStream.writeObject(newGroup);
        Thread.sleep(1000);
        secondMenu();
    }

    public void setServerClients(ArrayList<Client> serverClients) {
        this.serverClients = serverClients;
    }

    public ArrayList<Client> getServerClients() {
        return serverClients;
    }

    public void sendMSG(int groupId,String text) throws IOException, ClassNotFoundException {

            if (text.equalsIgnoreCase("/exit")) {
                outputStream.writeObject(new Message(username, "", Message.Type.exit));
            }
            else if (text.equalsIgnoreCase("/back")) {
                groups.get(groupId).seeMessages();
                secondMenu();
            }
            else if (text.equalsIgnoreCase("/react")) {
                for(Group group :groups){
                    if(group.getId()==groupId){
                        int i=0;
                      for(Message message:group.getMessages()){
                          System.out.println(i+"-->"+message.getSender()+" : "+message.getText());
                          i++;
                      }
                        System.out.println("which?");
                      String reactChoice=scanner.nextLine();
                      String messageToBeReacted=group.getMessages().get(Integer.valueOf(reactChoice)).getText();
                        System.out.println("1-like\n2-dislike\n3-laughter");
                        String reaction=scanner.nextLine();
                        System.out.println(ANSI_CYAN+"your reaction saved successfully"+ANSI_RESET);
                        outputStream.writeObject(new Message(username,messageToBeReacted, Message.Type.react));
                        outputStream.writeObject(group);
                        outputStream.writeObject(reaction);
                        break;
                    }
                }
            }
            else if (text.equals("/File")) {
                System.out.println("enter the address of file that you want to send");
                String addressFile = scanner.nextLine();
                outputStream.writeObject(new Message(username, addressFile, Message.Type.sendFile));
                System.out.println("enter the name of file that you want to send");
                String fileName = scanner.nextLine();
                outputStream.writeObject(fileName);
                outputStream.writeObject(groupId);
                ///
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                File myFile = new File(addressFile);
                byte[] mybytearray = new byte[(int) myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray, 0, mybytearray.length);
                System.out.println("Sending " + "(" + mybytearray.length + " bytes)");
                outputStream.write(mybytearray, 0, mybytearray.length);
                outputStream.flush();
                System.out.println("Done.");
            }
            else if (text.equals("/download")) {
                outputStream.writeObject(new Message(username, "", Message.Type.receiveFile));
                outputStream.writeObject(groupId);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i=0;
                for(String link:linkOfFile){
                    System.out.println(i+"-->"+link);
                }
                System.out.println("enter the address of file that you want to receive");
                String address = scanner.nextLine();
                outputStream.writeObject(address);
                System.out.println("enter the path of that you want to receive file");
                String path=scanner.nextLine();
                String FILE_TO_RECEIVED = path;
                int FILE_SIZE = 6022386;
                int SOCKET_PORT = 13267;
                String SERVER = "127.0.0.1";
                int bytesRead;
                int current = 0;
                FileOutputStream fos = null;
                BufferedOutputStream bos = null;
                Socket sock = null;
                try {
                    sock = new Socket(SERVER, SOCKET_PORT);
                    System.out.println("Connecting...");

                    // receive file
                    byte[] mybytearray = new byte[FILE_SIZE];
                    InputStream is = sock.getInputStream();
                    fos = new FileOutputStream(FILE_TO_RECEIVED);
                    bos = new BufferedOutputStream(fos);
                    bytesRead = is.read(mybytearray, 0, mybytearray.length);
                    current = bytesRead;
                    do {
                        System.out.println(mybytearray);
                        System.out.println(current);
                        System.out.println(mybytearray.length - current);
                        bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                        if (bytesRead >= 0) current += bytesRead;
                    } while (bytesRead > -1);

                    bos.write(mybytearray, 0, current);
                    bos.flush();
                    System.out.println("File " + FILE_TO_RECEIVED
                            + " downloaded (" + current + " bytes read)");
                } finally {
                    if (fos != null) fos.close();
                    if (bos != null) bos.close();
                    if (sock != null) sock.close();
                }
            }
            else if(text.contains("@")){
                String[] arrayOfString = text.split("\\s+");
                String target=null;
                for(String st:arrayOfString){
                    if(st.contains("@")){
                        target=st;
                    }
                }
                String splitted=target.substring(1,target.length());
                System.out.println("tagged : "+splitted);
                outputStream.writeObject(new Message(username,splitted, Message.Type.tag));
                outputStream.writeObject(new Message(username,text, Message.Type.text));
            }
            else if(text.equals("/pin")){
                Group target=null;
                for(Group group:groups){
                    if(group.getId()==groupId){
                        target=group;
                        break;
                    }
                }
                Scanner scanner=new Scanner(System.in);
                int i=0;
                for(Message message:target.getMessages()){
                    System.out.println(i+"--"+message.getSender()+" : "+message.getText());
                    i++;
                }
                String pinChoice=scanner.nextLine();
                text=target.getMessages().get(Integer.valueOf(pinChoice)).getText();
                target.getMessages().get(Integer.valueOf(pinChoice)).setPinned(true);
                System.out.println("Message pinned successfully");
                outputStream.writeObject(new Message(username,text, Message.Type.pin));
                outputStream.writeObject(target);
            }
            else if(text.equals("/show pin")){
                for(Group group:groups){
                    if(group.getId()==groupId){
                        group.showPinnedMessages();
                    }
                }
            }
            else {
                Message message = new Message(username, text, Message.Type.text);
                message.setGroupIndex(groupId);
                outputStream.writeObject(message);
            }

    }
    public void serverChatMenu(){
        System.out.println("1-CREATE A NEW Server\n2-SHOw Servers");
    }
    public void   createNewChannel(ServerChat serverChat) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("enter a name for the channel");
        String groupName = scanner.nextLine();
        outputStream.writeObject(new Message(username, "groupIndex", Message.Type.groupIndex));
        outputStream.writeObject(new Message(username, groupName, Message.Type.createNewGroupRequest));
        int i = 0;
        Thread.sleep(2000);
        for (Client client : serverChat.getUsers()) {
            System.out.println(i + "- " + client.toString());
            i++;
        }
        ArrayList<Client> chosenClients = new ArrayList<>();
        while (true) {
            System.out.println("choose(-1 to end)");
            int temp = scanner.nextInt();
            if (temp == -1) {
                scanner.nextLine();
                break;
            }
            chosenClients.add(serverChat.getUsers().get(temp));
        }
        Group newGroup = new Group(groupName, serverGroupIndex);
        newGroup.setType(Group.Type.channel);
        newGroup.setServerChatId(serverChat.id);
        newGroup.addClients(chosenClients);
        System.out.println(ANSI_BLUE + "channel created successfully" + ANSI_RESET);
        outputStream.writeObject(newGroup);
        Thread.sleep(1000);
    }
    public void sendReqForGroup(int groupIndex) throws IOException {
        outputStream.writeObject(new Message(username, groupIndex + "", Message.Type.newGroup));
    }
    public void set(Client client){
        this.username = client.getUsername();
        this.password= client.getPassword();
        this.email = client.getEmail();
        this.friends = client.friends;
        this.friendShipReqs = client.friendShipReqs;
        this.groups = client.groups;
    }
    public void showGroupList() {
        int i = 0;
        System.out.println("groups");
        for (Group group : groups) {
            if(group.getType()== Group.Type.privateChat) {
                System.out.println(i + "--> " + group.getName() + "--NEW MESSAGES: " + group.countOfNotSeenMessages());
                i++;
            }
        }
    }
    public void showFriends() {
        int i = 0;
        for (Client friend : friends) {
            System.out.println(i + "--> " + friend.toString());
            i++;
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                ", status=" + status +
                '}';
    }
    public void createFriendShipRequest() throws IOException, ClassNotFoundException, InterruptedException {
        outputStream.writeObject(new Message(username, "nothing", Message.Type.allClients));
        Thread.sleep(2000);
        System.out.println("THE CLIENTS OF SERVER :");
        int i = 0;
        ArrayList<Client> serverC = getServerClients();
        for (Client client : serverC) {
            System.out.println(i + " ---> " + client.getUsername());
            i++;
        }
        System.out.println("CHOOSE THE CLIENT TO SEND FRIEND REQ");
        String friendReq = scanner.nextLine();
        Client target = serverC.get(Integer.valueOf(friendReq));
        FriendShipReq friendShipReq=new FriendShipReq(this.getUsername(),target.getUsername(),"request");
        outputStream.writeObject(friendShipReq);
        System.out.println("THE CLIENT : " + target.getUsername() + " ADDED TO FRIENDS OF " + username);
        secondMenu();
    }
    public void showFriendShipReqs() throws IOException {
        int i = 0;
        for (FriendShipReq friendShipReq : friendShipReqs) {
            if (friendShipReq != null)
                System.out.println(i + "----->" + "from : " + friendShipReq.getOriginClient() + " to " + friendShipReq.getTargetClient());
            i++;
        }
        String choice = scanner1.nextLine();
        FriendShipReq friendShipReq = friendShipReqs.get(Integer.valueOf(choice));
        System.out.println("1-ACCEPT\n2-REJECT");
        String freqChoice = scanner1.nextLine();
        if (freqChoice.equals("1")) {
            Client originc=null;
            Client targetc=null;
            for(Client client:serverClients){
                if(client.getUsername().equals(friendShipReq.getOriginClient())){
                    originc=client;
                }
                else if(client.getUsername().equals(friendShipReq.getTargetClient())){
                    targetc=client;
                }
            }
            outputStream.writeObject(new FriendShipReq(friendShipReq.getOriginClient(), friendShipReq.getTargetClient(), "accepted"));
            System.out.println(ANSI_BLUE + friendShipReq.getOriginClient() + " successfully added to your friends");
        }
    }

    public void showServerClients() {
        for (Client client : serverClients) {
            System.out.println(client.getUsername());
        }
    }
//    public void playMusic(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        })
//    }
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
