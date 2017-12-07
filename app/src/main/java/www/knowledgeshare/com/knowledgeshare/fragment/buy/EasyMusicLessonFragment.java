package www.knowledgeshare.com.knowledgeshare.fragment.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.EasyLessonBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;

/**
 * Created by Administrator on 2017/11/23.
 * 轻松音乐课
 */

public class EasyMusicLessonFragment extends BaseFragment {
    @BindView(R.id.recycler_easy)
    RecyclerView recyclerEasy;
    Unbinder unbinder;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_easy_lesson, null);
        unbinder = ButterKnife.bind(this, inflate);

        return inflate;
    }

    @Override
    protected void initData() {
        recyclerEasy.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerEasy.setNestedScrollingEnabled(false);

        List<EasyLessonBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EasyLessonBean easyLessonBean = new EasyLessonBean();
            easyLessonBean.setTitle("如何成为一名合格的歌者");
            easyLessonBean.setName("刘鹏飞");
            easyLessonBean.setDesc("中国音乐好声音王牌级人物");
            easyLessonBean.setYbf("已播放："+ i +"%");
            easyLessonBean.setGmrs("购买人数：110人");
            list.add(easyLessonBean);
        }
        EasyMusicLessonAdapter adapter = new EasyMusicLessonAdapter(R.layout.item_easy_lesson, list);
        recyclerEasy.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, SoftMusicDetailActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class EasyMusicLessonAdapter extends BaseQuickAdapter<EasyLessonBean, BaseViewHolder> {

        public EasyMusicLessonAdapter(@LayoutRes int layoutResId, @Nullable List<EasyLessonBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, EasyLessonBean item) {
            TextView easyTitleTv = helper.getView(R.id.easy_title_tv);
            TextView easyNameTv = helper.getView(R.id.easy_name_tv);
            TextView easyDescTv = helper.getView(R.id.easy_desc_tv);
            TextView easyYbfTv = helper.getView(R.id.easy_ybf_tv);
            TextView easyGmrsTv = helper.getView(R.id.easy_gmrs_tv);

            easyTitleTv.setText(item.getTitle());
            easyNameTv.setText(item.getName());
            easyDescTv.setText(item.getDesc());
            easyYbfTv.setText(item.getYbf());
            easyGmrsTv.setText(item.getGmrs());
        }

    }

}
