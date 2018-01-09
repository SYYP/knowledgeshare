package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.bean.FavoriteBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MyClassfragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<FavoriteBean.DataBean> list = new ArrayList<>();

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_myclass, null);
        recyclerView = inflate.findViewById(R.id.class_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestFavorite();
        EventBus.getDefault().register(this);
        return inflate;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("refrashCollect")) {
           requestFavorite();
        }
    }

    private void requestFavorite() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        HttpParams params = new HttpParams();
        params.put("after","");
        OkGo.<FavoriteBean>get(MyContants.favorite)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<FavoriteBean>(mActivity,FavoriteBean.class) {
                    @Override
                    public void onSuccess(Response<FavoriteBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            list = response.body().getData();
                            Myclassadapter myclassadapter=new Myclassadapter(getActivity(),list);
                            myclassadapter.setOnItemClickListener(new Myclassadapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position, int type,int id) {
                                    Log.d("ccc",type+"");
                                    if(type==1){
                                        Intent intent = new Intent(getActivity(), SoftMusicDetailActivity.class);
                                        intent.putExtra("id",id+"");
                                        startActivity(intent);
                                    }
                                    else if(type==2){
                                        Intent intent = new Intent(getActivity(), ZhuanLanActivity.class);
                                        intent.putExtra("id",id+"");
                                        startActivity(intent);
                                    }
                                }
                            });
                            recyclerView.setAdapter(myclassadapter);
                        }
                    }
                });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
