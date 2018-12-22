package com.example.hbprolibrary.callback;

/** http请求回调对象
 * 作者
 * 邮箱：
 */
public interface OkHttpCallBack<T> {

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
     * 出现此种情况，可能token过期，url地址无效 等
     * @param error
     */
    void onResponseNull(String error);

}
