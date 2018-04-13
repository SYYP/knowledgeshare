package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Intent;
import android.os.Environment;
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
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.AlreadyDownloadDetailActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * Created by Administrator on 2017/11/28.
 */

public class AlreadyDownLoadFragment extends BaseFragment {
    @BindView(R.id.recycler_alread_download) RecyclerView recyclerAlreadDownload;
    @BindView(R.id.scrollView) NestedScrollView scrollView;
    @BindView(R.id.free_ll) LinearLayout freeLl;
    @BindView(R.id.comment_ll) LinearLayout commentLl;
    @BindView(R.id.free_tv) TextView freeTv;
    @BindView(R.id.comment_tv) TextView commentTv;
    Unbinder unbinder;
    private DownLoadListsBean downLoadListBean;
    private List<DownLoadListsBean> freeList;
    private List<DownLoadListsBean> commentList;
    private List<DownLoadListsBean> list = new ArrayList<>();

    private List<DownloadTask> restoreList = new ArrayList<>();
    List<String> xiaokeNameList = new ArrayList<>();
    private String typeName = "";

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_already_download, null);
        unbinder = ButterKnife.bind(this, inflate);
        EventBus.getDefault().register(this);
        return inflate;
    }

    @OnClick(R.id.free_ll)
    public void free(){
        Intent intent = new Intent(getActivity(),AlreadyDownloadDetailActivity.class);
        intent.putExtra("type","free");
        intent.putExtra("list", (Serializable) freeList);
        startActivity(intent);
    }

    @OnClick(R.id.comment_ll)
    public void comment(){
        Intent intent = new Intent(getActivity(),AlreadyDownloadDetailActivity.class);
        intent.putExtra("type","comment");
        intent.putExtra("list", (Serializable) commentList);
        startActivity(intent);
    }

    @Override
    protected void initData() {
        recyclerAlreadDownload.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerAlreadDownload.setNestedScrollingEnabled(false);
        freeList = new ArrayList<>();
        commentList = new ArrayList<>();
        restoreList = OkDownload.restore(DownloadManager.getInstance().getFinished());
        /*for (int i = 0; i < restoreList.size(); i++) {
            Progress progress = restoreList.get(i).progress;
            DownLoadListsBean extra3 = (DownLoadListsBean) progress.extra3;
            if (extra3.getType().equals("xiaoke")){
                if (typeName.equals(extra3.getTypeName())){
                    restoreList.remove(i);
                }else {
                    typeName = extra3.getTypeName();
                }
            }
        }*/
        for (int i = 0; i < restoreList.size(); i++) {
            Progress progress = restoreList.get(i).progress;
            downLoadListBean = (DownLoadListsBean) progress.extra3;
            if (downLoadListBean.getType().equals("xiaoke")){
                list.add(downLoadListBean);
            }
            if (downLoadListBean.getType().equals("zhuanlan")){
                list.add(downLoadListBean);
            }
            if (downLoadListBean.getType().equals("free")){
                freeList.add(downLoadListBean);
            }
            if (downLoadListBean.getType().equals("comment")){
                commentList.add(downLoadListBean);
            }
        }

        for (int i = 0; i < list.size(); i++) {
        }


        if (freeList.size() == 0){
            freeLl.setVisibility(View.GONE);
        }else {
            freeLl.setVisibility(View.VISIBLE);
            freeTv.setText("共缓存"+ freeList.size()+"节");
        }
        if (commentList.size() == 0){
            commentLl.setVisibility(View.GONE);
        }else {
            commentLl.setVisibility(View.VISIBLE);
            commentTv.setText("共缓存"+ commentList.size()+"节");
        }

        AlreadyDownLoadAdapter adapter = new AlreadyDownLoadAdapter(R.layout.item_already_download,list);
        recyclerAlreadDownload.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list.get(position).getType().equals("xiaoke")){
                    List<DownLoadListsBean.ListBean> list1 = list.get(position).getList();
                    List<DownLoadListsBean.ListBean> list2 = new ArrayList<>();
                    File file = null;
                    for (int i = 0; i < list1.size(); i++) {
                        DownLoadListsBean.ListBean listBean = list1.get(i);
                        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download/" +
                                list1.get(i).getName() + list1.get(i).getTypeId() + "_" + list1.get(i).getChildId() + ".mp3");
                        if (file.exists()&&file.canRead()&&file.length()>3060000){//本地文件大于3M才显示
                            list2.add(listBean);
//                            Logger.e(file.length()+"");
                        }
                    }
                    Intent intent = new Intent(getActivity(),AlreadyDownloadDetailActivity.class);
                    SpUtils.putBoolean(mContext,SpUtils.getString(mContext,"id","")+"xiaoke"+list.get(position).getTypeId()+list.get(position).getTypeName(),true);
                    intent.putExtra("type","xiaoke");
                    intent.putExtra("title",list.get(position).getTypeName());
                    intent.putExtra("list", (Serializable) list2);
                    startActivity(intent);
                }
                if (list.get(position).getType().equals("zhuanlan")){
                    List<DownLoadListsBean.ListBean> list1 = list.get(position).getList();
                    SpUtils.putBoolean(mContext,SpUtils.getString(mContext,"id","")+"zhuanlan"+list.get(position).getTypeId()+list.get(position).getTypeName(),true);
                    Intent intent = new Intent(getActivity(),AlreadyDownloadDetailActivity.class);
                    intent.putExtra("type","zhuanlan");
                    intent.putExtra("title",list.get(position).getTypeName());
                    intent.putExtra("list", (Serializable) list1);
                    startActivity(intent);
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("refrash")) {
            restoreList.clear();
            list.clear();
            initData();
        }
        if (eventBean.getMsg().equals("back")){
            restoreList.clear();
            list.clear();
            initData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private class AlreadyDownLoadAdapter extends BaseQuickAdapter<DownLoadListsBean,BaseViewHolder>{

        public AlreadyDownLoadAdapter(@LayoutRes int layoutResId, @Nullable List<DownLoadListsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DownLoadListsBean item) {

            ImageView alreadyFaceIv = helper.getView(R.id.already_face_iv);
            ImageView alreadyTishiIv = helper.getView(R.id.already_tishi_iv);
            TextView alreadyTitleTv = helper.getView(R.id.already_title_tv);
            TextView alreadyNmaeTv = helper.getView(R.id.already_name_tv);
            TextView alreadyDescTv = helper.getView(R.id.already_desc_tv);
            TextView alreadyZhangjieTv = helper.getView(R.id.already_zhangjie_tv);
            TextView alreadyXiazaiTv = helper.getView(R.id.already_xiazai_tv);

            Glide.with(mActivity).load(item.getTypeIcon()).into(alreadyFaceIv);
            alreadyTitleTv.setText(item.getTypeName());
            if (item.getType().equals("xiaoke")){
                alreadyNmaeTv.setText(item.gettName());
                boolean isLook = SpUtils.getBoolean(mContext, SpUtils.getString(mContext, "id", "") + "xiaoke" + item.getTypeId()+item.getTypeName(), false);
                if (isLook){
                    alreadyTishiIv.setVisibility(View.GONE);
                }else {
                    alreadyTishiIv.setVisibility(View.VISIBLE);
                }
            }else {
                alreadyNmaeTv.setVisibility(View.GONE);
                boolean isLook = SpUtils.getBoolean(mContext, SpUtils.getString(mContext, "id", "") + "zhuanlan" + item.getTypeId()+item.getTypeName(), false);
                if (isLook){
                    alreadyTishiIv.setVisibility(View.GONE);
                }else {
                    alreadyTishiIv.setVisibility(View.VISIBLE);
                }
            }
//            int num = 0;
//            if (item.getType().equals("xiaoke")) {
//                List<DownLoadListsBean.ListBean> list1 = item.getList();
//                File file = null;
//
//                for (int i = 0; i < list1.size(); i++) {
//                    DownLoadListsBean.ListBean listBean = list1.get(i);
//                    file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download/" +
//                            list1.get(i).getName() + list1.get(i).getTypeId() + "_" + list1.get(i).getChildId() + ".mp3");
//                    if (file.exists() && file.canRead()) {
//                        num = num + 1;
//                    }
//                }
//            }
            alreadyDescTv.setText(item.gettTag());
            alreadyZhangjieTv.setText("共"+item.getTypeSize()+"节");
            alreadyXiazaiTv.setText("共缓存"+item.getList().size()+"章节");
        }
    }
}
