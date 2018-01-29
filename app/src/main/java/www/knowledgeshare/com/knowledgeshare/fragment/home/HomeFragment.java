package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.DownLoadActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.CommentListBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.FreeListBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HomeBannerBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HomeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HomeDaShiBanNewBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.LikeMoreBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BannerUtils;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

import static android.content.Context.BIND_AUTO_CREATE;


/**
 * Created by Administrator on 2017/11/17.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private TextView tv_search;
    private ImageView iv_download;
    private TextView tv_download_number;
    private LinearLayout ll_download;
    private Banner banner;
    private LinearLayout ll_gudian;
    private LinearLayout ll_minzu;
    private LinearLayout ll_liuxing;
    private LinearLayout ll_suyang;
    private LinearLayout ll_guanzhu;
    private TextView tv_lianxubofang;
    private CircleImageView iv_zhuanlan_head;
    private TextView tv_zhuanlan_name;
    private TextView tv_zhuanlan_more;
    private RecyclerView recycler_zhuanlan;
    private TextView tv_meiri_more;
    private RecyclerView recycler_comment;
    private RecyclerView recycler_dashiban;
    private TextView tv_dashi_lookmore;
    private RecyclerView recycler_yinyueke;
    private RecyclerView recycler_like;
    private NestedScrollView nestView;
    private SpringView springview;
    private List<String> bannerList = new ArrayList<>();
    private ImageView iv_delete, iv_bo_head, iv_arrow_top, iv_mulu;
    private TextView tv_title, tv_subtitle;
    private RelativeLayout rl_bofang;
    private BaseDialog mDialog;
    private ZhuanLanAdapter mZhuanLanAdapter;
    private CommentAdapter mCommentAdapter;
    private DaShiBanAdapter mDaShiBanAdapter;
    private YinYueKeAdapter mYinYueKeAdapter;
    private LikeAdapter mLikeAdapter;
    private LinearLayout ll_like_refresh, ll_dashi_refresh;
    private ImageView iv_dashi_refresh, iv_like_refresh;
    private TextView tv_yinyueke_lookmore;
    private boolean isBofang;
    private Animation mRotate_anim;
    private String mytype = "";
    private int myposition;
    private List<HomeBean.DailyEntity> mDaily;
    private HomeBean.FreeEntity mFree;
    private List<HomeBean.LiveEntity> mLive;
    private List<HomeBean.XiaokeEntity> mXiaoke;
    private List<HomeBean.ZhuanlanEntity> mZhuanlan;
    private DaShiBanNewAdapter mDaShiBanNewAdapter;
    private LikeNewAdapter mLikeNewAdapter;
    private List<HomeBean.FreeEntity.ChildEntity> mMFreeChild;
    private MusicTypeBean mMusicTypeBean;
    private ImageView iv_download_number;
    private List<CommentListBean.DataEntity> mCommentListBeanData;
    private List<FreeListBean.DataEntity> mFreeListBeanData;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        EventBus.getDefault().register(this);
        View inflate = View.inflate(mContext, R.layout.fragment_home, null);
        tv_search = (TextView) inflate.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        iv_download = (ImageView) inflate.findViewById(R.id.iv_download);
        tv_download_number = (TextView) inflate.findViewById(R.id.tv_download_number);
        iv_download_number = inflate.findViewById(R.id.iv_download_number);
        ll_download = (LinearLayout) inflate.findViewById(R.id.ll_download);
        ll_download.setOnClickListener(this);
        banner = (Banner) inflate.findViewById(R.id.banner);
        ll_gudian = (LinearLayout) inflate.findViewById(R.id.ll_gudian);
        ll_gudian.setOnClickListener(this);
        ll_minzu = (LinearLayout) inflate.findViewById(R.id.ll_minzu);
        ll_minzu.setOnClickListener(this);
        ll_liuxing = (LinearLayout) inflate.findViewById(R.id.ll_liuxing);
        ll_liuxing.setOnClickListener(this);
        ll_suyang = (LinearLayout) inflate.findViewById(R.id.ll_suyang);
        ll_suyang.setOnClickListener(this);
        ll_guanzhu = (LinearLayout) inflate.findViewById(R.id.ll_guanzhu);
        ll_guanzhu.setOnClickListener(this);
        tv_lianxubofang = (TextView) inflate.findViewById(R.id.tv_lianxubofang);
        tv_lianxubofang.setOnClickListener(this);
        iv_zhuanlan_head = (CircleImageView) inflate.findViewById(R.id.iv_zhuanlan_head);
        iv_zhuanlan_head.setOnClickListener(this);
        tv_zhuanlan_name = (TextView) inflate.findViewById(R.id.tv_zhuanlan_name);
        tv_zhuanlan_more = (TextView) inflate.findViewById(R.id.tv_zhuanlan_more);
        tv_zhuanlan_more.setOnClickListener(this);
        recycler_zhuanlan = (RecyclerView) inflate.findViewById(R.id.recycler_zhuanlan);
        tv_meiri_more = (TextView) inflate.findViewById(R.id.tv_meiri_more);
        tv_meiri_more.setOnClickListener(this);
        recycler_comment = (RecyclerView) inflate.findViewById(R.id.recycler_comment);
        ll_dashi_refresh = (LinearLayout) inflate.findViewById(R.id.ll_dashi_refresh);
        ll_dashi_refresh.setOnClickListener(this);
        recycler_dashiban = (RecyclerView) inflate.findViewById(R.id.recycler_dashiban);
        tv_dashi_lookmore = (TextView) inflate.findViewById(R.id.tv_dashi_lookmore);
        tv_dashi_lookmore.setOnClickListener(this);
        tv_yinyueke_lookmore = (TextView) inflate.findViewById(R.id.tv_yinyueke_lookmore);
        tv_yinyueke_lookmore.setOnClickListener(this);
        recycler_yinyueke = (RecyclerView) inflate.findViewById(R.id.recycler_yinyueke);
        ll_like_refresh = (LinearLayout) inflate.findViewById(R.id.ll_like_refresh);
        ll_like_refresh.setOnClickListener(this);
        recycler_like = (RecyclerView) inflate.findViewById(R.id.recycler_like);
        nestView = (NestedScrollView) inflate.findViewById(R.id.nestView);
        springview = (SpringView) inflate.findViewById(R.id.springview);
        iv_delete = inflate.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
        iv_delete.setVisibility(View.GONE);
        iv_arrow_top = inflate.findViewById(R.id.iv_arrow_top);
        iv_arrow_top.setOnClickListener(this);
        iv_mulu = inflate.findViewById(R.id.iv_mulu);
        iv_mulu.setOnClickListener(this);
        iv_bo_head = inflate.findViewById(R.id.iv_bo_head);
        tv_title = inflate.findViewById(R.id.tv_title);
        tv_subtitle = inflate.findViewById(R.id.tv_subtitle);
        rl_bofang = inflate.findViewById(R.id.rl_bofang);
        rl_bofang.setOnClickListener(this);
        rl_bofang.setVisibility(View.GONE);
        iv_dashi_refresh = inflate.findViewById(R.id.iv_dashi_refresh);
        iv_like_refresh = inflate.findViewById(R.id.iv_like_refresh);
        recycler_zhuanlan.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_zhuanlan.setNestedScrollingEnabled(false);
        recycler_comment.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_comment.setNestedScrollingEnabled(false);
        recycler_dashiban.setLayoutManager(new GridLayoutManager(mContext, 2));
        recycler_dashiban.setNestedScrollingEnabled(false);
        recycler_yinyueke.setLayoutManager(new GridLayoutManager(mContext, 2));
        recycler_yinyueke.setNestedScrollingEnabled(false);
        recycler_like.setLayoutManager(new GridLayoutManager(mContext, 3));
        recycler_like.setNestedScrollingEnabled(false);
        return inflate;
    }

    private void initAnim() {
        mRotate_anim = AnimationUtils.loadAnimation(mContext, R.anim.rotate_animation);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        mRotate_anim.setInterpolator(interpolator);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("home_bofang")) {
            isBofang = true;
            rl_bofang.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mytype)) {
                refreshbofang();
            }
        } else if (eventBean.getMsg().equals("home_pause")) {
            isBofang = false;
            iv_delete.setVisibility(View.VISIBLE);
            allpause();
        } else if (eventBean.getMsg().equals("home_close")) {
            isBofang = false;
            rl_bofang.setVisibility(View.GONE);
            allclose();
        } else if (eventBean.getMsg().equals("morenbofang")) {
            morenbofang();
        } else if (eventBean.getMsg().equals("number")) {
            initNumber();
        } else if (eventBean.getMsg().equals("suishenting")) {
            suishenting();
        }
    }

    private void suishenting() {
        morenbofang();
//        mytype = "comment";
//        myposition = 0;
        if (mCommentListBeanData != null && mCommentListBeanData.size() > 0) {
            CommentListBean.DataEntity dataEntity = mCommentListBeanData.get(myposition);
            if (dataEntity != null) {
                //还要设置一个播放主界面的list数据，因为自动播放下一首上一首的时候主界面的数据也得变
                List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                for (int i = 0; i < mCommentListBeanData.size(); i++) {
                    CommentListBean.DataEntity dataEntity1 = mCommentListBeanData.get(i);
                    MusicTypeBean musicTypeBean = new MusicTypeBean("everydaycomment",
                            dataEntity1.getT_header(), dataEntity1.getVideo_name(), dataEntity1.getId() + "",
                            dataEntity1.getIs_collect() == 0 ? false : true);
                    musicTypeBean.setMsg("musicplayertype");
                    musicTypeBeanList.add(musicTypeBean);
                }
                MediaService.insertMusicTypeList2(musicTypeBeanList);
                //加入默认的播放列表
                List<PlayerBean> playerBeanList = new ArrayList<PlayerBean>();
                for (int i = 0; i < mCommentListBeanData.size(); i++) {
                    CommentListBean.DataEntity dataEntity1 = mCommentListBeanData.get(i);
                    PlayerBean playerBean1 = new PlayerBean(dataEntity1.getT_header(), dataEntity1.getVideo_name(),
                            dataEntity1.getT_tag(), dataEntity1.getVideo_url());
                    playerBeanList.add(playerBean1);
                }
                MediaService.insertMusicList2(playerBeanList);

                //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                for (int i = 0; i < mCommentListBeanData.size(); i++) {
                    CommentListBean.DataEntity dataEntity1 = mCommentListBeanData.get(i);
                    BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("everydaycomment", dataEntity1.getId(), dataEntity1.getVideo_name(),
                            dataEntity1.getCreated_at(), dataEntity1.getVideo_url(), dataEntity1.getGood_count(),
                            dataEntity1.getCollect_count(), dataEntity1.getView_count(), dataEntity1.getIs_good() == 1 ? true : false,
                            dataEntity1.isIsfav(), dataEntity1.getT_header(), dataEntity1.getT_tag(), dataEntity1.getShare_h5_url()
                            , SystemClock.currentThreadTimeMillis(), "commentId", dataEntity1.getParent_name(), dataEntity1.getTxt_url());
                    histroyBeanList.add(bofangHistroyBean);
                }
                MediaService.insertBoFangHistroyList2(histroyBeanList);
            }
        } else {
            //还要设置一个播放主界面的list数据，因为自动播放下一首上一首的时候主界面的数据也得变
            List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
            for (int i = 0; i < mDaily.size(); i++) {
                HomeBean.DailyEntity dailyEntity = mDaily.get(i);
                MusicTypeBean musicTypeBean = new MusicTypeBean("everydaycomment",
                        dailyEntity.getT_header(), dailyEntity.getVideo_name(), dailyEntity.getId() + "",
                        dailyEntity.getIs_collect() == 0 ? false : true);
                musicTypeBean.setMsg("musicplayertype");
                musicTypeBeanList.add(musicTypeBean);
            }
            MediaService.insertMusicTypeList2(musicTypeBeanList);
            //加入默认的播放列表
            List<PlayerBean> playerBeanList = new ArrayList<PlayerBean>();
            for (int i = 0; i < mDaily.size(); i++) {
                HomeBean.DailyEntity entity = mDaily.get(i);
                PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_name(), entity.getT_tag(), entity.getVideo_url());
                playerBeanList.add(playerBean1);
            }
            MediaService.insertMusicList2(playerBeanList);

            //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
            List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
            for (int i = 0; i < mDaily.size(); i++) {
                HomeBean.DailyEntity dailyEntity = mDaily.get(i);
                BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("everydaycomment", dailyEntity.getId(), dailyEntity.getVideo_name(),
                        dailyEntity.getCreated_at(), dailyEntity.getVideo_url(), dailyEntity.getGood_count(),
                        dailyEntity.getCollect_count(), dailyEntity.getView_count(), dailyEntity.getIs_good() == 1 ? true : false,
                        dailyEntity.isIsfav(), dailyEntity.getT_header(), dailyEntity.getT_tag(), dailyEntity.getShare_h5_url()
                        , SystemClock.currentThreadTimeMillis(), "commentId", dailyEntity.getParent_name(), dailyEntity.getTxt_url());
                histroyBeanList.add(bofangHistroyBean);
            }
            MediaService.insertBoFangHistroyList2(histroyBeanList);
        }
    }

    private void morenbofang() {
//        isBofang = true;
//        rl_bofang.setVisibility(View.VISIBLE);
//        iv_delete.setVisibility(View.GONE);
        mytype = "zhuanlan";
        myposition = 0;
        if (mFreeListBeanData != null && mFreeListBeanData.size() > 0) {
            FreeListBean.DataEntity dataEntity = mFreeListBeanData.get(myposition);
            if (dataEntity != null) {
                //刷新小型播放器
                PlayerBean playerBean = new PlayerBean(dataEntity.getT_header(), dataEntity.getVideo_name(), dataEntity.getParent_name(),
                        dataEntity.getVideo_url(), myposition);
                gobofang(playerBean);
                //设置进入播放主界面的数据
                mMusicTypeBean = new MusicTypeBean("free",
                        dataEntity.getT_header(), dataEntity.getVideo_name(), dataEntity.getId() + "",
                        dataEntity.getIs_collect() == 0 ? false : true);
                mMusicTypeBean.setMsg("musicplayertype");
                EventBus.getDefault().postSticky(mMusicTypeBean);
                //还要设置一个播放主界面的list数据，因为自动播放下一首上一首的时候主界面的数据也得变
                List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                for (int i = 0; i < mFreeListBeanData.size(); i++) {
                    FreeListBean.DataEntity dataEntity1 = mFreeListBeanData.get(i);
                    MusicTypeBean musicTypeBean = new MusicTypeBean("free",
                            dataEntity1.getT_header(), dataEntity1.getVideo_name(), dataEntity1.getId() + "",
                            dataEntity1.getIs_collect() == 0 ? false : true);
                    musicTypeBean.setMsg("musicplayertype");
                    musicTypeBeanList.add(musicTypeBean);
                }
                MediaService.insertMusicTypeList(musicTypeBeanList);

                //加入默认的播放列表
                List<PlayerBean> playerBeanList = new ArrayList<PlayerBean>();
                for (int i = 0; i < mFreeListBeanData.size(); i++) {
                    FreeListBean.DataEntity dataEntity1 = mFreeListBeanData.get(i);
                    PlayerBean playerBean1 = new PlayerBean(dataEntity1.getT_header(), dataEntity1.getVideo_name(),
                            dataEntity1.getParent_name(), dataEntity1.getVideo_url());
                    playerBeanList.add(playerBean1);
                }
                MediaService.insertMusicList(playerBeanList);
                //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                for (int i = 0; i < mFreeListBeanData.size(); i++) {
                    FreeListBean.DataEntity dataEntity1 = mFreeListBeanData.get(i);
                    BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("free", dataEntity1.getId(), dataEntity1.getVideo_name(),
                            dataEntity1.getCreated_at(), dataEntity1.getVideo_url(), dataEntity1.getGood_count(),
                            dataEntity1.getCollect_count(), dataEntity1.getView_count(), dataEntity1.getIs_good() == 1 ? true : false,
                            dataEntity1.isIsfav(), dataEntity1.getT_header(), dataEntity1.getParent_name(), dataEntity1.getShare_h5_url()
                            , SystemClock.currentThreadTimeMillis(), "freeId", dataEntity1.getParent_name(), dataEntity1.getTxt_url());
                    histroyBeanList.add(bofangHistroyBean);
                }
                MediaService.insertBoFangHistroyList(histroyBeanList);
            }
        } else {
            HomeBean.FreeEntity.ChildEntity item = mMFreeChild.get(0);
            //刷新小型播放器
            PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getVideo_name(), item.getParent_name(),
                    item.getVideo_url(), 0);
            gobofang(playerBean);
            //设置进入播放主界面的数据
            mMusicTypeBean = new MusicTypeBean("free",
                    item.getT_header(), item.getVideo_name(), item.getId() + "",
                    item.getIs_collect() == 0 ? false : true);
            mMusicTypeBean.setMsg("musicplayertype");
            EventBus.getDefault().postSticky(mMusicTypeBean);
            //还要设置一个播放主界面的list数据，因为自动播放下一首上一首的时候主界面的数据也得变
            List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
            for (int i = 0; i < mMFreeChild.size(); i++) {
                HomeBean.FreeEntity.ChildEntity childEntity = mMFreeChild.get(i);
                MusicTypeBean musicTypeBean = new MusicTypeBean("free",
                        childEntity.getT_header(), childEntity.getVideo_name(), childEntity.getId() + "",
                        childEntity.getIs_collect() == 0 ? false : true);
                musicTypeBean.setMsg("musicplayertype");
                musicTypeBeanList.add(musicTypeBean);
            }
            MediaService.insertMusicTypeList(musicTypeBeanList);

            //加入默认的播放列表
            List<PlayerBean> playerBeanList = new ArrayList<PlayerBean>();
            for (int i = 0; i < mMFreeChild.size(); i++) {
                HomeBean.FreeEntity.ChildEntity entity = mMFreeChild.get(i);
                PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_name(), entity.getParent_name(), entity.getVideo_url());
                playerBeanList.add(playerBean1);
            }
            MediaService.insertMusicList(playerBeanList);
            //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
            List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
            for (int i = 0; i < mMFreeChild.size(); i++) {
                HomeBean.FreeEntity.ChildEntity childEntity = mMFreeChild.get(i);
                BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("free", childEntity.getId(), childEntity.getVideo_name(),
                        childEntity.getCreated_at(), childEntity.getVideo_url(), childEntity.getGood_count(),
                        childEntity.getCollect_count(), childEntity.getView_count(), childEntity.getIs_good() == 1 ? true : false,
                        childEntity.isIsfav(), childEntity.getT_header(), childEntity.getParent_name(), childEntity.getShare_h5_url()
                        , SystemClock.currentThreadTimeMillis(), "freeId", childEntity.getParent_name(), childEntity.getTxt_url());
                histroyBeanList.add(bofangHistroyBean);
            }
            MediaService.insertBoFangHistroyList(histroyBeanList);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(PlayerBean playerBean) {
        if (playerBean.getMsg().equals("refreshplayer")) {//当在其他界面点击播放的时候homeFragment也要更新小型播放器
            Glide.with(MyApplication.getGloableContext()).load(playerBean.getTeacher_head()).into(iv_bo_head);
            tv_title.setText(playerBean.getTitle());
            tv_subtitle.setText(playerBean.getSubtitle());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(MusicTypeBean musicTypeBean) {
        if (musicTypeBean.getMsg().equals("musicplayertype")) {
            //因为homefragment和其他界面的popupwindow不是一个，所以这边也要接收一下，免得返回的时候点击进入音乐播放主界面出现错误
            mMusicTypeBean = musicTypeBean;
        }
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("tagid", SpUtils.getString(mContext, "tag", ""));
        params.put("userid", SpUtils.getString(mContext, "id", ""));
        OkGo.<HomeBean>post(MyContants.LXKURL + "index")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<HomeBean>(mActivity, HomeBean.class) {
                    @Override
                    public void onSuccess(Response<HomeBean> response) {
                        int code = response.code();
                        HomeBean homeBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            mDaily = homeBean.getDaily();
                            mFree = homeBean.getFree();
                            mLive = homeBean.getLive();
                            mXiaoke = homeBean.getXiaoke();
                            mZhuanlan = homeBean.getZhuanlan();
                            mDaShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban, mZhuanlan);
                            recycler_dashiban.setAdapter(mDaShiBanAdapter);
                            mDaShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(mContext, ZhuanLanActivity.class);
                                    intent.putExtra("id", mDaShiBanAdapter.getData().get(position).getId() + "");
                                    startActivity(intent);
                                }
                            });
                            mYinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke, mXiaoke);
                            recycler_yinyueke.setAdapter(mYinYueKeAdapter);
                            mYinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(mContext, SoftMusicDetailActivity.class);
                                    intent.putExtra("id", mYinYueKeAdapter.getData().get(position).getXk_id() + "");
                                    startActivity(intent);
                                }
                            });
                            mLikeAdapter = new LikeAdapter(R.layout.item_like, mLive);
                            recycler_like.setAdapter(mLikeAdapter);
                            mLikeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(mContext, LikeDetailActivity.class);
                                    intent.putExtra("id", mLikeAdapter.getData().get(position).getXk_id() + "");
                                    startActivity(intent);
                                }
                            });
                            Glide.with(MyApplication.getGloableContext()).load(mFree.getT_header()).into(iv_zhuanlan_head);
                            mMFreeChild = mFree.getChild();
                            mZhuanLanAdapter = new ZhuanLanAdapter(R.layout.item_zhuanlan, mMFreeChild);
                            recycler_zhuanlan.setAdapter(mZhuanLanAdapter);

                            mCommentAdapter = new CommentAdapter(R.layout.item_zhuanlan, mDaily);
                            recycler_comment.setAdapter(mCommentAdapter);
                        } else {

                        }

                    }
                });
        initbanner();
        initFreeList();
        initCommentList();
        initDialog();
        initAnim();
        initListener();
        initMusic();
        initNumber();
    }

    private void initCommentList() {
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(mContext, "id", ""));
        OkGo.<CommentListBean>post(MyContants.LXKURL + "index/daily-list")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<CommentListBean>(CommentListBean.class) {
                    @Override
                    public void onSuccess(Response<CommentListBean> response) {
                        int code = response.code();
                        CommentListBean commentListBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            mCommentListBeanData = commentListBean.getData();
                        } else {
                        }
                    }
                });
    }

    private void initFreeList() {
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(mContext, "id", ""));
        OkGo.<FreeListBean>post(MyContants.LXKURL + "index/free-list")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<FreeListBean>(FreeListBean.class) {
                    @Override
                    public void onSuccess(Response<FreeListBean> response) {
                        int code = response.code();
                        FreeListBean freeListBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            mFreeListBeanData = freeListBean.getData();
                        } else {
                        }
                    }
                });
    }

    private void initNumber() {
        int TYPE_ING = 2;
        List<DownloadTask> taskList = OkDownload.restore(DownloadManager.getInstance().getDownloading());
        for (int i = 0; i < taskList.size(); i++) {
            DownloadTask task = taskList.get(i);
            String tag = TYPE_ING + "_" + task.progress.tag;
            task.register(new ListDownloadListener(tag));
        }
        if (taskList.size() == 0) {
            tv_download_number.setText("");
            iv_download_number.setVisibility(View.GONE);
        } else {
            iv_download_number.setVisibility(View.VISIBLE);
            tv_download_number.setText(taskList.size() + "");
            Logger.e("正在下载的数量：" + taskList.size());
        }
    }

    private class ListDownloadListener extends DownloadListener {

        public ListDownloadListener(Object tag) {
            super(tag);
        }

        @Override
        public void onStart(Progress progress) {
        }

        @Override
        public void onProgress(Progress progress) {

        }

        @Override
        public void onError(Progress progress) {
            Throwable throwable = progress.exception;
            if (throwable != null)
                throwable.printStackTrace();
        }

        @Override
        public void onFinish(File file, Progress progress) {
            //            Toast.makeText(context, "下载完成:" + progress.filePath, Toast.LENGTH_SHORT).show();
            EventBean eventBean = new EventBean("number");
            EventBus.getDefault().postSticky(eventBean);
        }

        @Override
        public void onRemove(Progress progress) {

        }
    }

    private void initbanner() {
        HttpParams params2 = new HttpParams();
        //        params2.put("type", false);
        params2.put("from", "android");
        OkGo.<HomeBannerBean>get(MyContants.LXKURL + "bootstrappers")
                .tag(this)
                .params(params2)
                .execute(new JsonCallback<HomeBannerBean>(HomeBannerBean.class) {
                    @Override
                    public void onSuccess(Response<HomeBannerBean> response) {
                        int code = response.code();
                        HomeBannerBean bannerBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            final List<HomeBannerBean.HomeslideEntity> homeslide = bannerBean.getHomeslide();
                            if (bannerList != null) {
                                bannerList.clear();
                            }
                            for (int i = 0; i < homeslide.size(); i++) {
                                String imgurl = homeslide.get(i).getImgurl();
                                bannerList.add(imgurl);
                            }
                            ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
                            layoutParams.height = MyUtils.getScreenWidth(mContext) / 2;
                            banner.setLayoutParams(layoutParams);
                            BannerUtils.startBanner(banner, bannerList);
                            banner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(int position) {
                                    HomeBannerBean.HomeslideEntity entity = homeslide.get(position);
                                    if (entity != null) {
                                        if (entity.getCourse_id() == 0) {
                                            Intent intent = new Intent(mContext, WebActivity.class);
                                            intent.putExtra("url", entity.getLink());
                                            startActivity(intent);
                                        } else {
                                            if (entity.getType() == 1) {
                                                Intent intent = new Intent(mContext, SoftMusicDetailActivity.class);
                                                intent.putExtra("id", entity.getCourse_id() + "");
                                                startActivity(intent);
                                            } else if (entity.getType() == 2) {
                                                Intent intent = new Intent(mContext, ZhuanLanActivity.class);
                                                intent.putExtra("id", entity.getCourse_id() + "");
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }
                            });
                        } else {

                        }
                    }
                });
    }

    //“绑定”服务的intent
    private Intent MediaServiceIntent;
    private MediaService.MyBinder mMyBinder;

    private void initMusic() {
        MediaServiceIntent = new Intent(mContext, MediaService.class);
        //        mContext.startService(MediaServiceIntent);
        mContext.bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void initListener() {
        nestView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (isBofang) {
                    if (scrollY - oldScrollY > 0) {
                        rl_bofang.setVisibility(View.GONE);
                    } else if (scrollY - oldScrollY < 0) {
                        rl_bofang.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (!NetWorkUtils.isNetworkConnected(mContext)){
                    Toast.makeText(mContext, "当前无网络连接，请检查设置", Toast.LENGTH_SHORT).show();
                    return;
                }
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
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        springview.onFinishFreshAndLoad();
//                    }
//                }, 2000);
            }
        });
        springview.setHeader(new MyHeader(mContext));
    }

    private void initDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(mContext);
        mDialog = builder.setViewId(R.layout.dialog_iswifi)
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


    private class ZhuanLanAdapter extends BaseQuickAdapter<HomeBean.FreeEntity.ChildEntity, BaseViewHolder> {

        public ZhuanLanAdapter(@LayoutRes int layoutResId, @Nullable List<HomeBean.FreeEntity.ChildEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final HomeBean.FreeEntity.ChildEntity item) {
            final ImageView iv_pause = helper.getView(R.id.iv_pause);
            if (item.isChecked()) {
                iv_pause.setImageResource(R.drawable.bofang_yellow);
            } else {
                iv_pause.setImageResource(R.drawable.pause_yellow);
            }
            helper.setText(R.id.tv_video_name, item.getVideo_name());
            helper.getView(R.id.rl_root_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mytype = "zhuanlan";
                    myposition = helper.getAdapterPosition();
                    if (mFreeListBeanData != null && mFreeListBeanData.size() > 0) {
                        FreeListBean.DataEntity dataEntity = mFreeListBeanData.get(myposition);
                        if (dataEntity != null) {
                            //刷新小型播放器
                            PlayerBean playerBean = new PlayerBean(dataEntity.getT_header(), dataEntity.getVideo_name(), dataEntity.getParent_name(),
                                    dataEntity.getVideo_url(), myposition);
                            gobofang(playerBean);
                            //设置进入播放主界面的数据
                            mMusicTypeBean = new MusicTypeBean("free",
                                    dataEntity.getT_header(), dataEntity.getVideo_name(), dataEntity.getId() + "",
                                    dataEntity.getIs_collect() == 0 ? false : true);
                            mMusicTypeBean.setMsg("musicplayertype");
                            EventBus.getDefault().postSticky(mMusicTypeBean);
                            //还要设置一个播放主界面的list数据，因为自动播放下一首上一首的时候主界面的数据也得变
                            List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                            for (int i = 0; i < mFreeListBeanData.size(); i++) {
                                FreeListBean.DataEntity dataEntity1 = mFreeListBeanData.get(i);
                                MusicTypeBean musicTypeBean = new MusicTypeBean("free",
                                        dataEntity1.getT_header(), dataEntity1.getVideo_name(), dataEntity1.getId() + "",
                                        dataEntity1.getIs_collect() == 0 ? false : true);
                                musicTypeBean.setMsg("musicplayertype");
                                musicTypeBeanList.add(musicTypeBean);
                            }
                            MediaService.insertMusicTypeList(musicTypeBeanList);

                            //加入默认的播放列表
                            List<PlayerBean> playerBeanList = new ArrayList<PlayerBean>();
                            for (int i = 0; i < mFreeListBeanData.size(); i++) {
                                FreeListBean.DataEntity dataEntity1 = mFreeListBeanData.get(i);
                                PlayerBean playerBean1 = new PlayerBean(dataEntity1.getT_header(), dataEntity1.getVideo_name(),
                                        dataEntity1.getParent_name(), dataEntity1.getVideo_url());
                                playerBeanList.add(playerBean1);
                            }
                            MediaService.insertMusicList(playerBeanList);
                            //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                            List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                            for (int i = 0; i < mFreeListBeanData.size(); i++) {
                                FreeListBean.DataEntity dataEntity1 = mFreeListBeanData.get(i);
                                BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("free", dataEntity1.getId(), dataEntity1.getVideo_name(),
                                        dataEntity1.getCreated_at(), dataEntity1.getVideo_url(), dataEntity1.getGood_count(),
                                        dataEntity1.getCollect_count(), dataEntity1.getView_count(), dataEntity1.getIs_good() == 1 ? true : false,
                                        dataEntity1.isIsfav(), dataEntity1.getT_header(), dataEntity1.getParent_name(), dataEntity1.getShare_h5_url()
                                        , SystemClock.currentThreadTimeMillis(), "freeId", dataEntity1.getParent_name(), dataEntity1.getTxt_url());
                                histroyBeanList.add(bofangHistroyBean);
                            }
                            MediaService.insertBoFangHistroyList(histroyBeanList);
                        }
                    } else {
                        //刷新小型播放器
                        PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getVideo_name(), item.getParent_name(),
                                item.getVideo_url(), myposition);
                        gobofang(playerBean);
                        //设置进入播放主界面的数据
                        mMusicTypeBean = new MusicTypeBean("free",
                                item.getT_header(), item.getVideo_name(), item.getId() + "",
                                item.getIs_collect() == 0 ? false : true);
                        mMusicTypeBean.setMsg("musicplayertype");
                        EventBus.getDefault().postSticky(mMusicTypeBean);
                        //还要设置一个播放主界面的list数据，因为自动播放下一首上一首的时候主界面的数据也得变
                        List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                        for (int i = 0; i < mMFreeChild.size(); i++) {
                            HomeBean.FreeEntity.ChildEntity childEntity = mMFreeChild.get(i);
                            MusicTypeBean musicTypeBean = new MusicTypeBean("free",
                                    childEntity.getT_header(), childEntity.getVideo_name(), childEntity.getId() + "",
                                    childEntity.getIs_collect() == 0 ? false : true);
                            musicTypeBean.setMsg("musicplayertype");
                            musicTypeBeanList.add(musicTypeBean);
                        }
                        MediaService.insertMusicTypeList(musicTypeBeanList);

                        //加入默认的播放列表
                        List<PlayerBean> playerBeanList = new ArrayList<PlayerBean>();
                        for (int i = 0; i < mMFreeChild.size(); i++) {
                            HomeBean.FreeEntity.ChildEntity entity = mMFreeChild.get(i);
                            PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_name(), entity.getParent_name(), entity.getVideo_url());
                            playerBeanList.add(playerBean1);
                        }
                        MediaService.insertMusicList(playerBeanList);
                        //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                        List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                        for (int i = 0; i < mMFreeChild.size(); i++) {
                            HomeBean.FreeEntity.ChildEntity childEntity = mMFreeChild.get(i);
                            BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("free", childEntity.getId(), childEntity.getVideo_name(),
                                    childEntity.getCreated_at(), childEntity.getVideo_url(), childEntity.getGood_count(),
                                    childEntity.getCollect_count(), childEntity.getView_count(), childEntity.getIs_good() == 1 ? true : false,
                                    childEntity.isIsfav(), childEntity.getT_header(), childEntity.getParent_name(), childEntity.getShare_h5_url()
                                    , SystemClock.currentThreadTimeMillis(), "freeId", childEntity.getParent_name(), childEntity.getTxt_url());
                            histroyBeanList.add(bofangHistroyBean);
                        }
                        MediaService.insertBoFangHistroyList(histroyBeanList);
                    }
                }
            });
        }
    }

    private class CommentAdapter extends BaseQuickAdapter<HomeBean.DailyEntity, BaseViewHolder> {

        public CommentAdapter(@LayoutRes int layoutResId, @Nullable List<HomeBean.DailyEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final HomeBean.DailyEntity item) {
            helper.getView(R.id.tv_teacher_name).setVisibility(View.VISIBLE);
            final ImageView iv_pause = helper.getView(R.id.iv_pause);
            if (item.isChecked()) {
                iv_pause.setImageResource(R.drawable.bofang_yellow);
            } else {
                iv_pause.setImageResource(R.drawable.pause_yellow);
            }
            helper.setText(R.id.tv_video_name, item.getVideo_name())
                    .setText(R.id.tv_teacher_name, item.getT_name());
            helper.getView(R.id.rl_root_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mytype = "comment";
                    myposition = helper.getAdapterPosition();
                    if (mCommentListBeanData != null && mCommentListBeanData.size() > 0) {
                        CommentListBean.DataEntity dataEntity = mCommentListBeanData.get(myposition);
                        if (dataEntity != null) {
                            //刷新小型播放器
                            PlayerBean playerBean = new PlayerBean(dataEntity.getT_header(), dataEntity.getVideo_name(), dataEntity.getT_tag(),
                                    dataEntity.getVideo_url(), myposition);
                            gobofang(playerBean);
                            //设置进入播放主界面的数据
                            mMusicTypeBean = new MusicTypeBean("everydaycomment",
                                    dataEntity.getT_header(), dataEntity.getVideo_name(), dataEntity.getId() + "",
                                    dataEntity.isIsfav());
                            mMusicTypeBean.setMsg("musicplayertype");
                            EventBus.getDefault().postSticky(mMusicTypeBean);
                            //还要设置一个播放主界面的list数据，因为自动播放下一首上一首的时候主界面的数据也得变
                            List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                            for (int i = 0; i < mCommentListBeanData.size(); i++) {
                                CommentListBean.DataEntity dataEntity1 = mCommentListBeanData.get(i);
                                MusicTypeBean musicTypeBean = new MusicTypeBean("everydaycomment",
                                        dataEntity1.getT_header(), dataEntity1.getVideo_name(), dataEntity1.getId() + "",
                                        dataEntity1.getIs_collect() == 0 ? false : true);
                                musicTypeBean.setMsg("musicplayertype");
                                musicTypeBeanList.add(musicTypeBean);
                            }
                            MediaService.insertMusicTypeList(musicTypeBeanList);
                            //加入默认的播放列表
                            List<PlayerBean> playerBeanList = new ArrayList<PlayerBean>();
                            for (int i = 0; i < mCommentListBeanData.size(); i++) {
                                CommentListBean.DataEntity dataEntity1 = mCommentListBeanData.get(i);
                                PlayerBean playerBean1 = new PlayerBean(dataEntity1.getT_header(), dataEntity1.getVideo_name(),
                                        dataEntity1.getT_tag(), dataEntity1.getVideo_url());
                                playerBeanList.add(playerBean1);
                            }
                            MediaService.insertMusicList(playerBeanList);

                            //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                            List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                            for (int i = 0; i < mCommentListBeanData.size(); i++) {
                                CommentListBean.DataEntity dataEntity1 = mCommentListBeanData.get(i);
                                BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("everydaycomment", dataEntity1.getId(), dataEntity1.getVideo_name(),
                                        dataEntity1.getCreated_at(), dataEntity1.getVideo_url(), dataEntity1.getGood_count(),
                                        dataEntity1.getCollect_count(), dataEntity1.getView_count(), dataEntity1.getIs_good() == 1 ? true : false,
                                        dataEntity1.isIsfav(), dataEntity1.getT_header(), dataEntity1.getT_tag(), dataEntity1.getShare_h5_url()
                                        , SystemClock.currentThreadTimeMillis(), "commentId", dataEntity1.getParent_name(), dataEntity1.getTxt_url());
                                histroyBeanList.add(bofangHistroyBean);
                            }
                            MediaService.insertBoFangHistroyList(histroyBeanList);
                        }
                    } else {
                        //刷新小型播放器
                        PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getVideo_name(), item.getT_tag(),
                                item.getVideo_url(), helper.getAdapterPosition());
                        gobofang(playerBean);
                        //设置进入播放主界面的数据
                        mMusicTypeBean = new MusicTypeBean("everydaycomment",
                                item.getT_header(), item.getVideo_name(), item.getId() + "",
                                item.isIsfav());
                        mMusicTypeBean.setMsg("musicplayertype");
                        EventBus.getDefault().postSticky(mMusicTypeBean);
                        //还要设置一个播放主界面的list数据，因为自动播放下一首上一首的时候主界面的数据也得变
                        List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                        for (int i = 0; i < mDaily.size(); i++) {
                            HomeBean.DailyEntity dailyEntity = mDaily.get(i);
                            MusicTypeBean musicTypeBean = new MusicTypeBean("everydaycomment",
                                    dailyEntity.getT_header(), dailyEntity.getVideo_name(), dailyEntity.getId() + "",
                                    dailyEntity.getIs_collect() == 0 ? false : true);
                            musicTypeBean.setMsg("musicplayertype");
                            musicTypeBeanList.add(musicTypeBean);
                        }
                        MediaService.insertMusicTypeList(musicTypeBeanList);
                        //加入默认的播放列表
                        List<PlayerBean> playerBeanList = new ArrayList<PlayerBean>();
                        for (int i = 0; i < mDaily.size(); i++) {
                            HomeBean.DailyEntity entity = mDaily.get(i);
                            PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_name(), entity.getT_tag(), entity.getVideo_url());
                            playerBeanList.add(playerBean1);
                        }
                        MediaService.insertMusicList(playerBeanList);

                        //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                        List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                        for (int i = 0; i < mDaily.size(); i++) {
                            HomeBean.DailyEntity dailyEntity = mDaily.get(i);
                            BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("everydaycomment", dailyEntity.getId(), dailyEntity.getVideo_name(),
                                    dailyEntity.getCreated_at(), dailyEntity.getVideo_url(), dailyEntity.getGood_count(),
                                    dailyEntity.getCollect_count(), dailyEntity.getView_count(), dailyEntity.getIs_good() == 1 ? true : false,
                                    dailyEntity.isIsfav(), dailyEntity.getT_header(), dailyEntity.getT_tag(), dailyEntity.getShare_h5_url()
                                    , SystemClock.currentThreadTimeMillis(), "commentId", dailyEntity.getParent_name(), dailyEntity.getTxt_url());
                            histroyBeanList.add(bofangHistroyBean);
                        }
                        MediaService.insertBoFangHistroyList(histroyBeanList);
                    }
                }
            });
        }
    }

    private class DaShiBanAdapter extends BaseQuickAdapter<HomeBean.ZhuanlanEntity, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<HomeBean.ZhuanlanEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeBean.ZhuanlanEntity item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            Glide.with(MyApplication.getGloableContext()).load(item.getZl_img()).into(imageView);
            helper.setText(R.id.tv_name, item.getZl_name())
                    .setText(R.id.tv_introduce, item.getZl_introduce())
                    .setText(R.id.tv_update_name, item.getZl_update_name())
                    .setText(R.id.tv_update_time, item.getZl_update_time())
                    .setText(R.id.tv_price, item.getZl_price());
        }
    }

    private class DaShiBanNewAdapter extends BaseQuickAdapter<HomeDaShiBanNewBean.DataEntity, BaseViewHolder> {

        public DaShiBanNewAdapter(@LayoutRes int layoutResId, @Nullable List<HomeDaShiBanNewBean.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeDaShiBanNewBean.DataEntity item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            Glide.with(MyApplication.getGloableContext()).load(item.getZl_img()).into(imageView);
            helper.setText(R.id.tv_name, item.getZl_name())
                    .setText(R.id.tv_introduce, item.getZl_introduce())
                    .setText(R.id.tv_update_name, item.getZl_update_name())
                    .setText(R.id.tv_update_time, item.getZl_update_time())
                    .setText(R.id.tv_price, item.getZl_price());
        }
    }

    private class YinYueKeAdapter extends BaseQuickAdapter<HomeBean.XiaokeEntity, BaseViewHolder> {

        public YinYueKeAdapter(@LayoutRes int layoutResId, @Nullable List<HomeBean.XiaokeEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeBean.XiaokeEntity item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int www = MyUtils.getScreenWidth(mContext) / 2 - 20;
            layoutParams.width = www;
            layoutParams.height = www;
            imageView.setLayoutParams(layoutParams);
            Glide.with(MyApplication.getGloableContext()).load(item.getXk_image()).into(imageView);
            helper.setText(R.id.tv_buy_count, item.getBuy_count())
                    .setText(R.id.tv_name, item.getXk_name())
                    .setText(R.id.tv_jie_count, item.getNodule_count())
                    .setText(R.id.tv_teacher_name, item.getTeacher_name())
                    .setText(R.id.tv_price, item.getXk_price())
                    .setText(R.id.tv_time, item.getTime_count())
                    .setText(R.id.tv_teacher_tag, item.getXk_teacher_tags());
        }
    }

    private class LikeAdapter extends BaseQuickAdapter<HomeBean.LiveEntity, BaseViewHolder> {

        public LikeAdapter(@LayoutRes int layoutResId, @Nullable List<HomeBean.LiveEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeBean.LiveEntity item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int www = MyUtils.getScreenWidth(mContext) / 3 - 20;
            layoutParams.width = www;
            layoutParams.height = www * 7 / 5;
            imageView.setLayoutParams(layoutParams);
            Glide.with(MyApplication.getGloableContext()).load(item.getXk_image()).into(imageView);
            helper.setText(R.id.tv_name, item.getXk_name());
        }
    }

    private class LikeNewAdapter extends BaseQuickAdapter<LikeMoreBean.DataEntity, BaseViewHolder> {

        public LikeNewAdapter(@LayoutRes int layoutResId, @Nullable List<LikeMoreBean.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LikeMoreBean.DataEntity item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int www = MyUtils.getScreenWidth(mContext) / 3 - 20;
            layoutParams.width = www;
            layoutParams.height = www * 7 / 5;
            imageView.setLayoutParams(layoutParams);
            Glide.with(MyApplication.getGloableContext()).load(item.getXk_image()).into(imageView);
            helper.setText(R.id.tv_name, item.getXk_name());
        }
    }

    private void gobofang(final PlayerBean playerBean) {
        isBofang = false;
        int apnType = NetWorkUtils.getAPNType(mContext);
        if (apnType == 0) {
            Toast.makeText(mContext, "没有网络呢~", Toast.LENGTH_SHORT).show();
        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
            if (SpUtils.getBoolean(mContext, "nowifiallowlisten", false)) {//记住用户允许流量播放
                isBofang = true;
                rl_bofang.setVisibility(View.VISIBLE);
                mMyBinder.setMusicUrl(playerBean.getVideo_url());
                Glide.with(MyApplication.getGloableContext()).load(playerBean.getTeacher_head()).into(iv_bo_head);
                tv_title.setText(playerBean.getTitle());
                tv_subtitle.setText(playerBean.getSubtitle());
                mMyBinder.playMusic(playerBean);
                mDialog.dismiss();
                SpUtils.putBoolean(mContext, "nowifiallowlisten", true);
                playerBean.setMsg("refreshplayer");
                EventBus.getDefault().postSticky(playerBean);
                refreshbofang();
            } else {
                mDialog.show();
                mDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isBofang = true;
                        rl_bofang.setVisibility(View.VISIBLE);
                        mMyBinder.setMusicUrl(playerBean.getVideo_url());
                        Glide.with(MyApplication.getGloableContext()).load(playerBean.getTeacher_head()).into(iv_bo_head);
                        tv_title.setText(playerBean.getTitle());
                        tv_subtitle.setText(playerBean.getSubtitle());
                        mMyBinder.playMusic(playerBean);
                        mDialog.dismiss();
                        SpUtils.putBoolean(mContext, "nowifiallowlisten", true);
                        playerBean.setMsg("refreshplayer");
                        EventBus.getDefault().postSticky(playerBean);
                        refreshbofang();
                    }
                });
                mDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        isBofang = false;
                        rl_bofang.setVisibility(View.GONE);
                        EventBus.getDefault().postSticky(new EventBean("norotate"));
                    }
                });
            }
        } else if (NetWorkUtils.isMobileConnected(mContext)) {
            Toast.makeText(mContext, "wifi不可用呢~", Toast.LENGTH_SHORT).show();
        } else {
            isBofang = true;
            rl_bofang.setVisibility(View.VISIBLE);
            mMyBinder.setMusicUrl(playerBean.getVideo_url());
            Glide.with(MyApplication.getGloableContext()).load(playerBean.getTeacher_head()).into(iv_bo_head);
            tv_title.setText(playerBean.getTitle());
            tv_subtitle.setText(playerBean.getSubtitle());
            mMyBinder.playMusic(playerBean);
            playerBean.setMsg("refreshplayer");
            EventBus.getDefault().postSticky(playerBean);
            refreshbofang();
        }
    }

    private void refreshbofang() {
        if (mytype.equals("comment")) {
            for (int i = 0; i < mMFreeChild.size(); i++) {
                mMFreeChild.get(i).setChecked(false);
            }
            for (int i = 0; i < mDaily.size(); i++) {
                if (i == myposition) {
                    mDaily.get(i).setChecked(true);
                } else {
                    mDaily.get(i).setChecked(false);
                }
            }
        } else {
            for (int i = 0; i < mMFreeChild.size(); i++) {
                if (i == myposition) {
                    mMFreeChild.get(i).setChecked(true);
                } else {
                    mMFreeChild.get(i).setChecked(false);
                }
            }
            for (int i = 0; i < mDaily.size(); i++) {
                mDaily.get(i).setChecked(false);
            }
        }
        mZhuanLanAdapter.notifyDataSetChanged();
        mCommentAdapter.notifyDataSetChanged();
    }

    private void allpause() {
        for (int i = 0; i < mMFreeChild.size(); i++) {
            mMFreeChild.get(i).setChecked(false);
        }
        for (int i = 0; i < mDaily.size(); i++) {
            mDaily.get(i).setChecked(false);
        }
        mZhuanLanAdapter.notifyDataSetChanged();
        mCommentAdapter.notifyDataSetChanged();
    }

    private void allclose() {
        for (int i = 0; i < mMFreeChild.size(); i++) {
            mMFreeChild.get(i).setChecked(false);
        }
        for (int i = 0; i < mDaily.size(); i++) {
            mDaily.get(i).setChecked(false);
        }
        mZhuanLanAdapter.notifyDataSetChanged();
        mCommentAdapter.notifyDataSetChanged();
        mytype = "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
            case R.id.ll_download:
                startActivity(new Intent(getActivity(), DownLoadActivity.class));
                break;
            case R.id.ll_gudian:
                Intent intent = new Intent(mContext, GuDianActivity.class);
                intent.putExtra("title", "古典");
                intent.putExtra("type", "gudian");
                startActivity(intent);
                break;
            case R.id.ll_minzu:
                Intent intent2 = new Intent(mContext, GuDianActivity.class);
                intent2.putExtra("title", "民族");
                intent2.putExtra("type", "minzu");
                startActivity(intent2);
                break;
            case R.id.ll_liuxing:
                Intent intent3 = new Intent(mContext, GuDianActivity.class);
                intent3.putExtra("title", "流行");
                intent3.putExtra("type", "liuxing");
                startActivity(intent3);
                break;
            case R.id.ll_suyang:
                Intent intent4 = new Intent(mContext, GuDianActivity.class);
                intent4.putExtra("title", "素养");
                intent4.putExtra("type", "suyang");
                startActivity(intent4);
                break;
            case R.id.ll_guanzhu:
                startActivity(new Intent(mContext, MyGuanzhuActivity.class));
                break;
            case R.id.tv_lianxubofang:
                EventBus.getDefault().post(new EventBean("morenbofang"));
                break;
            case R.id.iv_zhuanlan_head:
                break;
            case R.id.tv_zhuanlan_more:
                startActivity(new Intent(mContext, FreeActivity.class));
                break;
            case R.id.tv_meiri_more:
                startActivity(new Intent(mContext, EveryDayCommentActivity.class));
                break;
            case R.id.ll_dashi_refresh:
                daShiBanRefresh();
                break;
            case R.id.tv_dashi_lookmore:
                startActivity(new Intent(mContext, MusicMasterActivity.class));
                break;
            case R.id.tv_yinyueke_lookmore:
                startActivity(new Intent(mContext, SoftMusicActivity.class));
                break;
            case R.id.ll_like_refresh:
                yourLikeRefresh();
                break;
            case R.id.iv_delete:
                isBofang = false;
                rl_bofang.setVisibility(View.GONE);
                EventBean eventBean = new EventBean("norotate");
                EventBus.getDefault().postSticky(eventBean);
                allclose();
                break;
            case R.id.iv_arrow_top:
                Intent intent1 = new Intent(mContext, MusicActivity.class);
                intent1.putExtra("data", mMusicTypeBean);
                startActivity(intent1);
                mActivity.overridePendingTransition(R.anim.bottom_in, 0);
                break;
            case R.id.iv_mulu:
                Intent intent11 = new Intent(mContext, BoFangListActivity.class);
                startActivity(intent11);
                //                mActivity.overridePendingTransition(R.anim.bottom_in, 0);
                break;
            case R.id.rl_bofang:
                break;
        }
    }

    private void daShiBanRefresh() {
        if (mRotate_anim != null) {
            iv_dashi_refresh.startAnimation(mRotate_anim);  //开始动画
            iv_dashi_refresh.setClickable(false);
        }
        OkGo.<HomeDaShiBanNewBean>post(MyContants.LXKURL + "index/change-zl")
                .tag(this)
                .execute(new JsonCallback<HomeDaShiBanNewBean>(HomeDaShiBanNewBean.class) {
                             @Override
                             public void onSuccess(Response<HomeDaShiBanNewBean> response) {
                                 int code = response.code();
                                 HomeDaShiBanNewBean zhuanLanMoreBean = response.body();
                                 final List<HomeDaShiBanNewBean.DataEntity> data = zhuanLanMoreBean.getData();
                                 mDaShiBanNewAdapter = new DaShiBanNewAdapter(R.layout.item_dashiban, data);
                                 recycler_dashiban.setAdapter(mDaShiBanNewAdapter);
                                 iv_dashi_refresh.clearAnimation();
                                 mDaShiBanNewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         Intent intent = new Intent(mContext, ZhuanLanActivity.class);
                                         intent.putExtra("id", data.get(position).getId() + "");
                                         startActivity(intent);
                                     }
                                 });
                             }
                         }
                );
    }

    private void yourLikeRefresh() {
        if (mRotate_anim != null) {
            iv_like_refresh.startAnimation(mRotate_anim);  //开始动画
            iv_like_refresh.setClickable(false);
        }
        HttpParams params = new HttpParams();
        params.put("tagid", SpUtils.getString(mContext, "tag", ""));
        params.put("userid", SpUtils.getString(mContext, "id", ""));
        OkGo.<LikeMoreBean>post(MyContants.LXKURL + "index/change-live")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<LikeMoreBean>(LikeMoreBean.class) {
                             @Override
                             public void onSuccess(Response<LikeMoreBean> response) {
                                 int code = response.code();
                                 final LikeMoreBean likeMoreBean = response.body();
                                 mLikeNewAdapter = new LikeNewAdapter(R.layout.item_like, likeMoreBean.getData());
                                 recycler_like.setAdapter(mLikeNewAdapter);
                                 mLikeNewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         Intent intent = new Intent(mContext, LikeDetailActivity.class);
                                         intent.putExtra("id", likeMoreBean.getData().get(position).getXk_id() + "");
                                         startActivity(intent);
                                     }
                                 });
                                 iv_like_refresh.clearAnimation();
                             }
                         }
                );
    }
}
