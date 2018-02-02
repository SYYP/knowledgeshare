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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.TaskListBean;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.LearnTimeBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
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
    private TaskDetailAdapter adapter;
    private String jifen;

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
        jifen = getIntent().getStringExtra("jifen");
        recyclerTask.setLayoutManager(new LinearLayoutManager(this));
        recyclerTask.setNestedScrollingEnabled(false);
        requestTask();
        /*List<TaskDetailBean> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            TaskDetailBean taskDetailBean = new TaskDetailBean();
            taskDetailBean.setMonth("11月份");
            taskDetailBean.setAllJifen("+10积分");
            list.add(taskDetailBean);
        }*/


    }

    private void requestTask() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<String>post(MyContants.taskList)
                .tag(this)
                .headers(headers)
                .params("after","")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            String body = response.body();
                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                String user_integral = jsonObject.getString("user_integral");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    Logger.e(jsonObject1.toString());
                                    Iterator keys = jsonObject1.keys();
                                    List<LearnTimeBean> list = new ArrayList<LearnTimeBean>();
                                    while (keys.hasNext()){
                                        String key = String.valueOf(keys.next());
                                        JSONObject data = jsonObject1.getJSONObject(key);
                                        Logger.e(data.toString());
                                        int count = data.getInt("count");
                                        Logger.e(count+"");
                                        JSONArray child = data.getJSONArray("child");
                                        Logger.e(child.toString());
                                        LearnTimeBean bean = new LearnTimeBean(key,count,child.toString());
                                        list.add(bean);
                                    }
                                    adapter = new TaskDetailAdapter(R.layout.item_task_detail,list);
                                    View header = LayoutInflater.from(TaskDetailActivity.this).inflate(R.layout.header_taskdetail,recyclerTask,false);
                                    TextView totalJifen = header.findViewById(R.id.total_jf_tv);
                                    totalJifen.setText(user_integral);
                                    recyclerTask.addItemDecoration(new SpaceItemDecoration(10));
                                    adapter.addHeaderView(header);
                                    recyclerTask.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
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

    private class TaskDetailAdapter extends BaseQuickAdapter<LearnTimeBean,BaseViewHolder>{

        public TaskDetailAdapter(@LayoutRes int layoutResId, @Nullable List<LearnTimeBean> data) {
            super(layoutResId, data);
        }

        @Override
        public int addHeaderView(View header) {
            return super.addHeaderView(header);
        }

        @Override
        protected void convert(BaseViewHolder helper, LearnTimeBean item) {
            TextView monthTv = helper.getView(R.id.month_tv);
            TextView jifenTv = helper.getView(R.id.jifen_tv);
            RecyclerView recyclerTaskDetail = helper.getView(R.id.recycler_task_detail);
            String date = item.getDate();
            String substring = date.substring(date.length() - 2);
            monthTv.setText(substring+"月份");
            jifenTv.setText("+" + item.getCount() + "积分");

            List<TaskListBean> beanList = JSON.parseArray(item.getContent(), TaskListBean.class);
            recyclerTaskDetail.setLayoutManager(new FullyLinearLayoutManager(mContext));
            recyclerTaskDetail.setNestedScrollingEnabled(false);
            TaskDetailsAdapter detailsAdapter = new TaskDetailsAdapter(R.layout.item_task_details,beanList);
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

    private class TaskDetailsAdapter extends BaseQuickAdapter<TaskListBean,BaseViewHolder>{

        public TaskDetailsAdapter(@LayoutRes int layoutResId, @Nullable List<TaskListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TaskListBean item) {
            TextView nameTv = helper.getView(R.id.name_tv);
            TextView dateTv = helper.getView(R.id.date_tv);
            TextView timeTv = helper.getView(R.id.time_tv);
            TextView jifenTv = helper.getView(R.id.jifen_tv);

            nameTv.setText(item.getName());
            dateTv.setText(item.getDay());
            timeTv.setText(item.getTime());
            jifenTv.setText("+"+item.getCount()+"积分");
        }
    }
}
