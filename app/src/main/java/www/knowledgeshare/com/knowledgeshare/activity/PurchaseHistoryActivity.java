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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.PurchaseHistoryBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * 购买记录
 */
public class PurchaseHistoryActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.recycler_gmjl)
    RecyclerView recyclerGmjl;
    private List<PurchaseHistoryBean.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleBackIv.setOnClickListener(this);
        titleContentTv.setText("购买记录");
        recyclerGmjl.setLayoutManager(new LinearLayoutManager(this));
        recyclerGmjl.setNestedScrollingEnabled(false);
        requestBuyHistory();
        /*list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PurchaseHistoryBean purchaseHistoryBean = new PurchaseHistoryBean();
            purchaseHistoryBean.setBianhao("123456789"+i);
            purchaseHistoryBean.setDate("2017-12-5 10:21");
            purchaseHistoryBean.setName("《凌晨4点的北京》");
            purchaseHistoryBean.setMoney("￥198.00");
            list.add(purchaseHistoryBean);
        }*/

    }

    private void requestBuyHistory() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<PurchaseHistoryBean>post(MyContants.buyHistory)
                .tag(this)
                .headers(headers)
                .execute(new DialogCallback<PurchaseHistoryBean>(this,PurchaseHistoryBean.class) {
                    @Override
                    public void onSuccess(Response<PurchaseHistoryBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            list = response.body().getData();
                            PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter(R.layout.item_purchase_history,list);
                            recyclerGmjl.setAdapter(adapter);
                        }else {
                            TUtils.showShort(PurchaseHistoryActivity.this,response.body().getMessage());
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

    private class PurchaseHistoryAdapter extends BaseQuickAdapter<PurchaseHistoryBean.DataBean,BaseViewHolder>{

        public PurchaseHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<PurchaseHistoryBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaseHistoryBean.DataBean item) {
            TextView bianhaoTv = helper.getView(R.id.danhao_tv);
            TextView dateTv = helper.getView(R.id.date_tv);
            TextView nameTv = helper.getView(R.id.name_tv);
            TextView moneyTv = helper.getView(R.id.money_tv);

            bianhaoTv.setText(item.getOrder_sn());
            dateTv.setText(item.getPayment_time());
            nameTv.setText(item.getName());
            moneyTv.setText(item.getOrder_amount());

        }
    }
}
