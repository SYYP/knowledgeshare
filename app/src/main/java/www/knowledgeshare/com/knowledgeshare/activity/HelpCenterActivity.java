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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.HeleCenterBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;


/**
 * 帮助中心
 */
public class HelpCenterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.recycler_bzzx) RecyclerView recyclerBzzx;
    private List<HeleCenterBean> list = new ArrayList<>();
    private HelpCenterAdapter adapter;
    private int id;
    private List<HeleCenterBean.DataBean> data = new ArrayList<>();

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
        requestHelp();
        /*for (int i = 0; i < 6; i++) {
            HeleCenterBean heleCenterBean = new HeleCenterBean();
            heleCenterBean.setTitle("如何使用app？");
            list.add(heleCenterBean);
        }*/


    }

    private void requestHelp() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));

        OkGo.<HeleCenterBean>get(MyContants.help)
                .tag(this)
                .headers(headers)
                .execute(new JsonCallback<HeleCenterBean>(HeleCenterBean.class) {
                    @Override
                    public void onSuccess(Response<HeleCenterBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            data = response.body().getData();
                            adapter = new HelpCenterAdapter(R.layout.item_help_center, data);
                            recyclerBzzx.setAdapter(adapter);
                            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(HelpCenterActivity.this,HelpCenterDetailActivity.class);
                                    intent.putExtra("h5_url",data.get(position).getH5_url());
                                    startActivity(intent);
                                }
                            });
                        }
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

    private class HelpCenterAdapter extends BaseQuickAdapter<HeleCenterBean.DataBean,BaseViewHolder>{

        public HelpCenterAdapter(@LayoutRes int layoutResId, @Nullable List<HeleCenterBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HeleCenterBean.DataBean item) {
            TextView titleTv = helper.getView(R.id.title_tv);
            titleTv.setText(item.getTitle());
            id = item.getId();
        }
    }
}
