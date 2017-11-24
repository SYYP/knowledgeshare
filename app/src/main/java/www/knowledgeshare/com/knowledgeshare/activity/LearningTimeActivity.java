package www.knowledgeshare.com.knowledgeshare.activity;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.LearnContentBean;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.LearnTimeBean;

public class LearningTimeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.learn_time_tv) TextView learnTimeTv;
    @BindView(R.id.learn_date_tv) TextView learnDateTv;
    @BindView(R.id.recycler_learntime) RecyclerView recyclerLearntime;
    private List<LearnTimeBean> list;
    private List<LearnContentBean> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_time);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("学习时间");
        titleBackIv.setOnClickListener(this);
        recyclerLearntime.setLayoutManager(new LinearLayoutManager(this));
        recyclerLearntime.setNestedScrollingEnabled(false);

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LearnTimeBean learnTimeBean = new LearnTimeBean();
            learnTimeBean.setDate("2017-11-11");
            LearnTimeBean.ContentBean contentBean = new LearnTimeBean.ContentBean();
            contentBean.setTime("10:00");
            contentBean.setContent("今天学习了钢铁是怎样练成的"+i);
            learnTimeBean.setContentBeen(contentBean);
            list.add(learnTimeBean);
        }
        list1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LearnContentBean learnContentBean = new LearnContentBean();
            learnContentBean.setTime("10:00");
            learnContentBean.setContent("今天学习了钢铁是怎样练成的"+i);
            list1.add(learnContentBean);
        }

        LearningTimeAdapter adapter = new LearningTimeAdapter(R.layout.item_learn_time, list);
        recyclerLearntime.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
        }
    }

    private class LearningTimeAdapter extends BaseQuickAdapter<LearnTimeBean,BaseViewHolder>{

        public LearningTimeAdapter(@LayoutRes int layoutResId, @Nullable List<LearnTimeBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LearnTimeBean item) {
            TextView itemTimeTv = helper.getView(R.id.item_time_tv);
            RecyclerView recyclerView = helper.getView(R.id.recycler_list);

            itemTimeTv.setText(item.getDate());

            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setNestedScrollingEnabled(false);

            LearningItemAdapter adapter = new LearningItemAdapter(R.layout.item_learn_content,list1);
            recyclerView.setAdapter(adapter);
        }
    }

    private class LearningItemAdapter extends BaseQuickAdapter<LearnContentBean,BaseViewHolder>{

        public LearningItemAdapter(@LayoutRes int layoutResId, @Nullable List<LearnContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LearnContentBean item) {
            TextView itemTimeTv = helper.getView(R.id.item_time_tv);
            TextView itemContentTv = helper.getView(R.id.item_content_tv);

            itemTimeTv.setText(item.getTime());
            itemContentTv.setText(item.getContent());
        }
    }
}
