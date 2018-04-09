package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.activity.MyAccountActivity;
import www.knowledgeshare.com.knowledgeshare.base.UMShareActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.LookBean;
import www.knowledgeshare.com.knowledgeshare.db.LookUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.AliPayBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.OrderBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.PayResult;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.TimeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.WXPayBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.ZhuanLanBean;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

import static www.knowledgeshare.com.knowledgeshare.R.id.tv_read;

public class ZhuanLanActivity extends UMShareActivity implements View.OnClickListener {

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
    private LinearLayout rootView;
    private TextView readTv;
    private Intent intent;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private String WX_APPID = "wxf33afce9142929dc";// 微信appid
    private static final int SDK_PAY_FLAG = 1;//支付宝
    //-------------------------------------支付宝支付---------------------------------------------
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
                        Toast.makeText(ZhuanLanActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                        finish();
                        showPaySuccessDialog();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        /*
                        "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，
                        最终交易是否成功以服务端异步通知为准（小概率状态）
                         */
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(ZhuanLanActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(ZhuanLanActivity.this, "支付宝支付取消", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuan_lan);
        initView();
        initData();
        initDialog();
        initListener();
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WX_APPID, false);
        // 将该app注册到微信
        api.registerApp(WX_APPID);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        //当在该页面下拉通知栏点击暂停的时候这边按钮也要变化
        if (eventBean.getMsg().equals("weixinpaysuccess")) {
            showPaySuccessDialog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        if (getIntent().getStringExtra("type") != null) {
            rootView.setVisibility(View.GONE);
            readTv.setVisibility(View.VISIBLE);
        }
        mId = getIntent().getStringExtra("id");
        HttpParams params = new HttpParams();
        params.put("id", mId);
        params.put("userid", SpUtils.getString(this, "id", ""));
        OkGo.<ZhuanLanBean>post(MyContants.LXKURL + "zl/show")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<ZhuanLanBean>(ZhuanLanActivity.this, ZhuanLanBean.class) {
                             @Override
                             public void onSuccess(Response<ZhuanLanBean> response) {
                                 int code = response.code();
                                 mZhuanLanBean = response.body();
                                 RequestOptions options=new RequestOptions();
                                 options.error(R.drawable.default_banner);
                                 options.placeholder(R.drawable.default_banner);
                                 Glide.with(MyApplication.getGloableContext()).load(mZhuanLanBean.getZl_img()).apply(options).into(iv_beijing);
                                 ViewGroup.LayoutParams layoutParams = iv_beijing.getLayoutParams();
                                 int width = MyUtils.getScreenWidth(ZhuanLanActivity.this);
                                 layoutParams.height= width*7/15;
                                 iv_beijing.setLayoutParams(layoutParams);
                                 tv_shiyirenqun.setText(mZhuanLanBean.getZl_suitable());
                                 tv_zhuanlanjianjie.setText(mZhuanLanBean.getZl_introduce());
                                 tv_readxuzhi.setText(mZhuanLanBean.getZl_look());
                                 tv_title.setText(mZhuanLanBean.getZl_name());
                                 tv_buy.setText("订阅：" + mZhuanLanBean.getZl_price());
                                 tv_dignyue_count.setText(mZhuanLanBean.getZl_buy_count() + "人订阅");
                                 mLately = mZhuanLanBean.getLately();
                                 mLatelyAdapter = new LatelyAdapter(R.layout.item_lately, mLately);
                                 recycler_lately.setAdapter(mLatelyAdapter);
                                 if (!LookUtils.isInserted(mZhuanLanBean.getZl_name())) {
                                     LookUtils.add(new LookBean(Integer.parseInt(mId), "zhuanlan", mZhuanLanBean.getZl_name()
                                             , mZhuanLanBean.getZl_teacher_tags(), mZhuanLanBean.getZl_price()
                                             , System.currentTimeMillis()));
                                 } else {
                                     LookUtils.updateTime(System.currentTimeMillis(), mZhuanLanBean.getZl_name()
                                             , "zhuanlan");
                                 }
                                 if (!mZhuanLanBean.is_try_look()){
                                     tv_tryread.setBackgroundColor(getResources().getColor(R.color.tab_text_normal_color));
                                     tv_tryread.setTextColor(getResources().getColor(R.color.textcolor));
                                 }
                                 if (mZhuanLanBean.is_buy()){
                                     rootView.setVisibility(View.GONE);
                                     readTv.setVisibility(View.VISIBLE);
                                 }else {
                                     rootView.setVisibility(View.VISIBLE);
                                     readTv.setVisibility(View.GONE);
                                 }
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
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(ZhuanLanActivity.this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<TimeBean>get(MyContants.LXKURL + "order/expire-time")
                .tag(this)
                .headers(headers)
                .execute(new DialogCallback<TimeBean>(ZhuanLanActivity.this, TimeBean.class) {
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
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", mId);
        params.put("type", "zhuanlan");
        params.put("from", "android");
        OkGo.<OrderBean>post(MyContants.LXKURL + "order/buy")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<OrderBean>(OrderBean.class) {
                             @Override
                             public void onSuccess(Response<OrderBean> response) {
                                 OrderBean orderBean = response.body();
                                 if (response.code() >= 200 && response.code() <= 204) {
                                     String order_sn = orderBean.getOrder_sn();
                                     goPay(order_sn, type);
                                 } else {
                                     Toast.makeText(ZhuanLanActivity.this, orderBean.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             }
                         }
                );
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
                    .execute(new DialogCallback<BaseBean>(ZhuanLanActivity.this, BaseBean.class) {
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
                                         Toast.makeText(ZhuanLanActivity.this, message, Toast.LENGTH_SHORT).show();
                                     }

                                 }
                             }
                    );
        } else if (type.equals("2")) {//微信支付
            OkGo.<WXPayBean>post(MyContants.LXKURL + "order/pay")
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new DialogCallback<WXPayBean>(ZhuanLanActivity.this, WXPayBean.class) {
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
                    .execute(new DialogCallback<AliPayBean>(ZhuanLanActivity.this, AliPayBean.class) {
                                 @Override
                                 public void onSuccess(Response<AliPayBean> response) {
                                     int code = response.code();
                                     final AliPayBean aliPayBean = response.body();
                                     Runnable payRunnable = new Runnable() {

                                         @Override
                                         public void run() {
                                             PayTask alipay = new PayTask(ZhuanLanActivity.this);
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

    private void showShareDialog() {
        mBuilder = new BaseDialog.Builder(this);
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
        mDialog.getView(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mZhuanLanBean.getH5_url(), mZhuanLanBean.getZl_introduce(),
                        mZhuanLanBean.getZl_img(), "", ZhuanLanActivity.this, SHARE_MEDIA.WEIXIN);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mZhuanLanBean.getH5_url(), mZhuanLanBean.getZl_introduce(),
                        mZhuanLanBean.getZl_img(), "", ZhuanLanActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mZhuanLanBean.getH5_url(), mZhuanLanBean.getZl_introduce(),
                        mZhuanLanBean.getZl_img(), "", ZhuanLanActivity.this, SHARE_MEDIA.QZONE);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mZhuanLanBean.getH5_url(), mZhuanLanBean.getZl_introduce(),
                        mZhuanLanBean.getZl_img(), "", ZhuanLanActivity.this, SHARE_MEDIA.QQ);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mZhuanLanBean.getH5_url(), mZhuanLanBean.getZl_introduce(),
                        mZhuanLanBean.getZl_img(), "", ZhuanLanActivity.this, SHARE_MEDIA.SINA);
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
                removeAllActivitys();
                Intent intent = new Intent(ZhuanLanActivity.this, MainActivity.class);
                intent.putExtra("gobuy","gobuy");
                startActivity(intent);
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
                startActivity(new Intent(ZhuanLanActivity.this, MyAccountActivity.class));
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
        rootView = (LinearLayout) findViewById(R.id.ll_root_view);
        readTv = (TextView) findViewById(R.id.tv_read);
        readTv.setOnClickListener(this);
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
                intent = new Intent(this, ZhuanLanDetail1Activity.class);
                intent.putExtra("id", mZhuanLanBean.getId() + "");
                intent.putExtra("title", mZhuanLanBean.getZl_name());
                intent.putExtra("is_buy", mZhuanLanBean.is_buy());
                startActivity(intent);
                break;
            case R.id.tv_buy:
                showBuyDialog();
                break;
            case tv_read:
                if (!mZhuanLanBean.is_try_look()){
                    Toast.makeText(this, "暂无试读课程~", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(this, ZhuanLanDetail1Activity.class);
                intent.putExtra("id", mZhuanLanBean.getId() + "");
                intent.putExtra("is_buy", mZhuanLanBean.is_buy());
                intent.putExtra("title", mZhuanLanBean.getZl_name());
//                if (getIntent().getStringExtra("type") != null) {
                    intent.putExtra("type","alreadyBuy");
//                }
                startActivity(intent);
                break;
        }
    }

}
