package com.example.autobanner

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.banner.AutoBanner
import com.example.banner.AutoBannerConfig
import com.example.banner.ImageLoader
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var bannerLinear: AutoBanner? = null;
    var bannerFrame: AutoBanner? = null;
    var bannerFrameDefault: AutoBanner? = null;
    var bannerFrameOnlyOnePic: AutoBanner? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val array = ArrayList<String>()
        array.add("https://b-ssl.duitang.com/uploads/item/201505/01/20150501010111_2BrKP.jpeg")
        array.add("https://b-ssl.duitang.com/uploads/item/201412/02/20141202213236_NGdxu.jpeg")
        array.add("https://b-ssl.duitang.com/uploads/item/201410/29/20141029153633_GGTV3.jpeg")
        array.add("https://b-ssl.duitang.com/uploads/item/201610/02/20161002145901_wAMaz.jpeg")

        bannerLinear = findViewById(R.id.bannerLinear);
        bannerLinear!!.autoPlay = true;
        bannerLinear!!.setIImageLoader(object : AutoBanner.IImageLoader {
            override fun disPlayImage(context: Context, imageView: ImageView, url: String) {
                ImageLoader.loadImage(context, imageView, url);
                //imageView.setImageResource(R.mipmap.ic_launcher)
            }

        })
        bannerLinear!!.imageClick = object : AutoBanner.IImageClick {
            override fun imageClick(position: Int) {
                Log.e("MainActivity", position.toString());
            }
        }
        bannerLinear!!.setImageUrls(array)

        /****************************************/


        bannerFrame = findViewById(R.id.bannerFrame);
        bannerFrame!!.autoPlay = false;
        bannerFrame!!.gravity = AutoBannerConfig.START
        bannerFrame!!.setIImageLoader(object : AutoBanner.IImageLoader {
            override fun disPlayImage(context: Context, imageView: ImageView, url: String) {
                ImageLoader.loadImage(context, imageView, url);
                //imageView.setImageResource(R.mipmap.ic_launcher)
            }

        })
        bannerFrame!!.setImageUrls(array)


        /****************************************/


        bannerFrameDefault = findViewById(R.id.bannerFrame_indicatorDefault)
        bannerFrameDefault!!.autoPlay = true;
        bannerFrameDefault!!.setIImageLoader(object : AutoBanner.IImageLoader {
            override fun disPlayImage(context: Context, imageView: ImageView, url: String) {
                ImageLoader.loadImage(context, imageView, url);
                //imageView.setImageResource(R.mipmap.ic_launcher)
            }

        })
        bannerFrameDefault!!.setImageUrls(array)

        /****************************************/


        array.clear()
        array.add("https://b-ssl.duitang.com/uploads/item/201505/01/20150501010111_2BrKP.jpeg")
        bannerFrameOnlyOnePic = findViewById(R.id.bannerFrame_one_pic)
        bannerFrameOnlyOnePic!!.autoPlay = false;
        bannerFrameOnlyOnePic!!.setIImageLoader(object : AutoBanner.IImageLoader {
            override fun disPlayImage(context: Context, imageView: ImageView, url: String) {
                ImageLoader.loadImage(context, imageView, url);
                //imageView.setImageResource(R.mipmap.ic_launcher)
            }

        })
        bannerFrameOnlyOnePic!!.imageClick = object : AutoBanner.IImageClick {
            override fun imageClick(position: Int) {
                Log.e("MainActivity", position.toString());
            }
        }
        bannerFrameOnlyOnePic!!.setImageUrls(array)
    }


}
