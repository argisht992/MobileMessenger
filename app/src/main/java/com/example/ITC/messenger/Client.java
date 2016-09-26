package com.example.ITC.messenger;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by student on 9/20/16.
 */
public class Client extends AsyncTask<String, Void, Void> {
    private Socket pairSocket = null;
    private PrintWriter writer = null;
    private User user = null;

    public Client(User user) {
        this.user = user;
    }


    public void sendMessage(String message) {
        writer.write(message);
        writer.flush();
    }

    public void disConnect() {
        try {
            pairSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            pairSocket = new Socket(user.getIp(),ConstansContainer.SOCKET_PORT);
            writer = new PrintWriter(pairSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
