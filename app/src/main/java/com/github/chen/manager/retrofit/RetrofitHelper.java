package com.github.chen.manager.retrofit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.SparseArray;
import android.webkit.CookieManager;

import com.github.chen.library.LogHelper;
import com.github.chen.manager.BuildConfig;
import com.github.chen.manager.R;
import com.github.chen.manager.retrofit.cookie.CookiesManager;
import com.github.chen.manager.retrofit.entry.User;
import com.github.chen.manager.ui.login.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static final String ERRH5 = "file:///android_asset/err.html";// 错误页面地址
    public static final String BASE_URL = "http://yun.kongtang120.com/";// 必须以 “/” 结束
    private static final String CODE_HEADER = "HTTP状态码：";
    private static final SparseArray<String> sCodeArray = new SparseArray<>();
    private static Retrofit sRetrofit;
    private static OkHttpClient sOkHttpClient;

    static {
        // 服务器仅接收到部分请求，但是一旦服务器并没有拒绝该请求，客户端应该继续发送其余的请求
        sCodeArray.put(100, CODE_HEADER + "100");
        // 服务器转换协议：服务器将遵从客户的请求转换到另外一种协议。
        sCodeArray.put(101, CODE_HEADER + "101");

        // 请求成功（其后是对GET和POST请求的应答文档）。
        sCodeArray.put(200, CODE_HEADER + "200");
        // 请求被创建完成，同时新的资源被创建。
        sCodeArray.put(201, CODE_HEADER + "201");
        // 供处理的请求已被接受，但是处理未完成。
        sCodeArray.put(202, CODE_HEADER + "202");
        // 文档已经正常地返回，但一些应答头可能不正确，因为使用的是文档的拷贝。
        sCodeArray.put(203, CODE_HEADER + "203");
        // 没有新文档。浏览器应该继续显示原来的文档。如果用户定期地刷新页面，而Servlet可以确定用户文档足够新，这个状态代码是很有用的。
        sCodeArray.put(204, CODE_HEADER + "204");
        // 没有新文档。但浏览器应该重置它所显示的内容。用来强制浏览器清除表单输入内容。
        sCodeArray.put(205, CODE_HEADER + "205");
        // 客户发送了一个带有Range头的GET请求，服务器完成了它。
        sCodeArray.put(206, CODE_HEADER + "206");

        // 多重选择。链接列表。用户可以选择某链接到达目的地。最多允许五个地址。
        sCodeArray.put(300, CODE_HEADER + "300");
        // 所请求的页面已经转移至新的url。
        sCodeArray.put(301, CODE_HEADER + "301");
        // 所请求的页面已经临时转移至新的url。
        sCodeArray.put(302, CODE_HEADER + "302");
        // 所请求的页面可在别的url下被找到。
        sCodeArray.put(303, CODE_HEADER + "303");
        // 未按预期修改文档。客户端有缓冲的文档并发出了一个条件性的请求（一般是提供If-Modified-Since头表示客户只想比指定日期更新的文档）。服务器告诉客户，原来缓冲的文档还可以继续使用。
        sCodeArray.put(304, CODE_HEADER + "304");
        // 客户请求的文档应该通过Location头所指明的代理服务器提取。
        sCodeArray.put(305, CODE_HEADER + "305");
        // 此代码被用于前一版本。目前已不再使用，但是代码依然被保留。
        sCodeArray.put(306, CODE_HEADER + "306");
        // 被请求的页面已经临时移至新的url。
        sCodeArray.put(307, CODE_HEADER + "307");

        // 服务器未能理解请求。
        sCodeArray.put(400, CODE_HEADER + "400");
        // 被请求的页面需要用户名和密码。
        sCodeArray.put(401, CODE_HEADER + "401");
        // 此代码尚无法使用。
        sCodeArray.put(402, CODE_HEADER + "402");
        // 对被请求页面的访问被禁止。
        sCodeArray.put(403, CODE_HEADER + "403");
        // 服务器无法找到被请求的页面。
        sCodeArray.put(404, CODE_HEADER + "404");
        // 请求中指定的方法不被允许。
        sCodeArray.put(405, CODE_HEADER + "405");
        // 服务器生成的响应无法被客户端所接受。
        sCodeArray.put(406, CODE_HEADER + "406");
        // 用户必须首先使用代理服务器进行验证，这样请求才会被处理。
        sCodeArray.put(407, CODE_HEADER + "407");
        // 请求超出了服务器的等待时间。
        sCodeArray.put(408, CODE_HEADER + "408");
        // 由于冲突，请求无法被完成。
        sCodeArray.put(409, CODE_HEADER + "409");
        // 被请求的页面不可用。
        sCodeArray.put(410, CODE_HEADER + "410");
        // "Content-Length" 未被定义。如果无此内容，服务器不会接受请求。
        sCodeArray.put(411, CODE_HEADER + "411");
        // 请求中的前提条件被服务器评估为失败。
        sCodeArray.put(412, CODE_HEADER + "412");
        // 由于所请求的实体的太大，服务器不会接受请求。
        sCodeArray.put(413, CODE_HEADER + "413");
        // 由于url太长，服务器不会接受请求。当post请求被转换为带有很长的查询信息的get请求时，就会发生这种情况。
        sCodeArray.put(414, CODE_HEADER + "414");
        // 由于媒介类型不被支持，服务器不会接受请求。
        sCodeArray.put(415, CODE_HEADER + "415");
        // 服务器不能满足客户在请求中指定的Range头。
        sCodeArray.put(416, CODE_HEADER + "416");
        sCodeArray.put(417, CODE_HEADER + "417");

        // 请求未完成。服务器遇到不可预知的情况。
        sCodeArray.put(500, CODE_HEADER + "500");
        // 请求未完成。服务器不支持所请求的功能。
        sCodeArray.put(501, CODE_HEADER + "501");
        // 请求未完成。服务器从上游服务器收到一个无效的响应。
        sCodeArray.put(502, CODE_HEADER + "502");
        // 请求未完成。服务器临时过载或当机。
        sCodeArray.put(503, CODE_HEADER + "503");
        // 网关超时。
        sCodeArray.put(504, CODE_HEADER + "504");
        // 服务器不支持请求中指明的HTTP协议版本。
        sCodeArray.put(505, CODE_HEADER + "505");
    }

    private RetrofitHelper() {
    }

    public static void init(Context context) {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)// 必须以 “/” 结束
                    .client(client(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static OkHttpClient client(Context context) {
        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .cookieJar(new CookiesManager(context))
                    .build();
        }
        return sOkHttpClient;
    }

    public static void webCookie(Context context) {
        CookieJar cookieJar = client(context).cookieJar();
        HttpUrl httpUrl = HttpUrl.parse(RetrofitHelper.BASE_URL);
        List<Cookie> list = cookieJar.loadForRequest(httpUrl);
        StringBuilder builder = new StringBuilder();
        for (Cookie cookie : list) {
            String str = cookie.toString();

            String replace = null;
            try {
                String pattern = ".*expires=(.+?);.*";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(str);
                replace = null;
                if (m.find()) {
                    String s = "expires=" + m.group(1);
                    replace = str.replace(s, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
                replace = str;
            }
            builder.append(replace);
//            System.out.println(replace);
            builder.append(";");
        }
        String cookies = builder.toString();
//        System.out.println(cookies);
//        cookies = "DiabetesDoctorCookie=7ZrZnoQuu%2B2J8Y5rTKpZIAth42%2BR4DB9zxcaIWugNwYo1o6SZufOLyfumn1l5EGpqTPwMklqmrkP%0AFS" +
//                "%2F32sApO4YQvoJqr7S2YZsSYl3zP8JdcwD89iEvKLF5YSm3YtAbzFxM4Lztjv0%3D; domain=tnb.hanyouapp.com; path=/;";
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(RetrofitHelper.BASE_URL, cookies);
    }

    @SuppressWarnings("unchecked")
    public static <T> T service(Class<T> service) {
        return (T) sRetrofit.create(service);
    }

    public static boolean isSuccess(Context context, Response response) {

        int httpCode = response.code();
        String statusCode = response.headers().get("Status-Code");
        if (!TextUtils.isEmpty(statusCode)) {
            String showMsg = response.headers().get("Show-Message");
            if (!TextUtils.isEmpty(showMsg) && showMsg.equals("1")) {
                String msg = response.headers().get("Message");
                try {
                    if (!TextUtils.isEmpty(msg))
                        LogHelper.tS(context, URLDecoder.decode(msg, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (httpCode == 200) {
                if (statusCode.equals("121020")) {
                    if (User.loginOut(User.getLocalUser(context))) {
//                        LoginActivity.start(context, "登录状态已失效，请重新登录");
//                        BroadcastHelper.send(Config.ACTION_USER_LOGOUT);
                    }
                }
                if (statusCode.equals("111001")) {
                    return true;
                }
            }
        } else {
            String msg = sCodeArray.get(httpCode);
            if (BuildConfig.DEBUG) {
                LogHelper.tS(context, msg);
            } else {
                LogHelper.tS(context, R.string.http_unknown);
            }
        }
        return false;
    }

    public static boolean networkEnable(Context context, boolean showToast) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        } else {
            if (showToast) LogHelper.tS(context, R.string.http_not_network);
        }
        try {
            User user = new User(context);
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo.versionCode != user.versionCode) {
                user.versionCode = packageInfo.versionCode;
                user.save();
//                BroadcastHelper.send(Config.ACTION_USER_LOGOUT);
                LoginActivity.start(context);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
