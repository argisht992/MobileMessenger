package com.example.ITC.messenger;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by student on 9/22/16.
 */
public class MessagingServer extends AsyncTask <Void,Void,Void>{
    ServerSocket serverSocket = null;
    private BufferedReader reader = null;
    char []buf;
    private ContainerActivity activity = null;

    public MessagingServer(Activity activity) {
        this.activity = (ContainerActivity) activity;

    }

    public void startServer() {
        //Start server and calling receiveMessage for receiving messages
        try {
            serverSocket = new ServerSocket(ConstansContainer.SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stopServer() {
        //Stop receiving messages
    }
    public synchronized void receiveMessage(String message, String ip) {
        String userName = activity.getUserNameByIp(ip);
        activity.getMessagesManager().openDB();
        activity.getMessagesManager().addNewMessage(message,userName,0);
        activity.getMessagesManager().closeDb();
    }

    private synchronized char[] bzero(char[] buf) {
        for(int i = 0; i < buf.length; ++i) {
            buf[i] = 0;
        }
        return buf;
    }


    @Override
    protected Void doInBackground(Void... params) {
        startServer();
        while (serverSocket !=null) {
            try {
                 final Socket socket = serverSocket.accept();
                String ip = socket.getInetAddress().toString();
                Log.d("llll",ip);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            buf = new char[1024];
                            while (true) {
                                reader.read(buf);
                                receiveMessage(String.valueOf(buf),socket.getInetAddress().toString());
                                buf = bzero(buf);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("message", "ENDDDDD");
        }
        return null;
    }


}
