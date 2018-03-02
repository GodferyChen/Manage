package com.github.chen.manager.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chen.library.LogHelper;
import com.github.chen.manager.R;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import static com.google.common.base.Strings.emptyToNull;

public class TestGuavaActivity extends AppCompatActivity {

    private static final String TAG = "TestGuavaActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, TestGuavaActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_guava);


        String value = "";
        Optional possible = Optional.fromNullable(emptyToNull(value));
        LogHelper.e(TAG, "value is " + possible.or("empty"));

        Preconditions.checkArgument(false,"hahhhhhhhh ","error return value","I don't known " +
                "what to do");



    }
}
