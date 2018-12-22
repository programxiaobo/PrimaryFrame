package com.example.hbprolibrary.utils;


import android.util.Log;

/** 日志打印工具类
 * 作者：
 * 邮箱：
 */
public class LogcatUtil {

   private static boolean isDebug = true;

    /**
     * 打印 i级别的日志
     * @param objTag objTag可以传任何对象 如果是String类型直接作为string 类型，如果是Object 类型，直接转换成类名
     * @param objMsg 直接打印toString();
     */
   public static void  i(Object objTag,Object objMsg){

       if(!isDebug)return;

       String tag; //标签
       String msg; //信息

      if(objTag instanceof CharSequence){
          tag = (String) objTag;
      }else if (objTag instanceof  Class<?>){
          tag = ((Class) objTag).getSimpleName();
      }else{
          tag = objTag.getClass().getSimpleName();
      }

      if(objMsg  == null || objMsg.toString() == null ){
          msg ="null";
      }else{
          msg = objMsg.toString();
      }

       Log.i(tag,msg);

   }

    /**
     * 打印 d级别的日志
     * @param objTag  objTag可以传任何对象 如果是String类型直接作为string 类型，如果是Object 类型，直接转换成类名
     * @param objMsg  直接打印toString();
     */
   public static void d(Object objTag,Object objMsg){

       if(!isDebug) return ;

       String tag; // 标签
       String msg; // 信息

       if(objTag instanceof String ){
            tag = (String) objTag;
       }else if (objTag instanceof  Class<?>){
            tag = ((Class) objTag).getSimpleName();
       }else{
            tag = objTag.getClass().getSimpleName();
       }

       if(objMsg == null || objMsg.toString() == null){
           msg ="null";
       }else{
           msg = objMsg.toString();
       }

       Log.d(tag,msg);
   }


}
