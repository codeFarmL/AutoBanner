package com.example.banner

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import android.view.ViewGroup
import com.example.banner.transformer.DefaultTransformer


class AutoBanner : FrameLayout, ViewPager.OnPageChangeListener {

    var mContext: Context? = null;
    var layoutId: Int = R.layout.layout_auto_banner;

    var viewPager: AutoBannerViewPager? = null; //轮播
    var viewPagerWidth: Int = 0;
    var viewPagerHeight: Int = 0;
    var mImgViews: ArrayList<View> = arrayListOf(); //轮播View
    var myPageAdapter: MyPageAdapter? = null;
    var autoPlay: Boolean = false;
    var isScroll: Boolean = true;
    var isLimitless: Boolean = true; //是否是无界限的
    var imageLoader: IImageLoader? = null;
    var viewLoader: IViewLoader? = null
    var pageSelected: IPageSelected? = null
    var viewClick: IViewClick? = null;
    var size: Int = 0;
    var currentItem: Int = 0;

    var mLLIndicator: LinearLayout? = null; //轮播指示点
    var mIndicatorViews: ArrayList<ImageView> = arrayListOf(); //指示点的数据
    var indicatorTop: Int = 0; //如果是线性排布，是轮播指示点的上边距
    var indicatorBottom: Int = 0; //如果是帧性排布，是轮播指示点的下边距
    var indicatorWidth: Int = 0;
    var indicatorHeight: Int = 0;
    var indicatorWidthSelect: Int = 0;
    var indicatorHeightSelect: Int = 0;
    var indicatorMarginStart: Int = 0;
    var indicatorMarginEnd: Int = 0;
    var indicatorSpace: Int = 0;
    var indicatorSelect = R.drawable.bg_banner_select_indicator;
    var indicatorUnSelect = R.drawable.bg_banner_unselect_indicator;
    var indicatorBackground = 0;
    var bannerIsFrameLayout: Boolean = true;
    var lastPosition = 1;

    var intervalTime: Int = 0; //自动滑动的停留时间
    var scrollTime: Int = 0; //自动滑动的切换时间
    var mVpScroll: AutoBannerScroller? = null;
    var gravity: Int = AutoBannerConfig.CENTER;

    var isSetClipChildren = true;
    var clipChildrenMargin = 0;

