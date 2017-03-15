package com.github.chen.manager.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.github.chen.manager.R;


/**
 * Created by chen on 2017/3/14.
 */

public class CircleProgress extends View {

    private static final String TAG = "CircleProgress";

    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_STROKE_WIDTH = "stroke_width";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_MAIN_TEXT_COLOR = "main_text_color";
    private static final String INSTANCE_SUB_TEXT_COLOR = "sub_text_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_MAIN_TEXT_SIZE = "main_text_size";
    private static final String INSTANCE_SUB_TEXT_SIZE = "sub_text_size";
    private static final String INSTANCE_MAIN_TEXT = "main_text";
    private static final String INSTANCE_SUB_TEXT = "sub_text";

    private final int DEFAULT_FINISHED_COLOR = Color.rgb(15, 169, 197);
    private final int DEFAULT_UNFINISHED_COLOR = Color.rgb(221, 221, 221);
    private final int DEFAULT_MAIN_TEXT_COLOR = Color.rgb(15, 169, 197);
    private final int DEFAULT_SUB_TEXT_COLOR = Color.rgb(221, 221, 221);
    private final int DEFAULT_MAX = 100;
    private final float DEFAULT_MAIN_TEXT_SIZE;
    private final float DEFAULT_SUB_TEXT_SIZE;
    private final float DEFAULT_STROKE_WIDTH;
    private final String DEFAULT_MAIN_TEXT = "--";
    private final String DEFAULT_SUB_TEXT = "总步数";
    private final float DEFAULT_SWEEP_DEGREE = 360 * 0.25f;

