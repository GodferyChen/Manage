package com.sample.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.sample.aidl.DeviceEngine;

/**
 * Created by chen on 2017/5/21.
 */

public class DemoService extends Service {

    public class DemoServiceImpl extends DeviceEngine.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double
                aDouble, String aString) throws RemoteException {

        }

        @Override
        public String helloAndroidAIDL(String name) throws RemoteException {
            return "Hello world";
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new DemoServiceImpl();
    }
}
