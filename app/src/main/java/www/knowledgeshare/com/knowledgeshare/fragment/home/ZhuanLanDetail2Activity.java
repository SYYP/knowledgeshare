package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.activity.MyAccountActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.db.StudyTimeBean;
import www.knowledgeshare.com.knowledgeshare.db.StudyTimeUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.AliPayBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.FreeTryReadDetailBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.OrderBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.PayResult;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.TimeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.WXPayBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.web.ActionSelectListener;
import www.knowledgeshare.com.knowledgeshare.fragment.home.web.CustomActionWebView;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;


public class ZhuanLanDetail2Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private CircleImageView iv_teacher_head;
    private TextView tv_teacher_name;
    private ImageView iv_collect, iv_beijing;
    private TextView tv_time1;
    private ImageView iv_bofang;
    private TextView tv_title2, tv_content;
    private TextView tv_time2;
    private ImageView iv_download;
    private TextView tv_writeliuyan;
    private RecyclerView recycler_liuyan;
    private TextView tv_buy;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private boolean isCollected;
    private boolean isDianzan;
    private CustomActionWebView webview;
    private NestedScrollView nestView;
    private boolean isBofang;
    private int lastID;
    private LiuYanAdapter mLiuYanAdapter;
    private List<FreeTryReadDetailBean.CommentEntity> mComment;
    private FreeTryReadDetailBean mFreeTryReadDetailBean;
    private String mTime;
    private String mId;
    private BaseDialog mNetDialog;
    private long pretime;
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
                        Toast.makeText(ZhuanLanDetail2Activity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                        finish();
                        showPaySuccessDialog();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        /*
                        "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，
                        最终交易是否成功以服务端异步通知为准（小概率状态）
                         */
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(ZhuanLanDetail2Activity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(ZhuanLanDetail2Activity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_zhuan_lan_detail2);
        EventBus.getDefault().register(this);
        initView();
        initDialog();
        initData();
        initListener();
        initNETDialog();
        setTimeRecord();
        setStudyTime();
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WX_APPID, false);
        // 将该app注册到微信
        api.registerApp(WX_APPID);
    }
    private boolean weixinpaysuccess;

    @Override
    protected void onRestart() {
        super.onRestart();
        if (weixinpaysuccess) {
            showPaySuccessDialog();
        }
    }

    private void setStudyTime() {
        pretime = SystemClock.currentThreadTimeMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        long lasttime = SystemClock.currentThreadTimeMillis() - pretime;
        if (!StudyTimeUtils.isHave("zhuanlandetail", mId)) {
            StudyTimeBean studyTimeBean = new StudyTimeBean(Integer.parseInt(mId), "zhuanlandetail",
                    MyUtils.getCurrentDate(), lasttime);
            StudyTimeUtils.add(studyTimeBean);
        } else {
            long oneTime = StudyTimeUtils.getOneTime("zhuanlandetail", mId);
            lasttime += oneTime;
            StudyTimeUtils.updateTime("zhuanlandetail", mId, lasttime);
        }
    }

    private void setTimeRecord() {
        String userid = SpUtils.getString(this, "id", "");
        if (!TextUtils.isEmpty(userid)) {
            HttpHeaders headers = new HttpHeaders();
            headers.put("Authorization", "Bearer " + SpUtils.getString(MyApplication.getGloableContext(), "token", ""));
            HttpParams params = new HttpParams();
            params.put("id", mId);
            params.put("type", "zl");
            params.put("date", MyUtils.getCurrentDate());
            OkGo.<DianZanbean>post(MyContants.LXKURL + "user/study-add")
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                                 @Override
                                 public void onSuccess(Response<DianZanbean> response) {
                                     int code = response.code();

                                 }
                             }
                    );
        }
    }

    private void initWebView(String url) {
        List<String> list = new ArrayList<>();
        list.add("添加笔记");
        webview.setWebViewClient(new CustomWebViewClient());
        //设置item
        webview.setActionList(list);
        //链接js注入接口，使能选中返回数据
        webview.linkJSInterface();
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        //使用javascript
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        //增加点击回调
        webview.setActionSelectListener(new ActionSelectListener() {
            @Override
            public void onClick(String title, String selectText) {
                //                Toast.makeText(ZhuanLanDetail2Activity.this, "Click Item: " + title + "。\n\nValue: " + selectText, Toast.LENGTH_LONG).show();
                addNote(selectText);
            }
        });
        webview.loadUrl(url);
    }

    private void addNote(String selectText) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("title", getIntent().getStringExtra("title"));
        params.put("content", selectText);
        params.put("type", "zl");
        OkGo.<DianZanbean>post(MyContants.addNote)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

    private class CustomWebViewClient extends WebViewClient {

        private boolean mLastLoadFailed = false;

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            if (!mLastLoadFailed) {
                CustomActionWebView customActionWebView = (CustomActionWebView) webView;
                customActionWebView.linkJSInterface();
            }
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mLastLoadFailed = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {//当这个界面的小型播放器点击了叉号的时候，播放按钮成暂停
        if (eventBean.getMsg().equals("norotate")) {
            isBofang = false;
            iv_bofang.setImageResource(R.drawable.pause_yellow_middle);
        }
        //当在该页面下拉通知栏点击暂停的时候这边按钮也要变化
        if (eventBean.getMsg().equals("weixinpaysuccess")) {
            weixinpaysuccess = true;
        }
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

    private void initNETDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        mNetDialog = builder.setViewId(R.layout.dialog_iswifi)
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
    }

    private void gobofang(final PlayerBean playerBean) {
        int apnType = NetWorkUtils.getAPNType(this);
        if (apnType == 0) {
            Toast.makeText(this, "没有网络呢~", Toast.LENGTH_SHORT).show();
        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
            if (SpUtils.getBoolean(this, "nowifiallowlisten", false)) {//记住用户允许流量播放
                playerBean.setMsg("refreshplayer");
                EventBus.getDefault().postSticky(playerBean);
                mMyBinder.setMusicUrl(playerBean.getVideo_url());
                mMyBinder.playMusic(playerBean);
                mNetDialog.dismiss();
                ClickPopShow();
                SpUtils.putBoolean(this, "nowifiallowlisten", true);
            } else {
                mNetDialog.show();
                mNetDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playerBean.setMsg("refreshplayer");
                        EventBus.getDefault().postSticky(playerBean);
                        mMyBinder.setMusicUrl(playerBean.getVideo_url());
                        mMyBinder.playMusic(playerBean);
                        mNetDialog.dismiss();
                        ClickPopShow();
                        SpUtils.putBoolean(ZhuanLanDetail2Activity.this, "nowifiallowlisten", true);
                    }
                });
                mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNetDialog.dismiss();
                    }
                });
            }
        } else if (NetWorkUtils.isMobileConnected(ZhuanLanDetail2Activity.this)) {
            Toast.makeText(this, "wifi不可用呢~", Toast.LENGTH_SHORT).show();
        } else {
            playerBean.setMsg("refreshplayer");
            EventBus.getDefault().postSticky(playerBean);
            mMyBinder.setMusicUrl(playerBean.getVideo_url());
            mMyBinder.playMusic(playerBean);
            ClickPopShow();
        }
    }

    private void initData() {
        tv_title.setText(getIntent().getStringExtra("title"));
        mId = getIntent().getStringExtra("id");
        HttpParams params = new HttpParams();
        params.put("id", mId);
        params.put("userid", SpUtils.getString(this, "id", ""));
        if (getIntent().getStringExtra("type") != null) {
            params.put("type", "2");
        }else {
            params.put("type", "1");
        }
        OkGo.<FreeTryReadDetailBean>post(MyContants.LXKURL + "zl/trials-show")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<FreeTryReadDetailBean>(ZhuanLanDetail2Activity.this, FreeTryReadDetailBean.class) {
                             @Override
                             public void onSuccess(Response<FreeTryReadDetailBean> response) {
                                 int code = response.code();
                                 mFreeTryReadDetailBean = response.body();
                                 Glide.with(MyApplication.getGloableContext()).load(mFreeTryReadDetailBean.getT_header()).into(iv_teacher_head);
                                 tv_teacher_name.setText(mFreeTryReadDetailBean.getT_name());
                                 tv_time1.setText(mFreeTryReadDetailBean.getCreated_at());
                                 tv_time2.setText(mFreeTryReadDetailBean.getVideo_time());
                                 tv_buy.setText(mFreeTryReadDetailBean.getZl_price());
                                 tv_title2.setText(mFreeTryReadDetailBean.getName());
                                 //                                 loadMoreComment("");
                                 mComment = mFreeTryReadDetailBean.getComment();
                                 mLiuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan, mComment);
                                 recycler_liuyan.setAdapter(mLiuYanAdapter);
                                 if (mFreeTryReadDetailBean.isIsfav()) {
                                     iv_collect.setImageResource(R.drawable.xinxin);
                                 } else {
                                     iv_collect.setImageResource(R.drawable.weiguanzhuxin);
                                 }
                                 initWebView(mFreeTryReadDetailBean.getZl_h5_url());
                             }
                         }
                );
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_teacher_head = (CircleImageView) findViewById(R.id.iv_teacher_head);
        tv_teacher_name = (TextView) findViewById(R.id.tv_teacher_name);
        iv_collect = (ImageView) findViewById(R.id.iv_collect);
        iv_collect.setOnClickListener(this);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_bofang = (ImageView) findViewById(R.id.iv_bofang);
        iv_bofang.setOnClickListener(this);
        tv_title2 = (TextView) findViewById(R.id.tv_title2);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        iv_download.setOnClickListener(this);
        tv_writeliuyan = (TextView) findViewById(R.id.tv_writeliuyan);
        tv_writeliuyan.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(this);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        nestView = (NestedScrollView) findViewById(R.id.nestView);
        webview = (CustomActionWebView) findViewById(R.id.webview);
        if (getIntent().getStringExtra("type") != null) {
            tv_buy.setVisibility(View.GONE);
        }
    }

    private class LiuYanAdapter extends BaseQuickAdapter<FreeTryReadDetailBean.CommentEntity, BaseViewHolder> {

        public LiuYanAdapter(@LayoutRes int layoutResId, @Nullable List<FreeTryReadDetailBean.CommentEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final FreeTryReadDetailBean.CommentEntity item) {
            lastID = item.getId();
            final ImageView iv_dianzan = helper.getView(R.id.iv_dianzan);
            final ImageView iv_head = helper.getView(R.id.iv_head);
            if (item.isIslive()) {
                iv_dianzan.setImageResource(R.drawable.free_yizan);
            } else {
                iv_dianzan.setImageResource(R.drawable.free_dianzan);
            }
            Glide.with(MyApplication.getGloableContext()).load(item.getUser_avatar()).into(iv_head);
            helper.setText(R.id.tv_name, item.getUser_name())
                    .setText(R.id.tv_time, item.getCreated_at())
                    .setText(R.id.tv_content, item.getContent())
                    .setText(R.id.tv_dainzan_count, item.getLive() + "");
            if (item.getComment() != null && item.getComment().size() > 0) {
                helper.setVisible(R.id.ll_author, true);
                helper.setText(R.id.tv_author_content, item.getContent());
            } else {
                helper.setVisible(R.id.ll_author, false);
            }
            helper.getView(R.id.ll_dianzan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean islive = item.isIslive();
                    if (islive) {
                        nodianzan(helper.getAdapterPosition(), item.getId(), item.getLive());
                    } else {
                        dianzan(helper.getAdapterPosition(), item.getId(), item.getLive());
                    }
                    mLiuYanAdapter.notifyDataSetChanged();
                }
            });
            if (helper.getAdapterPosition() == mLiuYanAdapter.getData().size() - 1) {
                helper.getView(R.id.view_line).setVisibility(View.GONE);
            }
        }
    }

    private void dianzan(final int adapterPosition, int id, final int count) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("comment_id", id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-comment/live")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 mComment.get(adapterPosition).setIslive(true);
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mComment.get(adapterPosition).setLive(count + 1);
                                 mLiuYanAdapter.notifyDataSetChanged();
                             }
                         }
                );
    }

    private void nodianzan(final int adapterPosition, int id, final int count) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("comment_id", id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-comment/no-live")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 mComment.get(adapterPosition).setIslive(false);
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mComment.get(adapterPosition).setLive(count - 1);
                                 mLiuYanAdapter.notifyDataSetChanged();
                             }
                         }
                );
    }

    private void showBuyDialog() {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<TimeBean>get(MyContants.LXKURL + "order/expire-time")
                .tag(this)
                .headers(headers)
                .execute(new DialogCallback<TimeBean>(ZhuanLanDetail2Activity.this, TimeBean.class) {
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
                                 tv_content.setText("您将订阅从" + mTime + "时间内的《" + mFreeTryReadDetailBean.getName() + "》");
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
                                     Toast.makeText(ZhuanLanDetail2Activity.this, orderBean.getMessage(), Toast.LENGTH_SHORT).show();
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
                    .execute(new DialogCallback<BaseBean>(ZhuanLanDetail2Activity.this, BaseBean.class) {
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
                                         Toast.makeText(ZhuanLanDetail2Activity.this, message, Toast.LENGTH_SHORT).show();
                                     }

                                 }
                             }
                    );
        } else if (type.equals("2")) {//微信支付
            OkGo.<WXPayBean>post(MyContants.LXKURL + "order/pay")
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new DialogCallback<WXPayBean>(ZhuanLanDetail2Activity.this, WXPayBean.class) {
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
                    .execute(new DialogCallback<AliPayBean>(ZhuanLanDetail2Activity.this, AliPayBean.class) {
                                 @Override
                                 public void onSuccess(Response<AliPayBean> response) {
                                     int code = response.code();
                                     final AliPayBean aliPayBean = response.body();
                                     Runnable payRunnable = new Runnable() {

                                         @Override
                                         public void run() {
                                             PayTask alipay = new PayTask(ZhuanLanDetail2Activity.this);
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
                Intent intent = new Intent(ZhuanLanDetail2Activity.this, MainActivity.class);
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
                startActivity(new Intent(ZhuanLanDetail2Activity.this, MyAccountActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_collect:
                if (isCollected) {
                    noCollect();
                } else {
                    collect();
                }
                break;
            case R.id.iv_bofang:
                if (!isBofang) {
                    //                    Toast.makeText(this, "播放", Toast.LENGTH_SHORT).show();
                    iv_bofang.setImageResource(R.drawable.bofang_yellow_middle);
                    setISshow(true);
                    if (mMyBinder.isClosed()) {
                        PlayerBean playerBean = new PlayerBean(mFreeTryReadDetailBean.getT_header(),
                                mFreeTryReadDetailBean.getName(), mFreeTryReadDetailBean.getT_tag(),
                                mFreeTryReadDetailBean.getVideo_url());
                        gobofang(playerBean);
                        addListenCount(mFreeTryReadDetailBean.getId() + "");
                        MusicTypeBean musicTypeBean = new MusicTypeBean("zhuanlandetail",
                                mFreeTryReadDetailBean.getT_header(), mFreeTryReadDetailBean.getName(), mFreeTryReadDetailBean.getId()+"",
                                mFreeTryReadDetailBean.isIsfav());
                        musicTypeBean.setMsg("musicplayertype");
                        EventBus.getDefault().postSticky(musicTypeBean);
                        List<PlayerBean> list = new ArrayList<PlayerBean>();
                        list.add(playerBean);
                        MediaService.insertMusicList(list);
                        //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                        List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                        BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("zhuanlandetail", mFreeTryReadDetailBean.getId(),
                                mFreeTryReadDetailBean.getName(),
                                mFreeTryReadDetailBean.getCreated_at(), mFreeTryReadDetailBean.getVideo_url(),
                                mFreeTryReadDetailBean.getRss_count(),
                                mFreeTryReadDetailBean.getCollect_count(), mFreeTryReadDetailBean.getView_count(),
                                mFreeTryReadDetailBean.isIsfav(), mFreeTryReadDetailBean.isIsfav(),
                                mFreeTryReadDetailBean.getT_header(), mFreeTryReadDetailBean.getT_tag(),
                                mFreeTryReadDetailBean.getH5_url(), SystemClock.currentThreadTimeMillis()
                                , mFreeTryReadDetailBean.getZl_id() + "",
                                mFreeTryReadDetailBean.getParent_name(), "");
                        histroyBeanList.add(bofangHistroyBean);
                        MediaService.insertBoFangHistroyList(histroyBeanList);
                    } else {
                        mMyBinder.playMusic();
                    }
                } else {
                    //                    Toast.makeText(this, "暂停", Toast.LENGTH_SHORT).show();
                    iv_bofang.setImageResource(R.drawable.pause_yellow_middle);
                    mMyBinder.pauseMusic();
                    EventBean eventBean = new EventBean("main_pause");
                    EventBus.getDefault().postSticky(eventBean);
                    EventBean eventBean2 = new EventBean("home_pause");
                    EventBus.getDefault().postSticky(eventBean2);
                }
                isBofang = !isBofang;
                break;
            case R.id.iv_download:
                String userid = SpUtils.getString(MyApplication.getGloableContext(), "id", "");
                if (TextUtils.isEmpty(userid)) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                String created_at = mFreeTryReadDetailBean.getCreated_at();
                String[] split = created_at.split(" ");

                List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                listBean.setTypeId(mFreeTryReadDetailBean.getZl_id() + "");
                listBean.setChildId(mFreeTryReadDetailBean.getId() + "");
                listBean.setName(mFreeTryReadDetailBean.getName());
                listBean.setVideoTime(mFreeTryReadDetailBean.getVideo_time());
                listBean.setDate(split[0]);
                listBean.setTime(split[0]);
                listBean.setVideoUrl(mFreeTryReadDetailBean.getVideo_url());
                listBean.setTxtUrl("");
                //                listBean.setTxtUrl(mFreeTryReadDetailBean.getTxt_url());
                listBean.setIconUrl(mFreeTryReadDetailBean.getT_header());
                listBean.settName(mFreeTryReadDetailBean.getT_name());
                listBean.setParentName(mFreeTryReadDetailBean.getParent_name());
                listBean.setH5_url(mFreeTryReadDetailBean.getH5_url());
                listBean.setGood_count(mFreeTryReadDetailBean.getRss_count());
                listBean.setCollect_count(mFreeTryReadDetailBean.getCollect_count());
                listBean.setView_count(mFreeTryReadDetailBean.getView_count());
                listBean.setDianzan(mFreeTryReadDetailBean.isIsfav());
                listBean.setCollected(mFreeTryReadDetailBean.isIsfav());
                list.add(listBean);
                if (MyUtils.isHaveFile("zhuanlan",mFreeTryReadDetailBean.getName() + mFreeTryReadDetailBean.getZl_id() + "_" + mFreeTryReadDetailBean.getId() + ".mp3")){
                    Toast.makeText(ZhuanLanDetail2Activity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                    return;
                }
                DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                        "zhuanlan", mFreeTryReadDetailBean.getZl_id() + "", getIntent().getStringExtra("title"), mFreeTryReadDetailBean.getT_header(),
                        mFreeTryReadDetailBean.getT_name(), mFreeTryReadDetailBean.getT_tag(), "1", list);
                DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(mFreeTryReadDetailBean.getChildId(), mFreeTryReadDetailBean.getZl_id(), -3,
                        mFreeTryReadDetailBean.getName(), mFreeTryReadDetailBean.getVideo_time(), split[0], split[0],
                        mFreeTryReadDetailBean.getVideo_url(), mFreeTryReadDetailBean.getTxt_url(), mFreeTryReadDetailBean.getT_header());
                DownUtils.add(DownLoadListBean);*/

                GetRequest<File> request = OkGo.<File>get(mFreeTryReadDetailBean.getVideo_url());
                OkDownload.request(mFreeTryReadDetailBean.getZl_id() + "_" + mFreeTryReadDetailBean.getId(), request)
                        .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download")
                        .fileName(mFreeTryReadDetailBean.getName() + mFreeTryReadDetailBean.getZl_id() + "_" + mFreeTryReadDetailBean.getId() + ".mp3")
                        .extra3(downLoadListsBean)
                        .save()
                        .register(new LogDownloadListener())//当前任务的回调监听
                        .start();
                EventBean eventBean = new EventBean("number");
                EventBus.getDefault().postSticky(eventBean);
                break;
            case R.id.tv_writeliuyan:
                Intent intent = new Intent(this, LiuYanActivity.class);
                intent.putExtra("teacher_id", mFreeTryReadDetailBean.getTeacher_id() + "");
                intent.putExtra("type", "zhuanlandetail");
                intent.putExtra("xiaoke_id", mFreeTryReadDetailBean.getId() + "");
                startActivity(intent);
                break;
            case R.id.tv_buy:
                showBuyDialog();
                break;
        }
    }

    private void addListenCount(String id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("type", "zl");
        OkGo.<BaseBean>post(MyContants.LXKURL + "views")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                    }
                });
    }

    private void collect() {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", mId);
        params.put("fav_type", "1");
        OkGo.<DianZanbean>post(MyContants.LXKURL + "zl/favorite")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 iv_collect.setImageResource(R.drawable.xinxin);
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 isCollected = !isCollected;
                             }
                         }
                );
    }

    private void noCollect() {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", mId);
        params.put("fav_type", "0");
        OkGo.<DianZanbean>post(MyContants.LXKURL + "zl/favorite")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 iv_collect.setImageResource(R.drawable.weiguanzhuxin);
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 isCollected = !isCollected;
                             }
                         }
                );
    }

}
