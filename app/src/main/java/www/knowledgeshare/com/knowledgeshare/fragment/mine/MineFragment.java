package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.AccountSafeActivity;
import www.knowledgeshare.com.knowledgeshare.activity.LearningRecordActivity;
import www.knowledgeshare.com.knowledgeshare.activity.LearningTimeActivity;
import www.knowledgeshare.com.knowledgeshare.activity.MyAccountActivity;
import www.knowledgeshare.com.knowledgeshare.activity.MyMedalActivity;
import www.knowledgeshare.com.knowledgeshare.activity.MySubscriptionsActivity;
import www.knowledgeshare.com.knowledgeshare.activity.PersonInfomationActivity;
import www.knowledgeshare.com.knowledgeshare.activity.SettingActivity;
import www.knowledgeshare.com.knowledgeshare.activity.TaskDetailActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.UserInfoBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

/**
 * Created by Administrator on 2017/11/17.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.title_message_iv) ImageView titleMessageIv;@BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.title_setting_iv) ImageView titleSettingIv;@BindView(R.id.mine_face_iv) CircleImageView mineFaceIv;
    @BindView(R.id.mine_name_tv) TextView mineNameTv;@BindView(R.id.xz_start_iv) ImageView xzStartIv;
    @BindView(R.id.progressBar) ProgressBar progressBar;@BindView(R.id.xz_end_iv) ImageView xzEndIv;
    @BindView(R.id.qiandao_btn) TextView qiandaoBtn;@BindView(R.id.xxsj_rl) RelativeLayout xxsjRl;
    @BindView(R.id.wddy_rl) RelativeLayout wddyRl;@BindView(R.id.xxjl_rl) RelativeLayout xxjlRl;
    @BindView(R.id.zhye_tv) TextView zhyeTv;@BindView(R.id.wdzh_rl) RelativeLayout wdzhRl;
    @BindView(R.id.wdxz_rl) RelativeLayout wdxzRl;@BindView(R.id.zhaq_rl) RelativeLayout zhaqRl;
    @BindView(R.id.rwxq_ll) LinearLayout rwxqLl;@BindView(R.id.xz_start_tv) TextView xzStartTv;
    @BindView(R.id.xz_end_tv) TextView xzEndTv;@BindView(R.id.end_LL) LinearLayout endLl;
    Unbinder unbinder;
    private UserInfoBean userInfoBean;
    private Intent intent;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, inflate);
        titleMessageIv.setVisibility(View.VISIBLE);
        titleSettingIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("我的");
        if (SpUtils.getString(mContext,"userFace","") != null){
            Glide.with(mActivity).load(SpUtils.getString(mContext,"userFace","")).into(mineFaceIv);
        }
        titleMessageIv.setOnClickListener(this);
        titleSettingIv.setOnClickListener(this);
        mineFaceIv.setOnClickListener(this);
        qiandaoBtn.setOnClickListener(this);
        xxsjRl.setOnClickListener(this);
        wddyRl.setOnClickListener(this);
        xxjlRl.setOnClickListener(this);
        wdzhRl.setOnClickListener(this);
        wdxzRl.setOnClickListener(this);
        zhaqRl.setOnClickListener(this);
        rwxqLl.setOnClickListener(this);
        return inflate;
    }

    @Override
    protected void initData() {
        requestUserInfo();
    }

    private void requestUserInfo() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        OkGo.<UserInfoBean>get(MyContants.userInfo)
                .tag(this)
                .headers(headers)
                .execute(new DialogCallback<UserInfoBean>(mActivity,UserInfoBean.class) {
                    @Override
                    public void onSuccess(Response<UserInfoBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            userInfoBean = response.body();
                            mineNameTv.setText(userInfoBean.getUser_name());
                            if (SpUtils.getString(mContext,"userFace","") != null){
                                if (!TextUtils.equals(SpUtils.getString(mContext,"userFace",""),userInfoBean.getUser_avatar())){
                                    SpUtils.putString(mContext,"userFace",userInfoBean.getUser_avatar());
                                    Glide.with(mActivity).load(userInfoBean.getUser_avatar()).into(mineFaceIv);
                                }
                            }
                            int user_level = userInfoBean.getUser_level();
                            int user_integral = userInfoBean.getUser_integral();
                            getLevel(user_level,user_integral);
                            zhyeTv.setText(userInfoBean.getUser_android_balance()+"元");
                        }
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void getLevel(int userLevel, int integral) {
        switch (userLevel){
            case 0:
                progressBar.setMax(200);
                progressBar.setProgress(integral);
                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v0));
                xzStartTv.setText("默认");
                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v1));
                xzEndTv.setText("铜勋章");
                break;
            case 1:
                progressBar.setMin(200);
                progressBar.setMax(600);
                progressBar.setProgress(integral);
                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v1));
                xzStartTv.setText("铜勋章");
                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v2));
                xzEndTv.setText("银勋章");
                break;
            case 2:
                progressBar.setMin(600);
                progressBar.setMax(1500);
                progressBar.setProgress(integral);
                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v2));
                xzStartTv.setText("银勋章");
                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v3));
                xzEndTv.setText("金勋章");
                break;
            case 3:
                progressBar.setMin(1500);
                progressBar.setMax(3000);
                progressBar.setProgress(integral);
                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v3));
                xzStartTv.setText("金勋章");
                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v4));
                xzEndTv.setText("白金勋章");
                break;
            case 4:
                progressBar.setMin(3000);
                progressBar.setMax(5000);
                progressBar.setProgress(integral);
                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v4));
                xzStartTv.setText("白金勋章");
                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v5));
                xzEndTv.setText("钻石勋章");
                break;
            case 5:
                progressBar.setVisibility(View.GONE);
                endLl.setVisibility(View.GONE);
                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v5));
                xzStartTv.setText("钻石勋章");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_message_iv://消息
                intent = new Intent(getActivity(), MyMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.title_setting_iv://设置
                startActivity(new Intent(getActivity(),SettingActivity.class));
                break;
            case R.id.mine_face_iv://个人信息
                intent = new Intent(getActivity(),PersonInfomationActivity.class);
                intent.putExtra("url",userInfoBean.getUser_avatar());
                intent.putExtra("name",userInfoBean.getUser_name());
                intent.putExtra("sex",userInfoBean.getUser_sex()+"");
                intent.putExtra("brithday",userInfoBean.getUser_birthday());
                intent.putExtra("education",userInfoBean.getUser_education()+"");
                intent.putExtra("industry",userInfoBean.getUser_industry()+"");
                startActivity(intent);
                break;
            case R.id.rwxq_ll://任务详情
                startActivity(new Intent(getActivity(),TaskDetailActivity.class));
                break;
            case R.id.xxsj_rl://学习时间
                startActivity(new Intent(getActivity(), LearningTimeActivity.class));
                break;
            case R.id.wddy_rl://我的订阅
                startActivity(new Intent(getActivity(),MySubscriptionsActivity.class));
                break;
            case R.id.xxjl_rl://学习记录
                startActivity(new Intent(getActivity(),LearningRecordActivity.class));
                break;
            case R.id.wdzh_rl://我的账户
                startActivity(new Intent(getActivity(),MyAccountActivity.class));
                break;
            case R.id.wdxz_rl://我的勋章
                startActivity(new Intent(getActivity(),MyMedalActivity.class));
                break;
            case R.id.zhaq_rl://帐号安全
                startActivity(new Intent(getActivity(), AccountSafeActivity.class));
                break;
            case R.id.qiandao_btn://签到
                qiandaoBtn.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_qiandao_hui));
                qiandaoBtn.setText("已签到");
                qiandaoBtn.setClickable(false);
                TUtils.showShort(getActivity(),"签到成功");
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
