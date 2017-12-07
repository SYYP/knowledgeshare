package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.ShoppingCartBean;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

public class QueryOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.recycler_order)
    RecyclerView recyclerOrder;
    @BindView(R.id.gopay_tv)
    TextView gopayTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("确认订单");
        titleBackIv.setOnClickListener(this);
        gopayTv.setOnClickListener(this);
        initData();
    }

    private void initData() {
        recyclerOrder.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrder.setNestedScrollingEnabled(false);
        List<ShoppingCartBean> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setTitle("崔宗顺的男低音歌唱家秘籍");
            shoppingCartBean.setContent("男低音，一个神秘而又充满魅力的声部男低音，一个神秘而又充满魅力的声部");
            shoppingCartBean.setMoney("19"+i);
            shoppingCartBean.setZhekou(i+"");
            list.add(shoppingCartBean);
        }
        QueryOrderAdapter adapter = new QueryOrderAdapter(R.layout.item_query_order,list);
        recyclerOrder.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.gopay_tv:
                TUtils.showShort(this,"去支付");
                break;
        }
    }

    private class QueryOrderAdapter extends BaseQuickAdapter<ShoppingCartBean,BaseViewHolder>{

        public QueryOrderAdapter(@LayoutRes int layoutResId, @Nullable List<ShoppingCartBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShoppingCartBean item) {
            TextView title = helper.getView(R.id.item_title_tv);
            TextView content = helper.getView(R.id.item_content_tv);
            TextView money = helper.getView(R.id.item_money_tv);
            TextView zhekou = helper.getView(R.id.item_zhekou_tv);
            TextView zhekouMoney = helper.getView(R.id.item_chekou_money_tv);
            TextView hejiTv = helper.getView(R.id.item_heji_tv);

            title.setText(item.getTitle());
            content.setText(item.getContent());
            money.setText("￥"+item.getMoney()+"/年");
        }
    }
}
