package com.buckeyeconnection;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.buckeyeconnection.model.UserBean;

public class EditUserInfoService extends Service {
    public EditUserInfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public UserBean getUserInfo()
    {
        // TODO: 10/31/16
        UserBean ub= new UserBean();
        return ub;
    }

    class EditUserInfoBinder extends Binder
    {
        EditUserInfoService getService()
        {
            return EditUserInfoService.this;
        }
    }
}


