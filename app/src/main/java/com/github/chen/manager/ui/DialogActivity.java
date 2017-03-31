package com.github.chen.manager.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chen.manager.R;

public class DialogActivity extends Activity {
    
    public static void start(Context context) {
        Intent starter = new Intent(context, DialogActivity.class);
//        starter.putExtra();
        context.startActivity(starter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }
}
