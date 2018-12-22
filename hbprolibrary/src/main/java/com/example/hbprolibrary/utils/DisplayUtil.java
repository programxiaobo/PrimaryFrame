package com.example.hbprolibrary.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**   dp 、sp、 dip 转换的工具类
 * 作者：
 * 邮箱：
 */
public class DisplayUtil {


    /**
     * 获取屏幕尺寸
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2){
            return new Point(display.getWidth(), display.getHeight());
        }else{
            Point point = new Point();
            display.getSize(point);
            return point;
        }
    }


    /**
     *  dip 转  px
     * @param context  上下文
     * @param dipValue  dip值
     * @return
     */
    public static  int dip2px(Context context ,float dipValue){
        if ( context instanceof  Context){
            context = context.getApplicationContext() ;
        }else if (context instanceof ContextWrapper){
            context = context;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale +0.5f);
    }

    /**
     * px转 dip
     * @param context 上下文
     * @param pxValue px值
     * @return
     */
    public static int px2dip(Context context ,float pxValue){
        if ( context instanceof  Context){
            context = context.getApplicationContext() ;
        }else if (context instanceof ContextWrapper){
            context = context;
        }
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /* sp 转 dp
     * @param context 上下文
     * @param spValue sp值
     * @return
     */
   public static int sp2dp(Context context ,float spValue){
       if ( context instanceof  Context){
           context = context.getApplicationContext() ;
       }else if (context instanceof ContextWrapper){
           context = context;
       }
       float scale = context.getResources().getDisplayMetrics().density;
       return (int) (spValue * scale +0.5f);
   }

    /**
     * dp转sp
     * @param context 上下文
     * @param dpValue dp值
     * @return
     */
   public static int dp2sp(Context context,float dpValue){
       if ( context instanceof  Context){
           context = context.getApplicationContext() ;
       }else if (context instanceof ContextWrapper){
           context = context;
       }
       float scale = context.getResources().getDisplayMetrics().density;
       return (int) (dpValue / scale +0.5f);
   }


    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        if ( context instanceof  Context){
            context = context.getApplicationContext() ;
        }else if (context instanceof ContextWrapper){
            context = context;
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        if ( context instanceof  Context){
            context = context.getApplicationContext() ;
        }else if (context instanceof ContextWrapper){
            context = context;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }

}
