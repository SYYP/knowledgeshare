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
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.PurchaseHistoryBean;

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
    private List<PurchaseHistoryBean> list;

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
        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PurchaseHistoryBean purchaseHistoryBean = new PurchaseHistoryBean();
            purchaseHistoryBean.setBianhao("123456789"+i);
            purchaseHistoryBean.setDate("2017-12-5 10:21");
            purchaseHistoryBean.setName("《凌晨4点的北京》");
            purchaseHistoryBean.setMoney("￥198.00");
            list.add(purchaseHistoryBean);
        }
        PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter(R.layout.item_purchase_history,list);
        recyclerGmjl.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
        }
    }

    private class PurchaseHistoryAdapter extends BaseQuickAdapter<PurchaseHistoryBean,BaseViewHolder>{

        public PurchaseHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<PurchaseHistoryBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaseHistoryBean item) {
            TextView bianhaoTv = helper.getView(R.id.danhao_tv);
            TextView dateTv = helper.getView(R.id.date_tv);
            TextView nameTv = helper.getView(R.id.name_tv);
            TextView moneyTv = helper.getView(R.id.money_tv);

            bianhaoTv.setText(item.getBianhao());
            dateTv.setText(item.getDate());
            nameTv.setText(item.getName());
            moneyTv.setText(item.getMoney());
        }
    }
}
