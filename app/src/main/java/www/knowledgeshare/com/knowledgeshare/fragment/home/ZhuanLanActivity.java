package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.OrderBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.TimeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.ZhuanLanBean;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

public class ZhuanLanActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_share;
    private ImageView iv_beijing;
    private TextView tv_title;
    private TextView tv_dignyue_count;
    private TextView tv_shiyirenqun;
    private TextView tv_readxuzhi;
    private TextView tv_zhuanlanjianjie;
    private RecyclerView recycler_lately;
    private TextView tv_tryread;
    private TextView tv_buy;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private FrameLayout fl_root_view;
    private LinearLayout ll_root_view;
    private NestedScrollView nestView;
    private List<ZhuanLanBean.LatelyEntity> mLately;
    private LatelyAdapter mLatelyAdapter;
    private ZhuanLanBean mZhuanLanBean;
    private String mId;
    private String mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuan_lan);
        initView();
        initData();
        initDialog();
        initListener();
    }

    private void initListener() {
        nestView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 0) {
                    setPopHide();
                } else if (scrollY - oldScrollY < 0) {
                    SlidePopShow();
                }
            }
        });
    }

    private void initData() {
        mId = getIntent().getStringExtra("id");
        HttpParams params = new HttpParams();
        params.put("id", mId);
        OkGo.<ZhuanLanBean>post(MyContants.LXKURL + "zl/show")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<ZhuanLanBean>(ZhuanLanBean.class) {
                             @Override
                             public void onSuccess(Response<ZhuanLanBean> response) {
                                 int code = response.code();
                                 mZhuanLanBean = response.body();
                                 Glide.with(ZhuanLanActivity.this).load(mZhuanLanBean.getZl_img()).into(iv_beijing);
                                 tv_shiyirenqun.setText(mZhuanLanBean.getZl_suitable());
                                 tv_zhuanlanjianjie.setText(mZhuanLanBean.getZl_introduce());
                                 tv_readxuzhi.setText(mZhuanLanBean.getZl_look());
                                 tv_title.setText(mZhuanLanBean.getZl_name());
                                 tv_buy.setText("订阅：" + mZhuanLanBean.getZl_price());
                                 tv_dignyue_count.setText(mZhuanLanBean.getZl_buy_count() + "人订阅");
                                 mLately = mZhuanLanBean.getLately();
                                 mLatelyAdapter = new LatelyAdapter(R.layout.item_lately, mLately);
                                 recycler_lately.setAdapter(mLatelyAdapter);
                             }
                         }
                );
        //        if (getIntent().getBooleanExtra("buyed",false)){
        //            tv_tryread.setText("阅读");
        //            tv_buy.setVisibility(View.GONE);
        //            tv_tryread.setBackgroundColor(getResources().getColor(R.color.text_red_dark));
        //            tv_tryread.setTextColor(getResources().getColor(R.color.white));
        //        }
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    private void showBuyDialog() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<TimeBean>get(MyContants.LXKURL + "order/expire-time")
                .tag(this)
                .headers(headers)
                .execute(new JsonCallback<TimeBean>(TimeBean.class) {
                             @Override
                             public void onSuccess(Response<TimeBean> response) {
                                 int code = response.code();
                                 TimeBean timeBean = response.body();
                                 mTime = timeBean.getTime();
                                 mDialog = mBuilder.setViewId(R.layout.dialog_dingyue)
                                         //设置dialogpadding
                                         .setPaddingdp(10, 0, 10, 0)
                                         //设置显示位置
                                         .setGravity(Gravity.BOTTOM)
                                         //设置动画
                                         .setAnimation(R.style.Bottom_Top_aniamtion)
                                         //设置dialog的宽高
                                         .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                         //设置触摸dialog外围是否关闭
                                         .isOnTouchCanceled(true)
                                         //设置监听事件
                                         .builder();
                                 mDialog.show();
                                 TextView tv_content = mDialog.getView(R.id.tv_content);
                                 tv_content.setText("您将订阅从" + mTime + "时间内的《" + mZhuanLanBean.getZl_name() + "》");
                                 mDialog.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         mDialog.dismiss();
                                     }
                                 });
                                 mDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         mDialog.dismiss();
                                         showPayStyleDialog();
                                     }
                                 });
                             }
                         }
                );
    }

    private void pushOrder(final String type) {
        HttpParams params = new HttpParams();
        params.put("id", mId);
        params.put("type", "zhuanlan");
        params.put("from", "android");
        OkGo.<OrderBean>post(MyContants.LXKURL + "order/buy")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<OrderBean>(OrderBean.class) {
                             @Override
                             public void onSuccess(Response<OrderBean> response) {
                                 int code = response.code();
                                 OrderBean orderBean = response.body();
                                 String order_sn = orderBean.getOrder_sn();
                                 goPay(order_sn, type);
                             }
                         }
                );
    }

    private void goPay(String order_sn, String type) {
        HttpParams params = new HttpParams();
        params.put("order_sn", order_sn);
        params.put("type", type);
        params.put("from", "android");
        OkGo.<BaseBean>post(MyContants.LXKURL + "order/pay")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                             @Override
                             public void onSuccess(Response<BaseBean> response) {
                                 int code = response.code();
                                 BaseBean baseBean = response.body();
                                 String message = baseBean.getMessage();
                                 if (message.equals("余额不足")){
                                     showChongzhiDialog();
                                 }else {
                                     Toast.makeText(ZhuanLanActivity.this,message, Toast.LENGTH_SHORT).show();
                                 }
                             }
                         }
                );
    }

    private void showShareDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_share)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.BOTTOM)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    private void showPayStyleDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_buy)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.BOTTOM)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
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
        mDialog.getView(R.id.rl_yuezhifu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                pushOrder("1");
            }
        });
        mDialog.getView(R.id.rl_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                pushOrder("2");
            }
        });
        mDialog.getView(R.id.rl_alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                pushOrder("3");
            }
        });
    }

    private void showPaySuccessDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_paysuccess)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    private void showChongzhiDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_ischongzhi)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showPaySuccessDialog();
            }
        });
    }

    private void initView() {
        fl_root_view = (FrameLayout) findViewById(R.id.fl_root_view);
        ll_root_view = (LinearLayout) findViewById(R.id.ll_root_view);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_dignyue_count = (TextView) findViewById(R.id.tv_dignyue_count);
        tv_shiyirenqun = (TextView) findViewById(R.id.tv_shiyirenqun);
        tv_zhuanlanjianjie = (TextView) findViewById(R.id.tv_zhuanlanjianjie);
        tv_readxuzhi = (TextView) findViewById(R.id.tv_readxuzhi);
        recycler_lately = (RecyclerView) findViewById(R.id.recycler_lately);
        tv_tryread = (TextView) findViewById(R.id.tv_tryread);
        tv_tryread.setOnClickListener(this);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(this);
        recycler_lately.setLayoutManager(new LinearLayoutManager(this));
        recycler_lately.setNestedScrollingEnabled(false);
        nestView = (NestedScrollView) findViewById(R.id.nestView);
    }

    private class LatelyAdapter extends BaseQuickAdapter<ZhuanLanBean.LatelyEntity, BaseViewHolder> {

        public LatelyAdapter(@LayoutRes int layoutResId, @Nullable List<ZhuanLanBean.LatelyEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ZhuanLanBean.LatelyEntity item) {
            helper.setText(R.id.tv_name, item.getName())
                    .setText(R.id.tv_time, item.getCreated_at())
                    .setText(R.id.tv_description, item.getDescription());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                showShareDialog();
                break;
            case R.id.tv_tryread:
                Intent intent = new Intent(this, ZhuanLanDetail1Activity.class);
                intent.putExtra("id", mZhuanLanBean.getId() + "");
                intent.putExtra("title", mZhuanLanBean.getZl_name());
                startActivity(intent);
                break;
            case R.id.tv_buy:
                showBuyDialog();
                break;
        }
    }
}
