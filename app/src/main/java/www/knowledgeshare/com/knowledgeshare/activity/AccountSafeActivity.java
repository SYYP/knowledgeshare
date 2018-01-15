package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.login.AlterActivity;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

public class AccountSafeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.tel_tv) TextView telTv;
    @BindView(R.id.bdsjh_rl) RelativeLayout bdsjhRl;
    @BindView(R.id.if_bangding_tv) TextView ifBangdingTv;
    @BindView(R.id.bdwx_rl) RelativeLayout bdwxRl;
    @BindView(R.id.xgmm_rl) RelativeLayout xgmmRl;
    private String mobile;
    private String wx_id;

    private String uniqueId;
    private UMShareAPI umShareAPI;
    private UMShareConfig config;
    private String openid;
    private String userFace;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleBackIv.setOnClickListener(this);
        titleContentTv.setText("账号安全");
        bdsjhRl.setOnClickListener(this);
        bdwxRl.setOnClickListener(this);
        xgmmRl.setOnClickListener(this);
        mobile = SpUtils.getString(this, "mobile", "");
        wx_id = SpUtils.getString(this, "wx_id", "");
        Logger.e(mobile+"\n"+wx_id);
        telTv.setText(mobile);
        if (wx_id == null){
            ifBangdingTv.setText("未绑定");
        }else {
            ifBangdingTv.setText("已绑定");
        }
        initUM();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.bdsjh_rl:
                if (mobile == null){
                    Intent intent = new Intent(this, BindPhoneActivity.class);
                    intent.putExtra("type","isbind");
                    startActivity(intent);
                }else {
                    TUtils.showShort(this,"已绑定手机号");
                }
                break;
            case R.id.bdwx_rl:
                if (wx_id == null){
                    //TODO 请求绑定微信接口
                    config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO);
                    umShareAPI.setShareConfig(config);
                    umShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN,umAuthListener);
                }else {
                    TUtils.showShort(this,"已绑定微信");
                }
                break;
            case R.id.xgmm_rl:
                startActivity(new Intent(this,AlterActivity.class));
                break;
        }
    }

    private void initUM() {
        Config.DEBUG = true;
        umShareAPI = UMShareAPI.get(this);
        PlatformConfig.setWeixin("wxf33afce9142929dc","1ec3dd295f384088757e5fd5500ef897");
        config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            openid = data.get("openid");
            userFace = data.get("iconurl");
            name = data.get("screen_name");
            Logger.e("openid:"+ openid +"\n"+"userFace:"+ userFace +"\n"+"name:"+ name);
            requestBindWx(openid,name);
            TUtils.showShort(AccountSafeActivity.this,"微信登录成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private void requestBindWx(String openid, String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("wx_unionid",openid);
        params.put("wx_name",name);
        OkGo.<BaseBean>post(MyContants.bindWx)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<BaseBean>(this,BaseBean.class) {

                    private BaseBean baseBean;

                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            baseBean = response.body();
                            TUtils.showShort(AccountSafeActivity.this, baseBean.getMessage());
                        }else {
                            TUtils.showShort(AccountSafeActivity.this,baseBean.getMessage());
                        }
                    }
                });

    }
}
