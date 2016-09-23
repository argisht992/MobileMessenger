package com.example.liana.messenger;

import android.os.AsyncTask;

import java.util.ArrayList;


/**
 * Created by student on 9/22/16.
 */
public class MessagingServer extends AsyncTask <Void,Void,Void>{
    private ArrayList<User> talkerList;
    public void startServer() {
        //Start server and calling receiveMessage for receiving messages
    }
    public void stopServer() {
        //Stop receiving messages
    }
    public void receiveMessage() {
        //Start receiving messages and update database
        // socket_1.read();
        // socket_2.read();
    }


    @Override
    protected Void doInBackground(Void... params) {
        //Starting server
        return null;
    }


}
