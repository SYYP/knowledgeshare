package www.knowledgeshare.com.knowledgeshare.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HuanChongBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.Actions;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.AudioFocusManager;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.NoisyAudioStreamReceiver;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.Notifier;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

public class MediaService extends Service implements MediaPlayer.OnCompletionListener {

    private MyBinder mBinder = new MyBinder();

    //初始化MediaPlayer
    public MediaPlayer mMediaPlayer = new MediaPlayer();
    private boolean isPlaying;
    private boolean isPrepared;
    private boolean isClosed = true;
    private static String mMusicUrl = "";
    private PlayerBean mPlayerBean;
    private AudioFocusManager mAudioFocusManager;
    private final NoisyAudioStreamReceiver mNoisyReceiver = new NoisyAudioStreamReceiver();
    private final IntentFilter mNoisyFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    private static List<PlayerBean> musicList = new ArrayList<>();
    private int currPosition;

    @Override
    public void onCreate() {
        super.onCreate();
        Notifier.init(this);
        mAudioFocusManager = new AudioFocusManager(this);
        mMediaPlayer.setOnCompletionListener(this);
    }

    public static void insertMusicList(List<PlayerBean> musiclist) {
        musicList = musiclist;
    }

    public static void startCommand(Context context, String action) {
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(action);
        context.startService(intent);
    }

