package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.WenGaoFileActivity;
import www.knowledgeshare.com.knowledgeshare.base.UMShareActivity;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.db.HistroyUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DownBean1;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DownBean2;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DownBean3;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HuanChongBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.VideoCollectBean;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

public class MusicActivity extends UMShareActivity implements View.OnClickListener {

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
    private MusicTypeBean mMusicTypeBean;
    private String mShare_h5_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        setISshow(false);
        EventBus.getDefault().register(this);
        initView();
        initMusic();
        initDialog();
        initCollect();
        initNETDialog();
    }

    private void initCollect() {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(this, "id", ""));
        if (mMusicTypeBean == null) {
            return;
        }
        if (mMusicTypeBean.getType().equals("free")) {
            params.put("type", "free");
        } else if (mMusicTypeBean.getType().equals("everydaycomment")) {
            params.put("type", "daily");
        } else if (mMusicTypeBean.getType().equals("softmusicdetail")) {
            params.put("type", "xk");
        } else if (mMusicTypeBean.getType().equals("zhuanlandetail")) {
            params.put("type", "zl");
        }
        params.put("id", mMusicTypeBean.getId());
        OkGo.<VideoCollectBean>post(MyContants.LXKURL + "video/fav-live")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<VideoCollectBean>(VideoCollectBean.class) {
                    @Override
                    public void onSuccess(Response<VideoCollectBean> response) {
                        int code = response.code();
                        VideoCollectBean videoCollectBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            VideoCollectBean.DataEntity dataEntity = videoCollectBean.getData().get(0);
                            if (dataEntity.isIslive()) {

                            } else {

                            }
                            //是否收藏
                            isCollected = dataEntity.isIsfav();
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
                            mShare_h5_url = dataEntity.getShare_h5_url();
                        } else {
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(HuanChongBean eventBean) {
        if (eventBean.getMsg().equals("huanchong")) {
            play_seek.setSecondaryProgress(play_seek.getMax() * eventBean.getPercent() / 100);
            //            play_seek.setSecondaryProgress(play_seek.getMax() * 100 / eventBean.getPercent());
            //            System.out.println("ssssssssssssssssssssss"+play_seek.getMax()+"   "+eventBean.getPercent());
        }
    }

    private boolean allmusiccomplete;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        //当在该页面下拉通知栏点击暂停的时候这边按钮也要变化
        if (eventBean.getMsg().equals("home_bofang")) {
            iv_pause.setImageResource(R.drawable.bofang_yellow_big);
        } else if (eventBean.getMsg().equals("home_pause")) {
            iv_pause.setImageResource(R.drawable.pause_yellow_big);
        } else if (eventBean.getMsg().equals("allmusiccomplete")) {
            iv_pause.setImageResource(R.drawable.pause_yellow_big);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(MusicTypeBean musicTypeBean) {
        if (musicTypeBean.getMsg().equals("musicplayertype")) {
            if (musicTypeBean == null) {
                iv_bigphoto.setImageResource(R.drawable.home_default_xk);
                tv_title.setText("");
                return;
            }
            //接收到播放主界面要刷新的数据
            mMusicTypeBean = musicTypeBean;
            tv_title.setText(mMusicTypeBean.getVideo_name());
            //用glide会闪烁，而且网上的方法不好使，于是我就使用Picasso就OK了
            //            RequestOptions options=new RequestOptions();
            //            options.centerCrop();
            //            options.dontAnimate();
            //            Glide.with(this).load(mMusicTypeBean.getT_head()).apply(options).into(iv_bigphoto);
            if (!NetWorkUtils.isNetworkConnected(this)) {
                iv_bigphoto.setImageResource(R.drawable.home_default_xk);
            } else {
                Picasso.with(this).load(mMusicTypeBean.getT_head()).into(iv_bigphoto);
            }
            List<BofangHistroyBean> search = HistroyUtils.search();
            if (search != null && search.size() > 0) {
                tv_liebiao.setText(1 + "/" + search.size());
            }
            if (mMyBinder.isPlaying()) {
                iv_pause.setImageResource(R.drawable.bofang_yellow_big);
            } else {
                iv_pause.setImageResource(R.drawable.pause_yellow_big);
            }
            play_seek.setMax(mMyBinder.getProgress());
            String format = time.format(mMyBinder.getProgress());
            if (!format.equals("59:59")) {//oppoA37的bug，老是出现59:59
                music_duration.setText(format);
            } else {
                music_duration.setText("00:00");
            }
            initCollect();
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
    private boolean isChanging;
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
            String format = time.format(mMyBinder.getProgress());
            if (!format.equals("59:59")) {//oppoA37的bug，老是出现59:59
                music_duration.setText(format);
            } else {
                music_duration.setText("00:00");
            }
            mMyBinder.refreshhuanchong();
            play_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //这里很重要，如果不判断是否来自用户操作进度条，会不断执行下面语句块里面的逻辑，然后就会卡顿卡顿
                    if (fromUser) {
                        mMyBinder.seekToPositon(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    isChanging=true;//正在拖动
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    isChanging=false;//结束拖动
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
        mDialog.getView(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mShare_h5_url, mMusicTypeBean.getVideo_name(),
                        mMusicTypeBean.getT_head(), "", MusicActivity.this, SHARE_MEDIA.WEIXIN);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mShare_h5_url, mMusicTypeBean.getVideo_name(),
                        mMusicTypeBean.getT_head(), "", MusicActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mShare_h5_url, mMusicTypeBean.getVideo_name(),
                        mMusicTypeBean.getT_head(), "", MusicActivity.this, SHARE_MEDIA.QZONE);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mShare_h5_url, mMusicTypeBean.getVideo_name(),
                        mMusicTypeBean.getT_head(), "", MusicActivity.this, SHARE_MEDIA.QQ);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(mShare_h5_url, mMusicTypeBean.getVideo_name(),
                        mMusicTypeBean.getT_head(), "", MusicActivity.this, SHARE_MEDIA.SINA);
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void finish() {
        //关闭窗体动画显示
        super.finish();
        this.overridePendingTransition(0, R.anim.bottom_out2);
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
        String title = getIntent().getStringExtra("title");
        tv_title.setText(title);
        if (mMusicTypeBean == null) {
            iv_bigphoto.setImageResource(R.drawable.home_default_xk);
            //            tv_title.setText("");
        } else {
            if (!NetWorkUtils.isNetworkConnected(this)) {
                iv_bigphoto.setImageResource(R.drawable.home_default_xk);
            } else {
                Picasso.with(this).load(mMusicTypeBean.getT_head()).into(iv_bigphoto);
            }
            //            tv_title.setText(mMusicTypeBean.getVideo_name());
        }
        List<BofangHistroyBean> search = HistroyUtils.search();
        if (search != null && search.size() > 0) {
            tv_liebiao.setText(1 + "/" + search.size());
        }
    }

    private void changeCollect() {
        String userid = SpUtils.getString(this, "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
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
                break;
            case R.id.tv_share:
                showShareDialog();
                break;
            case R.id.tv_wengao:
                if (mMusicTypeBean.getType().equals("zhuanlandetail")) {
                    Toast.makeText(this, "此音频暂时无文稿", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!NetWorkUtils.isNetworkConnected(this)) {
                    Intent intent = new Intent(this, WenGaoFileActivity.class);
                    intent.putExtra("type", mMusicTypeBean.getType());
                    intent.putExtra("title", mMusicTypeBean.getVideo_name());
                    intent.putExtra("tname", mMyBinder.getTname());
                    intent.putExtra("mohuchaxun", mMusicTypeBean.getVideo_name());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, WenGaoActivity.class);
                    intent.putExtra("type", mMusicTypeBean.getType());
                    //                intent.putExtra("t_name", mMusicTypeBean.getT_name());
                    //                intent.putExtra("t_head", mMusicTypeBean.getT_head());
                    //                intent.putExtra("video_name", mMusicTypeBean.getVideo_name());
                    intent.putExtra("id", mMusicTypeBean.getId());
                    //                intent.putExtra("teacher_id", mMusicTypeBean.getTeacher_id());
                    startActivity(intent);
                }
                break;
            case R.id.tv_download:
                goDownload();
                break;
            case R.id.tv_collect:
                changeCollect();
                break;
            case R.id.iv_previous:
                mMyBinder.preciousMusic();
                break;
            case R.id.iv_pause:
                if (mMyBinder.isPlaying()) {
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
                break;
            case R.id.iv_next:
                mMyBinder.nextMusic();
                break;
        }
    }

    private BaseDialog mNetDialog;
    private TextView mTv_content;
    private boolean nowifiallowdown;

    private void initNETDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        mNetDialog = builder.setViewId(R.layout.dialog_iswifi)
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
        mTv_content = mNetDialog.getView(R.id.tv_content);
    }

    private void goDownload() {
        String userid = SpUtils.getString(MyApplication.getGloableContext(), "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        int apnType = NetWorkUtils.getAPNType(this);
        if (apnType == 0) {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
            return;
        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
            nowifiallowdown = SpUtils.getBoolean(this, "nowifiallowdown", false);
            if (!nowifiallowdown) {
                mTv_content.setText("当前无WiFi，是否允许用流量下载");
                mNetDialog.show();
                mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNetDialog.dismiss();
                        return;
                    }
                });
                mNetDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//记住用户允许流量下载
                        SpUtils.putBoolean(MusicActivity.this, "nowifiallowdown", true);
                        download();
                    }
                });
            } else {
                download();
            }
        } else {
            download();
        }
    }

    private String getRingDuring(String url) {
        String duration = null;
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        String s = null;
        try {
            if (url != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(url, headers);
            }
            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
            int musicTime = Integer.parseInt(duration) / 1000;
            int xxx = musicTime / 60;
            int yyy = musicTime % 60;
            String zzz = "";
            if (yyy == 0) {
                zzz = "00";
            } else {
                zzz = yyy + "";
            }
            if (xxx <= 9) {
                s = "0" + xxx + ":" + zzz;
            } else {
                s = xxx + ":" + zzz;
            }
        } catch (Exception ex) {
        } finally {
            mmr.release();
        }
        return s + "";
    }

    private void download() {
        mNetDialog.dismiss();
        HttpParams params = new HttpParams();
        params.put("id", mMusicTypeBean.getId());
        String type = mMusicTypeBean.getType();
        if (type.equals("free")) {
            params.put("video_type", "free");
        } else if (type.equals("everydaycomment")) {
            params.put("video_type", "daily");
        } else if (type.equals("softmusicdetail")) {
            params.put("video_type", "xk");
        } else if (type.equals("zhuanlandetail")) {
            params.put("video_type", "zl");
        }

        if (type.equals("free")) {
            OkGo.<DownBean1>post(MyContants.LXKURL + "down")
                    .tag(this)
                    .params(params)
                    .execute(new JsonCallback<DownBean1>(DownBean1.class) {
                                 @Override
                                 public void onSuccess(Response<DownBean1> response) {
                                     int code = response.code();
                                     DownBean1 downBean1 = response.body();
                                     List<DownBean1.DataEntity> data = downBean1.getData();
                                     DownBean1.DataEntity dataEntity = data.get(0);
                                     List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                     DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                     listBean.setTypeId("freeId");
                                     listBean.setChildId(dataEntity.getId() + "");
                                     listBean.setName(dataEntity.getVideo_name());
//                                     listBean.setVideoTime(dataEntity.getVideo_time());
                                     listBean.setVideoTime(getRingDuring(dataEntity.getVideo_url()));
                                     listBean.setDate("");
                                     listBean.setTime(dataEntity.getVideo_time());
                                     listBean.setVideoUrl(dataEntity.getVideo_url());
                                     listBean.setTxtUrl(dataEntity.getTxt_url());
                                     listBean.setIconUrl(dataEntity.getImage());
                                     list.add(listBean);
                                     if (MyUtils.isHaveFile("free", dataEntity.getVideo_name() + listBean.getTypeId() + "_" + dataEntity.getId() + ".mp3")) {
                                         Toast.makeText(MusicActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                     Toast.makeText(MusicActivity.this, "已加入下载列表", Toast.LENGTH_SHORT).show();
                                     DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                             "free", listBean.getTypeId() + "", "", dataEntity.getImage(), "", "", list.size() + "", list);
                                     DownUtil.add(downLoadListsBean);
                                     GetRequest<File> request = OkGo.<File>get(dataEntity.getVideo_url());
                                     OkDownload.request(listBean.getTypeId() + "_" + dataEntity.getId(), request)
                                             .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download")
                                             .fileName(dataEntity.getVideo_name() + listBean.getTypeId() + "_" + dataEntity.getId() + ".mp3")
                                             .extra3(downLoadListsBean)//额外数据
                                             .save()
                                             .register(new LogDownloadListener())//当前任务的回调监听
                                             .start();

                                     OkGo.<File>get(dataEntity.getTxt_url())
                                             .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download"
                                                     , listBean.getTypeId() + "-" + dataEntity.getId() + dataEntity.getVideo_name() + ".txt") {
                                                 @Override
                                                 public void onSuccess(Response<File> response) {
                                                     int code = response.code();
                                                     if (code >= 200 && code <= 204) {
                                                         Logger.e("文稿下载完成");
                                                     }
                                                 }
                                             });
                                     EventBean eventBean = new EventBean("number");
                                     EventBus.getDefault().postSticky(eventBean);
                                 }
                             }
                    );
        } else if (type.equals("everydaycomment")) {
            OkGo.<DownBean1>post(MyContants.LXKURL + "down")
                    .tag(this)
                    .params(params)
                    .execute(new JsonCallback<DownBean1>(DownBean1.class) {
                                 @Override
                                 public void onSuccess(Response<DownBean1> response) {
                                     int code = response.code();
                                     DownBean1 downBean1 = response.body();
                                     List<DownBean1.DataEntity> data = downBean1.getData();
                                     DownBean1.DataEntity dataEntity = data.get(0);
                                     List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                     DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                     listBean.setTypeId("commentId");
                                     listBean.setChildId(dataEntity.getId() + "");
                                     listBean.setName(dataEntity.getVideo_name());
                                     listBean.setVideoTime(dataEntity.getVideo_time());
                                     listBean.setDate("");
//                                     listBean.setTime(dataEntity.getVideo_time());
                                     listBean.setVideoTime(getRingDuring(dataEntity.getVideo_url()));
                                     listBean.setVideoUrl(dataEntity.getVideo_url());
                                     listBean.setTxtUrl(dataEntity.getTxt_url());
                                     listBean.setIconUrl(dataEntity.getImage());
                                     list.add(listBean);
                                     if (MyUtils.isHaveFile("comment", dataEntity.getVideo_name() + listBean.getTypeId() + "_" + dataEntity.getId() + ".mp3")) {
                                         Toast.makeText(MusicActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                     Toast.makeText(MusicActivity.this, "已加入下载列表", Toast.LENGTH_SHORT).show();
                                     DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                             "comment", listBean.getTypeId() + "", "", dataEntity.getImage(), "", "", list.size() + "", list);
                                     DownUtil.add(downLoadListsBean);
                                     GetRequest<File> request = OkGo.<File>get(dataEntity.getVideo_url());
                                     OkDownload.request(listBean.getTypeId() + "_" + dataEntity.getId(), request)
                                             .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download")
                                             .fileName(dataEntity.getVideo_name() + listBean.getTypeId() + "_" + dataEntity.getId() + ".mp3")
                                             .extra3(downLoadListsBean)//额外数据
                                             .save()
                                             .register(new LogDownloadListener())//当前任务的回调监听
                                             .start();

                                     OkGo.<File>get(dataEntity.getTxt_url())
                                             .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download"
                                                     , listBean.getTypeId() + "-" + dataEntity.getId() + dataEntity.getVideo_name() + ".txt") {
                                                 @Override
                                                 public void onSuccess(Response<File> response) {
                                                     int code = response.code();
                                                     if (code >= 200 && code <= 204) {
                                                         Logger.e("文稿下载完成");
                                                     }
                                                 }
                                             });
                                     EventBean eventBean = new EventBean("number");
                                     EventBus.getDefault().postSticky(eventBean);
                                 }
                             }
                    );
        } else if (type.equals("softmusicdetail")) {
            OkGo.<DownBean2>post(MyContants.LXKURL + "down")
                    .tag(this)
                    .params(params)
                    .execute(new JsonCallback<DownBean2>(DownBean2.class) {
                                 @Override
                                 public void onSuccess(Response<DownBean2> response) {
                                     int code = response.code();
                                     DownBean2 downBean2 = response.body();
                                     DownBean2.DataEntity dataEntity = downBean2.getData().get(0);
                                     DownBean2.DataEntity.XkDataEntity xk_data = dataEntity.getXk_data();
                                     List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                     DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                     listBean.setTypeId(dataEntity.getId() + "");
                                     listBean.setChildId(xk_data.getId() + "");
                                     listBean.setName(xk_data.getName());
                                     listBean.setVideoTime(xk_data.getVideo_time());
                                     listBean.setDate("");
                                     listBean.setTime(xk_data.getVideo_time());
                                     listBean.setVideoUrl(xk_data.getVideo_url());
                                     listBean.setTxtUrl(xk_data.getTxt_url());
                                     listBean.setIconUrl(dataEntity.getXk_image());
                                     listBean.settName(xk_data.getT_name());
                                     list.add(listBean);
                                     if (MyUtils.isHaveFile("xiaoke", xk_data.getName() + dataEntity.getId() + "_" + xk_data.getId() + ".mp3")) {
                                         Toast.makeText(MusicActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                     Toast.makeText(MusicActivity.this, "已加入下载列表", Toast.LENGTH_SHORT).show();
                                     DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                             "xiaoke", dataEntity.getId() + "", dataEntity.getXk_name(), dataEntity.getXk_image(),
                                             dataEntity.getT_name(), dataEntity.getXk_teacher_tags(), "1", list);
                                     DownUtil.add(downLoadListsBean);
                                     GetRequest<File> request = OkGo.<File>get(xk_data.getVideo_url());
                                     OkDownload.request(dataEntity.getId() + "_" + xk_data.getId(), request)
                                             .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download")
                                             .fileName(xk_data.getName() + dataEntity.getId() + "_" + xk_data.getId() + ".mp3")
                                             .extra3(downLoadListsBean)
                                             .save()
                                             .register(new LogDownloadListener())//当前任务的回调监听
                                             .start();
                                     OkGo.<File>get(xk_data.getTxt_url())
                                             .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download"
                                                     , dataEntity.getId() + "-" + xk_data.getId() + xk_data.getName() + ".txt") {
                                                 @Override
                                                 public void onSuccess(Response<File> response) {
                                                     int code = response.code();
                                                     if (code >= 200 && code <= 204) {
                                                         Logger.e("文稿下载完成");
                                                     }
                                                 }
                                             });
                                     EventBean eventBean = new EventBean("number");
                                     EventBus.getDefault().postSticky(eventBean);
                                 }
                             }
                    );
        } else if (type.equals("zhuanlandetail")) {
            OkGo.<DownBean3>post(MyContants.LXKURL + "down")
                    .tag(this)
                    .params(params)
                    .execute(new JsonCallback<DownBean3>(DownBean3.class) {
                                 @Override
                                 public void onSuccess(Response<DownBean3> response) {
                                     int code = response.code();
                                     DownBean3 downBean3 = response.body();
                                     DownBean3.DataEntity dataEntity = downBean3.getData().get(0);
                                     DownBean3.DataEntity.ZlDataEntity zl_data = dataEntity.getZl_data();
                                     List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                     DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                     listBean.setTypeId(dataEntity.getId() + "");
                                     listBean.setChildId(zl_data.getId() + "");
                                     listBean.setName(zl_data.getName());
                                     listBean.setVideoTime(zl_data.getVideo_time());
                                     listBean.setDate("");
                                     listBean.setTime(zl_data.getVideo_time());
                                     listBean.setVideoUrl(zl_data.getVideo_url());
                                     listBean.setTxtUrl(zl_data.getTxt_url());
                                     listBean.setIconUrl(dataEntity.getZl_image());
                                     listBean.settName(zl_data.getT_name());
                                     list.add(listBean);
                                     if (MyUtils.isHaveFile("zhuanlan", zl_data.getName() + dataEntity.getId() + "_" + zl_data.getId() + ".mp3")) {
                                         Toast.makeText(MusicActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                     Toast.makeText(MusicActivity.this, "已加入下载列表", Toast.LENGTH_SHORT).show();
                                     DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                             "zhuanlan", dataEntity.getId() + "", getIntent().getStringExtra("title"),
                                             dataEntity.getZl_image(),
                                             zl_data.getT_name(), dataEntity.getZl_teacher_tags(), "1", list);
                                     DownUtil.add(downLoadListsBean);
                                     GetRequest<File> request = OkGo.<File>get(zl_data.getVideo_url());
                                     OkDownload.request(dataEntity.getId() + "_" + zl_data.getId(), request)
                                             .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download")
                                             .fileName(zl_data.getName() + dataEntity.getId() + "_" + zl_data.getId() + ".mp3")
                                             .extra3(downLoadListsBean)
                                             .save()
                                             .register(new LogDownloadListener())//当前任务的回调监听
                                             .start();
                                     EventBean eventBean = new EventBean("number");
                                     EventBus.getDefault().postSticky(eventBean);
                                 }
                             }
                    );
        }
    }

}
