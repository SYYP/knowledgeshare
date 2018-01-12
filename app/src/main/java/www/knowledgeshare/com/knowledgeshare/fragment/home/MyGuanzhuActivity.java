package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MyFollowBean;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

public class MyGuanzhuActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_search;
    private RecyclerView recycler_guanzhu;
    private LinearLayout activity_my_guanzhu;
    private GuanzhuAdapter mGuanzhuAdapter;
    private List<MyFollowBean.DataEntity> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_guanzhu);
        setISshow(false);
        initView();
        initData();
    }

    private void initData() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<MyFollowBean>get(MyContants.LXKURL + "index/my-follow")
                .tag(this)
                .headers(headers)
                .execute(new DialogCallback<MyFollowBean>(MyGuanzhuActivity.this, MyFollowBean.class) {
                             @Override
                             public void onSuccess(Response<MyFollowBean> response) {
                                 int code = response.code();
                                 MyFollowBean myFollowBean = response.body();
                                 mData = myFollowBean.getData();
                                 mGuanzhuAdapter = new GuanzhuAdapter(R.layout.item_guanzhu, mData);
                                 recycler_guanzhu.setAdapter(mGuanzhuAdapter);
                                 mGuanzhuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                         Intent intent = new Intent(MyGuanzhuActivity.this, TeacherDetailActivity.class);
                                         intent.putExtra("teacher_id",mData.get(position).getId()+"");
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
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        recycler_guanzhu = (RecyclerView) findViewById(R.id.recycler_guanzhu);
        activity_my_guanzhu = (LinearLayout) findViewById(R.id.activity_my_guanzhu);
        recycler_guanzhu.setLayoutManager(new LinearLayoutManager(this));
    }

    private class GuanzhuAdapter extends BaseQuickAdapter<MyFollowBean.DataEntity, BaseViewHolder> {

        public GuanzhuAdapter(@LayoutRes int layoutResId, @Nullable List<MyFollowBean.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, MyFollowBean.DataEntity item) {
            ImageView iv_teacher_head = helper.getView(R.id.iv_teacher_head);
            Glide.with(MyApplication.getGloableContext()).load(item.getT_header()).into(iv_teacher_head);
            helper.setText(R.id.tv_teacher_name, item.getT_name())
                    .setText(R.id.tv_teacher_intro, item.getT_tag());
            helper.getView(R.id.iv_quxiaoguanzhu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(Gravity.CENTER, R.style.Alpah_aniamtion, helper.getAdapterPosition());
                }
            });
        }
    }

    private void noguanzhu(int teacher_id) {
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
                                 Toast.makeText(MyGuanzhuActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 initData();
                             }
                         }
                );
    }

    private void showDialog(int grary, int animationStyle, final int position) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_guanzhu)
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
                noguanzhu(mData.get(position).getId());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
    }
}
