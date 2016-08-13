package com.github.chen.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.github.chen.library.DimenManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Context context = this;
        ImageView ivCircle = (ImageView) findViewById(R.id.iv_pic_circle);
        ImageView ivSquare = (ImageView) findViewById(R.id.iv_pic_square);
        ImageView ivGlide = (ImageView) findViewById(R.id.iv_pic_glide);
        final ImageView ivLoader = (ImageView) findViewById(R.id.iv_pic_imageloder);
        String url = "http://tnb.hanyouapp.com/upload/201607/15/1468580192589.jpg";
        Picasso.with(context)
                .load(url)
                .centerCrop()
                .resize(250, 250)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .transform(new CircleImageTransformation())
                .into(ivCircle);
        Picasso.with(context)
                .load(url)
                .centerCrop()
                .resize(250, 250)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(ivSquare);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_launcher)
                .animate(R.anim.alpha)
                .placeholder(R.mipmap.ic_launcher)
                .transform(new RoundTransformation(context))
                .into(ivGlide);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        ImageSize targetSize = new ImageSize((int) DimenManager.dp2px(context, 48), (int)
                DimenManager.dp2px(context, 48));
//        imageLoader.loadImage(url, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String s, View view) {
//                Log.d(TAG, "onLoadingStarted() called with: s = [" + s + "], view = [" + view + "]");
//            }
//
//            @Override
//            public void onLoadingFailed(String s, View view, FailReason failReason) {
//                Log.d(TAG, "onLoadingFailed() called with: s = [" + s + "], view = [" + view + "], failReason = [" + failReason + "]");
//            }
//
//            @Override
//            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                Log.d(TAG, "onLoadingComplete() called with: s = [" + s + "], view = [" + view + "], bitmap = [" + bitmap + "]");
//                ivLoader.setImageBitmap(bitmap);
//            }
//
//            @Override
//            public void onLoadingCancelled(String s, View view) {
//                Log.d(TAG, "onLoadingCancelled() called with: s = [" + s + "], view = [" + view + "]");
//            }
//        });
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader.loadImage(url, targetSize, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                ivLoader.setImageBitmap(loadedImage);
            }
        });

    }
}
