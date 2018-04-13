package www.knowledgeshare.com.knowledgeshare.activity;

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
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * Created by Administrator on 2017/11/28.
 */

public class AlreadyDownLoadFragment2 extends BaseFragment {
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

        List<String> ss = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String typeName = list.get(i).getTypeName();
            ss.add(typeName);
        }

        Set set = new  HashSet();
        List<String> newList = new ArrayList<>();
        for (String cd : ss) {
            if(set.add(cd)){
                newList.add(cd);
            }
        }

        int typenum = newList.size();//几种小课
        final List<List<DownLoadListsBean>> dalist = new ArrayList<>();
        for (int i = 0; i < typenum; i++) {
            List<DownLoadListsBean> itemList = new ArrayList<>();
            String typeName = newList.get(i);
            for (int j = 0; j < list.size(); j++) {
                if (typeName.equals(list.get(j).getTypeName())){
                    itemList.add(list.get(j));
                }
            }
            dalist.add(itemList);
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

        AlreadyDownLoadAdapter adapter = new AlreadyDownLoadAdapter(R.layout.item_already_download,dalist);
        recyclerAlreadDownload.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dalist.get(position).get(0).getType().equals("xiaoke")){
                    /*List<DownLoadListsBean.ListBean> list1 = list.get(position).getList();
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
                    }*/

                    Intent intent = new Intent(getActivity(),AlreadyDownloadDetailActivity.class);
                    SpUtils.putBoolean(mContext,SpUtils.getString(mContext,"id","")+"xiaoke"+dalist.get(position).get(0).getTypeId()+dalist.get(position).get(0).getTypeName(),true);
                    intent.putExtra("type","xiaoke");
                    intent.putExtra("title",dalist.get(position).get(0).getTypeName());
                    intent.putExtra("list", (Serializable) dalist.get(position));
                    startActivity(intent);
                }
                if (dalist.get(position).get(0).getType().equals("zhuanlan")){
//                    List<DownLoadListsBean.ListBean> list1 = list.get(position).getList();
                    SpUtils.putBoolean(mContext,SpUtils.getString(mContext,"id","")+"zhuanlan"+dalist.get(position).get(0).getTypeId()+dalist.get(position).get(0).getTypeName(),true);
                    Intent intent = new Intent(getActivity(),AlreadyDownloadDetailActivity.class);
                    intent.putExtra("type","zhuanlan");
                    intent.putExtra("title",dalist.get(position).get(0).getTypeName());
                    intent.putExtra("list", (Serializable) dalist.get(position));
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

    private class AlreadyDownLoadAdapter extends BaseQuickAdapter<List<DownLoadListsBean>,BaseViewHolder>{

        public AlreadyDownLoadAdapter(@LayoutRes int layoutResId, @Nullable List<List<DownLoadListsBean>> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, List<DownLoadListsBean> item) {

            ImageView alreadyFaceIv = helper.getView(R.id.already_face_iv);
            ImageView alreadyTishiIv = helper.getView(R.id.already_tishi_iv);
            TextView alreadyTitleTv = helper.getView(R.id.already_title_tv);
            TextView alreadyNmaeTv = helper.getView(R.id.already_name_tv);
            TextView alreadyDescTv = helper.getView(R.id.already_desc_tv);
            TextView alreadyZhangjieTv = helper.getView(R.id.already_zhangjie_tv);
            TextView alreadyXiazaiTv = helper.getView(R.id.already_xiazai_tv);
//            int adapterPosition = helper.getAdapterPosition();
            int adapterPosition = 0;
            Glide.with(mActivity).load(item.get(adapterPosition).getTypeIcon()).into(alreadyFaceIv);
            alreadyTitleTv.setText(item.get(adapterPosition).getTypeName());
            if (item.get(adapterPosition).getType().equals("xiaoke")){
                alreadyNmaeTv.setText(item.get(adapterPosition).gettName());
                boolean isLook = SpUtils.getBoolean(mContext, SpUtils.getString(mContext, "id", "") + "xiaoke" + item.get(adapterPosition).getTypeId()+item.get(adapterPosition).getTypeName(), false);
                if (isLook){
                    alreadyTishiIv.setVisibility(View.GONE);
                }else {
                    alreadyTishiIv.setVisibility(View.VISIBLE);
                }
            }else {
                alreadyNmaeTv.setVisibility(View.GONE);
                boolean isLook = SpUtils.getBoolean(mContext, SpUtils.getString(mContext, "id", "") + "zhuanlan" + item.get(adapterPosition).getTypeId()+item.get(adapterPosition).getTypeName(), false);
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
            alreadyDescTv.setText(item.get(adapterPosition).gettTag());
            alreadyZhangjieTv.setText("共"+item.get(adapterPosition).getTypeSize()+"节");

            alreadyXiazaiTv.setText("共缓存"+item.size()+"章节");
        }
    }
}
