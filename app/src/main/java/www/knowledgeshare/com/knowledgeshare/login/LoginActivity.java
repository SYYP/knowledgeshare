package www.knowledgeshare.com.knowledgeshare.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.BindPhoneActivity;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.activity.RegisterActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.base.UMLoginActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.LoginBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class LoginActivity extends UMLoginActivity implements View.OnClickListener {

    private ImageView login_headimg;
    private EditText login_phone;
    private EditText login_pwd;
    private CheckBox rember_pwd;
    private TextView no_account;
    private TextView login_sso;
    private RelativeLayout forget_pwd;
    private TextView login_close;
    private ImageView colse_back;
    private ImageView img_weixin;
    private String uniqueId;
    private UMShareAPI umShareAPI;
    private UMShareConfig config;
    private String openid;
    private String userFace;
    private String name;
    private int code;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setISshow(false);
        setContentView(R.layout.activity_login);
          /*
         获取控件id
        */
        login_headimg = (ImageView) findViewById(R.id.login_headimg);
        login_phone = (EditText) findViewById(R.id.login_phone);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        rember_pwd = (CheckBox) findViewById(R.id.rember_pwd);
        no_account = (TextView) findViewById(R.id.no_account);
        login_sso = (TextView) findViewById(R.id.login_sso);
        colse_back = (ImageView) findViewById(R.id.close_login);
        forget_pwd = (RelativeLayout) findViewById(R.id.forget_pwd);
        img_weixin = (ImageView) findViewById(R.id.weixin);
        login_phone.setOnClickListener(this);
        rember_pwd.setOnClickListener(this);
        login_pwd.setOnClickListener(this);
        no_account.setOnClickListener(this);
        login_sso.setOnClickListener(this);
        forget_pwd.setOnClickListener(this);
        colse_back.setOnClickListener(this);
        img_weixin.setOnClickListener(this);



        if (SpUtils.getBoolean(LoginActivity.this,"rember_pwd",false)){
            rember_pwd.setChecked(true);
            if (!TextUtils.isEmpty(SpUtils.getString(LoginActivity.this,"zhanghao",""))){
                login_phone.setText(SpUtils.getString(LoginActivity.this,"zhanghao",""));
                login_pwd.setText(SpUtils.getString(LoginActivity.this,"mima",""));
            }
        }

        rember_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            }
        });

//        initUM();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_login:
                finish();
                overridePendingTransition(0, R.anim.close_anim);
                break;
            case R.id.login_sso:
                login();
                break;
            case R.id.login_phone:
                login_phone.setCursorVisible(true);
                break;
            //注册
            case R.id.no_account:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            //忘记密码
            case R.id.forget_pwd:
                Intent intent1 = new Intent(this, ForgetActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.weixin:
                loginByWeiXin(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private void login() {
        if (TextUtils.isEmpty(login_phone.getText().toString())) {
            Toast.makeText(this, "请填写您的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!MyUtils.isMobileNO(login_phone.getText().toString())) {
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(login_pwd.getText().toString())) {
            Toast.makeText(this, "请填写您的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ispsd(login_pwd.getText().toString())){
            Toast.makeText(this, "请输入6-20位字母+数字组合！", Toast.LENGTH_SHORT).show();
            return;
        }
        //存一个值判断登录过
        SpUtils.putBoolean(this, "abool", true);
        requestLogin();

    }

    /**
     * 是否是纯数字或者纯英文
     * @param psd
     * @return
     */
    public static boolean ispsd(String psd) {
        Pattern p = Pattern
                .compile("^[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
        Matcher m = p.matcher(psd);

        return m.matches();
    }

    private void requestLogin() {

        HttpParams params = new HttpParams();
        params.put("mobile",login_phone.getText().toString());
        Logger.e(login_phone.getText().toString());
        //字符串倒序
        params.put("password",new StringBuffer(login_pwd.getText().toString()).reverse().toString());
        Logger.e(new StringBuffer(login_pwd.getText().toString()).reverse().toString());
        //0--手机号登录  1--微信登录
        params.put("type","0");
        String device_token = SpUtils.getString(this, "device_token", "");
        params.put("device_token",device_token);
//        params.put("device_token",uniqueId);
        params.put("from_type","2");
        Logger.e(uniqueId);

        OkGo.<LoginBean>post(MyContants.login)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<LoginBean>(LoginActivity.this,LoginBean.class) {
                    @Override
                    public void onSuccess(Response<LoginBean> response) {
                        code = response.code();
                        LoginBean loginBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            if (rember_pwd.isChecked()){
                                SpUtils.putBoolean(LoginActivity.this,"rember_pwd",true);
                                SpUtils.putString(LoginActivity.this,"zhanghao",login_phone.getText().toString());
                                SpUtils.putString(LoginActivity.this,"mima",login_pwd.getText().toString());
                            }else {
                                SpUtils.putBoolean(LoginActivity.this,"rember_pwd",false);
                            }
                            SpUtils.putString(LoginActivity.this,"id",loginBean.getUser().getId());
                            SpUtils.putString(LoginActivity.this,"name",loginBean.getUser().getUser_name());
                            SpUtils.putString(LoginActivity.this,"userFace",loginBean.getUser().getUser_avatar());
                            SpUtils.putString(LoginActivity.this,"wx_id",loginBean.getUser().getWx_unionid());
                            String token = loginBean.getToken();
                            SpUtils.putString(LoginActivity.this,"token",token);
                            long ttlMs = loginBean.getTtl() * 60 * 1000L;
                            long timeMillis = System.currentTimeMillis();
                            long totalMs = ttlMs + timeMillis;
                            SpUtils.putString(LoginActivity.this,"totalMs",totalMs+"");
                            String go = MyUtils.go(ttlMs);
                            Logger.e(go);
                            //跳转到主页面
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("token",token);
                            startActivity(intent);
                            finish();
                        }else {
                            TUtils.showShort(LoginActivity.this,loginBean.getMessage());

                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101){
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String tmDevice, tmSerial, tmPhone, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            uniqueId = deviceUuid.toString();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
