package com.example.ITC.messenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by student on 9/22/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private String tableName = null;
    private ContentValues cv = null;
    public DataBaseHelper(Context context,String tableName) {
        super(context,"messengerDB",null,1);
        cv = new ContentValues();
        this.tableName = tableName;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("create table if not exists " + tableName +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pair TEXT, " +
                "msg TEXT, " +
                "time TEXT, " +
                "sendByMe INTEGER" +
                ");");
         }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //add MessageModel in table
    public void insertData(MessageModel message, SQLiteDatabase db) {
        cv.put("pair",message.getPair());
       // cv.put("msg",message.getMessage());
        cv.put("msg","PRIVET");
        cv.put("time", message.getTime());
        cv.put("sendByMe",message.getSentByMe());
        db.insert(tableName,null,cv);
    }

    public MessageModel[] readAllData(SQLiteDatabase db,String pair1) {
        String []pairName = new String[]{ pair1 };
        Cursor c = db.query(tableName,null,"pair = ?",pairName,null,null,null,null);
        MessageModel []messages = new MessageModel[c.getCount()];
        if(c.moveToFirst()) {
           for(int i = 0; i < messages.length; ++i) {
               String pair = c.getString(c.getColumnIndex("pair"));
               String msg = c.getString(c.getColumnIndex("msg"));
               String time = c.getString(c.getColumnIndex("time"));
               int sendByMe = c.getInt(c.getColumnIndex("sendByMe"));
               messages[i] = new MessageModel(pair,msg,time,sendByMe);
               if(!c.moveToNext()){
                   break;
               }
           }
        }
        return messages;
    }
    public void closeDb() {
        this.close();
    }
    public SQLiteDatabase openDB() {
        return this.getWritableDatabase();
    }

    /*public MessageModel readLastData() {
        //mutable
        return new MessageModel();
    }*/
}

