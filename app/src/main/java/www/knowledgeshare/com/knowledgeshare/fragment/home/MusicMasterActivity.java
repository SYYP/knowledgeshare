package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicMasterMoreBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

public class MusicMasterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RadioButton rb_gudian;
    private RadioButton rb_minzu;
    private RadioButton rb_liuxing;
    private RadioButton rb_suyang;
    private RadioGroup rgp;
    private RecyclerView recycler_dashiban;
    private LinearLayout activity_gu_dian;
    private List<MusicMasterMoreBean.DataEntity> mData;
    private DaShiBanAdapter mDaShiBanAdapter;
    private String after = "";
    private SpringView springview;
    private boolean isLoadMore;
    private String type = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_master);
        initView();
        initData("");
        initListener();
    }

    private void initListener() {
//        recycler_dashiban.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    setPopHide();
//                } else if (dy < 0) {
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
                        initData(after);
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
                        initData(after);
                        springview.onFinishFreshAndLoad();
                    }
                }, 2000);
            }
        });
        springview.setHeader(new MyHeader(this));
        springview.setFooter(new MyFooter(this));
    }

    private void initData(String after) {
        HttpParams params = new HttpParams();
        if (isLoadMore) {
            params.put("after", after);
        }
        params.put("class_id", type);
        OkGo.<MusicMasterMoreBean>post(MyContants.LXKURL + "index/zl-more")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<MusicMasterMoreBean>(MusicMasterActivity.this, MusicMasterMoreBean.class) {
                             @Override
                             public void onSuccess(Response<MusicMasterMoreBean> response) {
                                 int code = response.code();
                                 MusicMasterMoreBean musicMasterMoreBean = response.body();
                                 if (!isLoadMore) {
                                     mData = musicMasterMoreBean.getData();
                                     if (mDaShiBanAdapter == null) {
                                         mDaShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban4, mData);
                                         recycler_dashiban.setAdapter(mDaShiBanAdapter);
                                     } else {
                                         mDaShiBanAdapter.setNewData(mData);
                                         mDaShiBanAdapter.notifyDataSetChanged();
                                     }
                                 } else {
                                     List<MusicMasterMoreBean.DataEntity> data = musicMasterMoreBean.getData();
                                     if (data==null || data.size()==0){
                                         Toast.makeText(MusicMasterActivity.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                     mData.addAll(data);
                                     mDaShiBanAdapter.notifyDataSetChanged();
                                 }
                                 mDaShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         Intent intent = new Intent(MusicMasterActivity.this, ZhuanLanActivity.class);
                                         intent.putExtra("id", mDaShiBanAdapter.getData().get(position).getId() + "");
                                         startActivity(intent);
                                     }
                                 });
                             }
                         }
                );
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        rb_gudian = (RadioButton) findViewById(R.id.rb_gudian);
        rb_minzu = (RadioButton) findViewById(R.id.rb_minzu);
        rb_liuxing = (RadioButton) findViewById(R.id.rb_liuxing);
        rb_suyang = (RadioButton) findViewById(R.id.rb_suyang);
        rgp = (RadioGroup) findViewById(R.id.rgp);
        springview = (SpringView) findViewById(R.id.springview);
        recycler_dashiban = (RecyclerView) findViewById(R.id.recycler_dashiban);
        activity_gu_dian = (LinearLayout) findViewById(R.id.activity_gu_dian);
        rgp.check(R.id.rb_gudian);
        recycler_dashiban.setLayoutManager(new LinearLayoutManager(this));
        recycler_dashiban.requestDisallowInterceptTouchEvent(true);
        rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_gudian:
                        type = "1";
                        isLoadMore=false;
                        initData("");
                        break;
                    case R.id.rb_minzu:
                        type = "2";
                        isLoadMore=false;
                        initData("");
                        break;
                    case R.id.rb_liuxing:
                        type = "3";
                        isLoadMore=false;
                        initData("");
                        break;
                    case R.id.rb_suyang:
                        type = "4";
                        isLoadMore=false;
                        initData("");
                        break;
                }
            }
        });
    }


    private class DaShiBanAdapter extends BaseQuickAdapter<MusicMasterMoreBean.DataEntity, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<MusicMasterMoreBean.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MusicMasterMoreBean.DataEntity item) {
            after = item.getId() + "";
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            RequestOptions options=new RequestOptions();
            options.error(R.drawable.home_default_like);
            options.placeholder(R.drawable.home_default_like);
            Glide.with(MyApplication.getGloableContext()).load(item.getZl_img()).apply(options).into(imageView);
            helper.setVisible(R.id.iv_bofang, false);
            helper.setText(R.id.tv_name, item.getZl_name())
                    .setText(R.id.tv_introduce, item.getZl_introduce())
                    .setText(R.id.tv_update_name, item.getZl_update_name())
                    .setText(R.id.tv_update_time, item.getZl_update_time())
                    .setText(R.id.tv_price, item.getZl_price());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
