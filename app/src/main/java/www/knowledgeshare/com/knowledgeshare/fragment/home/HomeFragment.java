package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.DownLoadActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BannerUtils;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
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

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_home, null);
        tv_search = (TextView) inflate.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        iv_download = (ImageView) inflate.findViewById(R.id.iv_download);
        tv_download_number = (TextView) inflate.findViewById(R.id.tv_download_number);
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
        EventBus.getDefault().register(this);
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
        if (eventBean.getMsg().equals("bofang")) {
            isBofang = true;
            rl_bofang.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.GONE);
            //            Glide.with(mContext).load("").into(iv_bo_head);
        } else if (eventBean.getMsg().equals("pause")) {
            isBofang = false;
            iv_delete.setVisibility(View.VISIBLE);
        } else if (eventBean.getMsg().equals("close")) {
            isBofang = false;
            rl_bofang.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
        layoutParams.height = MyUtils.getScreenWidth(mContext) / 2;
        banner.setLayoutParams(layoutParams);
        BannerUtils.startBanner(banner, bannerList);
        Glide.with(this).load(R.drawable.face).into(iv_zhuanlan_head);
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
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        List<String> list2 = new ArrayList<>();
        list2.add("");
        list2.add("");
        list2.add("");
        list2.add("");
        mZhuanLanAdapter = new ZhuanLanAdapter(R.layout.item_zhuanlan, list);
        recycler_zhuanlan.setAdapter(mZhuanLanAdapter);

        mCommentAdapter = new CommentAdapter(R.layout.item_zhuanlan, list);
        recycler_comment.setAdapter(mCommentAdapter);

        mDaShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban, list2);
        recycler_dashiban.setAdapter(mDaShiBanAdapter);
        mDaShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, ZhuanLanActivity.class));
            }
        });

        mYinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke, list2);
        recycler_yinyueke.setAdapter(mYinYueKeAdapter);
        mYinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, SoftMusicDetailActivity.class));
            }
        });

        mLikeAdapter = new LikeAdapter(R.layout.item_like, list);
        recycler_like.setAdapter(mLikeAdapter);
        mLikeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, LikeDetailActivity.class));
            }
        });
        initDialog();
        initAnim();
        initListener();
        initMusic();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springview.onFinishFreshAndLoad();
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {

            }
        });
        springview.setHeader(new MyHeader(mContext));//xml中设置了header
        //        springview.setFooter(new DefaultFooter(getActivity()));
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

    private class ZhuanLanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ZhuanLanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            final ImageView iv_pause = helper.getView(R.id.iv_pause);
            helper.getView(R.id.rl_root_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gobofang();
                    if (isBofang) {
                        iv_pause.setImageResource(R.drawable.bofang_yellow);
                    } else {
                        iv_pause.setImageResource(R.drawable.pause_yellow);
                    }
                }
            });
        }
    }

    private class CommentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public CommentAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.getView(R.id.tv_teacher_name).setVisibility(View.VISIBLE);
            final ImageView iv_pause = helper.getView(R.id.iv_pause);
            helper.getView(R.id.rl_root_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gobofang();
                    if (isBofang) {
                        iv_pause.setImageResource(R.drawable.bofang_yellow);
                    } else {
                        iv_pause.setImageResource(R.drawable.pause_yellow);
                    }
                }
            });
        }
    }

    private class DaShiBanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            //            Glide.with(mContext).load().into(imageView);
        }
    }

    private class YinYueKeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public YinYueKeAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int www = MyUtils.getScreenWidth(mContext) / 2 - 20;
            layoutParams.width = www;
            layoutParams.height = www;
            imageView.setLayoutParams(layoutParams);
            //            Glide.with(mContext).load().into(imageView);
        }
    }

    private class LikeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LikeAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int www = MyUtils.getScreenWidth(mContext) / 3 - 20;
            layoutParams.width = www;
            layoutParams.height = www * 7 / 5;
            imageView.setLayoutParams(layoutParams);
            //            Glide.with(mContext).load().into(imageView);
        }
    }

    private void gobofang() {
        isBofang = false;
        int apnType = NetWorkUtils.getAPNType(mContext);
        if (apnType == 0) {
            Toast.makeText(mContext, "没有网络呢~", Toast.LENGTH_SHORT).show();
            return;
        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
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
                    isBofang = true;
                    rl_bofang.setVisibility(View.VISIBLE);
                    //                    Glide.with(mContext).load().into(iv_bo_head);
                    EventBean eventBean = new EventBean("rotate");
                    EventBus.getDefault().postSticky(eventBean);
                    mMyBinder.playMusic();
                }
            });
            return;
        } else if (NetWorkUtils.isMobileConnected(mContext)) {
            Toast.makeText(mContext, "wifi不可用呢~", Toast.LENGTH_SHORT).show();
            return;
        }
        isBofang = true;
        rl_bofang.setVisibility(View.VISIBLE);
        //        Glide.with(mContext).load().into(iv_bo_head);
        EventBean eventBean = new EventBean("rotate");
        EventBus.getDefault().postSticky(eventBean);
        mMyBinder.playMusic();
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
                startActivity(intent);
                break;
            case R.id.ll_minzu:
                Intent intent2 = new Intent(mContext, GuDianActivity.class);
                intent2.putExtra("title", "民族");
                startActivity(intent2);
                break;
            case R.id.ll_liuxing:
                Intent intent3 = new Intent(mContext, GuDianActivity.class);
                intent3.putExtra("title", "流行");
                startActivity(intent3);
                break;
            case R.id.ll_suyang:
                Intent intent4 = new Intent(mContext, GuDianActivity.class);
                intent4.putExtra("title", "素养");
                startActivity(intent4);
                break;
            case R.id.ll_guanzhu:
                startActivity(new Intent(mContext, MyGuanzhuActivity.class));
                break;
            case R.id.tv_lianxubofang:
                gobofang();
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
                if (mRotate_anim != null) {
                    iv_dashi_refresh.startAnimation(mRotate_anim);  //开始动画
                    iv_dashi_refresh.setClickable(false);
                }
                break;
            case R.id.tv_dashi_lookmore:
                startActivity(new Intent(mContext, MusicMasterActivity.class));
                break;
            case R.id.tv_yinyueke_lookmore:
                startActivity(new Intent(mContext, SoftMusicActivity.class));
                break;
            case R.id.ll_like_refresh:
                if (mRotate_anim != null) {
                    iv_like_refresh.startAnimation(mRotate_anim);  //开始动画
                    iv_like_refresh.setClickable(false);
                }
                break;
            case R.id.iv_delete:
                isBofang = false;
                rl_bofang.setVisibility(View.GONE);
                EventBean eventBean = new EventBean("norotate");
                EventBus.getDefault().postSticky(eventBean);
                break;
            case R.id.iv_arrow_top:
                Intent intent1 = new Intent(mContext, MusicActivity.class);
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
}
