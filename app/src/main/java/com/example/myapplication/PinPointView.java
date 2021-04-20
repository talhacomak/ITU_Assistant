package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PinPointView extends View {

    Paint paint = new Paint();
    PointF point = new PointF();
    Paint dummyPaint = new Paint();
    int pinPointId;


    public PinPointView(Context context) {
        super(context);
    }

    public PinPointView(Context context, @Nullable AttributeSet attributes){
        super(context, attributes);
    }

    public PinPointView (Context context, @Nullable AttributeSet attributes, int defStyleAttr){
        super(context, attributes, defStyleAttr);
    }

    public PinPointView (Context context, int pinPointId, float x, float y) {
        super(context);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        dummyPaint.setColor(Color.WHITE);
        dummyPaint.setAlpha(125);
        this.pinPointId = pinPointId;
        point.x = x;
        point.y = y;
    }

    public void init (int pinPointId, float x, float y){
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        dummyPaint.setColor(Color.WHITE);
        dummyPaint.setAlpha(125);
        this.pinPointId = pinPointId;
        point.x = x;
        point.y = y;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float posx = point.x;
        float posy = point.y;
        paint.setColor(Color.GRAY);
        canvas.drawCircle(posx, posy, 25 * 2 , paint);

        paint.setColor(Color.WHITE);
        canvas.drawLine(posx, posy - (30), posx, posy + (30), paint);
        canvas.drawLine(posx - (30), posy, posx + (30), posy, paint);

        paint.setColor(Color.RED);
        paint.setTextSize(20);
        canvas.drawText("" + pinPointId, posx - (14f * 2), posy - (14f * 2), paint);
    }
}
