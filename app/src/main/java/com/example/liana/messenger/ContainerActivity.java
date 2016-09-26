package com.example.liana.messenger;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Map;

public class ContainerActivity extends FragmentActivity implements ResultListener, DataUpdateListener{
    private FragmentTransaction fragmentTransaction = null;
    private Fragment loginFragment = null;
    private Fragment usersListFragment = null;
    public MainClient mainClient = null;
    private FragmentManager fm = null;
    public ArrayAdapter<String> adapter = null;
    public Map<String,User> onlineUsersMap = null;
    ArrayList<String> onlineUserslist = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        mainClient = new MainClient(this);
        loginFragment = new LoginFragment();
        adapter = new ArrayAdapter<String>(ContainerActivity.this,android.R.layout.simple_list_item_1);
        fm = getSupportFragmentManager();
        Fragment lastFragment = fm.findFragmentById(R.id.fragment_container);
        fragmentTransaction = fm.beginTransaction();

        if (lastFragment != null) {
            fragmentTransaction.replace(R.id.fragment_container,lastFragment).commit();
        } else {
            fragmentTransaction.replace(R.id.fragment_container, loginFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onResultLogin(final boolean isSuccess) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSuccess) {
                    usersListFragment = new UsersListFragment();
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,usersListFragment).commit();
                    new MessagingServer().execute();
                }

            }
        });
    }

    @Override
    public void onResultRegister(boolean isSuccess) {

    }


    @Override
    public void onLogout(boolean flag) {

    }

    @Override
    public void dataReceiver(Map<String, User> map) {

        onlineUsersMap = map;
        onlineUserslist = new ArrayList<String>(map.keySet());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(onlineUserslist);
            }
        });


        Log.d("MYLOG","DataReceiver");

    }

    @Override
    public void UpdateUserList() {

    }

    public Map<String,User> getOnlineUsersMap() {
        return onlineUsersMap;
    }
}
