package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.ChangeShowBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HomeBean;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * 我的账户
 */
public class MyAccountActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.account_money_tv) TextView accountMoneyTv;
    @BindView(R.id.one_money_tv) TextView oneMoneyTv;
    @BindView(R.id.two_money_tv) TextView twoMoneyTv;
    @BindView(R.id.three_money_tv) TextView threeMoneyTv;
    @BindView(R.id.four_money_tv) TextView fourMoneyTv;
    @BindView(R.id.five_money_tv) TextView fiveMoneyTv;
    @BindView(R.id.six_money_tv) TextView sixMoneyTv;
    @BindView(R.id.pay_money_tv) TextView payMoneyTv;
    @BindView(R.id.query_tv) TextView queryTv;
    @BindView(R.id.gmjl_tv) TextView gmjlTv;
    @BindView(R.id.gwc_tv) TextView gwcTv;
    @BindView(R.id.recycler_money) RecyclerView recyclerView;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private List<ChangeShowBean.MoneyBean> money;
    private TextView moneyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("我的账户");
        mBuilder = new BaseDialog.Builder(this);
        oneMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
        oneMoneyTv.setTextColor(getResources().getColor(R.color.white));
        titleBackIv.setOnClickListener(this);
        oneMoneyTv.setOnClickListener(this);
        twoMoneyTv.setOnClickListener(this);
        threeMoneyTv.setOnClickListener(this);
        fourMoneyTv.setOnClickListener(this);
        fiveMoneyTv.setOnClickListener(this);
        sixMoneyTv.setOnClickListener(this);
        queryTv.setOnClickListener(this);
        gmjlTv.setOnClickListener(this);
        gwcTv.setOnClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(MyAccountActivity.this, 3));
        recyclerView.setNestedScrollingEnabled(false);
        requestChange();
    }

    private void requestChange() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<ChangeShowBean>get(MyContants.changeShow)
                .tag(this)
                .headers(headers)
                .execute(new JsonCallback<ChangeShowBean>(ChangeShowBean.class) {
                    @Override
                    public void onSuccess(Response<ChangeShowBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            money = response.body().getMoney();
                            money.get(0).setChecked(true);
                            ChangeShowAdapter adapter = new ChangeShowAdapter(R.layout.item_change_show,money);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
    }

    private class ChangeShowAdapter extends BaseQuickAdapter<ChangeShowBean.MoneyBean, BaseViewHolder>{

        public ChangeShowAdapter(@LayoutRes int layoutResId, @Nullable List<ChangeShowBean.MoneyBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ChangeShowBean.MoneyBean item) {
            final int position = helper.getAdapterPosition();

            moneyTv = helper.getView(R.id.money_tv);
            moneyTv.setText(item.getData());

            if (item.isChecked()){
                moneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                moneyTv.setTextColor(getResources().getColor(R.color.white));
            }else {
                moneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                moneyTv.setTextColor(getResources().getColor(R.color.yellow));
            }

            moneyTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < money.size(); i++) {
                        money.get(i).setChecked(false);
                        moneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                        moneyTv.setTextColor(getResources().getColor(R.color.yellow));
                    }
                    item.setChecked(true);
                    moneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                    moneyTv.setTextColor(getResources().getColor(R.color.white));
                    payMoneyTv.setText("支付金额："+money.get(position).getData()+"元");
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.one_money_tv:
                oneMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                oneMoneyTv.setTextColor(getResources().getColor(R.color.white));
                payMoneyTv.setText("支付金额：28元");
                clear(1);
                break;
            case R.id.two_money_tv:
                twoMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                twoMoneyTv.setTextColor(getResources().getColor(R.color.white));
                payMoneyTv.setText("支付金额：88元");
                clear(2);
                break;
            case R.id.three_money_tv:
                threeMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                threeMoneyTv.setTextColor(getResources().getColor(R.color.white));
                payMoneyTv.setText("支付金额：99元");
                clear(3);
                break;
            case R.id.four_money_tv:
                fourMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                fourMoneyTv.setTextColor(getResources().getColor(R.color.white));
                payMoneyTv.setText("支付金额：100元");
                clear(4);
                break;
            case R.id.five_money_tv:
                fiveMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                fiveMoneyTv.setTextColor(getResources().getColor(R.color.white));
                payMoneyTv.setText("支付金额：188元");
                clear(5);
                break;
            case R.id.six_money_tv:
                sixMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                sixMoneyTv.setTextColor(getResources().getColor(R.color.white));
                payMoneyTv.setText("支付金额：888元");
                clear(6);
                break;
            case R.id.query_tv:
                showBuyDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
                break;
            case R.id.gmjl_tv://购买记录
                startActivity(new Intent(this,PurchaseHistoryActivity.class));
                break;
            case R.id.gwc_tv://购物车
                startActivity(new Intent(this,ShoppingCartActivity.class));
                break;
        }
    }

    private void clear(int i) {
        switch (i){
            case 1:
                twoMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                twoMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                threeMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                threeMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fourMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fourMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fiveMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fiveMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                sixMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                sixMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case 2:
                oneMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                oneMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                threeMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                threeMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fourMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fourMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fiveMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fiveMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                sixMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                sixMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case 3:
                oneMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                oneMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                twoMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                twoMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fourMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fourMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fiveMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fiveMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                sixMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                sixMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case 4:
                oneMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                oneMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                twoMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                twoMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                threeMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                threeMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fiveMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fiveMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                sixMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                sixMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case 5:
                oneMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                oneMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                twoMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                twoMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                threeMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                threeMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fourMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fourMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                sixMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                sixMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case 6:
                oneMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                oneMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                twoMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                twoMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                threeMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                threeMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fourMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fourMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                fiveMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow2));
                fiveMoneyTv.setTextColor(getResources().getColor(R.color.yellow));
                break;
        }
    }

    private void showBuyDialog(int grary, int animationStyle) {
        mDialog = mBuilder.setViewId(R.layout.dialog_buy)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.rl_yuezhifu).setVisibility(View.GONE);
       /* mDialog.getView(R.id.rl_yuezhifu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showChongzhiDialog();
            }
        });*/
    }
}
