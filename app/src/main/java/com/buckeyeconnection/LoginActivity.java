package com.buckeyeconnection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_FLAG = "flag";


    LoginService mService;
    boolean mBound = false;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LoginService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        int flag = 0;
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        flag = intent.getIntExtra(EXTRA_FLAG, flag);
        message = "Hello";
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LoginService.LoginBinder binder = (LoginService.LoginBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    public void onLogin(View view) {

        String message;

        EditText edt_username = (EditText) findViewById(R.id.edt_id);
        EditText edt_password = (EditText) findViewById(R.id.edt_pwd);

        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();

        if (mBound) {
            int flag = mService.checkLogin(username, password);
            if (flag==1) {
                message = "Welcome, " + username + "!";
                Log.d("IT SHOULD:", message);
                Intent nIntent = new Intent(this, UserCenterActivity.class);
                //nIntent.putExtra(InfoActivity.EXTRA_MESSAGE, message);
                //nIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(nIntent);
            }
            else if (flag==2) {
                message = "Sorry, wrong password!";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
            else if (flag==3) {
                message = "Sorry, invalid username, please sign up!";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signUp(View view) {
        Intent nIntent = new Intent(this, RegisterActivity.class);
        startActivity(nIntent);
    }
}
