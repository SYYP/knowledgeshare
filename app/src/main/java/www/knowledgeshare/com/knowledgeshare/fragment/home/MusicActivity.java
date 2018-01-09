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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.HistroyUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HuanChongBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

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
    private MusicTypeBean mMusicTypeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        setISshow(false);
        EventBus.getDefault().register(this);
        initView();
        initDialog();
        initMusic();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(HuanChongBean eventBean) {
        if (eventBean.getMsg().equals("huanchong")) {
            play_seek.setSecondaryProgress(play_seek.getMax() * eventBean.getPercent()/100);
//            play_seek.setSecondaryProgress(play_seek.getMax() * 100 / eventBean.getPercent());
//            System.out.println("ssssssssssssssssssssss"+play_seek.getMax()+"   "+eventBean.getPercent());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(MusicTypeBean musicTypeBean) {
        if (musicTypeBean.getMsg().equals("musicplayertype")) {
            //接收到播放主界面要刷新的数据
            mMusicTypeBean = musicTypeBean;
            tv_title.setText(mMusicTypeBean.getVideo_name());
            Glide.with(this).load(mMusicTypeBean.getT_head()).into(iv_bigphoto);
            isCollected = mMusicTypeBean.isCollected();
            if (isCollected) {
                Drawable drawable = getResources().getDrawable(R.drawable.music_collected);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_collect.setCompoundDrawables(null, drawable, null, null);
            } else {
                Drawable drawable = getResources().getDrawable(R.drawable.music_collect);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_collect.setCompoundDrawables(null, drawable, null, null);
            }
            List<BofangHistroyBean> search = HistroyUtils.search();
            if (search!=null && search.size()>0){
                tv_liebiao.setText(1+"/"+search.size());
            }
            if (mMyBinder.isPlaying()) {
                iv_pause.setImageResource(R.drawable.bofang_yellow_big);
            } else {
                iv_pause.setImageResource(R.drawable.pause_yellow_big);
            }
            play_seek.setMax(mMyBinder.getProgress());
            music_duration.setText(time.format(mMyBinder.getProgress()) + "");
        }
    }

    private void initMusic() {
        MediaServiceIntent = new Intent(this, MediaService.class);
        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //我们的handler发送是定时1000s发送的，如果不关闭，MediaPlayer release掉了还在获取getCurrentPosition就会爆IllegalStateException错误
        mHandler.removeCallbacks(mRunnable);
        unbindService(mServiceConnection);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //onrestart死活不走，他妈的我就放到onresume中了
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMyBinder != null) {
            if (mMyBinder.isPlaying()) {
                iv_pause.setImageResource(R.drawable.bofang_yellow_big);
            } else {
                iv_pause.setImageResource(R.drawable.pause_yellow_big);
            }
        }
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
            if (mMyBinder.isPlaying()) {
                iv_pause.setImageResource(R.drawable.bofang_yellow_big);
            } else {
                iv_pause.setImageResource(R.drawable.pause_yellow_big);
            }
            play_seek.setMax(mMyBinder.getProgress());
            music_duration.setText(time.format(mMyBinder.getProgress()) + "");
            mMyBinder.refreshhuanchong();
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
        mMusicTypeBean = (MusicTypeBean) getIntent().getSerializableExtra("data");
        tv_title.setText(mMusicTypeBean.getVideo_name());
        Glide.with(this).load(mMusicTypeBean.getT_head()).into(iv_bigphoto);
        isCollected = mMusicTypeBean.isCollected();
        if (isCollected) {
            Drawable drawable = getResources().getDrawable(R.drawable.music_collected);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_collect.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.music_collect);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_collect.setCompoundDrawables(null, drawable, null, null);
        }
        List<BofangHistroyBean> search = HistroyUtils.search();
        if (search!=null && search.size()>0){
            tv_liebiao.setText(1+"/"+search.size());
        }
    }

    private void changeCollect() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", mMusicTypeBean.getId());
        String url = "";
        if (mMusicTypeBean.getType().equals("free")) {
            params.put("type", "1");
            if (isCollected) {
                url = MyContants.LXKURL + "free/no-favorite";
            } else {
                url = MyContants.LXKURL + "free/favorite";
            }
        } else if (mMusicTypeBean.getType().equals("everydaycomment")) {
            params.put("type", "1");
            if (isCollected) {
                url = MyContants.LXKURL + "daily/no-favorite";
            } else {
                url = MyContants.LXKURL + "daily/favorite";
            }
        } else if (mMusicTypeBean.getType().equals("softmusicdetail")) {
            params.put("type", "1");
            if (isCollected) {
                url = MyContants.LXKURL + "xk/no-favorite";
            } else {
                url = MyContants.LXKURL + "xk/favorite";
            }
        } else if (mMusicTypeBean.getType().equals("zhuanlandetail")) {
            url = MyContants.LXKURL + "zl/favorite";
            if (isCollected) {
                params.put("fav_type", "0");
            } else {
                params.put("fav_type", "1");
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
                                 Toast.makeText(MusicActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
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
                             }
                         }
                );
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
                Intent intent = new Intent(this, WenGaoActivity.class);
                intent.putExtra("type", mMusicTypeBean.getType());
                //                intent.putExtra("t_name", mMusicTypeBean.getT_name());
                //                intent.putExtra("t_head", mMusicTypeBean.getT_head());
                //                intent.putExtra("video_name", mMusicTypeBean.getVideo_name());
                intent.putExtra("id", mMusicTypeBean.getId());
                //                intent.putExtra("teacher_id", mMusicTypeBean.getTeacher_id());
                startActivity(intent);
                break;
            case R.id.tv_download:
                break;
            case R.id.tv_collect:
                changeCollect();
                break;
            case R.id.iv_previous:
                mMyBinder.preciousMusic();
                break;
            case R.id.iv_pause:
                if (isbofang) {
                    mMyBinder.pauseMusic();
                    iv_pause.setImageResource(R.drawable.pause_yellow_big);
                    EventBean eventBean = new EventBean("main_pause");
                    EventBus.getDefault().postSticky(eventBean);
                    EventBean eventBean2 = new EventBean("home_pause");
                    EventBus.getDefault().postSticky(eventBean2);
                } else {
                    mMyBinder.playMusic();
                    iv_pause.setImageResource(R.drawable.bofang_yellow_big);
                    EventBean eventBean = new EventBean("rotate");
                    EventBus.getDefault().postSticky(eventBean);
                    EventBean eventBean2 = new EventBean("home_bofang");
                    EventBus.getDefault().postSticky(eventBean2);
                }
                isbofang = !isbofang;
                break;
            case R.id.iv_next:
                mMyBinder.nextMusic();
                break;
        }
    }

}
