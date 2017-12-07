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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

public class SoftMusicActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RecyclerView recycler_yinyueke;
    private LinearLayout activity_gu_dian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_music);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        recycler_yinyueke = (RecyclerView) findViewById(R.id.recycler_yinyueke);
        activity_gu_dian = (LinearLayout) findViewById(R.id.activity_gu_dian);
        recycler_yinyueke.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        YinYueKeAdapter yinYueKeAdapter=new YinYueKeAdapter(R.layout.item_yinyueke2,list);
        recycler_yinyueke.setAdapter(yinYueKeAdapter);
        yinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(SoftMusicActivity.this,SoftMusicDetailActivity.class));
            }
        });
        recycler_yinyueke.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private class YinYueKeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public YinYueKeAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
//            Glide.with(mContext).load().into(imageView);
            helper.setVisible(R.id.iv_bofang,false);
            helper.setVisible(R.id.view_line,true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
