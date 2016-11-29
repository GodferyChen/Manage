package com.github.chen.manager.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.github.chen.library.LogHelper;
import com.github.chen.manager.R;
import com.github.chen.manager.retrofit.RetrofitHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by chen on 2016/11/25.
 */
public class SimpleWebActivity extends BaseActivity {

    private static final String ANDROID_CALLBACK = "androidcallback://";
    public static final int TYPE_DEFAULT = 0;

    private static final String ARG_TYPE = "ARG_TYPE";
    private static final String ARG_URL = "ARG_URL";
    private static final String ARG_TITLE = "ARG_TITLE";
    private static final String ARG_ACTION = "ARG_ACTION";

    private WebView webView;
    private int type;
    private String url;
    private ProgressBar progressBar;

    public static void start(Context context, String urlAddress, String action) {
        Intent starter = new Intent(context, SimpleWebActivity.class);
        starter.putExtra(ARG_TYPE, TYPE_DEFAULT);
        starter.putExtra(ARG_URL, urlAddress);
        starter.putExtra(ARG_ACTION, action);
        context.startActivity(starter);
    }

    public static void start(Context context, int pageType, String pageTitle, String urlAddress) {
        Intent starter = new Intent(context, SimpleWebActivity.class);
        starter.putExtra(ARG_TYPE, pageType);
        starter.putExtra(ARG_TITLE, pageTitle);
        starter.putExtra(ARG_URL, urlAddress);
        context.startActivity(starter);
    }

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_web);

        initToolbar();
        type = getIntent().getIntExtra(ARG_TYPE, TYPE_DEFAULT);
        url = getIntent().getStringExtra(ARG_URL);
        progressBar = (ProgressBar) findViewById(R.id.web_progress_bar);
        webView = (WebView) findViewById(R.id.web_view);
        RetrofitHelper.webCookie(mActivity);//设置cookie
        setUpWebViewDefaults(webView);
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore the previous URL and history stack
            webView.restoreState(savedInstanceState);
        }
        webView.addJavascriptInterface(this, "nativeMethod");
        webView.loadUrl(url);
    }

    /**
     * Convenience method to set some generic defaults for a
     * given WebView
     *
     * @param webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        // We set the WebViewClient to ensure links are consumed by the WebView rather
        // than passed to a browser if it can
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeAllViews();
            webView.clearHistory();
            webView.destroy();
        }
        super.onDestroy();
    }

    /**
     * 设置回退
     * 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(webView != null && webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){
            webView.goBack();// goBack()表示返回WebView的上一页面
            return true;
        }else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogHelper.d(TAG, "shouldOverrideUrlLoading --------> " + url);
            String string;
            try {
                string = URLDecoder.decode(url, "utf-8");
                url = string;
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            LogHelper.d(TAG,url);
            if (url.startsWith(ANDROID_CALLBACK)) {
                url = url.substring(ANDROID_CALLBACK.length());
                JSONTokener jsonParser = new JSONTokener(url);
                JSONObject jo = null;
                try {
                    jo = (JSONObject) jsonParser.nextValue();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jo == null) {
                    LogHelper.tS(mActivity, "Service Error");
                    // setResult(RESULT_CANCELED);
                    // finish();
                    return true;
                }
            } else {
                if (RetrofitHelper.networkEnable(mActivity,false)) {
                    progressBar.setVisibility(View.VISIBLE);
                    onLoadResource(view, url);
                    view.loadUrl(url);
                } else {
                    view.loadUrl(RetrofitHelper.ERRH5);
                }
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.local_obj.showSource(''+"
                    + "document.getElementsByTagName('title')[0].innerHTML+'');");
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            LogHelper.tS(mActivity, getString(R.string.http_unknown));
            finish();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            LogHelper.d(TAG,"onJsAlert ---------> view = [" + view + "], url = [" + url + "], " +
                    "message = [" + message + "], result = [" + result + "]");
            switch (message){
                case "提交成功":

                    break;
                case "请先登录":

                    break;
            }
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            LogHelper.d(TAG,"onShowFileChooser-------->");
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            LogHelper.d(TAG,"onProgressChanged--------->");
            if(newProgress > 0 && newProgress < 100){
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }else {
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            LogHelper.d(TAG,"onReceivedTitle-------->" + title);
            mToolbar.setTitle(title);
            super.onReceivedTitle(view, title);
        }
    }
}
