package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.GoldBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Myjinjufragment extends BaseFragment {

    private RecyclerView recyclerView;
    List<GoldBean.DataBean> list = new ArrayList<>();

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.fragment_class_jinju,null);
        recyclerView = view.findViewById(R.id.jin_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestJinJu();

        return view;
    }

    private void requestJinJu() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        HttpParams params = new HttpParams();
        params.put("after","");
        OkGo.<GoldBean>get(MyContants.gold)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<GoldBean>(GoldBean.class) {
                    @Override
                    public void onSuccess(Response<GoldBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            list = response.body().getData();
                            Myclassjinjuadapter myclassjinjuadapter=new Myclassjinjuadapter(getActivity(),list);
                            recyclerView.setAdapter(myclassjinjuadapter);
                        }
                    }
                });
    }

    @Override
    protected void initData() {

    }
}
