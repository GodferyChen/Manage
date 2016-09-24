package com.github.chen.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.chen.library.LogHelper;
import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;

public class TextInputActivity extends BaseActivity {

    private TextInputEditText mName;
    private TextInputEditText mPhone;
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private TextInputEditText mFeedback;
    
    public static void start(Context context) {
        Intent starter = new Intent(context, TextInputActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        initToolbar();
        initView();
    }

    private void initView() {
        mName = (TextInputEditText) findViewById(R.id.edit_name);
        mPhone = (TextInputEditText) findViewById(R.id.edit_phone);
        mEmail = (TextInputEditText) findViewById(R.id.edit_email);
        mPassword = (TextInputEditText) findViewById(R.id.edit_password);
        mFeedback = (TextInputEditText) findViewById(R.id.edit_feedback);

        Button submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlAction();
            }
        });
    }

    private void controlAction() {
        if (mName.getText().toString().length() == 0) {
            LogHelper.tS(mActivity,"姓名不能为空!");
        } else if (mPhone.getText().toString().length() != 11) {
            LogHelper.tS(mActivity,"请正确填写11位手机号码!");
        } else if (mEmail.getText().toString().length() == 0 || !android.util.Patterns.EMAIL_ADDRESS
                .matcher(mEmail.getText().toString()).matches()) {
            LogHelper.tS(mActivity,"请正确填写邮箱地址!");
        } else if (mPassword.getText().toString().length() == 0) {
            LogHelper.tS(mActivity,"密码不能为空");
        } else if (mFeedback.getText().toString().length() == 0) {
            LogHelper.tS(mActivity,"反馈意见不能为空!");
        } else if (mFeedback.getText().toString().length() > 10) {
            LogHelper.tS(mActivity,"反馈意见长度字数不能大于10!");
        } else {
            LogHelper.tS(mActivity,"恭喜您,数据正确!");
        }
    }

}
