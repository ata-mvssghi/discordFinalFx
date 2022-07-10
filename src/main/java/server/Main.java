package server;

import server.Server;

import java.util.ArrayList;

public class Main {
    public void createGroup() {
       // Group group = new Group();

    }
    public static void main(String[] args) {
        Server server = new Server(6090);
        server.startServer();
    }
}
