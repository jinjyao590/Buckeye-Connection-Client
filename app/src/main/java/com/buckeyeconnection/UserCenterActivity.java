package com.buckeyeconnection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.buckeyeconnection.chat.CustomUserProvider;
import com.buckeyeconnection.chat.MainActivity;
import com.buckeyeconnection.model.UserBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.leancloud.chatkit.LCChatKit;

public class UserCenterActivity extends AppCompatActivity {

    private static final String TAG= "UserCenterActivity";
    //private EditUserInfoService eService= new EditUserInfoService();
    private boolean eBound;
    protected Button friendButton;
    private SharedPreferences sp;
    FriendService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        //button
        //edit--> edit-->service-->activity
        //Button editUserInfoBtn= (Button)findViewById(R.id.edit_user_btn);
        friendButton = (Button) findViewById(R.id.friend_button);
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendList();
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(this, FriendService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        //bind to EditUserInfoService
        //Intent intent= new Intent(this, EditUserInfoService.class);
        //bindService(intent, eConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            FriendService.FriendBinder binder = (FriendService.FriendBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    //this method is called when "edit" button is clicked, attached to the button in layout file
    /*
    public void onEdit(View v) throws IOException
    {
        UserBean ub= new UserBean();
        if (eBound)
        {
            //call the method in Service
            ub= eService.getUserInfo();
        }
        if (ub!= null) {
            Intent editIntent = new Intent(this, EditUserInfoActivity.class);
            //UserBean ub= new UserBean();
            //get data
            editIntent.putExtra("user_name", ub.getUsername());
            editIntent.putExtra("user_gender", ub.getGender());
            editIntent.putExtra("user_nation", ub.getNation());
            editIntent.putExtra("user_major", ub.getMajor());
            startActivity(editIntent);
        }
    }*/

    public void viewInfo(View v) {
        Intent nIntent = new Intent(this, InfoActivity.class);
        startActivity(nIntent);
    }

    public void openFriendList() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String json= sp.getString("userInfo", "NULL");
        try {
            JSONObject jsonObject = new JSONObject(json);
            final String clientId = jsonObject.getString("username");
            if (mBound) {
                mService.getFriendsInfo(clientId);
            }
            LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance(UserCenterActivity.this));
            LCChatKit.getInstance().open(clientId, new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null == e) {
                        finish();
                        Intent intent = new Intent(UserCenterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        AVUser user = new AVUser();// 新建 AVUser 对象实例
                        user.setUsername(clientId);// 设置用户名
                        user.setPassword("123");// 设置密码
                        user.setEmail(clientId+"@osu.edu");// 设置邮箱
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    // 注册成功
                                    Toast.makeText(UserCenterActivity.this, "Register Succeed ", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 失败的原因可能有多种，常见的是用户名已经存在。
                                    Toast.makeText(UserCenterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Toast.makeText(UserCenterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
