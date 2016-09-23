package com.example.liana.messenger;

import java.net.Socket;

/**
 * Created by student on 9/20/16.
 */
public class User {

    private String username = null;
    private String ip = null;
    private Socket uSocket = null;
    public User (String username,String ip, Socket uSocket) {
        this.username = username;
        this.ip = ip;
        this.uSocket = uSocket;
    }

    public String getUsername() {
        return this.username;
    }

    public String getIp() {
        return this.ip;
    }

    public Socket getuSocket() {
        return this.uSocket;
    }


}
