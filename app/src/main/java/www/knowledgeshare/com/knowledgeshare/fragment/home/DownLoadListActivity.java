package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.SoftMusicDetailBean;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;

public class DownLoadListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RecyclerView recycler_list;
    private ImageView iv_quanxuan;
    private TextView tv_quanxuan;
    private TextView tv_download;
    private MyAdapter mMyAdapter;
    private boolean isAllChecked = true;
    private SoftMusicDetailBean childEntity;
    private List<SoftMusicDetailBean.ChildEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_list);
        setISshow(false);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        recycler_list = (RecyclerView) findViewById(R.id.recycler_list);
        iv_quanxuan = (ImageView) findViewById(R.id.iv_quanxuan);
        iv_quanxuan.setOnClickListener(this);
        tv_quanxuan = (TextView) findViewById(R.id.tv_quanxuan);
        tv_quanxuan.setOnClickListener(this);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        recycler_list.setLayoutManager(new LinearLayoutManager(this));

        getIntentData();

    }

    private void getIntentData() {
        childEntity = (SoftMusicDetailBean) getIntent().getExtras().getSerializable("model");
        list = childEntity.getChild();
        if (list != null && list.size() > 0){
            mMyAdapter = new MyAdapter(R.layout.item_download, list);
            recycler_list.setAdapter(mMyAdapter);
            mMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    //                Toast.makeText(DownLoadListActivity.this, "触发", Toast.LENGTH_SHORT).show();
                    list.get(position).setChecked(!list.get(position).isChecked());
                    //                mMyAdapter.setNewData(mList);
                    mMyAdapter.notifyDataSetChanged();
                    if (!isAllChecked()) {
                        iv_quanxuan.setImageResource(R.drawable.noquanxuan);
                    } else {
                        iv_quanxuan.setImageResource(R.drawable.quanxuan_red);
                    }
                }
            });
        }
    }

    private void quanxuan() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(true);
        }
        //        mMyAdapter.setNewData(mList);
        mMyAdapter.notifyDataSetChanged();
        iv_quanxuan.setImageResource(R.drawable.quanxuan_red);
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

    private void noquanxuan() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
        //        mMyAdapter.setNewData(mList);
        mMyAdapter.notifyDataSetChanged();
        iv_quanxuan.setImageResource(R.drawable.noquanxuan);
    }

    private class MyAdapter extends BaseQuickAdapter<SoftMusicDetailBean.ChildEntity, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<SoftMusicDetailBean.ChildEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, SoftMusicDetailBean.ChildEntity item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_ischeck);
            if (item.isChecked()) {
                //                Glide.with(mContext).load(R.drawable.quanxuan_red).into(imageView);//glide加载图片会有闪烁
                imageView.setImageResource(R.drawable.quanxuan_red);
            } else {
                imageView.setImageResource(R.drawable.noquanxuan);
            }
            String created_at = item.getCreated_at();
            Logger.e(created_at);
            String[] split = created_at.split(" ");
            helper.setText(R.id.tv_name,item.getName())
                    .setText(R.id.tv_date,split[0])
                    .setText(R.id.tv_time,item.getVideo_time())
                    .setText(R.id.tv_order,helper.getAdapterPosition()+1+"");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_quanxuan:
            case R.id.tv_quanxuan:
                if (isAllChecked) {
                    noquanxuan();
                } else {
                    quanxuan();
                }
                isAllChecked = !isAllChecked;
                break;
            case R.id.tv_download:
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isChecked()){
                        SoftMusicDetailBean.ChildEntity childEntity = list.get(i);
                        String created_at = childEntity.getCreated_at();
                        String[] split = created_at.split(" ");
                        DownLoadListBean DownLoadListBean = new DownLoadListBean(childEntity.getId(),childEntity.getXk_id(),
                                childEntity.getName(),childEntity.getVideo_time(), split[0], split[1],
                                childEntity.getVideo_url(), childEntity.getTxt_url(),childEntity.getT_header());
                        DownUtils.add(DownLoadListBean);
                        GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                        OkDownload.request(childEntity.getXk_id()+"_"+childEntity.getId(), request)
                                .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download")
                                .fileName(childEntity.getName()+childEntity.getXk_id()+"_"+childEntity.getId()+".mp3")
                                .extra3(DownLoadListBean)//额外数据
                                .save()
                                .register(new LogDownloadListener())//当前任务的回调监听
                                .start();
                        OkGo.<File>get(childEntity.getTxt_url())
                                .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download"
                                        ,childEntity.getXk_id()+"_"+childEntity.getId()+childEntity.getName()+".txt") {
                                    @Override
                                    public void onSuccess(Response<File> response) {
                                        int code = response.code();
                                        if (code >= 200 && code <= 204){
                                            Logger.e("文稿下载完成");
                                        }
                                    }
                                });
                    }
                }
                break;
        }
    }
}
