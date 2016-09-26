package com.example.ITC.messenger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by argishti on 9/22/16.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{
    private Button loginButton = null;
    private Button regButton = null;
    private EditText username = null;
    private EditText password = null;
    private ContainerActivity activity = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,null,false);
        loginButton = (Button)view.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);
        regButton = (Button)view.findViewById(R.id.btn_register);
        regButton.setOnClickListener(this);
        username = (EditText)view.findViewById(R.id.et_username);
        password = (EditText)view.findViewById(R.id.et_password);
        activity = (ContainerActivity)getActivity();
        onClick(loginButton);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //  mainClient.setCaller();
                activity.mainClient.execute("login", username.getText().toString(),
                        password.getText().toString());
                break;
            case R.id.btn_register:
                activity.mainClient.execute("registration", username.getText().toString(),
                        password.getText().toString());
                break;
        }
    }

}
