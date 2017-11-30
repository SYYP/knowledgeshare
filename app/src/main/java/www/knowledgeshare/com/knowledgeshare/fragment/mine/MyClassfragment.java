package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MyClassfragment extends BaseFragment {

    private RecyclerView recyclerView;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_myclass, null);
        recyclerView = inflate.findViewById(R.id.class_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Myclassadapter myclassadapter=new Myclassadapter(getActivity());
        recyclerView.setAdapter(myclassadapter);
        return inflate;
    }

    @Override
    protected void initData() {

    }
}
