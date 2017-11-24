package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

/**
 * 学习记录
 */

public class LearningRecordActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.llls_rl) RelativeLayout lllsRl;
    @BindView(R.id.xz_rl) RelativeLayout xzRl;
    @BindView(R.id.sc_rl) RelativeLayout scRl;
    @BindView(R.id.xxbj_rl) RelativeLayout xxbjRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_record);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("学习记录");
        lllsRl.setOnClickListener(this);
        xzRl.setOnClickListener(this);
        scRl.setOnClickListener(this);
        xxbjRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.llls_rl://浏览历史
                break;
            case R.id.xz_rl://下载
                break;
            case R.id.sc_rl://收藏
                break;
            case R.id.xxbj_rl://学习笔记
                break;
        }
    }
}
