package client;

import server.Server;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.SplittableRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 5000, "tempClient", "123456", "test@gmail.com");
            client.startClient();
    }
}
