package com.example.ITC.messenger;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by student on 9/20/16.
 */
public class MainClient  extends AsyncTask<String, Void , Void> {
    public Socket clientSocket = null;
    private Thread updaterThread = null;
    private PrintWriter writer= null;
    private BufferedReader reader= null;
    private ResultListener lst = null;
    private DataUpdateListener ulst = null;

    public MainClient(Context context) {
        this.lst = (ResultListener)context;
        this.ulst = (DataUpdateListener)context;
    }

    public void connect() {
        try {
            clientSocket = new Socket(InetAddress.getByName(ConstansContainer.SERVER_URL),ConstansContainer.SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            //handle exception
        }
    }

    public boolean login(String uName, String password)
    {
        char[] buffer = new char[1024];
        connect();
        try {
            writer = new PrintWriter(clientSocket.getOutputStream());
            writer.write("login?" + uName + ";" + password);
            writer.flush();
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            reader.read(buffer);
            String buf = new String(buffer);
            return buf.contains("YES");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        catch (NullPointerException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public boolean register(String uName, String password)
    {

        char[] bufer = new char[1024];
        connect();
        try {
            writer = new PrintWriter(clientSocket.getOutputStream());
            writer.write("registration?" + uName + ";" + password);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            reader.read(bufer);
            return bufer.toString().contains("YES");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean logout()
    {
        char[] buffer = new char[1024];
        connect();
        try {
            writer = new PrintWriter(clientSocket.getOutputStream());
            writer.write("logout?");
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            reader.read(buffer);
            return buffer.toString().contains("YES");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, User> getOnlines()
    {
        char[] buffer = new char[4096];
        try {
            writer = new PrintWriter(clientSocket.getOutputStream());
            writer.write("update?");
            writer.flush();
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            reader.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return setMap(new String(buffer));
    }

    public Map<String,User> setMap(String str) {
        int f1 = 0, f2;
        String ip, userName;
        Map<String,User> map = new HashMap<>();
        while(true) {
            f2 = str.indexOf(";", f1);
            if(f2 == -1) {
                break;
            }
            ip = str.substring(f1, f2);
            f2 += 1;
            f1 = str.indexOf("\n", f2);
            userName = str.substring(f2, f1);
            f1 += 1;
            map.put(userName,new User(userName,ip,null));
        }
        return map;
    }

    public void startUpdateTimer() {
        updaterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (updaterThread != null) {
                    try {
                        ulst.dataReceiver(getOnlines());
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updaterThread.start();
    }

    public void interruptUpdateTimer() {
        if (updaterThread != null) {
            //updaterThread.cancel();
            updaterThread = null;
        }
    }
    @Override
    protected Void doInBackground(String... params) {
        switch (params[0]) {
            case "login":
                boolean loginResult = login(params[1], params[2]);
                lst.onResultLogin(loginResult);
                break;
            case "registration":
                lst.onResultRegister(register(params[1], params[2]));
                break;
            case "logout":
                lst.onLogout(logout());
                break;
        }
        return null;
    }


}
