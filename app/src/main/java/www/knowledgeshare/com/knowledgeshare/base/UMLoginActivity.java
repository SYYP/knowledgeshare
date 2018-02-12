package www.knowledgeshare.com.knowledgeshare.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.activity.BindPhoneActivity;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.LoginBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * Created by lxk on 2017/7/3.
 */

public class UMLoginActivity extends BaseActivity {

    private static Activity mContext;
    private static String username;
    private static String userhead;
    private static String uniqueId;
    private static String uid;

    /*
            * 授权中只是能拿到uid，openid，token这些授权信息，想获取用户名和用户资料，需要使用这个接口
            * 其中umAuthListener为授权回调，构建如下，其中授权成功会回调onComplete，取消授权回调onCancel，
            * 授权错误回调onError，对应的错误信息可以用过onError的Throwable参数来打印
            * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected static void loginBySina(Activity context) {
        mContext = context;
        UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.SINA, umAuthListener);
    }

    protected static void loginByWeiXin(Activity context) {
        mContext = context;
        UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    protected static void loginByQQ(Activity context) {
        mContext = context;
        UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.QQ, umAuthListener);
    }

    private static UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            //            Toast.makeText(MyApplication.getGloableContext(), "授权开始回调", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            /*
        * 登录成功后，第三方平台会将用户资料传回， 全部会在Map data中返回 ，由于各个平台对于用户资料的标识不同，
        * 因此为了便于开发者使用，我们将一些常用的字段做了统一封装，开发者可以直接获取，
        * 不再需要对不同平台的不同字段名做转换，这里列出我们封装的字段及含义
        * */
            //            Toast.makeText(MyApplication.getGloableContext(), "登陆成功", Toast.LENGTH_SHORT).show();
            username = data.get("name");
            userhead = data.get("iconurl");
            uid = data.get("openid");
            Logger.e(data.toString());
            //            SpUtils.putString(MyApplication.getGloableContext(), "threeid", uid);
            //            SpUtils.putString(MyApplication.getGloableContext(), "logintype", "three");
            Logger.e("openid:"+ uid +"\n"+"userFace:"+ userhead +"\n"+"name:"+ username);
            requestIsBind(uid);
            TUtils.showShort(mContext,"微信登录成功");
            String type = "";
            if (platform.equals(SHARE_MEDIA.QQ)) {
                type = "qq";
            } else if (platform.equals(SHARE_MEDIA.WEIXIN)) {
                type = "weixin";
            } else if (platform.equals(SHARE_MEDIA.SINA)) {
                type = "weibo";
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(MyApplication.getGloableContext(), "登陆失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MyApplication.getGloableContext(), "取消登录", Toast.LENGTH_SHORT).show();
        }
    };

    /*
    * 最后在登录所在的Activity里复写onActivityResult方法,注意不可在fragment中实现，如果在fragment中调用登录，
    * 就在fragment依赖的Activity中实现，如果不实现onActivityResult方法，会导致登录或回调无法正常进行
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    private static void requestIsBind(final String openid) {
        HttpParams params = new HttpParams();
        params.put("wx_unionid",openid);

        OkGo.<BaseBean>post(MyContants.isBindMobile)
                .tag(mContext)
                .params(params)
                .execute(new DialogCallback<BaseBean>(mContext,BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            String message = response.body().getMessage();
                            if (TextUtils.equals("0",message)){
                                Intent intent2 = new Intent(mContext, BindPhoneActivity.class);
                                intent2.putExtra("type","wechat");
                                intent2.putExtra("openid",openid);
                                intent2.putExtra("userFace",userhead);
                                intent2.putExtra("name",username);
                                mContext.startActivity(intent2);
                                mContext.finish();
                            }else {
                                requestLoginWeChat();
                            }
                        }
                    }
                });
    }

    private static void requestLoginWeChat() {
        HttpParams params = new HttpParams();
        //0--手机号登录  1--微信登录
        params.put("type","1");
        String device_token = SpUtils.getString(mContext, "device_token", "");
        params.put("device_token",device_token);
        params.put("wx_name",username);
        params.put("wx_unionid",uid);
        params.put("wx_avatar",userhead);
        params.put("from_type","2");

        OkGo.<LoginBean>post(MyContants.login)
                .tag(mContext)
                .params(params)
                .execute(new DialogCallback<LoginBean>(mContext,LoginBean.class) {
                    @Override
                    public void onSuccess(Response<LoginBean> response) {
                        LoginBean loginBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            SpUtils.putString(mContext,"id",loginBean.getUser().getId());
                            SpUtils.putString(mContext,"name",loginBean.getUser().getUser_name());
                            SpUtils.putString(mContext,"userFace",loginBean.getUser().getUser_avatar());
                            SpUtils.putString(mContext,"wx_id",loginBean.getUser().getWx_unionid());
                            String token = loginBean.getToken();
                            SpUtils.putString(mContext,"token",token);
                            long ttlMs = loginBean.getTtl() * 60 * 1000L;
                            long timeMillis = System.currentTimeMillis();
                            long totalMs = ttlMs + timeMillis;
                            SpUtils.putString(mContext,"totalMs",totalMs+"");
                            String go = MyUtils.go(ttlMs);
                            Logger.e(go);
                            //存一个值判断登录过
                            SpUtils.putBoolean(mContext, "abool", true);
                            //跳转到主页面
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.putExtra("token",token);
                            mContext.startActivity(intent);
                            //阿里云推送绑定账号
                            final CloudPushService pushService = PushServiceFactory.getCloudPushService();
                            pushService.bindAccount(loginBean.getUser().getUser_mobile(), new CommonCallback() {
                                @Override
                                public void onSuccess(String s) {

                                }

                                @Override
                                public void onFailed(String s, String s1) {

                                }
                            });
                            mContext.finish();
                        }else if (response.code() == 404){
                            //TODO 微信登录，没有绑定帐号
                        }else {
                            TUtils.showShort(mContext,loginBean.getMessage());
                        }
                    }
                });
    }



}
