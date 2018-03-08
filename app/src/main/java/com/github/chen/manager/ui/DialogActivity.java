package com.github.chen.manager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;

public class DialogActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, DialogActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }
}
