package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

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
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.bean.UserInfoBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;



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
    @BindView(R.id.linearlayout) LinearLayout linearLayout;
    Unbinder unbinder;
    private UserInfoBean userInfoBean;
    private Intent intent;
    private int user_level;
    private int user_integral;
    private int user_education;
    private List<UserInfoBean.LevelBean> level;
    private int apnType;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, inflate);
        EventBus.getDefault().register(this);
        apnType = NetWorkUtils.getAPNType(mContext);
        if (apnType == 0) {
            Toast.makeText(mContext, "没有网络呢~", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.GONE);
        } else{
            linearLayout.setVisibility(View.VISIBLE);
            titleMessageIv.setVisibility(View.VISIBLE);
            titleSettingIv.setVisibility(View.VISIBLE);
            titleContentTv.setText("我的");
            if (SpUtils.getString(mContext,"userFace","") != null && !SpUtils.getString(mContext,"userFace","").equals("")){
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
        }
        return inflate;
    }

    @Override
    protected void initData() {
        requestUserInfo();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        apnType = NetWorkUtils.getAPNType(mContext);
        if (apnType == 0) {
            Toast.makeText(mContext, "没有网络呢~", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.GONE);
        } else{
            linearLayout.setVisibility(View.VISIBLE);
            requestUserInfo1();
        }
    }

    private void requestUserInfo1() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        OkGo.<UserInfoBean>get(MyContants.userInfo)
                .tag(this)
                .headers(headers)
                .execute(new JsonCallback<UserInfoBean>(UserInfoBean.class) {
                    @Override
                    public void onSuccess(Response<UserInfoBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            userInfoBean = response.body();
                            user_level = userInfoBean.getUser_level();
                            user_integral = userInfoBean.getUser_integral();
                            getLevel(user_level, user_integral);
                            zhyeTv.setText(userInfoBean.getUser_android_balance()+"");
//                            zhyeTv.setText(userInfoBean.getUser_android_balance()+"元");
                        }
                    }
                });
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
                            if (userInfoBean.getUser_name() != null && !userInfoBean.getUser_name().equals("")){
                                mineNameTv.setText(userInfoBean.getUser_name());
                                SpUtils.putString(mContext,"name",userInfoBean.getUser_name());
                            }else if (userInfoBean.getWx_name() != null && !userInfoBean.getWx_name().equals("")){
                                mineNameTv.setText(userInfoBean.getWx_name());
                                SpUtils.putString(mContext,"name",userInfoBean.getWx_name());
                            }else {
                                String user_mobile = userInfoBean.getUser_mobile();
                                String ss = user_mobile.substring(0,3)+"****"+user_mobile.substring(7,11);
                                mineNameTv.setText(ss);
                                SpUtils.putString(mContext,"name",ss);
                            }
                            if ( !TextUtils.isEmpty(userInfoBean.getUser_avatar()) && !userInfoBean.getUser_avatar().equals("") ){
                                Glide.with(mActivity).load(userInfoBean.getUser_avatar()).into(mineFaceIv);
                                SpUtils.putString(mContext,"userFace",userInfoBean.getUser_avatar());
                            }
                            level = response.body().getLevel();
                            if (userInfoBean.isIs_sign()){
                                qiandaoBtn.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_qiandao_hui));
                                qiandaoBtn.setText("已签到");
                                qiandaoBtn.setClickable(false);
                            }
                            SpUtils.putString(mContext,"setphone",userInfoBean.getConfig().get(0).getValue());
                            SpUtils.putString(mContext,"setemail",userInfoBean.getConfig().get(1).getValue());
                            SpUtils.putString(mContext,"mobile",userInfoBean.getUser_mobile());
                            SpUtils.putString(mContext,"wx_id",userInfoBean.getWx_unionid());
                            user_education = userInfoBean.getUser_education();
                            user_level = userInfoBean.getUser_level();
                            user_integral = userInfoBean.getUser_integral();
                            getLevel(user_level, user_integral);
                            zhyeTv.setText(userInfoBean.getUser_android_balance()+"元");
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("userinfo")) {
            requestUserInfo();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void getLevel(int userLevel, int integral) {
        switch (userLevel){
            case 0:
                progressBar.setMax(200);
                progressBar.setProgress(integral);
                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v0));
                xzStartTv.setText("默认");
                Glide.with(mContext).load(level.get(0).getLevel_get_img()).into(xzStartIv);
//                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v1));
                xzEndTv.setText("铜勋章");
                break;
            case 1:
                progressBar.setMax(600-200);
                progressBar.setProgress(integral-200);
                Glide.with(mContext).load(level.get(0).getLevel_get_img()).into(xzStartIv);
//                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v1));
                xzStartTv.setText("铜勋章");
                Glide.with(mContext).load(level.get(1).getLevel_get_img()).into(xzStartIv);
//                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v2));
                xzEndTv.setText("银勋章");
                break;
            case 2:
                progressBar.setMax(1500-600);
                progressBar.setProgress(integral-600);
                Glide.with(mContext).load(level.get(1).getLevel_get_img()).into(xzStartIv);
//                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v2));
                xzStartTv.setText("银勋章");
                Glide.with(mContext).load(level.get(2).getLevel_get_img()).into(xzStartIv);
//                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v3));
                xzEndTv.setText("金勋章");
                break;
            case 3:
                progressBar.setMax(3000-1500);
                progressBar.setProgress(integral-1500);
                Glide.with(mContext).load(level.get(2).getLevel_get_img()).into(xzStartIv);
//                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v3));
                xzStartTv.setText("金勋章");
                Glide.with(mContext).load(level.get(3).getLevel_get_img()).into(xzStartIv);
//                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v4));
                xzEndTv.setText("白金勋章");
                break;
            case 4:
                progressBar.setMax(5000-3000);
                progressBar.setProgress(integral-3000);
                Glide.with(mContext).load(level.get(3).getLevel_get_img()).into(xzStartIv);
//                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v4));
                xzStartTv.setText("白金勋章");
                Glide.with(mContext).load(level.get(4).getLevel_get_img()).into(xzStartIv);
//                xzEndIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v5));
                xzEndTv.setText("钻石勋章");
                break;
            case 5:
                progressBar.setVisibility(View.GONE);
                endLl.setVisibility(View.GONE);
                Glide.with(mContext).load(level.get(4).getLevel_get_img()).into(xzStartIv);
//                xzStartIv.setImageDrawable(getResources().getDrawable(R.drawable.power_xz_v5));
                xzStartTv.setText("钻石勋章");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        apnType = NetWorkUtils.getAPNType(mContext);
        if (apnType == 0) {
            Toast.makeText(mContext, "没有网络呢~", Toast.LENGTH_SHORT).show();
        } else{
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
                    intent.putExtra("url",SpUtils.getString(mContext,"userFace",""));
                    intent.putExtra("name",SpUtils.getString(mContext,"name",""));
                    intent.putExtra("sex",userInfoBean.getUser_sex()+"");
                    intent.putExtra("brithday",userInfoBean.getUser_birthday());
                    intent.putExtra("education",userInfoBean.getUser_education()+"");
                    intent.putExtra("industry",userInfoBean.getUser_industry()+"");
                    startActivity(intent);
                    break;
                case R.id.rwxq_ll://任务详情
                    Intent intent1 = new Intent(getActivity(),TaskDetailActivity.class);
                    intent1.putExtra("jifen",user_integral+"");
                    startActivity(intent1);
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
                    Intent intent = new Intent(getActivity(),MyMedalActivity.class);
                    intent.putExtra("level",user_level+"");
                    intent.putExtra("xunzhang", (Serializable) level);
                    startActivity(intent);
                    break;
                case R.id.zhaq_rl://帐号安全
                    startActivity(new Intent(getActivity(), AccountSafeActivity.class));
                    break;
                case R.id.qiandao_btn://签到
                    requestSignIn();
                default:
                    break;
            }
        }
    }

    private void requestSignIn() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(getActivity(), "token", ""));

        OkGo.<BaseBean>post(MyContants.signIn)
                .tag(this)
                .headers(headers)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            qiandaoBtn.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_qiandao_hui));
                            qiandaoBtn.setText("已签到");
                            qiandaoBtn.setClickable(false);
                            TUtils.showShort(getActivity(),"签到成功");
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

}
