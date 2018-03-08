package com.github.chen.manager.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.chen.library.LogHelper;
import com.github.chen.manager.R;
import com.github.chen.manager.retrofit.RetrofitHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        //设置cookie
        RetrofitHelper.webCookie(mActivity);
        setUpWebViewDefaults(webView);
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore the previous URL and history stack
            webView.restoreState(savedInstanceState);
        }
        //1.JavaScript调用java方法,由于4.2以下存在安全漏洞，即使加上JavascriptInterface注解也不推荐使用
        webView.addJavascriptInterface(new JavascriptInterface(), "nativeMethod");
        webView.loadUrl(url);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //java调用JavaScript，4.4及其以上可用，scriptString为Javascript代码
        //ValueCallback用来获取JavaScript的执行结果，这是一个异步调用，ValueCallback并不是在UI线程执行的
        webView.evaluateJavascript("scriptString", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

            }
        });
    }

    class JavascriptInterface {

        @android.webkit.JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SimpleWeb Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
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
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView != null && webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            webView.goBack();// goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewClient extends WebViewClient {

        /**
         *  2.JavaScript调用java方法，通过拦截所有webVIew的url跳转，执行自身的逻辑
         * @param view
         * @param url
         * @return
         */
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
            LogHelper.d(TAG, url);
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
                if (RetrofitHelper.networkEnable(mActivity, false)) {
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

        /**
         * 3.JavaScript调用java方法,此方法是android提供给JS调试在Native代码里打印日志信息的API
         * 同时也成了一种js与Native代码通信的方法
         * Js代码里调用console.log('xxx');
         * @param consoleMessage
         * @return
         */
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String msg = consoleMessage.message();//JavaScript输入的Log内容
            return super.onConsoleMessage(consoleMessage);
        }

        /**
         * 展示警告信息
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            LogHelper.d(TAG, "onJsAlert ---------> view = [" + view + "], url = [" + url + "], " +
                    "message = [" + message + "], result = [" + result + "]");
            switch (message) {
                case "提交成功":

                    break;
                case "请先登录":

                    break;
            }
            return super.onJsAlert(view, url, message, result);
        }

        /**
         * 展示确认信息
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        /**
         * 展示提示信息
         * 4.JavaScript调用java方法 鉴于onJsAlert和onJsConfirm使用率很高，所以倾向于用onJsPrompt()
         * Js调用 window.prompt(message,value);
         *
         * @param view
         * @param url
         * @param message
         * @param defaultValue
         * @param result
         * @return
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            //处理Js调用的逻辑
            result.confirm();
            return true;
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            LogHelper.d(TAG, "onShowFileChooser-------->");
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            LogHelper.d(TAG, "onProgressChanged--------->");
            if (newProgress > 0 && newProgress < 100) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            } else {
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            LogHelper.d(TAG, "onReceivedTitle-------->" + title);
            mToolbar.setTitle(title);
            super.onReceivedTitle(view, title);
        }
    }
}
