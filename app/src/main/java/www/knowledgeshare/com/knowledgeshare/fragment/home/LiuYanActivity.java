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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.LiuYanListFree;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

public class LiuYanActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView iv_submit;
    private EditText edt_liuyan;
    private RecyclerView recycler_list;
    private List<LiuYanListFree.DataEntity> mData;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liu_yan);
        setISshow(false);
        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_submit = (TextView) findViewById(R.id.iv_submit);
        iv_submit.setOnClickListener(this);
        edt_liuyan = (EditText) findViewById(R.id.edt_liuyan);
        recycler_list = (RecyclerView) findViewById(R.id.recycler_list);
        recycler_list.setLayoutManager(new LinearLayoutManager(this));
        recycler_list.setNestedScrollingEnabled(false);
    }

    private void initData() {
        mType = getIntent().getStringExtra("type");
        String url = "";
        if (mType.equals("free-root")) {
            HttpHeaders headers = new HttpHeaders();
            headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
            url = MyContants.LXKURL + "free-comment/comment-list";
            OkGo.<LiuYanListFree>get(url)//get请求
                    .tag(this)
                    .headers(headers)
                    .execute(new JsonCallback<LiuYanListFree>(LiuYanListFree.class) {
                        @Override
                        public void onSuccess(Response<LiuYanListFree> response) {
                            int code = response.code();
                            LiuYanListFree liuYanListFree = response.body();
                            if (response.code() >= 200 && response.code() <= 204) {
                                Logger.e(code + "");
                                mData = liuYanListFree.getData();
                                MyAdapter myAdapter = new MyAdapter(R.layout.item_myliuyan, mData);
                                recycler_list.setAdapter(myAdapter);
                            } else {
                            }
                        }
                    });
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
            HttpParams params = new HttpParams();
            params.put("id", getIntent().getStringExtra("xiaoke_id"));
            if (mType.equals("free-wengao")) {
                url = MyContants.LXKURL + "free-comment/draft-comment";
            } else if (mType.equals("everydaycomment-wengao")) {
                url = MyContants.LXKURL + "daily/comment-list";
            } else if (mType.equals("softmusicdetail-root")) {
                url = MyContants.LXKURL + "xk/xk-commnet-list";
            } else if (mType.equals("softmusicdetail-wengao")) {
                url = MyContants.LXKURL + "xk/draft-comment-list";
            } else if (mType.equals("zhuanlandetail")) {
                url = MyContants.LXKURL + "zl/zl-commnet-list";
            }
            OkGo.<LiuYanListFree>post(url)//post请求
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new JsonCallback<LiuYanListFree>(LiuYanListFree.class) {
                        @Override
                        public void onSuccess(Response<LiuYanListFree> response) {
                            int code = response.code();
                            LiuYanListFree liuYanListFree = response.body();
                            if (response.code() >= 200 && response.code() <= 204) {
                                Logger.e(code + "");
                                mData = liuYanListFree.getData();
                                MyAdapter myAdapter = new MyAdapter(R.layout.item_myliuyan, mData);
                                recycler_list.setAdapter(myAdapter);
                            } else {

                            }
                        }
                    });
        }
    }

    private class MyAdapter extends BaseQuickAdapter<LiuYanListFree.DataEntity, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<LiuYanListFree.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LiuYanListFree.DataEntity item) {
            helper.setText(R.id.tv_time, item.getCreated_at())
                    .setText(R.id.tv_content, item.getContent());
            if (item.getComment_reply() != null && !TextUtils.isEmpty(item.getComment_reply().getContent())) {
                helper.setVisible(R.id.ll_author, true);
                helper.setText(R.id.tv_author_content, item.getComment_reply().getContent());
            } else {
                helper.setVisible(R.id.ll_author, false);
            }
        }
    }

    private void submit() {
        String liuyan = edt_liuyan.getText().toString().trim();
        if (TextUtils.isEmpty(liuyan)) {
            Toast.makeText(this, "留言不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("content", liuyan);
        params.put("teacher_id", getIntent().getStringExtra("teacher_id"));
        String url = "";
        if (mType.equals("free-root")) {
            url = MyContants.LXKURL + "free-comment/submit";
        } else if (mType.equals("free-wengao")) {
            params.put("id", getIntent().getStringExtra("xiaoke_id"));
            url = MyContants.LXKURL + "free-comment/draft";
        } else if (mType.equals("everydaycomment-wengao")) {
            params.put("id", getIntent().getStringExtra("xiaoke_id"));
            url = MyContants.LXKURL + "daily/submit-comment";
        } else if (mType.equals("softmusicdetail-root")) {
            params.put("id", getIntent().getStringExtra("xiaoke_id"));
            url = MyContants.LXKURL + "xk/submit-comment";
        } else if (mType.equals("softmusicdetail-wengao")) {
            params.put("id", getIntent().getStringExtra("xiaoke_id"));
            url = MyContants.LXKURL + "xk/draft-submit-comment";
        } else if (mType.equals("zhuanlandetail")) {
            params.put("id", getIntent().getStringExtra("xiaoke_id"));
            url = MyContants.LXKURL + "zl/zl-sumbit-comment";
        }
        OkGo.<DianZanbean>post(url)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<DianZanbean>(LiuYanActivity.this, DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(LiuYanActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void onError(Response<DianZanbean> response) {
                                 super.onError(response);
                             }
                         }
                );
        //        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_submit:
                submit();
                break;
        }
    }
}