    var transformer: ViewPager.PageTransformer = DefaultTransformer();

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context;
        if (attrs != null) {
            getTypeArray(context, attrs)
        };
        initView(context);
    }

    @SuppressLint("Recycle")
    fun getTypeArray(context: Context, attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.AutoBanner)
        viewPagerHeight =
            typeArray.getDimensionPixelSize(R.styleable.AutoBanner_banner_height, UIUtils.dip2px(mContext, 0f));
        indicatorTop = typeArray.getDimensionPixelSize(
            R.styleable.AutoBanner_banner_indicator_top,
            UIUtils.dip2px(mContext, 0f)
        );
        indicatorBottom = typeArray.getDimensionPixelSize(
            R.styleable.AutoBanner_banner_indicator_bottom,
            UIUtils.dip2px(mContext, 5f)
        );
        indicatorWidth =
            typeArray.getDimensionPixelSize(
                R.styleable.AutoBanner_banner_indicator_width,
                UIUtils.dip2px(mContext, 5F)
            )
        indicatorHeight = typeArray.getDimensionPixelSize(
            R.styleable.AutoBanner_banner_indicator_height,
            UIUtils.dip2px(mContext, 5F)
        )
        indicatorWidthSelect =
            typeArray.getDimensionPixelSize(
                R.styleable.AutoBanner_banner_indicator_width_select,
                UIUtils.dip2px(mContext, 5F)
            )
        indicatorHeightSelect = typeArray.getDimensionPixelSize(
            R.styleable.AutoBanner_banner_indicator_height_select,
            UIUtils.dip2px(mContext, 5F)
        )


        indicatorMarginStart = typeArray.getDimensionPixelSize(
            R.styleable.AutoBanner_banner_indicator_start_margin,
            UIUtils.dip2px(mContext, 20f)
        );
        indicatorMarginEnd = typeArray.getDimensionPixelSize(
            R.styleable.AutoBanner_banner_indicator_end_margin,
            UIUtils.dip2px(mContext, 20f)
        );
        indicatorSpace = typeArray.getDimensionPixelSize(
            R.styleable.AutoBanner_banner_indicator_space,
            UIUtils.dip2px(mContext, 3.5f)
        );

        bannerIsFrameLayout = typeArray.getBoolean(R.styleable.AutoBanner_banner_is_frameLayout, true);
        indicatorSelect = typeArray.getResourceId(
            R.styleable.AutoBanner_banner_indicator_select_bg,
            R.drawable.bg_banner_select_indicator
        )
        indicatorUnSelect = typeArray.getResourceId(
            R.styleable.AutoBanner_banner_indicator_unSelect_bg,
            R.drawable.bg_banner_unselect_indicator
        )

        indicatorBackground = typeArray.getResourceId(
            R.styleable.AutoBanner_banner_indicator_background,
            0
        )

        isSetClipChildren = typeArray.getBoolean(R.styleable.AutoBanner_banner_clipChildren, true);
        clipChildrenMargin = typeArray.getDimensionPixelSize(
            R.styleable.AutoBanner_banner_clipChildren_margin,
            UIUtils.dip2px(mContext, 0f)
        )
        autoPlay = typeArray.getBoolean(R.styleable.AutoBanner_banner_is_auto_scroll, true);
        intervalTime = typeArray.getInt(R.styleable.AutoBanner_banner_interval_time, 1000);
        scrollTime = typeArray.getInt(R.styleable.AutoBanner_banner_scroll_time, 1000);
        typeArray.recycle();
    }

    fun initView(context: Context) {
        this.mContext = context;
        val view = LayoutInflater.from(context).inflate(layoutId, this, true);
        viewPager = view.findViewById(R.id.vp);
        mLLIndicator = view.findViewById(R.id.indicator);
        viewPager!!.addOnPageChangeListener(this)
        if (!isSetClipChildren) {
            var fl = view.findViewById<FrameLayout>(R.id.fl)
            viewPager!!.clipChildren = false;
            fl!!.clipChildren = false;
            viewPager!!.post(object : Runnable {
                override fun run() {
                    var vpLayoutParams: LayoutParams = viewPager!!.layoutParams as LayoutParams;
                    vpLayoutParams.setMargins(
                        clipChildrenMargin,
                        0,
                        clipChildrenMargin,
                        0
                    )
                    viewPager!!.setLayoutParams(vpLayoutParams);
                }

            })
        }


        setViewPagerScroll();

    }

    //设置viewPager scroll
    fun setViewPagerScroll() {
        try {
            val mField = ViewPager::class.java.getDeclaredField("mScroller")
            mField.isAccessible = true
            mVpScroll = AutoBannerScroller(viewPager!!.getContext())
            mVpScroll!!.setDuration(scrollTime)
            mField.set(viewPager, mVpScroll)
        } catch (e: Exception) {
        }

    }

    //设置轮播指示点的位置（线性布局和帧布局的切换）
    fun setIndicatorLocation() {
        if (mIndicatorViews.size >= 0) {
            if (!bannerIsFrameLayout) {
                val mVpLayoutParas = viewPager!!.layoutParams as LayoutParams;
                mVpLayoutParas.height = viewPagerHeight;
                viewPager!!.layoutParams = mVpLayoutParas;
            }
            mLLIndicator!!.gravity = gravity or Gravity.BOTTOM
            if (indicatorBackground != 0) {
                mLLIndicator!!.setBackgroundResource(indicatorBackground)
            }
        }

    }

    fun setCustomView(viewUrls: ArrayList<View>) {
        mImgViews.clear();
        size = viewUrls.size;
        initCurrentItem()
        createIndicatorImages();
        setIndicatorLocation();
        if (isLimitless) {
            lastPosition = 1;
            for (i in 0..size + 1) {
                handleCusView(viewUrls, i)
            }

        } else {
            lastPosition = 0;
            for (i in 0 until size) {
                handleCusView(viewUrls, i)
            }
        }
        setAdapter();
        if (autoPlay) {
            startAuto();
        }
    }

    private fun handleCusView(viewUrls: ArrayList<View>, i: Int) {
        var view = viewUrls[i];
        if (viewClick != null) {
            view.setOnClickListener { viewClick!!.viewClick(i) }
        }
        mImgViews.add(view)
        if (viewLoader != null) {
            mContext?.let { viewLoader!!.disPlayView(it, view, i) }
        }
    }

    fun setImageUrls(imgUrls: ArrayList<String>) {
        mImgViews.clear();
        size = imgUrls.size;
        initCurrentItem()
        createIndicatorImages();
        setIndicatorLocation();
        if (isLimitless) {
            lastPosition = 1;
            for (i in 0..size + 1) {
                val imgeView = ImageView(mContext);
                imgeView.scaleType = ImageView.ScaleType.CENTER_CROP;
                if (viewClick != null) {
                    imgeView.setOnClickListener { viewClick!!.viewClick(i) }
                }
                var url: String;
                if (i == 0) {
                    url = imgUrls.get(size - 1)

                } else if (i == size + 1) {
                    url = imgUrls.get(0)
                } else {
                    url = imgUrls.get(i - 1);
                }
                mImgViews.add(imgeView)
                mContext?.let { imageLoader!!.disPlayImage(it, imgeView, url) }

            }

        } else {
            lastPosition = 0;
            for (i in 0..size - 1) {
                val imgeView = ImageView(mContext);
                imgeView.scaleType = ImageView.ScaleType.CENTER_CROP;
                if (viewClick != null) {
                    imgeView.setOnClickListener { viewClick!!.viewClick(i) }
                }
                var url: String;
                url = imgUrls.get(i);
                mImgViews.add(imgeView)
                mContext?.let { imageLoader!!.disPlayImage(it, imgeView, url) }

            }
        }
        setAdapter();
        if (autoPlay) {
            startAuto();
        }
    }

    private fun initCurrentItem() {
        if (size > 1) {
            if (isLimitless) {
                currentItem = 1
            } else {
                currentItem == 0
            }
        } else {
            currentItem == 0;
        }
    }

    fun createIndicatorImages() {
        mLLIndicator!!.removeAllViews();
        mIndicatorViews.clear();

        //当图片数量少的时候不用赋值指示点
        if (size > 1) {
            for (i in 0..size - 1) {
                val mIvIndicator = ImageView(mContext);
                val mLayoutPara: LinearLayout.LayoutParams;
                if (indicatorWidth != indicatorWidthSelect) {
                    if (i == 0) {
                        mLayoutPara = LinearLayout.LayoutParams(indicatorWidthSelect, indicatorHeightSelect);
                    } else {
                        mLayoutPara = LinearLayout.LayoutParams(indicatorWidth, indicatorHeight);
                    }
                } else {
                    mLayoutPara = LinearLayout.LayoutParams(indicatorWidth, indicatorHeight);
                }
                if (i == 0) {
                    mLayoutPara.marginStart = indicatorMarginStart;
                    mLayoutPara.marginEnd = indicatorSpace;
                } else if (i == size - 1) {
                    mLayoutPara.marginEnd = indicatorMarginEnd;
                    mLayoutPara.marginStart = indicatorSpace;
                } else {
                    mLayoutPara.marginEnd = indicatorSpace;
                    mLayoutPara.marginStart = indicatorSpace;
                }
                mLayoutPara.topMargin = indicatorTop;
                mLayoutPara.bottomMargin = indicatorBottom;
                mIvIndicator.layoutParams = mLayoutPara;
                if (i == 0) {
                    mIvIndicator.setImageResource(indicatorSelect)
                } else {
                    mIvIndicator.setImageResource(indicatorUnSelect)
                }
                mLLIndicator!!.addView(mIvIndicator);
                mIndicatorViews.add(mIvIndicator);

            }
        }

    }

    fun setAdapter() {
        myPageAdapter = MyPageAdapter(mImgViews);
        viewPager!!.adapter = myPageAdapter;
        viewPager!!.setCurrentItem(currentItem)
        viewPager!!.setPageTransformer(true, transformer)
        //通过数据源的大小判断是否能滑动
        if (isScroll && size > 1) {
            viewPager!!.isAbleScroll = true;
            mLLIndicator!!.visibility = View.VISIBLE;
        } else {
            viewPager!!.isAbleScroll = false;
            mLLIndicator!!.visibility = View.GONE;
        }

    }

    var mDisposable: Disposable? = null;

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (autoPlay) {
            val action = ev!!.action;
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
                startAuto();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAuto();
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 开始自动轮播
     */
    fun startAuto() {
        //毫秒
        Observable.interval(intervalTime.toLong(), TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d;
                }

                override fun onError(e: Throwable) {
                }

                override fun onNext(t: Long) {
                    onNext();
                }

                override fun onComplete() {
                }

            })
    }

    fun onNext() {
        if (size > 1 && autoPlay) {
            currentItem = currentItem % (size + 1) + 1
        }
        if (currentItem == 1) {
            viewPager!!.setCurrentItem(currentItem, false)
            onNext();
        } else {
            viewPager!!.setCurrentItem(currentItem)
            // handler.postDelayed(task, delayTime.toLong())
        }
    }

    //停止自动轮播
    fun stopAuto() {
        if (mDisposable != null && !mDisposable!!.isDisposed()) {
            mDisposable!!.dispose();
        }

    }


    override fun onPageScrollStateChanged(state: Int) {
        if (isLimitless) {
            when (state) {
                0
                -> if (currentItem == 0) {
                    viewPager!!.setCurrentItem(size, false)
                } else if (currentItem == size + 1) {
                    viewPager!!.setCurrentItem(1, false)
                }
                1
                -> if (currentItem == size + 1) {
                    viewPager!!.setCurrentItem(1, false)
                } else if (currentItem == 0) {
                    viewPager!!.setCurrentItem(size, false)
                }
                2
                -> {
                }
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (isLimitless) {
            currentItem = position
            //切换指示器
            setLayoutParams(mIndicatorViews.get((lastPosition - 1 + size) % size), false)
            mIndicatorViews.get((lastPosition - 1 + size) % size).setImageResource(indicatorUnSelect)
            setLayoutParams(mIndicatorViews.get((position - 1 + size) % size), true)
            mIndicatorViews.get((position - 1 + size) % size).setImageResource(indicatorSelect)
            lastPosition = position
        } else {
            currentItem = position
            //切换指示器
            setLayoutParams(mIndicatorViews.get(lastPosition), false)
            mIndicatorViews.get(lastPosition).setImageResource(indicatorUnSelect)
            setLayoutParams(mIndicatorViews.get(position), true)
            mIndicatorViews.get(position).setImageResource(indicatorSelect)
            lastPosition = position
        }
        if (pageSelected != null) {
            pageSelected!!.onPageSelect(position)
        }

    }

    fun setLayoutParams(view: View, boolean: Boolean) {
        if (indicatorWidth != indicatorWidthSelect) {
            if (boolean) {
                var layoutParams = view.layoutParams as LinearLayout.LayoutParams
                layoutParams.width = indicatorWidthSelect;
                layoutParams.height = indicatorHeightSelect;
                view.layoutParams = layoutParams;
            } else {
                var layoutParams = view.layoutParams as LinearLayout.LayoutParams
                layoutParams.width = indicatorWidth;
                layoutParams.height = indicatorHeight;
                view.layoutParams = layoutParams;
            }
        }
    }

    class MyPageAdapter : PagerAdapter {
        var imgViews: ArrayList<View> = arrayListOf();

        constructor(imgViews: ArrayList<View>) : super() {
            this.imgViews = imgViews
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`;
        }

        override fun getCount(): Int {
            return imgViews.size;
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(imgViews.get(position))
            return imgViews.get(position);
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?);
        }

    }

    //item点击
    public interface IViewClick {
        fun viewClick(position: Int);
    }

    //item图片加载
    public interface IImageLoader {
        fun disPlayImage(context: Context, imageView: ImageView, url: String);
    }

    //item图片加载
    public interface IViewLoader {
        fun disPlayView(context: Context, view: View, position: Int);
    }

    public interface IPageSelected {
        fun onPageSelect(position: Int);
    }
}