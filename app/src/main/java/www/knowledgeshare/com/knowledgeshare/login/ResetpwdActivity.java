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
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.zackratos.ultimatebar.UltimateBar;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.VerifyCodesBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ResetpwdActivity extends BaseActivity {

    private ImageView tv_callback;
    private ImageView register_headimg;
    private ImageView set_lpwd;
    private EditText reset_loginpwd;
    private ImageView set_pwd;
    private EditText reset_forgetpwd;
    private TextView reset_ok;
    private String mobile;
    private String verify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setISshow(false);
        setContentView(R.layout.activity_resetpwd);
        initView();
    }

    private void initView() {
        tv_callback = (ImageView) findViewById(R.id.tv_callback);
        register_headimg = (ImageView) findViewById(R.id.register_headimg);
        set_lpwd = (ImageView) findViewById(R.id.set_lpwd);
        reset_loginpwd = (EditText) findViewById(R.id.reset_loginpwd);
        set_pwd = (ImageView) findViewById(R.id.set_pwd);
        reset_forgetpwd = (EditText) findViewById(R.id.reset_forgetpwd);
        reset_ok = (TextView) findViewById(R.id.reset_ok);
        reset_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        tv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mobile = getIntent().getStringExtra("mobile");
        verify = getIntent().getStringExtra("verify");


    }

    private void submit() {
        if (TextUtils.isEmpty( reset_loginpwd.getText().toString())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( reset_loginpwd.getText().toString().length() < 6|| reset_loginpwd.getText().toString().length() > 20) {
            Toast.makeText(this, "请输入6~20位字母、数字新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!reset_loginpwd.getText().toString().equals( reset_forgetpwd.getText().toString())) {
            Toast.makeText(this, "两次输入的密码不同，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        requestSetTwoPwd();

    }

    private void requestSetTwoPwd() {
        HttpParams params = new HttpParams();
        params.put("mobile",mobile);
        params.put("password",new StringBuffer(reset_loginpwd.getText().toString()).reverse().toString());
        params.put("verify",verify);

        OkGo.<BaseBean>post(MyContants.getTwoPwd)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<BaseBean>(ResetpwdActivity.this,BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        BaseBean baseBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            TUtils.showShort(ResetpwdActivity.this,baseBean.getMessage());
                            Intent intent=new Intent(ResetpwdActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            TUtils.showShort(ResetpwdActivity.this,baseBean.getMessage());
                        }
                    }
                });
    }

}
