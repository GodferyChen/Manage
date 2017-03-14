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

    private final int STROKE_WIDTH = 4;
    private final int UNIT_RADIUS = 80;
    private final int default_finished_color = Color.WHITE;
    private final int default_unfinished_color = Color.rgb(72, 106, 176);
    private final int default_main_text_color = Color.rgb(66, 145, 241);
    private final int default_sub_text_color = Color.rgb(66, 145, 241);
    private final float default_main_text_size;
    private final float default_sub_text_size;
    private final float default_stroke_width;
    private final String default_main_text;
    private final String default_sub_text;
    private final float default_arc_angle = 360 * 0.8f;
    private Paint grayCirclePaint;
    private Paint progressPaint;
    private Paint mainTextPaint;
    private Paint subTextPaint;
    private float dp1;
    private int width;
    private int radius;//屏幕所能形成的最大圆的半径
    private int innerRadius;//内圆的半径，也就是要所能看见的圆的半径
    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private RectF oval;
    private float sweepStartDegree = 90;//起点的度数
    private float sweepDegree = 0;//滑动的度数
    private float strokeWidth;
    private int progress;
    private float max;
    private float mainTextSize = 32f;
    private float subTextSize = 20f;
    private int mainTextColor;
    private int subTextColor;
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
        float sp1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, getContext()
                .getResources().getDisplayMetrics());

        default_main_text_size = 32*sp1;
        default_sub_text_size = 24*sp1;
        default_stroke_width = 4*dp1;
        default_main_text = "--";
        default_sub_text = "总步数";

        innerRadius = (int) (dp1 * UNIT_RADIUS);

        //灰色的圆圈
        grayCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        grayCirclePaint.setStyle(Paint.Style.STROKE);
        grayCirclePaint.setColor(0x0F000000);
        grayCirclePaint.setStrokeWidth(dp1 * STROKE_WIDTH);

        //进度条的圆圈
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(dp1 * STROKE_WIDTH);
        progressPaint.setColor(0xFF0fa9c5);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//设置头尾圆润

        mainTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainTextPaint.setColor(0xFF0fa9c5);
        mainTextPaint.setTextSize(sp1 * mainTextSize);

        subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subTextPaint.setColor(0x0F000000);
        subTextPaint.setTextSize(sp1 * subTextSize);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .CircleProgress,defStyleAttr,0);
        initByAttributes(attributes);
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
        canvas.drawText("--", (width - mainTextPaint.measureText("--")) / dp1,
                textBaseline,
                mainTextPaint);
        //画次要的字符
        float subTextBaseLine = width / 2 + innerRadius - (mainTextPaint.descent() - mainTextPaint
                .ascent()) / dp1;
        canvas.drawText("总步数", (width - subTextPaint.measureText("总步数")) / dp1, subTextBaseLine,
                subTextPaint);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    protected void initPainters() {

    }
}
