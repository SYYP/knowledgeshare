package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * 意见反馈
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.title_content_right_tv)
    TextView titleContentRightTv;
    @BindView(R.id.feed_et)
    EditText feedEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentRightTv.setVisibility(View.VISIBLE);
        titleContentTv.setText("意见反馈");
        titleContentRightTv.setText("提交");
        titleBackIv.setOnClickListener(this);
        titleContentRightTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.title_content_right_tv:
                if (!TextUtils.isEmpty(feedEt.getText().toString())){
                    requestFeedBack();
                }else {
                    TUtils.showShort(this,"请填写内容！");
                }
                break;
        }
    }

    private void requestFeedBack() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("content",feedEt.getText().toString());

        OkGo.<BaseBean>post(MyContants.feedBack)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<BaseBean>(this,BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            TUtils.showShort(FeedBackActivity.this,response.body().getMessage());
                            finish();
                        }
                    }
                });
    }
}
