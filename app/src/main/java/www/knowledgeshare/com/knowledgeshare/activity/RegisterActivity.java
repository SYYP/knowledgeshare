package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.zackratos.ultimatebar.UltimateBar;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.VerifyCodesBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.login.SetloginActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SendSmsTimerUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 * 注册
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    public ImageView register_headimg;
    public EditText register_phone;
    public EditText register_yanzheng;
    public TextView register_huoqu;
    public CheckBox register_agress;
    public TextView register_next;
    private ImageView tv_callback;
    private TextView register_xieyi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_register);
        initview();
    }
        public void  initview(){
            this.register_headimg = (ImageView) findViewById(R.id.register_headimg);
            this.register_phone = (EditText) findViewById(R.id.register_phone);
            this.register_yanzheng = (EditText) findViewById(R.id.register_yanzheng);
            this.register_huoqu = (TextView) findViewById(R.id.register_huoqu);
            this.register_agress = (CheckBox)findViewById(R.id.register_agress);
            this.register_next = (TextView) findViewById(R.id.register_next);
            tv_callback = (ImageView) findViewById(R.id.tv_callback);
            register_xieyi = (TextView) findViewById(R.id.register_xieyi);
             register_next.setOnClickListener(this);
            tv_callback.setOnClickListener(this);
            register_huoqu.setOnClickListener(this);
            register_xieyi.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_xieyi:
                  Intent intent=new Intent(this,UserAgreementActivity.class);
                  startActivity(intent);
                break;
            case R.id.register_next:
//                Intent intent2 = new Intent(RegisterActivity.this, SetloginActivity.class);
//                SpUtils.putString(RegisterActivity.this,"mobile",register_phone.getText().toString());
//                intent2.putExtra("verify",register_yanzheng.getText().toString());
//                startActivity(intent2);
//                finish();
                 register();
                break;
            case R.id.register_phone:
                register_phone.setCursorVisible(true);
                break;
            case R.id.tv_callback:
                finish();
                break;
            case R.id.register_huoqu:
                requestVerifyCodes();
                break;
        }

    }

    private void requestVerifyCodes() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Header-Sms","HxP&sU1YFs78RL&Src@G3YnN5ne3HYvR");
        HttpParams params = new HttpParams();
        params.put("mobile",register_phone.getText().toString());

        OkGo.<VerifyCodesBean>post(MyContants.verifycodes)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<VerifyCodesBean>(RegisterActivity.this,VerifyCodesBean.class) {
                    @Override
                    public void onSuccess(Response<VerifyCodesBean> response) {
                        VerifyCodesBean verifyCodesBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            TUtils.showShort(RegisterActivity.this,verifyCodesBean.getMessage());
                            SendSmsTimerUtils.sendSms(register_huoqu, R.color.white, R.color.red);
                        }else {
                            TUtils.showShort(RegisterActivity.this,verifyCodesBean.getMessage());
                        }
                    }
                });
    }

    private void register() {
        if (TextUtils.isEmpty(register_phone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!MyUtils.isMobileNO(register_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(register_agress.isChecked())){
              Toast.makeText(this, "请同意用户注册协议", Toast.LENGTH_SHORT).show();
              return;
          }
        if (TextUtils.isEmpty(register_yanzheng.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        requestRegistSetOne();

    }

    private void requestRegistSetOne() {
        HttpParams params = new HttpParams();
        params.put("mobile",register_phone.getText().toString());
        params.put("code",register_yanzheng.getText().toString());

        OkGo.<VerifyCodesBean>post(MyContants.registSetOne)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<VerifyCodesBean>(RegisterActivity.this,VerifyCodesBean.class) {
                    @Override
                    public void onSuccess(Response<VerifyCodesBean> response) {
                        VerifyCodesBean verifyCodesBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            Intent intent = new Intent(RegisterActivity.this, SetloginActivity.class);
                            intent.putExtra("verify", verifyCodesBean.getVerify());
                            SpUtils.putString(RegisterActivity.this,"mobile",register_phone.getText().toString());
                            startActivity(intent);
                            finish();
                        }else {
                            TUtils.showShort(RegisterActivity.this,verifyCodesBean.getMessage());
                        }
                    }
                });
    }
}
