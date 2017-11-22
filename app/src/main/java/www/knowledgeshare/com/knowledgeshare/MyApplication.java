package www.knowledgeshare.com.knowledgeshare;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.litepal.LitePalApplication;

import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

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
//        initUMShare();
//        initUMPush();
        application=this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication  getApplication(){
        if(application == null){
            application = getApplication();
        }
        return application;
    }


    private void initUMPush() {
        final PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //                Toast.makeText(MyApplication.this, "注册成功" + deviceToken, Toast.LENGTH_SHORT).show();
                System.out.println("友盟推送注册成功" + deviceToken);
                //注册成功会返回device token
                //                mRegistrationId = mPushAgent.getRegistrationId();
                //                Toast.makeText(MyApplication.this, "注册成功" + mRegistrationId, Toast.LENGTH_SHORT).show();
                //                SpUtils.putString(instance, "UMPUSHID", mRegistrationId);
                SpUtils.putString(application, "UMPUSHID", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                //                Toast.makeText(MyApplication.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUMShare() {
        Config.DEBUG = true;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx76c60c8c929e5061", "9b4a4e4380a6012cf84956415af46523");
        PlatformConfig.setQQZone("1106303149", "xhxcwesbSLrse2xS");
        PlatformConfig.setSinaWeibo("557964441", "b52b29e8a5393bd34e2315e509fb5842", "http://www.baidu.com");//回调地址要跟微博开放平台的一样
    }

    public static Application getInstance() {
        return  application;
    }

    public static Context getGloableContext()    {
        return  application.getApplicationContext();
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
            Log.e("ProxyApplication", "============================== cb = "+cb+"==================================");
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

}

