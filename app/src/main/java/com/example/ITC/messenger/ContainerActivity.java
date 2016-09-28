package com.example.ITC.messenger;

import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.style.EasyEditSpan;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

public class ContainerActivity extends FragmentActivity implements ResultListener, DataUpdateListener {
    private Fragment loginFragment = null;
    public MainClient mainClient = null;
    private FragmentManager fm = null;
    public ArrayAdapter<String> adapter = null;
    public Map<String,User> onlineUsersMap = null;
    ArrayList<String> onlineUserslist = null;
    private MessagesManager messagesManager = null;
    private String loginedUserName = null;
    ///


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        mainClient = new MainClient(this);
        loginFragment = new LoginFragment();
        adapter = new ArrayAdapter<String>(ContainerActivity.this,
                android.R.layout.simple_list_item_1);
        fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.fragment_container);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (currentFragment != null) {
            fragmentTransaction.replace(R.id.fragment_container,currentFragment);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.replace(R.id.fragment_container, loginFragment);
            fragmentTransaction.commit();
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
                    Fragment usersListFragment = new UsersListFragment();
                    fragmentTransaction(usersListFragment);
                    new MessagingServer(ContainerActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    //create DB
                    messagesManager = new MessagesManager(ContainerActivity.this,loginedUserName);
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


    void fragmentTransaction(Fragment endPoint) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, endPoint);
        if (endPoint instanceof ChatFragment) {
            fragmentTransaction.addToBackStack(null);
        } else if (endPoint instanceof UsersListFragment) {
            loginedUserName = ((LoginFragment)loginFragment).getCurrentUserName();
        }
        fragmentTransaction.commit();
        fm.executePendingTransactions();
    }

    public synchronized String getUserNameByIp(String ip) {
        for (User us : onlineUsersMap.values()) {
            String tmp = us.getStringIp();
                if (ip.contains(tmp)) {
                    return us.getUsername();
                }

        }
        return null;
    }

    public synchronized MessagesManager getMessagesManager() {
        return this.messagesManager;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
