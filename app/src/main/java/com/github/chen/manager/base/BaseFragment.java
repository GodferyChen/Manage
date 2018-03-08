package com.github.chen.manager.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.chen.library.DimenHelper;
import com.github.chen.manager.R;

/**
 * Created by chen on 2016/9/24.
 */

public class BaseFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {

    protected String TAG = this.getClass().getCanonicalName();

    protected Activity mActivity;
    protected View mRootView;
    protected boolean isAttachedToWindow, isDestroy;
    protected AppContext mAppContext;
    protected Toolbar mToolbar;
    protected ActionBar mActionBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return mRootView;
    }

    protected void initRootView(int resId) {
        mRootView = View.inflate(mActivity, resId, null);
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
    }

    @SuppressLint("RestrictedApi")
    protected void initToolbarWithBack(String title) {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        if (mToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar
                        .DISPLAY_SHOW_TITLE);
                mActionBar.setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
    }

    protected void initToolbar(String title) {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
    }

    protected void setPressAnime(View... views) {
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (Build.VERSION.SDK_INT >= 21) {
                    view.setTranslationZ(DimenHelper.dip2px(mActivity, 5f));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_POINTER_UP:
                if (Build.VERSION.SDK_INT >= 21) {
                    view.setTranslationZ(DimenHelper.dip2px(mActivity, 0f));
                }
                break;
            default:
                break;
        }
        return false;
    }

    @CallSuper
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDestroy = false;
        mAppContext = (AppContext) mActivity.getApplicationContext();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }

    public void onAttachedToWindow() {
        isAttachedToWindow = true;
    }

    public void onDetachedFromWindow() {
        isAttachedToWindow = false;
    }

    @Override
    public void onClick(View v) {

    }
}
