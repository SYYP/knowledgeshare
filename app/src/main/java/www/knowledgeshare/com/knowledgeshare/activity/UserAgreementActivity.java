package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

/**
 * 用户协议
 * @author Administrator
 */
public class UserAgreementActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv)
    ImageView titleBackIv;
    @BindView(R.id.title_content_tv)
    TextView titleContentTv;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleContentTv.setText("用户协议");
        titleBackIv.setOnClickListener(this);

        String weburl = "https://www.baidu.com/";

        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setDefaultTextEncodingName("utf-8");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(weburl);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            default:
                break;
        }
    }
}
