package com.github.chen.manager.ui.login;

import android.app.Activity;

import com.github.chen.manager.retrofit.entry.User;

/**
 * Created by chen on 2016/10/6.
 */

public interface ILoginInteractor {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess(User user);

        void onFailure();
    }

    void login(Activity activity, String username, String password, OnLoginFinishedListener listener);
}
