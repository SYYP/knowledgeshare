package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.AlreadyDlDetailBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.WenGaoActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;

/**
 * 已下载点进来的详情
 */
public class AlreadyDownloadDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.title_content_right_tv) TextView titleContentRightTv;
    @BindView(R.id.recycler_kcmc) RecyclerView recyclerKcmc;
    @BindView(R.id.all_checkBox) CheckBox allCheckBox;
    @BindView(R.id.heji_tv) TextView hejiTv;
    @BindView(R.id.delete_tv) TextView deleteTv;
    @BindView(R.id.bianji_rl) RelativeLayout bianjiRl;
    private List<AlreadyDlDetailBean> list;
    private AlreadyDlDetailAdapter adapter;
    private AlreadyDlDetailBean alreadyDlDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_download_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentTv.setText("崔宗顺等男低音歌唱家秘籍");
        titleContentRightTv.setText("编辑");
        titleBackIv.setOnClickListener(this);
        titleContentRightTv.setOnClickListener(this);
        deleteTv.setOnClickListener(this);
        initData();
        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    if (!isAllChecked()){
                        quanxuan();
                    }
                } else {
                    cancleQuanxuan();
                }
            }
        });
    }

    private void initData() {
        recyclerKcmc.setLayoutManager(new LinearLayoutManager(this));
        recyclerKcmc.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            alreadyDlDetailBean = new AlreadyDlDetailBean();
            alreadyDlDetailBean.setTitle("男低音，一个神秘又充满魅力的声部");
            alreadyDlDetailBean.setName("崔宗顺");
            alreadyDlDetailBean.setTime("05:00");
            alreadyDlDetailBean.setSize(i+"");
            list.add(alreadyDlDetailBean);
        }
        adapter = new AlreadyDlDetailAdapter(R.layout.item_alreadydl_detail, list);
        recyclerKcmc.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                if (TextUtils.equals("编辑", titleContentRightTv.getText().toString())) {
                    titleContentRightTv.setText("取消");
                    MyUtils.setMargins(recyclerKcmc,0,0,0,100);
                    bianjiRl.setVisibility(View.VISIBLE);
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setVisibility(true);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    titleContentRightTv.setText("编辑");
                    MyUtils.setMargins(recyclerKcmc,0,0,0,0);
                    bianjiRl.setVisibility(View.GONE);
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setVisibility(false);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.delete_tv:
                for (int i = list.size()-1; i >= 0; i--) {
                    if (list.get(i).isChecked()){
                        list.remove(i);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void quanxuan() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }


    private void cancleQuanxuan() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
        adapter.notifyDataSetChanged();
    }

    private boolean isAllChecked() {
        for (int i = 0; i < list.size(); i++) {
            boolean checked = list.get(i).isChecked();
            if (!checked) {
                return false;
            }
        }
        return true;
    }

    private class AlreadyDlDetailAdapter extends BaseQuickAdapter<AlreadyDlDetailBean, BaseViewHolder> {

        public AlreadyDlDetailAdapter(@LayoutRes int layoutResId, @Nullable List<AlreadyDlDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final AlreadyDlDetailBean item) {
            TextView sizeTv = helper.getView(R.id.size_tv);
            TextView titleTv = helper.getView(R.id.title_tv);
            TextView nameTv = helper.getView(R.id.name_tv);
            TextView timeTv = helper.getView(R.id.time_tv);
            ImageView wengaoIv = helper.getView(R.id.wengao_iv);
            CheckBox checkBox = helper.getView(R.id.checkbox);
            if (item.isVisibility()){
                checkBox.setVisibility(View.VISIBLE);
            }else {
                checkBox.setVisibility(View.GONE);
            }

            sizeTv.setText(item.getSize());
            titleTv.setText(item.getTitle());
            nameTv.setText(item.getName());
            timeTv.setText(item.getTime());
            checkBox.setChecked(item.isChecked());


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!((CheckBox) view).isChecked()){//未选中
                        allCheckBox.setChecked(false);
                        item.setChecked(false);
                    }else {
                        item.setChecked(true);
                        if (isAllChecked()){
                            allCheckBox.setChecked(true);
                        }
                    }
                }
            });

            wengaoIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, WenGaoActivity.class));
                }
            });
        }
    }
}
