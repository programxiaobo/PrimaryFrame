package com.example.administrator.primaryframe.base;

import android.os.Bundle;
import android.view.ViewGroup;

/**  定义接口初始化ui操作
 * 作者：Administrator on 2017/10/18 10:16
 * 邮箱：
 */
public interface OnUiOperation{

    /**
     *  获取layout布局资源ID
     * @return 返回对应layout 布局资源文件
     */
    int getLayoutResId();

    /** 重载的方式   适应于 activity onCreate
     * 初始化组件
     */
    void initializeComponent(Bundle savedInstanceState);

    /** 重载的方式   适应于 fragment onCreateView
     * 初始化View控件
     * @param savedInstanceState   Bundle 保存状态
     * @param container          ViewGroup容器
     */
     void initializeComponent(Bundle savedInstanceState, ViewGroup container);

    /**
     * 初始化操作（加载数据 等一系列的操作 ）
     */
    void  initializeOperation();

    /**
     * 给View等组件控件添加事件监听器
     */
     void  setComponentListener();
}
