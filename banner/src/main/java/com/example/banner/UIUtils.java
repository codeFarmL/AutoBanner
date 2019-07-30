package com.example.banner;

import android.content.Context;

public class UIUtils {

    public static int dip2px(Context context, float dpValue) {
        float sDensity = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * sDensity + 0.5f);
    }

}
