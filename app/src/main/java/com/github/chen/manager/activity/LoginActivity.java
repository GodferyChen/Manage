package com.github.chen.manager.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.chen.library.LogHelper;
import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends BaseActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ProgressDialog mProgressDialog;

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

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
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

        Button emailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //尝试登录
                attemptLogin();
            }
        });

    }

    /**
     * 说明:获取用户输入的内容并调用相应的验证方法
     */
    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (isEmailValid(email) && isPasswordValid(password)) {
            login();
        }
    }


    /**
     * 说明:验证邮箱格式
     *
     * @param email 邮箱地址
     * @return 邮箱地址格式正确或错误
     */
    private boolean isEmailValid(String email) {
        if (email.length() == 0) {
            LogHelper.tS(mActivity,"请输入邮箱!");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            LogHelper.tS(mActivity,"邮箱格式不正确!");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 说明:验证密码格式
     *
     * @param password 密码
     * @return 密码格式正确或错误
     */
    private boolean isPasswordValid(String password) {
        if (password.length() == 0) {
            LogHelper.tS(mActivity,"请输入密码!");
            return false;
        } else if (password.length() < 6) {
            LogHelper.tS(mActivity,"请输入至少6位密码!");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 说明:登录
     * <p>
     * 这里为了展示对话框,将对话框固定显示了3秒
     */
    private void login() {
        showDialog();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, 3000);
    }

    /**
     * 说明:show一个Dialog
     */
    private void showDialog() {
        mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("正在验证...");
        mProgressDialog.show();
    }

}
