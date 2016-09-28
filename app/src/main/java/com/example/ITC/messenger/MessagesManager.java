package com.example.ITC.messenger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by student on 9/20/16.
 */
public class MessagesManager {

    private DataBaseHelper dbHelper = null;
    private SQLiteDatabase db = null;

    public MessagesManager(Context context,String uName) {
        dbHelper = new DataBaseHelper(context,uName);
    }

    public void openDB() {
        this.db = dbHelper.openDB();
    }

    public void readAllMessages(String pairName)
    {
     //   MessageModel []a = dbHelper.readAllData(db,pairName);
        int t ;
        t=1;
    }

    public void readCurrentMessage(MessageListener msglst)
    {
        //fragmentListener.instantUpdateWindow();
    }

    public synchronized void addNewMessage(String msg, String userName,int sendByMe) {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        MessageModel m = new MessageModel(userName,msg,date.toString(),sendByMe);
        dbHelper.insertData(m,db);
    }

    public void closeDb() {
        dbHelper.closeDb();
    }

}
