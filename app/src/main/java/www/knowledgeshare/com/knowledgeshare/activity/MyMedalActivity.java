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

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.UserInfoBean;
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
    @BindView(id.tong_iv) ImageView tongIv;
    @BindView(id.yin_iv) ImageView yinIv;
    @BindView(id.jin_iv) ImageView jinIv;
    @BindView(id.baijin_iv) ImageView baijinIv;
    @BindView(id.zuanshi_iv) ImageView zuanshiIv;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private Drawable five;
    private Drawable four;
    private Drawable three;
    private Drawable two;
    private Drawable one;
    private List<UserInfoBean.LevelBean> levelList;

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
        String level1 = SpUtils.getString(this, "level", "");
        levelList = (List<UserInfoBean.LevelBean>) getIntent().getExtras().getSerializable("xunzhang");
        Glide.with(this).load(levelList.get(0).getLevel_no_img()).into(tongIv);
        Glide.with(this).load(levelList.get(1).getLevel_no_img()).into(yinIv);
        Glide.with(this).load(levelList.get(2).getLevel_no_img()).into(jinIv);
        Glide.with(this).load(levelList.get(3).getLevel_no_img()).into(baijinIv);
        Glide.with(this).load(levelList.get(4).getLevel_no_img()).into(zuanshiIv);
        if (!level.equals("0")&& !level.equals(level1)){
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
                Glide.with(this).load(levelList.get(0).getLevel_get_img()).into(dengjiIv);
//                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_tong));
                titleTv.setText("恭喜您获得铜勋章");
                contentTv.setText("并获得3元代金券，已放入账户余额");
                mDialog.show();
                SpUtils.putString(this,"level",level);
                break;
            case "2":
                Glide.with(this).load(levelList.get(1).getLevel_get_img()).into(dengjiIv);
//                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_yin));
                titleTv.setText("恭喜您获得银勋章");
                contentTv.setText("并获得8元代金券，已放入账户余额");
                mDialog.show();
                SpUtils.putString(this,"level",level);
                break;
            case "3":
                Glide.with(this).load(levelList.get(2).getLevel_get_img()).into(dengjiIv);
//                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_jin));
                titleTv.setText("恭喜您获得金勋章");
                contentTv.setText("并获取9.8折优惠，永久有效");
                mDialog.show();
                SpUtils.putString(this,"level",level);
                break;
            case "4":
                Glide.with(this).load(levelList.get(3).getLevel_get_img()).into(dengjiIv);
//                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_baijin));
                titleTv.setText("恭喜您获得白金勋章");
                contentTv.setText("并获取9.5折优惠，永久有效");
                mDialog.show();
                SpUtils.putString(this,"level",level);
                break;
            case "5":
                Glide.with(this).load(levelList.get(4).getLevel_get_img()).into(dengjiIv);
//                dengjiIv.setImageDrawable(getResources().getDrawable(drawable.power_zuanshi));
                titleTv.setText("恭喜您获得钻石勋章");
                contentTv.setText("并获取9.0折优惠，永久有效");
                mDialog.show();
                SpUtils.putString(this,"level",level);
                break;
        }
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
                Glide.with(this).load(levelList.get(0).getLevel_get_img()).into(tongIv);
                break;
            case "2":
                Glide.with(this).load(levelList.get(0).getLevel_get_img()).into(tongIv);
                Glide.with(this).load(levelList.get(1).getLevel_get_img()).into(yinIv);
                break;
            case "3":
                Glide.with(this).load(levelList.get(0).getLevel_get_img()).into(tongIv);
                Glide.with(this).load(levelList.get(1).getLevel_get_img()).into(yinIv);
                Glide.with(this).load(levelList.get(2).getLevel_get_img()).into(jinIv);
                break;
            case "4":
                Glide.with(this).load(levelList.get(0).getLevel_get_img()).into(tongIv);
                Glide.with(this).load(levelList.get(1).getLevel_get_img()).into(yinIv);
                Glide.with(this).load(levelList.get(2).getLevel_get_img()).into(jinIv);
                Glide.with(this).load(levelList.get(3).getLevel_get_img()).into(baijinIv);
                break;
            case "5":
                Glide.with(this).load(levelList.get(0).getLevel_get_img()).into(tongIv);
                Glide.with(this).load(levelList.get(1).getLevel_get_img()).into(yinIv);
                Glide.with(this).load(levelList.get(2).getLevel_get_img()).into(jinIv);
                Glide.with(this).load(levelList.get(3).getLevel_get_img()).into(baijinIv);
                Glide.with(this).load(levelList.get(3).getLevel_get_img()).into(zuanshiIv);
        }
    }
}
