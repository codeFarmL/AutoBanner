package com.example.banner

import android.content.Context
import android.os.Build
import android.view.animation.Interpolator
import android.widget.Scroller

class AutoBannerScroller : Scroller {
    var mDuration = 600;
    fun setDuration(mDuration: Int) {
        this.mDuration = mDuration;
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, interpolator: Interpolator?) : this(
        context, interpolator
        , context!!.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.HONEYCOMB
    )

    constructor(context: Context?, interpolator: Interpolator?, flywheel: Boolean) : super(
        context,
        interpolator,
        flywheel
    )

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

}