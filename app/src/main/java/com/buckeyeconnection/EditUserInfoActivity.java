package com.buckeyeconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.w3c.dom.Text;

public class EditUserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        Intent eIntent= getIntent();

        EditText user_name_text= (EditText) findViewById(R.id.user_name_text);

        String user_name= eIntent.getStringExtra("user_name");
        user_name_text.setText(user_name);


    }
}
