package com.github.chen.manager.retrofit.entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chen on 2016/10/6.
 */

public class User implements Serializable {

    public static final String SP_VERSION_CODE = "versionCode";
    public static final String SP_ID = "id";
    public static final String SP_USER_NAME = "userName";
    public static final String SP_TITLE = "title";
    public static final String SP_SEX = "sex";
    public static final String SP_HEIGHT = "height";
    public static final String SP_WEIGHT = "weight";
    public static final String SP_BIRTH = "birth";
    public static final String SP_SMALL_PIC = "smallPic";
    public static final String SP_USER_MOBILE = "userMobile";
    public static final String SP_ADDRESS = "address";

    private Context ctx;

    public int versionCode = 0;    // 版本码
    public String userName = "";   // 用户名
    public String title = "";  // 昵称
    public int id = 0;      // 用户id
    public int sex = 0;      // 性别
    public int height = 160;    // 身高
    public int weight = 50;    // 体重
    public long birth = 0;      // 出生日期
    public String smallPic = "";   // 用户头像图片
    public String userMobile = "";  // 用户手机
    public String address = "";  // 地址

    public static User getLocalUser(Context context) {
        User user = new User(context);
        user.get();
        return user;
    }

    public static boolean loginOut(User user) {
        if (user.isLogin()) {
            user.clear();
            return true;
        }
        return false;
    }

    public User(Context context) {
        ctx = context;
    }

    public User(User user) {
        ctx = user.ctx;
        versionCode = user.versionCode;
        userName = user.userName;
        title = user.title;
        id = user.id;
        sex = user.sex;
        height = user.height;
        weight = user.weight;
        birth = user.birth;
        smallPic = user.smallPic;
        userMobile = user.userMobile;
        address = user.address;
    }

    public int getAge() {
        Calendar userCalendar = Calendar.getInstance();
        userCalendar.setTimeInMillis(birth);
        return Calendar.getInstance().get(Calendar.YEAR) - userCalendar.get(Calendar.YEAR);
    }

    public String getBirthDay(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(birth));
    }

    public void setContext(Context context) {
        ctx = context;
    }

    public boolean isLogin() {
        SharedPreferences preferences = preferences();
        int localId = preferences.getInt(SP_ID, 0);
        return localId > 0 && localId == id;
    }

    private SharedPreferences preferences() {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    private SharedPreferences.Editor editor() {
        return preferences().edit();
    }

    public void remove(String key) {
        editor().remove(key).apply();
    }

    private void clear() {
        editor().remove(SP_USER_NAME)
                .remove(SP_TITLE)
                .remove(SP_ID)
                .remove(SP_SEX)
                .remove(SP_HEIGHT)
                .remove(SP_WEIGHT)
                .remove(SP_BIRTH)
                .remove(SP_SMALL_PIC)
                .remove(SP_USER_MOBILE)
                .remove(SP_ADDRESS)
                .apply();
        versionCode = 0;    // 版本码
        userName = "";   // 用户名
        title = "";  // 昵称
        id = 0;      // 用户id
        sex = 0;      // 性别
        height = 160;    // 身高
        weight = 50;    // 体重
        birth = 0;      // 出生日期
        smallPic = "";   // 用户头像图片
        userMobile = "";  // 用户手机
        address = "";  // 地址
    }

    public void save() {
        editor().putInt(SP_VERSION_CODE, this.versionCode)
                .putString(SP_USER_NAME, userName)
                .putString(SP_TITLE, title)
                .putInt(SP_ID, id)
                .putInt(SP_SEX, sex)
                .putInt(SP_HEIGHT, height)
                .putInt(SP_WEIGHT, weight)
                .putLong(SP_BIRTH, birth)
                .putString(SP_SMALL_PIC, smallPic)
                .putString(SP_USER_MOBILE, userMobile)
                .putString(SP_ADDRESS, address)
                .apply();
    }

    private void get() {
        SharedPreferences preferences = preferences();
        if (preferences.contains(SP_ID)) {
            versionCode = preferences.getInt(SP_VERSION_CODE, 0);
            userName = preferences.getString(SP_USER_NAME, "");
            title = preferences.getString(SP_TITLE, "");
            id = preferences.getInt(SP_ID, 0);
            sex = preferences.getInt(SP_SEX, 0);
            height = preferences.getInt(SP_HEIGHT, 160);
            weight = preferences.getInt(SP_WEIGHT, 50);
            birth = preferences.getLong(SP_BIRTH, 0);
            smallPic = preferences.getString(SP_SMALL_PIC, "");
            userMobile = preferences.getString(SP_USER_MOBILE, "");
            address = preferences.getString(SP_ADDRESS, "");
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "ctx=" + ctx +
                ", versionCode=" + versionCode +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                ", sex=" + sex +
                ", height=" + height +
                ", weight=" + weight +
                ", birth=" + birth +
                ", smallPic='" + smallPic + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
