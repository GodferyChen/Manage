package com.github.chen.manager.ui.login;

/**
 * Created by chen on 2016/10/6.
 */

public interface ILoginView {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void setNetWorkError();
}
