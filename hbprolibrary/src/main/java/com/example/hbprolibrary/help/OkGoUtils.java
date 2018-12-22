package com.example.hbprolibrary.help;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.hbprolibrary.callback.FileUploadCallBack;
import com.example.hbprolibrary.callback.OkHttpCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

/**
 * OkGo工具类
 */
public class OkGoUtils {

    /** OkGoUtils 单实例   */
    private static OkGoUtils mOkGoUtils = null;

    private OkGoUtils(){ }

    /**
     * 单例初始化对象
     **/
    public static OkGoUtils getInstance(){
        if(mOkGoUtils == null){
            synchronized (OkGoUtils.class){
                if(mOkGoUtils ==null) {
                    mOkGoUtils = new OkGoUtils();
                }
            }
        }
        return mOkGoUtils;
    }

    /**
     * POST请求
     * @param url       地址URL
     * @param paramsMap 参数Map
     * @param activity  当前的activity
     * @param callBack  回调对象
     */
    public void postRequest(String url, Map<String,String> paramsMap, Activity activity, @Nullable final OkHttpCallBack callBack){
        Log.i("tag","POST请求地址是："+url+" 参数是："+((null == paramsMap)?"":paramsMap.toString()));
        for (Map.Entry<String ,String> stringEntry :paramsMap.entrySet()){
            Log.i("tag","--getKey:"+ stringEntry.getKey() +"--getValue:"+stringEntry.getValue());
        }
        if (!TextUtils.isEmpty(url)) {
            OkGo.<String>post(url)
                    .tag(activity)
                    .params(paramsMap, false)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String json = "";
                            JSONObject obj;
                            if (null != response && response.body() instanceof String) {
                                json = response.body().toString();
                            }
                            if (!TextUtils.isEmpty(json)) {//数据不为空的情况
                                //分为两种  200或者500
                                try {
                                    obj = new JSONObject(json);
                                    if(obj.getInt("code")==500){
                                        callBack.onErrorResponse(obj.getString("message"));
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                callBack.onResponse(json);
                            } else {//数据为空的情况
                                callBack.onResponseNull(response.body().toLowerCase());
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if ( null != response && response.body() instanceof  String){
                                callBack.onErrorResponse(response.body().toString());
                            }else{
                                callBack.onErrorResponse(response.body().toLowerCase());
                            }
                        }
                    });
        }else{
            Toast.makeText(activity, "url为null", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * POST 文件上传的请求
     * @param url       地址URL
     * @param paramsMap 参数Map
     * @param activity  当前的activity
     * @param callBack  回调对象
     */
    public void postUploadFileRequest(String url, Map<String,String> paramsMap, @Nullable File file, Activity activity, @Nullable final FileUploadCallBack callBack){
        Log.i("tag","POST请求地址是："+url+" 参数是："+((null == paramsMap)?"":paramsMap.toString()));
        Log.i("tag","file："+file.getName()+"-----文件大小："+file.length());
        for (Map.Entry<String ,String> stringEntry :paramsMap.entrySet()){
            Log.i("tag","--getKey:"+ stringEntry.getKey() +"--getValue:"+stringEntry.getValue());
        }
        if (!TextUtils.isEmpty(url)) {
            OkGo.<String>post(url)
                    .tag(activity)
                    .params("file",file)
                    .isMultipart(true)//强制使用表单的形式提交，默认为false
                    .params(paramsMap, false)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String json = "";
                            JSONObject obj;
                            if (null != response && response.body() instanceof String) {
                                json = response.body().toString();
                            }
                            if (!TextUtils.isEmpty(json)) {//数据不为空的情况
                                //分为两种  200或者500
                                try {
                                    obj = new JSONObject(json);
                                    if(obj.getInt("code")==500){
                                        callBack.onErrorResponse(obj.getString("message"));
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                callBack.onResponse(json);
                            } else {//数据为空的情况
                                callBack.onResponseNull(response.body().toLowerCase());
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if ( null != response && response.body() instanceof  String){
                                callBack.onErrorResponse(response.body().toString());
                            }else{
                                callBack.onErrorResponse(response.body().toLowerCase());
                            }
                        }

                        @Override
                        public void uploadProgress(Progress progress) {
                            super.uploadProgress(progress);
                            if (null!= progress){
                                callBack.fileUploadProgress(progress);
                            }
                        }
                    });
        }else{
            Toast.makeText(activity, "url为null", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 下载文件的请求
     * @param url
     * @param destFileDir   文件路径(不传会默认)
     * @param destFileName  文件名称(不传会默认)
     * @param activity      当前的activity
     */
    private void getDownLoadFileRequest(String url, String destFileDir, String destFileName, Activity activity){
        if (!TextUtils.isEmpty(url)) {
            OkGo.<File>get(url)//
                    .tag(activity)//
                    .execute(new FileCallback(destFileDir, destFileName) {
                        @Override
                        public void onSuccess(Response<File> response) {

                        }

                        @Override
                        public void downloadProgress(Progress progress) {
                            super.downloadProgress(progress);
                        }
                    });
        }else{
            Toast.makeText(activity, "url为null", Toast.LENGTH_SHORT).show();
        }
    }
}
