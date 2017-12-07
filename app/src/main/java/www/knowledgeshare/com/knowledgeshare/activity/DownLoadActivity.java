package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.res.Resources;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.adapter.BuyTabAdapter;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.AlreadyDownLoadFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.DownLoadingFragment;
import www.knowledgeshare.com.knowledgeshare.view.NoScrollViewPager;

public class DownLoadActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.download_tablayout)
    TabLayout downloadTablayout;
    @BindView(R.id.download_viewpager)
    NoScrollViewPager downloadViewpager;

    private List<Fragment> fragmentList;
    private List<String> tab_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("下载");
        titleBackIv.setOnClickListener(this);
        initData();
    }

    private void initData() {
        AlreadyDownLoadFragment alreadyDownLoadFragment = new AlreadyDownLoadFragment();
        DownLoadingFragment downLoadingFragment = new DownLoadingFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(alreadyDownLoadFragment);
        fragmentList.add(downLoadingFragment);

        tab_list = new ArrayList<>();
        tab_list.add("已下载");
        tab_list.add("下载中");

        downloadTablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(downloadTablayout, 10, 10);
            }
        });

        downloadTablayout.setTabMode(TabLayout.MODE_FIXED);
        downloadTablayout.addTab(downloadTablayout.newTab().setText(tab_list.get(0)));
        downloadTablayout.addTab(downloadTablayout.newTab().setText(tab_list.get(1)));

        //设置中间竖线
        LinearLayout linearLayout = (LinearLayout) downloadTablayout.getChildAt(0);
        linearLayout.setDividerPadding(20);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));

        BuyTabAdapter adapter = new BuyTabAdapter(getSupportFragmentManager(),fragmentList,tab_list);
        downloadViewpager.setAdapter(adapter);
        downloadTablayout.setupWithViewPager(downloadViewpager);

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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
        }
    }
}
