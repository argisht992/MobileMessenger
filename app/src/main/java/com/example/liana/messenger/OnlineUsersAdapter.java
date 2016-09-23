package com.example.liana.messenger;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by student on 9/22/16.
 */
public class OnlineUsersAdapter extends ArrayAdapter<String> {
    private Map<String, User> onlineUsers = null;


    public OnlineUsersAdapter(Context context, int resource, String[] onlineUsers) {
        super(context, resource, onlineUsers);

       /*this.onlineUsers = onlineUsers;

        String[] strarr = new String[onlineUsers.size()];
        int i = 0;
        for (String s : onlineUsers.keySet()){
            strarr[i] = s;
            ++i;
        }*/

    }

    public void setOnlinesMap(Map<String, User> map)
    {
        onlineUsers = map;
    }



}
