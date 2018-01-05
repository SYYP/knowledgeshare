package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.AboutUsBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

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

        requestReg();
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

    private void requestReg() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));

        OkGo.<AboutUsBean>get(MyContants.registration)
                .tag(this)
                .headers(headers)
                .execute(new JsonCallback<AboutUsBean>(AboutUsBean.class) {
                    @Override
                    public void onSuccess(Response<AboutUsBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            AboutUsBean aboutUsBean = response.body();
                            String h5_url = aboutUsBean.getH5_url();
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
                            webView.loadUrl(h5_url);
                        }else {
                            Logger.e(response.body().getMessage());
                        }
                    }
                });
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
