package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.FreeTryReadListBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;

public class ZhuanLanDetail1Activity extends BaseActivity {

    private ImageView iv_back;
    private RecyclerView recycler;
    private boolean isCollected;
    private List<FreeTryReadListBean.DataEntity> mData;
    private MyAdapter mMyAdapter;
    private TextView tv_title;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuan_lan_detail1);
        initView();
        initData();
    }

    private void initData() {
        String title = getIntent().getStringExtra("title");
        tv_title.setText(title.length()>18?title.substring(0,17)+"...":title);
        mId = getIntent().getStringExtra("id");
        HttpParams params = new HttpParams();
        params.put("id", mId);
        if (getIntent().getStringExtra("type") != null) {
            params.put("type", "2");
        }else {
            params.put("type", "1");
        }
        OkGo.<FreeTryReadListBean>post(MyContants.LXKURL + "zl/free-trials")
                .tag(this)
                .params(params)
                .execute(new DialogCallback<FreeTryReadListBean>(ZhuanLanDetail1Activity.this, FreeTryReadListBean.class) {
                             @Override
                             public void onSuccess(Response<FreeTryReadListBean> response) {
                                 int code = response.code();
                                 FreeTryReadListBean freeTryReadListBean = response.body();
                                 mData = freeTryReadListBean.getData();
                                 mMyAdapter = new MyAdapter(R.layout.item_zhuanlan_xiaojie, mData);
                                 recycler.setAdapter(mMyAdapter);
                                 mMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         Intent intent = new Intent(ZhuanLanDetail1Activity.this, ZhuanLanDetail2Activity.class);
                                         intent.putExtra("id", mData.get(position).getId()+"");
                                         intent.putExtra("title", getIntent().getStringExtra("title"));
                                         if (getIntent().getStringExtra("type") != null) {
                                             intent.putExtra("type", "alreadyBuy");
                                         }
                                         intent.putExtra("is_buy", getIntent().getBooleanExtra("is_buy",false));
                                         startActivity(intent);
                                     }
                                 });
                             }
                         }
                );
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
//        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    }

    private class MyAdapter extends BaseQuickAdapter<FreeTryReadListBean.DataEntity, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<FreeTryReadListBean.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FreeTryReadListBean.DataEntity item) {
            ImageView iv_tupian = helper.getView(R.id.iv_tupian);
            RequestOptions options=new RequestOptions();
            options.error(R.drawable.default_banner);
            options.placeholder(R.drawable.default_banner);
            ViewGroup.LayoutParams layoutParams = iv_tupian.getLayoutParams();
            int width = MyUtils.getScreenWidth(ZhuanLanDetail1Activity.this)
                    - MyUtils.dip2px(ZhuanLanDetail1Activity.this, 60);
            layoutParams.height= width*8/17;
            iv_tupian.setLayoutParams(layoutParams);
            Glide.with(MyApplication.getGloableContext()).load(item.getImgurl()).apply(options).into(iv_tupian);
            helper.setText(R.id.tv_name, item.getName())
                    .setText(R.id.tv_introduce, item.getDescription())
                    .setText(R.id.tv_look_count, item.getIs_view() == 0 ? item.getView_count() + "" : item.getView_count_true() + "")
                    .setText(R.id.tv_collect_count, item.getIs_collect() == 0 ? item.getCollect_count() + "" : item.getCollect_count_true() + "")
                    .setText(R.id.tv_rss_count, item.getIs_rss() == 0 ? item.getRss_count() + "" : item.getRss_count_true() + "");
            //            final TextView tv_collect_count = helper.getView(R.id.tv_collect_count);
            //            tv_collect_count.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View view) {
            //                    if (isCollected) {
            //                        Drawable drawable = getResources().getDrawable(R.drawable.zhuanlan_collect);
            //                        /// 这一步必须要做,否则不会显示.
            //                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            //                        tv_collect_count.setCompoundDrawables(drawable, null, null, null);
            //                    } else {
            //                        Drawable drawable = getResources().getDrawable(R.drawable.music_collected);
            //                        /// 这一步必须要做,否则不会显示.
            //                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            //                        tv_collect_count.setCompoundDrawables(drawable, null, null, null);
            //                    }
            //                    isCollected = !isCollected;
            //                }
            //            });
        }
    }
}
