package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.MyAccountActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.db.StudyTimeBean;
import www.knowledgeshare.com.knowledgeshare.db.StudyTimeUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.FreeTryReadDetailBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.OrderBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.TimeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.web.ActionSelectListener;
import www.knowledgeshare.com.knowledgeshare.fragment.home.web.CustomActionWebView;
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
    }

    private void setStudyTime() {
        pretime= SystemClock.currentThreadTimeMillis();
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
            lasttime+=oneTime;
            StudyTimeUtils.updateTime("zhuanlandetail", mId, lasttime);
        }
    }

    private void setTimeRecord() {
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
        OkGo.<FreeTryReadDetailBean>post(MyContants.LXKURL + "zl/trials-show")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<FreeTryReadDetailBean>(ZhuanLanDetail2Activity.this, FreeTryReadDetailBean.class) {
                             @Override
                             public void onSuccess(Response<FreeTryReadDetailBean> response) {
                                 int code = response.code();
                                 mFreeTryReadDetailBean = response.body();
                                 Glide.with(ZhuanLanDetail2Activity.this).load(mFreeTryReadDetailBean.getT_header()).into(iv_teacher_head);
                                 tv_teacher_name.setText(mFreeTryReadDetailBean.getT_name());
                                 tv_time1.setText(mFreeTryReadDetailBean.getCreated_at());
                                 tv_time2.setText(mFreeTryReadDetailBean.getVideo_time());
                                 tv_buy.setText(mFreeTryReadDetailBean.getZl_price());
                                 tv_title2.setText(mFreeTryReadDetailBean.getName());
                                 //                                 loadMoreComment("");
                                 mComment = mFreeTryReadDetailBean.getComment();
                                 mLiuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan, mComment);
                                 recycler_liuyan.setAdapter(mLiuYanAdapter);
                                 if (mFreeTryReadDetailBean.isfav()) {
                                     iv_collect.setImageResource(R.drawable.xinxin);
                                 } else {
                                     iv_collect.setImageResource(R.drawable.weiguanzhuxin);
                                 }
                                 initWebView(mFreeTryReadDetailBean.getH5_url());
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
            Glide.with(mContext).load(item.getUser_avatar()).into(iv_head);
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
                        mComment.get(helper.getAdapterPosition()).setIslive(false);
                        nodianzan(helper.getAdapterPosition(), item.getId(), item.getLive());
                    } else {
                        mComment.get(helper.getAdapterPosition()).setIslive(true);
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
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mComment.get(adapterPosition).setLive(count + 1);
                                 mLiuYanAdapter.notifyDataSetChanged();
                             }
                         }
                );
    }

    private void nodianzan(final int adapterPosition, int id, final int count) {
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
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mComment.get(adapterPosition).setLive(count - 1);
                                 mLiuYanAdapter.notifyDataSetChanged();
                             }
                         }
                );
    }

    private void showBuyDialog() {
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
                                 int code = response.code();
                                 OrderBean orderBean = response.body();
                                 String order_sn = orderBean.getOrder_sn();
                                 goPay(order_sn, type);
                             }
                         }
                );
    }

    private void goPay(String order_sn, String type) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("order_sn", order_sn);
        params.put("type", type);
        params.put("from", "android");
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
                    iv_collect.setImageResource(R.drawable.weiguanzhuxin);
                    noCollect();
                } else {
                    iv_collect.setImageResource(R.drawable.xinxin);
                    collect();
                }
                isCollected = !isCollected;
                break;
            case R.id.iv_bofang:
                if (!isBofang) {
                    //                    Toast.makeText(this, "播放", Toast.LENGTH_SHORT).show();
                    iv_bofang.setImageResource(R.drawable.bofang_yellow_middle);
                    setISshow(true);
                    if (mMyBinder.isClosed()) {
                        PlayerBean playerBean = new PlayerBean(mFreeTryReadDetailBean.getT_header(),
                                mFreeTryReadDetailBean.getName(), mFreeTryReadDetailBean.getT_tag(), mFreeTryReadDetailBean.getVideo_url());
                        gobofang(playerBean);
                        addListenCount(mFreeTryReadDetailBean.getId() + "");
                        MusicTypeBean musicTypeBean = new MusicTypeBean("zhuanlandetail",
                                mFreeTryReadDetailBean.getT_header(), mFreeTryReadDetailBean.getName(), mId,
                                mFreeTryReadDetailBean.isfav());
                        musicTypeBean.setMsg("musicplayertype");
                        EventBus.getDefault().postSticky(musicTypeBean);
                        List<PlayerBean> list = new ArrayList<PlayerBean>();
                        list.add(playerBean);
                        MediaService.insertMusicList(list);
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
                listBean.setTxtUrl(mFreeTryReadDetailBean.getTxt_url());
                listBean.setIconUrl(mFreeTryReadDetailBean.getT_header());
                list.add(listBean);
                DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                        "zhuanlan", mFreeTryReadDetailBean.getZl_id() + "", getIntent().getStringExtra("title"), mFreeTryReadDetailBean.getT_header(),
                        mFreeTryReadDetailBean.getT_name(), mFreeTryReadDetailBean.getT_tag(), "1", list);
                DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(mFreeTryReadDetailBean.getId(), mFreeTryReadDetailBean.getZl_id(), -3,
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
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

    private void noCollect() {
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
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

}
