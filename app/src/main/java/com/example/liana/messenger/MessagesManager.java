package com.example.liana.messenger;

/**
 * Created by student on 9/20/16.
 */
public class MessagesManager {

    MessageListener fragmentListener = null;

    public MessagesManager(MessageListener msglst) {
                fragmentListener = msglst;
    }

    public void readAllMessages(DataBaseHelper helper)
    {
        MessageModel[] md = helper.readAllData();
        //fragmentListener.updateMessageWindow();
    }

    public void readCurrentMessage(MessageListener msglst)
    {
        //fragmentListener.instantUpdateWindow();
    }


}
