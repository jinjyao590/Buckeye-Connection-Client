package com.buckeyeconnection;

import android.app.IntentService;
import android.app.Service;
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
public class RegisterService extends Service {
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_PASSWORD = "password";
    //public static final String PREFS_NAME = "myinfo";

    //public static final String PREFS_NAME = "MyInfo";
    private final String TAG= "RegisterService";

    private final IBinder mBinder = new RegisterBinder();
    private SharedPreferences sp;

    @Override
    public void onCreate() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }



    public void register(UserBean ub) {

        /*
         * 如果是POST请求，则请求参数放在请求体中，
         * name=%E6%88%91&age=12
         *
         * */
        //StringBuilder buf = new StringBuilder();
        //buf.append("name="+URLEncoder.encode(name.getText().toString(),"UTF-8")+"&");
        //buf.append("age="+ URLEncoder.encode(age.getText().toString(),"UTF-8"));
        /*JSONObject obj = new JSONObject();
        try {
            obj.put("username", ub.getUsername());
            obj.put("password", ub.getPassword());
            obj.put("nation", ub.getNation());
            obj.put("gender", ub.getGender());
            obj.put("major", ub.getMajor());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        Map<String, String> mMap = new HashMap<>();
        mMap.put("username", ub.getUsername());
        mMap.put("realname", ub.getRealName());
        Log.d(TAG, "REAL NAME IS "+ mMap.get("realname"));
        mMap.put("password", ub.getPassword());
        mMap.put("nation", "" + ub.getNation());
        mMap.put("gender", "" + ub.getGender());
        mMap.put("major", "" + ub.getMajor());
        Log.d("lllllllllll","12"+ub.getNation());

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        //String url = "http://172.31.26.180:8080/BCServer/servlet/register";


        //String url = "http://192.241.179.166:8080/BCServer/servlet/register";

        String url = "http://192.168.0.107/BCServer/servlet/register";


        Request<JSONObject> jsonRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Response:", response.getString("userInfo"));

                            SharedPreferences.Editor editor =sp.edit();
                            editor.putString("userInfo", response.getString("userInfo"));
                            editor.commit();
                            Log.d("After Response:", "I can still do a lot of things");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG, "On response called");

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "on error called");
                //Log.d(TAG, error.getMessage());
                //error.printStackTrace();
            }
        }, mMap);
        requestQueue.add(jsonRequest);
        Log.d(TAG, "I am after volley");
    }

    /*public void aa()
    {
        Log.d(TAG, "this is hhh");
    }*/

    public class RegisterBinder extends Binder {
        RegisterService getService() {
            return RegisterService.this;
        }
    }
}
