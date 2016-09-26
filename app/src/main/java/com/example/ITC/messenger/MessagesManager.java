package com.example.ITC.messenger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by student on 9/20/16.
 */
public class MessagesManager {

    private MessageListener fragmentListener = null;
    private DataBaseHelper DBHelper = null;
    private SQLiteDatabase db = null;

    public MessagesManager(Context context,String uName) {
        fragmentListener = (MessageListener) context;
        DBHelper = new DataBaseHelper(context,uName);
        db = DBHelper.getWritableDatabase();
    }

    public void readAllMessages(DataBaseHelper helper)
    {
        //fragmentListener.updateMessageWindow();
    }

    public void readCurrentMessage(MessageListener msglst)
    {
        //fragmentListener.instantUpdateWindow();
    }


}
