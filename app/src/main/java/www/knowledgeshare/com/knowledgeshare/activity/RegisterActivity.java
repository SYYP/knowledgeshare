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

import org.zackratos.ultimatebar.UltimateBar;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.login.SetloginActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SendSmsTimerUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
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
                
                 register();
                break;
            case R.id.register_phone:
                register_phone.setCursorVisible(true);
                break;
            case R.id.tv_callback:
                finish();
                break;
            case R.id.register_huoqu:
                SendSmsTimerUtils.sendSms(register_huoqu, R.color.white, R.color.text_red);
                break;
        }

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
          if(!(register_agress.isChecked())){
              Toast.makeText(this, "请同意用户注册协议", Toast.LENGTH_SHORT).show();
              return;
          }
        if (TextUtils.isEmpty(register_yanzheng.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent=new Intent(this, SetloginActivity.class);
          startActivity(intent);
        finish();
    }
}
