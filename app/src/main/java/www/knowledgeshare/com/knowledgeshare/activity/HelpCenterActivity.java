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
import com.google.android.gms.auth.firstparty.shared.FACLConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.HeleCenterBean;


/**
 * 帮助中心
 */
public class HelpCenterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.recycler_bzzx) RecyclerView recyclerBzzx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("帮助中心");
        titleBackIv.setOnClickListener(this);
        initData();
    }

    private void initData() {
        recyclerBzzx.setLayoutManager(new LinearLayoutManager(this));
        recyclerBzzx.setNestedScrollingEnabled(false);
        List<HeleCenterBean> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            HeleCenterBean heleCenterBean = new HeleCenterBean();
            heleCenterBean.setTitle("如何使用app？");
            list.add(heleCenterBean);
        }
        HelpCenterAdapter adapter = new HelpCenterAdapter(R.layout.item_help_center,list);
        recyclerBzzx.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(HelpCenterActivity.this,HelpCenterDetailActivity.class));
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

    private class HelpCenterAdapter extends BaseQuickAdapter<HeleCenterBean,BaseViewHolder>{

        public HelpCenterAdapter(@LayoutRes int layoutResId, @Nullable List<HeleCenterBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HeleCenterBean item) {
            TextView titleTv = helper.getView(R.id.title_tv);


        }
    }
}
