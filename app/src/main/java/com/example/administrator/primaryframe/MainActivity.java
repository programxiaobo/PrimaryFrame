package com.example.administrator.primaryframe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.administrator.primaryframe.base.BasePresenter;
import com.example.administrator.primaryframe.base.RequestBaseActivity;
import com.example.administrator.primaryframe.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends RequestBaseActivity {
    private Toolbar mToolbar;
    //轮播图
    private Banner mBanner;
    private List<String> mBannerList;
    private List<String> mTitleList;
    //广告上下滚动控件
    private ViewFlipper mViewFlipper;
    private List<String> mFlipperList;

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initializeComponent(Bundle savedInstanceState) {
        super.initializeComponent(savedInstanceState);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**轮播图**/
        mBanner = findViewById(R.id.banner);
        initBannerData();
        initTitleData();
        mBanner.setImages(mBannerList);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setBannerTitles(mTitleList);
        mBanner.start();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtil.showToast("您点击了第"+(position+1)+"张");
            }
        });

        /**广告上下翻滚**/
        mViewFlipper = findViewById(R.id.viewFlipper);
        initFlipperData();
        for (int i = 0; i < mFlipperList.size(); i++) {
            View view = View.inflate(this,R.layout.view_viewflipper,null);
            TextView textView = view.findViewById(R.id.viewflipper_textView);
            textView.setText(mFlipperList.get(i).toString());
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mFlipperList.get(finalI).toString());
                }
            }) ;
            mViewFlipper.addView(view);
        }
        startActivity(new Intent(this,ChouJiangActivity.class));
    }

    /**
     * 初始化banner数据
     */
    private void initBannerData(){
        if(mBannerList == null){
            mBannerList = new ArrayList<>();
        }
        mBannerList.clear();
        mBannerList.add("http://f2.topitme.com/2/6a/bc/113109954583dbc6a2o.jpg");
        mBannerList.add("http://www.taopic.com/uploads/allimg/140320/235013-14032020515270.jpg");
        mBannerList.add("http://img.taopic.com/uploads/allimg/140804/240388-140P40P33417.jpg");
        mBannerList.add("http://pic10.nipic.com/20100926/2874022_122448852398_2.jpg");
    }

    /**
     * 初始化banner标题数据
     */
    private void initTitleData(){
        if(mTitleList == null){
            mTitleList = new ArrayList<>();
        }
        mTitleList.clear();
        mTitleList.add("小妖精");
        mTitleList.add("小可耐");
        mTitleList.add("小清新");
        mTitleList.add("小苹果");
    }

    /**
     * 初始化广告翻滚的数据
     */
    private void initFlipperData(){
        if(mFlipperList == null){
            mFlipperList = new ArrayList<>();
        }
        mFlipperList.clear();
        mFlipperList.add("这是第一条广告");
        mFlipperList.add("这是第二条广告");
    }
}
