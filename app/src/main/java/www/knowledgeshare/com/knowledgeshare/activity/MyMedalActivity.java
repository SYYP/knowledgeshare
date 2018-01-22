package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

import static www.knowledgeshare.com.knowledgeshare.R.*;

public class MyMedalActivity extends BaseActivity implements View.OnClickListener {

    @BindView(id.title_back_iv)
    ImageView titleBackIv;
    @BindView(id.title_content_tv)
    TextView titleContentTv;
    @BindView(id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(id.tong_tv) TextView tongTv;
    @BindView(id.yin_tv) TextView yinTv;
    @BindView(id.jin_tv) TextView jinTv;
    @BindView(id.baijin_tv) TextView baijinTv;
    @BindView(id.zuanshi_tv) TextView zuanshiTv;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private Drawable five;
    private Drawable four;
    private Drawable three;
    private Drawable two;
    private Drawable one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_my_medal);
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
        String level = getIntent().getStringExtra("level");
        boolean isShow = SpUtils.getBoolean(MyMedalActivity.this, "isShow", false);
        if (!level.equals("0") && !isShow){
            showShareDialog(level);
        }else {
            Show(level);
        }
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case id.title_back_iv:
                finish();
                break;
            case id.title_content_right_tv:
                startActivity(new Intent(this, MedalDetailActivity.class));
                break;
        }
    }

    private void showShareDialog(final String level) {
        mDialog = mBuilder.setViewId(layout.dialog_xunzhang)
                //设置dialogpadding
                .setPaddingdp(30, 0, 30, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(false)
                //设置监听事件
                .builder();
        ImageView dengjiIv = mDialog.getView(id.dengji_iv);
        TextView titleTv = mDialog.getView(id.title_tv);
        TextView contentTv = mDialog.getView(id.content_tv);
        switch (level){
            case "1":
                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_tong));
                titleTv.setText("恭喜您获得铜勋章");
                contentTv.setText("并获得3元代金券，已放入账户余额");
                break;
            case "2":
                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_yin));
                titleTv.setText("恭喜您获得银勋章");
                contentTv.setText("并获得8元代金券，已放入账户余额");
                break;
            case "3":
                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_jin));
                titleTv.setText("恭喜您获得金勋章");
                contentTv.setText("并获取9.8折优惠，永久有效");
                break;
            case "4":
                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_baijin));
                titleTv.setText("恭喜您获得白金勋章");
                contentTv.setText("并获取9.5折优惠，永久有效");
                break;
            case "5":
                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_zuanshi));
                titleTv.setText("恭喜您获得钻石勋章");
                contentTv.setText("并获取9.0折优惠，永久有效");
                break;
        }
        mDialog.show();
        SpUtils.putBoolean(MyMedalActivity.this,"isShow",true);
        mDialog.getView(id.tv_cancel).setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                Show(level);
                mDialog.dismiss();
            }
        });
    }

    private void Show(String level){
        switch (level){
            case "1":
                one = getResources().getDrawable(drawable.power_tong);
                // 这一步必须要做,否则不会显示.
                one.setBounds(0, 0, one.getMinimumWidth(), one.getMinimumHeight());
                tongTv.setCompoundDrawables(null, one,null,null);
                break;
            case "2":
                one= getResources().getDrawable(drawable.power_tong);
                // 这一步必须要做,否则不会显示.
                one.setBounds(0, 0, one.getMinimumWidth(), one.getMinimumHeight());
                tongTv.setCompoundDrawables(null, one,null,null);

                two = getResources().getDrawable(drawable.power_yin);
                // 这一步必须要做,否则不会显示.
                two.setBounds(0, 0, two.getMinimumWidth(), two.getMinimumHeight());
                yinTv.setCompoundDrawables(null,two,null,null);
                break;
            case "3":
                one= getResources().getDrawable(drawable.power_tong);
                // 这一步必须要做,否则不会显示.
                one.setBounds(0, 0, one.getMinimumWidth(), one.getMinimumHeight());
                tongTv.setCompoundDrawables(null, one,null,null);

                two = getResources().getDrawable(drawable.power_yin);
                // 这一步必须要做,否则不会显示.
                two.setBounds(0, 0, two.getMinimumWidth(), two.getMinimumHeight());
                yinTv.setCompoundDrawables(null,two,null,null);

                three = getResources().getDrawable(drawable.power_jin);
                // 这一步必须要做,否则不会显示.
                three.setBounds(0, 0, three.getMinimumWidth(), three.getMinimumHeight());
                jinTv.setCompoundDrawables(null,three,null,null);
                break;
            case "4":
                one= getResources().getDrawable(drawable.power_tong);
                // 这一步必须要做,否则不会显示.
                one.setBounds(0, 0, one.getMinimumWidth(), one.getMinimumHeight());
                tongTv.setCompoundDrawables(null, one,null,null);

                two = getResources().getDrawable(drawable.power_yin);
                // 这一步必须要做,否则不会显示.
                two.setBounds(0, 0, two.getMinimumWidth(), two.getMinimumHeight());
                yinTv.setCompoundDrawables(null,two,null,null);

                three = getResources().getDrawable(drawable.power_jin);
                // 这一步必须要做,否则不会显示.
                three.setBounds(0, 0, three.getMinimumWidth(), three.getMinimumHeight());
                jinTv.setCompoundDrawables(null,three,null,null);

                four = getResources().getDrawable(drawable.power_baijin);
                // 这一步必须要做,否则不会显示.
                four.setBounds(0, 0, four.getMinimumWidth(), four.getMinimumHeight());
                baijinTv.setCompoundDrawables(null, four,null,null);
                break;
            case "5":
                one= getResources().getDrawable(drawable.power_tong);
                // 这一步必须要做,否则不会显示.
                one.setBounds(0, 0, one.getMinimumWidth(), one.getMinimumHeight());
                tongTv.setCompoundDrawables(null, one,null,null);

                two = getResources().getDrawable(drawable.power_yin);
                // 这一步必须要做,否则不会显示.
                two.setBounds(0, 0, two.getMinimumWidth(), two.getMinimumHeight());
                yinTv.setCompoundDrawables(null,two,null,null);

                three = getResources().getDrawable(drawable.power_jin);
                // 这一步必须要做,否则不会显示.
                three.setBounds(0, 0, three.getMinimumWidth(), three.getMinimumHeight());
                jinTv.setCompoundDrawables(null,three,null,null);

                four = getResources().getDrawable(drawable.power_baijin);
                // 这一步必须要做,否则不会显示.
                four.setBounds(0, 0, four.getMinimumWidth(), four.getMinimumHeight());
                baijinTv.setCompoundDrawables(null, four,null,null);

                five = getResources().getDrawable(drawable.power_zuanshi);
                // 这一步必须要做,否则不会显示.
                five.setBounds(0, 0, five.getMinimumWidth(), five.getMinimumHeight());
                zuanshiTv.setCompoundDrawables(null, five,null,null);
        }
    }
}
