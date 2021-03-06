package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MyMessageActivity extends BaseActivity implements View.OnClickListener {
    boolean isEditing;
    private ImageView iv_back;
    private TextView message_bianji;
    private RelativeLayout rela;
    private RecyclerView message_recy;
    private List<Messagebean.DataBean> list = new ArrayList<>();
    private List<Messagebean.DataBean> listAll = new ArrayList<>();

    private Messagebean msssagebean;
    private LinearLayout msg_delete;
    private CheckBox checkall;
    private LinearLayout layout;
    private boolean isAllChecked;
    final static int ONE = 0, TWO = 1, THREE = 2;
    private Messageadapter messageadapter;
    StringBuilder sb = new StringBuilder();
    private SpringView springView;
    private String lastId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initView();
        initLoadMore();
        data();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        message_bianji = (TextView) findViewById(R.id.message_bianji);
        rela = (RelativeLayout) findViewById(R.id.rela);
        msg_delete = (LinearLayout) findViewById(R.id.msg_delete);
        checkall = (CheckBox) findViewById(R.id.check_all);
        layout = (LinearLayout) findViewById(R.id.liner);
        springView = (SpringView) findViewById(R.id.springview);
        checkall.setOnClickListener(this);
        msg_delete.setOnClickListener(this);
        message_bianji.setOnClickListener(this);
        layout.setOnClickListener(this);
        message_recy = (RecyclerView) findViewById(R.id.message_recy);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initLoadMore() {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listAll.clear();
                        requestNotification("");
                    }
                }, 800);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestNotification(lastId);
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }
        });
        springView.setHeader(new MyHeader(this));
        springView.setFooter(new MyFooter(this));
    }

    private void data() {
        requestNotification("");
    }

    private void requestNotification(String after) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("after", after);
        OkGo.<Messagebean>get(MyContants.nitification)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<Messagebean>(this, Messagebean.class) {
                    @Override
                    public void onSuccess(Response<Messagebean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204) {
                            list = response.body().getData();
                            if (list.size() > 0) {
                                listAll.addAll(list);
                            } else {
                                TUtils.showShort(MyMessageActivity.this, "没有更多数据了");
                            }
                            messageadapter = new Messageadapter(R.layout.item_my_message, listAll);
                            message_recy.setLayoutManager(new LinearLayoutManager(MyMessageActivity.this));
                            messageadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    listAll.get(position).setaBoolean(!listAll.get(position).isaBoolean());
                                    if (isEditing) {
                                        if (isAllItemChecked()) {
                                            //如果所有item都选中了，就让全选按钮变红
                                            checkall.setChecked(true);
                                        } else {
                                            checkall.setChecked(false);
                                        }
                                    } else {
                                        Intent intent = new Intent(MyMessageActivity.this, MessageDetailActivity.class);
                                        intent.putExtra("content", listAll.get(position).getContent());
                                        intent.putExtra("time", listAll.get(position).getCreated_at());
                                        startActivity(intent);
                                    }
                                    messageadapter.notifyDataSetChanged();
                                }
                            });
                            //设置分割线
                            message_recy.setAdapter(messageadapter);
                            springView.onFinishFreshAndLoad();
                        }
                    }
                });
    }

    private boolean hasItemChecked() {
        for (int i = 0; i < listAll.size(); i++) {
            if (listAll.get(i).isaBoolean()) {
                listAll.remove(i);
            }
        }
        return false;
    }

    private boolean isAllItemChecked() {
        for (int i = 0; i < listAll.size(); i++) {
            if (!listAll.get(i).isaBoolean()) {
                return false;
            }
        }
        return true;
    }

    private void setAllItemChecked() {
        if (isAllChecked) {
            for (int i = 0; i < listAll.size(); i++) {
                listAll.get(i).setaBoolean(false);
            }
            checkall.setChecked(false);
        } else {
            for (int i = 0; i < listAll.size(); i++) {
                listAll.get(i).setaBoolean(true);
            }
            checkall.setChecked(true);
        }
        isAllChecked = !isAllChecked;
        messageadapter.notifyDataSetChanged();
    }

    private void deleteCollect() {
        for (int i = listAll.size() - 1; i >= 0; i--) {
            if (listAll.get(i).isaBoolean()) {
                sb.append(listAll.get(i).getNotid() + ",");
            }
        }
        //当循环结束后截取最后一个逗号
        if (!TextUtils.isEmpty(sb)){
            String tag = sb.substring(0, sb.length() - 1);
            Logger.e(tag);
            requestDelNotification(tag);
        }
    }

    private void requestDelNotification(String ids) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("ids", ids);

        OkGo.<BaseBean>post(MyContants.delNotification)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<BaseBean>(MyMessageActivity.this, BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204) {
                            TUtils.showShort(MyMessageActivity.this, response.body().getMessage());
                            listAll.clear();
                            requestNotification("");
                        } else {
                            TUtils.showShort(MyMessageActivity.this, response.body().getMessage());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_bianji:
                if (listAll == null || listAll.size() == 0) {
                    return;
                } else {
                    if (isEditing) {
                        springView.setEnable(true);
                        message_bianji.setText("编辑");
                        layout.setVisibility(View.GONE);
                    } else {
                        message_bianji.setText("完成");
                        layout.setVisibility(View.VISIBLE);
                        springView.setEnable(false);
                    }
                    isEditing = !isEditing;
                    messageadapter.notifyDataSetChanged();
                }
                break;
            case R.id.msg_delete://删除
                if (listAll == null || listAll.size() == 0) {
                    return;
                } else {
                    deleteCollect();
                    message_bianji.setText("编辑");
                    layout.setVisibility(View.GONE);
                    isEditing = false;
                    messageadapter.notifyDataSetChanged();
                }
                break;
            //全选
            case R.id.check_all:
                if (listAll == null || listAll.size() == 0) {
                    return;
                } else {
                    setAllItemChecked();
                }
                break;
        }
    }

    class Messageadapter extends BaseQuickAdapter<Messagebean.DataBean, BaseViewHolder> {
        public Messageadapter(@LayoutRes int layoutResId, @Nullable List<Messagebean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Messagebean.DataBean item) {
            lastId = item.getNotid();
            if (isEditing) {
                helper.setVisible(R.id.msg_check, true);
            } else if (!isEditing) {
                helper.setVisible(R.id.msg_check, false);
            }
            helper.setIsRecyclable(false);
            helper.setText(R.id.message_count, item.getContent())
                    .setText(R.id.message_date, item.getCreated_at())
                    .setChecked(R.id.msg_check, item.isaBoolean());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(new EventBean("msgcountrefresh"));
    }
}

