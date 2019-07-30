package com.example.autobanner;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;


public class ImageLoader {
    public static void loadImage(Context context, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

}
