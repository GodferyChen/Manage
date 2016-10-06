package com.github.chen.manager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 *  Handler correct use method to avoid memory leak
 * Created by chen on 2016/9/16.
 */

public class HandlerActivity extends BaseActivity {

    private final MyHandler mHandler = new MyHandler(this);

    /**
     * Instances of anonymous classes do not hold an implicit
     * reference to their outer class when they are "static".
     */
    private static final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //...
        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, HandlerActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        // Post a message and delay its execution for 10 minutes.
        mHandler.postDelayed(mRunnable,1000*60*10);

        // Go back to the previous Activity.
        finish();
    }

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler extends Handler{

        private final WeakReference<HandlerActivity> mActivity;

        public MyHandler(HandlerActivity activity){
            mActivity = new WeakReference<HandlerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            HandlerActivity activity = mActivity.get();
            if(activity != null){
                //...
            }
        }

    }

}
