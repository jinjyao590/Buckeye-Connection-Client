package com.buckeyeconnection;

//created by Dingying Lu

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class MomentListActivity extends AppCompatActivity {

    private boolean pBound;
    private GetMomentService pService= new GetMomentService();
    private final String TAG= "MomentListActivity";
    Context pContext;
    /*private ServiceConnection pServiceConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GetMomentService.GetMomentBinder getMomentBinder= (GetMomentService.GetMomentBinder)service;
            //RegisterService.LoginBinder postMomentBinder= (RegisterService.LoginBinder)service;
            getMomentBinder.getService();
            pBound= true;
            Log.d(TAG, "SERVICE CONNECTED BBBB");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            pBound= false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_list);
         pContext= getApplicationContext();

        //Intent pIntent= new Intent(this, GetMomentService.class);
        //bindService(pIntent, pServiceConnection, BIND_AUTO_CREATE);
        //Log.d(TAG, "BIND SERVICE CALLED");
        //ListView lv= (ListView)findViewById(R.id.listView);
        //List<Map<String, Object>> moment_list= new ArrayList<Map<String, Object>>();
        //int[]to= new int[]{R.id.moment_text, R.id.moment_user_name};
        //moment_list= getData();
        getData();
        Log.d(TAG, "AFTER GET DATA CALLED IN ON CREATE");
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
        /*if(pBound)
        {
            unbindService(pServiceConnection);
            pBound= false;
        }*/
    }

    private void getData()
    {
        String url= "http://192.241.179.166:8080/BCServer/servlet/getMoments";
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
                            /*moments= getSharedPreferences("pref", Context.MODE_PRIVATE);
                            moment_editor.putString("moments", response.toString());
                            moment_editor.commit();
                            Log.d(TAG,"share"+ moments.getString("moments", "nothing"));*/
                            Log.d(TAG, "AAA IN T");
                            System.out.println("aaa in try");
                            JSONArray jsonArray= response.getJSONArray("data");
                            //ARR= jsonArray;
                            System.out.println("aaa in try");
                            Gson gson= new Gson();
                            System.out.println(jsonArray.length()+" lentgh");
                            List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
                            for (int i=jsonArray.length()-1; i>=0; i--)
                            {
                                System.out.println("array is "+jsonArray.get(i).toString());
                                MomentBean mb= gson.fromJson(jsonArray.get(i).toString(), MomentBean.class);
                                System.out.println("username is "+ mb.getUsername());
                                Map<String, Object> map= new HashMap<String, Object>();
                                map.put("username", mb.getUsername());
                                map.put("content", mb.getContent());
                                //list.add("username", (Object)mb.getUsername());
                                list.add(map);
                                //System.out.println(list.get(i).get("username"));
                                //Intent intent = new Intent(this, PostMomentActivity.class);
                            }
                            String[] from= new String[]{"username", "content"};
                            int[] to = new int[]{R.id.moment_user_name, R.id.moment_text};
                            SimpleAdapter adapter= new SimpleAdapter(pContext, list, R.layout.momentlistitem, from, to);
                            ListView lv= (ListView)findViewById(R.id.listView);
                            lv.setAdapter(adapter);
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

    }

}
