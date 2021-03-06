package com.example.administrator.primaryframe.base;

import android.app.Application;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import okhttp3.OkHttpClient;

/**
 * 作者：Administrator on 2017/11/18 10:53
 * 邮箱：
 */
public class PlatformApplication extends Application {

    //全部上下文
    private static PlatformApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //初始化okHttpGo
        initializeOkHttpGo();
    }



    /**
     * 初始化okHttpGo 参数
     */
    private void initializeOkHttpGo() {
        // OkHttp代理对象
        OkHttpClient.Builder builder  = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        //添加OkGo默认debug日志
        builder.addInterceptor(loggingInterceptor);
        //超时时间设置，默认60秒,/全局写入的超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));

        // 必须调用初始化
        OkGo.getInstance().init(this)
        .setOkHttpClient(builder.build())                    //建议设置OkHttpClient，不设置会使用默认的
        .setCacheMode(CacheMode.NO_CACHE)                 //全局统一缓存模式，默认不使用缓存，可以不传
        .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
        .setRetryCount(1);                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0

    }

    /**
     *  获取  application 对象
      * @return
     */
    public static PlatformApplication getInstance(){
        return  mInstance;

    }
}
