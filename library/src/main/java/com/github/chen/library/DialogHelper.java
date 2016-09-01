package com.github.chen.library;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.WindowManager;

/**
 * 通用的菊花对话框
 */
public class DialogHelper {
    public static ProgressDialog showProgressDialog(Activity activity, int
            message) {
        return showProgressDialog(activity, activity.getString(message));
    }

    public static ProgressDialog showProgressDialog(Activity activity, String
            message) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (!activity.isFinishing())
            progressDialog.show();
        return progressDialog;
    }

    /**
     * 全局对话框，在任何时候都能使用
     *
     * @param context
     * @param message
     * @return
     */
    public static AlertDialog showSystemDialog(Context context, int title, int
            message) {
        if (title > 0) {
            return showSystemDialog(context, context.getResources()
                    .getString(title), context.getResources()
                    .getString(message));
        } else {
            return showSystemDialog(context, null, context.getResources()
                    .getString(message));
        }
    }

    public static AlertDialog showSystemDialog(Context context, String title,
                                               String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context
                .getApplicationContext(), R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage(message)
                .setCancelable(false)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
        return alert;
    }

    public static AlertDialog showWarningDialog(Activity activity,
                                                String title,
                                                String message,
                                                final OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity
                , R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onClickListener.onPositiveClick(dialog, id);
                    }
                })
                .setNegativeButton("取消", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onClickListener.onNegativeClick(dialog, which);
                    }
                });
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static void setDialogOnBackKey(Dialog dialog, final DialogInterface
            .OnKeyListener onKeyListener) {
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (onKeyListener != null && keyCode == KeyEvent.KEYCODE_BACK) {
                    return onKeyListener.onKey(dialog, keyCode, event);
                }
                if (onKeyListener == null && keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return false;
            }
        });
    }

    public interface OnClickListener {


        /**
         * 取消按钮
         *
         * @param dialog
         */
        public void onNegativeClick(DialogInterface dialog, int id);
        /**
         * 确定
         *
         * @param dialog
         * @param id
         */
        void onPositiveClick(DialogInterface dialog, int id);
    }
}
