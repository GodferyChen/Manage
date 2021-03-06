package com.github.chen.manager.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.github.chen.library.LogHelper;
import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;
import com.github.chen.manager.ui.login.LoginActivity;
import com.google.common.base.Optional;

import butterknife.OnClick;

import static com.google.common.base.Strings.emptyToNull;

public class MainActivity extends BaseActivity {

    String appName = "unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initToolbar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        appName = getPackageName();

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick() called with: v = [" + v + "]");
        switch (v.getId()) {
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
            case R.id.btn_sim_info:
                SimInfoActivity.start(mActivity);
                break;
            case R.id.btn_test_guava:
                TestGuavaActivity.start(mActivity);
                break;
            default:
                break;
        }
    }

}
