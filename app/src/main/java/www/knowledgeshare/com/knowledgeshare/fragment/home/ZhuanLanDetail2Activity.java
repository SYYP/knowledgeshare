package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;


public class ZhuanLanDetail2Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private CircleImageView iv_teacher_head;
    private TextView tv_teacher_name;
    private ImageView iv_collect;
    private TextView tv_time1;
    private ImageView iv_bofang;
    private TextView tv_title2;
    private TextView tv_time2;
    private ImageView iv_download;
    private TextView tv_writeliuyan;
    private RecyclerView recycler_liuyan;
    private TextView tv_buy;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private boolean isCollected;
    private boolean isDianzan;
    private WebView webview;
    private NestedScrollView nestView;
    private boolean isBofang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuan_lan_detail2);
        initView();
        initDialog();
        initData();
        initListener();
    }

    private void initListener() {
        nestView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 0) {
                    setPopHide();
                } else if (scrollY - oldScrollY < 0) {
                    SlidePopShow();
                }
            }
        });
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        LiuYanAdapter liuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan2, list);
        recycler_liuyan.setAdapter(liuYanAdapter);
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_teacher_head = (CircleImageView) findViewById(R.id.iv_teacher_head);
        tv_teacher_name = (TextView) findViewById(R.id.tv_teacher_name);
        iv_collect = (ImageView) findViewById(R.id.iv_collect);
        iv_collect.setOnClickListener(this);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        iv_bofang = (ImageView) findViewById(R.id.iv_bofang);
        iv_bofang.setOnClickListener(this);
        tv_title2 = (TextView) findViewById(R.id.tv_title2);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        iv_download.setOnClickListener(this);
        tv_writeliuyan = (TextView) findViewById(R.id.tv_writeliuyan);
        tv_writeliuyan.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(this);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        nestView= (NestedScrollView) findViewById(R.id.nestView);
        webview= (WebView) findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webview.loadUrl("http://www.baidu.com");
    }

    private class LiuYanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LiuYanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final ImageView iv_dianzan = helper.getView(R.id.iv_dianzan);
            helper.getView(R.id.ll_dianzan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isDianzan) {
                        iv_dianzan.setImageResource(R.drawable.free_yizan);
                    }else {
                        iv_dianzan.setImageResource(R.drawable.free_dianzan);
                    }
                    isDianzan=!isDianzan;
                }
            });
        }
    }

    private void showBuyDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_dingyue)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.BOTTOM)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showPayStyleDialog();
            }
        });
    }

    private void showPayStyleDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_buy)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.BOTTOM)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.rl_yuezhifu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showChongzhiDialog();
            }
        });
        mDialog.getView(R.id.rl_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Toast.makeText(ZhuanLanDetail2Activity.this, "调起微信支付", Toast.LENGTH_SHORT).show();
            }
        });
        mDialog.getView(R.id.rl_alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Toast.makeText(ZhuanLanDetail2Activity.this, "调起支付宝支付", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPaySuccessDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_paysuccess)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    private void showChongzhiDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_ischongzhi)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showPaySuccessDialog();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_collect:
                if (isCollected) {
                    iv_collect.setImageResource(R.drawable.weiguanzhuxin);
                } else {
                    iv_collect.setImageResource(R.drawable.xinxin);
                }
                isCollected = !isCollected;
                break;
            case R.id.iv_bofang:
                if (!isBofang){
                    iv_bofang.setImageResource(R.drawable.bofang_yellow_middle);
                    setISshow(true);
                    ClickPopShow();
                }else {
                    iv_bofang.setImageResource(R.drawable.pause_yellow_middle);
                    ClickPauseMusic();
                }
                isBofang=!isBofang;
                break;
            case R.id.iv_download:
                Toast.makeText(this, "已成功加入下载列表", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_writeliuyan:
                startActivity(new Intent(this, LiuYanActivity.class));
                break;
            case R.id.tv_buy:
                showBuyDialog();
                break;
        }
    }
}
