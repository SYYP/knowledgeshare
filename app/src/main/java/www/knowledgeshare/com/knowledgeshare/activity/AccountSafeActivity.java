package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.login.AlterActivity;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

public class AccountSafeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.tel_tv) TextView telTv;
    @BindView(R.id.bdsjh_rl) RelativeLayout bdsjhRl;
    @BindView(R.id.if_bangding_tv) TextView ifBangdingTv;
    @BindView(R.id.bdwx_rl) RelativeLayout bdwxRl;
    @BindView(R.id.xgmm_rl) RelativeLayout xgmmRl;
    private String mobile;
    private String wx_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleBackIv.setOnClickListener(this);
        titleContentTv.setText("账号安全");
        bdsjhRl.setOnClickListener(this);
        bdwxRl.setOnClickListener(this);
        xgmmRl.setOnClickListener(this);
        mobile = SpUtils.getString(this, "mobile", "");
        wx_id = SpUtils.getString(this, "wx_id", "");
        Logger.e(mobile+"\n"+wx_id);
        telTv.setText(mobile);
        if (wx_id == null){
            ifBangdingTv.setText("未绑定");
        }else {
            ifBangdingTv.setText("已绑定");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.bdsjh_rl:
                if (mobile == null){
                    Intent intent = new Intent(this, BindPhoneActivity.class);
                    intent.putExtra("type","isbind");
                    startActivity(intent);
                }else {
                    TUtils.showShort(this,"已绑定手机号");
                }
                break;
            case R.id.bdwx_rl:
                if (wx_id == null){
                    //TODO 请求绑定微信接口
                }else {
                    TUtils.showShort(this,"已绑定微信");
                }
                break;
            case R.id.xgmm_rl:
                startActivity(new Intent(this,AlterActivity.class));
                break;
        }
    }
}
