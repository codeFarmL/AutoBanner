package com.example.banner

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class AutoBannerViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {
    var isAbleScroll = true //是否能够滑动

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (isAbleScroll) {
            super.onTouchEvent(ev)
        } else {
            false
        }

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (isAbleScroll) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }
}

