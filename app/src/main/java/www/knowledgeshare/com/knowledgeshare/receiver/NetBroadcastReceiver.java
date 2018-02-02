package www.knowledgeshare.com.knowledgeshare.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * Created by Administrator on 2018/2/2.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        SpUtils.putBoolean(MyApplication.getGloableContext(), "nowifiallowdown", false);
        SpUtils.putBoolean(MyApplication.getGloableContext(), "nowifiallowlisten", false);
        int apnType = NetWorkUtils.getAPNType(MyApplication.getGloableContext());
        if (apnType == 1) {
            boolean wifichecked = SpUtils.getBoolean(MyApplication.getGloableContext(), "wifichecked", false);
            if (wifichecked) {
                EventBus.getDefault().postSticky(new EventBean("wifidown"));
            }
        }
        if (apnType == 0) {
            EventBus.getDefault().postSticky(new EventBean("nonetwork"));
        }
        if (apnType == 2 || apnType == 3 || apnType == 4){
            EventBus.getDefault().postSticky(new EventBean("nonetwork1"));
        }
    }
}
