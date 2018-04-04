package www.knowledgeshare.com.knowledgeshare.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.RefreshToken;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.CustomPopupWindow;


/**
 * Created by lxk on 2017/6/10.
 */

public class BaseActivity extends AppCompatActivity {
    private static List<Activity> activityList = new ArrayList<>();
    private boolean isshow = true;//默认所有界面都显示
    public static CustomPopupWindow musicPop;
    public MediaService.MyBinder mMyBinder;
    //“绑定”服务的intent
    public Intent MediaServiceIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        //在这里判断是否token是否存在、是否过期之类的
        if (activityList != null) {
            activityList.add(this);
        }
        if (!NetWorkUtils.isNetworkConnected(this)) {
            Toast.makeText(this, "当前无网络连接，请检查设置", Toast.LENGTH_SHORT).show();
        }
        initMusic();
        refreshToken();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus)
            return;
        CustomPopupWindow.setPopContext(this);
        if (musicPop == null) {
            musicPop = new CustomPopupWindow(this);
        }
        SlidePopShow();
    }

    private void refreshToken() {
        String totalMs = SpUtils.getString(this, "totalMs", "");
        String token = SpUtils.getString(this, "token", "");
        if (!TextUtils.isEmpty(totalMs)) {
            long daoqitime = Long.parseLong(totalMs);
            if (daoqitime - System.currentTimeMillis() < 24 * 60 * 60 * 1000) {//提前一天刷新
                HttpParams params = new HttpParams();
                params.put("token", token);
                OkGo.<RefreshToken>post(MyContants.LXKURL + "tokens-refresh")
                        .tag(this)
                        .params(params)
                        .execute(new JsonCallback<RefreshToken>(RefreshToken.class) {
                            @Override
                            public void onSuccess(Response<RefreshToken> response) {
                                RefreshToken refreshToken = response.body();
                                if (response.code() >= 200 && response.code() <= 204) {
                                    String token1 = refreshToken.getToken();
                                    SpUtils.putString(BaseActivity.this, "token", token1);
                                    long ttlMs = refreshToken.getTtl() * 60 * 1000L;
                                    long timeMillis = System.currentTimeMillis();
                                    long totalMs = ttlMs + timeMillis;
                                    SpUtils.putString(BaseActivity.this, "totalMs", totalMs + "");
                                }
                            }
                        });
            }
        }
    }

    private void initMusic() {
        MediaServiceIntent = new Intent(this, MediaService.class);
        startService(MediaServiceIntent);
        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
            //            if (isshow && mMyBinder.isPlaying()) {
            //                musicPop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
            //            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void ClickPopShow() {//在子类页面中点击音频的时候弹出popupwindow
        if (musicPop == null) {
            return;
        }
        if (isshow) {
            musicPop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
        }
    }

    public void SlidePopShow() {
        if (musicPop == null) {
            return;
        }
        if (isshow && !mMyBinder.isClosed()) {
            musicPop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
        }
    }

    public void setPopHide() {
        if (musicPop == null) {
            return;
        }
        if (musicPop.isShowing()) {
            musicPop.dismiss();
        }
    }

    public void setISshow(boolean iSshow) {
        isshow = iSshow;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activityList != null) {
            activityList.remove(this);
        }
        unbindService(mServiceConnection);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setPopHide();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SlidePopShow();
    }

    public static void removeAllActivitys() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
        }
    }

    public static void realBack() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
            activityList = null;
        }
    }

}
