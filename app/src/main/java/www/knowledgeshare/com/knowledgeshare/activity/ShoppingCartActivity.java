package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.ShoppingCartBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.title_content_right_tv) TextView titleContentRightTv;
    @BindView(R.id.recycler_gwc) RecyclerView recyclerGwc;
    @BindView(R.id.all_checkBox) CheckBox allCheckBox;
    @BindView(R.id.heji_tv) TextView hejiTv;
    @BindView(R.id.jiesuan_tv) TextView jiesuanTv;
    private List<ShoppingCartBean> list;
    private ShoppingCartAdapter adapter;
    private float totalMoney;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentTv.setText("购物车");
        titleContentRightTv.setText("编辑");
        titleBackIv.setOnClickListener(this);
        titleContentRightTv.setOnClickListener(this);
        jiesuanTv.setOnClickListener(this);
        initData();

        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    if (!isAllChecked()){
                        quanxuan();
                    }
                } else {
                    cancleQuanxuan();
                }
            }
        });
    }

    private void initData() {
        recyclerGwc.setLayoutManager(new LinearLayoutManager(this));
        recyclerGwc.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setTitle("崔宗顺的男低音歌唱家秘籍");
            shoppingCartBean.setContent("男低音，一个神秘而又充满魅力的声部男低音，一个神秘而又充满魅力的声部");
            shoppingCartBean.setMoney("19"+i);
            list.add(shoppingCartBean);
        }
        adapter = new ShoppingCartAdapter(R.layout.item_shopping_cart, list);
        recyclerGwc.setAdapter(adapter);

    }

    private void quanxuan() {
        totalMoney = 0;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(true);
            String money = list.get(i).getMoney();
            int parseInt = Integer.parseInt(money);
            totalMoney += parseInt;
        }
//        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
        hejiTv.setText("￥"+totalMoney+"/年");
        jiesuanTv.setText("结算（"+list.size()+"）");
    }


    private void cancleQuanxuan() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
//        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
        totalMoney = 0;
        hejiTv.setText("￥0.00/年");
        jiesuanTv.setText("结算（"+0+"）");
    }

    private boolean isAllChecked() {
        for (int i = 0; i < list.size(); i++) {
            boolean checked = list.get(i).isChecked();
            if (!checked) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                if (TextUtils.equals("编辑",titleContentRightTv.getText().toString())){
                    titleContentRightTv.setText("取消");
                    hejiTv.setText("全选");
                    jiesuanTv.setText("删除");
                }else {
                    titleContentRightTv.setText("编辑");
                    hejiTv.setText("合计：￥"+totalMoney);
                    jiesuanTv.setText("结算（"+num+"）");
                }

                break;
            case R.id.jiesuan_tv:
                if (TextUtils.equals("删除",jiesuanTv.getText().toString())){
                    for (int i = list.size()-1; i >= 0; i--) {
                        if (list.get(i).isChecked()){
                            list.remove(i);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }else {
                    startActivity(new Intent(this,QueryOrderActivity.class));
                }
                break;
        }
    }

    private class ShoppingCartAdapter extends BaseQuickAdapter<ShoppingCartBean,BaseViewHolder>{

        public ShoppingCartAdapter(@LayoutRes int layoutResId, @Nullable List<ShoppingCartBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ShoppingCartBean item) {
            TextView title = helper.getView(R.id.item_title_tv);
            TextView content = helper.getView(R.id.item_content_tv);
            TextView money = helper.getView(R.id.item_money_tv);
            RelativeLayout itemRl = helper.getView(R.id.item_rl);
            final CheckBox item_checkBox = helper.getView(R.id.item_checkBox);

            title.setText(item.getTitle());
            content.setText(item.getContent());
            money.setText("￥"+item.getMoney()+"/年");
            item_checkBox.setChecked(item.isChecked());

            itemRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext,SoftMusicDetailActivity.class));
                }
            });

            item_checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    num = 0;
                    if (!((CheckBox) view).isChecked()){//未选中
                        allCheckBox.setChecked(false);
                        item.setChecked(false);
                        totalMoney = 0;
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).isChecked()){
                                String money = list.get(i).getMoney();
                                int parseInt = Integer.parseInt(money);
                                totalMoney += parseInt;
                                num += 1;
                            }
                        }
                        if (!TextUtils.equals("删除",jiesuanTv.getText().toString())){
                            hejiTv.setText("合计：￥"+totalMoney);
                            jiesuanTv.setText("结算（"+num+"）");
                        }

                    }else {
                        item.setChecked(true);
                        if (!isAllChecked()){//选中后判断----不是全选
                            totalMoney = 0;
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).isChecked()){
                                    String money = list.get(i).getMoney();
                                    int parseInt = Integer.parseInt(money);
                                    totalMoney += parseInt;
                                    num += 1;
                                }
                            }
                            if (!TextUtils.equals("删除",jiesuanTv.getText().toString())){
                                hejiTv.setText("合计：￥"+totalMoney);
                                jiesuanTv.setText("结算（"+num+"）");
                            }

                        }else {//如果点中后全部都是选中状态
                            allCheckBox.setChecked(true);
                            totalMoney = 0;
                            for (int i = 0; i < list.size(); i++) {
                                list.get(i).setChecked(true);
                                String money = list.get(i).getMoney();
                                int parseInt = Integer.parseInt(money);
                                totalMoney += parseInt;
                            }
                            if (!TextUtils.equals("删除",jiesuanTv.getText().toString())){
                                hejiTv.setText("合计：￥"+totalMoney);
                                jiesuanTv.setText("结算（"+list.size()+"）");
                            }
                        }
                    }
                }
            });
        }
    }
}
