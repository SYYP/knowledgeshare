package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

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
    private Messagebean msssagebean;
    private LinearLayout msg_delete;
    private CheckBox checkall;
    private LinearLayout layout;
    private boolean isAllChecked;
    final static int ONE = 0, TWO = 1, THREE = 2;
    private Messageadapter messageadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initView();
        data();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        message_bianji = (TextView) findViewById(R.id.message_bianji);
        rela = (RelativeLayout) findViewById(R.id.rela);
        msg_delete = (LinearLayout) findViewById(R.id.msg_delete);
        checkall = (CheckBox) findViewById(R.id.check_all);
        layout = (LinearLayout) findViewById(R.id.liner);
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

    private void data() {
        requestNotification();
    }

    private void requestNotification() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<Messagebean>get(MyContants.nitification)
                .tag(this)
                .headers(headers)
                .execute(new DialogCallback<Messagebean>(this,Messagebean.class) {
                    @Override
                    public void onSuccess(Response<Messagebean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            list = response.body().getData();
                            messageadapter = new Messageadapter(R.layout.item_my_message, list);
                            message_recy.setLayoutManager(new LinearLayoutManager(MyMessageActivity.this));
                            messageadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    list.get(position).setaBoolean(!list.get(position).isaBoolean());
                                    if (isEditing){
                                        if (isAllItemChecked()) {
                                            //如果所有item都选中了，就让全选按钮变红
                                            checkall.setChecked(true);
                                        } else {
                                            checkall.setChecked(false);
                                        }
                                    }else {
                                        startActivity(new Intent(MyMessageActivity.this,MessageDetailActivity.class));
                                    }
                                    messageadapter.notifyDataSetChanged();
                                }
                            });
                            //设置分割线
                            message_recy.setAdapter(messageadapter);
                        }
                    }
                });
    }

    private boolean hasItemChecked() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isaBoolean()) {
                list.remove(i);
            }
        }
        return false;
    }

    private boolean isAllItemChecked() {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isaBoolean()) {
                return false;
            }
        }
        return true;
    }

    private void setAllItemChecked() {
        if (isAllChecked) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setaBoolean(false);
            }
            checkall.setChecked(false);
        } else {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setaBoolean(true);
            }
            checkall.setChecked(true);
        }
        isAllChecked = !isAllChecked;
        messageadapter.notifyDataSetChanged();
    }

    private void deleteCollect() {
        for (int i = list.size()-1; i >= 0; i--) {
            if (list.get(i).isaBoolean()){
                list.remove(i);
            }
        }
        messageadapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_bianji:
                if (list == null || list.size() == 0) {
                    return;
                } else {
                    if (isEditing) {
                        message_bianji.setText("编辑");
                        layout.setVisibility(View.GONE);
                    } else {
                        message_bianji.setText("完成");
                        layout.setVisibility(View.VISIBLE);
                    }
                    isEditing = !isEditing;
                    messageadapter.notifyDataSetChanged();
                }
                break;
            case R.id.msg_delete://删除
                if (list == null || list.size() == 0) {
                    return;
                } else {
                    deleteCollect();
                    message_bianji.setText("编辑");
                    layout.setVisibility(View.GONE);
                    isEditing = !isEditing;
                }

                break;
            //全选
            case R.id.check_all:
                if (list == null || list.size() == 0) {
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
}

