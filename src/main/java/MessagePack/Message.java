package MessagePack;
import client.Client;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {
    public enum Type{
        text,
        createNewGroupRequest,
        createNewGroupRespons,
        groupIndex,
        servetChatIndex,
        groupClients,
        deleteAChannelReq,
        deleteAChannelRes,
        deleteAUserReq,
        deleteAUserRes,
        changeUserName,
        changePasswordConfirm,
        newPAss,
        passChangeRes,
        changeEmail,
        addPhoneNumber,
        serverPort,
        friendReqAccepted,
        friendReq,
        allClients,
        friendReqRejected,
        playMusic,
        statusReq,
        serverNewName,
        newGroup,
        tag,
        pin,
        react,
        sendFile,
        receiveFile,
        linkOfDownload,
        sigIn,
        singUp,
        join,
        exit
    }
    private String sender;
    private String text;
    private Type type;
     private boolean isSeenOrNot=false;
     private boolean isPinned=false;
     private  int groupIndex;
     private Object object;
     private Client friendShipOrigin;
     private Client getFriendShipTarget;
     public int likeCount;
     public int dislikeCount;
     public int  laughterCount;

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public Object getObject() {
        return object;}
  public void setObject(Object object) {
       this.object = object;
    }
    public void setSeenOrNot() {
        isSeenOrNot = true;
    }
    public boolean getSeenOrNot(){
        return isSeenOrNot;
    }
    public Message(String sender, String text, Type type) {
        this.sender = sender;
        this.text = text;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public Client getFriendShipOrigin() {
        return friendShipOrigin;
    }

    public void setFriendShipOrigin(Client friendShipOrigin) {
        this.friendShipOrigin = friendShipOrigin;
    }

    public void setGetFriendShipTarget(Client getFriendShipTarget) {
        this.getFriendShipTarget = getFriendShipTarget;
    }

    public Client getGetFriendShipTarget() {
        return getFriendShipTarget;
    }
//    public void setReact(Reaction.Reaction.Type type){
//       int currCount= reaccts.get(type);
//       currCount++;
//       reaccts.put(type,currCount);
//    }
//    public String  printReact(){
//        return (ANSI_BLUE+"  LIKES: "+reaccts.get(Reaction.Type.like)+" DISLIKES: "+reaccts.get(Reaction.Type.dislike)+" LAUGHTER: "+ reaccts.get(Reaction.Type.laughter)+ANSI_RESET);
//    }
    public String printReacts(){
        return ("LIKES:"+likeCount+" DISLIKE: "+dislikeCount+" LAUGHTER: "+laughterCount);
    }
    @Override
    public String toString() {
        return sender+" : "+text+"       "+ANSI_GREEN+"likes: "+likeCount+ANSI_RED+"    dislikes:  "+dislikeCount+ANSI_YELLOW+"      laughter: "+laughterCount+ANSI_RESET;
    }
    public void setReact(int num){
        if(num==1){
            likeCount++;
        }
        else if(num==2){
            dislikeCount++;
        }
        else if(num==3){
            laughterCount++;
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
