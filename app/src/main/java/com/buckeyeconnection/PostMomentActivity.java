package com.buckeyeconnection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.buckeyeconnection.model.MomentBean;
import com.buckeyeconnection.util.NormalPostRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostMomentActivity extends AppCompatActivity {

    private boolean pBound;
    private PostMomentService pService= new PostMomentService();
    //private RegisterService pService= new RegisterService();
    private final String TAG= "PostMomentActivity";

    private ServiceConnection pServiceConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PostMomentService.PostMomentBinder postMomentBinder= (PostMomentService.PostMomentBinder)service;
            //RegisterService.LoginBinder postMomentBinder= (RegisterService.LoginBinder)service;
            postMomentBinder.getService();
            pBound= true;
            Log.d(TAG, "SERVICE CONNECTED AAAA");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            pBound= false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationContext();
        setContentView(R.layout.activity_post_moment);

        Button send_button= (Button) findViewById(R.id.send_button);
        Intent pIntent= new Intent(this, PostMomentService.class);
        bindService(pIntent, pServiceConnection, BIND_AUTO_CREATE);
    }


    @Override
    protected void onStart()
    {
        super.onStart();

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(pBound)
        {
            unbindService(pServiceConnection);
            pBound= false;
        }
    }
    public void onPost(View view)
    {
        EditText momentContent= (EditText) findViewById(R.id.momentContent);
        MomentBean mb= new MomentBean();
        mb.setContent(momentContent.getText().toString());
        mb.setUsername("JJY");
        Log.d(TAG, "CONTENT IS "+ mb.getContent());
        Log.d(TAG, "User name is "+ mb.getUsername());
        pService.postMoment(mb);
        Intent mIntent= new Intent(this, MomentListActivity.class);
        startActivity(mIntent);

        //pService.postMoment(mb);
        //List<Map<String,Object>> list= new ArrayList<>();
        //list= pService.getMomentListFromServer();
        //SharedPreferences moments= getSharedPreferences("pref", Context.MODE_PRIVATE);
        //System.out.println("shard "+ moments.getString("moments", "nothing"));
        //if (list.size()==0) Log.d(TAG," NO LIST");


    }
}
