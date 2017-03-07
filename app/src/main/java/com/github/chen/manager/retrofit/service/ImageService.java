package com.github.chen.manager.retrofit.service;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Created by chen on 2017/3/7.
 */

public interface ImageService {

    @Multipart
    @POST("/fileUpload/file")
    Call<JSONObject> uploadImage(@Part("file") File file,@QueryMap Map<String, Object> options);

}
