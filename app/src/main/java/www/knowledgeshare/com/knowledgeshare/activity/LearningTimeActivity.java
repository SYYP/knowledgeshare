package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
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
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.LearnContentBean;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.LearnTimeBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.FullyLinearLayoutManager;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

import static www.knowledgeshare.com.knowledgeshare.R.id.learn_date_tv;
import static www.knowledgeshare.com.knowledgeshare.R.id.learn_time_tv;

public class LearningTimeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(learn_time_tv)
    TextView learnTimeTv;
    @BindView(learn_date_tv)
    TextView learnDateTv;
    @BindView(R.id.recycler_learntime)
    RecyclerView recyclerLearntime;
    @BindView(R.id.springview)
    SpringView springview;
    private LearningTimeAdapter mAdapter;
    private int after=2;
    private boolean isLoadMore;
    private List<LearnTimeBean> mDatas=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_time);
        ButterKnife.bind(this);
        initView();
        recyclerLearntime.requestDisallowInterceptTouchEvent(true);
        initData();
        initListener();
    }

    private void initData() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        if (isLoadMore) {
            params.put("after", after+"");
        }
        OkGo.<String>post(MyContants.LXKURL + "user/studys")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Logger.e(body);
                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            String count_time = jsonObject.getString("count_time");
                            learnTimeTv.setText(count_time);
                            String user_days = jsonObject.getString("user_days");
                            learnDateTv.setText(user_days);
                            JSONObject data = jsonObject.getJSONObject("study");
                            Iterator keys = data.keys();
                            List<LearnTimeBean> beanList =  new ArrayList<LearnTimeBean>();
                            while (keys.hasNext()) {
                                String key = String.valueOf(keys.next());
                                Logger.e(key);
                                JSONArray timeData = data.getJSONArray(key);
                                LearnTimeBean bean = new LearnTimeBean(key, timeData.toString());
                                beanList.add(bean);
                                Logger.e(timeData.toString());
                            }
                            if (isLoadMore) {
                                if (beanList == null || beanList.size() == 0) {
                                    Toast.makeText(LearningTimeActivity.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                mDatas.addAll(beanList);
                                after++;
                            } else {
                                mDatas = beanList;
                            }
                            mAdapter = new LearningTimeAdapter(R.layout.item_learn_time, mDatas);
                            recyclerLearntime.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initListener() {
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        springview.onFinishFreshAndLoad();
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {
                isLoadMore = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        springview.onFinishFreshAndLoad();
                    }
                }, 2000);
            }
        });
        springview.setHeader(new MyHeader(this));
        springview.setFooter(new MyFooter(this));
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("学习时间");
        titleBackIv.setOnClickListener(this);
        recyclerLearntime.setLayoutManager(new LinearLayoutManager(this));
        recyclerLearntime.setNestedScrollingEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_iv:
                finish();
                break;
        }
    }

    private class LearningTimeAdapter extends BaseQuickAdapter<LearnTimeBean, BaseViewHolder> {

        public LearningTimeAdapter(@LayoutRes int layoutResId, @Nullable List<LearnTimeBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LearnTimeBean item) {
            TextView itemTimeTv = helper.getView(R.id.item_time_tv);
            itemTimeTv.setText(item.getDate());
            RecyclerView recyclerView = helper.getView(R.id.recycler_list);
            recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext));
            recyclerView.setNestedScrollingEnabled(false);
            List<LearnContentBean> beanList = JSON.parseArray(item.getContent(), LearnContentBean.class);
            LearningItemAdapter adapter = new LearningItemAdapter(R.layout.item_learn_content, beanList);
            recyclerView.setAdapter(adapter);
        }
    }

    private class LearningItemAdapter extends BaseQuickAdapter<LearnContentBean, BaseViewHolder> {

        public LearningItemAdapter(@LayoutRes int layoutResId, @Nullable List<LearnContentBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LearnContentBean item) {
            TextView itemTimeTv = helper.getView(R.id.item_time_tv);
            TextView itemContentTv = helper.getView(R.id.item_content_tv);
            itemTimeTv.setText(item.getHour());
            itemContentTv.setText(item.getName());
        }
    }
}
