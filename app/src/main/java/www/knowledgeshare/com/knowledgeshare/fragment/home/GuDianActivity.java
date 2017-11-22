package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BannerUtils;
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
    private List<String> bannerList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gu_dian);
        initView();
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
        initData();
    }

    private void initData() {
        String title = getIntent().getStringExtra("title");
        tv_title.setText(title);
        tv_zhuanlan_title.setText(title+"分类专栏介绍");
        ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
        layoutParams.height = MyUtils.getScreenWidth(this) / 2;
        banner.setLayoutParams(layoutParams);
        bannerList.add("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=150659736,210774851&fm=73&s=75B913D56153FBD45C31DD6503006071");
        bannerList.add("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=3490381707,1962760987&fm=73&s=BBAC2E8E48114ACC49B33C7403008078");
        bannerList.add("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=1472666556,2243555624&fm=73&s=55250CF6FE5279D433B7907B03004019");
        BannerUtils.startBanner(banner,bannerList);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        DaShiBanAdapter daShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban2, list);
        recycler_dashiban.setAdapter(daShiBanAdapter);

        YinYueKeAdapter yinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke2, list);
        recycler_yinyueke.setAdapter(yinYueKeAdapter);
    }

    private class DaShiBanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
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
            Glide.with(mContext).load("https://ss0.baidu.com/73t1b" +
                    "jeh1BF3odCf/it/u=36377501,1487953910&fm=73&s=" +
                    "54BA3ED516335F824A2D777E03005078").into(imageView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
        }
    }
}
