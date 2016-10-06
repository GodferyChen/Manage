package com.github.chen.manager.retrofit.service;

import com.github.chen.manager.retrofit.entry.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chen on 2016/10/6.
 */

public interface UserService {

    /**
     * 登录
     * @param map <ul>
     *            <li>userName	string	身份证号码</li>
     *            <li>password	string	密码</li>
     *            </ul>
     * @return
     */
    @FormUrlEncoded // POST请求
    @POST("doctorApi/login")
    Call<User> generateLoginPost(@FieldMap Map<String,String> map);

}
