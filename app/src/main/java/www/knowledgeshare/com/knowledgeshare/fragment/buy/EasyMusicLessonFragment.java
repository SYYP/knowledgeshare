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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

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
import www.knowledgeshare.com.knowledgeshare.bean.BuyXkBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangjinduBean;
import www.knowledgeshare.com.knowledgeshare.db.JinduUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

/**
 * Created by Administrator on 2017/11/23.
 * 轻松音乐课
 */

public class EasyMusicLessonFragment extends BaseFragment {
    @BindView(R.id.recycler_easy)
    RecyclerView recyclerEasy;
    @BindView(R.id.springview)
    SpringView springView;
    Unbinder unbinder;
    private List<BuyXkBean.DataBean> list = new ArrayList<>();
    private List<BuyXkBean.DataBean> listAll = new ArrayList<>();
    private String lastId;
    private List<BofangjinduBean> search = new ArrayList<>();

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_easy_lesson, null);
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listAll.clear();
                        requestBuyXk("");
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestBuyXk(lastId);
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
        recyclerEasy.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerEasy.setNestedScrollingEnabled(false);
        requestBuyXk("");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("jindu")) {
            listAll.clear();
            requestBuyXk("");
        }
    }

    private void requestBuyXk(String after) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        HttpParams params = new HttpParams();
        params.put("after",after);

        OkGo.<BuyXkBean>get(MyContants.buyXk)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<BuyXkBean>(mActivity, BuyXkBean.class) {

                    private double jindu;

                    @Override
                    public void onSuccess(Response<BuyXkBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            list = response.body().getData();
                            if (list.size() > 0){
                                listAll.addAll(list);
                            }else {
                                TUtils.showShort(mContext,"没有更多数据了");
                            }

                            search = JinduUtils.search();
                            for (int i = 0; i < search.size(); i++) {
                                for (int j = 0; j < listAll.size(); j++) {
                                    if (search.get(i).getMyId().equals(listAll.get(j).getId())){
                                        BuyXkBean.DataBean dataBean = listAll.get(j);
                                        jindu = search.get(j).getJindu();
                                        dataBean.setJindu(jindu);
                                        listAll.set(j,dataBean);
                                    }
                                }
                            }
                            EasyMusicLessonAdapter adapter = new EasyMusicLessonAdapter(R.layout.item_easy_lesson, listAll);
                            recyclerEasy.setAdapter(adapter);
                            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(mContext, SoftMusicDetailActivity.class);
                                    intent.putExtra("type","alreadyBuy");
                                    Logger.e(position+"");
                                    intent.putExtra("id",listAll.get(position).getXk_id()+"");
                                    startActivity(intent);
                                }
                            });

                            springView.onFinishFreshAndLoad();

                        }else {
                            TUtils.showShort(mContext,response.body().getMessage());
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

    private class EasyMusicLessonAdapter extends BaseQuickAdapter<BuyXkBean.DataBean, BaseViewHolder> {

        public EasyMusicLessonAdapter(@LayoutRes int layoutResId, @Nullable List<BuyXkBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BuyXkBean.DataBean item) {
            TextView easyTitleTv = helper.getView(R.id.easy_title_tv);
            TextView easyNameTv = helper.getView(R.id.easy_name_tv);
            TextView easyDescTv = helper.getView(R.id.easy_desc_tv);
            TextView easyYbfTv = helper.getView(R.id.easy_ybf_tv);
            TextView easyGmrsTv = helper.getView(R.id.easy_gmrs_tv);
            ImageView easyFace = helper.getView(R.id.easy_face_iv);


            lastId = item.getId();
            /*if (search.size() > 0){
                for (int i = 0; i < search.size(); i++) {
                    if (search.get(i).getMyId().equals(lastId)){
                        double jindu = search.get(i).getJindu();
                        Logger.e(jindu+"");
                        easyYbfTv.setText("以播放："+ jindu*100+"%");
                    }
                }
            }

           *//* if (search.size() >0 ){
                double jindu = search.get(helper.getAdapterPosition()).getJindu();
                Logger.e(jindu+"");
                if (TextUtils.equals(lastId+"",search.get(helper.getAdapterPosition()).getMyId())){
                    easyYbfTv.setText("以播放："+ jindu*100+"%");
                }
            }*//*else {
                easyYbfTv.setText("以播放：0%");
            }*/
            if (item.getJindu() > 0){
                easyYbfTv.setText("已播放："+ item.getJindu()*100+"%");
            }else {
                easyYbfTv.setText("已播放：0%");
            }


            Glide.with(mContext).load(item.getXk_image()).into(easyFace);
            easyTitleTv.setText(item.getXk_name());
            easyNameTv.setText(item.getT_name());
            easyDescTv.setText(item.getXk_teacher_tags());
            easyGmrsTv.setText("购买人数："+item.getBuy_count()+"人");

        }

    }

}
