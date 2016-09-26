package com.example.ITC.messenger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by student on 9/19/16.
 */
public class UsersListFragment extends Fragment implements ListView.OnItemClickListener {
    private ContainerActivity activity = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        activity = (ContainerActivity)getActivity();



        ListView usersList = (ListView)view.findViewById(R.id.lw_user_list);
        //OnlineUsersAdapter adapter = new OnlineUsersAdapter(getContext(),android.R.layout.simple_list_item_1, activity.);
        usersList.setAdapter(activity.adapter);
        usersList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.mainClient.startUpdateTimer();
        Log.d("MYLOG","start thread");
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.mainClient.interruptUpdateTimer();
        Log.d("MYLOG","Interrupt");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String uName = ((TextView)view).getText().toString();

        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setUser(activity.getOnlineUsersMap().get(uName));
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, chatFragment).commit();
        //Toast.makeText(UsersListFragment.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
    }

}
