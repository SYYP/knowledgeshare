package www.knowledgeshare.com.knowledgeshare.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.LoginBean;
import www.knowledgeshare.com.knowledgeshare.bean.VerifyCodesBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SendSmsTimerUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_callback) ImageView ivCallback;
    @BindView(R.id.register_headimg) ImageView registerHeadimg;
    @BindView(R.id.name_tv) TextView nameTv;
    @BindView(R.id.set_lpwd) ImageView setLpwd;
    @BindView(R.id.verify_phone) EditText verifyPhone;
    @BindView(R.id.set_pwd) ImageView setPwd;
    @BindView(R.id.verify_yanzheng) EditText verifyYanzheng;
    @BindView(R.id.verify_huoqu) TextView verifyHuoqu;
    @BindView(R.id.verify_query) TextView verifyQuery;
    @BindView(R.id.tv_tiaoguo) TextView tv_tiaoguo;
    private String uniqueId;
    private String openid;
    private String name;
    private int flag = -1; // 0微信登录  1这是界面绑定手机号


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        initView();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))){
            String type = getIntent().getStringExtra("type");
            if (type.equals("wechat")){
                flag = 0;
                tv_tiaoguo.setVisibility(View.VISIBLE);
                openid = getIntent().getStringExtra("openid");
                String userFace = getIntent().getStringExtra("userFace");
                name = getIntent().getStringExtra("name");
                nameTv.setText("Hi! "+ name);

            }else {
                flag = 1;
                tv_tiaoguo.setVisibility(View.GONE);
                nameTv.setText("Hi! "+ SpUtils.getString(this,"name",""));
            }
        }
    }

    private void initView() {
        ivCallback.setOnClickListener(this);
        verifyHuoqu.setOnClickListener(this);
        verifyQuery.setOnClickListener(this);
        tv_tiaoguo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.verify_huoqu:
                requestVerifyCodes();
                break;
            case R.id.verify_query:
                submit();
                break;
            case R.id.tv_tiaoguo:
                requestLogin1();
                break;
        }
    }

    private void requestLogin1() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, tmPhone, androidId;
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
        } else {
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            uniqueId = deviceUuid.toString();
        }
        HttpParams params = new HttpParams();
        //0--手机号登录  1--微信登录
        params.put("type","1");
        params.put("device_token",uniqueId);
        params.put("wx_name",name);
        params.put("wx_unionid",openid);

        OkGo.<LoginBean>post(MyContants.login)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<LoginBean>(BindPhoneActivity.this,LoginBean.class) {
                    @Override
                    public void onSuccess(Response<LoginBean> response) {
                        LoginBean loginBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            SpUtils.putString(BindPhoneActivity.this,"id",loginBean.getUser().getId());
                            SpUtils.putString(BindPhoneActivity.this,"name",loginBean.getUser().getUser_name());
                            SpUtils.putString(BindPhoneActivity.this,"wx_id",loginBean.getUser().getWx_unionid());
                            String token = loginBean.getToken();
                            SpUtils.putString(BindPhoneActivity.this,"token",token);
                            long ttlMs = loginBean.getTtl() * 60 * 1000L;
                            long timeMillis = System.currentTimeMillis();
                            long totalMs = ttlMs + timeMillis;
                            SpUtils.putString(BindPhoneActivity.this,"totalMs",totalMs+"");
                            String go = MyUtils.go(ttlMs);
                            Logger.e(go);
                            //存一个值判断登录过
                            SpUtils.putBoolean(BindPhoneActivity.this, "abool", true);
                            //跳转到主页面
                            Intent intent = new Intent(BindPhoneActivity.this, MainActivity.class);
                            intent.putExtra("token",token);
                            startActivity(intent);
                            finish();
                        }else if (response.code() == 404){
                            //TODO 微信登录，没有绑定帐号
                        }else {
                            TUtils.showShort(BindPhoneActivity.this,loginBean.getMessage());
                        }
                    }
                });
    }

    private void submit() {
        if (TextUtils.isEmpty(verifyPhone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!MyUtils.isMobileNO(verifyPhone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(verifyYanzheng.getText().toString())) {
            Toast.makeText(this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (flag == 0){
            requestLogin(openid,name,flag);
        }else if (flag == 1){
            requestLogin(SpUtils.getString(this,"wx_id",""),SpUtils.getString(this,"name",""),flag);
        }


    }

    private void requestVerifyCodes() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Header-Sms","HxP&sU1YFs78RL&Src@G3YnN5ne3HYvR");
        HttpParams params = new HttpParams();
        params.put("mobile",verifyPhone.getText().toString());

        OkGo.<VerifyCodesBean>post(MyContants.getPwdCode)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<VerifyCodesBean>(BindPhoneActivity.this,VerifyCodesBean.class) {
                    @Override
                    public void onSuccess(Response<VerifyCodesBean> response) {
                        VerifyCodesBean verifyCodesBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            TUtils.showShort(BindPhoneActivity.this,verifyCodesBean.getMessage());
                            SendSmsTimerUtils.sendSms(verifyHuoqu,R.color.white,R.color.red);
                        }else {
                            TUtils.showShort(BindPhoneActivity.this,verifyCodesBean.getMessage());
                        }
                    }
                });
    }

    private void requestLogin(String wxId, String name, final int flag) {
        HttpParams params = new HttpParams();
        params.put("mobile",verifyPhone.getText().toString());
        params.put("code",verifyYanzheng.getText().toString());
        params.put("wx_unionid",wxId);
        params.put("wx_name",name);

        OkGo.<LoginBean>post(MyContants.bindMobile)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<LoginBean>(BindPhoneActivity.this,LoginBean.class) {
                    @Override
                    public void onSuccess(Response<LoginBean> response) {
                        LoginBean loginBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            SpUtils.putString(BindPhoneActivity.this,"id",loginBean.getUser().getId());
                            SpUtils.putString(BindPhoneActivity.this,"name",loginBean.getUser().getUser_name());
                            SpUtils.putString(BindPhoneActivity.this,"wx_id",loginBean.getUser().getWx_unionid());
                            String token = loginBean.getToken();
                            SpUtils.putString(BindPhoneActivity.this,"token",token);
                            long ttlMs = loginBean.getTtl() * 60 * 1000L;
                            long timeMillis = System.currentTimeMillis();
                            long totalMs = ttlMs + timeMillis;
                            SpUtils.putString(BindPhoneActivity.this,"totalMs",totalMs+"");
                            String go = MyUtils.go(ttlMs);
                            Logger.e(go);
                            //存一个值判断登录过
                            SpUtils.putBoolean(BindPhoneActivity.this, "abool", true);
                            if (flag == 0){
                                //跳转到主页面
                                Intent intent = new Intent(BindPhoneActivity.this, MainActivity.class);
                                intent.putExtra("token",token);
                                startActivity(intent);
                                finish();
                            }if (flag == 1){
                                TUtils.showShort(BindPhoneActivity.this,"绑定成功");
                                finish();
                            }

                        }else if (response.code() == 404){
                            //TODO 微信登录，没有绑定帐号
                        }else {
                            TUtils.showShort(BindPhoneActivity.this,loginBean.getMessage());
                        }
                    }
                });
    }

}
