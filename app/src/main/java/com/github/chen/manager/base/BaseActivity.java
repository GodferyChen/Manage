package com.github.chen.manager.base;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.github.chen.manager.R;

/**
 * Created by chen on 2016/9/24.
 */

public class BaseActivity extends AppCompatActivity implements View.OnTouchListener, View
        .OnClickListener {

    protected final String TAG = this.getClass().getCanonicalName();

    protected Toolbar mToolbar;
    protected ActionBar mActionBar;
    protected Activity mActivity;
    protected AppContext mAppContext;
    protected boolean isAttachedToWindow, isOperate, isDestroy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //长按出现复制粘贴栏在顶部占位问题解决方法
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_MODE_OVERLAY);
        isDestroy = false;
        mActivity = this;
        mAppContext = (AppContext) getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttachedToWindow = true;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttachedToWindow = false;
    }

    protected void setPressAnim(View... views) {
        for (View view : views) {
            view.setOnTouchListener(this);
        }
    }

    protected void setClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onlyInitToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            if(mActionBar != null){
                mActionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
                mActionBar.setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public void initToolbar(){
        immersedByToolbar();
        initActionBarByToolbar();
    }

    private void immersedByToolbar(){
        if(Build.VERSION.SDK_INT >= 19){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if(Build.VERSION.SDK_INT >= 21){
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            //            initStatusBarByToolbar();
        }
    }

    //	private void initStatusBarByToolbar() {
//		Resources res = getResources();
//		int statusBarHeight = res.getDimensionPixelSize(res.getIdentifier("status_bar_height", "dimen", "android"));
//		View statusBarPlaceholder = findViewById(R.id.status_bar_place_holder);
//		if (statusBarPlaceholder != null) {
//			statusBarPlaceholder.getLayoutParams().height = statusBarHeight;
//		}
//	}

    private ActionBar initActionBarByToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar != null){
            mToolbar.setTitle(mActivity.getTitle());
            setSupportActionBar(mToolbar);
        }
        mActionBar = getSupportActionBar();
        if(mActionBar != null){
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
//            mActionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        }
        return mActionBar;
    }

    @Override
    public void onSupportActionModeStarted(@NonNull ActionMode mode) {
        int[] attrs = {R.attr.colorPrimary};
        TypedArray array = obtainStyledAttributes(attrs);
        int colorPrimary = array.getColor(0,0xFF000000);
        array.recycle();
        if(Build.VERSION.SDK_INT >= 21) getWindow().setStatusBarColor(colorPrimary);
        super.onSupportActionModeStarted(mode);
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        if(Build.VERSION.SDK_INT >= 21) getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onSupportActionModeFinished(mode);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (Build.VERSION.SDK_INT >= 21) {
                    view.setTranslationZ(getResources().getDisplayMetrics().density * 5f);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_POINTER_UP:
                if (Build.VERSION.SDK_INT >= 21) {
                    view.setTranslationZ(0f);
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
