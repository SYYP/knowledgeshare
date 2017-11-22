package www.knowledgeshare.com.knowledgeshare.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;


/**
 * Created by lxk on 2017/7/3.
 */

public class SendSmsTimerUtils extends CountDownTimer {//验证码倒计时的工具类
    /*
    * 当点击后或出现倒计时的时候再点击是触发不了点击事件的。
      等倒计时结束显示重新获取验证码的时候可以重新触发点击事件；
    * */
    private int inFuture;
    private int downInterval;
    private TextView mTextView;

    //发送验证码，开始倒计时
    public static void sendSms(TextView view, int normalColor, int afterColor) {
        SendSmsTimerUtils mCountDownTimerUtils = new SendSmsTimerUtils(view, 60000, 1000, normalColor, afterColor);
        mCountDownTimerUtils.start();
    }
    /**
     * 第一个参数：TextView控件(需要实现倒计时的TextView)
     * 第二个参数：倒计时总时间，以毫秒为单位；
     * 第三个参数：渐变事件，最低1秒，也就是说设置0-1000都是以一秒渐变，设置1000以上改变渐变时间
     * 第四个参数：点击textview之前的字体颜色
     * 第五个参数：点击textview之后的字体颜色
     */
    public SendSmsTimerUtils(TextView textView, long millisInFuture, long countDownInterval,
                             int inFuture, int downInterval) {
        /*
        注意这个，super的构造器中millisInFuture是总时间，countDownInterval是间隔时间
        意思就是每隔countDownInterval时间会回调一次方法onTick，然后millisInFuture时间之后会回调onFinish方法。
         */
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        this.inFuture = inFuture;
        this.downInterval = downInterval;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);
        mTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        mTextView.setTextColor(MyApplication.getGloableContext().getResources().getColor(downInterval));

        SpannableString spannableString = new SpannableString(mTextView.getText().toString());
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        //设置秒数为红色
        if (millisUntilFinished / 1000 > 9) {
            spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取验证码");
        mTextView.setClickable(true);
        mTextView.setTextColor(MyApplication.getGloableContext().getResources().getColor(R.color.redd));
    }
}
