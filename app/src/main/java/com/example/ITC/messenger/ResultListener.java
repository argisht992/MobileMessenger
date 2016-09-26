package com.example.ITC.messenger;

/**
 * Created by student on 9/20/16.
 */
public interface ResultListener {
    void onResultLogin(boolean flag);
    void onResultRegister(boolean flag);
    void onLogout(boolean flag);
}
