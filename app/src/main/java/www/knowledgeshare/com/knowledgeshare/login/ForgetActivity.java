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

import org.zackratos.ultimatebar.UltimateBar;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SendSmsTimerUtils;

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
        setContentView(R.layout.acitivty_forgetpwd);
        setNotshow();
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
                SendSmsTimerUtils.sendSms(verify_huoqu,R.color.white,R.color.red);
                break;
            case R.id.tv_callback:
                finish();
                break;
        }
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
        Intent it=new Intent(this,ResetpwdActivity.class);
        startActivity(it);
        finish();
    }
}
