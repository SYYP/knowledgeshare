package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

public class ZhuanLanDetail1Activity extends BaseActivity {

    private ImageView iv_back;
    private RecyclerView recycler;
    private boolean isCollected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuan_lan_detail1);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        MyAdapter myAdapter = new MyAdapter(R.layout.item_zhuanlan_xiaojie, list);
        recycler.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(ZhuanLanDetail1Activity.this, ZhuanLanDetail2Activity.class));
            }
        });
    }

    private class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final TextView tv_collect = helper.getView(R.id.tv_collect);
            tv_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isCollected) {
                        Drawable drawable = getResources().getDrawable(R.drawable.music_collect);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tv_collect.setCompoundDrawables(drawable, null, null, null);
                    } else {
                        Drawable drawable = getResources().getDrawable(R.drawable.music_collected);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tv_collect.setCompoundDrawables(drawable, null, null, null);
                    }
                    isCollected = !isCollected;

                }
            });
        }
    }
}
