package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.lzy.okserver.task.XExecutor;
import com.orhanobut.logger.Logger;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MusicDownLoadingFragment extends BaseFragment implements View.OnClickListener, XExecutor.OnAllTaskEndListener {

    @BindView(R.id.qbks_ll)
    LinearLayout qbksLl;
    @BindView(R.id.qbsc_ll)
    LinearLayout qbscLl;
    @BindView(R.id.recycler_xzz)
    RecyclerView recyclerXzz;
    @BindView(R.id.kaishi_tv)
    TextView kaishiTv;
    @BindView(R.id.kongzhi_ll)
    LinearLayout kongzhiLl;
    Unbinder unbinder;
    private DownloadAdapter adapter;
    private OkDownload okDownload;

    private List<DownloadTask> values;

    private TextView mTv_content;
    private BaseDialog mNetDialog;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_downloading, null);
        unbinder = ButterKnife.bind(this, inflate);
        EventBus.getDefault().register(this);
        qbscLl.setOnClickListener(this);
        qbksLl.setOnClickListener(this);
        okDownload = OkDownload.getInstance();
        initNETDialog();
        return inflate;
    }

    @Override
    protected void initData() {
        initAdapter();
    }

    private void initAdapter() {
        //        taskList = OkDownload.restore(DownloadManager.getInstance().getDownloading());
        adapter = new DownloadAdapter(getActivity());
        adapter.updateData(DownloadAdapter.TYPE_ING);
        recyclerXzz.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerXzz.setNestedScrollingEnabled(false);
        recyclerXzz.setAdapter(adapter);
        okDownload.addOnAllTaskEndListener(this);
        if (values.size() == 0) {
            kongzhiLl.setVisibility(View.GONE);
        } else {
            EventBean eventBean = new EventBean("number");
            EventBus.getDefault().postSticky(eventBean);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        EventBean eventBean = new EventBean("refrash");
        EventBus.getDefault().postSticky(eventBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("wifidown1")) {
            boolean wifichecked = SpUtils.getBoolean(mContext, "wifichecked", false);
            Logger.e(wifichecked + "");
            if (wifichecked) {
                if (values.size() > 0) {
                    okDownload.startAll();
                    Logger.e("wifidown----下载");
                } else {
                    Logger.e("wifidown----未下载");
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        okDownload.removeOnAllTaskEndListener(this);
        EventBus.getDefault().unregister(this);
        //        adapter.unRegister();
    }

    @Override
    public void onAllTaskEnd() {
        //        TUtils.showShort(mContext,"所有下载任务已结束");
        kaishiTv.setText("全部开始");
    }

    private void initNETDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(mContext);
        mNetDialog = builder.setViewId(R.layout.dialog_iswifi)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mTv_content = mNetDialog.getView(R.id.tv_content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qbks_ll:
                if (TextUtils.equals("全部开始", kaishiTv.getText().toString())) {
                    int apnType = NetWorkUtils.getAPNType(mContext);
                    if (apnType == 0) {
                        Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (apnType == 2 || apnType == 3 || apnType == 4) {
                        mTv_content.setText("当前无WiFi，是否允许用流量下载");
                        mNetDialog.show();
                        mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mNetDialog.dismiss();
                                return;
                            }
                        });
                        mNetDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//记住用户允许流量下载
                                SpUtils.putBoolean(mContext, "nowifiallowdown", true);
                                kaishiTv.setText("全部暂停");
                                okDownload.startAll();
                                mNetDialog.dismiss();
                            }
                        });
                    } else {
                        SpUtils.putBoolean(mContext, "nowifiallowdown", true);
                        kaishiTv.setText("全部暂停");
                        okDownload.startAll();
                        mNetDialog.dismiss();
                    }
                } else {
                    okDownload.pauseAll();
                    kaishiTv.setText("全部开始");
                }
                break;
            case R.id.qbsc_ll:
                showTips();
        }
    }


    private void showTips() {
        new NormalAlertDialog.Builder(getActivity())
                .setTitleVisible(true).setTitleText("提示")
                .setTitleTextColor(R.color.text_black)
                .setContentText("确认要全部删除吗？")
                .setContentTextColor(R.color.text_black)
                .setLeftButtonText("是")
                .setLeftButtonTextColor(R.color.text_black)
                .setRightButtonText("否")
                .setRightButtonTextColor(R.color.text_black)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                        //                        okDownload.removeAll();
                        adapter.updateData(DownloadAdapter.TYPE_REMOVE);
                        adapter.notifyDataSetChanged();
                        EventBean eventBean = new EventBean("number");
                        EventBus.getDefault().postSticky(eventBean);
                        /*for (int i = taskList.size()-1; i >= 0; i--) {
                            task = taskList.get(i);
                            taskList.remove(i);
                            task.remove(true);
                        }
                        adapter.notifyDataSetChanged();*/
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

        public static final int TYPE_ALL = 0;
        public static final int TYPE_FINISH = 1;
        public static final int TYPE_ING = 2;
        public static final int TYPE_REMOVE = 3;

        private NumberFormat numberFormat;
        private LayoutInflater inflater;
        private Context context;
        private int type;
        List<DownLoadListsBean> list = new ArrayList<>();
        List<DownLoadListsBean.ListBean> listAllBeen = new ArrayList<>();
        private DownloadTask task;

        public DownloadAdapter(Context context) {
            this.context = context;
            numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(2);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void updateData(int type) {
            //这里是将数据库的数据恢复
            this.type = type;
            if (type == TYPE_ALL)
                values = OkDownload.restore(DownloadManager.getInstance().getAll());
            if (type == TYPE_FINISH)
                values = OkDownload.restore(DownloadManager.getInstance().getFinished());
            if (type == TYPE_ING)
                values = OkDownload.restore(DownloadManager.getInstance().getDownloading());
            if (type == TYPE_REMOVE) {
                values = OkDownload.restore(DownloadManager.getInstance().getDownloading());
                for (int i = values.size() - 1; i >= 0; i--) {
                    task = values.get(i);
                    values.remove(i);
                    task.remove(true);
                    notifyDataSetChanged();
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_download_manager, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            task = values.get(position);
            String tag = createTag(task);
            task.register(new ListDownloadListener(tag, holder))//
                    .register(new LogDownloadListener());
            holder.setTag(tag);
            holder.setTask(task);
            holder.bind();
            holder.refresh(task.progress);
            holder.shanchuTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterPosition = holder.getAdapterPosition();
                    values.remove(adapterPosition);
                    task.remove(true);
                    notifyItemRemoved(adapterPosition);
                    EventBean eventBean = new EventBean("number");
                    EventBus.getDefault().postSticky(eventBean);
                }
            });
        }

        public void unRegister() {
            Map<String, DownloadTask> taskMap = OkDownload.getInstance().getTaskMap();
            for (DownloadTask task : taskMap.values()) {
                task.unRegister(createTag(task));
            }
        }

        private String createTag(DownloadTask task) {
            return type + "_" + task.progress.tag;
        }

        @Override
        public int getItemCount() {
            return values == null ? 0 : values.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.icon)
            CircleImageView icon;
            @BindView(R.id.name)
            TextView name;
            @BindView(R.id.downloadSize)
            TextView downloadSize;
            @BindView(R.id.pbProgress)
            ProgressBar pbProgress;
            @BindView(R.id.start)
            ImageView download;
            @BindView(R.id.start_or_pause)
            TextView startOrPause;
            @BindView(R.id.shachu_tv)
            TextView shanchuTv;
            @BindView(R.id.content_ll)
            LinearLayout contentLl;
            private DownloadTask task;
            private String tag;
            private int apnType;

            public LinearLayout getContentLl() {
                return contentLl;
            }

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void setTask(DownloadTask task) {
                this.task = task;
            }

            public void bind() {
                Progress progress = task.progress;
                DownLoadListsBean downLoadListBean = (DownLoadListsBean) progress.extra3;

                list.add(downLoadListBean);
                for (int i = 0; i < list.size(); i++) {
                    List<DownLoadListsBean.ListBean> listBeen = list.get(i).getList();
                    listAllBeen.addAll(listBeen);
                }
                if (listAllBeen != null) {
                    Glide.with(context).load(listAllBeen.get(getAdapterPosition()).getIconUrl()).into(icon);
                    Logger.e("下载中头像URL地址：" + listAllBeen.get(getAdapterPosition()).getIconUrl());
                    name.setText(listAllBeen.get(getAdapterPosition()).getName());
                } else {
                    name.setText(progress.fileName);
                }
            }

            public void refresh(Progress progress) {
                String currentSize = Formatter.formatFileSize(context, progress.currentSize);
                String totalSize = Formatter.formatFileSize(context, progress.totalSize);
                downloadSize.setText(currentSize + "/" + totalSize);
                switch (progress.status) {
                    case Progress.NONE:
                        startOrPause.setText("下载");
                        break;
                    case Progress.PAUSE:
                        startOrPause.setText("已暂停");
                        kaishiTv.setText("全部开始");
                        download.setImageDrawable(context.getResources().getDrawable(R.drawable.power_kaishi_iv));
                        break;
                    case Progress.ERROR:
                        startOrPause.setText("下载出错");
                        download.setImageDrawable(context.getResources().getDrawable(R.drawable.power_kaishi_iv));
                        break;
                    case Progress.WAITING:
                        startOrPause.setText("等待中");
                        download.setImageDrawable(context.getResources().getDrawable(R.drawable.power_kaishi_iv));
                        break;
                    case Progress.FINISH:
                        startOrPause.setText("下载完成");
                        break;
                    case Progress.LOADING:
                        startOrPause.setText("正在下载");
                        kaishiTv.setText("全部暂停");
                        download.setImageDrawable(context.getResources().getDrawable(R.drawable.power_zanting));
                        break;
                }
                pbProgress.setMax(10000);
                pbProgress.setProgress((int) (progress.fraction * 10000));
            }

            @OnClick(R.id.start)
            public void start() {
                apnType = NetWorkUtils.getAPNType(mContext);
                final Progress progress = task.progress;
                switch (progress.status) {
                    case Progress.PAUSE:
                        if (apnType == 0) {
                            Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
                            mTv_content.setText("当前无WiFi，是否允许用流量下载");
                            mNetDialog.show();
                            mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mNetDialog.dismiss();
                                    task.pause();
                                }
                            });
                            mNetDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {//记住用户允许流量下载
                                    SpUtils.putBoolean(mContext, "nowifiallowdown", true);
                                    kaishiTv.setText("全部暂停");
                                    task.start();
                                    mNetDialog.dismiss();
                                }
                            });
                        }
                        break;
                    case Progress.NONE:
                        if (apnType == 0) {
                            Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
                            mTv_content.setText("当前无WiFi，是否允许用流量下载");
                            mNetDialog.show();
                            mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mNetDialog.dismiss();
                                    task.pause();
                                }
                            });
                            mNetDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {//记住用户允许流量下载
                                    SpUtils.putBoolean(mContext, "nowifiallowdown", true);
                                    kaishiTv.setText("全部暂停");
                                    task.start();
                                    mNetDialog.dismiss();
                                }
                            });
                        }
                        break;
                    case Progress.ERROR:
                        task.start();
                        for (int i = 0; i < values.size(); i++) {
                            if (progress.status == Progress.LOADING) {
                                kaishiTv.setText("全部暂停");
                            } else {
                                kaishiTv.setText("全部开始");
                            }
                        }
                        break;
                    case Progress.LOADING:
                        task.pause();
                        for (int i = 0; i < values.size(); i++) {
                            if (progress.status == Progress.LOADING) {
                                kaishiTv.setText("全部暂停");
                            } else {
                                kaishiTv.setText("全部开始");
                            }
                        }
                        break;
                    case Progress.FINISH:

                        break;
                }
                refresh(progress);
            }

        /*@OnClick(R.id.remove)
        public void remove() {
            task.remove(true);
            updateData(type);
        }*/

        /*@OnClick(R.id.restart)
        public void restart() {
            task.restart();
        }*/

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getTag() {
                return tag;
            }
        }

        private class ListDownloadListener extends DownloadListener {

            private ViewHolder holder;

            ListDownloadListener(Object tag, ViewHolder holder) {
                super(tag);
                this.holder = holder;
            }

            @Override
            public void onStart(Progress progress) {
            }

            @Override
            public void onProgress(Progress progress) {
                if (tag == holder.getTag()) {
                    holder.refresh(progress);
                }
            }

            @Override
            public void onError(Progress progress) {
                Throwable throwable = progress.exception;
                if (throwable != null)
                    throwable.printStackTrace();
            }

            @Override
            public void onFinish(File file, Progress progress) {
                //            Toast.makeText(context, "下载完成:" + progress.filePath, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                updateData(type);
                EventBean eventBean = new EventBean("number");
                EventBus.getDefault().postSticky(eventBean);
            }

            @Override
            public void onRemove(Progress progress) {

            }
        }
    }
}
