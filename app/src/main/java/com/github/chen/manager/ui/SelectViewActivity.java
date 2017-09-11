package com.github.chen.manager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;
import com.github.chen.manager.widget.SlideDialView;

public class SelectViewActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SelectViewActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final SlideDialView slideDialView = (SlideDialView) findViewById(R.id.select_view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slideDialView.start(8000);
            }
        }, 500);
    }

}
