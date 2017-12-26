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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.view.CustomPopupWindow;


/**
 * Created by lxk on 2017/6/10.
 */

public class BaseActivity extends AppCompatActivity {
    private static List<Activity> activityList = new ArrayList<>();
    private boolean isshow = true;//默认所有界面都显示
    protected CustomPopupWindow musicPop;
    private MediaService.MyBinder mMyBinder;
    //“绑定”服务的intent
    private Intent MediaServiceIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        //在这里判断是否token是否存在、是否过期之类的
        if (activityList != null)
            activityList.add(this);
        if (musicPop == null && isshow) {
            musicPop = new CustomPopupWindow(this);
            initMusic();
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
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void ClickPopShow() {
        if (isshow && !musicPop.isShowing()) {
            musicPop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
            mMyBinder.playMusic();
            EventBean eventBean = new EventBean("rotate");
            EventBus.getDefault().postSticky(eventBean);
            EventBean eventBean2 = new EventBean("bofang");
            EventBus.getDefault().postSticky(eventBean2);
        }
    }

    public void ClickPauseMusic() {
        mMyBinder.pauseMusic();
        EventBean eventBean = new EventBean("norotate");
        EventBus.getDefault().postSticky(eventBean);
        EventBean eventBean2 = new EventBean("pause");
        EventBus.getDefault().postSticky(eventBean2);
    }

    public void SlidePopShow() {
        if (isshow && !musicPop.isShowing() && mMyBinder.isPlaying()) {
            musicPop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
            EventBean eventBean = new EventBean("rotate");
            EventBus.getDefault().postSticky(eventBean);
            EventBean eventBean2 = new EventBean("bofang");
            EventBus.getDefault().postSticky(eventBean2);
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
        if (activityList != null)
            activityList.remove(this);
    }

    public static List<Activity> getAllActivitys() {
        return activityList;
    }

    public static void removeAllActivitys() {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i) != null) {
                    activityList.get(i).finish();
                }
            }
            activityList.clear();
            //            activityList = null;
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
