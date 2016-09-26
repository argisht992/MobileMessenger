package com.example.liana.messenger;

import android.util.Log;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public InetAddress getIp() throws UnknownHostException {
        return InetAddress.getByName(ntoa(Long.parseLong(ip)));
    }

    public Socket getuSocket() {
        return this.uSocket;
    }

    private String ntoa(long raw) {
       // byte[] b = new byte[] {(byte)(raw >> 24), (byte)(raw >> 16), (byte)(raw >> 8), (byte)raw};
        byte[] b = new byte[] {(byte)raw,  (byte)(raw >> 8),(byte)(raw >> 16), (byte)(raw >> 24)};
        try {
            return InetAddress.getByAddress(b).getHostAddress();
        } catch (UnknownHostException e) {
            //No way here
            return null;
        }
    }
}
