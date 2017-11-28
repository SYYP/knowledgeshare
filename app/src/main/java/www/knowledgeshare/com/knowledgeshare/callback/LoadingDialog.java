package www.knowledgeshare.com.knowledgeshare.callback;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import www.knowledgeshare.com.knowledgeshare.R;

/**
 * Created by Administrator on 2017/10/11.
 */

public class LoadingDialog extends Dialog {

    private ProgressBar myProgress;
    private TextView tipTextView;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);

        myProgress = (ProgressBar) findViewById(R.id.myProgress);
        tipTextView = (TextView) findViewById(R.id.tipTextView);

    }

    @Override
    public void show() {
        Window mwindow=this.getWindow();
        WindowManager.LayoutParams lp=mwindow.getAttributes();
        lp.dimAmount = 0.5f;
        mwindow.setAttributes(lp);
        mwindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }
}
