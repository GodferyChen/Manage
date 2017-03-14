package com.github.chen.manager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.github.chen.manager.R;

/**
 * Created by chen on 2017/3/14.
 */

public class CircleProgress extends View {

    private static final String TAG = "CircleProgress";

    private final int UNIT_RADIUS = 80;
    private final int default_finished_color = Color.rgb(15,169,197);
    private final int default_unfinished_color = Color.rgb(221, 221, 221);
    private final int default_main_text_color = Color.rgb(15,169,197);
    private final int default_sub_text_color = Color.rgb(221, 221, 221);
    private final float default_main_text_size;
    private final float default_sub_text_size;
    private final float default_stroke_width;
    private final String default_main_text = "--";
    private final String default_sub_text = "总步数";
    private final float default_arc_angle = 360 * 0.25f;

    private Paint grayCirclePaint;
    private Paint progressPaint;
    private Paint mainTextPaint;
    private Paint subTextPaint;
    private float dp1;
    private float sp1;
    private int width;
    private int radius;//屏幕所能形成的最大圆的半径
    private int innerRadius;//内圆的半径，也就是要所能看见的圆的半径
    private RectF oval;
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
        sp1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, getContext()
                .getResources().getDisplayMetrics());

        default_main_text_size = 32*sp1;
        default_sub_text_size = 24*sp1;
        default_stroke_width = 4*dp1;

        innerRadius = (int) (dp1 * UNIT_RADIUS);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .CircleProgress,defStyleAttr,0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    private void initByAttributes(TypedArray attributes) {
        finishedStrokeColor = attributes.getColor(R.styleable.CircleProgress_circle_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.CircleProgress_circle_unfinished_color, default_unfinished_color);
        mainTextColor = attributes.getColor(R.styleable.CircleProgress_circle_main_text_color, default_main_text_color);
        mainTextSize = attributes.getDimension(R.styleable.CircleProgress_circle_main_text_size,
                default_main_text_size);
        subTextColor = attributes.getColor(R.styleable.CircleProgress_circle_sub_text_color,
                default_sub_text_color);
        subTextSize = attributes.getDimension(R.styleable.CircleProgress_circle_sub_text_size,
                default_sub_text_size);
        sweepDegree = attributes.getFloat(R.styleable.CircleProgress_circle_angle, default_arc_angle);
        strokeWidth = attributes.getFloat(R.styleable.CircleProgress_circle_stroke_width,
                default_stroke_width);
        mainText = attributes.getString(R.styleable.CircleProgress_circle_main_text);
        subText = attributes.getString(R.styleable.CircleProgress_circle_sub_text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        radius = width / 2;

        //进度条所绘制的那个圆圈
        oval = new RectF(radius - innerRadius + progressPaint.getStrokeWidth() / dp1,
                radius - innerRadius + progressPaint.getStrokeWidth() / dp1,
                radius + innerRadius - progressPaint.getStrokeWidth() / dp1,
                radius + innerRadius - progressPaint.getStrokeWidth() / dp1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画灰色的圆圈
        canvas.drawCircle(radius, radius, innerRadius - grayCirclePaint.getStrokeWidth() / dp1,
                grayCirclePaint);
        //画进度条的圆弧
        canvas.drawArc(oval, sweepStartDegree, sweepDegree, false, progressPaint);
        //画主要的字符
        float textHeight = mainTextPaint.descent() + mainTextPaint.ascent();
        float textBaseline = (width - textHeight) / dp1;
        canvas.drawText(default_main_text, (width - mainTextPaint.measureText(default_main_text)) / dp1,
                textBaseline,
                mainTextPaint);
        //画次要的字符
        float subTextBaseLine = width / 2 + innerRadius - (mainTextPaint.descent() - mainTextPaint
                .ascent()) / dp1;
        canvas.drawText(default_sub_text, (width - subTextPaint.measureText(default_sub_text) /
                        dp1), subTextBaseLine,
                subTextPaint);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    protected void initPainters() {
        //灰色的圆圈
        grayCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        grayCirclePaint.setStyle(Paint.Style.STROKE);
        grayCirclePaint.setColor(unfinishedStrokeColor);
        grayCirclePaint.setStrokeWidth(dp1 * strokeWidth);

        //进度条的圆圈
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(dp1 * strokeWidth);
        progressPaint.setColor(finishedStrokeColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//设置头尾圆润

        //圆圈中央的字符
        mainTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainTextPaint.setColor(mainTextColor);
        mainTextPaint.setTextSize(sp1 * mainTextSize);

        //圆圈底部的字符
        subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subTextPaint.setColor(subTextColor);
        subTextPaint.setTextSize(sp1 * subTextSize);
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
        if(this.progress > getMax()){
            this.progress = (int) getMax();
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
        if(max > 0){
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
}
