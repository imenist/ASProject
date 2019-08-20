package com.example.administrator.wanandroid;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FourthFragment extends Fragment implements View.OnClickListener{
    private EditText account;
    private EditText password;
    private Button login;
    private Button register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FourthFragment(){
    }

    public static FourthFragment newInstance(){
        FourthFragment fragment = new FourthFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fourth_fragment,container,false);
        account = (EditText)view.findViewById(R.id.account);
        password = (EditText)view.findViewById(R.id.password);
        login = (Button)view.findViewById(R.id.login);
        register = (Button)view.findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                break;
            case R.id.register:
                Intent intent = new Intent(getContext(),Register.class);
                startActivity(intent);
        }
    }
}
