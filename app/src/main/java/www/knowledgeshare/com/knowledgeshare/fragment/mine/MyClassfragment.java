package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;

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
       myclassadapter.setOnItemClickListener(new Myclassadapter.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position, int type) {
               Log.d("ccc",type+"");
                   if(type==1){
                       Intent intent=new Intent(getActivity(), SoftMusicDetailActivity.class);
                       startActivity(intent);
                   }
                 else if(type==2){
                       Intent intent=new Intent(getActivity(), ZhuanLanActivity.class);
                       startActivity(intent);
               }
           }
       });
        recyclerView.setAdapter(myclassadapter);
        return inflate;
    }

    @Override
    protected void initData() {

    }
}
