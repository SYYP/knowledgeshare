package www.knowledgeshare.com.knowledgeshare.fragment.buy;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.BuyZlBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

/**
 * Created by Administrator on 2017/11/23.
 * 音乐大师班
 */

public class MaestroClassFragment extends BaseFragment {
    @BindView(R.id.recycler_meastro)
    RecyclerView recyclerMeastro;
    @BindView(R.id.springview)
    SpringView springView;
    Unbinder unbinder;
    private List<BuyZlBean.DataBean> list = new ArrayList<>();
    private List<BuyZlBean.DataBean> listAll = new ArrayList<>();
    private int lastId;

    @Override
    protected void lazyLoad() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("softmusicrefresh")){
            springView.callFresh();
        }
    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_meastro_class, null);
        unbinder = ButterKnife.bind(this, inflate);
        EventBus.getDefault().register(this);
        initLoadMore();
        return inflate;
    }

    private void initLoadMore() {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (!NetWorkUtils.isNetworkConnected(mContext)){
                    Toast.makeText(mContext, "当前无网络连接，请检查设置", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listAll.clear();
                        requestBuyZl("");
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {
                if (!NetWorkUtils.isNetworkConnected(mContext)){
                    Toast.makeText(mContext, "当前无网络连接，请检查设置", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestBuyZl(lastId+"");
                        springView.onFinishFreshAndLoad();
                    }
                }, 2000);
            }
        });
        springView.setHeader(new MyHeader(mContext));
        springView.setFooter(new MyFooter(mContext));
    }

    @Override
    protected void initData() {
        recyclerMeastro.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerMeastro.setNestedScrollingEnabled(false);
        requestBuyZl("");
    }

    private void requestBuyZl(String after) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        HttpParams params = new HttpParams();
        params.put("after",after);

        OkGo.<BuyZlBean>get(MyContants.buyZl)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<BuyZlBean>(mActivity,BuyZlBean.class) {
                    @Override
                    public void onSuccess(Response<BuyZlBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            list = response.body().getData();
                            if (list.size() > 0){
                                listAll.addAll(list);
                            }else {
                                TUtils.showShort(mContext,"没有更多数据了");
                            }
                            MeastroClassAdapter adapter = new MeastroClassAdapter(R.layout.item_meastro_class, listAll);
                            recyclerMeastro.setAdapter(adapter);
                            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(mContext, ZhuanLanActivity.class);
                                    intent.putExtra("type","alreadyBuy");
                                    intent.putExtra("id",listAll.get(position).getZl_id()+"");
                                    startActivity(intent);
                                }
                            });
                            springView.onFinishFreshAndLoad();
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
        EventBus.getDefault().unregister(this);
    }

    private class MeastroClassAdapter extends BaseQuickAdapter<BuyZlBean.DataBean, BaseViewHolder> {

        public MeastroClassAdapter(@LayoutRes int layoutResId, @Nullable List<BuyZlBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BuyZlBean.DataBean item) {
            TextView classTitleTv = helper.getView(R.id.class_title_tv);
            TextView classDescTv = helper.getView(R.id.class_desc_tv);
            TextView classUpdataTv = helper.getView(R.id.class_updata_tv);
            TextView classZlmcTv = helper.getView(R.id.class_zlmc_tv);
            ImageView classFaceIv = helper.getView(R.id.class_face_iv);

            lastId = item.getId();

            Glide.with(mContext).load(item.getZl_img()).into(classFaceIv);
            classTitleTv.setText(item.getZl_name());
            classDescTv.setText(item.getZl_teacher_tags());
            classUpdataTv.setText(item.getZl_update_time());
            classZlmcTv.setText(item.getZl_update_name());

        }
    }
}
