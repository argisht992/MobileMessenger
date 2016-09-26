package com.example.ITC.messenger;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by student on 9/22/16.
 */
public class MessagesAdapter {
    WeakReference<Map> map;
    public MessagesAdapter(WeakReference<Map> map) {
        this.map = map;
    }

}
