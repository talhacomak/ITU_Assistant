package com.example.myapplication;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class testing extends AppCompatActivity {
    private static TextView test, test2;
    LinearLayout lay, lay2;
    private boolean mIsScrolling = false;
    private static LineView lineView;
    Context c1 = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);
        lay = findViewById(R.id.layout);
        lay2 = findViewById(R.id.layout2);
        test = findViewById(R.id.test);
        test2 = findViewById(R.id.test2);
        lineView = findViewById(R.id.lineView);
        drawLine();
        test.setOnTouchListener(new MoveViewTouchListener(lay));
        test2.setOnTouchListener(new MoveViewTouchListener(lay2));
    }


    public class MoveViewTouchListener implements View.OnTouchListener {
        private GestureDetector mGestureDetector;
        private View mView;

        public MoveViewTouchListener(View view) {
            mGestureDetector = new GestureDetector(view.getContext(), mGestureListener);
            mView = view;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (mGestureDetector.onTouchEvent(event)) {
                return true;
            }
            switch (event.getAction()){
                case MotionEvent.ACTION_UP:
                    if (mIsScrolling)
                        mIsScrolling = false;
                    break;
                case MotionEvent.ACTION_DOWN:
                    lineView.setPoints(new PointF(test.getX() + test.getWidth()/(float)2, test.getY() + test.getHeight()/(float)2), new PointF(test2.getX() + test.getWidth()/(float)2, test2.getY() + test2.getHeight()/(float)2));
                    lineView.draw();
                    break;
            }
            return false;
        }

        private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            private float mMotionDownX, mMotionDownY;

            @Override
            public boolean onDown(MotionEvent e) {
                mMotionDownX = e.getRawX() - mView.getTranslationX();
                mMotionDownY = e.getRawY() - mView.getTranslationY();
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                mIsScrolling = true;
                mView.setTranslationX(e2.getRawX() - mMotionDownX);
                mView.setTranslationY(e2.getRawY() - mMotionDownY);
                drawLine();
                return true;
            }
        };

    }

    public void drawLine(View view){
        lineView.setPoints(new PointF(lay.getX() + lay.getWidth()/2, lay.getY() + lay.getHeight()/2), new PointF(lay2.getX() + lay2.getWidth()/2, lay2.getY() + lay2.getHeight()/2));
        lineView.draw();
    }
    public void drawLine(){
        lineView.setPoints(new PointF(lay.getX() + lay.getWidth()/2, lay.getY() + lay.getHeight()/2), new PointF(lay2.getX() + lay2.getWidth()/2, lay2.getY() + lay2.getHeight()/2));
        lineView.draw();
    }
}
