package com.example.administrator.primaryframe.utils;

import android.widget.Toast;

import com.example.administrator.primaryframe.base.PlatformApplication;

/**
 * toast工具类
 */
public class ToastUtil {

	/** 单例的吐司 */
	private static Toast mToast;
	/**
	 * 能够连续弹的吐司，不用等上个吐司消失
	 * @param text
	 */
	public static void showToast(String text){
		if( null == mToast){
			//创建toast
			mToast = Toast.makeText(PlatformApplication.getInstance().getApplicationContext(), text,Toast.LENGTH_SHORT);
		}else {
			//改变当前toast的文本
			mToast.setText(text);
		}
		mToast.show();
	}

	/**
	 *  在子线程toast
	 * @param text
	 */
	public static void  showThreadToast(final CharSequence text){

		GlobalCommon.runUiThread(new Runnable() {
			@Override
			public void run() {
				if(null == mToast){
					mToast = Toast.makeText(PlatformApplication.getInstance(),text,Toast.LENGTH_SHORT);
				}
				mToast.setText(text);
				mToast.show();
			}
		});
	}
}
