package com.github.chen.manager.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by chen on 2017/3/13.
 */

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public MyIntentService() {
        super("raki");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Log.d(TAG, "onStart");
        super.onStart(intent, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return super.onBind(intent);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        Log.d(TAG, "setIntentRedelivery");
        super.setIntentRedelivery(enabled);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent != null ? intent.getExtras().getString("param") : null;
        if(action != null && action.equals("oper1")){
            Log.d(TAG, "onHandleIntent: Operation1");
        }else if ((action != null && action.equals("oper2"))){
            Log.d(TAG, "onHandleIntent: Operation2");
        }
        Log.d(TAG, "onStartCommand:开始休眠2秒");
        try {
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onStartCommand:结束休眠");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    public static void start(Context context,String param) {
        Intent starter = new Intent(context, MyIntentService.class);
        starter.putExtra("param",param);
        context.startService(starter);
    }
}
