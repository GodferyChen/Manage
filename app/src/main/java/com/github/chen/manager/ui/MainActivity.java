package com.github.chen.manager.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.chen.library.LogHelper;
import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;
import com.github.chen.manager.ui.login.LoginActivity;
import com.google.common.base.Optional;
import com.sample.aidl.IMyAidlInterface;

import static com.google.common.base.Strings.emptyToNull;

public class MainActivity extends BaseActivity {

    IMyAidlInterface myAidlInterface;
    private Button btnAIDL;
    String appName = "unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initToolbar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        String value = "";
        Optional possible = Optional.fromNullable(emptyToNull(value));
        LogHelper.e(TAG,"value is "+possible.or("empty"));

        btnAIDL = (Button) findViewById(R.id.btn_aidl);
        appName = getPackageName();

        Intent intent = new Intent("com.sample.aidl.IMyAidlInterface");
        intent.setPackage("com.sample.myservice");
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void onClick(View v){
        Log.d(TAG, "onClick() called with: v = [" + v + "]");
        switch (v.getId()){
            case R.id.btn_image_loader:
                ImageLoaderActivity.start(mActivity);
                break;
            case R.id.btn_text_input:
                TextInputActivity.start(mActivity);
                break;
            case R.id.btn_login:
                LoginActivity.start(mActivity);
                break;
            case R.id.btn_select:
//                SelectViewActivity.start(mActivity);
                CircleActivity.start(mActivity);
                break;
            case R.id.btn_life_cycle:
                LifeCycleActivity.start(mActivity);
                break;
            case R.id.btn_image_upload:
                ImageUploadActivity.start(mActivity);
                break;
            case R.id.btn_aidl:
                try {
                    String msg = myAidlInterface.helloAndroidAIDL(appName);
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlInterface = IMyAidlInterface.Stub.asInterface(service);//获取服务对象
            btnAIDL.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
