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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.db.LookBean;
import www.knowledgeshare.com.knowledgeshare.db.LookUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.LikeDetailActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;


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
    private BrowserHistoryAdapter mAdapter;
    private List<LookBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_history);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleBackIv.setOnClickListener(this);
        titleContentTv.setText("浏览历史");
    }

    private void initData() {
        recyclerBh.setLayoutManager(new LinearLayoutManager(this));
        recyclerBh.setNestedScrollingEnabled(false);
        mList = LookUtils.search();
        if (mList != null && mList.size() > 0) {
            mAdapter = new BrowserHistoryAdapter(R.layout.item_browser_history, mList);
            recyclerBh.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String type = mList.get(position).getType();
                    if (type.equals("zhuanlan")) {
                        Intent intent = new Intent(BrowserHistoryActivity.this, ZhuanLanActivity.class);
                        intent.putExtra("id", mList.get(position).getId() + "");
                        startActivity(intent);
                    }else if (type.equals("xiaoke")) {
                        Intent intent = new Intent(BrowserHistoryActivity.this, SoftMusicDetailActivity.class);
                        intent.putExtra("id", mList.get(position).getId() + "");
                        startActivity(intent);
                    }else if (type.equals("like")) {
                        Intent intent = new Intent(BrowserHistoryActivity.this, LikeDetailActivity.class);
                        intent.putExtra("id", mList.get(position).getId() + "");
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
        }
    }

    private class BrowserHistoryAdapter extends BaseQuickAdapter<LookBean, BaseViewHolder> {

        public BrowserHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<LookBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LookBean item) {
            TextView itemTitleTv = helper.getView(R.id.item_title_tv);
            TextView itemNameTv = helper.getView(R.id.item_name_tv);
            TextView itemContentTv = helper.getView(R.id.item_content_tv);
            TextView itemMoneyTv = helper.getView(R.id.item_money_tv);
            if (item.getType().equals("zhuanlan")) {
                itemNameTv.setVisibility(View.GONE);
            } else {
                itemNameTv.setVisibility(View.VISIBLE);
            }
            itemTitleTv.setText(item.getTitle());
            itemNameTv.setText(item.getT_name());
            itemContentTv.setText(item.getT_tag());
            itemMoneyTv.setText(item.getPrice());
        }
    }
}
