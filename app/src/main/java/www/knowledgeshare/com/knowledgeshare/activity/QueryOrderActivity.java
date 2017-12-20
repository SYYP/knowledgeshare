package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.QueryOrderBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.ShoppingCartBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
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
    private List<QueryOrderBean> list = new ArrayList<>();

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
        String ids = getIntent().getStringExtra("ids");
        recyclerOrder.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrder.setNestedScrollingEnabled(false);
        requestSubmitOrder(ids);
       /* for (int i = 0; i < 2; i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setTitle("崔宗顺的男低音歌唱家秘籍");
            shoppingCartBean.setContent("男低音，一个神秘而又充满魅力的声部男低音，一个神秘而又充满魅力的声部");
            shoppingCartBean.setMoney("19"+i);
            shoppingCartBean.setZhekou(i+"");
            list.add(shoppingCartBean);
        }*/

    }

    private void requestSubmitOrder(String ids) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        Logger.e(SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("ids",ids);
        Logger.e(ids);
        params.put("from","android");

        OkGo.<QueryOrderBean>post(MyContants.submitOrder)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<QueryOrderBean>(QueryOrderActivity.this,QueryOrderBean.class) {
                    @Override
                    public void onSuccess(Response<QueryOrderBean> response) {
                        int code = response.code();
                        QueryOrderBean queryOrderBean = response.body();
                        if (code >= 200 && code <= 204){
                            list.add(queryOrderBean);
                            QueryOrderAdapter adapter = new QueryOrderAdapter(R.layout.item_query_order, list);
                            View footer = LayoutInflater.from(QueryOrderActivity.this).inflate(R.layout.footer_query_order,recyclerOrder,false);
                            adapter.addFooterView(footer);
                            recyclerOrder.setAdapter(adapter);

                        }else {
                            String message = response.body().getMessage();
                            Logger.e(message);
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
            case R.id.gopay_tv:
                TUtils.showShort(this,"去支付");
                break;
        }
    }

    private class QueryOrderAdapter extends BaseQuickAdapter<QueryOrderBean,BaseViewHolder>{

        private TextView zhekou;
        private TextView zhekouMoney;
        private TextView hejiTv;

        public QueryOrderAdapter(@LayoutRes int layoutResId, @Nullable List<QueryOrderBean> data) {
            super(layoutResId, data);
        }

        @Override
        public int addFooterView(View footer) {
            zhekou = footer.findViewById(R.id.item_zhekou_tv);
            zhekouMoney = footer.findViewById(R.id.item_chekou_money_tv);
            hejiTv = footer.findViewById(R.id.item_heji_tv);
            return super.addFooterView(footer);
        }

        @Override
        protected void convert(BaseViewHolder helper, QueryOrderBean item) {
            TextView title = helper.getView(R.id.item_title_tv);
            TextView content = helper.getView(R.id.item_content_tv);
            TextView money = helper.getView(R.id.item_money_tv);

            ImageView face = helper.getView(R.id.item_face_iv);

            int position = helper.getPosition();

            Glide.with(mContext).load(item.getData().get(position).getUrl()).into(face);
            title.setText(item.getData().get(position).getXk_name());
            content.setText(item.getData().get(position).getXk_teacher_tags());
            money.setText(item.getData().get(position).getXk_price());
            zhekou.setText("折扣优惠"+item.getLevel_discount()+"折");
            zhekouMoney.setText(item.getDiscounts()+"元");
            hejiTv.setText(item.getOrder_amount()+"元");
        }
    }
}
