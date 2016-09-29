package com.example.ITC.messenger;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class ContainerActivity extends AppCompatActivity implements ResultListener, DataUpdateListener {
    private Fragment loginFragment = null;
    public MainClient mainClient = null;
    private FragmentManager fm = null;
    public UsersListAdapter adapter = null;
    public Map<String,User> onlineUsersMap = null;
    ArrayList<String> onlineUserslist = null;
    private MessagesManager messagesManager = null;
    private String loginedUserName = null;
    private Menu myMenu = null;
    private EditText confirmPasswordText = null;
    ///

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        mainClient = new MainClient(this);
        loginFragment = new LoginFragment();
        //confirmPasswordText = (EditText) findViewById(R.id.confirm_password);
        adapter = new UsersListAdapter(ContainerActivity.this);
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
                    messagesManager = new MessagesManager(ContainerActivity.this,loginedUserName);
                } else {
                    invalidCommand("Invalid Username/Password");
                    mainClient.disconnect();
                    Log.d("activity","disconnected");
                }

            }
        });
    }

    @Override
    public void onResultRegister(final boolean isSuccess) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isSuccess) {
                    ((LoginFragment)loginFragment).confirmPasswordEditText.setVisibility(View.GONE);
                    Toast.makeText(ContainerActivity.this,"Registration Successfully Complated",Toast.LENGTH_LONG).show();
                } else {
                    invalidCommand("Username already exists");
                }
            }
        });
    }


    public void invalidCommand(String command) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(700);
        Toast.makeText(ContainerActivity.this,command,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLogout(boolean flag) {

    }

    @Override
    public void dataReceiver(Map<String, User> map) {

        onlineUsersMap = map;
        onlineUsersMap.remove(loginedUserName);
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

    public Map<String,User> getOnlineUsersMap() {
        return onlineUsersMap;
    }


    public void fragmentTransaction(Fragment endPoint) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, endPoint);
        if (endPoint instanceof ChatFragment) {
            fragmentTransaction.addToBackStack(null);
        } else if (endPoint instanceof UsersListFragment) {
            loginedUserName = ((LoginFragment)loginFragment).getCurrentUserName();
            myMenu.setGroupVisible(0,true);
        } else if (endPoint instanceof LoginFragment) {
            myMenu.setGroupVisible(0,false);
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
//        mainClient.disconnect();
    }

    public Fragment getCurrentFragment() {
        return fm.findFragmentById(R.id.fragment_container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.myMenu = menu;
        menu.add(0,0,0,"Logout");
        myMenu.setGroupVisible(0,false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                new MainClient(this).execute("logout", loginedUserName);
                fragmentTransaction(loginFragment);
                mainClient.disconnect();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*        */
    public void newMessage(String userName) {
        adapter.changeColor(userName);
    }


}
