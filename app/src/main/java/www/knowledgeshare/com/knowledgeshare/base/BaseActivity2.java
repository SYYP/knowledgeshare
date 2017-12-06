package www.knowledgeshare.com.knowledgeshare.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import www.knowledgeshare.com.knowledgeshare.R;


/**
 * Created by lxk on 2017/6/10.
 */

public class BaseActivity2 extends AppCompatActivity {
//    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里
    private FrameLayout parentFrameLayout;//把父类activity和子类activity的view都add到这里

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_demo);
    }

    /**
     * 初始化contentview
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentFrameLayout = new FrameLayout(this);
        viewGroup.addView(parentFrameLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentFrameLayout, true);
//        parentLinearLayout = new LinearLayout(this);
//        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
//        viewGroup.addView(parentLinearLayout);
//        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentFrameLayout, true);
//        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
    }

    @Override
    public void setContentView(View view) {
        parentFrameLayout.addView(view);
//        parentLinearLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentFrameLayout.addView(view, params);
//        parentLinearLayout.addView(view, params);
    }

}
