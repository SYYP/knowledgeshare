package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;

public class FreeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_beijing;
    private TextView tv_download;
    private TextView tv_search;
    private TextView tv_share;
    private TextView tv_guanzhu;
    private TextView tv_dianzan_count;
    private TextView tv_teacher_intro;
    private RecyclerView recycler_free;
    private TextView tv_shiyirenqun;
    private TextView tv_readxuzhi;
    private TextView tv_writeliuyan;
    private RecyclerView recycler_liuyan;
    private LinearLayout activity_free;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private boolean isDianzan;
    private boolean isGuanzhu=true;
    private boolean isZan=true;
    private ImageView iv_guanzhu,iv_dianzan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        initView();
        initData();
        initDialog();
        initMusic();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        FreeAdapter freeAdapter = new FreeAdapter(R.layout.item_free, list);
        recycler_free.setAdapter(freeAdapter);
        freeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mMyBinder.playMusic();
                EventBean eventBean = new EventBean("rotate");
                EventBus.getDefault().postSticky(eventBean);
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

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(this);
        tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);
        tv_guanzhu.setOnClickListener(this);
        tv_dianzan_count = (TextView) findViewById(R.id.tv_dianzan_count);
        tv_teacher_intro = (TextView) findViewById(R.id.tv_teacher_intro);
        recycler_free = (RecyclerView) findViewById(R.id.recycler_free);
        tv_shiyirenqun = (TextView) findViewById(R.id.tv_shiyirenqun);
        tv_readxuzhi = (TextView) findViewById(R.id.tv_readxuzhi);
        tv_writeliuyan = (TextView) findViewById(R.id.tv_writeliuyan);
        tv_writeliuyan.setOnClickListener(this);
        recycler_liuyan = (RecyclerView) findViewById(R.id.recycler_liuyan);
        activity_free = (LinearLayout) findViewById(R.id.activity_free);
        recycler_free.setLayoutManager(new LinearLayoutManager(this));
        recycler_free.setNestedScrollingEnabled(false);
        recycler_liuyan.setLayoutManager(new LinearLayoutManager(this));
        recycler_liuyan.setNestedScrollingEnabled(false);
        iv_guanzhu= (ImageView) findViewById(R.id.iv_guanzhu);
        iv_guanzhu.setOnClickListener(this);
        iv_dianzan= (ImageView) findViewById(R.id.iv_dianzan);
        iv_dianzan.setOnClickListener(this);
    }

    private class FreeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public FreeAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.getView(R.id.iv_dian).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showListDialog();
                }
            });
            helper.getView(R.id.iv_wengao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(FreeActivity.this,WenGaoActivity.class));
                }
            });
        }
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
    }

    private MediaService.MyBinder mMyBinder;
    //“绑定”服务的intent
    private Intent MediaServiceIntent;
    private void initMusic() {
        MediaServiceIntent = new Intent(this, MediaService.class);
//        startService(MediaServiceIntent);
        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
            if (mMyBinder.isPlaying()){
            }else {
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
        unbindService(mServiceConnection);
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
            case R.id.tv_share:
                showShareDialog();
                break;
            case R.id.tv_guanzhu:
            case R.id.iv_guanzhu:
                if (isGuanzhu){
                    iv_guanzhu.setImageResource(R.drawable.free_quxiaoguanzhu);
                }else {
                    iv_guanzhu.setImageResource(R.drawable.free_guanzhu);
                }
                isGuanzhu=!isGuanzhu;
                break;
            case R.id.tv_dianzan_count:
            case R.id.iv_dianzan:
                if (isZan){
                    iv_dianzan.setImageResource(R.drawable.free_dianzan);
                }else {
                    iv_dianzan.setImageResource(R.drawable.free_yizan);
                }
                isZan=!isZan;
                break;
            case R.id.tv_writeliuyan:
                startActivity(new Intent(this, LiuYanActivity.class));
                break;
        }
    }
}
