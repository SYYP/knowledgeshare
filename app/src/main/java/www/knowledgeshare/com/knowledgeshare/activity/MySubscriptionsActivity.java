package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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
import www.knowledgeshare.com.knowledgeshare.bean.MyRssBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.EasyLessonBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * 我的订阅
 */
public class MySubscriptionsActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.recycler_wddy) RecyclerView recyclerWddy;
    List<MyRssBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscriptions);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("我的订阅");
        titleBackIv.setOnClickListener(this);
        recyclerWddy.setLayoutManager(new LinearLayoutManager(this));
        recyclerWddy.setNestedScrollingEnabled(false);
        requestRss();
        /*List<EasyLessonBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EasyLessonBean easyLessonBean = new EasyLessonBean();
            easyLessonBean.setTitle("崔宗顺的男低音歌唱家秘籍");
            easyLessonBean.setDesc("男低音，一个神秘又充满魅力的声部");
            easyLessonBean.setUpdata(i+"小时前更新");
            easyLessonBean.setZlmc("最新更新的专栏名称");
            easyLessonBean.setMoney("￥198/年");
            list.add(easyLessonBean);
        }*/

    }

    private void requestRss() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));

        OkGo.<MyRssBean>get(MyContants.rss)
                .tag(this)
                .headers(headers)
                .execute(new DialogCallback<MyRssBean>(this,MyRssBean.class) {
                    @Override
                    public void onSuccess(Response<MyRssBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            list.add(response.body());
                            if (list.size() > 0){
                                final List<MyRssBean.DataBean> data = response.body().getData();
                                MySubAdapter adapter = new MySubAdapter(R.layout.item_my_sub,list);
                                recyclerWddy.setAdapter(adapter);
                                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(MySubscriptionsActivity.this, ZhuanLanActivity.class);
                                        intent.putExtra("id",data.get(position).getId()+"");
                                        intent.putExtra("type","alreadyBuy");
                                        startActivity(intent);
                                    }
                                });
                            }
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

    private class MySubAdapter extends BaseQuickAdapter<MyRssBean, BaseViewHolder>{

        public MySubAdapter(@LayoutRes int layoutResId, @Nullable List<MyRssBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyRssBean item) {
            TextView subTitleTv = helper.getView(R.id.sub_title_tv);
            TextView subDescTv = helper.getView(R.id.sub_desc_tv);
            TextView subUpdataTv = helper.getView(R.id.sub_updata_tv);
            TextView subZlmcTv = helper.getView(R.id.sub_zlmc_tv);
            TextView subMoneyTv = helper.getView(R.id.sub_money_tv);
            ImageView subFaceIv = helper.getView(R.id.sub_face_iv);

            int position = helper.getPosition();
            if (!TextUtils.isEmpty(item.getData().get(position).getZl_img())){
                Glide.with(mContext).load(item.getData().get(position).getZl_img()).into(subFaceIv);
            }
            subTitleTv.setText(item.getData().get(position).getZl_name());
            subDescTv.setText(item.getData().get(position).getZl_teacher_tags());
            subUpdataTv.setText(item.getData().get(position).getZl_update_time());
            subZlmcTv.setText(item.getData().get(position).getZl_update_name());
            subMoneyTv.setText(item.getData().get(position).getZl_price());
        }
    }
}