    private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            isPrepared = true;
            start();
        }
    };

    private void start() {
        if (mAudioFocusManager.requestAudioFocus()) {
            mMediaPlayer.start();
            EventBean eventBean = new EventBean("rotate");
            EventBus.getDefault().postSticky(eventBean);
            EventBean eventBean2 = new EventBean("home_bofang");
            EventBus.getDefault().postSticky(eventBean2);
            registerReceiver(mNoisyReceiver, mNoisyFilter);
            EventBus.getDefault().postSticky(new EventBean("isplaying"));
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            SpUtils.putString(MyApplication.getGloableContext(), "musicurl", mMusicUrl);
            SpUtils.putString(MyApplication.getGloableContext(), "title", mPlayerBean.getTitle());
            SpUtils.putString(MyApplication.getGloableContext(), "subtitle", mPlayerBean.getSubtitle());
            SpUtils.putString(MyApplication.getGloableContext(), "t_head", mPlayerBean.getTeacher_head());
            isClosed = false;
        }
    }

    private void start2() {
        if (mAudioFocusManager.requestAudioFocus()) {
            mMediaPlayer.start();
            registerReceiver(mNoisyReceiver, mNoisyFilter);
            EventBus.getDefault().postSticky(new EventBean("isplaying"));
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            SpUtils.putString(MyApplication.getGloableContext(), "musicurl", mMusicUrl);
            SpUtils.putString(MyApplication.getGloableContext(), "title", mPlayerBean.getTitle());
            SpUtils.putString(MyApplication.getGloableContext(), "subtitle", mPlayerBean.getSubtitle());
            SpUtils.putString(MyApplication.getGloableContext(), "t_head", mPlayerBean.getTeacher_head());
            isClosed = false;
        }
    }


    private int mPercent;
    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            mPercent = percent;
            EventBus.getDefault().postSticky(new HuanChongBean("huanchong", percent));
        }
    };

    public boolean isPlaying() {
        return mBinder.isPlaying();
    }

    public boolean isPreparing() {
        return isPrepared;
    }

    public void stop() {
        pause();
        mMediaPlayer.reset();
    }

    public void pause() {
        mBinder.pauseMusic();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case Actions.ACTION_MEDIA_PLAY_PAUSE:
                    playPause();
                    break;
                case Actions.ACTION_MEDIA_NEXT:
                    //                    next();
                    break;
                case Actions.ACTION_MEDIA_PREVIOUS:
                    //                    prev();
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    public void playPause() {
        if (mBinder.isPlaying()) {
            mBinder.pauseMusic();
            EventBean eventBean = new EventBean("main_pause");
            EventBus.getDefault().postSticky(eventBean);
        } else {
            mBinder.playMusic(mPlayerBean);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
        mAudioFocusManager.abandonAudioFocus();
        Notifier.cancelAll();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mBinder.nextMusic();
    }

    public class MyBinder extends Binder {

        // 获取MediaService.this（方便在ServiceConnection中）
        public MediaService getInstance() {
            return MediaService.this;
        }

        /**
         * 播放音乐
         */
        public void playMusic(final PlayerBean playerBean) {
            if (playerBean == null) {
                return;
            }
            mPlayerBean = playerBean;
            //            RequestBuilder<Bitmap> load = Glide.with(MyApplication.getGloableContext()).asBitmap().load(playerBean.getTeacher_head());

            // 为解决第二次播放时抛出的IllegalStateException，这里做了try-catch处理
            try {
                isPlaying = mMediaPlayer.isPlaying();
            } catch (IllegalStateException e) {
                //                Toast.makeText(MyApplication.getGloableContext(), "异常", Toast.LENGTH_SHORT).show();
                mMediaPlayer = null;
                mMediaPlayer = new MediaPlayer();
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(mMusicUrl);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            if (mPlayerBean.getBitmap() == null) {
                //如果还没开始播放，就开始
                new AsyncTask<String, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(String... params) {
                        try {
                            URL url = new URL(params[0]);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setConnectTimeout(6000);//设置超时
                            conn.setDoInput(true);
                            conn.setUseCaches(false);//不缓存
                            conn.connect();
                            int code = conn.getResponseCode();
                            Bitmap bitmap = null;
                            if (code == 200) {
                                InputStream is = conn.getInputStream();//获得图片的数据流
                                bitmap = BitmapFactory.decodeStream(is);
                            }
                            return bitmap;
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            return null;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Bitmap result) {
                        super.onPostExecute(result);
                        if (result != null) {
                            //                            mMediaPlayer.setOnPreparedListener(mPreparedListener);
                            mPlayerBean.setBitmap(result);
                            Notifier.showPlay(mPlayerBean);
                        }
                    }
                }.execute(playerBean.getTeacher_head());
            } else {
                if (!mBinder.isPlaying()) {
                    Notifier.showPlay(mPlayerBean);
                    EventBean eventBean = new EventBean("rotate");
                    EventBus.getDefault().postSticky(eventBean);
                }
            }
        }

        /**
         * 播放音乐
         */
        public void playMusic() {
            // 为解决第二次播放时抛出的IllegalStateException，这里做了try-catch处理
            try {
                isPlaying = mMediaPlayer.isPlaying();
            } catch (IllegalStateException e) {
                mMediaPlayer = null;
                mMediaPlayer = new MediaPlayer();
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(mMusicUrl);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //让MediaPlayer对象准备
                isPlaying = mMediaPlayer.isPlaying();
            }
            if (!isPlaying) {
                //如果还没开始播放，就开始
                start2();
                Notifier.showPlay(mPlayerBean);
            }
        }

        public boolean isPlaying() {
            try {
                isPlaying = mMediaPlayer.isPlaying();
            } catch (IllegalStateException e) {
                //                Toast.makeText(MyApplication.getGloableContext(), "异常", Toast.LENGTH_SHORT).show();
                mMediaPlayer = null;
                mMediaPlayer = new MediaPlayer();
            }
            return mMediaPlayer.isPlaying();
        }

        /**
         * 暂停播放
         */
        public void pauseMusic() {
            isPlaying = mMediaPlayer.isPlaying();
            if (isPlaying) {
                //如果正在播放，就暂停
                mMediaPlayer.pause();
                Notifier.showPause(mPlayerBean);
                unregisterReceiver(mNoisyReceiver);
                isClosed = false;
            }
        }

        public boolean isClosed() {
            return isClosed;
        }

        /**
         * 关闭播放器
         */
        public void closeMedia() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                isPlaying = false;
                isClosed = true;
                //                mMediaPlayer = null;
            }
        }

        /**
         * 下一首
         */
        public void nextMusic() {
            if (mMediaPlayer != null) {
                //切换歌曲reset()很重要很重要很重要，没有会报IllegalStateException
                if (currPosition == musicList.size() - 1) {
                    Toast.makeText(MyApplication.getGloableContext(), "已是最后一首", Toast.LENGTH_SHORT).show();
                } else {
                    currPosition++;
                    mMediaPlayer.reset();
                    mBinder.setMusicUrl(musicList.get(currPosition).getVideo_url());
                    mBinder.playMusic(musicList.get(currPosition));
                }
            }
        }

        /**
         * 上一首
         */
        public void preciousMusic() {
            if (mMediaPlayer != null) {
                if (currPosition == 0) {
                    Toast.makeText(MyApplication.getGloableContext(), "已是第一首", Toast.LENGTH_SHORT).show();
                } else {
                    currPosition--;
                    mMediaPlayer.reset();
                    mBinder.setMusicUrl(musicList.get(currPosition).getVideo_url());
                    mBinder.playMusic(musicList.get(currPosition));
                }
            }
        }

        /**
         * 获取歌曲长度
         **/
        public int getProgress() {
            return mMediaPlayer.getDuration();
        }

        /**
         * 获取播放位置
         */
        public int getPlayPosition() {
            if (mMediaPlayer != null) {
                try {
                    if (isPrepared) {
                        return mMediaPlayer.getCurrentPosition();
                    } else {
                        return 0;
                    }
                } catch (IllegalStateException e) {
                    return 0;
                }
            } else {
                return 0;
            }
        }

        /**
         * 播放指定位置
         */
        public void seekToPositon(int msec) {
            mMediaPlayer.seekTo(msec);
        }

        /**
         * 添加file文件到MediaPlayer对象并且准备播放音频
         */
        public void setMusicUrl(String musicUrl) {
            if (mMusicUrl.equals(musicUrl) && isPlaying()) {
                Toast.makeText(MyApplication.getGloableContext(), "音频正在播放中", Toast.LENGTH_SHORT).show();
                return;
            }
            mMusicUrl = musicUrl;
            try {
                //此处的两个方法需要捕获IO异常
                //设置音频文件到MediaPlayer对象中
                //            Uri uri = Uri.parse("android.resource://" + MyApplication.getGloableContext().getPackageName() + "/" + R.raw.music1);
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(mMusicUrl);
                //让MediaPlayer对象准备
                mMediaPlayer.prepareAsync();
            } catch (IllegalStateException e) {
                mMediaPlayer = null;
                mMediaPlayer = new MediaPlayer();
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(mMusicUrl);
                    mMediaPlayer.prepareAsync();
                    //                    mMediaPlayer.prepare();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                //            Log.d(TAG, "设置资源，准备阶段出错");
                e.printStackTrace();
            }
        }

        public void refreshhuanchong() {
            EventBus.getDefault().postSticky(new HuanChongBean("huanchong", mPercent));
        }
    }

}
