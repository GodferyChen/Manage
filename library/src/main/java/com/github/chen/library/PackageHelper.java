package com.github.chen.library;

import android.content.Context;
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

}
