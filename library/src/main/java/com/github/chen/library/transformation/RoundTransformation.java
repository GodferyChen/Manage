package com.github.chen.library.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @author chen
 * @date 2018/3/1
 * @Description 圆头像的
 * @version 1.0.0
 */
public class RoundTransformation extends BitmapTransformation {

    private static final String TAG = "RoundTransformation";
    private Context context;

    public RoundTransformation(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        int minEdge = Math.min(source.getWidth(), source.getHeight());
        int dx = (source.getWidth() - minEdge) / 2;
        int dy = (source.getHeight() - minEdge) / 2;

        // Init shader
        Shader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate(dx, dy);// Move the target area to center of the source bitmap
        shader.setLocalMatrix(matrix);

        // Init paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(shader);

        // Create and draw circle bitmap
        Bitmap output = pool.get(minEdge, minEdge, Bitmap.Config.ARGB_8888);
        if (output == null) {
            output = Bitmap.createBitmap(minEdge, minEdge, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(output);
        canvas.drawOval(new RectF(0, 0, minEdge, minEdge), paint);

        source.recycle();
        return output;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.random();
    }
}
