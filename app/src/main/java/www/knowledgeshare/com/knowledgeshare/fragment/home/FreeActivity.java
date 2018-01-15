package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.CommentMoreBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.FreeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

import static www.knowledgeshare.com.knowledgeshare.R.id.tv_collect;
import static www.knowledgeshare.com.knowledgeshare.R.id.tv_dianzan;

public class FreeActivity extends UMShareActivity implements View.OnClickListener {

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
    private RecyclerView recycler_liuyan;
    private LinearLayout activity_free;
    private BaseDialog mDialog;
    private BaseDialog mNetDialog;
    private BaseDialog.Builder mBuilder;
    private boolean isGuanzhu;
    private boolean isZan;
    private ImageView iv_guanzhu, iv_dianzan;
    private NestedScrollView nestView;
    private boolean mIsCollected;
    private boolean mDianzan;
    private List<FreeBean.ChildEntity> mChild;
    private FreeBean.TeacherHasEntity mTeacher_has;
    private FreeBean mFreeBean;
    private List<CommentMoreBean.DataEntity> mComment;
    private LiuYanAdapter mLiuYanAdapter;
    private FreeAdapter mFreeAdapter;
    private SpringView springview;
    private int lastID;
    private int mTeacher_zan_count;
    private TextView mTv_collect;
    private TextView mTv_dianzan;
    private boolean isRefreshing;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        initView();
        initData();
        initDialog();
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
                loadMoreComment(lastID + "");
            }
        });
        springview.setHeader(new MyHeader(this));
        springview.setFooter(new MyFooter(this));
    }

    private void loadMoreComment(String after) {
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(this, "id", ""));
        params.put("after", after);
        OkGo.<CommentMoreBean>post(MyContants.LXKURL + "free/more-comment")
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
                                    Toast.makeText(FreeActivity.this, "已无更多评论", Toast.LENGTH_SHORT).show();
                                } else {
                                    mComment.addAll(data);
                                    mLiuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan, mComment);
                                    recycler_liuyan.setAdapter(mLiuYanAdapter);
                                    //                                    mLiuYanAdapter.notifyDataSetChanged();
                                }
                            }
                            springview.onFinishFreshAndLoad();
                        } else {
                        }
                    }
                });
    }

    private void initData() {
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(this, "id", ""));
        OkGo.<FreeBean>post(MyContants.LXKURL + "free")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<FreeBean>(FreeActivity.this, FreeBean.class) {
                    @Override
                    public void onSuccess(Response<FreeBean> response) {
                        int code = response.code();
                        mFreeBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            mTeacher_has = mFreeBean.getTeacher_has();
                            Glide.with(MyApplication.getGloableContext()).load(mFreeBean.getImgurl()).into(iv_beijing);
                            tv_teacher_intro.setText(mTeacher_has.getT_introduce());
                            isGuanzhu = mTeacher_has.isIsfollow();
                            if (isGuanzhu) {
                                iv_guanzhu.setImageResource(R.drawable.free_guanzhu);
                                tv_guanzhu.setText("已关注");
                            } else {
                                iv_guanzhu.setImageResource(R.drawable.free_quxiaoguanzhu);
                                tv_guanzhu.setText("关注");
                            }
                            isZan = mTeacher_has.isIslive();
                            if (isZan) {
                                iv_dianzan.setImageResource(R.drawable.free_yizan);
                            } else {
                                iv_dianzan.setImageResource(R.drawable.free_dianzan);
                            }
                            mTeacher_zan_count = mTeacher_has.getT_live();
                            tv_dianzan_count.setText(mTeacher_zan_count + "");
                            tv_shiyirenqun.setText(mFreeBean.getSuitable());
                            tv_readxuzhi.setText(mFreeBean.getLook());
                            mChild = mFreeBean.getChild();
                            mFreeAdapter = new FreeAdapter(R.layout.item_free, mChild);
                            recycler_free.setAdapter(mFreeAdapter);
                            mFreeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    setISshow(true);
                                    FreeBean.ChildEntity item = mChild.get(position);
                                    //刷新小型播放器
                                    PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getVideo_name(), item.getParent_name(),
                                            item.getVideo_url(),position);
                                    gobofang(playerBean);
                                    addListenCount(mFreeAdapter.getData().get(position).getId() + "");
                                    //设置进入播放主界面的数据
                                    List<MusicTypeBean> musicTypeBeanList=new ArrayList<MusicTypeBean>();
                                    for (int i = 0; i < mChild.size(); i++) {
                                        FreeBean.ChildEntity childEntity = mChild.get(i);
                                        MusicTypeBean musicTypeBean = new MusicTypeBean("free",
                                                mFreeBean.getTeacher_has().getT_header(), childEntity.getVideo_name(), childEntity.getId() + "",
                                                childEntity.isIsfav());
                                        musicTypeBean.setMsg("musicplayertype");
                                        musicTypeBeanList.add(musicTypeBean);
                                    }
                                    MediaService.insertMusicTypeList(musicTypeBeanList);
                                    //加入默认的播放列表
                                    List<PlayerBean> list = new ArrayList<PlayerBean>();
                                    for (int i = 0; i < mChild.size(); i++) {
                                        FreeBean.ChildEntity entity = mChild.get(i);
                                        PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_name(), entity.getParent_name(), entity.getVideo_url());
                                        list.add(playerBean1);
                                    }
                                    MediaService.insertMusicList(list);
                                    //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                                    List<BofangHistroyBean> histroyBeanList=new ArrayList<BofangHistroyBean>();
                                    for (int i = 0; i < mChild.size(); i++) {
                                        FreeBean.ChildEntity childEntity = mChild.get(i);
                                        BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("free", childEntity.getId(), childEntity.getVideo_name(),
                                                childEntity.getCreated_at(), childEntity.getVideo_url(), childEntity.getGood_count(),
                                                childEntity.getCollect_count(), childEntity.getView_count(), childEntity.isIslive(), childEntity.isIsfav(),
                                                childEntity.getT_header(), childEntity.getParent_name(),
                                                childEntity.getShare_h5_url(), SystemClock.currentThreadTimeMillis()
                                                ,"freeId",childEntity.getParent_name(),childEntity.getTxt_url());
                                        histroyBeanList.add(bofangHistroyBean);
                                    }
                                    MediaService.insertBoFangHistroyList(histroyBeanList);

                                }
                            });
                            if (!isRefreshing) {
                                loadMoreComment("");
                            }
                            isRefreshing = false;
                        } else {
                        }
                    }

                    @Override
                    public void onError(Response<FreeBean> response) {
                        super.onError(response);
                        Logger.e(response.getException().getMessage());
                    }
                });
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
                        SpUtils.putBoolean(FreeActivity.this, "nowifiallowlisten", true);
                    }
                });
                mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNetDialog.dismiss();
                    }
                });
            }
        } else if (NetWorkUtils.isMobileConnected(FreeActivity.this)) {
            Toast.makeText(this, "wifi不可用呢~", Toast.LENGTH_SHORT).show();
        } else {
            playerBean.setMsg("refreshplayer");
            EventBus.getDefault().postSticky(playerBean);
            mMyBinder.setMusicUrl(playerBean.getVideo_url());
            mMyBinder.playMusic(playerBean);
            ClickPopShow();
        }
    }

    private void addListenCount(String id) {
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("type", "free");
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

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
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
        recycler_free.setLayoutManager(new LinearLayoutManager(this));
        recycler_free.setNestedScrollingEnabled(false);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        iv_guanzhu = (ImageView) findViewById(R.id.iv_guanzhu);
        iv_guanzhu.setOnClickListener(this);
        iv_dianzan = (ImageView) findViewById(R.id.iv_dianzan);
        iv_dianzan.setOnClickListener(this);
        nestView = (NestedScrollView) findViewById(R.id.nestView);
        springview = (SpringView) findViewById(R.id.springview);
    }

    private class FreeAdapter extends BaseQuickAdapter<FreeBean.ChildEntity, BaseViewHolder> {

        public FreeAdapter(@LayoutRes int layoutResId, @Nullable List<FreeBean.ChildEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final FreeBean.ChildEntity item) {
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
                    Intent intent = new Intent(FreeActivity.this, WenGaoActivity.class);
                    intent.putExtra("type", "free");
                    //                    intent.putExtra("t_name", mTeacher_has.getT_name());
                    //                    intent.putExtra("t_head", mTeacher_has.getT_header());
                    //                    intent.putExtra("video_name", item.getVideo_name());
                    //                    intent.putExtra("teacher_id", mFreeBean.getTeacher_id() + "");
                    intent.putExtra("id", item.getId() + "");
                    startActivity(intent);
                }
            });
            helper.setText(R.id.tv_order, "0" + (helper.getAdapterPosition() + 1));
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
                                 Toast.makeText(FreeActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                                 Toast.makeText(FreeActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
        mDialog.getView(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mFreeBean.getH5_url(), mFreeBean.getName(),
                            mFreeBean.getImgurl(), "", FreeActivity.this, SHARE_MEDIA.WEIXIN);
                } else {
                    FreeBean.ChildEntity entity = mFreeAdapter.getData().get(adapterPosition);
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", FreeActivity.this, SHARE_MEDIA.WEIXIN);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mFreeBean.getH5_url(), mFreeBean.getName(),
                            mFreeBean.getImgurl(), "", FreeActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {
                    FreeBean.ChildEntity entity = mFreeAdapter.getData().get(adapterPosition);
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", FreeActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mFreeBean.getH5_url(), mFreeBean.getName(),
                            mFreeBean.getImgurl(), "", FreeActivity.this, SHARE_MEDIA.QZONE);
                } else {
                    FreeBean.ChildEntity entity = mFreeAdapter.getData().get(adapterPosition);
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", FreeActivity.this, SHARE_MEDIA.QZONE);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mFreeBean.getH5_url(), mFreeBean.getName(),
                            mFreeBean.getImgurl(), "", FreeActivity.this, SHARE_MEDIA.QQ);
                } else {
                    FreeBean.ChildEntity entity = mFreeAdapter.getData().get(adapterPosition);
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", FreeActivity.this, SHARE_MEDIA.QQ);
                }
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (root.equals("root")) {
                    shareWebUrl(mFreeBean.getH5_url(), mFreeBean.getName(),
                            mFreeBean.getImgurl(), "", FreeActivity.this, SHARE_MEDIA.SINA);
                } else {
                    FreeBean.ChildEntity entity = mFreeAdapter.getData().get(adapterPosition);
                    shareWebUrl(entity.getShare_h5_url(), entity.getVideo_name(),
                            entity.getT_header(), "", FreeActivity.this, SHARE_MEDIA.SINA);
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
        mTv_collect = mDialog.getView(tv_collect);
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
        mTv_dianzan = mDialog.getView(tv_dianzan);
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
                FreeBean.ChildEntity childEntity = mChild.get(adapterPosition);
                String created_at = childEntity.getCreated_at();
                String[] split = created_at.split(" ");
                List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                listBean.setTypeId("freeId");
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
                DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                        "free", listBean.getTypeId(), "", childEntity.getT_header(), "", "", list.size() + "", list);
                DownUtil.add(downLoadListsBean);
                /*DownLoadListBean DownLoadListBean = new DownLoadListBean(-1,childEntity.getChildId(),-4,-3,
                        childEntity.getVideo_name(),childEntity.getVideo_time(), split[0], split[1],
                        childEntity.getVideo_url(), childEntity.getTxt_url(),childEntity.getT_header());
                DownUtils.add(DownLoadListBean);*/
                GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                OkDownload.request(listBean.getTypeId() + "_" + childEntity.getId(), request)
                        .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download")
                        .fileName(childEntity.getVideo_name() + listBean.getTypeId() + "_" + childEntity.getId() + ".mp3")
                        .extra3(downLoadListsBean)//额外数据
                        .save()
                        .register(new LogDownloadListener())//当前任务的回调监听
                        .start();

                OkGo.<File>get(childEntity.getTxt_url())
                        .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download"
                                , listBean.getTypeId() + "-" + childEntity.getId() + childEntity.getVideo_name() + ".txt") {
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
            url = MyContants.LXKURL + "free/no-favorite";
        } else {
            url = MyContants.LXKURL + "free/favorite";
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
                                 Toast.makeText(FreeActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                                 mFreeAdapter.notifyDataSetChanged();
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
            url = MyContants.LXKURL + "free/no-dianzan";
        } else {
            url = MyContants.LXKURL + "free/dianzan";
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
                                 Toast.makeText(FreeActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                                 mFreeAdapter.notifyDataSetChanged();
                                 mDialog.dismiss();
                             }
                         }
                );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_download:
                FreeBean model = mFreeBean;
                intent = new Intent(this, FreeDownListActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
                break;
            case R.id.tv_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.tv_share:
                showShareDialog("root", 0);
                break;
            case R.id.tv_guanzhu:
            case R.id.iv_guanzhu:
                if (isGuanzhu) {
                    iv_guanzhu.setImageResource(R.drawable.free_quxiaoguanzhu);
                    noguanzhu(mFreeBean.getTeacher_id());
                } else {
                    iv_guanzhu.setImageResource(R.drawable.free_guanzhu);
                    guanzhu(mFreeBean.getTeacher_id());
                }
                isGuanzhu = !isGuanzhu;
                break;
            case R.id.tv_dianzan_count:
            case R.id.iv_dianzan:
                if (isZan) {
                    iv_dianzan.setImageResource(R.drawable.free_dianzan);
                    nodianzanTeacher(mFreeBean.getTeacher_id());
                } else {
                    iv_dianzan.setImageResource(R.drawable.free_yizan);
                    dianzanTeacher(mFreeBean.getTeacher_id());
                }
                isZan = !isZan;
                break;
            case R.id.tv_writeliuyan:
                Intent intent = new Intent(this, LiuYanActivity.class);
                intent.putExtra("teacher_id", mFreeBean.getTeacher_id() + "");
                intent.putExtra("type", "free-root");
                startActivity(intent);
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
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 tv_guanzhu.setText("已关注");
                                 Toast.makeText(FreeActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 tv_guanzhu.setText("关注");
                                 Toast.makeText(FreeActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                                 Toast.makeText(FreeActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                                 Toast.makeText(FreeActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mTeacher_zan_count -= 1;
                                 tv_dianzan_count.setText(mTeacher_zan_count + "");
                             }
                         }
                );
    }
}
