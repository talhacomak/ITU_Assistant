package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LineView extends View {

    private final Paint paint = new Paint();
    private final PointF point1, point2;

    public LineView(Context context) {
        super(context);
        point1 = new PointF(0,0);
        point2 = new PointF(0, 0);
    }

    public LineView(Context context, @Nullable AttributeSet attributes){
        super(context, attributes);
        point1 = new PointF(0,0);
        point2 = new PointF(0, 0);
    }

    public LineView(Context context, @Nullable AttributeSet attributes, int defStyleAttr){
        super(context, attributes, defStyleAttr);
        point1 = new PointF(0,0);
        point2 = new PointF(0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        canvas.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
    }

    public void setPoints(PointF p1, PointF p2){
        point1.x = p1.x;
        point1.y = p1.y;
        point2.x = p2.x;
        point2.y = p2.y;
    }

    public void draw(){

        invalidate();
        requestLayout();
    }

}
