package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

/**
 * @author Administrator
 */
public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.content_tv)
    TextView contentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("消息");
        String content = getIntent().getStringExtra("content");
        String time = getIntent().getStringExtra("time");
        timeTv.setText(time);
        contentTv.setText(content);
    }

    @OnClick(R.id.title_back_iv)
    public void back() {
        finish();
    }
}
