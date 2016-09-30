package com.example.ITC.messenger;

/**
 * Created by student on 9/20/16.
 */
public interface ResultListener {
    void onResultLogin(int flag);
    void onResultRegister(int flag);
    void onLogout(int flag);
}
