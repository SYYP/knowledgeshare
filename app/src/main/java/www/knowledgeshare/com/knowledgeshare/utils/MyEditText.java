package www.knowledgeshare.com.knowledgeshare.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class MyEditText extends TextView{

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {

        super.setCustomSelectionActionModeCallback(actionModeCallback);
    }
}