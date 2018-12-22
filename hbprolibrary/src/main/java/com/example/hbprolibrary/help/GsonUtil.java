package com.example.hbprolibrary.help;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Gson工具类
 * 作者：
 * 邮箱：
 */
public class GsonUtil {

    /** Gson工具类 实例 */
    private static GsonUtil instance;
    /** Gson 实例 */
    private static Gson gson = null;

    private GsonUtil() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
    }

    /**
     * 单例初始化对象
     *
     * @return
     */
    public static GsonUtil getInstance() {
        if (instance == null) {
            synchronized (GsonUtil.class) {
                if (instance == null) {
                    instance = new GsonUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 对象转JSON
     *
     * @param object
     * @return
     */
    public String objectToJson(Object object) {
        String json = "";
        try {
            if (null != gson && null != object) {
                json = gson.toJson(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * JSON转对象
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T jsonToObject(String json, Class<T> cls) {
        T t = null;
        try {
            if (null != gson && !TextUtils.isEmpty(json)) {
                t = gson.fromJson(json, cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * JSON转成List
     * @param json
     * @param <T>
     * @return
     */
    public <T> List<T> jsonToList(String json) {
        List<T> list = new ArrayList<T>();
        try {
            if (null != gson && !TextUtils.isEmpty(json)) {
                Type type = new TypeToken<List<T>>() {}.getType();
                list = gson.fromJson(json, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * JSON转List<Map<String, T>>
     * @param json
     * @param <T>
     * @return
     */
    public <T> List<Map<String, T>> jsonToListMap(String json) {
        List<Map<String, T>> list = new ArrayList<Map<String, T>>();
        try {
            if (null != gson && !TextUtils.isEmpty(json)) {
                list = gson.fromJson(json, new TypeToken<List<Map<String, T>>>(){}.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * JSON转成Map
     * @param json
     * @param <T>
     * @return
     */
    public <T> Map<String, T> jsonToMap(String json) {
        Map<String, T> map = new HashMap<String, T>();
        try {
            if (null != gson && !TextUtils.isEmpty(json)) {
                map = gson.fromJson(json, new TypeToken<Map<String, T>>(){}.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     *  list 转json
     * @param list
     * @return
     */
    public String listToJson(List<?> list){
        String jsonStr= "";
        try {
            if (null != gson &&  null != list && list.size()>0){
                jsonStr = gson.toJson(list);
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        return  jsonStr;
    }

    /**
     *  map 转json
     * @param map
     * @return
     */
    public String mapToJson(Map<?,?> map){
        String jsonStr= "";
        try {
            if (null != gson &&  null != map && map.size()>0){
                jsonStr = gson.toJson(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  jsonStr;
    }

}
