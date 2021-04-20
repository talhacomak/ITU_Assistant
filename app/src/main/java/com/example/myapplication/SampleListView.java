package com.example.myapplication;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ListView;

public class SampleListView extends ListView {

    public SampleListView(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float mDiffX = 0;
        float mDiffY = 0;

        float mLastX = 0;
        float mLastY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // reset difference values
                mDiffX = 0;
                mDiffY = 0;

                mLastX = ev.getX();
                mLastY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                mDiffX += Math.abs(curX - mLastX);
                mDiffY += Math.abs(curY - mLastY);
                mLastX = curX;
                mLastY = curY;

                // don't intercept event, when user tries to scroll vertically
                if (mDiffX > mDiffY) {
                    return false; // do not react to horizontal touch events, these events will be passed to your list item view
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
}