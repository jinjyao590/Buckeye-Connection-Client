package com.buckeyeconnection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.buckeyeconnection.model.UserBean;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_FLAG = "flag";
    private static final String TAG = "RegisterActivity";
    //public static final String PREFS_NAME = "myinfo";
    RegisterService mService;
    boolean mBound = false;

    public SharedPreferences sp;

    private int nation_pos = 0;
    private int major_pos = 0;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            RegisterService.RegisterBinder binder = (RegisterService.RegisterBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("Loggg", "onCreate");
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Loggg", "onStop");
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Loggg", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Loggg", "onStart");
        Intent intent = new Intent(this, RegisterService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void onRegister(View view) throws IOException {
        EditText edt_username = (EditText) findViewById(R.id.edtOsuId);
        EditText edt_password = (EditText) findViewById(R.id.edtPwd);
        EditText edt_confirmPwd = (EditText) findViewById(R.id.edtPwdConf);
        EditText edt_realName= (EditText) findViewById(R.id.edtRealName);
        Spinner spn_nation = (Spinner) findViewById(R.id.spnCty);
        RadioButton rb_male = (RadioButton) findViewById(R.id.rbMale);
        RadioButton rb_female = (RadioButton) findViewById(R.id.rbFemale);
        Spinner spn_major = (Spinner) findViewById(R.id.spnMajor);

        spn_nation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String[] countries = getResources().getStringArray(R.array.countries);
                nation_pos = i;
                //Log.d("HHHHHHHHHH", "nation "+ nation_pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                major_pos = i;
                //Log.d("HHHHHHHHHH", "major "+ major_pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        UserBean ub = new UserBean();
        ub.setUsername(edt_username.getText().toString());
        ub.setRealName(edt_realName.getText().toString());
        ub.setPassword(edt_password.getText().toString());
        ub.setNation(nation_pos);
        ub.setGender(genderSelector2(rb_male, rb_female));
        ub.setMajor(major_pos);

        Log.d("HHHHHHHHHH", "nation" + nation_pos);
        Log.d("HHHHHHHHHH", "major" + major_pos);

        mService.register(ub);
        //String info;
        //SharedPreferences sp = getSharedPreferences("PREFS_NAME", 0);
        //info = sp.getString("userInfo", "Nothing");
        Intent nIntent = new Intent(this, UserCenterActivity.class);
        //nIntent.putExtra(InfoActivity.EXTRA_MESSAGE, message);
        //nIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(nIntent);

        //Log.d("Get shared:", info);
    }

    private int genderSelector2(RadioButton rb1, RadioButton rb2) {
        if (rb1.isChecked()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    public void onSelect(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbMale:
                if (checked) {
                    Log.d(TAG, "Male");
                    break;
                }
            case R.id.rbFemale:
                if (checked) {
                    Log.d(TAG, "Female");
                    break;
                }
        }
    }
}
