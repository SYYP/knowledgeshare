package www.knowledgeshare.com.knowledgeshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.zackratos.ultimatebar.UltimateBar;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class SetloginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView tv_callback;
    private ImageView register_headimg;
    private EditText set_loginpwd;
    private EditText login_forgetpwd;
    private TextView login_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setISshow(false);
        setContentView(R.layout.activity_setloginpwd);
        initView();
    }
    private void initView() {
        tv_callback = (ImageView) findViewById(R.id.tv_callback);
        register_headimg = (ImageView) findViewById(R.id.register_headimg);
        set_loginpwd = (EditText) findViewById(R.id.set_loginpwd);
        login_forgetpwd = (EditText) findViewById(R.id.login_forgetpwd);
        login_ok = (TextView) findViewById(R.id.login_ok);
        tv_callback.setOnClickListener(this);
        set_loginpwd.setOnClickListener(this);
        login_ok.setOnClickListener(this);
    }

    private void submit() {
        if (TextUtils.isEmpty(set_loginpwd.getText().toString())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (set_loginpwd.getText().toString().length() < 6|| set_loginpwd.getText().toString().length() > 20) {
            Toast.makeText(this, "请输入6-20位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!set_loginpwd.getText().toString().equals(login_forgetpwd.getText().toString())) {
            Toast.makeText(this, "两次输入的密码不同，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.set_loginpwd:
                 set_loginpwd.setCursorVisible(true);
                break;
            case R.id.login_ok:
                 submit();
                break;

        }

    }
    private void showDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_setpwd)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.show();
        TextView tv_yes = dialog.getView(R.id.tv_yes);
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭dialog
                dialog.close();
                 //跳到登录页面
                Intent intent=new Intent(SetloginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
