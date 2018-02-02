package www.knowledgeshare.com.knowledgeshare.fragment.buy;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.DownLoadActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.adapter.BuyTabAdapter;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SearchActivity;
import www.knowledgeshare.com.knowledgeshare.view.NoScrollViewPager;

/**
 * Created by Administrator on 2017/11/17.
 */

public class BuyFragment extends BaseFragment implements View.OnClickListener {
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
    @BindView(R.id.iv_download_number)
    ImageView ivDownloadNumber;
    Unbinder unbinder;

    private List<Fragment> fragmentList;
    private List<String> tab_list;


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_buy, null);
        unbinder = ButterKnife.bind(this, inflate);
        EventBus.getDefault().register(this);
        return inflate;
    }

    @Override
    protected void initData() {
        tvSearch.setOnClickListener(this);
        llDownload.setOnClickListener(this);
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
        linearLayout.setDividerPadding(20);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.layout_divider_vertical));

        BuyTabAdapter adapter = new BuyTabAdapter(getActivity().getSupportFragmentManager(),fragmentList,tab_list);
        buyViewpager.setAdapter(adapter);
        buyTablayout.setupWithViewPager(buyViewpager);

        initNumber();
    }

    private void initNumber() {
         int TYPE_ING = 2;
        List<DownloadTask> taskList = OkDownload.restore(DownloadManager.getInstance().getDownloading());
        for (int i = 0; i < taskList.size(); i++) {
            DownloadTask task = taskList.get(i);
            String tag = TYPE_ING+ "_" + task.progress.tag;
            task.register(new ListDownloadListener(tag));
        }
        if (taskList.size() == 0){
            tvDownloadNumber.setText("");
            ivDownloadNumber.setVisibility(View.GONE);
        }else {
            ivDownloadNumber.setVisibility(View.VISIBLE);
            tvDownloadNumber.setText(taskList.size()+"");
            Logger.e("正在下载的数量："+ taskList.size());
        }
    }

    private class ListDownloadListener extends DownloadListener {

        public ListDownloadListener(Object tag) {
            super(tag);
        }

        @Override
        public void onStart(Progress progress) {
        }

        @Override
        public void onProgress(Progress progress) {

        }

        @Override
        public void onError(Progress progress) {
            Throwable throwable = progress.exception;
            if (throwable != null) throwable.printStackTrace();
        }

        @Override
        public void onFinish(File file, Progress progress) {
//            Toast.makeText(context, "下载完成:" + progress.filePath, Toast.LENGTH_SHORT).show();
            EventBean eventBean = new EventBean("number");
            EventBus.getDefault().postSticky(eventBean);
        }

        @Override
        public void onRemove(Progress progress) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("number")) {
            initNumber();
        }
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
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_search:
                startActivity(new Intent(getActivity(),SearchActivity.class));
                break;
            case R.id.ll_download:
                startActivity(new Intent(getActivity(),DownLoadActivity.class));
        }
    }
}
