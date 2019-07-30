# AutoBanner
AutoBanner是一款支持自动轮播的banner，主要是kt + Rxjava编写。支持单图和多图，支持指示点的位置变化，包括和轮播图同等级也可以在轮播图的下面，在轮播图的左面，中间，右面，支持指示点设置背景，支持指示点设置上下左右margin，支持设置自动轮播的停留时间，支持设置自动轮播的scroll时间等等·。

一： 自定义属性
        <attr name="banner_width" format="dimension"/> 轮播图的宽度
      
        <attr name="banner_height" format="dimension"/> 轮播图的高度
        
        <attr name="banner_indicator_height" format="dimension"/> 指示点的高度
        
        <attr name="banner_indicator_width" format="dimension"/> 指示点的宽度
        
        <attr name="banner_indicator_space" format="dimension"/> 指示点之间的间距
        
        <attr name="banner_indicator_start_margin" format="dimension"/> 指示点视图的左边距
        
        <attr name="banner_indicator_end_margin" format="dimension"/> 指示点视图的右边距
       
        <attr name="banner_indicator_bottom" format="dimension"/> 指示点内部的下边距
        
        <attr name="banner_indicator_top" format="dimension"/> 指示点内部的上边距
        
        <attr name="banner_indicator_background" format="reference"/> 指示点的背景颜色
        
        <attr name="banner_indicator_select_bg" format="reference"/> 指示点被选中的试图
        
        <attr name="banner_indicator_unSelect_bg" format="reference"/> 指示点没有被选中的试图
         
        <attr name="banner_is_auto_scroll" format="boolean"/> 是否可以自动滑动
        
        <attr name="banner_is_frameLayout" format="boolean"/> 是否是帧布局
        
        <attr name="banner_interval_time" format="integer"/> //自动轮播的停留时间
        
        <attr name="banner_scroll_time" format="integer"/> //自动轮播的滑动时间
二：依赖导入：

        allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
        
        dependencies {
	        implementation 'com.github.codeFarmL:AutoBanner:Tag'
	}
        
        
三： AutoBanner的使用：
    1，布局中的使用
    （1），线性布局的使用：
    
    <com.example.banner.AutoBanner
                android:id="@+id/bannerLinear"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                app:banner_is_frameLayout="false"
                app:banner_interval_time="2000"
                app:banner_height="185dp"
                app:banner_indicator_top="10dp"
                app:banner_indicator_width="5dp"
                app:banner_indicator_height="5dp"
                app:banner_indicator_select_bg="@drawable/bg_banner_select_indicator"
                app:banner_indicator_unSelect_bg="@drawable/bg_banner_unselect_indicator"
                android:layout_marginBottom="10dp"/>
        
    （2），帧布局的使用：
    <com.example.banner.AutoBanner
                android:id="@+id/bannerFrame"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                app:banner_is_frameLayout="true"
                app:banner_height="200dp"
                app:banner_indicator_bottom="10dp"
                app:banner_indicator_top="10dp"
                app:banner_indicator_width="5dp"
                app:banner_indicator_height="5dp"
                app:banner_indicator_background="@drawable/bg_indicator"
                app:banner_indicator_select_bg="@drawable/bg_banner_select_indicator"
                app:banner_indicator_unSelect_bg="@drawable/bg_banner_unselect_indicator"
        />
        
     （3），默认使用
     <com.example.banner.AutoBanner
                android:id="@+id/bannerFrame_indicatorDefault"
                android:layout_width="match_parent"
                app:banner_height="200dp"
                android:layout_height="200dp"
                android:layout_marginTop="30dp"

        />
     
     （4），处理单图的banner
      <com.example.banner.AutoBanner
                android:id="@+id/bannerFrame_one_pic"
                android:layout_width="match_parent"
                app:banner_height="200dp"
                android:layout_height="200dp"
                android:layout_marginTop="30dp"
        />
        
     2，代码中调用
     
        val array = ArrayList<String>()
        array.add("https://b-ssl.duitang.com/uploads/item/201505/01/20150501010111_2BrKP.jpeg")
        array.add("https://b-ssl.duitang.com/uploads/item/201412/02/20141202213236_NGdxu.jpeg")
        array.add("https://b-ssl.duitang.com/uploads/item/201410/29/20141029153633_GGTV3.jpeg")
        array.add("https://b-ssl.duitang.com/uploads/item/201610/02/20161002145901_wAMaz.jpeg")

        bannerFrame = findViewById(R.id.bannerFrame);
        bannerFrame!!.autoPlay = true；
        bannerFrame!!.setIImageLoader(object : AutoBanner.IImageLoader {
            override fun disPlayImage(context: Context, imageView: ImageView, url: String) {
                //自己处理图片的渲染。
                ImageLoader.loadImage(context, imageView, url)；
            }

        })
        bannerFrame!!.imageClick = object : AutoBanner.IImageClick {
            override fun imageClick(position: Int) {
              //imageView点击监听。
            }
        }
        bannerFrame!!.setImageUrls(array)
        

