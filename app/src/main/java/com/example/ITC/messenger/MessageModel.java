package com.example.ITC.messenger;

import java.io.Serializable;

/**
 * Created by student on 9/20/16.
 */
public class MessageModel implements Serializable {

    private String time = null;
    private String message = null;
    private String from = null;
    private int sentByMe = -1;

    public MessageModel(String from, String message, String time, int sentByMe) {
        this.from = from;
        this.message = message;
        this.time = time;
        this.sentByMe = sentByMe;
    }

    public String getTime() {
        return this.time;
    }

    public String getMessage() {
        return this.message;
    }

    public String getFrom() {
        return this.from;
    }

    public int getSentByMe() {
        return this.sentByMe;
    }
}
