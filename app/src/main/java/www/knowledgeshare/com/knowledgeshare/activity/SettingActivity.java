package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.suke.widget.SwitchButton;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.utils.DataCleanManager;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

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
    @BindView(R.id.exit_btn) TextView exitBtn;
    @BindView(R.id.tv_cache_size) TextView tv_cache_size;
    @BindView(R.id.kfzx_call_rl) RelativeLayout kfzxCallRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("设置");
        boolean wifichecked = SpUtils.getBoolean(SettingActivity.this, "wifichecked", false);
        if (wifichecked){
            voiceSwitchBtn.setChecked(true);
        }else {
            voiceSwitchBtn.setChecked(false);
        }
        voiceSwitchBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO YOU job
                if (isChecked){
                    SpUtils.putBoolean(SettingActivity.this,"wifichecked",true);
                }else {
                    SpUtils.putBoolean(SettingActivity.this,"wifichecked",false);
                }
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
        kfzxCallRl.setOnClickListener(this);

        try {
            String data = DataCleanManager.getTotalCacheSize(SettingActivity.this);
            tv_cache_size.setText(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        kfzxCallTv.setText(SpUtils.getString(this,"setphone",""));
        youxiangTv.setText(SpUtils.getString(this,"setemail",""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.qxhc_rl://清除缓存
                showTips(1,"清除缓存","清除后，图片等多媒体消息需要重新下载查看，确定清除？","确定","取消",false);
                break;
            case R.id.rjsj_rl://软件升级
                showTips(2,"升级提示","最新版本v1.1.1，是否需要升级？","升级","取消",false);
//                showTips("升级提示","暂时没有最新版本","","",true);
                break;
            case R.id.yjfk_rl://意见反馈
                startActivity(new Intent(this,FeedBackActivity.class));
                break;
            case R.id.bzzx_rl://帮助中心
                startActivity(new Intent(this,HelpCenterActivity.class));
                break;
            case R.id.yhxy_rl://用户协议
                startActivity(new Intent(this,UserAgreementActivity.class));
                break;
            case R.id.gywm_rl://关于我们
                startActivity(new Intent(this,AboutUsActivity.class));
                break;
            case R.id.kfzx_call_rl://客服电话
                showTips(4,"提示","是否拨打客服电话？","是","否",false);
                break;
            case R.id.exit_btn://退出
                showTips(3,"提示","是否退出？","是","否",false);
                break;

        }
    }

    private void showTips(final int flag, String title, String content, String left, String right, boolean singleMode) {
        new NormalAlertDialog.Builder(this)
                .setTitleVisible(true).setTitleText(title)
                .setTitleTextColor(R.color.text_black)
                .setContentText(content)
                .setContentTextColor(R.color.text_black)
                .setLeftButtonText(left)
                .setLeftButtonTextColor(R.color.text_black)
                .setRightButtonText(right)
                .setRightButtonTextColor(R.color.text_black)
                .setSingleMode(singleMode)
                .setSingleButtonText("确定")
                .setSingleButtonTextColor(R.color.text_black)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        switch (flag){
                            case 1:
                                DataCleanManager.clearAllCache(SettingActivity.this);
                                tv_cache_size.setText("0M");
                                TUtils.showShort(SettingActivity.this,"缓存已清理");
                                break;
                            case 2:
                                break;
                            case 3:
                                requsetLogout();
                                TUtils.showShort(SettingActivity.this,"退出");
//                                SpUtils.putBoolean(SettingActivity.this, "abool", false);
                                SpUtils.putString(SettingActivity.this,"id","");
                                SpUtils.putBoolean(SettingActivity.this, "wengaowindow", false);
                                SpUtils.putString(SettingActivity.this,"token","");
                                SpUtils.putString(SettingActivity.this, "wx_id", "");
//                                removeAllActivitys();
                                finish();
                                DownloadManager.getInstance().getDownloading().clear();
                                EventBean eventBean = new EventBean("norotate");
                                EventBus.getDefault().postSticky(eventBean);
                                EventBean eventBean2 = new EventBean("home_close");
                                EventBus.getDefault().postSticky(eventBean2);
                                Intent intent1 = new Intent(SettingActivity.this, MainActivity.class);
                                intent1.putExtra("loginout","loginout");
                                startActivity(intent1);
                                break;
                            case 4:
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+kfzxCallTv.getText().toString()));
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {
                        switch (flag){
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    private void requsetLogout() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<BaseBean>get(MyContants.logout)
                .headers(headers)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {

                    }
                });
    }
}
