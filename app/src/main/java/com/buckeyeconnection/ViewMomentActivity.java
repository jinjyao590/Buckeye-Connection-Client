package com.buckeyeconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewMomentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_moment);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.activity_view_moment, new String[] { "username",  "content" }, new int[] { R.id.moment_user_name, R.id.moment_text });
        //setAdapter(adapter);
    }

    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "摩托罗拉");
        //map.put("img", R.drawable.icon);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "诺基亚");
        //map.put("img", R.drawable.icon);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "三星");
       // map.put("img", R.drawable.icon);
        list.add(map);
        return list;
    }
}
