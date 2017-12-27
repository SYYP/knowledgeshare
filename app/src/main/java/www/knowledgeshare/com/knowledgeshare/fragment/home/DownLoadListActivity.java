package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtils;

public class DownLoadListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RecyclerView recycler_list;
    private ImageView iv_quanxuan;
    private TextView tv_quanxuan;
    private TextView tv_download;
    private List<DownLoadListBean> mList;
    private MyAdapter mMyAdapter;
    private boolean isAllChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_list);
        setISshow(false);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        recycler_list = (RecyclerView) findViewById(R.id.recycler_list);
        iv_quanxuan = (ImageView) findViewById(R.id.iv_quanxuan);
        iv_quanxuan.setOnClickListener(this);
        tv_quanxuan = (TextView) findViewById(R.id.tv_quanxuan);
        tv_quanxuan.setOnClickListener(this);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        recycler_list.setLayoutManager(new LinearLayoutManager(this));
        mList = DownUtils.search();
        if (mList != null && mList.size() > 0) {
            mMyAdapter = new MyAdapter(R.layout.item_download, mList);
            recycler_list.setAdapter(mMyAdapter);
            mMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    //                Toast.makeText(DownLoadListActivity.this, "触发", Toast.LENGTH_SHORT).show();
                    mList.get(position).setChecked(!mList.get(position).isChecked());
                    //                mMyAdapter.setNewData(mList);
                    mMyAdapter.notifyDataSetChanged();
                    if (!isAllChecked()) {
                        iv_quanxuan.setImageResource(R.drawable.noquanxuan);
                    } else {
                        iv_quanxuan.setImageResource(R.drawable.quanxuan_red);
                    }
                }
            });
        }
    }

    private void quanxuan() {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setChecked(true);
        }
        //        mMyAdapter.setNewData(mList);
        mMyAdapter.notifyDataSetChanged();
        iv_quanxuan.setImageResource(R.drawable.quanxuan_red);
    }

    private boolean isAllChecked() {
        for (int i = 0; i < mList.size(); i++) {
            boolean checked = mList.get(i).isChecked();
            if (!checked) {
                return false;
            }
        }
        return true;
    }

    private void noquanxuan() {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setChecked(false);
        }
        //        mMyAdapter.setNewData(mList);
        mMyAdapter.notifyDataSetChanged();
        iv_quanxuan.setImageResource(R.drawable.noquanxuan);
    }

    private class MyAdapter extends BaseQuickAdapter<DownLoadListBean, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<DownLoadListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, DownLoadListBean item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_ischeck);
            if (item.isChecked()) {
                //                Glide.with(mContext).load(R.drawable.quanxuan_red).into(imageView);//glide加载图片会有闪烁
                imageView.setImageResource(R.drawable.quanxuan_red);
            } else {
                imageView.setImageResource(R.drawable.noquanxuan);
            }
            helper.setText(R.id.tv_name,item.getName())
                    .setText(R.id.tv_date,item.getDate())
                    .setText(R.id.tv_time,item.getTime())
                    .setText(R.id.tv_order,helper.getAdapterPosition()+1+"");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_quanxuan:
            case R.id.tv_quanxuan:
                if (isAllChecked) {
                    noquanxuan();
                } else {
                    quanxuan();
                }
                isAllChecked = !isAllChecked;
                break;
            case R.id.tv_download:
                break;
        }
    }
}
