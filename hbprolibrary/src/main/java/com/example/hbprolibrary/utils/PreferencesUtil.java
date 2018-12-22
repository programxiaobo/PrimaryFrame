package com.example.hbprolibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * sharedPreference工具类
 */
public class PreferencesUtil {
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    private Object object;
    private static PreferencesUtil preferencesUtil;

    private PreferencesUtil() {

    }

    public static PreferencesUtil getInstance(Context context) {
        if (preferencesUtil == null) {
            synchronized (PreferencesUtil.class) {
                if (preferencesUtil == null) {
                    // 使用双重同步锁
                    preferencesUtil = new PreferencesUtil(context);
                }
            }
        }
        return preferencesUtil;
    }

    /**
     * 问题在于，这个Context哪来的我们不能确定，很大的可能性，你在某个Activity里面为了方便，直接传了个this;
     * 这样问题就来了，我们的这个类中的sInstance是一个static且强引用的，在其内部引用了一个Activity作为Context，也就是说，
     * 我们的这个Activity只要我们的项目活着，就没有办法进行内存回收。而我们的Activity的生命周期肯定没这么长，造成了内存泄漏。
     * 所以这里使用context.getApplicationContext()
     *
     * @param context
     */
    private PreferencesUtil(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context
                .getApplicationContext());
    }

    /**
     * 保存数据 , 所有的类型都适用
     * @param key
     * @param object
     */
    public synchronized PreferencesUtil saveParam(String key, Object object) {
        if (editor == null)
            editor = preferences.edit();
        // 得到object的类型
        if (object != null) {
            String type = object.getClass().getSimpleName();
            if ("String".equals(type)) {
                // 保存String 类型
                editor.putString(key, (String) object);
            } else if ("Integer".equals(type)) {
                // 保存integer 类型
                editor.putInt(key, (Integer) object);
            } else if ("Boolean".equals(type)) {
                // 保存 boolean 类型
                editor.putBoolean(key, (Boolean) object);
            } else if ("Float".equals(type)) {
                // 保存float类型
                editor.putFloat(key, (Float) object);
            } else if ("Long".equals(type)) {
                // 保存long类型
                editor.putLong(key, (Long) object);
            } else {
                // 不是基本类型则是保存对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(object);
                    String productBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                    editor.putString(key, productBase64);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            editor.apply();
        }
//		editor = null;
        return preferencesUtil;
    }

    /**
     * 得到保存数据的方法，所有类型都适用
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getParam(String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        if ("String".equals(type)) {
            return preferences.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return preferences.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return preferences.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return preferences.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return preferences.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * Whether to use for the first time
     *
     * @return
     */
    public boolean isFirst() {
        return (Boolean) getParam("isFirst", true);
    }

    /**
     * set user first use is false
     *
     * @return
     */
    public void setFirst(Boolean isFirst) {
        saveParam("isFirst", isFirst);
    }

    /**
     * Set up the first time login
     *
     * @return
     */
    public boolean isLogin() {
        return (Boolean) getParam("isLogin", false);
    }

    /**
     * @return
     */
    public void setLogin(Boolean isLogin) {
        saveParam("isLogin", isLogin);
    }

    /**
     * save user head image
     *
     * @param bitmap
     */
    public void saveHeadImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String imageBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        preferences.edit().putString("head_image", imageBase64).commit();
        Log.d(this.getClass().getSimpleName(), "save head image success");
    }

    /**
     * get user head image
     * @return
     */
    public Bitmap getHeadImage() {
        String imageBase64;
        imageBase64 = preferences.getString("head_image", null);
        if (!TextUtils.isEmpty(imageBase64)) {
            byte[] base64Bytes = Base64.decode(imageBase64.getBytes(),
                    Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            Log.d(this.getClass().getSimpleName(), "get head image success");
            return BitmapFactory.decodeStream(bais);
        } else {
            Log.e(this.getClass().getSimpleName(), "get head image error");
            return null;
        }
    }

    public Object getObject(String key) {
        String wordBase64 = preferences.getString(key, "");
        byte[] base64 = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            ObjectInputStream bis = new ObjectInputStream(bais);
            object = bis.readObject();
            Log.d(this.getClass().getSimpleName(), "Get object success");
            return object;
        } catch (Exception e) {
        }
        Log.e(this.getClass().getSimpleName(), "Get object is error");
        return null;
    }

    /**
     * 移除某一条数据
     *
     * @param key
     */
    public synchronized PreferencesUtil removeKey(String key) {
        if (editor == null)
            editor = preferences.edit();
        editor.remove(key).commit();
        return preferencesUtil;
    }

    /**
     * 移除全部数据
     */
    public synchronized void removeAllKey() {
        if (editor == null)
            editor = preferences.edit();
            editor.clear().commit();
    }
}
