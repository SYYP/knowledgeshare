package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;

public class MyMedalActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.tong_tv) TextView tongTv;
    @BindView(R.id.yin_tv) TextView yinTv;
    @BindView(R.id.jin_tv) TextView jinTv;
    @BindView(R.id.baijin_tv) TextView baijinTv;
    @BindView(R.id.zuanshi_tv) TextView zuanshiTv;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medal);
        ButterKnife.bind(this);
        initDialog();
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleBackIv.setOnClickListener(this);
        titleContentTv.setText("我的勋章");
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentRightTv.setText("勋章优惠说明");
        titleContentRightTv.setOnClickListener(this);
        showShareDialog();
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                startActivity(new Intent(this, MedalDetailActivity.class));
                break;
        }
    }

    private void showShareDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_xunzhang)
                //设置dialogpadding
                .setPaddingdp(30, 0, 30, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(false)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable= getResources().getDrawable(R.drawable.power_tong);
                // 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tongTv.setCompoundDrawables(null,drawable,null,null);
                mDialog.dismiss();
            }
        });
    }
}
