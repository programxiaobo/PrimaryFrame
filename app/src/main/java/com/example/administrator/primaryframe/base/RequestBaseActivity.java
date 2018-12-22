package com.example.administrator.primaryframe.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP数据交互  基类activity
 */
public abstract class RequestBaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity implements OnUiOperation{

    /**  关联者 */
    protected T mPresenter;
    /** 绑定 */
    Unbinder mUnbinder;
    /** 是否使用沉浸式 **/
    boolean mIsImmersive = true;
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉默认导航栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if(mIsImmersive) {
            //设置沉浸式状态栏
            setImmersiveStatus();
            //设置占位状态栏高度
            setStatusViewWithHeight();
        }
        try {
            int layoutResId = getLayoutResId();
            if (layoutResId != 0){
                //设置根布局
                setContentView(getLayoutResId());
                //绑定
                mUnbinder = ButterKnife.bind(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //创建关联
        mPresenter = createPresenter();
        //关联view视图
        mPresenter.attachView((V) this);
        //初始化视图
        initializeComponent(savedInstanceState);
        //初始化操作
        initializeOperation();
        //初始化组件监听
        setComponentListener();
    }

    /**
     * 创建关联
     * @return
     */
    protected  abstract T createPresenter();

    /**
     * 查找View
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    @Override
    public void initializeComponent(Bundle savedInstanceState) {}

    @Override
    public void initializeComponent(Bundle savedInstanceState, ViewGroup container) {}

    @Override
    public void initializeOperation() {}

    @Override
    public void setComponentListener() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除关联
        if (null!= mPresenter){
            mPresenter.detachView();
        }
        //解除绑定
        if (null!= mUnbinder){
            mUnbinder.unbind();
        }
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(RequestBaseActivity.this);
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void setImmersiveStatus(){
        //因为 API21 之后（也就是 android 5.0 之后）的状态栏，会默认覆盖一层半透明遮罩。
        // 且为了保持4.4以前系统正常使用，故需要三份 style 文件，
        // 即默认的values（不设置状态栏透明）、values-v19、values-v21（解决半透明遮罩问题）。
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//>=API 19
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){//>=API 21
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }else{
                attributes.flags |= flagTranslucentStatus | flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 设置占位状态栏的高度
     */
    protected void setStatusViewWithHeight(){
        //设置 paddingTop
        ViewGroup rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.setPadding(0, getStatusBarHeight(), 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 以上直接设置状态栏颜色
            getWindow().setStatusBarColor(Color.parseColor("#3F51B5"));
        } else {
            //根布局添加占位状态栏
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight());
            statusBarView.setBackgroundColor(Color.parseColor("#3F51B5"));
            decorView.addView(statusBarView, lp);
        }
    }

    /**
     * 利用反射获取状态栏高度
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 通过设置全屏，设置状态栏透明
     */
    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
}
