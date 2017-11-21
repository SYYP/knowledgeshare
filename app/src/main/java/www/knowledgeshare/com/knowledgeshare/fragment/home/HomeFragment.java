package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.widget.SpringView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.utils.BannerUtils;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

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
    private TextView tv_dashi_refresh;
    private RecyclerView recycler_dashiban;
    private TextView tv_dashi_lookmore;
    private TextView tv_yinyueke_refresh;
    private RecyclerView recycler_yinyueke;
    private TextView tv_like_refresh;
    private RecyclerView recycler_like;
    private NestedScrollView nestView;
    private SpringView springview;
    private List<String> bannerList=new ArrayList<>();
    private ImageView iv_delete,iv_bo_head,iv_arrow_top,iv_mulu;
    private TextView tv_title,tv_subtitle;
    private RelativeLayout rl_bofang;

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
        tv_dashi_refresh = (TextView) inflate.findViewById(R.id.tv_dashi_refresh);
        tv_dashi_refresh.setOnClickListener(this);
        recycler_dashiban = (RecyclerView) inflate.findViewById(R.id.recycler_dashiban);
        tv_dashi_lookmore = (TextView) inflate.findViewById(R.id.tv_dashi_lookmore);
        tv_dashi_lookmore.setOnClickListener(this);
        tv_yinyueke_refresh = (TextView) inflate.findViewById(R.id.tv_yinyueke_refresh);
        tv_yinyueke_refresh.setOnClickListener(this);
        recycler_yinyueke = (RecyclerView) inflate.findViewById(R.id.recycler_yinyueke);
        tv_like_refresh = (TextView) inflate.findViewById(R.id.tv_like_refresh);
        tv_like_refresh.setOnClickListener(this);
        recycler_like = (RecyclerView) inflate.findViewById(R.id.recycler_like);
        nestView = (NestedScrollView) inflate.findViewById(R.id.nestView);
        springview = (SpringView) inflate.findViewById(R.id.springview);
        iv_delete=inflate.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
        iv_arrow_top=inflate.findViewById(R.id.iv_arrow_top);
        iv_arrow_top.setOnClickListener(this);
        iv_mulu=inflate.findViewById(R.id.iv_mulu);
        iv_mulu.setOnClickListener(this);
        iv_bo_head=inflate.findViewById(R.id.iv_bo_head);
        tv_title=inflate.findViewById(R.id.tv_title);
        tv_subtitle=inflate.findViewById(R.id.tv_subtitle);
        rl_bofang=inflate.findViewById(R.id.rl_bofang);
        rl_bofang.setVisibility(View.GONE);
        return inflate;
    }

    @Override
    protected void initData() {
        ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
        layoutParams.height = MyUtils.getScreenWidth(mContext) / 2;
        banner.setLayoutParams(layoutParams);
        bannerList.add("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=150659736,210774851&fm=73&s=75B913D56153FBD45C31DD6503006071");
        bannerList.add("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=3490381707,1962760987&fm=73&s=BBAC2E8E48114ACC49B33C7403008078");
        bannerList.add("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=1472666556,2243555624&fm=73&s=55250CF6FE5279D433B7907B03004019");
        BannerUtils.startBanner(banner,bannerList);
        Glide.with(this).load("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2797300661,2667050350&fm=27&gp=0.jpg").into(iv_zhuanlan_head);
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
        List<String> list=new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        ZhuanLanAdapter zhuanLanAdapter=new ZhuanLanAdapter(R.layout.item_zhuanlan,list);
        recycler_zhuanlan.setAdapter(zhuanLanAdapter);

        CommentAdapter commentAdapter=new CommentAdapter(R.layout.item_zhuanlan,list);
        recycler_comment.setAdapter(commentAdapter);

        DaShiBanAdapter daShiBanAdapter=new DaShiBanAdapter(R.layout.item_dashiban,list);
        recycler_dashiban.setAdapter(daShiBanAdapter);

        YinYueKeAdapter yinYueKeAdapter=new YinYueKeAdapter(R.layout.item_yinyueke,list);
        recycler_yinyueke.setAdapter(yinYueKeAdapter);

        LikeAdapter likeAdapter=new LikeAdapter(R.layout.item_like,list);
        recycler_like.setAdapter(likeAdapter);
    }

    private class ZhuanLanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ZhuanLanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    private class CommentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public CommentAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.getView(R.id.tv_teacher_name).setVisibility(View.VISIBLE);
        }
    }

    private class DaShiBanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
//            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//            int www = MyUtils.getScreenWidth(mContext) / 2 - 20;
//            layoutParams.width=www;
//            layoutParams.height=www*12/17;
//            imageView.setLayoutParams(layoutParams);
            Glide.with(mContext).load("https://ss0.baidu.com/73t1b" +
                    "jeh1BF3odCf/it/u=36377501,1487953910&fm=73&s=" +
                    "54BA3ED516335F824A2D777E03005078")
                    .into(imageView);
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
            layoutParams.width=www;
            layoutParams.height=www;
            imageView.setLayoutParams(layoutParams);
            Glide.with(mContext).load("https://ss0.baidu.com/73t1b" +
                    "jeh1BF3odCf/it/u=36377501,1487953910&fm=73&s=" +
                    "54BA3ED516335F824A2D777E03005078").into(imageView);
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
            layoutParams.width=www;
            layoutParams.height=www*7/5;
            imageView.setLayoutParams(layoutParams);
            Glide.with(mContext).load("https://ss0.baidu.com/73t1b" +
                    "jeh1BF3odCf/it/u=36377501,1487953910&fm=73&s=" +
                    "54BA3ED516335F824A2D777E03005078").into(imageView);
        }
    }

    private void showDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(mContext);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_iswifi)
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
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(mContext,SearchActivity.class));
                break;
            case R.id.ll_download:
                break;
            case R.id.ll_gudian:
                Intent intent = new Intent(mContext, GuDianActivity.class);
                intent.putExtra("title","古典");
                startActivity(intent);
                break;
            case R.id.ll_minzu:
                Intent intent2 = new Intent(mContext, GuDianActivity.class);
                intent2.putExtra("title","民族");
                startActivity(intent2);
                break;
            case R.id.ll_liuxing:
                Intent intent3 = new Intent(mContext, GuDianActivity.class);
                intent3.putExtra("title","流行");
                startActivity(intent3);
                break;
            case R.id.ll_suyang:
                Intent intent4 = new Intent(mContext, GuDianActivity.class);
                intent4.putExtra("title","素养");
                startActivity(intent4);
                break;
            case R.id.ll_guanzhu:

                break;
            case R.id.tv_lianxubofang:
                showDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                rl_bofang.setVisibility(View.VISIBLE);
                Glide.with(mContext).load("https://ss0.baidu.com/73t1b" +
                        "jeh1BF3odCf/it/u=36377501,1487953910&fm=73&s=" +
                        "54BA3ED516335F824A2D777E03005078").into(iv_bo_head);
                break;
            case R.id.iv_zhuanlan_head:
                break;
            case R.id.tv_zhuanlan_more:
                startActivity(new Intent(mContext,FreeActivity.class));
                break;
            case R.id.tv_meiri_more:
                break;
            case R.id.tv_dashi_refresh:
                break;
            case R.id.tv_dashi_lookmore:
                break;
            case R.id.tv_yinyueke_refresh:
                break;
            case R.id.tv_like_refresh:
                break;
            case R.id.iv_delete:
                break;
            case R.id.iv_arrow_top:
                break;
            case R.id.iv_mulu:
                break;
        }
    }
}
