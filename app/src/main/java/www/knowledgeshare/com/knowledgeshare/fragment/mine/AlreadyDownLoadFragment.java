package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.message.common.inter.ITagManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.AlreadyDownloadDetailActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.EasyLessonBean;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * Created by Administrator on 2017/11/28.
 */

public class AlreadyDownLoadFragment extends BaseFragment {
    @BindView(R.id.recycler_alread_download)
    RecyclerView recyclerAlreadDownload;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.free_ll)
    LinearLayout freeLl;
    @BindView(R.id.comment_ll)
    LinearLayout commentLl;
    Unbinder unbinder;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_already_download, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @OnClick(R.id.free_ll)
    public void free(){
        TUtils.showShort(mContext,"免费专区");
    }

    @OnClick(R.id.comment_ll)
    public void comment(){
        TUtils.showShort(mContext,"每日推荐");
    }

    @Override
    protected void initData() {

        List<DownLoadListBean> downList = DownUtils.search();


        recyclerAlreadDownload.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerAlreadDownload.setNestedScrollingEnabled(false);
        List<EasyLessonBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EasyLessonBean easyLessonBean = new EasyLessonBean();
            easyLessonBean.setTitle("如何成为一名合格的歌者");
            easyLessonBean.setName("刘鹏飞");
            easyLessonBean.setDesc("中国音乐好声音王牌级人物");
            easyLessonBean.setWendang("共100节");
            easyLessonBean.setHuancun("共缓存"+i+"章节");
            list.add(easyLessonBean);
        }
        AlreadyDownLoadAdapter adapter = new AlreadyDownLoadAdapter(R.layout.item_already_download,list);
        recyclerAlreadDownload.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), AlreadyDownloadDetailActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class AlreadyDownLoadAdapter extends BaseQuickAdapter<EasyLessonBean,BaseViewHolder>{

        public AlreadyDownLoadAdapter(@LayoutRes int layoutResId, @Nullable List<EasyLessonBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, EasyLessonBean item) {
            ImageView alreadyFaceIv = helper.getView(R.id.already_face_iv);
            ImageView alreadyTishiIv = helper.getView(R.id.already_tishi_iv);
            TextView alreadyTitleTv = helper.getView(R.id.already_title_tv);
            TextView alreadyNmaeTv = helper.getView(R.id.already_name_tv);
            TextView alreadyDescTv = helper.getView(R.id.already_desc_tv);
            TextView alreadyZhangjieTv = helper.getView(R.id.already_zhangjie_tv);
            TextView alreadyXiazaiTv = helper.getView(R.id.already_xiazai_tv);

            alreadyTitleTv.setText(item.getTitle());
            alreadyNmaeTv.setText(item.getName());
            alreadyDescTv.setText(item.getDesc());
            alreadyZhangjieTv.setText(item.getWendang());
            alreadyXiazaiTv.setText(item.getHuancun());
        }
    }
}
