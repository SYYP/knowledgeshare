package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.BofangjinduBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.db.JinduUtils;
import www.knowledgeshare.com.knowledgeshare.db.LookBean;
import www.knowledgeshare.com.knowledgeshare.db.LookUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.AliPayBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.CommentMoreBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.OrderBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.PayResult;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.SoftMusicDetailBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.WXPayBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

import static www.knowledgeshare.com.knowledgeshare.R.id.bottom_ll;

public class SoftMusicDetailActivity extends UMShareActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_beijing;
    private TextView tv_download;
    private TextView tv_search;
    private TextView tv_share;
    private TextView tv_guanzhu;
    private TextView tv_dianzan_count;
    private TextView tv_teacher_intro;
    private RecyclerView recycler_free;
    private TextView tv_shiyirenqun;
    private TextView tv_readxuzhi;
    private TextView tv_writeliuyan;
    private TextView tv_read;
    private RecyclerView recycler_liuyan;
    private LinearLayout activity_free;
    private TextView tv_tryread;
    private TextView tv_buy;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private boolean isDianzan;
    private boolean isGuanzhu = true;
    private boolean isZan = true;
    private ImageView iv_guanzhu, iv_dianzan;
    private NestedScrollView nestView;
    private boolean mIsCollected;
    private boolean mDianzan;
    private SoftMusicDetailBean.TeacherEntity mTeacher;
    private int mTeacher_zan_count;
    private List<SoftMusicDetailBean.ChildEntity> mChild;
    private LieBiaoAdapter mLieBiaoAdapter;
    private LiuYanAdapter mLiuYanAdapter;
    private List<CommentMoreBean.DataEntity> mComment;
    private SoftMusicDetailBean mMusicDetailBean;
    private SpringView springview;
    private int lastID;
    private TextView mTv_collect;
    private TextView mTv_dianzan;
    private boolean isRefreshing;
    private String mId;
    private BaseDialog mNetDialog;
    private TextView mTv_content;
    private Intent intent;
    private LinearLayout bottomLl;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private String WX_APPID = "wxf33afce9142929dc";// 微信appid
    private int size = 0;
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
                        Toast.makeText(SoftMusicDetailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //                        finish();
                        showPaySuccessDialog();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        /*
                        "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，
                        最终交易是否成功以服务端异步通知为准（小概率状态）
                         */
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(SoftMusicDetailActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(SoftMusicDetailActivity.this, "支付宝支付取消", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private boolean nowifiallowdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_music_detail);
        initView();
        initDialog();
        initData();
        initListener();
        initNETDialog();
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
        if (eventBean.getMsg().equals("refresh_xk")) {
            String id = eventBean.getMsg2();
            for (int i = 0; i < mChild.size(); i++) {
                String id1 = mChild.get(i).getId() + "";
                if (id.equals(id1)) {
                    int measuredHeight = tv_teacher_intro.getMeasuredHeight();
                    nestView.scrollTo(0, measuredHeight + MyUtils.dip2px(this, 220 + i * 50));//胡乱弄得
                    return;
                }
            }
        }
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
        mTv_content = mNetDialog.getView(R.id.tv_content);
    }

    private void gobofang(final PlayerBean playerBean) {
        int apnType = NetWorkUtils.getAPNType(this);
        if (apnType == 0) {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
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
                        SpUtils.putBoolean(SoftMusicDetailActivity.this, "nowifiallowlisten", true);
                    }
                });
                mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNetDialog.dismiss();
                    }
                });
            }
        } else if (NetWorkUtils.isMobileConnected(SoftMusicDetailActivity.this)) {
            Toast.makeText(this, "wifi不可用呢~", Toast.LENGTH_SHORT).show();
        } else {
            playerBean.setMsg("refreshplayer");
            EventBus.getDefault().postSticky(playerBean);
            mMyBinder.setMusicUrl(playerBean.getVideo_url());
            mMyBinder.playMusic(playerBean);
            ClickPopShow();
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
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        springview.onFinishFreshAndLoad();
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {
                //                loadMoreComment(lastID + "");
            }
        });
        springview.setHeader(new MyHeader(this));
        //        springview.setFooter(new MyFooter(this));
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(this);
        tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);
        tv_guanzhu.setOnClickListener(this);
        tv_dianzan_count = (TextView) findViewById(R.id.tv_dianzan_count);
        tv_dianzan_count.setOnClickListener(this);
        tv_teacher_intro = (TextView) findViewById(R.id.tv_teacher_intro);
        recycler_free = (RecyclerView) findViewById(R.id.recycler_free);
        tv_shiyirenqun = (TextView) findViewById(R.id.tv_shiyirenqun);
        tv_readxuzhi = (TextView) findViewById(R.id.tv_readxuzhi);
        tv_writeliuyan = (TextView) findViewById(R.id.tv_writeliuyan);
        tv_writeliuyan.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        activity_free = (LinearLayout) findViewById(R.id.activity_free);
        tv_tryread = (TextView) findViewById(R.id.tv_tryread);
        tv_tryread.setOnClickListener(this);
        tv_read = (TextView) findViewById(R.id.tv_read);
        tv_read.setOnClickListener(this);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(this);
        iv_guanzhu = (ImageView) findViewById(R.id.iv_guanzhu);
        iv_guanzhu.setOnClickListener(this);
        iv_dianzan = (ImageView) findViewById(R.id.iv_dianzan);
        iv_dianzan.setOnClickListener(this);
        bottomLl = (LinearLayout) findViewById(bottom_ll);
        nestView = (NestedScrollView) findViewById(R.id.nestView);
        recycler_free.setLayoutManager(new LinearLayoutManager(this));
        recycler_free.setNestedScrollingEnabled(false);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        springview = (SpringView) findViewById(R.id.springview);
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    private void initData() {
        if (getIntent().getStringExtra("type") != null) {
            bottomLl.setVisibility(View.GONE);
            SpringView.MarginLayoutParams layoutParams = (SpringView.MarginLayoutParams) springview.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
        }

        mId = getIntent().getStringExtra("id");
        HttpParams params = new HttpParams();
        params.put("id", mId);
        params.put("userid", SpUtils.getString(this, "id", ""));
        if (getIntent().getStringExtra("type") != null) {
            params.put("type", "2");
        } else {
            params.put("type", "1");
        }
        OkGo.<SoftMusicDetailBean>post(MyContants.LXKURL + "xk/show")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<SoftMusicDetailBean>(SoftMusicDetailActivity.this, SoftMusicDetailBean.class) {
                             @Override
                             public void onSuccess(Response<SoftMusicDetailBean> response) {
                                 int code = response.code();
                                 mMusicDetailBean = response.body();
                                 RequestOptions options = new RequestOptions();
                                 options.error(R.drawable.default_banner);
                                 options.placeholder(R.drawable.default_banner);
                                 Glide.with(MyApplication.getGloableContext()).load(mMusicDetailBean.getImgurl()).apply(options).into(iv_beijing);
                                 ViewGroup.LayoutParams layoutParams = iv_beijing.getLayoutParams();
                                 int width = MyUtils.getScreenWidth(SoftMusicDetailActivity.this);
                                 layoutParams.height = width * 7 / 15;
                                 iv_beijing.setLayoutParams(layoutParams);
                                 mTeacher = mMusicDetailBean.getTeacher();
                                 tv_teacher_intro.setText(mTeacher.getT_introduce());
                                 isGuanzhu = mTeacher.isIsfollow();
                                 if (isGuanzhu) {
                                     iv_guanzhu.setImageResource(R.drawable.free_guanzhu);
                                     tv_guanzhu.setText("已关注");
                                 } else {
                                     iv_guanzhu.setImageResource(R.drawable.free_quxiaoguanzhu);
                                     tv_guanzhu.setText("关注");
                                 }
                                 isZan = mTeacher.isIslive();
                                 if (isZan) {
                                     iv_dianzan.setImageResource(R.drawable.free_yizan);
                                 } else {
                                     iv_dianzan.setImageResource(R.drawable.free_dianzan);
                                 }
                                 tv_buy.setText(mMusicDetailBean.getXk_price() + "  立即购买");
                                 mTeacher_zan_count = mTeacher.getT_live();
                                 tv_dianzan_count.setText(mTeacher_zan_count + "");
                                 tv_shiyirenqun.setText(mMusicDetailBean.getXk_suitable());
                                 tv_readxuzhi.setText(mMusicDetailBean.getXk_rss());
                                 if (mMusicDetailBean.is_buy()) {
                                     bottomLl.setVisibility(View.GONE);
                                     tv_read.setVisibility(View.GONE);
                                     LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) springview.getLayoutParams();
                                     layoutParams1.setMargins(0, 0, 0, 0);
                                     springview.setLayoutParams(layoutParams1);
                                     springview.requestLayout();
                                 } else {
                                     bottomLl.setVisibility(View.VISIBLE);
                                     tv_read.setVisibility(View.GONE);
                                 }

                                 mChild = mMusicDetailBean.getChild();
                                 if (mChild==null || mChild.size()==0 ||mChild.get(0).getIs_try()!=1) {
                                     tv_tryread.setBackgroundColor(getResources().getColor(R.color.tab_text_normal_color));
                                     tv_tryread.setTextColor(getResources().getColor(R.color.textcolor));
                                     tv_tryread.setClickable(false);
                                 }
                                 mLieBiaoAdapter = new LieBiaoAdapter(R.layout.item_like_liebiao, mChild);
                                 recycler_free.setAdapter(mLieBiaoAdapter);
                                 mLieBiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         if (mLieBiaoAdapter.getData().get(position).getIs_try() != 1
                                                 && getIntent().getStringExtra("type") == null
                                                 && !mMusicDetailBean.is_buy()) {
                                             showIsBuyDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                                         } else {
                                             setISshow(true);
                                             SoftMusicDetailBean.ChildEntity item = mChild.get(position);
                                             item.setChecked(true);
                                             //刷新小型播放器
                                             PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getName(), item.getParent_name(), item.getVideo_url(), position);
                                             gobofang(playerBean);
                                             addListenCount(item.getId() + "");
                                             //设置进入播放主界面的数据
                                             List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                                             for (int i = 0; i < mChild.size(); i++) {
                                                 SoftMusicDetailBean.ChildEntity childEntity = mChild.get(i);
                                                 if (childEntity.getIs_try() == 1 || mMusicDetailBean.is_buy()) {
                                                     MusicTypeBean musicTypeBean = new MusicTypeBean("softmusicdetail",
                                                             childEntity.getT_header(), childEntity.getName(), childEntity.getId() + "", childEntity.isIsfav());
                                                     musicTypeBean.setMsg("musicplayertype");
                                                     musicTypeBeanList.add(musicTypeBean);
                                                 }
                                             }
                                             MediaService.insertMusicTypeList(musicTypeBeanList);
                                             //加入默认的播放列表
                                             List<PlayerBean> list = new ArrayList<PlayerBean>();
                                             for (int i = 0; i < mChild.size(); i++) {
                                                 SoftMusicDetailBean.ChildEntity entity = mChild.get(i);
                                                 if (entity.getIs_try() == 1 || mMusicDetailBean.is_buy()) {
                                                     PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getName(), entity.getParent_name(),
                                                             entity.getVideo_url(), position);
                                                     list.add(playerBean1);
                                                 }
                                             }
                                             MediaService.insertMusicList(list);
                                             //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                                             List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                                             for (int i = 0; i < mChild.size(); i++) {
                                                 SoftMusicDetailBean.ChildEntity entity = mChild.get(i);
                                                 if (entity.getIs_try() == 1 || mMusicDetailBean.is_buy()) {
                                                     BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("softmusicdetail", entity.getId(), entity.getName(),
                                                             entity.getCreated_at(), entity.getVideo_url(), entity.getGood_count(),
                                                             entity.getCollect_count(), entity.getView_count(), entity.isIslive(), entity.isIsfav()
                                                             , entity.getT_header(), entity.getParent_name(), entity.getShare_h5_url()
                                                             , System.currentTimeMillis(), mMusicDetailBean.getXk_class_id() + "",
                                                             entity.getParent_name(), entity.getTxt_url());
                                                     histroyBeanList.add(bofangHistroyBean);
                                                 }
                                             }
                                             MediaService.insertBoFangHistroyList(histroyBeanList);
                                         }
                                     }
                                 });
                                 if (!isRefreshing) {
                                     loadMoreComment("");
                                 }
                                 isRefreshing = false;
                                 springview.onFinishFreshAndLoad();
                                 if (!LookUtils.isInserted(mMusicDetailBean.getXk_name())) {
                                     LookUtils.add(new LookBean(Integer.parseInt(mId), "xiaoke", mMusicDetailBean.getXk_name(),
                                             mMusicDetailBean.getTeacher().getT_name(),
                                             mMusicDetailBean.getXk_teacher_tags(), mMusicDetailBean.getXk_price()
                                             , System.currentTimeMillis()));
                                 } else {
                                     LookUtils.updateTime(System.currentTimeMillis(),
                                             mMusicDetailBean.getXk_name(), "xiaoke");
                                 }
                             }
                         }
                );
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mChild != null) {
            for (int i = 0; i < mChild.size(); i++) {
                if (mChild.get(i).isChecked()) {
                    size++;
                }
            }
            if (mChild.size() > 0) {
                double jindu = new BigDecimal((float) size / mChild.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                Logger.e(jindu + "");
                BofangjinduBean bean = new BofangjinduBean(mMusicDetailBean.getId() + "", jindu);
                JinduUtils.add(bean);
                EventBean eventBean = new EventBean("jindu");
                EventBus.getDefault().postSticky(eventBean);
            }
        }
    }

    private void addListenCount(String id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("type", "xk");
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

    private void loadMoreComment(String after) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(this, "id", ""));
        params.put("after", after);
        OkGo.<CommentMoreBean>post(MyContants.LXKURL + "xk/more-comment")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<CommentMoreBean>(CommentMoreBean.class) {
                    @Override
                    public void onSuccess(Response<CommentMoreBean> response) {
                        int code = response.code();
                        CommentMoreBean commentMoreBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            List<CommentMoreBean.DataEntity> data = commentMoreBean.getData();
                            if (mComment == null || mComment.size() == 0) {
                                mComment = data;
                                mLiuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan, mComment);
                                recycler_liuyan.setAdapter(mLiuYanAdapter);
                            } else {
                                if (data == null || data.size() == 0) {
                                    Toast.makeText(SoftMusicDetailActivity.this, "已无更多评论", Toast.LENGTH_SHORT).show();
                                } else {
                                    mComment.addAll(data);
                                    mLiuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan, mComment);
                                    recycler_liuyan.setAdapter(mLiuYanAdapter);
                                    //                                    mLiuYanAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                        }
                    }
                });
        springview.onFinishFreshAndLoad();
    }

    private void showIsBuyDialog(int grary, int animationStyle) {
        mDialog = mBuilder.setViewId(R.layout.dialog_isbuy)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(MyUtils.dip2px(this, 250), LinearLayout.LayoutParams.WRAP_CONTENT)
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
        mDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showBuyDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
            }
        });
    }

    private void showBuyDialog(int grary, int animationStyle) {
        mDialog = mBuilder.setViewId(R.layout.dialog_buy)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
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
                showChongzhiDialog();
            }
        });
    }

    private class LieBiaoAdapter extends BaseQuickAdapter<SoftMusicDetailBean.ChildEntity, BaseViewHolder> {

        public LieBiaoAdapter(@LayoutRes int layoutResId, @Nullable List<SoftMusicDetailBean.ChildEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final SoftMusicDetailBean.ChildEntity item) {
            TextView tv_trylisten = helper.getView(R.id.tv_trylisten);
            if (getIntent().getStringExtra("type") != null || mMusicDetailBean.is_buy()) {
                tv_trylisten.setVisibility(View.INVISIBLE);
                helper.setVisible(R.id.iv_wengao, true);
            } else {
                if (item.getIs_try() == 1) {
                    tv_trylisten.setVisibility(View.VISIBLE);
                } else {
                    tv_trylisten.setVisibility(View.INVISIBLE);
                }
                ImageView iv_wengao = helper.getView(R.id.iv_wengao);
                if (item.getIs_try() == 1) {
                    iv_wengao.setVisibility(View.VISIBLE);
                } else {
                    iv_wengao.setVisibility(View.INVISIBLE);
                }
            }
            String created_at = item.getCreated_at();
            String[] split = created_at.split(" ");
            helper.setText(R.id.tv_name, item.getName())
                    .setText(R.id.tv_time, split[0] + "发布")
                    .setText(R.id.tv_look_count, item.getIs_view() == 0 ? item.getView_count() + "" : item.getView_count_true() + "")
                    .setText(R.id.tv_collect_count, item.getIs_collect() == 0 ? item.getCollect_count() + "" : item.getCollect_count_true() + "")
                    .setText(R.id.tv_dianzan_count, item.getIs_good() == 0 ? item.getGood_count() + "" : item.getGood_count_true() + "");
            helper.getView(R.id.iv_dian).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mMusicDetailBean.is_buy()) {
                        showListDialog(helper.getAdapterPosition(), item.isIsfav(), item.isIslive(), item.getId());
                    } else {
                        if (item.getIs_try() == 1) {
                            showListDialog(helper.getAdapterPosition(), item.isIsfav(), item.isIslive(), item.getId());
                        } else {
                            showIsBuyDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                        }
                    }
                }
            });
            helper.getView(R.id.iv_wengao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SoftMusicDetailActivity.this, WenGaoActivity.class);
                    intent.putExtra("type", "softmusicdetail");
                    //                    intent.putExtra("t_name", mTeacher.getT_name());
                    //                    intent.putExtra("t_head", mTeacher.getT_header());
                    //                    intent.putExtra("video_name", item.getVideo_old_name());
                    //                    intent.putExtra("teacher_id", mMusicDetailBean.getXk_teacher_id() + "");
                    intent.putExtra("id", item.getId() + "");
                    startActivity(intent);
                }
            });
            if (helper.getAdapterPosition() <= 8) {
                helper.setText(R.id.tv_order, "0" + (helper.getAdapterPosition() + 1));
            } else {
                helper.setText(R.id.tv_order, "" + (helper.getAdapterPosition() + 1));
            }
        }
    }

    private class LiuYanAdapter extends BaseQuickAdapter<CommentMoreBean.DataEntity, BaseViewHolder> {

        public LiuYanAdapter(@LayoutRes int layoutResId, @Nullable List<CommentMoreBean.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final CommentMoreBean.DataEntity item) {
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
                helper.setText(R.id.tv_author_content, item.getComment().get(0).getContent());
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
                                 Toast.makeText(SoftMusicDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                                 Toast.makeText(SoftMusicDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mComment.get(adapterPosition).setLive(count - 1);
                                 mLiuYanAdapter.notifyDataSetChanged();
                             }
                         }
                );
    }

    private void showShareDialog(final String root, final int adapterPosition) {
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
        final SoftMusicDetailBean.ChildEntity entity = mLieBiaoAdapter.getData().get(adapterPosition);
        mDialog.getView(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mMusicDetailBean.getH5_url(), mMusicDetailBean.getXk_name(),
                            mMusicDetailBean.getImgurl(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.WEIXIN);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.WEIXIN);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mMusicDetailBean.getH5_url(), mMusicDetailBean.getXk_name(),
                            mMusicDetailBean.getImgurl(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mMusicDetailBean.getH5_url(), mMusicDetailBean.getXk_name(),
                            mMusicDetailBean.getImgurl(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.QZONE);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.QZONE);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mMusicDetailBean.getH5_url(), mMusicDetailBean.getXk_name(),
                            mMusicDetailBean.getImgurl(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.QQ);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.QQ);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mMusicDetailBean.getH5_url(), mMusicDetailBean.getXk_name(),
                            mMusicDetailBean.getImgurl(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.SINA);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", SoftMusicDetailActivity.this, SHARE_MEDIA.SINA);
                }
                mDialog.dismiss();
            }
        });
    }

    private void showListDialog(final int adapterPosition, boolean isfav, boolean islive, final int id) {
        mDialog = mBuilder.setViewId(R.layout.dialog_free)
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
        mDialog.getView(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showShareDialog("list", adapterPosition);
            }
        });
        mTv_collect = mDialog.getView(R.id.tv_collect);
        mIsCollected = isfav;
        if (mIsCollected) {
            Drawable drawable = getResources().getDrawable(R.drawable.collect_shixin);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTv_collect.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.bofanglist_collect);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTv_collect.setCompoundDrawables(null, drawable, null, null);
        }
        mTv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCollect(adapterPosition, id);
            }
        });
        mTv_dianzan = mDialog.getView(R.id.tv_dianzan);
        mDianzan = islive;
        if (mDianzan) {
            Drawable drawable = getResources().getDrawable(R.drawable.dianzan_shixin);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.dianzan_yellow_big);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
        }
        mTv_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDianzan(adapterPosition, id);
            }
        });
        mDialog.getView(R.id.tv_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = SpUtils.getString(MyApplication.getGloableContext(), "id", "");
                if (TextUtils.isEmpty(userid)) {
                    startActivity(new Intent(SoftMusicDetailActivity.this, LoginActivity.class));
                    return;
                }
                int apnType = NetWorkUtils.getAPNType(SoftMusicDetailActivity.this);
                if (apnType == 0) {
                    Toast.makeText(SoftMusicDetailActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                } else if (apnType == 2 || apnType == 3 || apnType == 4) {
                    nowifiallowdown = SpUtils.getBoolean(SoftMusicDetailActivity.this, "nowifiallowdown", false);
                    if (!nowifiallowdown) {
                        mTv_content.setText("当前无WiFi，是否允许用流量下载");
                        mNetDialog.show();
                        mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mNetDialog.dismiss();
                                return;
                            }
                        });
                        mNetDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//记住用户允许流量下载
                                SpUtils.putBoolean(SoftMusicDetailActivity.this, "nowifiallowdown", true);
                                SoftMusicDetailBean.ChildEntity childEntity = mChild.get(adapterPosition);
                                String created_at = childEntity.getCreated_at();
                                String[] split = created_at.split(" ");
                                List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                listBean.setTypeId(childEntity.getXk_id() + "");
                                listBean.setChildId(childEntity.getId() + "");
                                listBean.setName(childEntity.getName());
                                listBean.setVideoTime(childEntity.getVideo_time());
                                listBean.setDate(split[0]);
                                listBean.setTime(split[1]);
                                listBean.setVideoUrl(childEntity.getVideo_url());
                                listBean.setTxtUrl(childEntity.getTxt_url());
                                listBean.setIconUrl(childEntity.getT_header());
                                listBean.settName(childEntity.getT_name());
                                listBean.setParentName(childEntity.getParent_name());
                                listBean.setH5_url(childEntity.getShare_h5_url());
                                listBean.setGood_count(childEntity.getGood_count());
                                listBean.setCollect_count(childEntity.getCollect_count());
                                listBean.setView_count(childEntity.getView_count());
                                listBean.setDianzan(childEntity.isIslive());
                                listBean.setCollected(childEntity.isIsfav());
                                list.add(listBean);
                                if (MyUtils.isHaveFile("xiaoke", childEntity.getName() + childEntity.getXk_id() + "_" + childEntity.getId() + ".mp3")) {
                                    Toast.makeText(SoftMusicDetailActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                        "xiaoke", mMusicDetailBean.getXk_class_id() + "", childEntity.getParent_name(), childEntity.getT_header(),
                                        childEntity.getT_name(), childEntity.getT_tag(), mChild.size() + "", list);
                                DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(childEntity.getChildId(), childEntity.getXk_id(),
                        childEntity.getName(), childEntity.getVideo_time(), split[0], split[1],
                        childEntity.getVideo_url(), childEntity.getTxt_url(), childEntity.getT_header());
                DownUtils.add(DownLoadListBean);*/
                                GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                                OkDownload.request(childEntity.getXk_id() + "_" + childEntity.getId(), request)
                                        .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download")
                                        .fileName(childEntity.getName() + childEntity.getXk_id() + "_" + childEntity.getId() + ".mp3")
                                        .extra3(downLoadListsBean)
                                        .save()
                                        .register(new LogDownloadListener())//当前任务的回调监听
                                        .start();
                                OkGo.<File>get(childEntity.getTxt_url())
                                        .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download"
                                                , childEntity.getXk_id() + "-" + childEntity.getId() + childEntity.getName() + ".txt") {
                                            @Override
                                            public void onSuccess(Response<File> response) {
                                                int code = response.code();
                                                if (code >= 200 && code <= 204) {
                                                    Logger.e("文稿下载完成");
                                                }
                                            }
                                        });
                                Toast.makeText(MyApplication.getInstance(), "已加入下载列表", Toast.LENGTH_SHORT).show();
                                EventBean eventBean = new EventBean("number");
                                EventBus.getDefault().postSticky(eventBean);
                                mDialog.dismiss();
                                mNetDialog.dismiss();
                            }
                        });
                    } else {
                        SoftMusicDetailBean.ChildEntity childEntity = mChild.get(adapterPosition);
                        String created_at = childEntity.getCreated_at();
                        String[] split = created_at.split(" ");
                        List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                        DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                        listBean.setTypeId(childEntity.getXk_id() + "");
                        listBean.setChildId(childEntity.getId() + "");
                        listBean.setName(childEntity.getName());
                        listBean.setVideoTime(childEntity.getVideo_time());
                        listBean.setDate(split[0]);
                        listBean.setTime(split[1]);
                        listBean.setVideoUrl(childEntity.getVideo_url());
                        listBean.setTxtUrl(childEntity.getTxt_url());
                        listBean.setIconUrl(childEntity.getT_header());
                        listBean.settName(childEntity.getT_name());
                        listBean.setParentName(childEntity.getParent_name());
                        listBean.setH5_url(childEntity.getShare_h5_url());
                        listBean.setGood_count(childEntity.getGood_count());
                        listBean.setCollect_count(childEntity.getCollect_count());
                        listBean.setView_count(childEntity.getView_count());
                        listBean.setDianzan(childEntity.isIslive());
                        listBean.setCollected(childEntity.isIsfav());
                        list.add(listBean);
                        if (MyUtils.isHaveFile("xiaoke", childEntity.getName() + childEntity.getXk_id() + "_" + childEntity.getId() + ".mp3")) {
                            Toast.makeText(SoftMusicDetailActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                "xiaoke", mMusicDetailBean.getXk_class_id() + "", childEntity.getParent_name(), childEntity.getT_header(),
                                childEntity.getT_name(), childEntity.getT_tag(), mChild.size() + "", list);
                        DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(childEntity.getChildId(), childEntity.getXk_id(),
                        childEntity.getName(), childEntity.getVideo_time(), split[0], split[1],
                        childEntity.getVideo_url(), childEntity.getTxt_url(), childEntity.getT_header());
                DownUtils.add(DownLoadListBean);*/
                        GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                        OkDownload.request(childEntity.getXk_id() + "_" + childEntity.getId(), request)
                                .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download")
                                .fileName(childEntity.getName() + childEntity.getXk_id() + "_" + childEntity.getId() + ".mp3")
                                .extra3(downLoadListsBean)
                                .save()
                                .register(new LogDownloadListener())//当前任务的回调监听
                                .start();
                        OkGo.<File>get(childEntity.getTxt_url())
                                .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download"
                                        , childEntity.getXk_id() + "-" + childEntity.getId() + childEntity.getName() + ".txt") {
                                    @Override
                                    public void onSuccess(Response<File> response) {
                                        int code = response.code();
                                        if (code >= 200 && code <= 204) {
                                            Logger.e("文稿下载完成");
                                        }
                                    }
                                });
                        Toast.makeText(MyApplication.getInstance(), "已加入下载列表", Toast.LENGTH_SHORT).show();
                        EventBean eventBean = new EventBean("number");
                        EventBus.getDefault().postSticky(eventBean);
                        mDialog.dismiss();
                        mNetDialog.dismiss();
                    }
                } else {
                    SoftMusicDetailBean.ChildEntity childEntity = mChild.get(adapterPosition);
                    String created_at = childEntity.getCreated_at();
                    String[] split = created_at.split(" ");
                    List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                    DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                    listBean.setTypeId(childEntity.getXk_id() + "");
                    listBean.setChildId(childEntity.getId() + "");
                    listBean.setName(childEntity.getName());
                    listBean.setVideoTime(childEntity.getVideo_time());
                    listBean.setDate(split[0]);
                    listBean.setTime(split[1]);
                    listBean.setVideoUrl(childEntity.getVideo_url());
                    listBean.setTxtUrl(childEntity.getTxt_url());
                    listBean.setIconUrl(childEntity.getT_header());
                    listBean.settName(childEntity.getT_name());
                    listBean.setParentName(childEntity.getParent_name());
                    listBean.setH5_url(childEntity.getShare_h5_url());
                    listBean.setGood_count(childEntity.getGood_count());
                    listBean.setCollect_count(childEntity.getCollect_count());
                    listBean.setView_count(childEntity.getView_count());
                    listBean.setDianzan(childEntity.isIslive());
                    listBean.setCollected(childEntity.isIsfav());
                    list.add(listBean);
                    if (MyUtils.isHaveFile("xiaoke", childEntity.getName() + childEntity.getXk_id() + "_" + childEntity.getId() + ".mp3")) {
                        Toast.makeText(SoftMusicDetailActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                            "xiaoke", mMusicDetailBean.getXk_class_id() + "", childEntity.getParent_name(), childEntity.getT_header(),
                            childEntity.getT_name(), childEntity.getT_tag(), mChild.size() + "", list);
                    DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(childEntity.getChildId(), childEntity.getXk_id(),
                        childEntity.getName(), childEntity.getVideo_time(), split[0], split[1],
                        childEntity.getVideo_url(), childEntity.getTxt_url(), childEntity.getT_header());
                DownUtils.add(DownLoadListBean);*/
                    GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                    OkDownload.request(childEntity.getXk_id() + "_" + childEntity.getId(), request)
                            .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download")
                            .fileName(childEntity.getName() + childEntity.getXk_id() + "_" + childEntity.getId() + ".mp3")
                            .extra3(downLoadListsBean)
                            .save()
                            .register(new LogDownloadListener())//当前任务的回调监听
                            .start();
                    OkGo.<File>get(childEntity.getTxt_url())
                            .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download"
                                    , childEntity.getXk_id() + "-" + childEntity.getId() + childEntity.getName() + ".txt") {
                                @Override
                                public void onSuccess(Response<File> response) {
                                    int code = response.code();
                                    if (code >= 200 && code <= 204) {
                                        Logger.e("文稿下载完成");
                                    }
                                }
                            });
                    Toast.makeText(MyApplication.getInstance(), "已加入下载列表", Toast.LENGTH_SHORT).show();
                    EventBean eventBean = new EventBean("number");
                    EventBus.getDefault().postSticky(eventBean);
                    mDialog.dismiss();
                    mNetDialog.dismiss();
                }

            }
        });
    }

    private void changeCollect(final int adapterPosition, int id) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", id + "");
        params.put("type", "1");
        String url = "";
        if (mIsCollected) {
            url = MyContants.LXKURL + "xk/no-favorite";
        } else {
            url = MyContants.LXKURL + "xk/favorite";
        }
        OkGo.<DianZanbean>post(url)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(SoftMusicDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 if (mIsCollected) {
                                     Drawable drawable = getResources().getDrawable(R.drawable.bofanglist_collect);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_collect.setCompoundDrawables(null, drawable, null, null);
                                 } else {
                                     Drawable drawable = getResources().getDrawable(R.drawable.collect_shixin);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_collect.setCompoundDrawables(null, drawable, null, null);
                                 }
                                 mIsCollected = !mIsCollected;
                                 mChild.get(adapterPosition).setIsfav(mIsCollected);
                                 mLieBiaoAdapter.notifyDataSetChanged();
                                 mDialog.dismiss();
                             }
                         }
                );
    }

    private void changeDianzan(final int adapterPosition, int id) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", id + "");
        String url = "";
        if (mDianzan) {
            url = MyContants.LXKURL + "xk/no-dianzan";
        } else {
            url = MyContants.LXKURL + "xk/dianzan";
        }
        OkGo.<DianZanbean>post(url)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(SoftMusicDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 if (mDianzan) {
                                     Drawable drawable = getResources().getDrawable(R.drawable.dianzan_yellow_big);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
                                 } else {
                                     Drawable drawable = getResources().getDrawable(R.drawable.dianzan_shixin);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
                                 }
                                 mDianzan = !mDianzan;
                                 mChild.get(adapterPosition).setIslive(mDianzan);
                                 mLieBiaoAdapter.notifyDataSetChanged();
                                 mDialog.dismiss();
                             }
                         }
                );
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
        params.put("type", "xiaoke");
        params.put("from", "android");
        OkGo.<OrderBean>post(MyContants.LXKURL + "order/buy")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<OrderBean>(SoftMusicDetailActivity.this, OrderBean.class) {
                             @Override
                             public void onSuccess(Response<OrderBean> response) {
                                 OrderBean orderBean = response.body();
                                 if (response.code() >= 200 && response.code() <= 204) {
                                     String order_sn = orderBean.getOrder_sn();
                                     goPay(order_sn, type);
                                 } else {
                                     Toast.makeText(SoftMusicDetailActivity.this, orderBean.getMessage(), Toast.LENGTH_SHORT).show();
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
                    .execute(new DialogCallback<BaseBean>(SoftMusicDetailActivity.this, BaseBean.class) {
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
                                         Toast.makeText(SoftMusicDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                     }

                                 }
                             }
                    );
        } else if (type.equals("2")) {//微信支付
            OkGo.<WXPayBean>post(MyContants.LXKURL + "order/pay")
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new DialogCallback<WXPayBean>(SoftMusicDetailActivity.this, WXPayBean.class) {
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
                    .execute(new DialogCallback<AliPayBean>(SoftMusicDetailActivity.this, AliPayBean.class) {
                                 @Override
                                 public void onSuccess(Response<AliPayBean> response) {
                                     int code = response.code();
                                     final AliPayBean aliPayBean = response.body();
                                     Runnable payRunnable = new Runnable() {

                                         @Override
                                         public void run() {
                                             PayTask alipay = new PayTask(SoftMusicDetailActivity.this);
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
                startActivity(new Intent(SoftMusicDetailActivity.this, MyAccountActivity.class));
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
                Intent intent = new Intent(SoftMusicDetailActivity.this, MainActivity.class);
                intent.putExtra("gobuy", "gobuy");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showIsCancelGuanzhuDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_guanzhu)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.show();
        dialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                noguanzhu(mMusicDetailBean.getXk_teacher_id());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_download:
                SoftMusicDetailBean model = mMusicDetailBean;
                Logger.e(model.getChild().size() + "");
                intent = new Intent(this, DownLoadListActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
                break;
            case R.id.tv_search:
                Intent intent1 = new Intent(this, SearchMusicActivity2.class);
                //                intent1.putExtra("type","xk");
                intent1.putExtra("id", mMusicDetailBean.getId() + "");
                startActivity(intent1);
                break;
            case R.id.tv_share:
                showShareDialog("root", 0);
                break;
            case R.id.tv_guanzhu:
            case R.id.iv_guanzhu:
                if (isGuanzhu) {
                    showIsCancelGuanzhuDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                } else {
                    guanzhu(mMusicDetailBean.getXk_teacher_id());
                }
                isGuanzhu = !isGuanzhu;
                break;
            case R.id.tv_dianzan_count:
            case R.id.iv_dianzan:
                if (isZan) {
                    nodianzanTeacher(mMusicDetailBean.getXk_teacher_id());
                } else {
                    dianzanTeacher(mMusicDetailBean.getXk_teacher_id());
                }
                isZan = !isZan;
                break;
            case R.id.tv_writeliuyan:
                Intent intent = new Intent(this, LiuYanActivity.class);
                intent.putExtra("teacher_id", mMusicDetailBean.getXk_teacher_id() + "");
                intent.putExtra("type", "softmusicdetail-root");
                intent.putExtra("xiaoke_id", mMusicDetailBean.getId() + "");
                startActivity(intent);
                break;
            case R.id.tv_tryread:
            case R.id.tv_read:
                if (mLieBiaoAdapter.getData().size() > 0 && mLieBiaoAdapter.getData().get(0).getIs_try() != 1) {
                    showIsBuyDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                } else {
                    setISshow(true);
                    SoftMusicDetailBean.ChildEntity item = mChild.get(0);
                    //刷新小型播放器
                    PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getName(), item.getParent_name(), item.getVideo_url());
                    gobofang(playerBean);
                    addListenCount(item.getId() + "");
                    //设置进入播放主界面的数据
                    List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                    for (int i = 0; i < mChild.size(); i++) {
                        SoftMusicDetailBean.ChildEntity childEntity = mChild.get(i);
                        if (childEntity.getIs_try() == 1 || mMusicDetailBean.is_buy()) {
                            MusicTypeBean musicTypeBean = new MusicTypeBean("softmusicdetail",
                                    childEntity.getT_header(), childEntity.getName(), childEntity.getId() + "", childEntity.isIsfav());
                            musicTypeBean.setMsg("musicplayertype");
                            musicTypeBeanList.add(musicTypeBean);
                        }
                    }
                    MediaService.insertMusicTypeList(musicTypeBeanList);
                    //加入默认的播放列表
                    List<PlayerBean> list = new ArrayList<PlayerBean>();
                    for (int i = 0; i < mChild.size(); i++) {
                        SoftMusicDetailBean.ChildEntity entity = mChild.get(i);
                        if (entity.getIs_try() == 1 || mMusicDetailBean.is_buy()) {
                            PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getName(), entity.getParent_name(),
                                    entity.getVideo_url(), 0);
                            list.add(playerBean1);
                        }
                    }
                    MediaService.insertMusicList(list);
                    //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                    List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                    for (int i = 0; i < mChild.size(); i++) {
                        SoftMusicDetailBean.ChildEntity entity = mChild.get(i);
                        if (entity.getIs_try() == 1 || mMusicDetailBean.is_buy()) {
                            BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("softmusicdetail", entity.getId(), entity.getName(),
                                    entity.getCreated_at(), entity.getVideo_url(), entity.getGood_count(),
                                    entity.getCollect_count(), entity.getView_count(), entity.isIslive(), entity.isIsfav()
                                    , entity.getT_header(), entity.getParent_name(), entity.getShare_h5_url()
                                    , System.currentTimeMillis(), mMusicDetailBean.getXk_class_id() + "",
                                    entity.getParent_name(), entity.getTxt_url());
                            histroyBeanList.add(bofangHistroyBean);
                        }
                    }
                    MediaService.insertBoFangHistroyList(histroyBeanList);
                }
                break;
            case R.id.tv_buy:
                showPayStyleDialog();
                break;
        }
    }

    private void guanzhu(int teacher_id) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("teacher_id", teacher_id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-follow/attention")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<DianZanbean>(SoftMusicDetailActivity.this, DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 iv_guanzhu.setImageResource(R.drawable.free_guanzhu);
                                 tv_guanzhu.setText("已关注");
                                 Toast.makeText(SoftMusicDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

    private void noguanzhu(int teacher_id) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("teacher_id", teacher_id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-follow/no-attention")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<DianZanbean>(SoftMusicDetailActivity.this, DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 iv_guanzhu.setImageResource(R.drawable.free_quxiaoguanzhu);
                                 tv_guanzhu.setText("关注");
                                 Toast.makeText(SoftMusicDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

    private void dianzanTeacher(int teacher_id) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("teacher_id", teacher_id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-teacher/live")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 iv_dianzan.setImageResource(R.drawable.free_yizan);
                                 Toast.makeText(SoftMusicDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mTeacher_zan_count += 1;
                                 tv_dianzan_count.setText(mTeacher_zan_count + "");
                             }
                         }
                );
    }

    private void nodianzanTeacher(int teacher_id) {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("teacher_id", teacher_id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-teacher/no-live")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 iv_dianzan.setImageResource(R.drawable.free_dianzan);
                                 Toast.makeText(SoftMusicDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mTeacher_zan_count -= 1;
                                 tv_dianzan_count.setText(mTeacher_zan_count + "");
                             }
                         }
                );
    }
}
