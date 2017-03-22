package com.github.chen.library;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PackageHelper {

    /**
     * 获取App安装包信息
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context){
        Context mContent = context.getApplicationContext();
        PackageInfo info = null;
        try {
            info = mContent.getPackageManager().getPackageInfo(mContent.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(info == null){
            info = new PackageInfo();
        }
        return info;
    }

    public static int getVersionCode(Context context) {
        PackageInfo info = getPackageInfo(context.getApplicationContext());
        return info.versionCode;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo
                    (context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static CharSequence getUpdateInfo(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().
                    getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String dataName = appInfo.metaData.getString("updateInfo");
        return dataName;
    }

}
