# AutoBanner
AutoBanner是一款支持自动轮播的banner，主要是kt + Rxjava编写。支持单图和多图，支持指示点的位置变化，包括和轮播图同等级也可以在轮播图的下面，在轮播图的左面，中间，右面，支持指示点设置背景，支持指示点设置上下左右margin，支持设置自动轮播的停留时间，支持设置自动轮播的scroll时间等等·。

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
