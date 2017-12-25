package www.knowledgeshare.com.knowledgeshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.NoticeBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;
import www.knowledgeshare.com.knowledgeshare.login.adapter.Messageadapter;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MessageActivity extends BaseActivity {

    private ImageView iv_back;
    private RecyclerView Study_mesage;
    List<NoticeBean.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studymessage);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        Study_mesage = (RecyclerView) findViewById(R.id.Study_mesage);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        requestNotice();


    }

    private void requestNotice() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        OkGo.<NoticeBean>get(MyContants.notice)
                .tag(this)
                .headers(headers)
                .execute(new DialogCallback<NoticeBean>(this,NoticeBean.class) {
                    @Override
                    public void onSuccess(Response<NoticeBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            list = response.body().getData();
                            Study_mesage.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                            Messageadapter messageadapter=new Messageadapter(MessageActivity.this,list);
                            Study_mesage.setAdapter(messageadapter);
                            messageadapter.setOnItemClickListener(new Messageadapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent=new Intent(MessageActivity.this, ZhuanLanActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }else {
                            TUtils.showShort(MessageActivity.this,response.body().getMessage());
                        }
                    }
                });
    }


}
