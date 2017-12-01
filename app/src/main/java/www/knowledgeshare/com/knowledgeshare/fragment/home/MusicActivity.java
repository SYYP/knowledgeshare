package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;

public class MusicActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_bigphoto;
    private TextView tv_title;
    private TextView music_duration_played;
    private SeekBar play_seek;
    private TextView music_duration;
    private ImageView iv_previous;
    private ImageView iv_pause;
    private ImageView iv_next;
    private TextView tv_liebiao;
    private TextView tv_wengao;
    private TextView tv_download;
    private TextView tv_share;
    private TextView tv_collect;
    private LinearLayout activity_music;
    private int currentProgress = 0;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private boolean isCollected;
    private MediaService.MyBinder mMyBinder;
    //“绑定”服务的intent
    private Intent MediaServiceIntent;
    private Handler mHandler = new Handler();
    //进度条下面的当前进度文字，将毫秒化为m:ss格式
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private boolean isbofang = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        UltimateBar ultimateBar = new UltimateBar(this);
        //        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_music);
        initView();
        initDialog();
//        initMusic();
    }

    private void initMusic() {
        MediaServiceIntent = new Intent(this, MediaService.class);
        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //我们的handler发送是定时1000s发送的，如果不关闭，MediaPlayer release掉了还在获取getCurrentPosition就会爆IllegalStateException错误
//        mHandler.removeCallbacks(mRunnable);
//        unbindService(mServiceConnection);
    }

    /**
     * 更新ui的runnable
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            play_seek.setProgress(mMyBinder.getPlayPosition());
            music_duration_played.setText(time.format(mMyBinder.getPlayPosition()) + "");
            mHandler.postDelayed(mRunnable, 1000);
        }
    };
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
            play_seek.setMax(mMyBinder.getProgress());
            music_duration.setText(time.format(mMyBinder.getProgress())+"");
            play_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //这里很重要，如果不判断是否来自用户操作进度条，会不断执行下面语句块里面的逻辑，然后就会卡顿卡顿
                    if (fromUser) {
                        mMyBinder.seekToPositon(seekBar.getProgress());
                        //                    mMediaService.mMediaPlayer.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            mHandler.post(mRunnable);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
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

    @Override
    public void finish() {
        //关闭窗体动画显示
        super.finish();
        this.overridePendingTransition(0, R.anim.bottom_out);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_bigphoto = (ImageView) findViewById(R.id.iv_bigphoto);
        tv_title = (TextView) findViewById(R.id.tv_title);
        music_duration_played = (TextView) findViewById(R.id.music_duration_played);
        play_seek = (SeekBar) findViewById(R.id.play_seek);
        music_duration = (TextView) findViewById(R.id.music_duration);
        iv_previous = (ImageView) findViewById(R.id.iv_previous);
        iv_previous.setOnClickListener(this);
        iv_pause = (ImageView) findViewById(R.id.iv_pause);
        iv_pause.setOnClickListener(this);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_next.setOnClickListener(this);
        tv_liebiao = (TextView) findViewById(R.id.tv_liebiao);
        tv_liebiao.setOnClickListener(this);
        tv_wengao = (TextView) findViewById(R.id.tv_wengao);
        tv_wengao.setOnClickListener(this);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(this);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        tv_collect.setOnClickListener(this);
        activity_music = (LinearLayout) findViewById(R.id.activity_music);
        play_seek.setProgress(0);
        play_seek.setMax(100);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_liebiao:
                Intent intent11 = new Intent(this, BoFangListActivity.class);
                startActivity(intent11);
                this.overridePendingTransition(R.anim.bottom_in, 0);
                break;
            case R.id.tv_share:
                showShareDialog();
                break;
            case R.id.tv_wengao:
                startActivity(new Intent(this, WenGaoActivity.class));
                break;
            case R.id.tv_download:
                break;
            case R.id.tv_collect:
                if (isCollected) {
                    Drawable drawable = getResources().getDrawable(R.drawable.music_collect);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_collect.setCompoundDrawables(null, drawable, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.music_collected);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tv_collect.setCompoundDrawables(null, drawable, null, null);
                }
                isCollected = !isCollected;
                break;
            case R.id.iv_previous:
                break;
            case R.id.iv_pause:
                if (isbofang) {
//                    mMyBinder.pauseMusic();
                    iv_pause.setImageResource(R.drawable.pause_yellow_big);
                } else {
//                    mMyBinder.playMusic();
                    iv_pause.setImageResource(R.drawable.bofang_yellow_big);
                }
                isbofang = !isbofang;
                break;
            case R.id.iv_next:
                break;
        }
    }

}
