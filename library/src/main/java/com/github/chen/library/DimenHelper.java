package com.github.chen.library;


import android.content.Context;
import android.util.TypedValue;

public class DimenHelper {

    /**
     * dp转像素
     */
    public static float dp2px(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources
                ().getDisplayMetrics());
    }

    /**
     * sp转像素
     */
    public static float sp2px(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * pt转像素
     */
    public static float pt2px(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, value, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * 英寸转像素
     */
    public static float in2px(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, value, context.getResources
                ().getDisplayMetrics());
    }

    /**
     * 毫米转像素
     */
    public static float mm2px(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, value, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    public static float custom(Context context, float value, int unit) {
        return TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics());
    }

}
