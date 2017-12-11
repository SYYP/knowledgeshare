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

public class MyGuanzhuActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_search;
    private RecyclerView recycler_guanzhu;
    private LinearLayout activity_my_guanzhu;
    private GuanzhuAdapter mGuanzhuAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_guanzhu);
        setISshow(false);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        recycler_guanzhu = (RecyclerView) findViewById(R.id.recycler_guanzhu);
        activity_my_guanzhu = (LinearLayout) findViewById(R.id.activity_my_guanzhu);
        mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        recycler_guanzhu.setLayoutManager(new LinearLayoutManager(this));
        mGuanzhuAdapter = new GuanzhuAdapter(R.layout.item_guanzhu, mList);
        recycler_guanzhu.setAdapter(mGuanzhuAdapter);
        mGuanzhuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(MyGuanzhuActivity.this, TeacherDetailActivity.class));
            }
        });
    }

    private class GuanzhuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public GuanzhuAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            helper.getView(R.id.iv_quxiaoguanzhu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(Gravity.CENTER, R.style.Alpah_aniamtion,helper.getAdapterPosition());
                }
            });
        }
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
                mList.remove(position);
                mGuanzhuAdapter.notifyDataSetChanged();
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
