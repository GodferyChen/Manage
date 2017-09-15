package com.github.chen.library;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

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
        return appInfo.metaData.getString("updateInfo");
    }

    public static String getAppPackageName(Context context) {
        String packageName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            packageName = pi.packageName;
            if (packageName == null || packageName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("PackageHelper", "Exception", e);
        }
        return packageName;
    }

    public static PublicKey getAppPublicKey(Context context) {
        PublicKey key = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(getAppPackageName(context),
                    PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(sign.toByteArray()));
            key = cert.getPublicKey();
        } catch (Exception e) {
            Log.e("PackageHelper", e.getMessage(), e);
        }
        return key;
    }

    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || packageName.isEmpty())
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void installApk(File file, Context ctx) {
        // 隐式意图
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);// 设置意图的动作
        intent.addCategory("android.intent.category.DEFAULT");// 为意图添加额外的数据
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");// 设置意图的数据与类型
        ctx.startActivity(intent);
    }

    public static boolean isMyApp(String appFilePath, Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(appFilePath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            String packageName = appInfo.packageName; // 得到安装包名称
            String myPackageName = getAppPackageName(context);
            if (myPackageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断手机是否ROOT
     */
    public static boolean isRoot() {
        boolean root = false;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }
        } catch (Exception e) {
        }
        return root;
    }

}
