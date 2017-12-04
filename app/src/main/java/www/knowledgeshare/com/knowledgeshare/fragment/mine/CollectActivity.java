package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.adapter.BuyTabAdapter;
import www.knowledgeshare.com.knowledgeshare.view.NoScrollViewPager;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class CollectActivity extends BaseActivity {
    private List<Fragment> fragmentList;
    private List<String> tab_list;
    private ImageView title_message_iv;
    private ImageView title_back_iv;
    private TextView title_content_tv;
    private ImageView title_setting_iv;
    private TextView title_content_right_tv;
    private TabLayout collect_tablayout;
    private NoScrollViewPager collect_viewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollect);
        initView();
    }

    private void initView() {
        title_message_iv = (ImageView) findViewById(R.id.title_message_iv);
        title_back_iv = (ImageView) findViewById(R.id.title_back_iv);
        title_content_tv = (TextView) findViewById(R.id.title_content_tv);
        title_setting_iv = (ImageView) findViewById(R.id.title_setting_iv);
        title_content_right_tv = (TextView) findViewById(R.id.title_content_right_tv);
        collect_tablayout = (TabLayout) findViewById(R.id.collect_tablayout);
        collect_viewpager = (NoScrollViewPager) findViewById(R.id.collect_viewpager);

        title_back_iv.setVisibility(View.VISIBLE);
        title_content_tv .setText("收藏");
      title_back_iv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              finish();
          }
      });
        initData();
    }

    private void initData() {

       MyClassfragment myclassfragment=new MyClassfragment();
        Myjinjufragment myjinjufragment=new Myjinjufragment();
        fragmentList=new ArrayList<>();
        fragmentList.add(myclassfragment);
        fragmentList.add(myjinjufragment);
        tab_list = new ArrayList<>();
        tab_list.add("课程");
        tab_list.add("金句");
        collect_tablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(collect_tablayout, 10, 10);
            }
        });
        collect_tablayout.setTabMode(TabLayout.MODE_FIXED);
        collect_tablayout.addTab(collect_tablayout.newTab().setText(tab_list.get(0)));
        collect_tablayout.addTab(collect_tablayout.newTab().setText(tab_list.get(1)));
        //设置中间竖线
        LinearLayout linearLayout = (LinearLayout) collect_tablayout.getChildAt(0);
        linearLayout.setDividerPadding(20);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));

        BuyTabAdapter adapter = new BuyTabAdapter(getSupportFragmentManager(),fragmentList,tab_list);
        collect_viewpager.setAdapter(adapter);
        collect_tablayout.setupWithViewPager( collect_viewpager);
    }
    //动态设置指示器下划线长度
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

}
