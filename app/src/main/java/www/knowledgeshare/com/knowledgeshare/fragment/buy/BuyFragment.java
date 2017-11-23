package www.knowledgeshare.com.knowledgeshare.fragment.buy;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.adapter.BuyTabAdapter;
import www.knowledgeshare.com.knowledgeshare.view.NoScrollViewPager;

/**
 * Created by Administrator on 2017/11/17.
 */

public class BuyFragment extends BaseFragment {
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.tv_download_number)
    TextView tvDownloadNumber;
    @BindView(R.id.ll_download)
    LinearLayout llDownload;
    @BindView(R.id.buy_tablayout)
    TabLayout buyTablayout;
    @BindView(R.id.buy_viewpager)
    NoScrollViewPager buyViewpager;
    Unbinder unbinder;

    private List<Fragment> fragmentList;
    private List<String> tab_list;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_buy, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    protected void initData() {
        //初始化各fragment
        EasyMusicLessonFragment easyMusicLessonFragment = new EasyMusicLessonFragment();
        MaestroClassFragment maestroClassFragment = new MaestroClassFragment();
        //将fragment装进列表中
        fragmentList = new ArrayList<>();
        fragmentList.add(easyMusicLessonFragment);
        fragmentList.add(maestroClassFragment);

        tab_list = new ArrayList<>();
        tab_list.add("轻松音乐课");
        tab_list.add("音乐大师班");

        buyTablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(buyTablayout, 10, 10);
            }
        });

        buyTablayout.setTabMode(TabLayout.MODE_FIXED);
        buyTablayout.addTab(buyTablayout.newTab().setText(tab_list.get(0)));
        buyTablayout.addTab(buyTablayout.newTab().setText(tab_list.get(1)));

        //设置中间竖线
        LinearLayout linearLayout = (LinearLayout) buyTablayout.getChildAt(0);
        linearLayout.setDividerPadding(30);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.layout_divider_vertical));

        BuyTabAdapter adapter = new BuyTabAdapter(getActivity().getSupportFragmentManager(),fragmentList,tab_list);
        buyViewpager.setAdapter(adapter);
        buyTablayout.setupWithViewPager(buyViewpager);

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
