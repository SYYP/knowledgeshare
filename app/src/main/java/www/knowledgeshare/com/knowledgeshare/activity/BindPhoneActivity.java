package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.zackratos.ultimatebar.UltimateBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.SendSmsTimerUtils;

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_callback) ImageView ivCallback;
    @BindView(R.id.register_headimg) ImageView registerHeadimg;
    @BindView(R.id.name_tv) TextView nameTv;
    @BindView(R.id.set_lpwd) ImageView setLpwd;
    @BindView(R.id.verify_phone) EditText verifyPhone;
    @BindView(R.id.set_pwd) ImageView setPwd;
    @BindView(R.id.verify_yanzheng) EditText verifyYanzheng;
    @BindView(R.id.verify_huoqu) TextView verifyHuoqu;
    @BindView(R.id.verify_query) TextView verifyQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivCallback.setOnClickListener(this);
        verifyHuoqu.setOnClickListener(this);
        verifyQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_callback:
                finish();
                break;
            case R.id.verify_huoqu:
                SendSmsTimerUtils.sendSms(verifyHuoqu,R.color.white,R.color.red);
                break;
            case R.id.verify_query:
                break;
        }
    }
}