    private Paint circlePaint;
    private Paint mainTextPaint;
    private Paint subTextPaint;
    private float dp1;
    private int width;
    private RectF rectF = new RectF();
    private final float sweepStartDegree = 90;//起点的度数
    private float sweepDegree = 0;//滑动的度数

    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private int mainTextColor;
    private int subTextColor;
    private int progress;
    private int max;
    private float strokeWidth;
    private float mainTextSize;
    private float subTextSize;
    private String mainText;
    private String subText;

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        dp1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getContext().getResources()
                .getDisplayMetrics());

        DEFAULT_MAIN_TEXT_SIZE = sp2px(getResources(), 18);
        DEFAULT_SUB_TEXT_SIZE = sp2px(getResources(), 12);
        DEFAULT_STROKE_WIDTH = dp2px(getResources(), 4);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .CircleProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    private void initByAttributes(TypedArray attributes) {
        finishedStrokeColor = attributes.getColor(R.styleable
                .CircleProgress_circle_finished_color, DEFAULT_FINISHED_COLOR);
        unfinishedStrokeColor = attributes.getColor(R.styleable
                .CircleProgress_circle_unfinished_color, DEFAULT_UNFINISHED_COLOR);
        mainTextColor = attributes.getColor(R.styleable.CircleProgress_circle_main_text_color,
                DEFAULT_MAIN_TEXT_COLOR);
        mainTextSize = attributes.getDimension(R.styleable.CircleProgress_circle_main_text_size,
                DEFAULT_MAIN_TEXT_SIZE);
        subTextColor = attributes.getColor(R.styleable.CircleProgress_circle_sub_text_color,
                DEFAULT_SUB_TEXT_COLOR);
        subTextSize = attributes.getDimension(R.styleable.CircleProgress_circle_sub_text_size,
                DEFAULT_SUB_TEXT_SIZE);
        sweepDegree = attributes.getFloat(R.styleable.CircleProgress_circle_angle,
                DEFAULT_SWEEP_DEGREE);
        strokeWidth = attributes.getDimension(R.styleable.CircleProgress_circle_stroke_width,
                DEFAULT_STROKE_WIDTH);
        setMax(attributes.getInteger(R.styleable.CircleProgress_circle_max, DEFAULT_MAX));
        setProgress(attributes.getInteger(R.styleable.CircleProgress_circle_progress, 0));
        mainText = TextUtils.isEmpty(attributes.getString(R.styleable
                .CircleProgress_circle_main_text)) ? DEFAULT_MAIN_TEXT : attributes.getString(R
                .styleable.CircleProgress_circle_main_text);
        subText = TextUtils.isEmpty(attributes.getString(R.styleable
                .CircleProgress_circle_sub_text)) ? DEFAULT_SUB_TEXT : attributes.getString(R
                .styleable.CircleProgress_circle_sub_text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        //进度条所绘制的那个圆圈
        rectF.set(strokeWidth / dp1, strokeWidth / dp1, width - strokeWidth / dp1, MeasureSpec
                .getSize(heightMeasureSpec) - strokeWidth / dp1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画灰色的圆圈
        circlePaint.setColor(unfinishedStrokeColor);
        canvas.drawArc(rectF, 0, 360, false, circlePaint);
        //画进度条的圆弧
        circlePaint.setColor(finishedStrokeColor);
        sweepDegree = ((float) getProgress() / getMax()) * 360;
        canvas.drawArc(rectF, sweepStartDegree, sweepDegree, false, circlePaint);
        //画主要的字符
        float textHeight = mainTextPaint.descent() + mainTextPaint.ascent();
        float textBaseline = (width - textHeight) / dp1;
        canvas.drawText(mainText, (width - mainTextPaint.measureText(mainText)) / dp1,
                textBaseline, mainTextPaint);
        //画次要的字符
        float subTextBaseLine = width - (subTextPaint.descent() - subTextPaint
                .ascent()) / dp1;
        canvas.drawText(getSubText(), (width - subTextPaint.measureText(getSubText())) /
                dp1, subTextBaseLine, subTextPaint);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    protected void initPainters() {
        //圆圈
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(dp1 * strokeWidth);
        circlePaint.setColor(unfinishedStrokeColor);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);//设置头尾圆润

        //圆圈中央的字符
        mainTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainTextPaint.setColor(mainTextColor);
        mainTextPaint.setTextSize(mainTextSize);

        //圆圈底部的字符
        subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subTextPaint.setColor(subTextColor);
        subTextPaint.setTextSize(subTextSize);
    }

    public int getFinishedStrokeColor() {
        return finishedStrokeColor;
    }

    public void setFinishedStrokeColor(int finishedStrokeColor) {
        this.finishedStrokeColor = finishedStrokeColor;
        this.invalidate();
    }

    public int getUnfinishedStrokeColor() {
        return unfinishedStrokeColor;
    }

    public void setUnfinishedStrokeColor(int unfinishedStrokeColor) {
        this.unfinishedStrokeColor = unfinishedStrokeColor;
        this.invalidate();
    }

    public void setMainTextColor(int mainTextColor) {
        this.mainTextColor = mainTextColor;
        this.invalidate();
    }

    public int getSubTextColor() {
        return subTextColor;
    }

    public void setSubTextColor(int subTextColor) {
        this.subTextColor = subTextColor;
        this.invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (this.progress > getMax()) {
            this.progress = getMax();
        }
        this.invalidate();
    }

    public int getMainTextColor() {
        return mainTextColor;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            this.invalidate();
        }
    }

    public float getMainTextSize() {
        return mainTextSize;
    }

    public void setMainTextSize(float mainTextSize) {
        this.mainTextSize = mainTextSize;
        this.invalidate();
    }

    public float getSubTextSize() {
        return subTextSize;
    }

    public void setSubTextSize(float subTextSize) {
        this.subTextSize = subTextSize;
        this.invalidate();
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
        this.invalidate();
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
        this.invalidate();
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE,super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_STROKE_WIDTH,getStrokeWidth());
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR,getUnfinishedStrokeColor());
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR,getFinishedStrokeColor());
        bundle.putInt(INSTANCE_MAIN_TEXT_COLOR,getMainTextColor());
        bundle.putInt(INSTANCE_SUB_TEXT_COLOR,getSubTextColor());
        bundle.putFloat(INSTANCE_MAIN_TEXT_SIZE,getMainTextSize());
        bundle.putFloat(INSTANCE_SUB_TEXT_SIZE,getSubTextSize());
        bundle.putInt(INSTANCE_MAX,getMax());
        bundle.putInt(INSTANCE_PROGRESS,getProgress());
        bundle.putString(INSTANCE_MAIN_TEXT,getMainText());
        bundle.putString(INSTANCE_SUB_TEXT,getSubText());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            final Bundle bundle = (Bundle) state;
            strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH);
            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR);
            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR);
            mainTextColor = bundle.getInt(INSTANCE_MAIN_TEXT_COLOR);
            subTextColor = bundle.getInt(INSTANCE_SUB_TEXT_COLOR);
            mainTextSize = bundle.getFloat(INSTANCE_MAIN_TEXT_SIZE);
            subTextSize = bundle.getFloat(INSTANCE_SUB_TEXT_SIZE);
            mainText = bundle.getString(INSTANCE_MAIN_TEXT);
            subText = bundle.getString(INSTANCE_SUB_TEXT);
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            initPainters();
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
