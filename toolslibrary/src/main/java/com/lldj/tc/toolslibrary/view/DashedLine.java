package com.lldj.tc.toolslibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

public class DashedLine extends View {
    int mColor = Color.BLUE;
    public DashedLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DashedLine(Context context, AttributeSet attrs, int mColor) {
        super(context, attrs);
        this.mColor = mColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(mColor);
        PathEffect pathEffect = new DashPathEffect(new float[]{5,5,5,5},1);
        paint.setPathEffect(pathEffect);
        Path path = new Path();
        path.moveTo(0,10);
        path.lineTo(480,10);
        canvas.drawPath(path, paint);
    }
}