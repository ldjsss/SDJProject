package com.lldj.tc.toolslibrary.view;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 说明：ViewPager 可滑动控制 <br>
 */

public class ViewPagerSlide extends ViewPager {


    private boolean isSlidAble = true;

    public ViewPagerSlide(Context context) {
        super(context);
    }

    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isSlidAble && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isSlidAble && super.onInterceptTouchEvent(event);
    }

    /**
     * 是否可以滑动
     *
     * @param b false 禁止滑动
     */
    public void setSlidAble(boolean b) {
        this.isSlidAble = b;
    }

    //控制是否使用滑动的动画效果
    @Override
    public void setCurrentItem(int item) {
        if (isSlidAble) {
            super.setCurrentItem(item);
        } else {
            super.setCurrentItem(item, false);
        }

    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
