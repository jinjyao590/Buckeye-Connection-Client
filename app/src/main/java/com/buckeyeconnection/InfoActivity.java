package com.buckeyeconnection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.buckeyeconnection.R;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {

    //public static final String EXTRA_MESSAGE = "message";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();
        //String messageText = intent.getStringExtra(EXTRA_MESSAGE);
        //TextView msgView = (TextView) findViewById(R.id.view_msg);
        //msgView.setText(messageText);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String json= sp.getString("userInfo", "NULL");
        try {
            JSONObject jsonObject = new JSONObject(json);
            TextView txtUsername = (TextView) findViewById(R.id.infoOsuId);
            txtUsername.setText(jsonObject.getString("username"));
            TextView txtRealname = (TextView) findViewById(R.id.infoRealname);
            txtRealname.setText(jsonObject.getString("realname"));
            TextView txtNation = (TextView) findViewById(R.id.infoNation);
            txtNation.setText(jsonObject.getString("nation"));
            TextView txtGender = (TextView) findViewById(R.id.infoGender);
            txtGender.setText(jsonObject.getString("gender"));
            TextView txtMajor = (TextView) findViewById(R.id.infoMajor);
            txtMajor.setText(jsonObject.getString("major"));
            TextView txtCredits = (TextView) findViewById(R.id.infoCredits);
            txtCredits.setText(jsonObject.getString("credits"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
