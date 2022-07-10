package client;

import java.io.Serializable;

public class ClientMenu implements Serializable {
    public static void initialMenu(){
        System.out.println(ANSI_BLUE+"1-sign in\n2-sign up");
    }
    public void secondaryMenue(){
        System.out.println(ANSI_YELLOW+ "1-create a new privateChat\n2-show  privateChat\n3-show friends\n4- create a new friendship request\n5-show received friendship reqs\n6-set a status\n7-ServerMenu\n8-exit"+ANSI_RESET);
    }
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
}
