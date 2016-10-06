package com.github.chen.manager.ui.login;

/**
 * Created by chen on 2016/10/6.
 */

public interface ILoginPresenter {

    void validateCredentials(String username, String password);

    void onDestroy();

}
