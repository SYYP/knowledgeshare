package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.UMShareActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.db.HistroyUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.EveryDayBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
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
    private List<EveryDayBean.DailysEntity> mDailys;
    private BaseDialog mNetDialog;
    private String after = "";
    private SpringView springview;
    private boolean isLoadMore;
    private EveryDayBean everyDayBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_day_comment);
        initView();
        initData();
        initDialog();
        initMusic();
        initListener();
        initNETDialog();
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
        if (isLoadMore) {
            params.put("after", after);
        }
        OkGo.<EveryDayBean>post(MyContants.LXKURL + "daily")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<EveryDayBean>(EveryDayCommentActivity.this, EveryDayBean.class) {
                    @Override
                    public void onSuccess(Response<EveryDayBean> response) {
                        int code = response.code();
                        everyDayBean = response.body();
                        mDailys = everyDayBean.getDailys();
                        EveryDayBean everyDayBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            if (isLoadMore) {
                                List<EveryDayBean.DailysEntity> dailys = everyDayBean.getDailys();
                                if (dailys == null || dailys.size() == 0) {
                                    Toast.makeText(EveryDayCommentActivity.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                mDailys.addAll(dailys);
                                mLieBiaoAdapter.notifyDataSetChanged();
                            } else {
                                mDailys = everyDayBean.getDailys();
                                mLieBiaoAdapter = new LieBiaoAdapter(R.layout.item_free, mDailys);
                                recycler_liebiao.setAdapter(mLieBiaoAdapter);
                            }
                            Glide.with(EveryDayCommentActivity.this).load(everyDayBean.getImgurl()).into(iv_beijing);
                            tv_jie_count.setText("已更新：" + everyDayBean.getUpdate_count() + "节");
                            tv_look_count.setText("浏览次数：" + everyDayBean.getView_count() + "");
                            tv_collect_count.setText("收藏次数：" + everyDayBean.getCollect_count() + "");
                            mLieBiaoAdapter = new LieBiaoAdapter(R.layout.item_free, mDailys);
                            recycler_liebiao.setAdapter(mLieBiaoAdapter);
                            mLieBiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    setISshow(true);
                                    EveryDayBean.DailysEntity item = mDailys.get(position);
                                    PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getVideo_name(), item.getT_tag(), item.getVideo_url());
                                    gobofang(playerBean);
                                    addListenCount(mDailys.get(position).getId() + "");
                                    MusicTypeBean musicTypeBean = new MusicTypeBean("everydaycomment",
                                            item.getT_header(), item.getVideo_name(), item.getId() + "", item.isIsfav());
                                    musicTypeBean.setMsg("musicplayertype");
                                    EventBus.getDefault().postSticky(musicTypeBean);
                                    List<PlayerBean> list = new ArrayList<PlayerBean>();
                                    for (int i = 0; i < mDailys.size(); i++) {
                                        EveryDayBean.DailysEntity entity = mDailys.get(i);
                                        PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_name(), entity.getT_tag(), entity.getVideo_url());
                                        list.add(playerBean1);
                                    }
                                    MediaService.insertMusicList(list);
                                    if (!HistroyUtils.isInserted(item.getVideo_name())) {
                                        BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("everydaycomment", item.getId(), item.getVideo_name(),
                                                item.getCreated_at(), item.getVideo_url(), item.getGood_count(),
                                                item.getCollect_count(), item.getView_count(), item.isIslive(), item.isIsfav()
                                                , item.getT_header(), item.getT_tag(),item.getShare_h5_url());
                                        HistroyUtils.add(bofangHistroyBean);
                                    }
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
                isLoadMore = false;
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

    private class LieBiaoAdapter extends BaseQuickAdapter<EveryDayBean.DailysEntity, BaseViewHolder> {

        public LieBiaoAdapter(@LayoutRes int layoutResId, @Nullable List<EveryDayBean.DailysEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final EveryDayBean.DailysEntity item) {
            after = item.getId() + "";
            helper.setText(R.id.tv_name, item.getVideo_name())
                    .setText(R.id.tv_time, item.getCreated_at() + "发布")
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
            helper.setText(R.id.tv_order, "0" + (helper.getAdapterPosition() + 1));
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
                showShareDialog("list",adapterPosition);
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
                EveryDayBean.DailysEntity childEntity = mDailys.get(adapterPosition);
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
                list.add(listBean);
                DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                        "comment", "commentId", "", childEntity.getT_header(), "", "", list.size() + "", list);
                DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(-2,childEntity.getId(),-4,-3,-1,
                        childEntity.getVideo_name(),childEntity.getVideo_time(), split[0], split[1],
                        childEntity.getVideo_url(), childEntity.getTxt_url(),childEntity.getT_header());
                DownUtils.add(DownLoadListBean);*/
                GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                OkDownload.request(downLoadListsBean.getTypeId()+"_"+childEntity.getId(), request);
                OkDownload.request(childEntity.getVideo_name() + "_" + childEntity.getId(), request)
                        .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download")
                        .fileName(childEntity.getVideo_name()+"_"+childEntity.getId()+".mp3")
                        .extra3(downLoadListsBean)//额外数据
                        .save()
                        .register(new LogDownloadListener())//当前任务的回调监听
                        .start();
                OkGo.<File>get(childEntity.getTxt_url())
                        .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download"
                                ,childEntity.getId()+"-"+childEntity.getVideo_name()+".txt") {
                            @Override
                            public void onSuccess(Response<File> response) {
                                int code = response.code();
                                if (code >= 200 && code <= 204){
                                    Logger.e("文稿下载完成");
                                }
                            }
                        });
                mDialog.dismiss();
            }
        });
    }

    private void changeCollect(final int adapterPosition, int id) {
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
                                 mDailys.get(adapterPosition).setIsfav(mIsCollected);
                                 mLieBiaoAdapter.notifyDataSetChanged();
                                 mDialog.dismiss();
                             }
                         }
                );
    }

    private void changeDianzan(final int adapterPosition, int id) {
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
        final EveryDayBean.DailysEntity entity = mDailys.get(adapterPosition);
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

    private MediaService.MyBinder mMyBinder;
    //“绑定”服务的intent
    private Intent MediaServiceIntent;

    private void initMusic() {
        MediaServiceIntent = new Intent(this, MediaService.class);
        //        startService(MediaServiceIntent);
        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
            if (mMyBinder.isPlaying()) {
            } else {
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbindService(mServiceConnection);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                showShareDialog("root",0);
                break;
            case R.id.tv_search:
                startActivity(new Intent(this, SearchActivity.class));
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
