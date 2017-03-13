package com.github.chen.manager.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by chen on 2017/3/13.
 */

public class MyService extends Service{

    private static final String TAG = "MyService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand:开始休眠20秒");
        try {
            Thread.sleep(1000*20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onStartCommand:结束休眠");
        return super.onStartCommand(intent, flags, startId);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MyService.class);
//        starter.putExtra();
        context.startService(starter);
    }
}
