package Role;

import client.Client;

public class Role {
    String name;
    Client client;

    public Role(String name, Client client) {
        this.name = name;
        this.client = client;
    }
}
