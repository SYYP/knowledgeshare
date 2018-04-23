package www.knowledgeshare.com.knowledgeshare.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import www.knowledgeshare.com.knowledgeshare.bean.EventBean;

/**
 * Created by Administrator on 2018/4/23.
 */

public class MyReceiver extends BroadcastReceiver {

    private final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    private final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);

            if (reason == null)
                return;

            // Home键
            if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
//                Toast.makeText(MyApplication.getGloableContext(), "按了Home键", Toast.LENGTH_SHORT).show();
            }

            // 最近任务列表键
            if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
//                Toast.makeText(MyApplication.getGloableContext(), "按了最近任务列表", Toast.LENGTH_SHORT).show();
                //保存记忆播放位置
                EventBus.getDefault().postSticky(new EventBean("clickrenwulist"));
            }
        }
    }
}
