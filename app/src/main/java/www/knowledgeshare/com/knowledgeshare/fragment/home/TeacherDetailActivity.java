package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.TeacherDetailBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

public class TeacherDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_bigphoto;
    private TextView tv_teacher_intro;
    private RecyclerView recycler_dashiban;
    private LinearLayout ll_dashiban;
    private RecyclerView recycler_yinyueke;
    private LinearLayout ll_yinyueke;
    private LinearLayout activity_gu_dian;
    private NestedScrollView nestView;
    private TeacherDetailBean mTeacherDetailBean;
    private List<TeacherDetailBean.XiaokeEntity> mXiaoke;
    private List<TeacherDetailBean.ZhuanlanEntity> mZhuanlan;
    private DaShiBanAdapter mDaShiBanAdapter;
    private YinYueKeAdapter mYinYueKeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_bigphoto = (ImageView) findViewById(R.id.iv_bigphoto);
        tv_teacher_intro = (TextView) findViewById(R.id.tv_teacher_intro);
        recycler_dashiban = (RecyclerView) findViewById(R.id.recycler_dashiban);
        ll_dashiban = (LinearLayout) findViewById(R.id.ll_dashiban);
        recycler_yinyueke = (RecyclerView) findViewById(R.id.recycler_yinyueke);
        ll_yinyueke = (LinearLayout) findViewById(R.id.ll_yinyueke);
        activity_gu_dian = (LinearLayout) findViewById(R.id.activity_gu_dian);
        recycler_dashiban.setLayoutManager(new LinearLayoutManager(this));
        recycler_dashiban.setNestedScrollingEnabled(false);
        recycler_yinyueke.setLayoutManager(new LinearLayoutManager(this));
        recycler_yinyueke.setNestedScrollingEnabled(false);
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
        final String teacher_id = getIntent().getStringExtra("teacher_id");
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("teacher_id", teacher_id);
        OkGo.<TeacherDetailBean>post(MyContants.LXKURL + "teacher/list")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<TeacherDetailBean>(TeacherDetailActivity.this, TeacherDetailBean.class) {
                             @Override
                             public void onSuccess(Response<TeacherDetailBean> response) {
                                 int code = response.code();
                                 mTeacherDetailBean = response.body();
                                 Glide.with(MyApplication.getGloableContext()).load(mTeacherDetailBean.getT_img()).into(iv_bigphoto);
                                 tv_teacher_intro.setText(mTeacherDetailBean.getT_introduce());
                                 mXiaoke = mTeacherDetailBean.getXiaoke();
                                 mZhuanlan = mTeacherDetailBean.getZhuanlan();
                                 mDaShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban2, mZhuanlan);
                                 recycler_dashiban.setAdapter(mDaShiBanAdapter);
                                 mDaShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         Intent intent = new Intent(TeacherDetailActivity.this, ZhuanLanDetail1Activity.class);
                                         intent.putExtra("title", mDaShiBanAdapter.getData().get(position).getZl_name());
                                         intent.putExtra("id", mDaShiBanAdapter.getData().get(position).getId() + "");
                                         startActivity(intent);
                                     }
                                 });
                                 mYinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke2, mXiaoke);
                                 recycler_yinyueke.setAdapter(mYinYueKeAdapter);
                                 mYinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         Intent intent = new Intent(TeacherDetailActivity.this, SoftMusicDetailActivity.class);
                                         intent.putExtra("id", mYinYueKeAdapter.getData().get(position).getXk_id() + "");
                                         startActivity(intent);
                                     }
                                 });
                             }
                         }
                );

    }

    private class DaShiBanAdapter extends BaseQuickAdapter<TeacherDetailBean.ZhuanlanEntity, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<TeacherDetailBean.ZhuanlanEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final TeacherDetailBean.ZhuanlanEntity item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            Glide.with(MyApplication.getGloableContext()).load(item.getZl_img()).into(imageView);
            helper.setText(R.id.tv_name, item.getZl_name())
                    .setText(R.id.tv_introduce, item.getZl_introduce())
                    .setText(R.id.tv_update_name, item.getZl_update_name())
                    .setText(R.id.tv_update_time, item.getZl_update_time())
                    .setText(R.id.tv_price, item.getZl_price());
            helper.getView(R.id.iv_bofang).setVisibility(View.GONE);
        }
    }

    private class YinYueKeAdapter extends BaseQuickAdapter<TeacherDetailBean.XiaokeEntity, BaseViewHolder> {

        public YinYueKeAdapter(@LayoutRes int layoutResId, @Nullable List<TeacherDetailBean.XiaokeEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final TeacherDetailBean.XiaokeEntity item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            Glide.with(MyApplication.getGloableContext()).load(item.getXk_image()).into(imageView);
            helper.setText(R.id.tv_buy_count, item.getBuy_count())
                    .setText(R.id.tv_name, item.getXk_name())
                    .setText(R.id.tv_jie_count, item.getNodule_count())
                    .setText(R.id.tv_teacher_name, item.getTeacher_name())
                    .setText(R.id.tv_price, item.getXk_price())
                    .setText(R.id.tv_time, item.getTime_count())
                    .setText(R.id.tv_teacher_tag, item.getXk_teacher_tags());
            helper.getView(R.id.iv_bofang).setVisibility(View.GONE);
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
