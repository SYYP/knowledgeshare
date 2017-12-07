package www.knowledgeshare.com.knowledgeshare.login;

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

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class AlterActivity extends BaseActivity implements View.OnClickListener {


    private ImageView tv_callback;
    private ImageView register_headimg;
    private EditText alter_oldpwd;
    private EditText alter_loginpwd;
    private EditText alter_forgetpwd;
    private TextView alter_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setISshow(false);
        setContentView(R.layout.activity_alterpwd);
        UltimateBar ultimateBar=new UltimateBar(this);
        ultimateBar.setImmersionBar();
        initView();

    }


    private void submit() {

        if (TextUtils.isEmpty(alter_oldpwd.getText().toString())||TextUtils.isEmpty(alter_loginpwd.getText().toString())||
                TextUtils.isEmpty(alter_forgetpwd.getText().toString())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (alter_loginpwd.getText().toString().length() < 6|| alter_loginpwd.getText().toString().length() > 20) {
            Toast.makeText(this, "请输入6-20位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!alter_loginpwd.getText().toString().equals(alter_forgetpwd.getText().toString())) {
            Toast.makeText(this, "两次输入的密码不同，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_callback:
                finish();
                break;
            case R.id.alter_oldpwd:
                alter_oldpwd.setCursorVisible(true);
                break;
            case R.id.alter_ok:
                submit();
                break;
        }
    }

    private void initView() {
        tv_callback = (ImageView) findViewById(R.id.tv_callback);
        register_headimg = (ImageView) findViewById(R.id.register_headimg);
        alter_oldpwd = (EditText) findViewById(R.id.alter_oldpwd);
        alter_loginpwd = (EditText) findViewById(R.id.alter_loginpwd);
        alter_forgetpwd = (EditText) findViewById(R.id.alter_forgetpwd);
        alter_ok = (TextView) findViewById(R.id.alter_ok);
        tv_callback.setOnClickListener(this);
        alter_oldpwd.setOnClickListener(this);
        alter_ok.setOnClickListener(this);
    }
}
