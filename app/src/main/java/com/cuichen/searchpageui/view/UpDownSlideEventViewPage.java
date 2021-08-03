package com.cuichen.searchpageui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


//可以监听手势上下滑动的viewpage
public class UpDownSlideEventViewPage extends ViewPager {

    public UpDownSlideEventViewPage(@NonNull Context context) {
        super(context);
    }

    public UpDownSlideEventViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    private float downY = 0.0f;
    private float moveY = 0.0f;
    private int trigger = 66; //触发距离

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getRawY();
                Log.i("ccb", "downY: " + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = ev.getRawY();
                Log.i("ccb", "moveY: " + moveY);
                if (moveY - downY > trigger) {
                    if (upDownSlideListEvent != null) upDownSlideListEvent.onDown();
                    Log.i("ccb", "moveY onDown");
                } else if (moveY - downY < -trigger) {
                    Log.i("ccb", "moveY onUp");
                    if (upDownSlideListEvent != null) upDownSlideListEvent.onUp();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                downY = 0f;
                moveY = 0f;
                Log.i("ccb", "ACTION_UP: ");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }



    public interface UpDownSlideListEvent{
        void onUp();
        void onDown();
    }

    private UpDownSlideListEvent upDownSlideListEvent;
    public void setUpDownSlideListEvent(UpDownSlideListEvent event){
        upDownSlideListEvent = event;
    }


}
