package www.knowledgeshare.com.knowledgeshare.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftKeyboardTool {
    /**
     * 为EditText设置弹出软键盘
     *
     * @param et
     */
    public static void showSoftKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //关闭软键盘
    public static void closeKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    //关闭软键盘
    public static void closeKeyboard(Activity context) {
        ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
