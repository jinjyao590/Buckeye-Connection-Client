package com.buckeyeconnection;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.buckeyeconnection.model.UserBean;
import com.buckeyeconnection.util.CustomRequest;
import com.buckeyeconnection.util.NormalPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class LoginService extends Service {
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_PASSWORD = "password";
    //public static final String PREFS_NAME = "myinfo";

    //public static final String PREFS_NAME = "MyInfo";
    private final String TAG= "LogInService";

    private final IBinder mBinder = new LoginBinder();
    private SharedPreferences sp;

    @Override
    public void onCreate() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    public int checkLogin(String username, String password) {

        // TODO: use http request to query database and get the matching result of username and password
        Map<String, String> mMap = new HashMap<>();
        mMap.put("username", username);
        mMap.put("password", password);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        //String url = "http://192.241.179.166:8080/BCServer/servlet/login";
        String url = "http://192.168.0.107:8080/BCServer/servlet/login";

        Request<JSONObject> jsonRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Response:", response.toString());//getString("userInfo"));

                            SharedPreferences.Editor editor =sp.edit();
                            editor.putString("userInfo", response.getString("userInfo"));
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Errorrrrrr", error.toString());
            }
        }, mMap);
        requestQueue.add(jsonRequest);

        String message;

        /* TODO: modify the magic numbers by replacing them with constant
           e.g. private static final int SUCCESS = 1; or by creating a enum class
        *//*
        if (username.equals(db_username)) {
            if (password.equals(db_password)) {

                return 1;
            }
            else {
                return 2;
            }
        }
        else {
            return 3;
        }*/
        return 1;
    }

    /*public void aa()
    {
        Log.d(TAG, "this is hhh");
    }*/

    public class LoginBinder extends Binder {
        LoginService getService() {
            return LoginService.this;
        }
    }
}
