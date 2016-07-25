package com.std.ramsoapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Maik on 2016/1/20.
 */
public class ContentViewPager extends ViewPager {
    //去子控件,如果没有子控件则不触发 触摸事件
    public static boolean GO_TOUTH_CHILD = true;

    public ContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (GO_TOUTH_CHILD) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    //触摸方法 传给父控件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (GO_TOUTH_CHILD) {
            return false;
        }
        return super.onTouchEvent(ev);
    }
}
