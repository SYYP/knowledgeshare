package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.voice_switchBtn) SwitchButton voiceSwitchBtn;
    @BindView(R.id.qxhc_rl) RelativeLayout qxhcRl;
    @BindView(R.id.rjsj_rl) RelativeLayout rjsjRl;
    @BindView(R.id.yjfk_rl) RelativeLayout yjfkRl;
    @BindView(R.id.bzzx_rl) RelativeLayout bzzxRl;
    @BindView(R.id.yhxy_rl) RelativeLayout yhxyRl;
    @BindView(R.id.gywm_rl) RelativeLayout gywmRl;
    @BindView(R.id.kfzx_call_tv) TextView kfzxCallTv;
    @BindView(R.id.youxiang_tv) TextView youxiangTv;
    @BindView(R.id.exit_btn) Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("设置");
        voiceSwitchBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO YOU job

            }
        });
        titleBackIv.setOnClickListener(this);
        qxhcRl.setOnClickListener(this);
        rjsjRl.setOnClickListener(this);
        yjfkRl.setOnClickListener(this);
        bzzxRl.setOnClickListener(this);
        yhxyRl.setOnClickListener(this);
        gywmRl.setOnClickListener(this);
        kfzxCallTv.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.qxhc_rl://清除缓存
                break;
            case R.id.rjsj_rl://软件升级
                break;
            case R.id.yjfk_rl://意见反馈
                break;
            case R.id.bzzx_rl://帮助中心
                break;
            case R.id.yhxy_rl://用户协议
                break;
            case R.id.gywm_rl://关于我们
                break;
            case R.id.kfzx_call_tv://客服电话
                break;
            case R.id.exit_btn://退出
                break;

        }
    }
}
