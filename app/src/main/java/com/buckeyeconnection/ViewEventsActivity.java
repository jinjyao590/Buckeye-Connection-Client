package com.buckeyeconnection;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.buckeyeconnection.model.EventBean;
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

public class ViewEventsActivity extends AppCompatActivity {


    private final  String TAG= "ViewEventsActivity";
    private Context context;
    private String eventContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        context= getApplicationContext();
        getData();
        //View itemLayout= getLayoutInflater().inflate(R.layout.event_list_item, null);

        /*Button joinButton= (Button)itemLayout.findViewById(R.id.event_detail);
        joinButton.getText().toString();
        Log.d(TAG, joinButton.getText().toString());
       joinButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(TAG, "ON CLICK DETAIL");
                LinearLayout parent= (LinearLayout)v.getParent();
                Button btn= (Button) parent.getChildAt(5);
                btn.setText("aaa");
                onDetail();
            }
        });*/
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
    }

    private void getData()
    {
        String url= "http://192.168.0.6:8080/BCServer/servlet/getEvents";
        if (context== null) Log.d(TAG, "no context");
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        Log.d(TAG, "getEvents");
        HashMap<String, String> map= new HashMap<String, String>();
        map.put("gg","123");
        JSONArray ARR= new JSONArray();
        //List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        Request<JSONObject> jsonRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.length()==0) Log.d(TAG, "NO RESPONSE");
                        Log.d(TAG, "On response called in getEvents");
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
                                EventBean eb= gson.fromJson(jsonArray.get(i).toString(), EventBean.class);
                                System.out.println("event name is "+ eb.getEventName());
                                Map<String, Object> map= new HashMap<String, Object>();
                                map.put("event", eb.getEventName());
                                map.put("place", eb.getPlace());
                                map.put("econtent", eb.getEventContent());
                                map.put("club", eb.getClub());
                                //list.add("username", (Object)mb.getUsername());
                                list.add(map);
                                //System.out.println(list.get(i).get("username"));
                                //Intent intent = new Intent(this, PostMomentActivity.class);
                            }
                            String[] from= new String[]{"event", "place",  "club", "econtent"};
                            int[] to = new int[]{R.id.event_name, R.id.event_place, R.id.event_club, R.id.event_content};
                            //SimpleAdapter adapter= new SimpleAdapter(context, list, R.layout.event_list_item, from, to);
                            ContentAdapter adapter= new ContentAdapter(context, list);
                            ListView lv= (ListView)findViewById(R.id.event_list_view);
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
       // return list;

    }

    public class ContentAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list= new ArrayList<>();

        public ContentAdapter(Context c, List<Map<String, Object>> list)
        {
            Log.d(TAG, "ContentAdapter");
            this.layoutInflater= LayoutInflater.from(c);
            this.list= list;
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Object getItem(int arg0)
        {
            return null;
        }

        @Override
        public long getItemId(int arg0)
        {
            return 0;
        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            //View view= convertView;

            if (convertView== null)
            {
                convertView= layoutInflater.inflate(R.layout.event_list_item, null);

            }
            TextView event_name_tv= (TextView) convertView.findViewById(R.id.event_name);
            event_name_tv.setText(list.get(position).get("event").toString());
            TextView event_content_tv= (TextView) convertView.findViewById(R.id.event_content);
            event_content_tv.setText(list.get(position).get("econtent").toString());
            TextView event_place_tv= (TextView) convertView.findViewById(R.id.event_place);
            event_place_tv.setText(list.get(position).get("place").toString());
            TextView event_club_tv= (TextView) convertView.findViewById(R.id.event_club);
            event_club_tv.setText(list.get(position).get("club").toString());
            Button join_button= (Button)convertView.findViewById(R.id.event_detail);
            join_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                   // int p= position;
                    onDetail();
                }
            });
            return convertView;
        }
    }

    private void onDetail()
    {
        String url= "http://192.168.0.6:8080/BCServer/servlet/joinEvents";
       /* if (context== null) Log.d(TAG, "no context");
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        Log.d(TAG, "joinEvents");*/
        HashMap<String, String> map= new HashMap<String, String>();
        //put information into map

        map.put("username", "DYL");
        TextView event_name_tv= (TextView) findViewById(R.id.event_name);
        map.put("activity", event_name_tv.getText().toString());
        Log.d(TAG, "Name is "+ map.get("username"));
        Log.d(TAG, "Name of activity is "+ map.get("activity"));

        RequestQueue queue= Volley.newRequestQueue(context);
        Request<JSONObject> jsonRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "On response called in join");

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "on error called");
//                Log.d(TAG, error.getMessage());
                error.printStackTrace();
            }
        }, map);
        queue.add(jsonRequest);
    }


}
