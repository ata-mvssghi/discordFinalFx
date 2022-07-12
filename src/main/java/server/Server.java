package server;

import FriendShipRequest.FriendShipReq;
import Group.Group;
import Links.Link;
import MessagePack.Message;
import Request.Request;
import Response.Response;
import ServerChat.ServerChat;
import client.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    public static int port;
    private static ConcurrentLinkedQueue<ClientHandler> clients = new ConcurrentLinkedQueue<>();
    private ArrayList<Group> allGroup = new ArrayList<>();
    private static ArrayList<Message> messages = new ArrayList<>();
    public static ArrayList<Group> groups = new ArrayList<>();
    public ArrayList<FriendShipReq> friendShipRequests=new ArrayList<>();
    public    ArrayList<Client> allClients = new ArrayList<>();
    ArrayList<ServerChat> allServerChats=new ArrayList<>();
    ArrayList<Link> links = new ArrayList<>();
    public  int groupIndex = 0;
    public int serverChatIndex=0;
    private boolean isGroupChanged = false;

    public Server(int port) {
        this.port = port;
    }
//    public  static void addToAllCLients(Client client){
//        allClients.add(client);
//        System.out.println("client added successfully");
//    }
    //public static ArrayList<Client> getAllClients() {
//        return allClients;
//    }

    public static ArrayList<Group> getGroups() {
        return groups;
    }

    public static Group getGroup(int index) {
        return groups.get(index);
    }

    /* public static addToGroup(Client client,){

         }*/
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("server is running : ... ");
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void addToClients(Client c) {
//        allClients.add(c);
//    }



    public void sendToAll(String sender, Message message) {
        System.out.println(message.getGroupIndex());
        System.out.println("group index");
        ArrayList<Client> thisGroupClients = groups.get(message.getGroupIndex()).getClients();
        for(Client client:thisGroupClients){
            System.out.println(client.getUsername());
        }
        ArrayList<ClientHandler> currentClinetHandlers=new ArrayList<>();
        for(Client groupClient:thisGroupClients) {
            Iterator iterator1=clients.iterator();
            while (iterator1.hasNext()) {
                ClientHandler clientHandler = (ClientHandler) iterator1.next();
                if (clientHandler.name.equals(groupClient.getUsername())){
                    currentClinetHandlers.add(clientHandler);
                }
            }
        }
        Iterator iterator=currentClinetHandlers.iterator();
        while (iterator.hasNext()){
            ClientHandler client = (ClientHandler) iterator.next();
            System.out.println(client.name);
        }
        Iterator iterator1=currentClinetHandlers.iterator();
        while (iterator1.hasNext()) {
            ClientHandler client = (ClientHandler) iterator1.next();
            System.out.println(client.name);
            if ( message.getType() == Message.Type.text) {
                client.sendMsg(message);
            } else if (client.name.equals(sender) && message.getType() == Message.Type.exit) {
                client.sendMsg(message);
                iterator1.remove();
            } else if (message.getType() == Message.Type.join || message.getType() == Message.Type.exit) {
                client.sendMsg(message);
            }
        }
    }
    public static ConcurrentLinkedQueue<ClientHandler> getClients() {
        return clients;
    }

    class ClientHandler implements Runnable {
        private Socket socket;
        public ObjectOutputStream objectOutput;
        public ObjectInputStream objectInput;
        private String name;


        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;
                objectInput = new ObjectInputStream(socket.getInputStream());
                objectOutput = new ObjectOutputStream(socket.getOutputStream());
                name = "";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Object request = (Object) objectInput.readObject();
                    Load.load();
                    messages = Load.getMessages();
                    if (request instanceof Message) {
                        Message message = (Message) request;
                        if (message.getType() == Message.Type.join) {
                            System.out.println(ANSI_BLUE + message.getSender() + " is joined to server." + ANSI_RESET);
                            name = message.getSender();
                            clients.add(this);
                        }
                        else if (message.getType() == Message.Type.text) {
                            System.out.println(ANSI_YELLOW + message.getSender() + "--" + message.getText() + ANSI_RESET);
                            messages.add(message);
                            sendToAll(name, message);
                        }
                        else if (message.getType() == Message.Type.exit) {
                            System.out.println(ANSI_RED + message.getSender() + " left the server." + ANSI_RESET);
                            name = message.getSender();
                            sendToAll(name, new Message(message.getSender(), " left the group", Message.Type.exit));
                        }
                        else if (message.getType() == Message.Type.createNewGroupRequest) {
                            System.out.println(message.getSender()+" wants to create a new chat");
                            objectOutput.reset();
                            objectOutput.writeObject(allClients);
                            Group newGroup;
                            newGroup = (Group) objectInput.readObject();
                            groups.add(newGroup);
                            for(Client client:newGroup.getClients()){
                                System.out.println("client: "+client.getUsername());
                            for(ClientHandler clientHandler:clients){
                                System.out.println("clientHandler: "+clientHandler.name);
                                if(clientHandler.name.equals(client.getUsername())){
                                    clientHandler.objectOutput.writeObject(newGroup);
                                }
                            }
                            }
                            groupIndex++;
                            System.out.println("ada bo taza index di  "+groupIndex);
                            System.out.println("group created successfully" + ANSI_BLUE);
                        }
                        else if(message.getType()== Message.Type.allClients){
                            System.out.println(message.getSender()+" wants to access to all clients");
                            for(Client client:allClients){
                                System.out.println(client.getUsername());
                            }
                            objectOutput.reset();
                            objectOutput.writeObject(allClients);
                        }
                        else if(message.getType()== Message.Type.groupIndex){
                            System.out.println(message.getSender()+"wants to know group index");
                            objectOutput.writeObject(new Message(name,groupIndex+"", Message.Type.groupIndex));
                        }
                        else if(message.getType()== Message.Type.servetChatIndex){
                            System.out.println(message.getSender()+" wants to know sererChatIndex");
                            objectOutput.writeObject(new Message(name,serverChatIndex+"", Message.Type.servetChatIndex));
                        }
                        else if(message.getType()== Message.Type.newGroup){
                         objectOutput.writeObject( groups.get(Integer.valueOf(message.getText())));
                        }
                        else if(message.getType()== Message.Type.tag){
                            String originC=null;
                            for(ClientHandler clientHandler:clients){
                                if(clientHandler.name.equals(message.getText())){
                                    originC=message.getSender();
                                    clientHandler.objectOutput.writeObject(new Message(name,originC, Message.Type.tag));
                                }
                            }
                        }
                        else if(message.getType()== Message.Type.deleteAChannelReq){
                            Group channelTOBeDeleted=(Group) objectInput.readObject();
                            for(Client client:channelTOBeDeleted.getClients()){
                                for(ClientHandler clientHandler:clients){
                                    if(clientHandler.name.equals(client.getUsername())){
                                        System.out.println("247 + client:"+clientHandler.name);
                                        clientHandler.objectOutput.writeObject(new Message(name,"delete a chat response", Message.Type.deleteAChannelRes));
                                        clientHandler.objectOutput.writeObject(channelTOBeDeleted);
                                    }
                                }
                            }
                            groups.remove(channelTOBeDeleted);
                            System.out.println("the channel removed from server successfully");
                        }
                        else if(message.getType()== Message.Type.statusReq){
                            for(Client client:allClients){
                                if(client.getUsername().equals(message.getSender())){
                                    if(message.getText().equals("doNotDisturb"))
                                    client.setStatus(Client.Status.doNotDisturb);
                                    else if(message.getText().equals("IDLE")){
                                        client.setStatus(Client.Status.Idle);
                                    }
                                    else if(message.getText().equals("INVISIBLE")){
                                        client.setStatus(Client.Status.Invisible);
                                    }
                                    else if(message.getType().equals("online"))
                                        client.setStatus(Client.Status.online);
                                    else if(message.getText().equals("offline")){
                                        client.setStatus(Client.Status.offline);
                                    }
                                }
                            }
                        }
                        else if(message.getType()== Message.Type.serverNewName){
                                ServerChat  target=(ServerChat) objectInput.readObject();
                                for(Client client:target.getUsers()){
                                    for(ClientHandler clientHandler:clients){
                                        if(clientHandler.name.equals(client.getUsername())){
                                            clientHandler.objectOutput.writeObject(new Message(name,message.getText(), Message.Type.serverNewName));
                                            clientHandler.objectOutput.writeObject(target);
                                            break;
                                        }
                                    }
                                }
                        }
                        else if(message.getType()== Message.Type.deleteAUserReq){
                            Client clientToBeDeleted=(Client) objectInput.readObject();
                            ServerChat targetServeChat=(ServerChat) objectInput.readObject();
                            /////////////////////////////////////////////////////////////
                            for(Client client:targetServeChat.getUsers()) {
                                for (ClientHandler clientHandler : clients) {
                                    if (client.getUsername().equals(clientHandler.name)) {
                                        System.out.println("here we found the cient in server: " + clientHandler.name);
                                        clientHandler.objectOutput.writeObject(new Message(name, "delete a user response", Message.Type.deleteAUserRes));
                                        clientHandler.objectOutput.writeObject(clientToBeDeleted);
                                        clientHandler.objectOutput.writeObject(targetServeChat);
                                    }
                                }
                            }
                        }
                        else if(message.getType()== Message.Type.changeUserName){
                            for(ClientHandler clientHandler:clients){
                                if(clientHandler.name.equals(message.getSender())){
                                    clientHandler.name=message.getText();
                                }
                            }
                            for(Client client:allClients){
                                if(client.getUsername().equals(message.getSender())){
                                    client.username=message.getText();
                                }
                            }
                        }
                        else if(message.getType()== Message.Type.changeEmail){
                            String exEmail=(String) objectInput.readObject();
                            for(Client client:allClients){
                                if(client.getEmail().equals(exEmail)){
                                    client.email=message.getText();
                                }
                            }
                        }
                        else if(message.getType()== Message.Type.addPhoneNumber){
                            for(Client client:allClients){
                                if(client.getUsername().equals(message.getSender())){
                                    client.phoneNumber=message.getText();
                                }
                            }
                        }
                        else if(message.getType()== Message.Type.changePasswordConfirm){
                            int flag=0;
                            for(Client client:allClients){
                                if(client.getUsername().equals(message.getSender())){
                                    if(client.password.equals(message.getText())){
                                        objectOutput.writeObject(new Message(name,"true", Message.Type.passChangeRes));
                                                    flag=1;
                                    }
                                }
                            }
                            if(flag==0){
                                objectOutput.writeObject(new Message(name,"false", Message.Type.passChangeRes));
                            }
                            System.out.println("308");
                            String newPass=(String) objectInput.readObject();
                            System.out.println("311");
                            for(Client client:allClients){
                                if(client.getUsername().equals(message.getSender())){
                                    if(client.username.equals(message.getSender())){
                                        client.password=newPass;
                                    }
                                }
                            }

                        }
                        else if(message.getType()== Message.Type.pin){
                            Group group=(Group) objectInput.readObject();
                            for(Client client:group.getClients()){
                                for(ClientHandler clientHandler:clients){
                                    if(clientHandler.name.equals(client.getUsername())){
                                        clientHandler.objectOutput.writeObject(new Message(name,message.getText(), Message.Type.pin));
                                        clientHandler.objectOutput.writeObject(group.getId());
                                        break;
                                    }
                                }
                            }
                        }
                        else if(message.getType()== Message.Type.react){
                           Group targetGroup=(Group) objectInput.readObject();
                           String reaction=(String) objectInput.readObject();
                            for(Client client:targetGroup.getClients()){
                                for(ClientHandler clientHandler:clients){
                                    if(clientHandler.name.equals(client.getUsername())){
                                        clientHandler.objectOutput.writeObject(new Message(name,message.getText(), Message.Type.react));
                                        clientHandler.objectOutput.writeObject(targetGroup.getId());
                                        clientHandler.objectOutput.writeObject(reaction);
                                        break;
                                    }
                                }
                            }

                        }
                        else if (message.getType() == Message.Type.sendFile) {
                            String fileName= (String) objectInput.readObject();
                            String FILE_TO_RECEIVED = "C:\\Users\\SPINO.SHOP\\Desktop\\Midterm project-ata-mohammad\\Discord Project-ata-mohammad\\New-Discord\\src\\SaveSendFile\\"+fileName;
//                            int index = (Integer) objectInput.readObject();
//                            int flag=0;
//                            for(Link link:links){
//                                if(link.chatId==index){
//                                    link.links.add(FILE_TO_RECEIVED);
//                                     flag=1;
//                                }
//                            }
//                            if(flag==0){
//                                Link newLink=new Link(index);
//                                newLink.links.add(FILE_TO_RECEIVED);
//                                links.add(newLink);
//                            }
                            //////////
                            int FILE_SIZE = 6022386;
                            int bytesRead;
                            int current = 0;
                            FileOutputStream fos = null;
                            BufferedOutputStream bos = null;
                            byte[] mybytearray = new byte[FILE_SIZE];
                            try {
                                fos = new FileOutputStream(FILE_TO_RECEIVED);
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                            bos = new BufferedOutputStream(fos);
                            bytesRead = objectInput.read(mybytearray, 0, mybytearray.length);
                            current = bytesRead;
                            do {
                                bytesRead =
                                        objectInput.read(mybytearray, current, (mybytearray.length - current));
                                if (bytesRead >= 0) current += bytesRead;
                                System.out.println(bytesRead);
                            }
                            while (bytesRead ==1024);
                            bos.write(mybytearray, 0, current);
                            bos.flush();
                            bos.close();
                            System.out.println("File " + FILE_TO_RECEIVED
                                    + " downloaded (" + current + " bytes read)");
                        }
                        else if(message.getType() == Message.Type.receiveFile){
//                            int index= (int) objectInput.readObject();
//                            ArrayList<String> targetLinks=new ArrayList<>();
//                            for(Link link:links){
//                                if(link.chatId==index){
//                                    targetLinks=link.links;
//                                }
//                            }
//                            objectOutput.writeObject(new Message(name,"nothing inportant", Message.Type.linkOfDownload));
//                            objectOutput.writeObject(targetLinks);
                            String address= (String) objectInput.readObject();
                            int SOCKET_PORT = 13267;
                            String FILE_TO_SEND = address;
                            System.out.println(address+"        goy");
                            FileInputStream fis = null;
                            BufferedInputStream bis = null;
                            OutputStream os = null;
                            ServerSocket servsock = null;
                            Socket sock = null;
                            try {
                                servsock = new ServerSocket(SOCKET_PORT);
                                while (true) {
                                    System.out.println("Waiting...");
                                    try {
                                        sock = servsock.accept();
                                        System.out.println("Accepted connection : " + sock);
                                        // send file
                                        File myFile = new File (FILE_TO_SEND);
                                        byte [] mybytearray  = new byte [(int)myFile.length()];
                                        fis = new FileInputStream(myFile);
                                        bis = new BufferedInputStream(fis);
                                        bis.read(mybytearray,0,mybytearray.length);
                                        os = sock.getOutputStream();
                                        System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                                        os.write(mybytearray,0,mybytearray.length);
                                        os.flush();
                                        System.out.println("Done.");
                                    }
                                    finally {
                                        if (bis != null) bis.close();
                                        if (os != null) os.close();
                                        if (sock!=null) sock.close();
                                    }
                                }
                            }
                            finally {
                                if (servsock != null) servsock.close();
                            }



                        }
                        else if(message.getType()== Message.Type.setImage){
                            for(Client client:allClients){
                                if(client.getUsername().equals(message.getSender())){
                                    client.image=message.getText();
                                    System.out.println("image of the client "+client.getUsername()+"  was set successfully");
                                }
                            }
                        }
                    }
                    else if (request instanceof FriendShipReq) {
                        FriendShipReq friendShipReq=(FriendShipReq) request;
                        if(friendShipReq.getStatusOfRequest().equals("accepted")){
                            for(ClientHandler clientHandler:clients){
                                if(clientHandler.name.equals(friendShipReq.getTargetClient())){
                                    clientHandler.sendMsg(new Message(clientHandler.name,friendShipReq.getOriginClient(), Message.Type.friendReqAccepted));
                                }
                                else if(clientHandler.name.equals(friendShipReq.getOriginClient())){
                                    clientHandler.sendMsg(new Message(clientHandler.name,friendShipReq.getTargetClient(), Message.Type.friendReqAccepted));
                                }
                            }
                            System.out.println("THE FRIENDSHIP BETWEEN: "+friendShipReq.getOriginClient()+" AND "+friendShipReq.getTargetClient()+" SAVED SUCCESSFULLY");
                        }else if(friendShipReq.getStatusOfRequest().equals("rejected")){
                            for(ClientHandler clientHandler:clients){
                                if(clientHandler.name.equals(friendShipReq.getTargetClient())){
                                    clientHandler.sendMsg(new Message(clientHandler.name,friendShipReq.getOriginClient(), Message.Type.friendReqRejected));
                                }
                                else if(clientHandler.name.equals(friendShipReq.getOriginClient())){
                                    clientHandler.sendMsg(new Message(clientHandler.name,friendShipReq.getTargetClient(), Message.Type.friendReqRejected));
                                }
                            }
                        }
                        else if(friendShipReq.getStatusOfRequest().equals("request")){
                            System.out.println("460");
                            for(ClientHandler clientHandler:clients){
                                if(clientHandler.name.equals(friendShipReq.getTargetClient())){
                                    objectOutput.reset();
                                    clientHandler.objectOutput.writeObject(allClients);
                                    System.out.println("haaa eee esmash : "+clientHandler.name);
                                    clientHandler.sendMsg(new Message(clientHandler.name,friendShipReq.getOriginClient(), Message.Type.friendReq));
                                    System.out.println("466");
                                }
                                else if (clientHandler.name.equals(friendShipReq.getOriginClient())) {
                                    objectOutput.reset();
                                    clientHandler.objectOutput.writeObject(allClients);
                                }
                            }
                        }

                    }
                    else if (request instanceof Request) {
                        Request request1 = (Request) request;
                        if (request1.getType() == Request.Type.signIn) {
                            int index = 0;
                            String username = (String) objectInput.readObject();
                            int flag = 0;
                            for (int i = 0; i < allClients.size(); i++) {

                                System.out.println(ANSI_RED+allClients.get(i).getUsername()+ANSI_RESET);
                                if (allClients.get(i).getUsername().equals(username)) {
                                    flag = 1;
                                    index = i;
                                    break;
                                }
                            }
                            if (flag == 1) {
                                objectOutput.writeObject(new Response(Response.Type.signIn,"username exists"));
                            }
                            if (flag == 0) {
                                objectOutput.writeObject(new Response(Response.Type.signIn,"username doesn't exist"));
                            }
                            String password = (String) objectInput.readObject();
                            if (allClients.get(index).getPassword().equals(password)) {
                                objectOutput.writeObject(new Response(Response.Type.signIn,"password exists"));
                            } else {
                                objectOutput.writeObject(new Response(Response.Type.signIn,"password exists"));
                            }
                        }
                        else if (request1.getType() == Request.Type.signUp) {
                            String username = (String) objectInput.readObject();
                            System.out.println("got it " + username);
                            int flag = 0;
                            for (int i = 0; i < allClients.size(); i++) {
                                if (allClients.get(i).getUsername().equals(username)) {
                                    flag = 1;
                                }
                            }

                            if (flag == 1) {
                                objectOutput.writeObject(new Response(Response.Type.signup, "username exists"));
                            }
                            else {
                                objectOutput.writeObject(new Response(Response.Type.signup, "username doesn't exist"));
                            }
                            Client client = (Client) objectInput.readObject();
                            allClients.add(client);
                            System.out.println(allClients);
                        }
                    }
                    else if(request instanceof ServerChat){
                        ServerChat serverChat=(ServerChat)request;
                        System.out.println("new server chat with name "+serverChat.getServerName()+" added");
                        int flag=0;
                        for(ServerChat tempServerChat:allServerChats){
                            if(tempServerChat.id==serverChat.id){
                                flag=1;
                            }
                        }
                        if(flag==0){
                            allServerChats.add(serverChat);
                        }
                        ArrayList<Client>serverChatClients=new ArrayList<>();
                        serverChatClients=serverChat.getUsers();
                       for(Client client:serverChatClients){
                           for(ClientHandler clientHandler:clients){
                               if(clientHandler.name.equals(client.getUsername())){
                                   clientHandler.objectOutput.writeObject(serverChat);
                               }
                           }
                       }
                        serverChatIndex++;
                    }
                }
            } catch (
                    ClassNotFoundException e) {
                e.printStackTrace();
            } catch (
                    SocketException e) {
                e.printStackTrace();
                System.out.println("someone left the server");
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }
        public static boolean emailCheck(String email) {
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                System.out.println("invalid input");
                return false;
            } else return true;
        }
        public   synchronized void sendMsg(Message message) {
            try {
                objectOutput.writeObject(message);
                System.out.println("message will be sent form server :  "+message.getText());
            } catch (SocketException e) {
                System.out.print("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
