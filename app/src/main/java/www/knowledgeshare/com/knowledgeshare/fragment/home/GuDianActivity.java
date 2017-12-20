package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.GuDianBean;
import www.knowledgeshare.com.knowledgeshare.utils.BannerUtils;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;

import static com.taobao.accs.ACCSManager.mContext;

public class GuDianActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private Banner banner;
    private TextView tv_zhuanlan_title;
    private TextView tv_zhuanlan_content;
    private RecyclerView recycler_dashiban;
    private LinearLayout ll_dashiban;
    private RecyclerView recycler_yinyueke;
    private LinearLayout ll_yinyueke;
    private LinearLayout activity_gu_dian;
    private List<String> bannerList = new ArrayList<>();
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private NestedScrollView nestView;
    private List<GuDianBean.SlideEntity> mSlide;
    private List<GuDianBean.XiaokeEntity> mXiaoke;
    private List<GuDianBean.ZhuanlanEntity> mZhuanlan;
    private DaShiBanAdapter mDaShiBanAdapter;
    private YinYueKeAdapter mYinYueKeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gu_dian);
        initView();
        initData();
        initListener();
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

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        banner = (Banner) findViewById(R.id.banner);
        tv_zhuanlan_title = (TextView) findViewById(R.id.tv_zhuanlan_title);
        tv_zhuanlan_content = (TextView) findViewById(R.id.tv_zhuanlan_content);
        recycler_dashiban = (RecyclerView) findViewById(R.id.recycler_dashiban);
        ll_dashiban = (LinearLayout) findViewById(R.id.ll_dashiban);
        recycler_yinyueke = (RecyclerView) findViewById(R.id.recycler_yinyueke);
        ll_yinyueke = (LinearLayout) findViewById(R.id.ll_yinyueke);
        activity_gu_dian = (LinearLayout) findViewById(R.id.activity_gu_dian);
        recycler_dashiban.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_dashiban.setNestedScrollingEnabled(false);
        recycler_yinyueke.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_yinyueke.setNestedScrollingEnabled(false);
        nestView = (NestedScrollView) findViewById(R.id.nestView);
    }

    private void initData() {
        String title = getIntent().getStringExtra("title");
        String type = getIntent().getStringExtra("type");
        tv_title.setText(title);
        tv_zhuanlan_title.setText(title + "分类专栏介绍");
        ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
        layoutParams.height = MyUtils.getScreenWidth(this) / 2;
        banner.setLayoutParams(layoutParams);
        OkGo.<GuDianBean>post(MyContants.LXKURL + "index/"+type)
                .tag(this)
                .execute(new DialogCallback<GuDianBean>(GuDianActivity.this,GuDianBean.class) {
                             @Override
                             public void onSuccess(Response<GuDianBean> response) {
                                 int code = response.code();
                                 GuDianBean guDianBean = response.body();
                                 tv_zhuanlan_content.setText(guDianBean.getIntroduce());
                                 mSlide = guDianBean.getSlide();
                                 if (bannerList != null) {
                                     bannerList.clear();
                                     for (int i = 0; i < mSlide.size(); i++) {
                                         bannerList.add(mSlide.get(i).getImgurl());
                                     }
                                 }
                                 BannerUtils.startBanner(banner, bannerList);
                                 mXiaoke = guDianBean.getXiaoke();
                                 mZhuanlan = guDianBean.getZhuanlan();
                                 mDaShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban3, mZhuanlan);
                                 recycler_dashiban.setAdapter(mDaShiBanAdapter);
                                 mDaShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         Intent intent = new Intent(GuDianActivity.this, ZhuanLanDetail1Activity.class);
                                         intent.putExtra("title",mDaShiBanAdapter.getData().get(position).getZl_name());
                                         intent.putExtra("id", mDaShiBanAdapter.getData().get(position).getId() + "");
                                         startActivity(intent);
                                     }
                                 });
                                 mYinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke2, mXiaoke);
                                 recycler_yinyueke.setAdapter(mYinYueKeAdapter);
                                 mYinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         Intent intent = new Intent(GuDianActivity.this, SoftMusicDetailActivity.class);
                                         intent.putExtra("id", mYinYueKeAdapter.getData().get(position).getXk_id() + "");
                                         startActivity(intent);
                                     }
                                 });
                             }
                         }
                );
    }

    private class DaShiBanAdapter extends BaseQuickAdapter<GuDianBean.ZhuanlanEntity, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<GuDianBean.ZhuanlanEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GuDianBean.ZhuanlanEntity item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            Glide.with(mContext).load(item.getZl_img()).into(imageView);
            helper.setText(R.id.tv_name, item.getZl_name())
                    .setText(R.id.tv_introduce, item.getZl_introduce())
                    .setText(R.id.tv_update_name, item.getZl_update_name())
                    .setText(R.id.tv_update_time, item.getZl_update_time())
                    .setText(R.id.tv_price, item.getZl_price());
            helper.getView(R.id.iv_bofang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(GuDianActivity.this, ZhuanLanDetail2Activity.class));
                }
            });
        }
    }

    private class YinYueKeAdapter extends BaseQuickAdapter<GuDianBean.XiaokeEntity, BaseViewHolder> {

        public YinYueKeAdapter(@LayoutRes int layoutResId, @Nullable List<GuDianBean.XiaokeEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GuDianBean.XiaokeEntity item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            Glide.with(mContext).load(item.getXk_image()).into(imageView);
            helper.setText(R.id.tv_buy_count, item.getBuy_count())
                    .setText(R.id.tv_name, item.getXk_name())
                    .setText(R.id.tv_jie_count, item.getNodule_count())
                    .setText(R.id.tv_teacher_name, item.getTeacher_name())
                    .setText(R.id.tv_price, item.getXk_price())
                    .setText(R.id.tv_time, item.getTime_count())
                    .setText(R.id.tv_teacher_tag, item.getXk_teacher_tags());
            helper.getView(R.id.iv_bofang).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(GuDianActivity.this, ZhuanLanDetail2Activity.class));
                }
            });
        }
    }

    private void showShareDialog() {
        mBuilder = new BaseDialog.Builder(this);
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
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                showShareDialog();
                break;
        }
    }
}
