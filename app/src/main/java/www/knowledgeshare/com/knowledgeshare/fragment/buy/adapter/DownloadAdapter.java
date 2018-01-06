/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package www.knowledgeshare.com.knowledgeshare.fragment.buy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
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
import com.orhanobut.logger.Logger;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2017/6/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder>{

    public static final int TYPE_ALL = 0;
    public static final int TYPE_FINISH = 1;
    public static final int TYPE_ING = 2;
    public static final int TYPE_REMOVE = 3;

    private List<DownloadTask> values;
    private NumberFormat numberFormat;
    private LayoutInflater inflater;
    private Context context;
    private int type;
    List<DownLoadListsBean> list = new ArrayList<>();
    List<DownLoadListsBean.ListBean> listAllBeen = new ArrayList<>();
    private DownloadTask task;

    public DownloadAdapter(Context context, List<DownloadTask> list) {
        this.context = context;
        this.values = list;
        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateData(int type) {
        //这里是将数据库的数据恢复
        this.type = type;
        if (type == TYPE_ALL) values = OkDownload.restore(DownloadManager.getInstance().getAll());
        if (type == TYPE_FINISH) values = OkDownload.restore(DownloadManager.getInstance().getFinished());
        if (type == TYPE_ING) values = OkDownload.restore(DownloadManager.getInstance().getDownloading());
        if (type == TYPE_REMOVE){
            values = new ArrayList<>();
            /*values = OkDownload.restore(DownloadManager.getInstance().getDownloading());
            for (int i = 0; i < values.size(); i++) {
                task = values.get(i);
                values.remove(i);
                task.remove(true);
                notifyDataSetChanged();
            }*/
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

        @BindView(R.id.icon) CircleImageView icon;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.downloadSize) TextView downloadSize;
        @BindView(R.id.pbProgress) ProgressBar pbProgress;
        @BindView(R.id.start) ImageView download;
        @BindView(R.id.start_or_pause) TextView startOrPause;
        @BindView(R.id.shachu_tv) TextView shanchuTv;
        @BindView(R.id.content_ll) LinearLayout contentLl;
        private DownloadTask task;
        private String tag;

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
                Logger.e("下载中头像URL地址："+listAllBeen.get(getAdapterPosition()).getIconUrl());
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
                    download.setImageDrawable(context.getResources().getDrawable(R.drawable.power_zanting));
                    break;
            }
            pbProgress.setMax(10000);
            pbProgress.setProgress((int) (progress.fraction * 10000));
        }

        @OnClick(R.id.start)
        public void start() {
            Progress progress = task.progress;
            switch (progress.status) {
                case Progress.PAUSE:
                case Progress.NONE:
                case Progress.ERROR:
                    task.start();
                    break;
                case Progress.LOADING:
                    task.pause();
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
            if (throwable != null) throwable.printStackTrace();
        }

        @Override
        public void onFinish(File file, Progress progress) {
            Toast.makeText(context, "下载完成:" + progress.filePath, Toast.LENGTH_SHORT).show();
//            updateData(type);
            notifyDataSetChanged();
        }

        @Override
        public void onRemove(Progress progress) {

        }
    }
}
