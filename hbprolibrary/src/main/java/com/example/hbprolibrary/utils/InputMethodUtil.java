package com.example.hbprolibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**  输入键盘工具类
 * 作者：
 * 邮箱：
 */
public class InputMethodUtil {


    /**隐藏输入法
     * @param activity
     */
    public static void closeInputMethod(Activity activity) {
        try {
            InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(manager.isActive()) {
                manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**隐藏输入法
     * @param activity
     */
    public static void closeInputMethod(Activity activity, View myView) {
        try {
            InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(myView.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**显示输入法
     * @param activity
     * @param myView
     */
    public static void showInputMethod(Activity activity, View myView) {
        try {
            InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.showSoftInput(myView, InputMethodManager.SHOW_FORCED);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
