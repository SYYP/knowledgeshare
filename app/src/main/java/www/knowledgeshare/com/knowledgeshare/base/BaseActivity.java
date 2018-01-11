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
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
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
        if (musicPop == null) {
            musicPop = new CustomPopupWindow(this);
        }
        if (!NetWorkUtils.isNetworkConnected(this)){
            Toast.makeText(this, "当前无网络连接，请检查设置", Toast.LENGTH_SHORT).show();
        }
        initMusic();
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
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void ClickPopShow() {//在子类页面中点击音频的时候弹出popupwindow
        if (isshow && !musicPop.isShowing()) {
            musicPop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
        }
    }

    public void SlidePopShow() {
        if (isshow && !musicPop.isShowing() && mMyBinder.isPlaying()) {
            musicPop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
            //            EventBean eventBean = new EventBean("rotate");
            //            EventBus.getDefault().postSticky(eventBean);
            //            EventBean eventBean2 = new EventBean("home_bofang");
            //            EventBus.getDefault().postSticky(eventBean2);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (isshow && mMyBinder.isPlaying()) {
                musicPop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
            }
        }
    }

    public void setPopDismiss() {
        if (musicPop.isShowing()) {
            musicPop.dismiss();
            mMyBinder.closeMedia();
            isshow = false;
        }
    }

    public void setPopHide() {
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
        //        unbindService(mServiceConnection);
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
