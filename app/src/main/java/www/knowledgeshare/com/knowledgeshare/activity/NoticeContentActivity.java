package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

public class NoticeContentActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    private int id;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_content);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleBackIv.setOnClickListener(this);
        titleContentTv.setText("笔记详情");
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentRightTv.setText("编辑");
        titleContentRightTv.setOnClickListener(this);
        content = getIntent().getStringExtra("content");
        contentTv.setText(content);
        id = getIntent().getIntExtra("id", -1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                Intent intent = new Intent(this,EditNoticeContentActivity.class);
                intent.putExtra("content",content);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
        }
    }
}
