package com.github.chen.manager.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initToolbar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onClick(View v){
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
            default:
                break;
        }
    }

}
