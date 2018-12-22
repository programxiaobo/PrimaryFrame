package com.example.administrator.primaryframe.base;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP数据交互  基类Fragment
 */
public abstract class LazyLoadBaseFragment<V,T extends BasePresenter<V>> extends Fragment implements OnUiOperation{

    /**  关联者 */
    protected T mPresenter;
    /**  根布局  */
    protected View mContentView;
    /** 上下文*/
    protected FragmentActivity mActivity;
    /** 绑定 */
    Unbinder mUnbinder;
    /** 是否初始化试图 **/
    protected boolean mIsInit = false;
    /** 是否加载过数据 **/
    protected boolean mIsLoad = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity =  getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        mIsInit = true;
        if (mContentView == null) {
            isCanLoadData();
            //创建关联
            mPresenter = createPresenter();
            //关联view视图
            mPresenter.attachView((V) this);
            //初始化view 视图
            initializeComponent(savedInstanceState,container);
            //处理逻辑
            initializeOperation();
            //设置监听
            setComponentListener();
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }

    /**
     * 设置填充view视图
     * @param context      上下文
     *  @param container    ViewGroup容器
     */
    protected View setLoadContentView(Context context,ViewGroup container) {
        int layoutResId = getLayoutResId();
        if (layoutResId != 0){
            mContentView = LayoutInflater.from(context).inflate(getLayoutResId(), container,false);
            //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
            mUnbinder = ButterKnife.bind(this,mContentView);
        }
        return  mContentView;
    }

    /**
     * 创建关联
     * @return
     */
    protected  abstract T createPresenter();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData(){
        if(!mIsInit){
            return;
        }
        if(getUserVisibleHint()){
            lazyLoad();
            mIsLoad = true;
        }else{
            if(mIsLoad){
                stopLoad();
            }
        }
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {}

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除关联
        if (null!= mPresenter){
            mPresenter.detachView();
        }
        //解除绑定
        if (null!= mUnbinder){
            mUnbinder.unbind();
        }
        mIsInit = false;
        mIsLoad = false;
    }
}
