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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.FreeTryReadDetailBean;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;


public class ZhuanLanDetail2Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private CircleImageView iv_teacher_head;
    private TextView tv_teacher_name;
    private ImageView iv_collect,iv_beijing;
    private TextView tv_time1;
    private ImageView iv_bofang;
    private TextView tv_title2,tv_content;
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
    private int lastID;
    private LiuYanAdapter mLiuYanAdapter;
    private List<FreeTryReadDetailBean.CommentEntity> mComment;
    private FreeTryReadDetailBean mFreeTryReadDetailBean;

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
        tv_title.setText(getIntent().getStringExtra("title"));
        String id = getIntent().getStringExtra("id");
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("userid", SpUtils.getString(this, "id", ""));
        OkGo.<FreeTryReadDetailBean>post(MyContants.LXKURL + "zl/trials-show")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<FreeTryReadDetailBean>(FreeTryReadDetailBean.class) {
                             @Override
                             public void onSuccess(Response<FreeTryReadDetailBean> response) {
                                 int code = response.code();
                                 mFreeTryReadDetailBean = response.body();
                                 Glide.with(ZhuanLanDetail2Activity.this).load(mFreeTryReadDetailBean.getT_header()).into(iv_teacher_head);
                                 Glide.with(ZhuanLanDetail2Activity.this).load(mFreeTryReadDetailBean.getImgurl()).into(iv_beijing);
                                 tv_teacher_name.setText(mFreeTryReadDetailBean.getT_name());
                                 tv_content.setText(mFreeTryReadDetailBean.getDescription());
                                 tv_time1.setText(mFreeTryReadDetailBean.getCreated_at());
                                 tv_time2.setText(mFreeTryReadDetailBean.getVideo_time());
                                 tv_buy.setText(mFreeTryReadDetailBean.getZl_price());
                                 tv_title2.setText(mFreeTryReadDetailBean.getName());
//                                 loadMoreComment("");
                                 mComment=mFreeTryReadDetailBean.getComment();
                                 mLiuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan, mComment);
                                 recycler_liuyan.setAdapter(mLiuYanAdapter);
                                 if (mFreeTryReadDetailBean.isfollow()){
                                     iv_collect.setImageResource(R.drawable.xinxin);
                                 }else {
                                     iv_collect.setImageResource(R.drawable.weiguanzhuxin);
                                 }
                             }
                         }
                );
    }

    /*private void loadMoreComment(String after) {
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(this, "id", ""));
        params.put("after", after);
        OkGo.<CommentMoreBean>post(MyContants.LXKURL + "xk/more-comment")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<CommentMoreBean>(CommentMoreBean.class) {
                    @Override
                    public void onSuccess(Response<CommentMoreBean> response) {
                        int code = response.code();
                        CommentMoreBean commentMoreBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            List<CommentMoreBean.DataEntity> data = commentMoreBean.getData();
                            if (mComment == null || mComment.size() == 0) {
                                mComment = data;
                                mLiuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan, mComment);
                                recycler_liuyan.setAdapter(mLiuYanAdapter);
                            } else {
                                if (data == null || data.size() == 0) {
                                    Toast.makeText(ZhuanLanDetail2Activity.this, "已无更多评论", Toast.LENGTH_SHORT).show();
                                } else {
                                    mComment.addAll(data);
                                    mLiuYanAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                        }
                    }
                });
        springview.onFinishFreshAndLoad();
    }*/

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
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_bofang = (ImageView) findViewById(R.id.iv_bofang);
        iv_bofang.setOnClickListener(this);
        tv_title2 = (TextView) findViewById(R.id.tv_title2);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        iv_download.setOnClickListener(this);
        tv_writeliuyan = (TextView) findViewById(R.id.tv_writeliuyan);
        tv_writeliuyan.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(this);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        nestView = (NestedScrollView) findViewById(R.id.nestView);
        webview = (WebView) findViewById(R.id.webview);
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

    private class LiuYanAdapter extends BaseQuickAdapter<FreeTryReadDetailBean.CommentEntity, BaseViewHolder> {

        public LiuYanAdapter(@LayoutRes int layoutResId, @Nullable List<FreeTryReadDetailBean.CommentEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final FreeTryReadDetailBean.CommentEntity item) {
            lastID = item.getId();
            final ImageView iv_dianzan = helper.getView(R.id.iv_dianzan);
            final ImageView iv_head = helper.getView(R.id.iv_head);
            if (item.isIslive()) {
                iv_dianzan.setImageResource(R.drawable.free_yizan);
            } else {
                iv_dianzan.setImageResource(R.drawable.free_dianzan);
            }
            Glide.with(mContext).load(item.getUser_avatar()).into(iv_head);
            helper.setText(R.id.tv_name, item.getUser_name())
                    .setText(R.id.tv_time, item.getCreated_at())
                    .setText(R.id.tv_content, item.getContent())
                    .setText(R.id.tv_dainzan_count, item.getLive() + "");
            if (item.getComment() != null && item.getComment().size() > 0) {
                helper.setVisible(R.id.ll_author, true);
                helper.setText(R.id.tv_author_content, item.getContent());
            } else {
                helper.setVisible(R.id.ll_author, false);
            }
            helper.getView(R.id.ll_dianzan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean islive = item.isIslive();
                    if (islive) {
                        mComment.get(helper.getAdapterPosition()).setIslive(false);
                        nodianzan(helper.getAdapterPosition(), item.getId(), item.getLive());
                    } else {
                        mComment.get(helper.getAdapterPosition()).setIslive(true);
                        dianzan(helper.getAdapterPosition(), item.getId(), item.getLive());
                    }
                    mLiuYanAdapter.notifyDataSetChanged();
                }
            });
            if (helper.getAdapterPosition() == mLiuYanAdapter.getData().size() - 1) {
                helper.getView(R.id.view_line).setVisibility(View.GONE);
            }
        }
    }

    private void dianzan(final int adapterPosition, int id, final int count) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("comment_id", id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-comment/live")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mComment.get(adapterPosition).setLive(count + 1);
                                 mLiuYanAdapter.notifyDataSetChanged();
                             }
                         }
                );
    }

    private void nodianzan(final int adapterPosition, int id, final int count) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("comment_id", id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-comment/no-live")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mComment.get(adapterPosition).setLive(count - 1);
                                 mLiuYanAdapter.notifyDataSetChanged();
                             }
                         }
                );
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
                    noguanzhu(mFreeTryReadDetailBean.getTeacher_id());
                } else {
                    iv_collect.setImageResource(R.drawable.xinxin);
                    guanzhu(mFreeTryReadDetailBean.getTeacher_id());
                }
                isCollected = !isCollected;
                break;
            case R.id.iv_bofang:
                if (!isBofang) {
                    iv_bofang.setImageResource(R.drawable.bofang_yellow_middle);
                    setISshow(true);
                    ClickPopShow();
                } else {
                    iv_bofang.setImageResource(R.drawable.pause_yellow_middle);
                    ClickPauseMusic();
                }
                isBofang = !isBofang;
                break;
            case R.id.iv_download:
                Toast.makeText(this, "已成功加入下载列表", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_writeliuyan:
                Intent intent = new Intent(this, LiuYanActivity.class);
                intent.putExtra("teacher_id", mFreeTryReadDetailBean.getTeacher_id() + "");
                intent.putExtra("type", "zhuanlandetail");
                intent.putExtra("xiaoke_id", mFreeTryReadDetailBean.getId()+"");
                startActivity(intent);
                break;
            case R.id.tv_buy:
                showBuyDialog();
                break;
        }
    }

    private void guanzhu(int teacher_id) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("teacher_id", teacher_id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-follow/attention")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

    private void noguanzhu(int teacher_id) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("teacher_id", teacher_id);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "free-follow/no-attention")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(ZhuanLanDetail2Activity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

}
