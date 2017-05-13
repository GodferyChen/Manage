package com.sample.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sample.aidl.IMyAidlInterface;

/**
 * Created by chen on 2017/5/14.
 */

public class MyService extends Service {

    public class MyServiceImpl extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double
                aDouble, String aString) throws RemoteException {

        }

        @Override
        public String helloAndroidAIDL(String name) throws RemoteException {
            Log.d("aidl", "helloAndroidAIDL: "+name);
            return "Raki: Service return value successful";
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceImpl();
    }
}
