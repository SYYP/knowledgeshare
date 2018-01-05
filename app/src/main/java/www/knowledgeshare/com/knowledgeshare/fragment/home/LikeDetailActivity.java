package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
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
import www.knowledgeshare.com.knowledgeshare.activity.MyAccountActivity;
import www.knowledgeshare.com.knowledgeshare.activity.ShoppingCartActivity;
import www.knowledgeshare.com.knowledgeshare.base.UMShareActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.db.HistroyUtils;
import www.knowledgeshare.com.knowledgeshare.db.LookBean;
import www.knowledgeshare.com.knowledgeshare.db.LookUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.CommentMoreBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.OrderBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.SoftMusicDetailBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

public class LikeDetailActivity extends UMShareActivity implements View.OnClickListener {
    private ImageView iv_back;
    private ImageView iv_beijing;
    private TextView tv_download, tv_share;
    private TextView tv_search;
    private ImageView iv_shopcar;
    private TextView tv_guanzhu;
    private TextView tv_dianzan_count;
    private TextView tv_teacher_intro;
    private RecyclerView recycler_free;
    private TextView tv_shiyirenqun;
    private TextView tv_readxuzhi;
    private TextView tv_writeliuyan;
    private RecyclerView recycler_liuyan;
    private TextView tv_shopcar, tv_buy;
    private BaseDialog.Builder mBuilder;
    private BaseDialog mDialog;
    private boolean isDianzan;
    private boolean isGuanzhu = true;
    private boolean isZan = true;
    private ImageView iv_guanzhu, iv_dianzan;
    private NestedScrollView nestView;
    private boolean mIsCollected;
    private boolean mDianzan;
    private SpringView springview;
    private SoftMusicDetailBean mMusicDetailBean;
    private SoftMusicDetailBean.TeacherEntity mTeacher;
    private int mTeacher_zan_count;
    private List<SoftMusicDetailBean.ChildEntity> mChild;
    private LieBiaoAdapter mLieBiaoAdapter;
    private List<CommentMoreBean.DataEntity> mComment;
    private LiuYanAdapter mLiuYanAdapter;
    private int lastID;
    private TextView mTv_collect;
    private TextView mTv_dianzan;
    private boolean isRefreshing;
    private String mId;
    private BaseDialog mNetDialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_detail);
        initView();
        initDialog();
        initData();
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
                        SpUtils.putBoolean(LikeDetailActivity.this, "nowifiallowlisten", true);
                    }
                });
                mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNetDialog.dismiss();
                    }
                });
            }
        } else if (NetWorkUtils.isMobileConnected(LikeDetailActivity.this)) {
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
                loadMoreComment(lastID + "");
            }
        });
        springview.setHeader(new MyHeader(this));
        springview.setFooter(new MyFooter(this));
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        iv_shopcar = (ImageView) findViewById(R.id.iv_shopcar);
        iv_shopcar.setOnClickListener(this);
        tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);
        tv_guanzhu.setOnClickListener(this);
        tv_dianzan_count = (TextView) findViewById(R.id.tv_dianzan_count);
        tv_dianzan_count.setOnClickListener(this);
        tv_teacher_intro = (TextView) findViewById(R.id.tv_teacher_intro);
        tv_shopcar = (TextView) findViewById(R.id.tv_shopcar);
        tv_shopcar.setOnClickListener(this);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(this);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(this);
        recycler_free = (RecyclerView) findViewById(R.id.recycler_free);
        tv_shiyirenqun = (TextView) findViewById(R.id.tv_shiyirenqun);
        tv_readxuzhi = (TextView) findViewById(R.id.tv_readxuzhi);
        tv_writeliuyan = (TextView) findViewById(R.id.tv_writeliuyan);
        tv_writeliuyan.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        recycler_free.setLayoutManager(new LinearLayoutManager(this));
        recycler_free.setNestedScrollingEnabled(false);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        iv_guanzhu = (ImageView) findViewById(R.id.iv_guanzhu);
        iv_guanzhu.setOnClickListener(this);
        iv_dianzan = (ImageView) findViewById(R.id.iv_dianzan);
        iv_dianzan.setOnClickListener(this);
        springview = (SpringView) findViewById(R.id.springview);
        nestView = (NestedScrollView) findViewById(R.id.nestView);
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
        params.put("userid", SpUtils.getString(this, "id", ""));
        OkGo.<SoftMusicDetailBean>post(MyContants.LXKURL + "xk/show")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<SoftMusicDetailBean>(LikeDetailActivity.this, SoftMusicDetailBean.class) {
                             @Override
                             public void onSuccess(Response<SoftMusicDetailBean> response) {
                                 int code = response.code();
                                 mMusicDetailBean = response.body();
                                 Glide.with(LikeDetailActivity.this).load(mMusicDetailBean.getImgurl()).into(iv_beijing);
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
                                 tv_buy.setText("购买：" + mMusicDetailBean.getXk_price());
                                 mTeacher_zan_count = mTeacher.getT_live();
                                 tv_dianzan_count.setText(mTeacher_zan_count + "");
                                 tv_shiyirenqun.setText(mMusicDetailBean.getXk_suitable());
                                 tv_readxuzhi.setText(mMusicDetailBean.getXk_rss());
                                 mChild = mMusicDetailBean.getChild();
                                 mLieBiaoAdapter = new LieBiaoAdapter(R.layout.item_like_liebiao, mChild);
                                 recycler_free.setAdapter(mLieBiaoAdapter);
                                 mLieBiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         if (mLieBiaoAdapter.getData().get(position).getIs_try() != 1) {
                                             showIsBuyDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                                         } else {
                                             setISshow(true);
                                             SoftMusicDetailBean.ChildEntity item = mChild.get(position);
                                             PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getVideo_old_name(), item.getT_tag(), item.getVideo_url());
                                             gobofang(playerBean);
                                             addListenCount(item.getId() + "");
                                             MusicTypeBean musicTypeBean = new MusicTypeBean("softmusicdetail",
                                                     item.getT_header(), item.getVideo_old_name(), item.getId() + "", item.isIsfav());
                                             musicTypeBean.setMsg("musicplayertype");
                                             EventBus.getDefault().postSticky(musicTypeBean);
                                             List<PlayerBean> list = new ArrayList<PlayerBean>();
                                             for (int i = 0; i < mChild.size(); i++) {
                                                 SoftMusicDetailBean.ChildEntity entity = mChild.get(i);
                                                 PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_old_name(), entity.getT_tag(), entity.getVideo_url());
                                                 list.add(playerBean1);
                                             }
                                             MediaService.insertMusicList(list);
                                             if (!HistroyUtils.isInserted(item.getVideo_old_name())) {
                                                 BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("softmusicdetail", item.getId(), item.getVideo_old_name(),
                                                         item.getCreated_at(), item.getVideo_url(), item.getGood_count(),
                                                         item.getCollect_count(), item.getView_count(), item.isIslive(), item.isIsfav()
                                                         , item.getT_header(), item.getT_tag(),item.getShare_h5_url());
                                                 HistroyUtils.add(bofangHistroyBean);
                                             }
                                         }
                                     }
                                 });
                                 if (!isRefreshing) {
                                     loadMoreComment("");
                                 }
                                 isRefreshing = false;
                                 springview.onFinishFreshAndLoad();
                                 if (!LookUtils.isInserted(mMusicDetailBean.getXk_name())) {
                                     LookUtils.add(new LookBean(Integer.parseInt(mId), "like", mMusicDetailBean.getXk_name(),
                                             mMusicDetailBean.getTeacher().getT_name(),
                                             mMusicDetailBean.getXk_teacher_tags(), mMusicDetailBean.getXk_price()
                                             , SystemClock.currentThreadTimeMillis()));
                                 }else {
                                     LookUtils.updateTime(SystemClock.currentThreadTimeMillis(),
                                             mMusicDetailBean.getXk_name(),"like");
                                 }
                             }
                         }
                );
    }

    private void loadMoreComment(String after) {
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
                                    Toast.makeText(LikeDetailActivity.this, "已无更多评论", Toast.LENGTH_SHORT).show();
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

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    private class LieBiaoAdapter extends BaseQuickAdapter<SoftMusicDetailBean.ChildEntity, BaseViewHolder> {

        public LieBiaoAdapter(@LayoutRes int layoutResId, @Nullable List<SoftMusicDetailBean.ChildEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final SoftMusicDetailBean.ChildEntity item) {
            if (item.getIs_try() == 1) {
                helper.setVisible(R.id.tv_trylisten, true);
                helper.setVisible(R.id.iv_wengao, true);
            } else {
                helper.setVisible(R.id.tv_trylisten, false);
                helper.setVisible(R.id.iv_wengao, false);
            }

            helper.setText(R.id.tv_name, item.getVideo_old_name())
                    .setText(R.id.tv_time, item.getCreated_at() + "发布")
                    .setText(R.id.tv_look_count, item.getIs_view() == 0 ? item.getView_count() + "" : item.getView_count_true() + "")
                    .setText(R.id.tv_collect_count, item.getIs_collect() == 0 ? item.getCollect_count() + "" : item.getCollect_count_true() + "")
                    .setText(R.id.tv_dianzan_count, item.getIs_good() == 0 ? item.getGood_count() + "" : item.getGood_count_true() + "");
            helper.getView(R.id.iv_dian).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getIs_try() == 1) {
                        showListDialog(helper.getAdapterPosition(), item.isIsfav(), item.isIslive(), item.getId());
                    } else {
                        showIsBuyDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                    }
                }
            });
            helper.getView(R.id.iv_wengao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LikeDetailActivity.this, WenGaoActivity.class);
                    intent.putExtra("type", "softmusicdetail");
                    //                    intent.putExtra("t_name", mTeacher.getT_name());
                    //                    intent.putExtra("t_head", mTeacher.getT_header());
                    //                    intent.putExtra("video_name", item.getVideo_old_name());
                    //                    intent.putExtra("teacher_id", mMusicDetailBean.getXk_teacher_id() + "");
                    intent.putExtra("id", item.getId() + "");
                    startActivity(intent);
                }
            });
            helper.setText(R.id.tv_order, "0" + (helper.getAdapterPosition() + 1));
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
                SoftMusicDetailBean.ChildEntity childEntity = mChild.get(adapterPosition);
                String created_at = childEntity.getCreated_at();
                String[] split = created_at.split(" ");

                List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                listBean.setTypeId(childEntity.getXk_id()+"");
                listBean.setChildId(childEntity.getId()+"");
                listBean.setName(childEntity.getName());
                listBean.setVideoTime(childEntity.getVideo_time());
                listBean.setDate(split[0]);
                listBean.setTime(split[1]);
                listBean.setVideoUrl(childEntity.getVideo_url());
                listBean.setTxtUrl(childEntity.getTxt_url());
                listBean.setIconUrl(childEntity.getT_header());
                list.add(listBean);
                DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                        "xiaoke",mMusicDetailBean.getXk_class_id()+"", childEntity.getParent_name(),childEntity.getT_header(),
                        childEntity.getT_name(),childEntity.getT_tag(),mChild.size()+"",list);
                DownUtil.add(downLoadListsBean);

                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(childEntity.getId(), childEntity.getXk_id(),
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
                                ,childEntity.getXk_id()+"-"+childEntity.getId()+childEntity.getName()+".txt") {
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
                                 Toast.makeText(LikeDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                                 Toast.makeText(LikeDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                showPayStyleDialog();
            }
        });
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
                            mMusicDetailBean.getImgurl(), "", LikeDetailActivity.this, SHARE_MEDIA.WEIXIN);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", LikeDetailActivity.this, SHARE_MEDIA.WEIXIN);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mMusicDetailBean.getH5_url(), mMusicDetailBean.getXk_name(),
                            mMusicDetailBean.getImgurl(), "", LikeDetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", LikeDetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mMusicDetailBean.getH5_url(), mMusicDetailBean.getXk_name(),
                            mMusicDetailBean.getImgurl(), "", LikeDetailActivity.this, SHARE_MEDIA.QZONE);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", LikeDetailActivity.this, SHARE_MEDIA.QZONE);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mMusicDetailBean.getH5_url(), mMusicDetailBean.getXk_name(),
                            mMusicDetailBean.getImgurl(), "", LikeDetailActivity.this, SHARE_MEDIA.QQ);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", LikeDetailActivity.this, SHARE_MEDIA.QQ);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mMusicDetailBean.getH5_url(), mMusicDetailBean.getXk_name(),
                            mMusicDetailBean.getImgurl(), "", LikeDetailActivity.this, SHARE_MEDIA.SINA);
                } else {
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_old_name(),
                            entity.getT_header(), "", LikeDetailActivity.this, SHARE_MEDIA.SINA);
                }
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
                startActivity(new Intent(LikeDetailActivity.this, MyAccountActivity.class));
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
        params.put("type", "xiaoke");
        params.put("from", "android");
        OkGo.<OrderBean>post(MyContants.LXKURL + "order/buy")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<OrderBean>(LikeDetailActivity.this, OrderBean.class) {
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
                .execute(new DialogCallback<BaseBean>(LikeDetailActivity.this, BaseBean.class) {
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
                                     Toast.makeText(LikeDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                 }
                             }
                         }
                );
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
            Glide.with(mContext).load(item.getUser_avatar()).into(iv_head);
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
                                 Toast.makeText(LikeDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                                 Toast.makeText(LikeDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mComment.get(adapterPosition).setLive(count - 1);
                                 mLiuYanAdapter.notifyDataSetChanged();
                             }
                         }
                );
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
        //        unbindService(mServiceConnection);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_download:
                //TODO 跳转下载页面
                SoftMusicDetailBean model = mMusicDetailBean;
                intent = new Intent(this, DownLoadListActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
                break;
            case R.id.tv_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.iv_shopcar:
                startActivity(new Intent(this, ShoppingCartActivity.class));
                break;
            case R.id.tv_shopcar:
                insertShopCar();
                break;
            case R.id.tv_writeliuyan:
                Intent intent = new Intent(this, LiuYanActivity.class);
                intent.putExtra("teacher_id", mMusicDetailBean.getXk_teacher_id() + "");
                intent.putExtra("type", "softmusicdetail-root");
                intent.putExtra("xiaoke_id", mMusicDetailBean.getXk_class_id() + "");
                startActivity(intent);
                break;
            case R.id.tv_share:
                showShareDialog("root",0);
                break;
            case R.id.tv_buy:
                showPayStyleDialog();
                break;
            case R.id.tv_guanzhu:
            case R.id.iv_guanzhu:
                if (isGuanzhu) {
                    iv_guanzhu.setImageResource(R.drawable.free_quxiaoguanzhu);
                    noguanzhu(mMusicDetailBean.getXk_teacher_id());
                } else {
                    iv_guanzhu.setImageResource(R.drawable.free_guanzhu);
                    guanzhu(mMusicDetailBean.getXk_teacher_id());
                }
                isGuanzhu = !isGuanzhu;
                break;
            case R.id.tv_dianzan_count:
            case R.id.iv_dianzan:
                if (isZan) {
                    iv_dianzan.setImageResource(R.drawable.free_dianzan);
                    nodianzanTeacher(mMusicDetailBean.getXk_teacher_id());
                } else {
                    iv_dianzan.setImageResource(R.drawable.free_yizan);
                    dianzanTeacher(mMusicDetailBean.getXk_teacher_id());
                }
                isZan = !isZan;
                break;
        }
    }

    private void insertShopCar() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", mMusicDetailBean.getId());
        OkGo.<BaseBean>post(MyContants.LXKURL + "order/add-cart")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<BaseBean>(LikeDetailActivity.this, BaseBean.class) {
                             @Override
                             public void onSuccess(Response<BaseBean> response) {
                                 int code = response.code();
                                 BaseBean baseBean = response.body();
                                 Toast.makeText(LikeDetailActivity.this, baseBean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

    private void guanzhu(int teacher_id) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("teacher_id", teacher_id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-follow/attention")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 tv_guanzhu.setText("已关注");
                                 Toast.makeText(LikeDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

    private void noguanzhu(int teacher_id) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("teacher_id", teacher_id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-follow/no-attention")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 tv_guanzhu.setText("关注");
                                 Toast.makeText(LikeDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

    private void dianzanTeacher(int teacher_id) {
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
                                 Toast.makeText(LikeDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mTeacher_zan_count += 1;
                                 tv_dianzan_count.setText(mTeacher_zan_count + "");
                             }
                         }
                );
    }

    private void nodianzanTeacher(int teacher_id) {
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
                                 Toast.makeText(LikeDetailActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mTeacher_zan_count -= 1;
                                 tv_dianzan_count.setText(mTeacher_zan_count + "");
                             }
                         }
                );
    }
}
