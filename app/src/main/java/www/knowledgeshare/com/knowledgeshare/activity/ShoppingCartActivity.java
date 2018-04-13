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
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.ShoppingCartBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.title_content_right_tv) TextView titleContentRightTv;
    @BindView(R.id.recycler_gwc) RecyclerView recyclerGwc;
    @BindView(R.id.all_checkBox) CheckBox allCheckBox;
    @BindView(R.id.heji_tv) TextView hejiTv;
    @BindView(R.id.jiesuan_tv) TextView jiesuanTv;
    @BindView(R.id.null_rl) RelativeLayout nullRl;
    @BindView(R.id.jiesuan_rl) RelativeLayout jiesuanRl;
    private List<ShoppingCartBean.DataBean> list = new ArrayList<>();
    private ShoppingCartAdapter adapter;
    private double totalMoney;
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
        jiesuanRl.setVisibility(View.VISIBLE);
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
        requestCartList();
        /*list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setTitle("崔宗顺的男低音歌唱家秘籍");
            shoppingCartBean.setContent("男低音，一个神秘而又充满魅力的声部男低音，一个神秘而又充满魅力的声部");
            shoppingCartBean.setMoney("19"+i);
            list.add(shoppingCartBean);
        }*/

    }

    private void requestCartList() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));

        OkGo.<ShoppingCartBean>post(MyContants.cartList)
                .tag(this)
                .headers(headers)
                .execute(new DialogCallback<ShoppingCartBean>(ShoppingCartActivity.this,ShoppingCartBean.class) {
                    @Override
                    public void onSuccess(Response<ShoppingCartBean> response) {
                        int code = response.code();
                        ShoppingCartBean body = response.body();
                        if (response.code() >= 200 && response.code() <= 204){
                            list = response.body().getData();
                            if (list.toString().equals("[]") && list.size() == 0){
//                                adapter.notifyDataSetChanged();
                                recyclerGwc.setVisibility(View.GONE);
                                nullRl.setVisibility(View.VISIBLE);
                                hejiTv.setText("合计：￥0.00");
                                jiesuanTv.setText("结算（0）");
                                allCheckBox.setChecked(false);
                            }else {
                                adapter = new ShoppingCartAdapter(R.layout.item_shopping_cart, list);
                                recyclerGwc.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                if (list.size() > 0){
                                    jiesuanRl.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < list.size(); i++) {
                                        if (list.get(i).isChecked()){
                                            String money = list.get(i).getXk_price();
                                            double parseInt = Double.parseDouble(money);
                                            totalMoney += parseInt;
                                            num += 1;
                                            Logger.e("打印："+totalMoney+"\n"+num);
                                        }
                                    }
                                    hejiTv.setText("合计：￥"+totalMoney);
                                    jiesuanTv.setText("结算（"+num+"）");
                                }else {
                                    totalMoney = 0;
                                    num = 0;
                                    jiesuanRl.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                });
    }

    private void quanxuan() {
        totalMoney = 0;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(true);
            String money = list.get(i).getXk_price();
            double parseInt = Double.parseDouble(money);
            totalMoney += parseInt;
        }
//        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
        if (TextUtils.equals("编辑",titleContentRightTv.getText().toString())){
            hejiTv.setText("合计：￥"+totalMoney);
            jiesuanTv.setText("结算（"+list.size()+"）");
        }else {
            hejiTv.setText("全选");
            jiesuanTv.setText("删除");
        }
    }


    private void cancleQuanxuan() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
//        adapter.setNewData(list);
        if (adapter!=null)
        adapter.notifyDataSetChanged();
        totalMoney = 0;
        if (TextUtils.equals("编辑",titleContentRightTv.getText().toString())){
            hejiTv.setText("合计：￥0.00");
            jiesuanTv.setText("结算（"+0+"）");
        }else {
            hejiTv.setText("全选");
            jiesuanTv.setText("删除");
        }
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
                    totalMoney = 0;
                    num = 0;
//                    if (TextUtils.equals("0",list.size()+""))
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isChecked()){
                            String money = list.get(i).getXk_price();
                            double parseInt = Double.parseDouble(money);
                            totalMoney += parseInt;
                            num += 1;
                            Logger.e("打印："+totalMoney+"\n"+num);
                        }
                    }
                    hejiTv.setText("合计：￥"+totalMoney);
                    jiesuanTv.setText("结算（"+num+"）");
                }

                break;
            case R.id.jiesuan_tv:
                if (TextUtils.equals("删除",jiesuanTv.getText().toString())){
                    if (list.size() > 0){
                        StringBuffer sb = new StringBuffer();
                        for (int i = list.size()-1; i >= 0; i--) {
                            if (list.get(i).isChecked()){
                                list.get(i).getId();
                                sb.append(list.get(i).getId()+",");
                            /*if (TextUtils.equals("0",list.size()+"")){
                                recyclerGwc.setVisibility(View.GONE);
                                nullRl.setVisibility(View.VISIBLE);
                            }else {
                                adapter.notifyDataSetChanged();
                            }*/
                            }
                        }
                        //当循环结束后截取最后一个逗号
                        String ids = sb.substring(0, sb.length() - 1);
                        requestDelCart(ids);
                    }else {
                        TUtils.showShort(this,"购物车是空的");
                    }
                }else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isChecked()){
                            sb.append(list.get(i).getId()+",");
                        }
                    }
                    if (TextUtils.isEmpty(sb)){
                        TUtils.showShort(this,"购物车是空的");
                    }else {
                        //当循环结束后截取最后一个逗号
                        String ids = sb.substring(0, sb.length() - 1);
                        Intent intent = new Intent(this,QueryOrderActivity.class);
                        intent.putExtra("ids",ids);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        totalMoney = 0;
        num = 0;
        requestCartList();
    }

    private void requestDelCart(String ids) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("ids",ids);

        OkGo.<BaseBean>post(MyContants.delCart)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            requestCartList();
                        }
                    }
                });

    }

    private class ShoppingCartAdapter extends BaseQuickAdapter<ShoppingCartBean.DataBean,BaseViewHolder>{

        public ShoppingCartAdapter(@LayoutRes int layoutResId, @Nullable List<ShoppingCartBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final ShoppingCartBean.DataBean item) {
            TextView title = helper.getView(R.id.item_title_tv);
            TextView content = helper.getView(R.id.item_content_tv);
            TextView money = helper.getView(R.id.item_money_tv);
            ImageView face = helper.getView(R.id.item_face_iv);
            RelativeLayout itemRl = helper.getView(R.id.item_rl);
            final CheckBox item_checkBox = helper.getView(R.id.item_checkBox);

            Glide.with(mContext).load(item.getUrl()).into(face);
            title.setText(item.getXk_name());
            content.setText(item.getXk_teacher_tags());
            money.setText("￥"+item.getXk_price()+"/年");
            item_checkBox.setChecked(item.isChecked());

            itemRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,SoftMusicDetailActivity.class);
                    intent.putExtra("id",list.get(helper.getAdapterPosition()).getXk_id());
                    startActivity(intent);
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
                                String money = list.get(i).getXk_price();
                                double parseInt = Double.parseDouble(money);
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
                                    String money = list.get(i).getXk_price();
                                    double parseInt = Double.parseDouble(money);
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
                                String money = list.get(i).getXk_price();
                                double parseInt = Double.parseDouble(money);
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
