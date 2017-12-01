package www.knowledgeshare.com.knowledgeshare.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

/*
  动态生成selector
 */
public class CommonUtils {
    /**
     * 用java代码的方式动态生成状态选择器
     */
    public static Drawable generatePressedSelector(Drawable pressed, Drawable normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);//  状态  , 设置按下的图片
        drawable.addState(new int[]{}, normal);//默认状态,默认状态下的图片

        //根据SDK版本设置状态选择器过度动画/渐变选择器/渐变动画
        if (Build.VERSION.SDK_INT > 10) {
            drawable.setEnterFadeDuration(500);
            drawable.setExitFadeDuration(500);
        }

        return drawable;
    }
}
