package com.github.chen.manager.base;

import android.app.Application;

import com.github.chen.manager.retrofit.RetrofitHelper;

/**
 * Created by chen on 2016/9/24.
 */

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitHelper.init(this);
    }
}
