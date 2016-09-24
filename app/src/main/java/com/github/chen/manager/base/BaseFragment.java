package com.github.chen.manager.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.chen.library.DimenHelper;

/**
 * Created by chen on 2016/9/24.
 */

public class BaseFragment extends Fragment implements View.OnTouchListener {

    protected String TAG = this.getClass().getCanonicalName();

    protected Activity mActivity;
    protected View mRootView;
    protected boolean isAttachedToWindow, isDestroy, isOperate;
    protected AppContext mAppContext;


    protected void setPressAnime(View... views) {
        for (View view : views) {
            view.setOnTouchListener(this);
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
//        BroadcastHelper.register(this, this);
        isDestroy = false;
        mAppContext = (AppContext) mActivity.getApplicationContext();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroy = true;
//        BroadcastHelper.unregister(this);
//        for (Call call : mCallList) {
//            if (call != null) call.cancel();
//        }
//        mCallList.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mRootView;
    }

    public void onAttachedToWindow() {
        isAttachedToWindow = true;
    }

    public void onDetachedFromWindow() {
        isAttachedToWindow = false;
    }

}
