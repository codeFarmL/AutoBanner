package com.example.autobanner

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.banner.AutoBanner
import com.example.banner.AutoBannerConfig
import com.example.banner.transformer.GalleryTransformer
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
        bannerLinear!!.gravity = AutoBannerConfig.CENTER
        bannerLinear!!.imageLoader = (object : AutoBanner.IImageLoader {
            override fun disPlayImage(context: Context, imageView: ImageView, url: String) {
                ImageLoader.loadImage(context, imageView, url);
                //imageView.setImageResource(R.mipmap.ic_launcher)
            }

        })
        bannerLinear!!.viewClick = object : AutoBanner.IViewClick {
            override fun viewClick(position: Int) {
                Log.e("MainActivity", position.toString());
            }
        }
        val array1 = ArrayList<View>()
        var view = LayoutInflater.from(this).inflate(R.layout.item_banner_cus_view, null, false);
        var view1 = LayoutInflater.from(this).inflate(R.layout.item_banner_cus_view, null, false);
        var view2 = LayoutInflater.from(this).inflate(R.layout.item_banner_cus_view, null, false);
        array1.add(view)
        array1.add(view1)
        array1.add(view2)
        var num = 0;
        bannerLinear!!.viewLoader = (object : AutoBanner.IViewLoader {
            @SuppressLint("ResourceType")
            override fun disPlayView(context: Context, view: View) {
                ;
                num++
                if (num == 1) {
                    view.findViewById<LinearLayout>(R.id.ll).setBackgroundColor(Color.BLUE)
                } else if (num == 2) {
                    view.findViewById<LinearLayout>(R.id.ll).setBackgroundColor(Color.RED)
                }

                //view.setBackgroundResource(R.color.)
            }

        })
        bannerLinear!!.transformer = GalleryTransformer();
        bannerLinear!!.autoPlay = false;
        bannerLinear!!.viewPager!!.setOffscreenPageLimit(3)
        bannerLinear!!.isLimitless = false;
        bannerLinear!!.setCustomView(array1)

        /****************************************/


        bannerFrame = findViewById(R.id.bannerFrame);
        bannerFrame!!.autoPlay = false;
        bannerFrame!!.imageLoader = (object : AutoBanner.IImageLoader {
            override fun disPlayImage(context: Context, imageView: ImageView, url: String) {
                ImageLoader.loadImage(context, imageView, url);
                //imageView.setImageResource(R.mipmap.ic_launcher)
            }

        })
        bannerFrame!!.isLimitless = true;
        bannerFrame!!.setImageUrls(array)


        /****************************************/


        bannerFrameDefault = findViewById(R.id.bannerFrame_indicatorDefault)
        bannerFrameDefault!!.autoPlay = true;
        bannerFrameDefault!!.imageLoader = (object : AutoBanner.IImageLoader {
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
        bannerFrameOnlyOnePic!!.imageLoader = (object : AutoBanner.IImageLoader {
            override fun disPlayImage(context: Context, imageView: ImageView, url: String) {
                ImageLoader.loadImage(context, imageView, url);
                //imageView.setImageResource(R.mipmap.ic_launcher)
            }

        })
        bannerFrameOnlyOnePic!!.setImageUrls(array)
    }


}
