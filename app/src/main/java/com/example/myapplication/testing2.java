package com.example.myapplication;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class testing2 extends AppCompatActivity {
    private boolean mIsScrolling = false;
    private static LineView lineView;
    Context c1 = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing2);
        RelativeLayout main_lay = findViewById(R.id.main_layout);
        PinPointView ppv1 = new PinPointView(c1, 1, 200.0f, 200.0f );
        PinPointView ppv2 = new PinPointView(c1, 2, 200.0f, 300.0f );
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ppv1.setLayoutParams(params);
        ppv2.setLayoutParams(params);
        main_lay.addView(ppv1);
        main_lay.addView(ppv2);
        //PinPointView ppv1 = findViewById(R.id.ppview1);
        //PinPointView ppv2 = findViewById(R.id.ppview2);
        //ppv1.init(200.0f, 200.0f);
        //ppv2.init(200.0f, 300.0f);
        ppv1.setOnTouchListener(new MoveViewTouchListener(ppv1));
        ppv2.setOnTouchListener(new MoveViewTouchListener(ppv2));
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
                //drawLine(mView);
                return true;
            }
        };

    }

    public void drawLine(View view){
        lineView.setPoints(new PointF(view.getX() + view.getWidth()/(float)2, view.getY() +
                view.getHeight()/(float)2), new PointF(view.getX() + view.getWidth()/(float)2,
                view.getY() + view.getHeight()/(float)2));
        lineView.draw();
    }
}
