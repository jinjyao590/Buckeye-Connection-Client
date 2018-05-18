package com.buckeyeconnection.chat;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.Display;

import com.buckeyeconnection.LoginService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by wli on 15/12/4.
 * 实现自定义用户体系
 */
public class CustomUserProvider implements LCChatProfileProvider {

  private static CustomUserProvider customUserProvider;
  private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();
  private SharedPreferences sp;
  private Context context;

  public synchronized static CustomUserProvider getInstance(Context context) {
    if (null == customUserProvider) {
      customUserProvider = new CustomUserProvider(context);
    }
    return customUserProvider;
  }

  private CustomUserProvider(Context context) {
    this.context = context;
    sp = PreferenceManager.getDefaultSharedPreferences(context);
    int ct= sp.getInt("count_friends", 0);
    for (int i=0; i < ct; i++) {
      String json= sp.getString("friendInfo_"+i, null);
      try {
        JSONObject jsonObject = new JSONObject(json);
        String username = jsonObject.getString("username");
        String realName = jsonObject.getString("realname");
        partUsers.add(new LCChatKitUser(username, realName, "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

  private CustomUserProvider(String username) {

  }

  @Override
  public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {
    List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
    for (String userId : list) {
      for (LCChatKitUser user : partUsers) {
        if (user.getUserId().equals(userId)) {
          userList.add(user);
          break;
        }
      }
    }
    callBack.done(userList, null);
  }

  public List<LCChatKitUser> getAllUsers() {
    return partUsers;
  }
}
