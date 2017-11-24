package www.knowledgeshare.com.knowledgeshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

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
                        startActivity(new Intent(LancherActivity.this, LoginActivity.class));
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        UltimateBar ultimateBar = new UltimateBar(this);
//        ultimateBar.setImmersionBar();
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
                    startActivity(new Intent(LancherActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(LancherActivity.this, GuidePageActivity.class));
                }
                finish();
            }
        }, 1500);
    }
}
