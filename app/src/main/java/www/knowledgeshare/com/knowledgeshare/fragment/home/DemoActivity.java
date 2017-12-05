package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

public class DemoActivity extends AppCompatActivity {

    private ImageView iv_delete;
    private CircleImageView iv_bo_head;
    private TextView tv_title;
    private TextView tv_subtitle;
    private ImageView iv_arrow_top;
    private ImageView iv_mulu;
    private RelativeLayout rl_bofang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smallmusic);
        initView();
    }


    private void initView() {
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        iv_bo_head = (CircleImageView) findViewById(R.id.iv_bo_head);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        iv_arrow_top = (ImageView) findViewById(R.id.iv_arrow_top);
        iv_mulu = (ImageView) findViewById(R.id.iv_mulu);
        rl_bofang = (RelativeLayout) findViewById(R.id.rl_bofang);
    }
}
