package www.knowledgeshare.com.knowledgeshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.GuidePageBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.ViewPagerIndicator;

/**
 * Created by Administrator on 2017/8/24.
 */

public class LancherActivity extends BaseActivity {

    private int time = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                time--;
                if (time == 0) {
                    boolean isguide = SpUtils.getBoolean(LancherActivity.this, "guide", false);
                    if (isguide) {
                        startActivity(new Intent(LancherActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(LancherActivity.this, GuidePageActivity.class));
                    }
                    finish();
                } else {
                    Message message = mHandler.obtainMessage(1);
                    mHandler.sendMessageDelayed(message, 1000);
                }
            }
        }
    };
    private List<GuidePageBean.GuideBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        UltimateBar ultimateBar = new UltimateBar(this);
//        ultimateBar.setImmersionBar();
        setISshow(false);
        setContentView(R.layout.activity_launcher);
//        String umpushid = SpUtils.getString(this, "UMPUSHID", "");
//        if (!TextUtils.isEmpty(umpushid)) {
//            ArrayMap arrayMap = new ArrayMap<String, String>();
//            arrayMap.put("user_id", SpUtils.getString(this, "user_id", ""));
//            arrayMap.put("token", MyUtils.getToken());
//            arrayMap.put("device_token", umpushid);
//            RetrofitManager.get(MyContants.BASEURL + "s=User/upush", arrayMap, new BaseObserver1<EasyBean>("") {
//                @Override
//                public void onSuccess(EasyBean result, String tag) {
//
//                    //                Toast.makeText(RegisterActivity.this, result.getSuccess(), Toast.LENGTH_SHORT).show();
//                    if (result.getCode() == 200) {
//
//                    } else {
//
//                    }
//                }
//
//                @Override
//                public void onFailed(int code) {
//                }
//            });
    //    }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isguide = SpUtils.getBoolean(LancherActivity.this, "guide", false);
                if (isguide) {
                    startActivity(new Intent(LancherActivity.this, MainActivity.class));
                } else {
//                    requestGuide();
                    Intent intent = new Intent(LancherActivity.this, GuidePageActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3500);
    }

    private void requestGuide() {
        HttpParams params = new HttpParams();
        params.put("type","true");
        params.put("from","android");

        OkGo.<GuidePageBean>get(MyContants.bootstrappers)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<GuidePageBean>(GuidePageBean.class) {
                    @Override
                    public void onSuccess(Response<GuidePageBean> response) {
                        GuidePageBean guidePageBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            list = guidePageBean.getGuide();
                            for (int i = 0; i < list.size(); i++) {
                                SpUtils.putString(LancherActivity.this,"yindao"+i,list.get(i).getImgurl());
                            }
                        }
                    }
                });
    }
}
