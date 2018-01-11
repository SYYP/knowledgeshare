package www.knowledgeshare.com.knowledgeshare;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okserver.OkDownload;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import org.litepal.LitePalApplication;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/8/24.
 */

public class MyApplication extends LitePalApplication {
    private static MyApplication application;
    private static ClipboardManager cb;
    private static ClipChangedListener listener;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initUMShare();
        setLogger();
        setOkGo();//OkGo----第三方网络框架
        initCloudChannel(this);
    }

    /**
     * 初始化云推送通道
     *移动推送的初始化必须在Application中，不能放到Activity中执行。移动推送在初始化过程中将启动后台进程channel，
     * 必须保证应用进程和channel进程都执行到推送初始化代码。
     如果设备成功注册，将回调callback.onSuccess()方法。
     但如果注册服务器连接失败，则调用callback.onFailed方法，并且自动进行重新注册，直到onSuccess为止。
     （重试规则会由网络切换等时间自动触发。）
     请在网络通畅的情况下进行相关的初始化调试，如果网络不通，或者App信息配置错误，在onFailed方法中，会有相应的错误码返回
     * @param applicationContext
     */
    private void initCloudChannel(final Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                //                Log.d(TAG, "init cloudchannel success");
                String deviceId = pushService.getDeviceId();
                System.out.println("xxxxxxxxx  "+deviceId);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                //                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication getApplication() {
        if (application == null) {
            application = getApplication();
        }
        return application;
    }

    private void initUMShare() {
        Config.DEBUG = true;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wxf33afce9142929dc", "1ec3dd295f384088757e5fd5500ef897");
//        PlatformConfig.setQQZone("1106303149", "xhxcwesbSLrse2xS");
        PlatformConfig.setSinaWeibo("1418202942", "5e451c151d8f4ed063979bc03529abac", "http://www.baidu.com");//回调地址要跟微博开放平台的一样
    }

    public static Application getInstance() {
        return application;
    }

    public static Context getGloableContext() {
        return application.getApplicationContext();
    }

    public static class ClipChangedListener implements ClipboardManager.OnPrimaryClipChangedListener {

        @Override
        public void onPrimaryClipChanged() {
            // TODO Auto-generated method stub
            // 具体实现
            String string = cb.getText().toString();
            if (string != null && string.equals("")) {
                return;
            }
            cb.setText("");
        }
    }

    public static void startClearClip(Context context) {

        cb = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);

        Log.e("ProxyApplication", "=============================================================================");
        Log.e("ProxyApplication", "============================== cb.setPrimaryClipBefore！==================================");
        Log.e("ProxyApplication", "=============================================================================");

        cb.setPrimaryClip(ClipData.newPlainText("", ""));
        Log.e("ProxyApplication", "=============================================================================");
        Log.e("ProxyApplication", "============================== cb.setPrimaryClipAfter！cb= " + cb + "==================================");
        Log.e("ProxyApplication", "=============================================================================");

        listener = new ClipChangedListener();
        Log.e("ProxyApplication", "=============================================================================");
        Log.e("ProxyApplication", "============================== 创建lisitener！==================================");
        Log.e("ProxyApplication", "=============================================================================");

        cb.addPrimaryClipChangedListener(listener);
        Log.e("ProxyApplication", "=============================================================================");
        Log.e("ProxyApplication", "============================== 开启禁止复制！==================================");
        Log.e("ProxyApplication", "=============================================================================");
    }

    // 关闭禁用剪切板
    public static void stopClearClip() {
        try {
            Log.e("ProxyApplication", "=============================================================================");
            Log.e("ProxyApplication", "============================== cb = " + cb + "==================================");
            Log.e("ProxyApplication", "====================================================");
            if (cb != null) {
                cb.removePrimaryClipChangedListener(listener);
                listener = null;
                cb = null;
                Log.e("ProxyApplication", "=============================================================================");
                Log.e("ProxyApplication", "============================== 停止禁止复制！==================================");
                Log.e("ProxyApplication", "====================================================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * logger-----第三方日志打印
     */
    private void setLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                //                .methodCount(3)         // (Optional) How many method line to show. Default 2
                //                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("TAG")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                //                .logStrategy(logStrategy)   // (Optional) Changes the log strategy to print out. Default LogCat
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        /**
         * 隐藏Log日志---上线前打开注释即可
         */
        /*Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });*/
    }

    /**
     * OkGo------第三方网络请求框架
     */
    private void setOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("TAG");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.SEVERE);                     //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失


        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
        //        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
        //        builder.hostnameVerifier(new SafeHostnameVerifier());

        OkGo.getInstance().init(this)                              //必须调用初始化
                .setOkHttpClient(builder.build())                  //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)                 //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)    //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)//全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                         //全局公共头
                .addCommonParams(params);                          //全局公共参数

        //全局下载配置---如果每个任务不设置下载路径，将默认使用这个路径
        OkDownload okDownload = OkDownload.getInstance();
        okDownload.setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/");
        okDownload.getThreadPool().setCorePoolSize(5);
    }
}

