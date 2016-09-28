package com.example.ITC.messenger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by student on 9/19/16.
 */
public class ChatFragment extends Fragment implements View.OnClickListener{
    private TextView userName = null;
    private EditText editText = null;
    private Button sendButton = null;
    private Client client = null;
    private User user = null;
    private ContainerActivity activity = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        userName = (TextView)view.findViewById(R.id.tv_username);
        editText = (EditText) view.findViewById(R.id.editText);
        sendButton = (Button) view.findViewById(R.id.btn_send);
        sendButton.setOnClickListener(this);
        userName.setText(user.getUsername());
        client = new Client(user);
        client.execute();
        /////////////////////////
        activity =(ContainerActivity) getActivity();
        activity.getMessagesManager().readAllMessages(user.getUsername());
        return view;
    }

    @Override
    public void onClick(View v) {
        client.sendMessage(editText.getText().toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        client.disConnect();
    }

    public void setUser (User user) {
        this.user = user;
    }

    public void setTextView(String s) {
        this.userName.setText(s);
    }
}
