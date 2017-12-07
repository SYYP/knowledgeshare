package www.knowledgeshare.com.knowledgeshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;
import www.knowledgeshare.com.knowledgeshare.login.adapter.Messageadapter;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MessageActivity extends BaseActivity {

    private ImageView iv_back;
    private RecyclerView Study_mesage;

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

          Study_mesage.setLayoutManager(new LinearLayoutManager(this));
        Messageadapter messageadapter=new Messageadapter(this);
         messageadapter.setOnItemClickListener(new Messageadapter.OnItemClickListener() {
             @Override
             public void onItemClick(View view, int position) {
                 Intent intent=new Intent(MessageActivity.this, ZhuanLanActivity.class);
                    startActivity(intent);
             }
         });
        Study_mesage.setAdapter(messageadapter);
    }


}
