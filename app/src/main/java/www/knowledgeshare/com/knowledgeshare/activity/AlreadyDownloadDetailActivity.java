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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.adapter.DownloadAdapter;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.AlreadyDlDetailBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.WenGaoActivity;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
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
    private AlreadyDlDetailAdapter adapter;
    private List<DownLoadListsBean> listFree = new ArrayList<>();
    private List<DownLoadListsBean> listComment = new ArrayList<>();
    private List<DownLoadListsBean.ListBean> list = new ArrayList<>();
    private String type;

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
        getIntentData();
    }

    private void getIntentData() {
        type = getIntent().getStringExtra("type");
        switch (type){
            case "free":
                listFree = (List<DownLoadListsBean>) getIntent().getExtras().getSerializable("list");
                for (int i = 0; i < listFree.size(); i++) {
                    list.addAll(listFree.get(i).getList());
                }
                break;
            case "comment":
                listComment = (List<DownLoadListsBean>) getIntent().getExtras().getSerializable("list");
                for (int i = 0; i < listComment.size(); i++) {
                    list.addAll(listComment.get(i).getList());
                }
                break;
            case "xiaoke":
                list = (List<DownLoadListsBean.ListBean>) getIntent().getExtras().getSerializable("list");
                break;
            case "zhuanlan":
                list = (List<DownLoadListsBean.ListBean>) getIntent().getExtras().getSerializable("list");
                break;
        }
        initData();
    }

    private void initData() {
        recyclerKcmc.setLayoutManager(new LinearLayoutManager(this));
        recyclerKcmc.setNestedScrollingEnabled(false);
        adapter = new AlreadyDlDetailAdapter(R.layout.item_alreadydl_detail, list);
        recyclerKcmc.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBean eventBean = new EventBean("back");
        EventBus.getDefault().postSticky(eventBean);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                if (TextUtils.equals("编辑", titleContentRightTv.getText().toString())) {
                    if (list.size() > 0){
                        titleContentRightTv.setText("取消");
                        MyUtils.setMargins(recyclerKcmc,0,0,0,100);
                        bianjiRl.setVisibility(View.VISIBLE);
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setVisibility(true);
                        }
                        adapter.notifyDataSetChanged();
                    }
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
                        GetRequest< File > request = OkGo.<File>get(list.get(i).getVideoUrl());
                        DownloadTask task = new DownloadTask(list.get(i).getTypeId()+"_"+list.get(i).getChildId(),request);
                        Logger.e("TAG:"+list.get(i).getTypeId()+"_"+list.get(i).getChildId());
                        task.remove(true);
                        list.remove(i);
                    }
                    if (list.size() == 0){
                        bianjiRl.setVisibility(View.GONE);
                        titleContentRightTv.setText("编辑");
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

    private class AlreadyDlDetailAdapter extends BaseQuickAdapter<DownLoadListsBean.ListBean, BaseViewHolder> {

        public AlreadyDlDetailAdapter(@LayoutRes int layoutResId, @Nullable List<DownLoadListsBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final DownLoadListsBean.ListBean item) {
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

            if (type.equals("zhuanlan")){
                wengaoIv.setVisibility(View.GONE);
            }

            sizeTv.setText(helper.getPosition()+1+"");
            titleTv.setText(item.getName());
//            nameTv.setText(item.getName());
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
                    Intent intent = new Intent(mContext, WenGaoFileActivity.class);
                    intent.putExtra("img",item.getIconUrl());
                    intent.putExtra("title",item.getName());
                    intent.putExtra("id",item.getTypeId());
                    intent.putExtra("childId",item.getChildId());
                    intent.putExtra("type",type);
                    startActivity(intent);
                }
            });
        }
    }
}
