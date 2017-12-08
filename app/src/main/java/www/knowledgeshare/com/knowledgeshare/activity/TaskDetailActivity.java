package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.TaskDetailBean;
import www.knowledgeshare.com.knowledgeshare.view.FullyLinearLayoutManager;

public class TaskDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.recycler_task)
    RecyclerView recyclerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentTv.setText("任务详情");
        titleContentRightTv.setText("晋级规则");
        titleBackIv.setOnClickListener(this);
        titleContentRightTv.setOnClickListener(this);
        initData();
    }

    private void initData() {
        recyclerTask.setLayoutManager(new LinearLayoutManager(this));
        recyclerTask.setNestedScrollingEnabled(false);
        List<TaskDetailBean> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            TaskDetailBean taskDetailBean = new TaskDetailBean();
            taskDetailBean.setMonth("11月份");
            taskDetailBean.setAllJifen("+10积分");
            list.add(taskDetailBean);
        }
        TaskDetailAdapter adapter = new TaskDetailAdapter(R.layout.item_task_detail,list);
        recyclerTask.addItemDecoration(new SpaceItemDecoration(20));
        recyclerTask.setAdapter(adapter);
        View header = LayoutInflater.from(this).inflate(R.layout.header_taskdetail,recyclerTask,false);
        adapter.addHeaderView(header);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                startActivity(new Intent(this,RulesPromotionActivity.class));
                break;
        }
    }

    private class TaskDetailAdapter extends BaseQuickAdapter<TaskDetailBean,BaseViewHolder>{

        public TaskDetailAdapter(@LayoutRes int layoutResId, @Nullable List<TaskDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        public int addHeaderView(View header) {
            return super.addHeaderView(header);
        }

        @Override
        protected void convert(BaseViewHolder helper, TaskDetailBean item) {
            TextView monthTv = helper.getView(R.id.month_tv);
            TextView jifenTv = helper.getView(R.id.jifen_tv);
            RecyclerView recyclerTaskDetail = helper.getView(R.id.recycler_task_detail);

            monthTv.setText(item.getMonth());
            jifenTv.setText(item.getAllJifen());

            List<TaskDetailBean> list1 = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                TaskDetailBean taskDetailBean = new TaskDetailBean();
                taskDetailBean.setName("购买课程");
                taskDetailBean.setDate("5日");
                taskDetailBean.setTime("11:00");
                taskDetailBean.setJifen("+5积分");
                list1.add(taskDetailBean);
            }

            recyclerTaskDetail.setLayoutManager(new FullyLinearLayoutManager(mContext));
            recyclerTaskDetail.setNestedScrollingEnabled(false);

            TaskDetailsAdapter detailsAdapter = new TaskDetailsAdapter(R.layout.item_task_details,list1);
            recyclerTaskDetail.setAdapter(detailsAdapter);
        }
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mSpace;
            }
        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }

    private class TaskDetailsAdapter extends BaseQuickAdapter<TaskDetailBean,BaseViewHolder>{

        public TaskDetailsAdapter(@LayoutRes int layoutResId, @Nullable List<TaskDetailBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TaskDetailBean item) {
            TextView nameTv = helper.getView(R.id.name_tv);
            TextView dateTv = helper.getView(R.id.date_tv);
            TextView timeTv = helper.getView(R.id.time_tv);
            TextView jifenTv = helper.getView(R.id.jifen_tv);

            nameTv.setText(item.getName());
            dateTv.setText(item.getDate());
            timeTv.setText(item.getTime());
            jifenTv.setText(item.getJifen());
        }
    }
}
