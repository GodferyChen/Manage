package com.github.chen.manager.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.github.chen.manager.R;

/**
 * Created by chen on 2016/10/9.
 */

public class SlideDialView extends View {

    private float dp1;
    private int innerRadius;
    private int catWidth;
    private int catHeight;
    private Bitmap curCat;
    private int width;
    private int radius;
    private RectF oval;

    private Paint grayCirclePaint;
    private Paint lineCirclePaint;
    private Paint longScalePaint;
    private Paint shortScalePaint;
    private Paint progressPaint;
    private Paint numberPaint;
    private Paint catPaint;

    public SlideDialView(Context context) {
        this(context, null, 0);
    }

    public SlideDialView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideDialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dp1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getContext().getResources()
                .getDisplayMetrics());
        float sp1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, getContext()
                .getResources().getDisplayMetrics());
        innerRadius = (int) (dp1 * 80);

        curCat = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap
                .ic_sleep_morning_slide);
        float ratio = curCat.getWidth() * 1.0f / curCat.getHeight();
        catWidth = (int) (dp1 * 20);
        catHeight = (int) (catWidth / ratio);

        grayCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//这个标志位意指抗锯齿的
        grayCirclePaint.setStyle(Paint.Style.STROKE);
        grayCirclePaint.setColor(0x0F000000);
        grayCirclePaint.setStrokeWidth(dp1 * 16);

        lineCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineCirclePaint.setStyle(Paint.Style.STROKE);
        lineCirclePaint.setColor(0xFF24AFEE);
        lineCirclePaint.setStrokeWidth(dp1*6);

        longScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        longScalePaint.setColor(0xFF24AFEE);
        longScalePaint.setStrokeCap(Paint.Cap.ROUND);
        longScalePaint.setStrokeWidth(dp1*3);

        shortScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shortScalePaint.setColor(0xFF24AFEE);
        shortScalePaint.setStrokeCap(Paint.Cap.ROUND);
        shortScalePaint.setStrokeWidth(dp1*2);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(dp1*16);

        numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setColor(0xFF24AFEE);
        numberPaint.setTextSize(sp1*32);

        catPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        radius = width / 2;
        oval = new RectF(radius - innerRadius+progressPaint.getStrokeWidth()/2,
                radius-innerRadius+progressPaint.getStrokeWidth()/2,
                radius+innerRadius-progressPaint.getStrokeWidth()/2,
                radius+innerRadius-progressPaint.getStrokeWidth()/2);
        setMeasuredDimension(width,width);
    }
}
