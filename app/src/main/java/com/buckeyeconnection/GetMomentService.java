package com.buckeyeconnection;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.buckeyeconnection.model.MomentBean;
import com.buckeyeconnection.util.NormalPostRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingyinglu on 11/13/16.
 */
public class GetMomentService extends Service {

    private final String TAG= "GetMomentService";
    private final IBinder pBinder= new GetMomentBinder();
    private static Context pContext;

    public GetMomentService() {
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

    @Override
    public void onCreate() {
        pContext= getApplicationContext();
    }

    public List<Map<String, Object>> getMomentListFromServer()
    {
        String url= "http://172.31.76.237:8080/BCServer/servlet/getMoments";
        if (pContext== null) Log.d(TAG, "no context");
        RequestQueue requestQueue= Volley.newRequestQueue(pContext);
        Log.d(TAG, "getMoment");
        HashMap<String, String> map= new HashMap<String, String>();
        map.put("gg","123");
        List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        Request<JSONObject> jsonRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.length()==0) Log.d(TAG, "NO RESPONSE");
                        Log.d(TAG, "On response called in getMoments");
                        System.out.println("aaaa response is "+response.toString());
                        try {
                            //JSONObject obj = response.getJSONObject("data");
                            Log.d(TAG, "AAA IN T");
                            System.out.println("aaa in try");
                            JSONArray jsonArray= response.getJSONArray("data");
                            System.out.println("aaa in try");
                            Gson gson= new Gson();
                            System.out.println(jsonArray.length()+" lentgh");
                            List<Map<String, Object>> list= new ArrayList<>();
                            for (int i=0; i< jsonArray.length(); i++)
                            {
                                System.out.println("array is "+jsonArray.get(i).toString());
                                MomentBean mb= gson.fromJson(jsonArray.get(i).toString(), MomentBean.class);
                                //System.out.println()
                            }
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
        return list;
    }


    class GetMomentBinder extends Binder
    {
        GetMomentService getService() {return GetMomentService.this;}
    }
}
