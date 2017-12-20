package www.knowledgeshare.com.knowledgeshare.fragment.buy;

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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.BuyZhuanLanActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.BuyXkBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.EasyLessonBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * Created by Administrator on 2017/11/23.
 * 音乐大师班
 */

public class MaestroClassFragment extends BaseFragment {
    @BindView(R.id.recycler_meastro)
    RecyclerView recyclerMeastro;
    Unbinder unbinder;
    private List<BuyXkBean> list = new ArrayList<>();


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_meastro_class, null);
        unbinder = ButterKnife.bind(this, inflate);

        return inflate;
    }

    @Override
    protected void initData() {
        recyclerMeastro.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerMeastro.setNestedScrollingEnabled(false);
        requestBuyZl();
        /*List<EasyLessonBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EasyLessonBean easyLessonBean = new EasyLessonBean();
            easyLessonBean.setTitle("崔宗顺的男低音歌唱家秘籍");
            easyLessonBean.setDesc("男低音，一个神秘又充满魅力的音部");
            easyLessonBean.setUpdata(i + "小时前更新");
            easyLessonBean.setZlmc("最新更新的专栏名称");
            list.add(easyLessonBean);
        }*/

    }

    private void requestBuyZl() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        HttpParams params = new HttpParams();
        params.put("after","");

        OkGo.<BuyXkBean>get(MyContants.buyZl)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<BuyXkBean>(mActivity,BuyXkBean.class) {
                    @Override
                    public void onSuccess(Response<BuyXkBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            list.add(response.body());
                            MeastroClassAdapter adapter = new MeastroClassAdapter(R.layout.item_meastro_class, list);
                            recyclerMeastro.setAdapter(adapter);
                            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    startActivity(new Intent(mContext, BuyZhuanLanActivity.class));
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class MeastroClassAdapter extends BaseQuickAdapter<BuyXkBean, BaseViewHolder> {

        public MeastroClassAdapter(@LayoutRes int layoutResId, @Nullable List<BuyXkBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BuyXkBean item) {
            TextView classTitleTv = helper.getView(R.id.class_title_tv);
            TextView classDescTv = helper.getView(R.id.class_desc_tv);
            TextView classUpdataTv = helper.getView(R.id.class_updata_tv);
            TextView classZlmcTv = helper.getView(R.id.class_zlmc_tv);

            /*classTitleTv.setText(item.getTitle());
            classDescTv.setText(item.getDesc());
            classUpdataTv.setText(item.getUpdata());
            classZlmcTv.setText(item.getZlmc());*/
        }
    }
}
