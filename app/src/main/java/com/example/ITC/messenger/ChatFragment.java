package com.example.ITC.messenger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ListView messagesListView = null;
    MessagesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        messagesListView = (ListView) view.findViewById(R.id.messages_list);
        userName = (TextView)view.findViewById(R.id.tv_username);
        editText = (EditText) view.findViewById(R.id.editText);
        sendButton = (Button) view.findViewById(R.id.btn_send);
        sendButton.setOnClickListener(this);
        userName.setText(user.getUsername());
        activity =(ContainerActivity) getActivity();
        adapter = new MessagesAdapter(activity, (activity.getMessagesManager()).readAllMessages(user.getUsername()));
        messagesListView.setAdapter(adapter);
        client = new Client(user, activity);
        client.execute();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (!editText.getText().toString().isEmpty()) {
            client.sendMessage(editText.getText().toString());
            editText.setText("");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //client.disConnect();
    }

    public void updateAdapter(final MessageModel m) {
        adapter.addNewMessage(m);
        if (m.getPair().equals(user.getUsername())) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity,"New Message From "+m.getPair(),1).show();
                    activity.newMessage(m.getPair());
                }
            });

        }
    }

    public void setUser (User user) {
        this.user = user;
    }

}
