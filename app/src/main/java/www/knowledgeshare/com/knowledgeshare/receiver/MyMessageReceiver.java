package www.knowledgeshare.com.knowledgeshare.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanDetail2Activity;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.MyMessageActivity;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * Created by Administrator on 2018/1/4.
 */

public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    private BaseDialog mDialog;

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        //积分 type notice
        //type zl
        //type
    }


    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        String type = cPushMessage.getTitle();
        String content = cPushMessage.getContent();
        TUtils.showShort(MyApplication.getGloableContext(),type+":"+content);
//        if (type.equals("获得奖励")) {
            /*BaseDialog.Builder builder = new BaseDialog.Builder(context);
            mDialog = builder.setViewId(R.layout.dialog_notice)
                    //设置dialogpadding
                    .setPaddingdp(10, 0, 10, 0)
                    //设置显示位置
                    .setGravity(Gravity.CENTER)
                    //设置动画
                    .setAnimation(R.style.Alpah_aniamtion)
                    //设置dialog的宽高
                    .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    //设置触摸dialog外围是否关闭
                    .isOnTouchCanceled(true)
                    //设置监听事件
                    .builder();
            TextView tv_content = mDialog.getView(R.id.tv_content);
            tv_content.setText(content);
            mDialog.show();*/
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        try {
            JSONObject jsonObject=new JSONObject(extraMap);
            String id = jsonObject.optString("id");
            String type =jsonObject.optString("type");
            if (type.equals("zl")) {
                Intent intent = new Intent(context, ZhuanLanDetail2Activity.class);
                intent.putExtra("id", id);
                intent.putExtra("title", summary);
                intent.putExtra("type","alreadyBuy");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                if (SpUtils.getString(context,"id","") == null || SpUtils.getString(context,"id","").equals("")){
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, MyMessageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }
}
