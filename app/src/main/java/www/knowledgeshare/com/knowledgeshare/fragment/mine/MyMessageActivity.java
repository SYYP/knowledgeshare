package www.knowledgeshare.com.knowledgeshare.fragment.mine;

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

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

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
    private List<Messagebean> list = new ArrayList<>();
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

    /*
          添加数据
        */
    private void data() {
        for (int i = 0; i < 6; i++) {
            msssagebean = new Messagebean();
            msssagebean.setCount("系统消息： 恭喜行走的肉夹馍获得金牌用户勋章，再接再厉成为砖石用户");
            msssagebean.setTime("2017-10-10  10:10");
            list.add(msssagebean);
            messageadapter = new Messageadapter(R.layout.item_my_message, list);
            message_recy.setLayoutManager(new LinearLayoutManager(this));

            messageadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    list.get(position).setaBoolean(!list.get(position).isaBoolean());
                    if (isAllItemChecked()) {
                        //如果所有item都选中了，就让全选按钮变红
                        checkall.setChecked(true);
                    } else {
                        checkall.setChecked(false);
                    }
                    messageadapter.notifyDataSetChanged();
                }
            });
            //设置分割线
            message_recy.setAdapter(messageadapter);
            //    message_recy.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));

        }


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
                Log.d("ddd", list.get(i).isaBoolean() + "");

            }
            checkall.setChecked(false);
        } else {
            for (int i = 0; i < list.size(); i++) {

                list.get(i).setaBoolean(true);
                Log.d("ddd", list.get(i).isaBoolean() + "");
            }
            checkall.setChecked(true);
        }
        isAllChecked = !isAllChecked;
        Log.d("tag", list.size() + "");
        messageadapter.notifyDataSetChanged();
    }

    private void deleteCollect() {
        Log.d("bbb", list.size() + "");

        for (int i = 0; i < list.size(); i++) {
            Log.d("ccc", list.get(i).isaBoolean() + "");
            if (list.get(i).isaBoolean()) {
                Log.d("eee", list.get(i).isaBoolean() + "");
            //    list.remove(i);
            }


        }
        messageadapter.notifyDataSetChanged();
        Log.d("aaa", list.size() + "");
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

    class Messageadapter extends BaseQuickAdapter<Messagebean, BaseViewHolder> {
        public Messageadapter(@LayoutRes int layoutResId, @Nullable List<Messagebean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Messagebean item) {
            if (isEditing) {
                helper.setVisible(R.id.msg_check, true);
            } else if (!isEditing) {
                helper.setVisible(R.id.msg_check, false);
            }
            helper.setIsRecyclable(false);
            helper.setText(R.id.message_count, item.getCount())
                    .setText(R.id.message_date, item.getTime())
                    .setChecked(R.id.msg_check, item.isaBoolean());
        }
    }
}

