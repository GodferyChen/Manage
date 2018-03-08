package com.github.chen.manager.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;
import com.github.chen.manager.service.MyIntentService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 为了研究生命周期
 * Created by chen on 2017/2/18.
 */

public class LifeCycleActivity extends BaseActivity {

    @BindView(R.id.btn_show)
    Button btnShow;

    public static void start(Context context) {
        Intent starter = new Intent(context, LifeCycleActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_life_cycle);
        ButterKnife.bind(this);

        MyIntentService.start(mActivity, "oper1");
        MyIntentService.start(mActivity, "oper2");
    }

    @OnClick(R.id.btn_show)
    void showDialog(View view) {
        Log.d(TAG, "showDialog: ");
//        new AlertDialog.Builder(this).setTitle("Alert").setMessage("This is a dialog")
//                .setPositiveButton("ok",null).create().show();
        DialogActivity.start(mActivity);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //当Activity布局发送改变时，即setContentView()或者addContentView()执行完毕就会调用
        //Activity中各种View的findViewById()方法都可以放在这里处理
        Log.d(TAG, "onContentChanged");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //onPostCreate()方法是在onCreate()方法彻底执行完毕的回调，onPostResume类似
        Log.d(TAG, "onPostCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("key", 111);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Activity被系统销毁了才会被调用
        Log.d(TAG, "onRestoreInstanceState");
    }
}
