package com.github.chen.manager.retrofit.service;

import com.github.chen.manager.retrofit.entry.LoginRequest;
import com.github.chen.manager.retrofit.entry.LoginResponse;
import com.github.chen.manager.retrofit.entry.RegisterRequest;
import com.github.chen.manager.retrofit.entry.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by chen on 2017/6/27.
 */

public interface LoginService {

    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);

}
