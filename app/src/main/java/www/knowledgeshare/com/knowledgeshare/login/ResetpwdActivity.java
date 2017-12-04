package www.knowledgeshare.com.knowledgeshare.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        tv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void submit() {
        // validate
        String loginpwd = reset_loginpwd.getText().toString().trim();
        if (TextUtils.isEmpty(loginpwd)) {
            Toast.makeText(this, "请输入6~20位字母，数字密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String forgetpwd = reset_forgetpwd.getText().toString().trim();
        if (TextUtils.isEmpty(forgetpwd)) {
            Toast.makeText(this, "请输入相同的密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

}
