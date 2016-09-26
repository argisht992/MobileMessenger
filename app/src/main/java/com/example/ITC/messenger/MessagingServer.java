package com.example.ITC.messenger;

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
    public synchronized void receiveMessage(String message) {
        Log.d("message", "1111"+message);
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
                                Log.d("Privet",socket.getInetAddress().toString());
                                Log.d("message",String.valueOf(buf));
                                receiveMessage(String.valueOf(buf));
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
