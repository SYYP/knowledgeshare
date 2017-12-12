package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.zackratos.ultimatebar.UltimateBar;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.login.ForgetActivity;
import www.knowledgeshare.com.knowledgeshare.login.bean.HobbyActivity;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_login:
                finish();

                overridePendingTransition(0, R.anim.close_anim);
                break;
            case R.id.login_sso:
                //                login();
                startActivity(new Intent(this, HobbyActivity.class));
                //存一个值判断登录过
                SpUtils.putBoolean(this, "abool", true);
                finish();
                break;
            case R.id.login_phone:
                login_phone.setCursorVisible(true);
                break;

            /*
              注册
             */
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
                Intent intent2 = new Intent(this, BindPhoneActivity.class);
                startActivity(intent2);
                finish();
                break;
        }

    }

    private void login() {
        //        if (TextUtils.isEmpty(login_phone.getText().toString())) {
        //            Toast.makeText(this, "请填写您的手机号", Toast.LENGTH_SHORT).show();
        //            return;
        //        }
        //        else if (!MyUtils.isMobileNO(login_phone.getText().toString())) {
        //            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
        //            return;
        //        }
        //        if (TextUtils.isEmpty(login_pwd.getText().toString())) {
        //            Toast.makeText(this, "请填写您的密码", Toast.LENGTH_SHORT).show();
        //            return;
        //        }
        //跳转到主页面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
