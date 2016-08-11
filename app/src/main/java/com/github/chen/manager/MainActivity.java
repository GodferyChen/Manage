package com.github.chen.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class MainActivity extends AppCompatActivity {

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
        Picasso.with(context)
                .load("http://tnb.hanyouapp.com/upload/201607/15/1468580192589.jpg")
                .centerCrop()
                .resize(250,250)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .transform(new CircleImageTransformation())
                .into(ivCircle);
        Picasso.with(context)
                .load("http://tnb.hanyouapp.com/upload/201607/15/1468580192589.jpg")
                .centerCrop()
                .resize(250,250)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(ivSquare);
        Glide.with(context)
                .load("http://tnb.hanyouapp.com/upload/201607/15/1468580192589.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_launcher)
                .animate(R.anim.alpha)
                .placeholder(R.mipmap.ic_launcher)
                .transform(new RoundTransformation(context))
                .into(ivGlide);
    }
}
