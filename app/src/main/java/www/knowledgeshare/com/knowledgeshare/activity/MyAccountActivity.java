package www.knowledgeshare.com.knowledgeshare.activity;

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
                break;
            case R.id.two_money_tv:
                break;
            case R.id.three_money_tv:
                break;
            case R.id.four_money_tv:
                break;
            case R.id.five_money_tv:
                break;
            case R.id.six_money_tv:
                break;
            case R.id.query_tv:
                break;
            case R.id.gmjl_tv:
                break;
            case R.id.gwc_tv:
                break;
        }
    }
}
