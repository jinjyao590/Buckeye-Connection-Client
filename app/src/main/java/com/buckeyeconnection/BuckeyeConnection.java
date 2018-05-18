package com.buckeyeconnection;

import android.app.Application;

import com.buckeyeconnection.chat.CustomUserProvider;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by yao.590 on 11/23/16.
 */
public class BuckeyeConnection extends Application {
    // appId、appKey 可以在「LeanCloud  控制台 > 设置 > 应用 Key」获取
    private final String APP_ID = "hLPAW2HqvoJk0G2pekxg7Dmn-MdYXbMMI";
    private final String APP_KEY = "jGBSg4z7KLmHJGpJ1JyiJ94T";

    @Override
    public void onCreate() {
        super.onCreate();
        // 关于 CustomUserProvider 可以参看后面的文档
    //LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
    LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
}
}
