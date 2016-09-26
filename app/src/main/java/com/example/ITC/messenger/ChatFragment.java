package com.example.ITC.messenger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by student on 9/19/16.
 */
public class ChatFragment extends Fragment implements View.OnClickListener{
    private TextView userName = null;
    private EditText editText = null;
    private Button sendButton = null;
    private User user = null;
    private Socket pairSocket = null;
    private PrintWriter writer = null;

    @Override
    public void onStart() {
        super.onStart();
        //new ConnectToChatPair().execute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pairSocket = new Socket(user.getIp(),12345);
                    writer = new PrintWriter(pairSocket.getOutputStream());
                } catch (IOException e) {
                    //show error message
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        userName = (TextView)view.findViewById(R.id.tv_username);
        editText = (EditText) view.findViewById(R.id.editText);
        sendButton = (Button) view.findViewById(R.id.btn_send);
        sendButton.setOnClickListener(this);
        userName.setText(user.getUsername());
        return view;
    }

    @Override
    public void onClick(View v) {
        writer.write(editText.getText().toString());
        writer.flush();
    }

    public void setUser (User user) {
        this.user = user;
    }


    class ConnectToChatPair extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                pairSocket = new Socket(user.getIp(),12345);
                writer = new PrintWriter(pairSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
