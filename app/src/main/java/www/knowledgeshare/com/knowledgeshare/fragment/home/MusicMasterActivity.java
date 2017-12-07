package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

public class MusicMasterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RadioButton rb_gudian;
    private RadioButton rb_minzu;
    private RadioButton rb_liuxing;
    private RadioButton rb_suyang;
    private RadioGroup rgp;
    private RecyclerView recycler_dashiban;
    private LinearLayout activity_gu_dian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_master);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        rb_gudian = (RadioButton) findViewById(R.id.rb_gudian);
        rb_minzu = (RadioButton) findViewById(R.id.rb_minzu);
        rb_liuxing = (RadioButton) findViewById(R.id.rb_liuxing);
        rb_suyang = (RadioButton) findViewById(R.id.rb_suyang);
        rgp = (RadioGroup) findViewById(R.id.rgp);
        recycler_dashiban = (RecyclerView) findViewById(R.id.recycler_dashiban);
        activity_gu_dian = (LinearLayout) findViewById(R.id.activity_gu_dian);
        rgp.check(R.id.rb_gudian);
        recycler_dashiban.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        DaShiBanAdapter daShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban3, list);
        recycler_dashiban.setAdapter(daShiBanAdapter);
        daShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(MusicMasterActivity.this, ZhuanLanActivity.class));
            }
        });
        recycler_dashiban.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    setPopHide();
                } else if (dy < 0) {
                    SlidePopShow();
                }
            }
        });
    }


    private class DaShiBanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            //            Glide.with(mContext).load().into(imageView);
            helper.setVisible(R.id.iv_bofang, false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
