package com.example.hbprolibrary.callback;

import com.lzy.okgo.model.Progress;

/**    文件上传的callBack回调对象
 * 作者
 * 邮箱：
 */
public interface FileUploadCallBack<T> {

    /**
     * 加载JSON数据正常回调
     * @param t 返回的实体对象
     */
    void onResponse(T t);

    /**
     * 加载JSON数据异常回调
     * @param msg 返回的错误提示
     */
    void onErrorResponse(String msg);

    /**
     * 返回结果是null
     * @param error
     */
    void onResponseNull(String error);

    /**
     * 文件长传进度回调
     * @param progress
     */
    void fileUploadProgress(Progress progress);

}
