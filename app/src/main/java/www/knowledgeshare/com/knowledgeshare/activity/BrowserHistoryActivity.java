package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
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
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.BrowserHistoryBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;


/**
 * 浏览历史
 */
public class BrowserHistoryActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.recycler_bh)
    RecyclerView recyclerBh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_history);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleBackIv.setOnClickListener(this);
        titleContentTv.setText("浏览历史");
        initData();
    }

    private void initData() {
        recyclerBh.setLayoutManager(new LinearLayoutManager(this));
        recyclerBh.setNestedScrollingEnabled(false);

        List<BrowserHistoryBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BrowserHistoryBean browserHistoryBean = new BrowserHistoryBean();
            browserHistoryBean.setTitle("如何成为一名合格的歌者");
            browserHistoryBean.setName("刘鹏飞");
            browserHistoryBean.setContent("中国声乐好声音王牌级人物");
            browserHistoryBean.setMoney("￥19"+i+"元/年");
            list.add(browserHistoryBean);
        }
        BrowserHistoryAdapter adapter = new BrowserHistoryAdapter(R.layout.item_browser_history,list);
        recyclerBh.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(BrowserHistoryActivity.this, SoftMusicDetailActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
        }
    }

    private class BrowserHistoryAdapter extends BaseQuickAdapter<BrowserHistoryBean,BaseViewHolder>{

        public BrowserHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<BrowserHistoryBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BrowserHistoryBean item) {
            TextView itemTitleTv = helper.getView(R.id.item_title_tv);
            TextView itemNameTv = helper.getView(R.id.item_name_tv);
            TextView itemContentTv = helper.getView(R.id.item_content_tv);
            TextView itemMoneyTv = helper.getView(R.id.item_money_tv);

            itemTitleTv.setText(item.getTitle());
            itemNameTv.setText(item.getName());
            itemContentTv.setText(item.getContent());
            itemMoneyTv.setText(item.getMoney());
        }
    }
}
