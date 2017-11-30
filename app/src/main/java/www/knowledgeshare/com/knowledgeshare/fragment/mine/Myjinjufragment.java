package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Myjinjufragment extends BaseFragment {

    private RecyclerView recyclerView;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.fragment_class_jinju,null);
        recyclerView = view.findViewById(R.id.jin_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Myclassjinjuadapter myclassjinjuadapter=new Myclassjinjuadapter(getActivity());
        recyclerView.setAdapter(myclassjinjuadapter);
        return view;
    }

    @Override
    protected void initData() {

    }
}
