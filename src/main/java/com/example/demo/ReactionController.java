package com.example.demo;

import Group.Group;
import MessagePack.Message;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReactionController {
    public static int groupindex;
    public  static int messageIndex;
    public static ArrayList<Message> messages;
    @FXML
    ImageView laughter;
    @FXML
    ImageView like;
    @FXML
    ImageView dislike;
    public void react(Event event){
        Message messageToBeReacted=null;
        for(Group group:signInController.client.groups){
            if(group.getId()==groupindex){
                int i=0;
                for(Message message:group.getMessages()){
                    if(i==messageIndex){
                        messageToBeReacted=message;
                        break;
                    }
                }
            }
        }
        if(((ImageView)event.getSource()).getId().equals("laughter")){
            for(Group group:signInController.client.groups){
                if(group.getId()==groupindex){
                    int i=0;
                    for(Message message:group.getMessages()){
                        if(i==messageIndex){
                           message.laughterCount++;
                            break;
                        }
                    }
                }
            }
        }
        else if (((ImageView)event.getSource()).getId().equals("like")){
            for(Group group:signInController.client.groups){
                if(group.getId()==groupindex){
                    int i=0;
                    for(Message message:group.getMessages()){
                        if(i==messageIndex){
                        message.likeCount++;
                            break;
                        }
                    }
                }
            }
        }
        else if(((ImageView) event.getSource()).getId().equals("dislike")){
            for(Group group:signInController.client.groups){
                if(group.getId()==groupindex){
                    int i=0;
                    for(Message message:group.getMessages()){
                        if(i==messageIndex){
                            message.dislikeCount++;
                            signInController.client.chatController1.react(event);
                            break;
                        }
                    }
                }
            }
        }
    }
}
