package www.knowledgeshare.com.knowledgeshare.login;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

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
        if (!ispsd(alter_loginpwd.getText().toString())){
            Toast.makeText(this, "请输入6-20位字母+数字组合！", Toast.LENGTH_SHORT).show();
            return;
        }
        requestEditPwd();
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

    private void requestEditPwd() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("oldpwd",new StringBuffer(alter_oldpwd.getText().toString()).reverse().toString());
        params.put("newpwd",new StringBuffer(alter_loginpwd.getText().toString()).reverse().toString());

        OkGo.<BaseBean>post(MyContants.editPwd)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<BaseBean>(this,BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            TUtils.showShort(AlterActivity.this,response.body().getMessage());
                            finish();
                        }else {
                            TUtils.showShort(AlterActivity.this,response.body().getMessage());
                        }
                    }
                });
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
