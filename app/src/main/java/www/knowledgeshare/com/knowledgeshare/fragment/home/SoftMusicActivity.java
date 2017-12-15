package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.SoftMusicMoreBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;

public class SoftMusicActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RecyclerView recycler_yinyueke;
    private LinearLayout activity_gu_dian;
    private List<SoftMusicMoreBean.DataEntity> mData;
    private YinYueKeAdapter mYinYueKeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_music);
        initView();
        initData();
    }

    private void initData() {
        OkGo.<SoftMusicMoreBean>post(MyContants.LXKURL + "index/xk-more")
                .tag(this)
                .execute(new JsonCallback<SoftMusicMoreBean>(SoftMusicMoreBean.class) {
                             @Override
                             public void onSuccess(Response<SoftMusicMoreBean> response) {
                                 int code = response.code();
                                 SoftMusicMoreBean softMusicMoreBean = response.body();
                                 mData = softMusicMoreBean.getData();
                                 if (mYinYueKeAdapter == null) {
                                     mYinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke2, mData);
                                     recycler_yinyueke.setAdapter(mYinYueKeAdapter);
                                 } else {
                                     mYinYueKeAdapter.setNewData(mData);
                                     mYinYueKeAdapter.notifyDataSetChanged();
                                 }
                                 mYinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         startActivity(new Intent(SoftMusicActivity.this, SoftMusicDetailActivity.class));
                                     }
                                 });
                             }
                         }
                );
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        recycler_yinyueke = (RecyclerView) findViewById(R.id.recycler_yinyueke);
        activity_gu_dian = (LinearLayout) findViewById(R.id.activity_gu_dian);
        recycler_yinyueke.setLayoutManager(new LinearLayoutManager(this));
        recycler_yinyueke.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    setPopHide();
                } else if (dy < 0) {
                    SlidePopShow();
                }
            }
        });
    }

    private class YinYueKeAdapter extends BaseQuickAdapter<SoftMusicMoreBean.DataEntity, BaseViewHolder> {

        public YinYueKeAdapter(@LayoutRes int layoutResId, @Nullable List<SoftMusicMoreBean.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SoftMusicMoreBean.DataEntity item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            Glide.with(mContext).load(item.getXk_image()).into(imageView);
            helper.setVisible(R.id.iv_bofang, false);
            helper.setText(R.id.tv_buy_count, item.getBuy_count())
                    .setText(R.id.tv_name, item.getXk_name())
                    .setText(R.id.tv_teacher_name, item.getTeacher_name())
                    .setText(R.id.tv_teacher_tag, item.getXk_teacher_tags())
                    .setText(R.id.tv_time, item.getTime_count())
                    .setText(R.id.tv_jie_count, item.getNodule_count())
                    .setText(R.id.tv_price, item.getXk_price());
            if (helper.getAdapterPosition() == mData.size() - 1) {
                helper.setVisible(R.id.view_line, false);
            } else {
                helper.setVisible(R.id.view_line, true);
            }
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
