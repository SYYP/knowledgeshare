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

public class EveryDayCommentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_share;
    private ImageView iv_beijing;
    private TextView tv_search;
    private TextView tv_download;
    private TextView tv_jie_count;
    private TextView tv_look_count;
    private TextView tv_collect_count;
    private RecyclerView recycler_liebiao;
    private LinearLayout activity_free;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_day_comment);
        initView();
        initDialog();
        initMusic();
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
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
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(this);
        iv_beijing = (ImageView) findViewById(R.id.iv_beijing);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        tv_jie_count = (TextView) findViewById(R.id.tv_jie_count);
        tv_look_count = (TextView) findViewById(R.id.tv_look_count);
        tv_collect_count = (TextView) findViewById(R.id.tv_collect_count);
        recycler_liebiao = (RecyclerView) findViewById(R.id.recycler_liebiao);
        activity_free = (LinearLayout) findViewById(R.id.activity_free);
        recycler_liebiao.setLayoutManager(new LinearLayoutManager(this));
        recycler_liebiao.setNestedScrollingEnabled(false);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        LieBiaoAdapter lieBiaoAdapter = new LieBiaoAdapter(R.layout.item_free, list);
        recycler_liebiao.setAdapter(lieBiaoAdapter);
        lieBiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mMyBinder.playMusic();
                EventBean eventBean = new EventBean("rotate");
                EventBus.getDefault().postSticky(eventBean);
            }
        });
    }

    private class LieBiaoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public LieBiaoAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.getView(R.id.iv_dian).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });
            helper.getView(R.id.iv_wengao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(EveryDayCommentActivity.this,WenGaoActivity.class));
                }
            });
        }
    }

    private void showDialog() {
        mDialog.show();
        mDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
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
            case R.id.iv_share:
                showShareDialog();
                break;
            case R.id.tv_search:
                startActivity(new Intent(this,SearchActivity.class));
                break;
            case R.id.tv_download:
                startActivity(new Intent(this,DownLoadListActivity.class));
                break;
        }
    }
}
