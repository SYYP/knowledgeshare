package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

public class SoftMusicDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_beijing;
    private TextView tv_download;
    private TextView tv_search;
    private TextView tv_share;
    private TextView tv_guanzhu;
    private TextView tv_dianzan_count;
    private TextView tv_teacher_intro;
    private RecyclerView recycler_free;
    private TextView tv_shiyirenqun;
    private TextView tv_readxuzhi;
    private TextView tv_writeliuyan;
    private RecyclerView recycler_liuyan;
    private LinearLayout activity_free;
    private TextView tv_tryread;
    private TextView tv_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_music_detail);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(this);
        tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);
        tv_guanzhu.setOnClickListener(this);
        tv_dianzan_count = (TextView) findViewById(R.id.tv_dianzan_count);
        tv_dianzan_count.setOnClickListener(this);
        tv_teacher_intro = (TextView) findViewById(R.id.tv_teacher_intro);
        recycler_free = (RecyclerView) findViewById(R.id.recycler_free);
        tv_shiyirenqun = (TextView) findViewById(R.id.tv_shiyirenqun);
        tv_readxuzhi = (TextView) findViewById(R.id.tv_readxuzhi);
        tv_writeliuyan = (TextView) findViewById(R.id.tv_writeliuyan);
        tv_writeliuyan.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        activity_free = (LinearLayout) findViewById(R.id.activity_free);
        tv_tryread = (TextView) findViewById(R.id.tv_tryread);
        tv_tryread.setOnClickListener(this);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(this);
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        recycler_free.setLayoutManager(new LinearLayoutManager(this));
        recycler_free.setNestedScrollingEnabled(false);
        LieBiaoAdapter lieBiaoAdapter = new LieBiaoAdapter(R.layout.item_free, list);
        recycler_free.setAdapter(lieBiaoAdapter);
        lieBiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        LiuYanAdapter liuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan, list);
        recycler_liuyan.setAdapter(liuYanAdapter);
    }

    private class LieBiaoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LieBiaoAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    private class LiuYanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LiuYanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_download:
                break;
            case R.id.tv_search:
                break;
            case R.id.tv_share:
                break;
            case R.id.tv_guanzhu:
                break;
            case R.id.tv_dianzan_count:
                break;
            case R.id.tv_writeliuyan:
                break;
            case R.id.tv_tryread:
                break;
        }
    }
}
