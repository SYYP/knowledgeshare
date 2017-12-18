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
import com.orhanobut.logger.Logger;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.WenGaoBean;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

public class WenGaoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_share;
    private CircleImageView iv_teacher_head;
    private TextView tv_ke_name;
    private TextView tv_teacher_name;
    private TextView tv_content;
    private ImageView iv_collect;
    private LinearLayout ll_liuyan;
    private LinearLayout ll_guanzhu;
    private RecyclerView recycler_liuyan;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private boolean isGuanzhu;
    private WebView webview;
    private boolean isDianzan;
    private NestedScrollView nestView;
    private List<WenGaoBean.CommentEntity> mComment;
    private LiuYanAdapter mLiuYanAdapter;
    private String mId, mType;
    private WenGaoBean mWenGaoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen_gao);
        initView();
        initDialog();
        showTanchuangDialog();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        iv_teacher_head = (CircleImageView) findViewById(R.id.iv_teacher_head);
        tv_ke_name = (TextView) findViewById(R.id.tv_ke_name);
        tv_teacher_name = (TextView) findViewById(R.id.tv_teacher_name);
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_collect = (ImageView) findViewById(R.id.iv_collect);
        ll_liuyan = (LinearLayout) findViewById(R.id.ll_liuyan);
        ll_liuyan.setOnClickListener(this);
        ll_guanzhu = (LinearLayout) findViewById(R.id.ll_guanzhu);
        ll_guanzhu.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
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
        nestView = (NestedScrollView) findViewById(R.id.nestView);
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
        String t_name = getIntent().getStringExtra("t_name");
        String t_head = getIntent().getStringExtra("t_head");
        String video_name = getIntent().getStringExtra("video_name");
        mId = getIntent().getStringExtra("id");
        mType = getIntent().getStringExtra("type");
        Glide.with(this).load(t_head).into(iv_teacher_head);
        tv_teacher_name.setText(t_name);
        tv_ke_name.setText(video_name);
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(this, "id", ""));
        params.put("id", mId);
        String url = "";
        if (mType.equals("free")) {
            url = MyContants.LXKURL + "free/draft-show";
        } else if (mType.equals("everydaycomment")) {
            url = MyContants.LXKURL + "daily/draft-show";
        }else if (mType.equals("softmusicdetail")){
            url = MyContants.LXKURL + "xk/draft-show";
        }
        OkGo.<WenGaoBean>post(url)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<WenGaoBean>(WenGaoBean.class) {
                    @Override
                    public void onSuccess(Response<WenGaoBean> response) {
                        int code = response.code();
                        mWenGaoBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            tv_content.setText(mWenGaoBean.getContent());
                            mComment = mWenGaoBean.getComment();
                            isGuanzhu = mWenGaoBean.isIsfav();
                            if (isGuanzhu) {
                                iv_collect.setImageResource(R.drawable.xinxin);
                            } else {
                                iv_collect.setImageResource(R.drawable.weiguanzhuxin);
                            }
                            mLiuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan2, mComment);
                            recycler_liuyan.setAdapter(mLiuYanAdapter);
                        } else {
                        }
                    }
                });
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    private class LiuYanAdapter extends BaseQuickAdapter<WenGaoBean.CommentEntity, BaseViewHolder> {

        public LiuYanAdapter(@LayoutRes int layoutResId, @Nullable List<WenGaoBean.CommentEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final WenGaoBean.CommentEntity item) {
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
                helper.setText(R.id.tv_author_content, item.getComment().get(0).getContent());
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
        }
    }

    private void changeCollect() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", mId);
        params.put("type", "2");
        String url = "";
        if (mType.equals("free")) {
            if (isGuanzhu) {
                url = MyContants.LXKURL + "free/no-favorite";
            } else {
                url = MyContants.LXKURL + "free/favorite";
            }
        }else if (mType.equals("everydaycomment")) {
            if (isGuanzhu) {
                url = MyContants.LXKURL + "daily/no-favorite";
            } else {
                url = MyContants.LXKURL + "daily/favorite";
            }
        }else if (mType.equals("softmusicdetail")) {
            if (isGuanzhu) {
                url = MyContants.LXKURL + "xk/no-favorite";
            } else {
                url = MyContants.LXKURL + "xk/favorite";
            }
        }

        OkGo.<DianZanbean>post(url)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(WenGaoActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 if (isGuanzhu) {
                                     iv_collect.setImageResource(R.drawable.weiguanzhuxin);
                                 } else {
                                     iv_collect.setImageResource(R.drawable.xinxin);
                                 }
                                 isGuanzhu = !isGuanzhu;
                             }
                         }
                );
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
                                 Toast.makeText(WenGaoActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                                 Toast.makeText(WenGaoActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 mComment.get(adapterPosition).setLive(count - 1);
                                 mLiuYanAdapter.notifyDataSetChanged();
                             }
                         }
                );
    }

    private void showShareDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_share)
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
    }

    private void showTanchuangDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_biji)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.view_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                showShareDialog();
                break;
            case R.id.ll_liuyan:
                Intent intent = new Intent(this, LiuYanActivity.class);
                intent.putExtra("type", mType+"-wengao");
                intent.putExtra("xiaoke_id", mId);
                intent.putExtra("teacher_id", getIntent().getStringExtra("teacher_id"));
                startActivity(intent);
                break;
            case R.id.ll_guanzhu:
                changeCollect();
                break;
        }
    }
}
