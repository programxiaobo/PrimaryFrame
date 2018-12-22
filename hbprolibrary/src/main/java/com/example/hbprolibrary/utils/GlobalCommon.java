package com.example.hbprolibrary.utils;


import android.os.Handler;
import android.os.Looper;


/** 全局通用类
 * 作者：
 * 邮箱：
 */
public class GlobalCommon {

    /** GlobalCommon单实例 */
   private static GlobalCommon instance;
   /** handler  */
   private static Handler mHandler = new Handler();

   private GlobalCommon(){}

    /**
     * 单实例
     * @return
     */
   public static  synchronized GlobalCommon newInstance(){
       if(instance == null){
          instance = new GlobalCommon();
       }
       return instance;
   }

    /**
     *  获取handler对象
     * @return
     */
    public static Handler getMainHandler(){
        return mHandler;
    }

    /**
     * 判断是否为主线程
     * @return
     */
    public static boolean isUiThread(){
        return Looper.getMainLooper() == Looper.myLooper();

    }

    /**
     *  主线程刷新Ui
     * @param runnable
     */
    public static void runUiThread(Runnable runnable){
        if(isUiThread()){
           runnable.run();
        // 子线程
        }else{
            mHandler.post(runnable);
        }
    }


}
