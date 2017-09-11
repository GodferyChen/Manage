package com.github.chen.manager.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.github.chen.library.LogHelper;
import com.github.chen.manager.R;
import com.github.chen.manager.base.BaseActivity;
import com.github.chen.manager.retrofit.RetrofitHelper;
import com.github.chen.manager.retrofit.service.ImageService;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadActivity extends BaseActivity {

    private ImageView imageView;

    public static void start(Context context) {
        Intent starter = new Intent(context, ImageUploadActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxGalleryFinal.with(mActivity)
                        .image()
//                        .multiple()
                        .maxSize(1)
                        .imageLoader(ImageLoaderType.GLIDE)
                        .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                            @Override
                            protected void onEvent(ImageMultipleResultEvent resultEvent) throws
                                    Exception {
                                for (int i = 0; i < resultEvent.getResult().size(); i++) {
                                    MediaBean bean = resultEvent.getResult().get(i);
                                    File file = new File(bean.getOriginalPath());
                                    System.out.println("ImageUploadActivity.onEvent " + bean
                                            .getOriginalPath());
                                    imageView.setImageURI(Uri.fromFile(file));
                                    upload(file);
                                }
                            }
                        }).openGallery();
            }
        });
    }

    private void upload(File file) {
        ImageService service = RetrofitHelper.service(ImageService.class);
        Map<String, Object> map = new HashMap<>();
        map.put("fileType", "user");
        map.put("id", "123");
        Call<JSONObject> call = service.uploadImage(file, map);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject object = response.body();
                if (object != null) {
                    if (object.optInt("error_code", 0) == 0) {

                    } else {
                    }
                    LogHelper.tS(mAppContext, object.toString());
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                LogHelper.tS(mActivity, "未知");
            }
        });
    }

}
