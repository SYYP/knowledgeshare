package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

public class LiuYanActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView iv_submit;
    private EditText edt_liuyan;
    private RecyclerView recycler_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liu_yan);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_submit = (TextView) findViewById(R.id.iv_submit);
        iv_submit.setOnClickListener(this);
        edt_liuyan = (EditText) findViewById(R.id.edt_liuyan);
        recycler_list = (RecyclerView) findViewById(R.id.recycler_list);
        recycler_list.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        recycler_list.setNestedScrollingEnabled(false);
        MyAdapter myAdapter=new MyAdapter(R.layout.item_myliuyan,list);
        recycler_list.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    private void submit() {
        String liuyan = edt_liuyan.getText().toString().trim();
        if (TextUtils.isEmpty(liuyan)) {
            Toast.makeText(this, "留言不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_submit:
                submit();
                break;
        }
    }
}
