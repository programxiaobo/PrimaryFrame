package com.example.administrator.primaryframe.base;

import java.lang.ref.WeakReference;

/**    Presenter层基类
 * 作者：
 * 邮箱：
 */
public class BasePresenter<T> {

     /**
     * View的弱引用
     */
    protected WeakReference<T> mViewReference;


    /**
     * 关联view视图
     * @param view
     */
    public void attachView(T view){
        mViewReference = new WeakReference<T>(view);
    }

    /**
     * 解除关联view视图
     */
    public  void  detachView(){
        if (null != mViewReference){
            mViewReference.clear();
        }
    }

    /**
     * 获取关联viw 视图
     * @return
     */
    protected T getAttachView(){
        if (null != mViewReference){
            return  mViewReference.get();
        }else{
            return  null;
        }
    }

}



