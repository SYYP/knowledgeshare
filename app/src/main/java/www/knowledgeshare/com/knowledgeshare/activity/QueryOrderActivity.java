package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.bean.QueryOrderBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.AliPayBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.PayResult;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.WXPayBean;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

public class QueryOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.recycler_order)
    RecyclerView recyclerOrder;
    @BindView(R.id.gopay_tv)
    TextView gopayTv;
    private TextView zhekou;
    private TextView zhekouMoney;
    private TextView hejiTv;
    private List<QueryOrderBean.DataBean> list = new ArrayList<>();
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private String WX_APPID = "wxf33afce9142929dc";// 微信appid
    private static final int SDK_PAY_FLAG = 1;//支付宝
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    //                    Toast.makeText(ZhuanLanActivity.this, " " + payResult.getResultStatus(), Toast.LENGTH_SHORT).show();
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(QueryOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //                        finish();
                        showPaySuccessDialog();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        /*
                        "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，
                        最终交易是否成功以服务端异步通知为准（小概率状态）
                         */
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(QueryOrderActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(QueryOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private String ids;
    private String order_sn;
    private QueryOrderBean queryOrderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_order);
        ButterKnife.bind(this);
        initView();
        EventBus.getDefault().register(this);
    }

    private boolean weixinpaysuccess;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        //当在该页面下拉通知栏点击暂停的时候这边按钮也要变化
        if (eventBean.getMsg().equals("weixinpaysuccess")) {
            weixinpaysuccess = true;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (weixinpaysuccess) {
            showPaySuccessDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("确认订单");
        titleBackIv.setOnClickListener(this);
        gopayTv.setOnClickListener(this);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WX_APPID, false);
        // 将该app注册到微信
        api.registerApp(WX_APPID);
        initData();
        initDialog();
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    private void initData() {
        ids = getIntent().getStringExtra("ids");
        recyclerOrder.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrder.setNestedScrollingEnabled(false);
        requestSubmitOrder(ids);
    }

    private void requestSubmitOrder(String ids) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        Logger.e(SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("ids", ids);
        Logger.e(ids);
        params.put("from", "android");

        OkGo.<QueryOrderBean>post(MyContants.submitOrder)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<QueryOrderBean>(QueryOrderActivity.this, QueryOrderBean.class) {
                    @Override
                    public void onSuccess(Response<QueryOrderBean> response) {
                        int code = response.code();
                        queryOrderBean = response.body();
                        if (code >= 200 && code <= 204) {
                            order_sn = queryOrderBean.getOrder_sn();
                            List<QueryOrderBean.DataBean> data = queryOrderBean.getData();
                            list.addAll(data);
                            QueryOrderAdapter adapter = new QueryOrderAdapter(R.layout.item_query_order, list);
                            View footer = LayoutInflater.from(QueryOrderActivity.this).inflate(R.layout.footer_query_order, recyclerOrder, false);
                            zhekou = footer.findViewById(R.id.item_zhekou_tv);
                            zhekouMoney = footer.findViewById(R.id.item_chekou_money_tv);
                            hejiTv = footer.findViewById(R.id.item_heji_tv);
                            zhekou.setText("折扣优惠" + queryOrderBean.getLevel_discount() + "折");
                            zhekouMoney.setText(queryOrderBean.getDiscounts() + "元");
                            hejiTv.setText(queryOrderBean.getOrder_amount() + "元");
                            adapter.addFooterView(footer);
                            recyclerOrder.setAdapter(adapter);

                        } else {
                            String message = response.body().getMessage();
                            Logger.e(message);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.gopay_tv:
                showPayStyleDialog();
                break;
        }
    }

    private void showPayStyleDialog() {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
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
                goPay(order_sn, "1");
            }
        });
        mDialog.getView(R.id.rl_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                goPay(order_sn, "2");

            }
        });
        mDialog.getView(R.id.rl_alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                goPay(order_sn, "3");
            }
        });
    }

    private void goPay(String order_sn, final String type) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("order_sn", order_sn);
        params.put("type", type);
        params.put("from", "android");
        if (type.equals("1")) {//余额支付
            OkGo.<BaseBean>post(MyContants.LXKURL + "order/pay")
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new DialogCallback<BaseBean>(QueryOrderActivity.this, BaseBean.class) {
                                 @Override
                                 public void onSuccess(Response<BaseBean> response) {
                                     int code = response.code();
                                     BaseBean baseBean = response.body();
                                     String message = baseBean.getMessage();
                                     if (message.equals("余额不足")) {
                                         showChongzhiDialog();
                                     } else if (message.equals("支付成功")) {
                                         showPaySuccessDialog();
                                     } else {
                                         Toast.makeText(QueryOrderActivity.this, message, Toast.LENGTH_SHORT).show();
                                     }

                                 }
                             }
                    );
        } else if (type.equals("2")) {//微信支付
            OkGo.<WXPayBean>post(MyContants.LXKURL + "order/pay")
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new DialogCallback<WXPayBean>(QueryOrderActivity.this, WXPayBean.class) {
                                 @Override
                                 public void onSuccess(Response<WXPayBean> response) {
                                     int code = response.code();
                                     WXPayBean wxPayBean = response.body();
                                     PayReq req = new PayReq();
                                     req.appId = wxPayBean.getAppid();// 微信开放平台审核通过的应用APPID
                                     req.partnerId = wxPayBean.getPartnerid();// 微信支付分配的商户号
                                     req.prepayId = wxPayBean.getPrepayid();// 预支付订单号，app服务器调用“统一下单”接口获取
                                     req.nonceStr = wxPayBean.getNoncestr();// 随机字符串，不长于32位，服务器小哥会给咱生成
                                     req.timeStamp = wxPayBean.getTimestamp() + "";// 时间戳，app服务器小哥给出
                                     req.packageValue = wxPayBean.getPackage1();// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
                                     req.sign = wxPayBean.getSign();// 签名，服务器小哥给出
                                     //                        req.extData = "app data"; // optional
                                     // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                     api.sendReq(req);//调起支付
                                 }

                                 @Override
                                 public void onError(Response<WXPayBean> response) {
                                     super.onError(response);
                                 }
                             }
                    );
        } else if (type.equals("3")) {//支付宝支付
            OkGo.<AliPayBean>post(MyContants.LXKURL + "order/pay")
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new DialogCallback<AliPayBean>(QueryOrderActivity.this, AliPayBean.class) {
                                 @Override
                                 public void onSuccess(Response<AliPayBean> response) {
                                     int code = response.code();
                                     final AliPayBean aliPayBean = response.body();
                                     Runnable payRunnable = new Runnable() {

                                         @Override
                                         public void run() {
                                             PayTask alipay = new PayTask(QueryOrderActivity.this);
                                             String result = alipay.pay(aliPayBean.getAlipay(), true);//调用支付接口，获取支付结果
                                             Message msg = new Message();
                                             msg.what = SDK_PAY_FLAG;
                                             msg.obj = result;
                                             mHandler.sendMessage(msg);
                                         }
                                     };

                                     // 必须异步调用，支付或者授权的行为需要在独立的非ui线程中执行
                                     Thread payThread = new Thread(payRunnable);
                                     payThread.start();
                                 }

                                 @Override
                                 public void onError(Response<AliPayBean> response) {
                                     super.onError(response);
                                 }
                             }
                    );
        }
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
                startActivity(new Intent(QueryOrderActivity.this, MyAccountActivity.class));
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
                removeAllActivitys();
                Intent intent = new Intent(QueryOrderActivity.this, MainActivity.class);
                intent.putExtra("gobuy", "gobuy");
                startActivity(intent);
            }
        });
    }

    private class QueryOrderAdapter extends BaseQuickAdapter<QueryOrderBean.DataBean, BaseViewHolder> {

        public QueryOrderAdapter(@LayoutRes int layoutResId, @Nullable List<QueryOrderBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        public int addFooterView(View footer) {

            return super.addFooterView(footer);
        }

        @Override
        protected void convert(BaseViewHolder helper, QueryOrderBean.DataBean item) {
            TextView title = helper.getView(R.id.item_title_tv);
            TextView content = helper.getView(R.id.item_content_tv);
            TextView money = helper.getView(R.id.item_money_tv);
            ImageView face = helper.getView(R.id.item_face_iv);


            Glide.with(mContext).load(item.getUrl()).into(face);
            title.setText(item.getXk_name());
            content.setText(item.getXk_teacher_tags());
            money.setText(item.getXk_price());
        }
    }
}
