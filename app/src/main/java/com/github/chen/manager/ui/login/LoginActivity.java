package com.github.chen.manager.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.chen.library.EncryptHelper;
import com.github.chen.library.LogHelper;
import com.github.chen.library.ValidatorHelper;
import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;
import com.github.chen.manager.ui.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends BaseActivity implements ILoginView {

    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private ProgressDialog mProgressDialog;
    private ILoginPresenter presenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();

        mUserNameView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //尝试登录
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        presenter = new LoginPresenterImpl(mActivity, this);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //尝试登录
                attemptLogin();
            }
        });

    }

    /**
     * 说明:获取用户输入的内容并调用相应的验证方法
     */
    private void attemptLogin() {
        String userName = mUserNameView.getText().toString();
        String passWord = mPasswordView.getText().toString();

        if (!TextUtils.isEmpty(userName) && ValidatorHelper.isPasswordValid(mActivity, passWord)) {
            presenter.validateCredentials(userName, EncryptHelper.MD5StrLower32(passWord));
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    /**
     * 说明:show一个Dialog
     */
    private void showDialog() {
        mProgressDialog = new ProgressDialog(mActivity, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("正在验证...");
        mProgressDialog.show();
    }

    @Override
    public void showProgress() {
        showDialog();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void setUsernameError() {
        mUserNameView.setError("用户不存在");
    }

    @Override
    public void setPasswordError() {
        mPasswordView.setError("密码不正确");
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(mActivity, MainActivity.class));
        finish();
    }

    @Override
    public void setNetWorkError() {
        LogHelper.tS(mActivity, "未知错误");
    }
}
