package com.buckeyeconnection;

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
import com.buckeyeconnection.util.NormalPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendService extends Service {
  //public static final String PREFS_NAME = "MyInfo";
  private final String TAG= "FriendService";
  private SharedPreferences sp;

  @Override
  public void onCreate() {
    sp = PreferenceManager.getDefaultSharedPreferences(this);
  }

  private final IBinder mBinder = new FriendBinder();

  public FriendService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    return mBinder;
  }

  public class FriendBinder extends Binder {
    FriendService getService() {
      return FriendService.this;
    }
  }

  public void getFriendsInfo(String username) {
    Map<String, String> mMap = new HashMap<>();
    mMap.put("username", username);

    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


    //String url = "http://192.241.179.166:8080/BCServer/servlet/login";
    String url = "http://192.168.0.107:8080/BCServer/servlet/getFriends";

    Request<JSONObject> jsonRequest = new NormalPostRequest(url,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            try {
              Log.d("Response:", response.toString());//getString("userInfo"));

              SharedPreferences.Editor editor =sp.edit();
              int ct = response.getInt("count_friends");
              editor.putInt("count_friends", ct);
              for (int i = 0; i < ct; i++) {
                editor.putString("friendInfo_"+i, response.getString("friendInfo_"+i));
              }
              //editor.putString("userInfo", response.getString("userInfo"));
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
  }
}
