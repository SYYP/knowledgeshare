package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
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
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;

/**
 * 已下载点进来的详情
 */
public class AlreadyDownloadDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.recycler_kcmc)
    RecyclerView recyclerKcmc;
    @BindView(R.id.all_checkBox)
    CheckBox allCheckBox;
    @BindView(R.id.heji_tv)
    TextView hejiTv;
    @BindView(R.id.delete_tv)
    TextView deleteTv;
    @BindView(R.id.bianji_rl)
    RelativeLayout bianjiRl;
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
        initMusic();
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
                    if (!isAllChecked()) {
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
        switch (type) {
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
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                               String mytype = "";
                                               if (type.equals("free")) {
                                                   mytype = "free";
                                               } else if (type.equals("comment")) {
                                                   mytype = "everydaycomment";
                                               } else if (type.equals("xiaoke")) {
                                                   mytype = "softmusicdetail";
                                               } else {
                                                   mytype = "zhuanlandetail";
                                               }
                                               //还要设置一个播放主界面的list数据，因为自动播放下一首上一首的时候主界面的数据也得变
                                               List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                                               for (int i = 0; i < list.size(); i++) {
                                                   DownLoadListsBean.ListBean listBean1 = list.get(i);
                                                   MusicTypeBean musicTypeBean1 = new MusicTypeBean(mytype,
                                                           listBean1.getIconUrl(), listBean1.getName(), listBean1.getTypeId(),
                                                           listBean1.isSave());
                                                   musicTypeBean1.setMsg("musicplayertype");
                                                   musicTypeBeanList.add(musicTypeBean1);
                                               }
                                               MediaService.insertMusicTypeList(musicTypeBeanList);
                                               //播放列表
                                               List<PlayerBean> beanList = new ArrayList<PlayerBean>();
                                               for (int i = 0; i < list.size(); i++) {
                                                   DownLoadListsBean.ListBean listBean1 = list.get(i);
                                                   PlayerBean playerBean1 = new PlayerBean(listBean1.getIconUrl(), listBean1.getName(),
                                                           "", "", loadFromSDFile(listBean1.getName() + listBean1.getTypeId() + "_"
                                                           + listBean1.getChildId() + ".mp3"));
                                                   beanList.add(playerBean1);
                                               }
                                               MediaService.insertMusicList(beanList);
                                               //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                                               List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                                               for (int i = 0; i < list.size(); i++) {
                                                   DownLoadListsBean.ListBean entity = list.get(i);
                                                   BofangHistroyBean bofangHistroyBean = new BofangHistroyBean(mytype,
                                                           Integer.parseInt(entity.getChildId()), entity.getName(),
                                                           entity.getDate(), entity.getVideoUrl(), entity.getGood_count(),
                                                           entity.getCollect_count(), entity.getView_count(), entity.isDianzan(), entity.isCollected()
                                                           , entity.getIconUrl(), entity.getParentName(), entity.getH5_url()
                                                           , SystemClock.currentThreadTimeMillis(), entity.getTypeId(),
                                                           entity.getParentName(), entity.getTxtUrl());
                                                   bofangHistroyBean.setLocal(true);
                                                   histroyBeanList.add(bofangHistroyBean);
                                               }
                                               MediaService.insertBoFangHistroyList(histroyBeanList);
                                               setISshow(true);
                                               DownLoadListsBean.ListBean listBean = list.get(position);
                                               PlayerBean playerBean = new PlayerBean(listBean.getIconUrl(), listBean.getName(),
                                                       listBean.getParentName(), "", loadFromSDFile(listBean.getName() + listBean.getTypeId() + "_"
                                                       + listBean.getChildId() + ".mp3"), position);
                                               gobofang(playerBean);
                                           }
                                       }

        );
    }

    private String loadFromSDFile(String fname) {
        fname = "/" + fname;
        String result = null;
        try {
            switch (type) {
                case "free":
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download" + fname);
                    result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download" + fname;
                    break;
                case "comment":
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download" + fname);
                    result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download" + fname;
                    break;
                case "xiaoke":
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download" + fname);
                    result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download" + fname;
                    break;
                case "zhuanlan":
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download" + fname);
                    result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download" + fname;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AlreadyDownloadDetailActivity.this, "没有找到指定文件", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private void gobofang(final PlayerBean playerBean) {
        mMyBinder.setMusicLocal(playerBean);
        ClickPopShow();
        playerBean.setMsg("refreshplayer");
        EventBus.getDefault().postSticky(playerBean);
    }

    private MediaService.MyBinder mMyBinder;
    //“绑定”服务的intent
    private Intent MediaServiceIntent;

    private void initMusic() {
        MediaServiceIntent = new Intent(this, MediaService.class);
        //        startService(MediaServiceIntent);
        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
            if (mMyBinder.isPlaying()) {
            } else {
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

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
                    if (list.size() > 0) {
                        titleContentRightTv.setText("取消");
                        MyUtils.setMargins(recyclerKcmc, 0, 0, 0, 100);
                        bianjiRl.setVisibility(View.VISIBLE);
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setVisibility(true);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    titleContentRightTv.setText("编辑");
                    MyUtils.setMargins(recyclerKcmc, 0, 0, 0, 0);
                    bianjiRl.setVisibility(View.GONE);
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setVisibility(false);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.delete_tv:
                for (int i = list.size() - 1; i >= 0; i--) {
                    if (list.get(i).isChecked()) {
                        GetRequest<File> request = OkGo.<File>get(list.get(i).getVideoUrl());
                        DownloadTask task = new DownloadTask(list.get(i).getTypeId() + "_" + list.get(i).getChildId(), request);
                        Logger.e("TAG:" + list.get(i).getTypeId() + "_" + list.get(i).getChildId());
                        task.remove(true);
                        list.remove(i);
                    }
                    if (list.size() == 0) {
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
            if (item.isVisibility()) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }

            if (type.equals("zhuanlan")) {
                wengaoIv.setVisibility(View.GONE);
            }

            sizeTv.setText(helper.getPosition() + 1 + "");
            titleTv.setText(item.getName());
            nameTv.setText(item.gettName());
            timeTv.setText(item.getTime());
            checkBox.setChecked(item.isChecked());


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!((CheckBox) view).isChecked()) {//未选中
                        allCheckBox.setChecked(false);
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                        if (isAllChecked()) {
                            allCheckBox.setChecked(true);
                        }
                    }
                }
            });

            wengaoIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WenGaoFileActivity.class);
                    intent.putExtra("img", item.getIconUrl());
                    intent.putExtra("title", item.gettName());
                    intent.putExtra("id", item.getTypeId());
                    intent.putExtra("childId", item.getChildId());
                    intent.putExtra("type", type);
                    startActivity(intent);
                }
            });
        }
    }
}
