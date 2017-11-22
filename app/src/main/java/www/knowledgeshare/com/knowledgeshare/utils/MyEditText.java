package www.knowledgeshare.com.knowledgeshare.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends EditText{

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {

           switch (id){


           }
        return false;
    }
}