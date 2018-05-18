package com.buckeyeconnection;

// Created by Dingying Lu

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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.buckeyeconnection.model.MomentBean;
import com.buckeyeconnection.util.NormalPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class PostMomentService extends Service {
    private final String TAG= "PostMomentService";
    private final IBinder pBinder= new PostMomentBinder();
    private static Context pContext;
    SharedPreferences moments;
    SharedPreferences.Editor moment_editor;

    public PostMomentService() {
    }

    /* @Override
     public void onStart()
     {
        super.onS
         pContext= getApplicationContext();
     }*/
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        pContext= getApplicationContext();
        Log.d(TAG, "onBind");
        return pBinder;
        //throw new UnsupportedOperationException("Not yet implemented");
    }


    static List<Map<String, Object>>  list= new ArrayList<Map<String, Object>>();
 /*   public List<Map<String, Object>> getMomentListFromServer()
    {
        String url= "http://192.168.0.6:8080/BCServer/servlet/getMoments";
        if (pContext== null) Log.d(TAG, "no context");
        RequestQueue requestQueue= Volley.newRequestQueue(pContext);
        Log.d(TAG, "getMoment");
        HashMap<String, String> map= new HashMap<String, String>();
        map.put("gg","123");
        JSONArray ARR= new JSONArray();
        //List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        Request<JSONObject> jsonRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.length()==0) Log.d(TAG, "NO RESPONSE");
                        Log.d(TAG, "On response called in getMoments");
                        System.out.println("aaaa response is "+response.toString());
                        try {
                            //JSONObject obj = response.getJSONObject("data");
                            moments= getSharedPreferences("pref", Context.MODE_PRIVATE);
                            moment_editor.putString("moments", response.toString());
                            moment_editor.commit();
                            Log.d(TAG,"share"+ moments.getString("moments", "nothing"));
                            Log.d(TAG, "AAA IN T");
                            System.out.println("aaa in try");
                            JSONArray jsonArray= response.getJSONArray("data");
                            //ARR= jsonArray;
                            System.out.println("aaa in try");
                            Gson gson= new Gson();
                            System.out.println(jsonArray.length()+" lentgh");
                            //List<Map<String, Object>> inner_list= new ArrayList<Map<String, Object>>();
                            for (int i=0; i< jsonArray.length(); i++)
                            {
                                System.out.println("array is "+jsonArray.get(i).toString());
                                MomentBean mb= gson.fromJson(jsonArray.get(i).toString(), MomentBean.class);
                                System.out.println("username is "+ mb.getUsername());
                                Map<String, Object> map= new HashMap<String, Object>();
                                map.put("username", mb.getUsername());
                                map.put("content", mb.getContent());
                                //list.add("username", (Object)mb.getUsername());
                                list.add(map);
                                System.out.println(list.get(i).get("username"));
                                //Intent intent = new Intent(this, PostMomentActivity.class);
                            }
                            //return inner_list;
                        }
                        catch (JSONException e)
                        {
                            System.out.println("aaa in try  ffff");
                            throw new RuntimeException();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "on error called in getmoment");
//                Log.d(TAG, error.getMessage());
                error.printStackTrace();
            }
        }, map);
        requestQueue.add(jsonRequest);
        Log.d(TAG, "AFTER VOLLEY");
        //System.out.println(list.get(0).get("username"));
        return list;
    }*/
    public void postMoment(MomentBean mb)
    {
        //// TODO: 11/2/16
<<<<<<< HEAD
        String url= "http://172.31.76.237/BCServer/servlet/postMoment";
=======
        String url= "http://192.241.179.166:8080/BCServer/servlet/postMoment";
>>>>>>> origin/master
        if (pContext==null) Log.d(TAG, "NO CONTEXT");
        RequestQueue requestQueue= Volley.newRequestQueue(pContext);
        HashMap<String, String> map= new HashMap<String, String>();
        //put information from mb into map
        map.put("username", mb.getUsername());
        map.put("content", mb.getContent());
        Log.d(TAG, "Name is "+ map.get("username"));
        Log.d(TAG, "Content is "+ map.get("content"));
        /*Request<JSONObject> request= new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "ON ERROR CALLED");
                       if (error!= null && error.toString().length()>0 ) Log.d(TAG, error.getMessage());
                        error.printStackTrace();;
                       // Log.d(TAG)
                    }
                }, map);*/
        Request<JSONObject> jsonRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "On response called");

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "on error called");
//                Log.d(TAG, error.getMessage());
                error.printStackTrace();
            }
        }, map);

        requestQueue.add(jsonRequest);

    }

    class PostMomentBinder extends Binder
    {
        PostMomentService getService() {return PostMomentService.this;}
    }
}
