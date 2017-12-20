package www.knowledgeshare.com.knowledgeshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.zackratos.ultimatebar.UltimateBar;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.RegisterActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.VerifyCodesBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SendSmsTimerUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ForgetActivity extends BaseActivity implements View.OnClickListener {
    public ImageView tv_callback;
    public ImageView register_headimg;
    public EditText verify_phone;
    public EditText verify_yanzheng;
    public TextView verify_huoqu;
    public TextView verify_next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setISshow(false);
        setContentView(R.layout.acitivty_forgetpwd);
        inintview();
    }

    private void inintview() {
        tv_callback = (ImageView) findViewById(R.id.tv_callback);
        register_headimg = (ImageView) findViewById(R.id.register_headimg);
        verify_phone = (EditText) findViewById(R.id.verify_phone);
        verify_yanzheng = (EditText) findViewById(R.id.verify_yanzheng);
        verify_huoqu = (TextView) findViewById(R.id.verify_huoqu);
        verify_next = (TextView) findViewById(R.id.verify_next);
        verify_phone.setOnClickListener(this);
        verify_next.setOnClickListener(this);
        tv_callback.setOnClickListener(this);
        verify_huoqu.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.verify_phone:
                verify_phone.setCursorVisible(true);
                break;
            case R.id.verify_next:
                 subit();
                break;
            case R.id.verify_huoqu:
                requestCode();
                break;
            case R.id.tv_callback:
                finish();
                break;
        }
    }

    private void requestCode() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Header-Sms","HxP&sU1YFs78RL&Src@G3YnN5ne3HYvR");
        HttpParams params = new HttpParams();
        params.put("mobile",verify_phone.getText().toString());

        OkGo.<VerifyCodesBean>post(MyContants.getPwdCode)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<VerifyCodesBean>(VerifyCodesBean.class) {
                    @Override
                    public void onSuccess(Response<VerifyCodesBean> response) {
                        VerifyCodesBean verifyCodesBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            TUtils.showShort(ForgetActivity.this,verifyCodesBean.getMessage());
                            SendSmsTimerUtils.sendSms(verify_huoqu,R.color.white,R.color.red);
                        }else {
                            TUtils.showShort(ForgetActivity.this,verifyCodesBean.getMessage());
                        }
                    }
                });
    }

    private void subit() {

        if (TextUtils.isEmpty(verify_phone.getText().toString())) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!MyUtils.isMobileNO(verify_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(verify_yanzheng.getText().toString())) {
            Toast.makeText(this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        requestgetOnePwd();

    }

    private void requestgetOnePwd() {
        HttpParams params = new HttpParams();
        params.put("mobile",verify_phone.getText().toString());
        params.put("code", verify_yanzheng.getText().toString());

        OkGo.<VerifyCodesBean>post(MyContants.getOnePwd)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<VerifyCodesBean>(ForgetActivity.this,VerifyCodesBean.class) {
                    @Override
                    public void onSuccess(Response<VerifyCodesBean> response) {
                        VerifyCodesBean verifyCodesBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            TUtils.showShort(ForgetActivity.this,verifyCodesBean.getMessage());
                            Intent it=new Intent(ForgetActivity.this,ResetpwdActivity.class);
                            it.putExtra("verify", verifyCodesBean.getVerify());
                            it.putExtra("mobile",verify_phone.getText().toString());
                            startActivity(it);
                            finish();
                        }else {
                            TUtils.showShort(ForgetActivity.this,verifyCodesBean.getMessage());
                        }
                    }
                });
    }
}
