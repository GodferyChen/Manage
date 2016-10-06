package com.github.chen.manager.ui.login;

import android.app.Activity;

import com.github.chen.manager.retrofit.entry.User;

/**
 * Created by chen on 2016/10/6.
 */

public class LoginPresenterImpl implements ILoginPresenter, ILoginInteractor.OnLoginFinishedListener {

    private ILoginView loginView;
    private ILoginInteractor loginInteractor;
    private Activity mActivity;

    public LoginPresenterImpl(Activity activity, ILoginView loginView){
        this.mActivity = activity;
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String username, String password) {
        if(loginView != null){
            loginView.showProgress();
        }
        loginInteractor.login(mActivity,username,password,this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUsernameError() {
        if(loginView != null){
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if(loginView != null){
            loginView.hideProgress();
            loginView.setPasswordError();
        }
    }

    @Override
    public void onSuccess(User user) {
        if(loginView != null){
            loginView.hideProgress();
            loginView.navigateToHome();
        }
    }

    @Override
    public void onFailure() {
        if(loginView != null){
            loginView.hideProgress();
            loginView.setNetWorkError();
        }
    }
}
