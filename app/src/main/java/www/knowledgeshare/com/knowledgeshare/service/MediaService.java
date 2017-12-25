package www.knowledgeshare.com.knowledgeshare.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import www.knowledgeshare.com.knowledgeshare.fragment.home.player.Actions;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.Notifier;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;

public class MediaService extends Service {

    private MyBinder mBinder = new MyBinder();

    //初始化MediaPlayer
    public MediaPlayer mMediaPlayer = new MediaPlayer();
    private boolean isPlaying = false;
    private boolean isPrepared;
    private static String mMusicUrl = "";
    private PlayerBean mPlayerBean;

    @Override
    public void onCreate() {
        super.onCreate();
        Notifier.init(this);
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
            mMediaPlayer.start();
        }
    };

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
        } else {
            mBinder.playMusic();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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
                        mMediaPlayer.setOnPreparedListener(mPreparedListener);
                        playerBean.setBitmap(result);
                        Notifier.showPlay(playerBean);
                    }
                }
            }.execute(playerBean.getTeacher_head());
        }

        /**
         * 播放音乐
         */
        public void playMusic() {
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
                //让MediaPlayer对象准备
                isPlaying = mMediaPlayer.isPlaying();
            }
            if (!isPlaying) {
                //如果还没开始播放，就开始
                mMediaPlayer.start();
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
            }
        }

        /**
         * reset
         */
        public void resetMusic() {
            isPlaying = mMediaPlayer.isPlaying();
            if (!isPlaying) {
                mMediaPlayer.reset();
            }
        }

        /**
         * 关闭播放器
         */
        public void closeMedia() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                isPlaying = false;
                //                mMediaPlayer = null;
            }
        }

        /**
         * 下一首
         */
        public void nextMusic() {
            if (mMediaPlayer != null) {
                //切换歌曲reset()很重要很重要很重要，没有会报IllegalStateException
                mMediaPlayer.reset();
                //                playMusic();
            }
        }

        /**
         * 上一首
         */
        public void preciousMusic() {
            if (mMediaPlayer != null) {
                mMediaPlayer.reset();
                //                playMusic();
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
                //                Toast.makeText(MyApplication.getGloableContext(), "异常", Toast.LENGTH_SHORT).show();
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
    }

}
