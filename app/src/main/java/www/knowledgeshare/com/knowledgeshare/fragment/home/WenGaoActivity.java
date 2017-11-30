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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

public class WenGaoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_share;
    private CircleImageView iv_teacher_head;
    private TextView tv_ke_name;
    private TextView tv_teacher_name;
    private TextView tv_teacher_intro;
    private ImageView iv_collect;
    private LinearLayout ll_liuyan;
    private LinearLayout ll_guanzhu;
    private RecyclerView recycler_liuyan;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private boolean isGuanzhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_gao);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        iv_teacher_head = (CircleImageView) findViewById(R.id.iv_teacher_head);
        tv_ke_name = (TextView) findViewById(R.id.tv_ke_name);
        tv_teacher_name = (TextView) findViewById(R.id.tv_teacher_name);
        tv_teacher_intro = (TextView) findViewById(R.id.tv_teacher_intro);
        iv_collect = (ImageView) findViewById(R.id.iv_collect);
        ll_liuyan = (LinearLayout) findViewById(R.id.ll_liuyan);
        ll_liuyan.setOnClickListener(this);
        ll_guanzhu = (LinearLayout) findViewById(R.id.ll_guanzhu);
        ll_guanzhu.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        initDialog();
        showTanchuangDialog();
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        LiuYanAdapter liuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan2, list);
        recycler_liuyan.setAdapter(liuYanAdapter);
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    private class LiuYanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LiuYanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    private void showShareDialog() {
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
    }

    private void showTanchuangDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_biji)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.view_yes).setOnClickListener(new View.OnClickListener() {
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
            case R.id.ll_liuyan:
                startActivity(new Intent(this, LiuYanActivity.class));
                break;
            case R.id.ll_guanzhu:
                if (isGuanzhu) {
                    iv_collect.setImageResource(R.drawable.weiguanzhuxin);
                } else {
                    iv_collect.setImageResource(R.drawable.xinxin);
                }
                isGuanzhu = !isGuanzhu;
                break;
        }
    }
}
