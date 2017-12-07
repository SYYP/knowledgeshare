package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

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
                clear(1);
                break;
            case R.id.two_money_tv:
                twoMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                twoMoneyTv.setTextColor(getResources().getColor(R.color.white));
                clear(2);
                break;
            case R.id.three_money_tv:
                threeMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                threeMoneyTv.setTextColor(getResources().getColor(R.color.white));
                clear(3);
                break;
            case R.id.four_money_tv:
                fourMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                fourMoneyTv.setTextColor(getResources().getColor(R.color.white));
                clear(4);
                break;
            case R.id.five_money_tv:
                fiveMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                fiveMoneyTv.setTextColor(getResources().getColor(R.color.white));
                clear(5);
                break;
            case R.id.six_money_tv:
                sixMoneyTv.setBackground(getResources().getDrawable(R.drawable.bg_yellow3));
                sixMoneyTv.setTextColor(getResources().getColor(R.color.white));
                clear(6);
                break;
            case R.id.query_tv:
                break;
            case R.id.gmjl_tv:
                startActivity(new Intent(this,PurchaseHistoryActivity.class));
                break;
            case R.id.gwc_tv:
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
}
