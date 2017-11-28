package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.EasyLessonBean;

/**
 * 我的订阅
 */
public class MySubscriptionsActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.recycler_wddy) RecyclerView recyclerWddy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscriptions);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("我的订阅");
        titleBackIv.setOnClickListener(this);
        recyclerWddy.setLayoutManager(new LinearLayoutManager(this));
        recyclerWddy.setNestedScrollingEnabled(false);
        List<EasyLessonBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EasyLessonBean easyLessonBean = new EasyLessonBean();
            easyLessonBean.setTitle("崔宗顺的男低音歌唱家秘籍");
            easyLessonBean.setDesc("男低音，一个神秘又充满魅力的音部");
            easyLessonBean.setUpdata(i+"小时前更新");
            easyLessonBean.setZlmc("最新更新的专栏名称");
            easyLessonBean.setMoney("￥198/年");
            list.add(easyLessonBean);
        }
        MySubAdapter adapter = new MySubAdapter(R.layout.item_my_sub,list);
        recyclerWddy.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
        }
    }

    private class MySubAdapter extends BaseQuickAdapter<EasyLessonBean, BaseViewHolder>{

        public MySubAdapter(@LayoutRes int layoutResId, @Nullable List<EasyLessonBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, EasyLessonBean item) {
            TextView subTitleTv = helper.getView(R.id.sub_title_tv);
            TextView subDescTv = helper.getView(R.id.sub_desc_tv);
            TextView subUpdataTv = helper.getView(R.id.sub_updata_tv);
            TextView subZlmcTv = helper.getView(R.id.sub_zlmc_tv);
            TextView subMoneyTv = helper.getView(R.id.sub_money_tv);

            subTitleTv.setText(item.getTitle());
            subDescTv.setText(item.getDesc());
            subUpdataTv.setText(item.getUpdata());
            subZlmcTv.setText(item.getZlmc());
            subMoneyTv.setText(item.getMoney());
        }
    }
}
