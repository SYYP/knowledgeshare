package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.ShoppingCartActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;

public class LikeDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private ImageView iv_beijing;
    private TextView tv_download, tv_share;
    private TextView tv_search;
    private ImageView iv_shopcar;
    private TextView tv_guanzhu;
    private TextView tv_dianzan_count;
    private TextView tv_teacher_intro;
    private RecyclerView recycler_free;
    private TextView tv_shiyirenqun;
    private TextView tv_readxuzhi;
    private TextView tv_writeliuyan;
    private RecyclerView recycler_liuyan;
    private TextView tv_shopcar, tv_buy;
    private BaseDialog.Builder mBuilder;
    private BaseDialog mDialog;
    private boolean isDianzan;
    private boolean isGuanzhu = true;
    private boolean isZan = true;
    private ImageView iv_guanzhu, iv_dianzan;
    private NestedScrollView nestView;
    private boolean mIsCollected;
    private boolean mDianzan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_detail);
        initView();
        initDialog();
        initData();
        initMusic();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        iv_shopcar = (ImageView) findViewById(R.id.iv_shopcar);
        iv_shopcar.setOnClickListener(this);
        tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);
        tv_guanzhu.setOnClickListener(this);
        tv_dianzan_count = (TextView) findViewById(R.id.tv_dianzan_count);
        tv_dianzan_count.setOnClickListener(this);
        tv_teacher_intro = (TextView) findViewById(R.id.tv_teacher_intro);
        tv_shopcar = (TextView) findViewById(R.id.tv_shopcar);
        tv_shopcar.setOnClickListener(this);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(this);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        tv_buy.setOnClickListener(this);
        recycler_free = (RecyclerView) findViewById(R.id.recycler_free);
        tv_shiyirenqun = (TextView) findViewById(R.id.tv_shiyirenqun);
        tv_readxuzhi = (TextView) findViewById(R.id.tv_readxuzhi);
        tv_writeliuyan = (TextView) findViewById(R.id.tv_writeliuyan);
        tv_writeliuyan.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        recycler_free.setLayoutManager(new LinearLayoutManager(this));
        recycler_free.setNestedScrollingEnabled(false);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        iv_guanzhu = (ImageView) findViewById(R.id.iv_guanzhu);
        iv_guanzhu.setOnClickListener(this);
        iv_dianzan = (ImageView) findViewById(R.id.iv_dianzan);
        iv_dianzan.setOnClickListener(this);
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
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        LieBiaoAdapter lieBiaoAdapter = new LieBiaoAdapter(R.layout.item_like_liebiao, list);
        recycler_free.setAdapter(lieBiaoAdapter);
        lieBiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position != 0) {
                    showIsBuyDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                } else {
                    //                    mMyBinder.playMusic();
                    setISshow(true);
                    ClickPopShow();
                }
            }
        });
        LiuYanAdapter liuYanAdapter = new LiuYanAdapter(R.layout.item_liuyan, list);
        recycler_liuyan.setAdapter(liuYanAdapter);
        tv_teacher_intro.setText("法撒旦撒多撒多撒旦撒海带丝哦啊湖附近很大佛诞节搜附近" +
                "哦都是奇偶发奇偶及欧冠大佛结构辅导机构奇偶辅导机构");
        tv_shiyirenqun.setText("法撒旦撒多撒多撒旦撒海带丝哦啊湖附近很大佛诞节搜附近" +
                "哦都是奇偶发奇偶及欧冠大佛结构辅导机构奇偶辅导机构");
        tv_readxuzhi.setText("法撒旦撒多撒多撒旦撒海带丝哦啊湖附近很大佛诞节搜附近" +
                "哦都是奇偶发奇偶及欧冠大佛结构辅导机构奇偶辅导机构");
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    private class LieBiaoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LieBiaoAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            if (helper.getAdapterPosition() == 0) {
                helper.setVisible(R.id.tv_trylisten, true);
                helper.setVisible(R.id.iv_wengao, true);
            } else {
                helper.setVisible(R.id.tv_trylisten, false);
                helper.setVisible(R.id.iv_wengao, false);
            }
            helper.getView(R.id.iv_dian).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showListDialog();
                }
            });
            helper.getView(R.id.iv_wengao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(LikeDetailActivity.this, WenGaoActivity.class));
                }
            });
        }
    }

    private void showListDialog() {
        mDialog = mBuilder.setViewId(R.layout.dialog_free)
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
        mDialog.getView(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showShareDialog();
            }
        });
        final TextView tv_collect = mDialog.getView(R.id.tv_collect);
        tv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsCollected) {
                    Drawable drawable = getResources().getDrawable(R.drawable.bofanglist_collect);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_collect.setCompoundDrawables(null, drawable, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.collect_shixin);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_collect.setCompoundDrawables(null, drawable, null, null);
                }
                mIsCollected = !mIsCollected;
            }
        });
        final TextView tv_dianzan = mDialog.getView(R.id.tv_dianzan);
        tv_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDianzan) {
                    Drawable drawable = getResources().getDrawable(R.drawable.dianzan_yellow_big);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_dianzan.setCompoundDrawables(null, drawable, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.dianzan_shixin);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_dianzan.setCompoundDrawables(null, drawable, null, null);
                }
                mDianzan = !mDianzan;
            }
        });
        mDialog.getView(R.id.tv_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LikeDetailActivity.this, "已加入下载列表", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }

    private void showIsBuyDialog(int grary, int animationStyle) {
        mDialog = mBuilder.setViewId(R.layout.dialog_isbuy)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(MyUtils.dip2px(this, 250), LinearLayout.LayoutParams.WRAP_CONTENT)
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
        mDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showBuyDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
            }
        });
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
        mDialog.getView(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
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

    private void showBuyDialog(int grary, int animationStyle) {
        mDialog = mBuilder.setViewId(R.layout.dialog_buy)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
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
                    } else {
                        iv_dianzan.setImageResource(R.drawable.free_dianzan);
                    }
                    isDianzan = !isDianzan;
                }
            });
            if (helper.getAdapterPosition()==2){
                helper.getView(R.id.view_line).setVisibility(View.GONE);
            }
        }
    }

    private MediaService.MyBinder mMyBinder;
    //“绑定”服务的intent
    private Intent MediaServiceIntent;

    private void initMusic() {
        //        MediaServiceIntent = new Intent(this, MediaService.class);
        //        startService(MediaServiceIntent);
        //        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
            if (mMyBinder.isPlaying()) {
            } else {
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //        unbindService(mServiceConnection);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_download:
                startActivity(new Intent(this, DownLoadListActivity.class));
                break;
            case R.id.tv_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.iv_shopcar:
                startActivity(new Intent(this, ShoppingCartActivity.class));
                break;
            case R.id.tv_shopcar:
                Toast.makeText(this, "已成功加入购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_writeliuyan:
                startActivity(new Intent(this, LiuYanActivity.class));
                break;
            case R.id.tv_share:
                showShareDialog();
                break;
            case R.id.tv_buy:
                showBuyDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion);
                break;
            case R.id.tv_guanzhu:
            case R.id.iv_guanzhu:
                if (isGuanzhu) {
                    iv_guanzhu.setImageResource(R.drawable.free_quxiaoguanzhu);
                } else {
                    iv_guanzhu.setImageResource(R.drawable.free_guanzhu);
                }
                isGuanzhu = !isGuanzhu;
                break;
            case R.id.tv_dianzan_count:
            case R.id.iv_dianzan:
                if (isZan) {
                    iv_dianzan.setImageResource(R.drawable.free_dianzan);
                } else {
                    iv_dianzan.setImageResource(R.drawable.free_yizan);
                }
                isZan = !isZan;
                break;
        }
    }
}
