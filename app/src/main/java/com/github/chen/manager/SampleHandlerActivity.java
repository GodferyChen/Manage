package com.github.chen.manager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 *  Handler correct use method to avoid memory leak
 * Created by chen on 2016/9/16.
 */

public class SampleHandlerActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        private final WeakReference<SampleHandlerActivity> mActivity;

        public MyHandler(SampleHandlerActivity activity){
            mActivity = new WeakReference<SampleHandlerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            SampleHandlerActivity activity = mActivity.get();
            if(activity != null){
                //...
            }
        }

    }

}
