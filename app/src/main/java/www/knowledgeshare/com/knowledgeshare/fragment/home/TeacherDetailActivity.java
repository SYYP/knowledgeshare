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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

public class TeacherDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_bigphoto;
    private TextView tv_teacher_intro;
    private RecyclerView recycler_dashiban;
    private LinearLayout ll_dashiban;
    private RecyclerView recycler_yinyueke;
    private LinearLayout ll_yinyueke;
    private LinearLayout activity_gu_dian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_bigphoto = (ImageView) findViewById(R.id.iv_bigphoto);
        tv_teacher_intro = (TextView) findViewById(R.id.tv_teacher_intro);
        recycler_dashiban = (RecyclerView) findViewById(R.id.recycler_dashiban);
        ll_dashiban = (LinearLayout) findViewById(R.id.ll_dashiban);
        recycler_yinyueke = (RecyclerView) findViewById(R.id.recycler_yinyueke);
        ll_yinyueke = (LinearLayout) findViewById(R.id.ll_yinyueke);
        activity_gu_dian = (LinearLayout) findViewById(R.id.activity_gu_dian);
        recycler_dashiban.setLayoutManager(new LinearLayoutManager(this));
        recycler_dashiban.setNestedScrollingEnabled(false);
        recycler_yinyueke.setLayoutManager(new LinearLayoutManager(this));
        recycler_yinyueke.setNestedScrollingEnabled(false);
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        DaShiBanAdapter daShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban2, list);
        recycler_dashiban.setAdapter(daShiBanAdapter);
        daShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(TeacherDetailActivity.this,ZhuanLanActivity.class));
            }
        });
        YinYueKeAdapter yinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke2, list);
        recycler_yinyueke.setAdapter(yinYueKeAdapter);
        yinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(TeacherDetailActivity.this,SoftMusicDetailActivity.class));
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
            helper.setVisible(R.id.iv_bofang,false);
        }
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
