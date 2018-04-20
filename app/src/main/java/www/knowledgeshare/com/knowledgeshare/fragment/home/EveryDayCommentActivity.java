package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.UMShareActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.EveryDayBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

public class EveryDayCommentActivity extends UMShareActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_share;
    private ImageView iv_beijing;
    private TextView tv_search;
    private TextView tv_download;
    private TextView tv_jie_count;
    private TextView tv_look_count;
    private TextView tv_collect_count;
    private RecyclerView recycler_liebiao;
    private LinearLayout activity_free;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private NestedScrollView nestView;
    private boolean mIsCollected;
    private boolean mDianzan;
    private LieBiaoAdapter mLieBiaoAdapter;
    private List<EveryDayBean.DailysBean> mDailys;
    private BaseDialog mNetDialog;
    private String after = "";
    private SpringView springview;
    private boolean isLoadMore;
    private EveryDayBean everyDayBean;
    private boolean backRefresh;
    private boolean nowifiallowdown;
    private TextView mTv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_day_comment);
        initView();
        initData();
        initDialog();
        initListener();
        initNETDialog();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("refresh_daily")) {
            String id=eventBean.getMsg2();
            for (int i = 0; i < mDailys.size(); i++) {
                String id1 = mDailys.get(i).getId() + "";
                if (id.equals(id1)){
                    int measuredHeight = iv_beijing.getMeasuredHeight();
                    nestView.scrollTo(0,measuredHeight+MyUtils.dip2px(this,20+i*50));
                    return;
                }
            }
            after= eventBean.getMsg2();
            isLoadMore=false;
            backRefresh=true;
            initData();
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
                        SpUtils.putBoolean(EveryDayCommentActivity.this, "nowifiallowlisten", true);
                    }
                });
                mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNetDialog.dismiss();
                    }
                });
            }
        } else if (NetWorkUtils.isMobileConnected(EveryDayCommentActivity.this)) {
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
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(this, "id", ""));
        if (isLoadMore || backRefresh) {
            params.put("after", after);
        }
        if (backRefresh){
            params.put("is_search", "1");
        }
        OkGo.<EveryDayBean>post(MyContants.LXKURL + "daily")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<EveryDayBean>(EveryDayCommentActivity.this, EveryDayBean.class) {
                    @Override
                    public void onSuccess(Response<EveryDayBean> response) {
                        int code = response.code();
                        everyDayBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            if (isLoadMore) {
                                List<EveryDayBean.DailysBean> dailys = everyDayBean.getDailys();
                                if (dailys == null || dailys.size() == 0) {
                                    Toast.makeText(EveryDayCommentActivity.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                mDailys.addAll(dailys);
                            } else {
                                mDailys = everyDayBean.getDailys();
                            }
                            RequestOptions options=new RequestOptions();
                            options.error(R.drawable.default_banner);
                            options.placeholder(R.drawable.default_banner);
                            Glide.with(EveryDayCommentActivity.this).load(everyDayBean.getImgurl()).apply(options).into(iv_beijing);
                            ViewGroup.LayoutParams layoutParams = iv_beijing.getLayoutParams();
                            int width = MyUtils.getScreenWidth(EveryDayCommentActivity.this);
                            layoutParams.height= width*7/15;
                            iv_beijing.setLayoutParams(layoutParams);
                            tv_jie_count.setText("已更新：" + everyDayBean.getUpdate_count() + "节");
                            tv_look_count.setText("浏览次数：" + everyDayBean.getView_count() + "");
                            tv_collect_count.setText("收藏次数：" + everyDayBean.getCollect_count() + "");
                            mLieBiaoAdapter = new LieBiaoAdapter(R.layout.item_free, mDailys);
                            recycler_liebiao.setAdapter(mLieBiaoAdapter);
                            mLieBiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    setISshow(true);
                                    EveryDayBean.DailysBean item = mDailys.get(position);
                                    //刷新小型播放器
                                    PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getVideo_name(),
                                            item.getParent_name(), item.getVideo_url(), position);
                                    gobofang(playerBean);
                                    addListenCount(mDailys.get(position).getId() + "");
                                    //设置进入播放主界面的数据
                                    List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                                    for (int i = 0; i < mDailys.size(); i++) {
                                        EveryDayBean.DailysBean entity = mDailys.get(i);
                                        MusicTypeBean musicTypeBean = new MusicTypeBean("everydaycomment",
                                                entity.getT_header(), entity.getVideo_name(), entity.getId() + "", entity.isIsfav());
                                        musicTypeBean.setMsg("musicplayertype");
                                        musicTypeBeanList.add(musicTypeBean);
                                    }
                                    MediaService.insertMusicTypeList(musicTypeBeanList);
                                    //加入默认的播放列表
                                    List<PlayerBean> list = new ArrayList<PlayerBean>();
                                    for (int i = 0; i < mDailys.size(); i++) {
                                        EveryDayBean.DailysBean entity = mDailys.get(i);
                                        PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_name(), entity.getParent_name(), entity.getVideo_url());
                                        list.add(playerBean1);
                                    }
                                    MediaService.insertMusicList(list);
                                    //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                                    List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                                    for (int i = 0; i < mDailys.size(); i++) {
                                        EveryDayBean.DailysBean entity = mDailys.get(i);
                                        BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("everydaycomment", entity.getId(), entity.getVideo_name(),
                                                entity.getCreated_at(), entity.getVideo_url(), entity.getGood_count(),
                                                entity.getCollect_count(), entity.getView_count(), entity.isIslive(), entity.isIsfav()
                                                , entity.getT_header(), entity.getParent_name(), entity.getShare_h5_url(),
                                                System.currentTimeMillis()
                                                , "commentId", entity.getParent_name(), entity.getTxt_url());
                                        histroyBeanList.add(bofangHistroyBean);
                                    }
                                    MediaService.insertBoFangHistroyList(histroyBeanList);
                                }
                            });
                        } else {
                        }
                    }

                    @Override
                    public void onError(Response<EveryDayBean> response) {
                        super.onError(response);
                        Logger.e(response.getException().getMessage());
                    }
                });
    }

    private void addListenCount(String id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("type", "daily");
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

    private void initListener() {
//        nestView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY - oldScrollY > 0) {
//                    setPopHide();
//                } else if (scrollY - oldScrollY < 0) {
//                    SlidePopShow();
//                }
//            }
//        });
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        springview.onFinishFreshAndLoad();
                    }
                }, 800);
            }

            @Override
            public void onLoadmore() {
                isLoadMore = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        springview.onFinishFreshAndLoad();
                    }
                }, 2000);
            }
        });
        springview.setHeader(new MyHeader(this));
        springview.setFooter(new MyFooter(this));
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
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
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        tv_jie_count = (TextView) findViewById(R.id.tv_jie_count);
        tv_look_count = (TextView) findViewById(R.id.tv_look_count);
        tv_collect_count = (TextView) findViewById(R.id.tv_collect_count);
        recycler_liebiao = (RecyclerView) findViewById(R.id.recycler_liebiao);
        activity_free = (LinearLayout) findViewById(R.id.activity_free);
        nestView = (NestedScrollView) findViewById(R.id.nestView);
        recycler_liebiao.setLayoutManager(new LinearLayoutManager(this));
        recycler_liebiao.setNestedScrollingEnabled(false);
        springview = (SpringView) findViewById(R.id.springview);
    }

    private class LieBiaoAdapter extends BaseQuickAdapter<EveryDayBean.DailysBean, BaseViewHolder> {

        public LieBiaoAdapter(@LayoutRes int layoutResId, @Nullable List<EveryDayBean.DailysBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final EveryDayBean.DailysBean item) {
            after = item.getId() + "";
            String created_at = item.getCreated_at();
            String[] split = created_at.split(" ");
            helper.setText(R.id.tv_name, item.getVideo_name())
                    .setText(R.id.tv_time, split[0] + "发布")
                    .setText(R.id.tv_look_count, item.getIs_view() == 0 ? item.getView_count() + "" : item.getView_count_true() + "")
                    .setText(R.id.tv_collect_count, item.getIs_collect() == 0 ? item.getCollect_count() + "" : item.getCollect_count_true() + "")
                    .setText(R.id.tv_dianzan_count, item.getIs_good() == 0 ? item.getGood_count() + "" : item.getGood_count_true() + "");
            helper.getView(R.id.iv_dian).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showListDialog(helper.getAdapterPosition(), item.isIsfav(), item.isIslive(), item.getId());
                }
            });
            helper.getView(R.id.iv_wengao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EveryDayCommentActivity.this, WenGaoActivity.class);
                    intent.putExtra("type", "everydaycomment");
                    //                    intent.putExtra("t_name", item.getT_name());
                    //                    intent.putExtra("t_head", item.getT_header());
                    //                    intent.putExtra("video_name", item.getVideo_name());
                    //                    intent.putExtra("teacher_id", item.getTeacher_id() + "");
                    intent.putExtra("id", item.getId() + "");
                    startActivity(intent);
                }
            });
            if (helper.getAdapterPosition() <= 8) {
                helper.setText(R.id.tv_order, "0" + (helper.getAdapterPosition() + 1));
            }else {
                helper.setText(R.id.tv_order, "" + (helper.getAdapterPosition() + 1));
            }
        }
    }

    private TextView mTv_collect, mTv_dianzan;

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
                    startActivity(new Intent(EveryDayCommentActivity.this, LoginActivity.class));
                    return;
                }
                int apnType = NetWorkUtils.getAPNType(EveryDayCommentActivity.this);
                if (apnType == 0) {
                    Toast.makeText(EveryDayCommentActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                } else if (apnType == 2 || apnType == 3 || apnType == 4) {
                    nowifiallowdown = SpUtils.getBoolean(EveryDayCommentActivity.this, "nowifiallowdown", false);
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
                                SpUtils.putBoolean(EveryDayCommentActivity.this, "nowifiallowdown", true);
                                EveryDayBean.DailysBean childEntity = mDailys.get(adapterPosition);
                                String created_at = childEntity.getCreated_at();
                                String[] split = created_at.split(" ");

                                List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                listBean.setTypeId("commentId");
                                listBean.setChildId(childEntity.getId() + "");
                                listBean.setName(childEntity.getVideo_name());
                                listBean.setVideoTime(childEntity.getVideo_time());
                                listBean.setDate(split[0]);
                                listBean.setTime(split[1]);
                                listBean.setVideoUrl(childEntity.getVideo_url());
                                listBean.setTxtUrl(childEntity.getTxt_url());
                                listBean.setIconUrl(childEntity.getT_header());
                                listBean.settName(childEntity.getT_name());
                                listBean.setParentName("");
                                listBean.setH5_url(childEntity.getShare_h5_url());
                                listBean.setGood_count(childEntity.getGood_count());
                                listBean.setCollect_count(childEntity.getCollect_count());
                                listBean.setView_count(childEntity.getView_count());
                                listBean.setDianzan(childEntity.isIslive());
                                listBean.setCollected(childEntity.isIsfav());
                                list.add(listBean);
                                if (MyUtils.isHaveFile("comment",childEntity.getVideo_name() + listBean.getTypeId() + "_" + childEntity.getId() + ".mp3")){
                                    Toast.makeText(EveryDayCommentActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(MyApplication.getInstance(), "已加入下载列表", Toast.LENGTH_SHORT).show();
                                DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                        "comment", "commentId", "", childEntity.getT_header(), "", "", list.size() + "", list);
                                DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(-2,childEntity.getChildId(),-4,-3,-1,
                        childEntity.getVideo_name(),childEntity.getVideo_time(), split[0], split[1],
                        childEntity.getVideo_url(), childEntity.getTxt_url(),childEntity.getT_header());
                DownUtils.add(DownLoadListBean);*/
                                GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                                OkDownload.request(downLoadListsBean.getTypeId() + "_" + childEntity.getId(), request)
                                        .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download")
                                        .fileName(childEntity.getVideo_name() + listBean.getTypeId() + "_" + childEntity.getId() + ".mp3")
                                        .extra3(downLoadListsBean)//额外数据
                                        .save()
                                        .register(new LogDownloadListener())//当前任务的回调监听
                                        .start();
                                OkGo.<File>get(childEntity.getTxt_url())
                                        .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download"
                                                , downLoadListsBean.getTypeId() + "-" + childEntity.getId() + childEntity.getVideo_name() + ".txt") {
                                            @Override
                                            public void onSuccess(Response<File> response) {
                                                int code = response.code();
                                                if (code >= 200 && code <= 204) {
                                                    Logger.e("文稿下载完成");
                                                }
                                            }
                                        });
                                EventBean eventBean = new EventBean("number");
                                EventBus.getDefault().postSticky(eventBean);
                                mDialog.dismiss();
                                mNetDialog.dismiss();
                            }
                        });
                    }else {
                        EveryDayBean.DailysBean childEntity = mDailys.get(adapterPosition);
                        String created_at = childEntity.getCreated_at();
                        String[] split = created_at.split(" ");

                        List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                        DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                        listBean.setTypeId("commentId");
                        listBean.setChildId(childEntity.getId() + "");
                        listBean.setName(childEntity.getVideo_name());
                        listBean.setVideoTime(childEntity.getVideo_time());
                        listBean.setDate(split[0]);
                        listBean.setTime(split[1]);
                        listBean.setVideoUrl(childEntity.getVideo_url());
                        listBean.setTxtUrl(childEntity.getTxt_url());
                        listBean.setIconUrl(childEntity.getT_header());
                        listBean.settName(childEntity.getT_name());
                        listBean.setParentName("");
                        listBean.setH5_url(childEntity.getShare_h5_url());
                        listBean.setGood_count(childEntity.getGood_count());
                        listBean.setCollect_count(childEntity.getCollect_count());
                        listBean.setView_count(childEntity.getView_count());
                        listBean.setDianzan(childEntity.isIslive());
                        listBean.setCollected(childEntity.isIsfav());
                        list.add(listBean);
                        if (MyUtils.isHaveFile("comment",childEntity.getVideo_name() + listBean.getTypeId() + "_" + childEntity.getId() + ".mp3")){
                            Toast.makeText(EveryDayCommentActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(MyApplication.getInstance(), "已加入下载列表", Toast.LENGTH_SHORT).show();
                        DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                "comment", "commentId", "", childEntity.getT_header(), "", "", list.size() + "", list);
                        DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(-2,childEntity.getChildId(),-4,-3,-1,
                        childEntity.getVideo_name(),childEntity.getVideo_time(), split[0], split[1],
                        childEntity.getVideo_url(), childEntity.getTxt_url(),childEntity.getT_header());
                DownUtils.add(DownLoadListBean);*/
                        GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                        OkDownload.request(downLoadListsBean.getTypeId() + "_" + childEntity.getId(), request)
                                .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download")
                                .fileName(childEntity.getVideo_name() + listBean.getTypeId() + "_" + childEntity.getId() + ".mp3")
                                .extra3(downLoadListsBean)//额外数据
                                .save()
                                .register(new LogDownloadListener())//当前任务的回调监听
                                .start();
                        OkGo.<File>get(childEntity.getTxt_url())
                                .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download"
                                        , downLoadListsBean.getTypeId() + "-" + childEntity.getId() + childEntity.getVideo_name() + ".txt") {
                                    @Override
                                    public void onSuccess(Response<File> response) {
                                        int code = response.code();
                                        if (code >= 200 && code <= 204) {
                                            Logger.e("文稿下载完成");
                                        }
                                    }
                                });
                        EventBean eventBean = new EventBean("number");
                        EventBus.getDefault().postSticky(eventBean);
                        mDialog.dismiss();
                        mNetDialog.dismiss();
                    }
                } else {
                    EveryDayBean.DailysBean childEntity = mDailys.get(adapterPosition);
                    String created_at = childEntity.getCreated_at();
                    String[] split = created_at.split(" ");

                    List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                    DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                    listBean.setTypeId("commentId");
                    listBean.setChildId(childEntity.getId() + "");
                    listBean.setName(childEntity.getVideo_name());
                    listBean.setVideoTime(childEntity.getVideo_time());
                    listBean.setDate(split[0]);
                    listBean.setTime(split[1]);
                    listBean.setVideoUrl(childEntity.getVideo_url());
                    listBean.setTxtUrl(childEntity.getTxt_url());
                    listBean.setIconUrl(childEntity.getT_header());
                    listBean.settName(childEntity.getT_name());
                    listBean.setParentName("");
                    listBean.setH5_url(childEntity.getShare_h5_url());
                    listBean.setGood_count(childEntity.getGood_count());
                    listBean.setCollect_count(childEntity.getCollect_count());
                    listBean.setView_count(childEntity.getView_count());
                    listBean.setDianzan(childEntity.isIslive());
                    listBean.setCollected(childEntity.isIsfav());
                    list.add(listBean);
                    if (MyUtils.isHaveFile("comment",childEntity.getVideo_name() + listBean.getTypeId() + "_" + childEntity.getId() + ".mp3")){
                        Toast.makeText(EveryDayCommentActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(MyApplication.getInstance(), "已加入下载列表", Toast.LENGTH_SHORT).show();
                    DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                            "comment", "commentId", "", childEntity.getT_header(), "", "", list.size() + "", list);
                    DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(-2,childEntity.getChildId(),-4,-3,-1,
                        childEntity.getVideo_name(),childEntity.getVideo_time(), split[0], split[1],
                        childEntity.getVideo_url(), childEntity.getTxt_url(),childEntity.getT_header());
                DownUtils.add(DownLoadListBean);*/
                    GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                    OkDownload.request(downLoadListsBean.getTypeId() + "_" + childEntity.getId(), request)
                            .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download")
                            .fileName(childEntity.getVideo_name() + listBean.getTypeId() + "_" + childEntity.getId() + ".mp3")
                            .extra3(downLoadListsBean)//额外数据
                            .save()
                            .register(new LogDownloadListener())//当前任务的回调监听
                            .start();
                    OkGo.<File>get(childEntity.getTxt_url())
                            .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download"
                                    , downLoadListsBean.getTypeId() + "-" + childEntity.getId() + childEntity.getVideo_name() + ".txt") {
                                @Override
                                public void onSuccess(Response<File> response) {
                                    int code = response.code();
                                    if (code >= 200 && code <= 204) {
                                        Logger.e("文稿下载完成");
                                    }
                                }
                            });
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
            url = MyContants.LXKURL + "daily/no-favorite";
        } else {
            url = MyContants.LXKURL + "daily/favorite";
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
                                 Toast.makeText(EveryDayCommentActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 int collect_count = mDailys.get(adapterPosition).getCollect_count();
                                 int collect_count_true = mDailys.get(adapterPosition).getCollect_count_true();
                                 if (mIsCollected) {
                                     Drawable drawable = getResources().getDrawable(R.drawable.bofanglist_collect);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_collect.setCompoundDrawables(null, drawable, null, null);
                                     if (collect_count <= 0) {
                                         mDailys.get(adapterPosition).setCollect_count(0);
                                     } else {
                                         mDailys.get(adapterPosition).setCollect_count(collect_count - 1);
                                     }
                                     if (collect_count_true<=0){
                                         mDailys.get(adapterPosition).setCollect_count_true(0);
                                     }else {
                                         mDailys.get(adapterPosition).setCollect_count_true(collect_count_true - 1);
                                     }
                                 } else {
                                     Drawable drawable = getResources().getDrawable(R.drawable.collect_shixin);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_collect.setCompoundDrawables(null, drawable, null, null);
                                     mDailys.get(adapterPosition).setCollect_count(collect_count + 1);
                                     mDailys.get(adapterPosition).setCollect_count_true(collect_count_true + 1);
                                 }
                                 mIsCollected = !mIsCollected;
                                 mDailys.get(adapterPosition).setIsfav(mIsCollected);
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
            url = MyContants.LXKURL + "daily/no-dianzan";
        } else {
            url = MyContants.LXKURL + "daily/dianzan";
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
                                 Toast.makeText(EveryDayCommentActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 int good_count = mDailys.get(adapterPosition).getGood_count();
                                 int good_count_true = mDailys.get(adapterPosition).getGood_count_true();
                                 if (mDianzan) {
                                     Drawable drawable = getResources().getDrawable(R.drawable.dianzan_yellow_big);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
                                     if (good_count <= 0) {
                                         mDailys.get(adapterPosition).setGood_count(0);
                                     } else {
                                         mDailys.get(adapterPosition).setGood_count(good_count - 1);
                                     }
                                     if (good_count_true<=0){
                                         mDailys.get(adapterPosition).setGood_count_true(0);
                                     }else {
                                         mDailys.get(adapterPosition).setGood_count_true(good_count_true - 1);
                                     }
                                 } else {
                                     Drawable drawable = getResources().getDrawable(R.drawable.dianzan_shixin);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
                                     mDailys.get(adapterPosition).setGood_count(good_count + 1);
                                     mDailys.get(adapterPosition).setGood_count_true(good_count_true + 1);
                                 }
                                 mDianzan = !mDianzan;
                                 mDailys.get(adapterPosition).setIslive(mDianzan);
                                 mLieBiaoAdapter.notifyDataSetChanged();
                                 mDialog.dismiss();
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
        final EveryDayBean.DailysBean entity = mDailys.get(adapterPosition);
        mDialog.getView(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(everyDayBean.getH5_url(), mDailys.get(0).getVideo_name(),
                            everyDayBean.getImgurl(), "", EveryDayCommentActivity.this, SHARE_MEDIA.WEIXIN);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", EveryDayCommentActivity.this, SHARE_MEDIA.WEIXIN);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(everyDayBean.getH5_url(), mDailys.get(0).getVideo_name(),
                            everyDayBean.getImgurl(), "", EveryDayCommentActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", EveryDayCommentActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(everyDayBean.getH5_url(), mDailys.get(0).getVideo_name(),
                            everyDayBean.getImgurl(), "", EveryDayCommentActivity.this, SHARE_MEDIA.QZONE);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", EveryDayCommentActivity.this, SHARE_MEDIA.QZONE);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(everyDayBean.getH5_url(), mDailys.get(0).getVideo_name(),
                            everyDayBean.getImgurl(), "", EveryDayCommentActivity.this, SHARE_MEDIA.QQ);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", EveryDayCommentActivity.this, SHARE_MEDIA.QQ);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(everyDayBean.getH5_url(), mDailys.get(0).getVideo_name(),
                            everyDayBean.getImgurl(), "", EveryDayCommentActivity.this, SHARE_MEDIA.SINA);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", EveryDayCommentActivity.this, SHARE_MEDIA.SINA);
                }
                mDialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        unbindService(mServiceConnection);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                showShareDialog("root", 0);
                break;
            case R.id.tv_search:
                Intent intent1 = new Intent(this, SearchMusicActivity.class);
                intent1.putExtra("type", "daily");
                startActivity(intent1);
                break;
            case R.id.tv_download:
                EveryDayBean model = everyDayBean;
                Intent intent = new Intent(this, CommentDownActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
                break;
        }
    }
}
