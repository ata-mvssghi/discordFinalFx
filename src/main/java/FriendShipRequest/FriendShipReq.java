package FriendShipRequest;

import client.Client;

import java.io.Serializable;

public class FriendShipReq implements Serializable {
    private String originClient;
    private String  targetClient;
    String statusOfRequest;
    int id;

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public FriendShipReq(String originClient, String targetClient, String statusOfRequest) {
        this.originClient = originClient;
        this.targetClient = targetClient;
        this.statusOfRequest = statusOfRequest;
    }

    public String getOriginClient() {
        return originClient;
    }

    public String getTargetClient() {
        return targetClient;
    }

    public String getStatusOfRequest() {
        return statusOfRequest;
    }
}
