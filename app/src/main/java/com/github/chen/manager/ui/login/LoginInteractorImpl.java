package com.github.chen.manager.ui.login;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.github.chen.manager.retrofit.RetrofitHelper;
import com.github.chen.manager.retrofit.entry.User;
import com.github.chen.manager.retrofit.service.UserService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chen on 2016/10/6.
 */

public class LoginInteractorImpl implements ILoginInteractor{
    @Override
    public void login(final Activity activity, String userName, String passWord, final OnLoginFinishedListener listener) {
        if(RetrofitHelper.networkEnable(activity,true)){
            UserService service = RetrofitHelper.service(UserService.class);
            Map<String,String> map = new HashMap<>();
            map.put("userName", userName);
            map.put("password", passWord);
            Call<User> call = service.generateLoginPost(map);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(RetrofitHelper.isSuccess(activity,response)){
                        User user = response.body();
                        user.setContext(activity);
                        try {
                            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
                            user.versionCode = packageInfo.versionCode;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        user.save();
                        listener.onSuccess(user);
                    }else {
                        listener.onFailure();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    listener.onFailure();
                }
            });
        }

    }
}
