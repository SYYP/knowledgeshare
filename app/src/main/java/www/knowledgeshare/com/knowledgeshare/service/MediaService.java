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
import android.text.TextUtils;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

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
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.HistroyUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HuanChongBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.Actions;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.AudioFocusManager;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.NoisyAudioStreamReceiver;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.Notifier;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;

public class MediaService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

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
    private static List<PlayerBean> musicList = new ArrayList<>();//传进来的音频列表
    private static List<MusicTypeBean> musicTypeBeanList = new ArrayList<>();//传进来的主界面播放数据的list
    private static List<BofangHistroyBean> bofangHistroyBeanList = new ArrayList<>();//传进来的播放列表的历史封装list
    private static int currPosition = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Notifier.init(this);
        mAudioFocusManager = new AudioFocusManager(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnCompletionListener(this);
    }

    /*
    * 我这边因为一开始没有考虑到音频列表的问题，所以在有播放的界面都是先播放一首，传进来一个playerBean,
    * 然后再传进来List<PlayerBean>也就是音频列表，然后设置当前位置为0，这种处理也是可以的
    * */
    public static void insertMusicList(List<PlayerBean> musiclist) {
        musicList = musiclist;
    }

    /*
    * 播放列表的主界面的数据的list，当播放下一首上一首的时候主界面的数据也要变化，所以要传进来
    * */
    public static void insertMusicTypeList(List<MusicTypeBean> beanList) {
        musicTypeBeanList = beanList;
    }

    /*
    * 传进来的播放列表的历史封装list，当播放上一首下一首的时候播放历史也要增加
    * */
    public static void insertBoFangHistroyList(List<BofangHistroyBean> beanList) {
        bofangHistroyBeanList = beanList;
    }

    /*
    * 随身听用的
    * */
    public static void insertMusicList2(List<PlayerBean> musiclist) {
        musicList.addAll(musiclist);
    }

    /*
    * 随身听用的
    * */
    public static void insertMusicTypeList2(List<MusicTypeBean> beanList) {
        musicTypeBeanList.addAll(beanList);
    }

    /*
    * 随身听用的
    * */
    public static void insertBoFangHistroyList2(List<BofangHistroyBean> beanList) {
        bofangHistroyBeanList.addAll(beanList);
    }

    /*
    * 手机上的通知栏上面的按钮通过广播接收者来控制，接收到广播后就调用这个方法，
    * 调用了这个方法自然就会调用onStartCommand()这个方法，根据action就可以判断应该进行什么操作
    * */
    public static void startCommand(Context context, String action) {
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(action);
        context.startService(intent);
    }

    private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            isPrepared = true;
            //从记忆播放的数据库中取出来继续播放
            Logger.e("xxxxxxxxxx" + "准备完成");
            BofangHistroyBean bofangHistroyBean = bofangHistroyBeanList.get(currPosition);
            int oneDuration = HistroyUtils.getOneDuration(bofangHistroyBean.getType(), bofangHistroyBean.getVideo_name());
            if (oneDuration != 0) {
                Logger.e("xxxxxxxxxx取出" + bofangHistroyBean.getVideo_name() + "历史：" + oneDuration);
                try {
                    mBinder.seekToPositon(oneDuration);
                } catch (Exception e) {
                    Logger.e("xxxxxxxxxx" + "捕捉到异常");
                    e.printStackTrace();
                }
            }
            start();
        }
    };

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (currPosition == musicList.size() - 1) {
            //全部播放完成
            EventBus.getDefault().postSticky(new EventBean("allmusiccomplete"));
            return;
        }
         /*
        此方法的回调在很多情况下会被回调，例如
        1.当播放完成的时候会被回调
        2.当mMediaPlayer.setDataSource()方法没有调用或者还没有完成，就使用了 mMediaPlayer.getDuration()、
        mMediaPlayer.seekto()等方法的时候，总之大部分不正确的使用都会回调onCompletion此方法，出现了这种情况就会走播放下一首的逻辑
         */
        Logger.e("xxxxxxxxxx" + "下一首！！");
        Logger.e("xxxxxxxxxx" + mPlayerBean.getTitle() + "  " + mBinder.getPlayPosition() + "::" + mBinder.getProgress());
        mBinder.nextMusic();
    }

    /*
    * 当你调用了mediaplayer的reset方法后，会出现播放异常的问题，要实现MediaPlayer的onError回调，然后返回值设置成true
    * */
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        //        很诡异的bug
        Logger.e("xxxxxxxxxx" + "播放错误");
        return true;//要设置为true
    }

    private void start() {
        if (mAudioFocusManager.requestAudioFocus()) {
            mMediaPlayer.start();
            EventBean eventBean = new EventBean("rotate");
            EventBus.getDefault().postSticky(eventBean);
            EventBean eventBean2 = new EventBean("home_bofang");
            EventBus.getDefault().postSticky(eventBean2);
            registerReceiver(mNoisyReceiver, mNoisyFilter);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            isClosed = false;
            Notifier.showPlay(mPlayerBean);
            if (musicTypeBeanList != null && musicTypeBeanList.size() > 0) {
                MusicTypeBean musicTypeBean = musicTypeBeanList.get(currPosition);
                EventBus.getDefault().postSticky(musicTypeBean);//播放上一首下一首的时候刷新播放主界面的数据
            }
            //播放下一首上一首的时候增加播放历史数据库中的数据
            if (bofangHistroyBeanList != null && bofangHistroyBeanList.size() > 0) {
                BofangHistroyBean bofangHistroyBean = bofangHistroyBeanList.get(currPosition);
                if (!HistroyUtils.isInserted(bofangHistroyBean.getVideo_name())) {
                    bofangHistroyBean.setTime(System.currentTimeMillis());
                    HistroyUtils.add(bofangHistroyBean);
                } else {
                    HistroyUtils.updateTime(System.currentTimeMillis(), bofangHistroyBean.getVideo_name());
                }
            }
        }
    }

    private void start2() {
        if (mAudioFocusManager.requestAudioFocus()) {
            mMediaPlayer.start();
            registerReceiver(mNoisyReceiver, mNoisyFilter);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            isClosed = false;
            Notifier.showPlay(mPlayerBean);
            if (musicTypeBeanList != null && musicTypeBeanList.size() > 0) {
                MusicTypeBean musicTypeBean = musicTypeBeanList.get(currPosition);
                EventBus.getDefault().postSticky(musicTypeBean);//播放上一首下一首的时候刷新播放主界面的数据
            }
            //播放下一首上一首的时候增加播放历史数据库中的数据
            if (bofangHistroyBeanList != null && bofangHistroyBeanList.size() > 0) {
                BofangHistroyBean bofangHistroyBean = bofangHistroyBeanList.get(currPosition);
                if (!HistroyUtils.isInserted(bofangHistroyBean.getVideo_name())) {
                    bofangHistroyBean.setTime(System.currentTimeMillis());
                    HistroyUtils.add(bofangHistroyBean);
                } else {
                    HistroyUtils.updateTime(System.currentTimeMillis(), bofangHistroyBean.getVideo_name());
                }
            }
        }
    }

    private void playLocal(String localPath) {
        if (mAudioFocusManager.requestAudioFocus()) {
            try {
                try {
                    if (mPlayerBean != null && !mPlayerBean.getLocalPath().equals(localPath)) {//点击了另外的音频就保存之前的
                        //保存上一首的记忆播放位置,注意要在MediaPlayer.release()之前
                        BofangHistroyBean bofangHistroyBean = mBinder.getBofangHistroyBean();
                        if (bofangHistroyBean != null) {
                            HistroyUtils.setOneDuration(bofangHistroyBean);
                            Logger.e("xxxxxxxxxx保存" + bofangHistroyBean.getVideo_name() + "：" + bofangHistroyBean.getDuration());
                        }
                    }
                    mMediaPlayer.setDataSource(localPath);
                    mMediaPlayer.prepare();//好像是本地播放不能用异步准备
                    mMediaPlayer.setOnPreparedListener(mPreparedListener);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    //                    mMediaPlayer = null;
                    //                    mMediaPlayer = new MediaPlayer();
                    try {
                        mMediaPlayer.reset();
                        mMediaPlayer.setDataSource(localPath);
                        mMediaPlayer.prepare();
                        mMediaPlayer.setOnPreparedListener(mPreparedListener);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int mPercent;
    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            mPercent = percent;//通过eventbus来向播放主界面发送缓冲的百分比
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
                    mBinder.nextMusic();
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
            EventBean eventBean2 = new EventBean("home_pause");
            EventBus.getDefault().postSticky(eventBean2);
        } else {
            if (isClosed) {
                if (TextUtils.isEmpty(mPlayerBean.getVideo_url())) {//播放本地的
                    mBinder.setMusicLocal(mPlayerBean);
                } else {
                    mBinder.setMusicUrl(mMusicUrl);
                    mBinder.playMusic(mPlayerBean);
                }
            } else {
                start();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
        mAudioFocusManager.abandonAudioFocus();
        isPrepared = false;
        isPlaying = false;
        isClosed = true;
        Notifier.cancelAll();
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

        public String getPlayingUrl() {
            return mMusicUrl;
        }

        public String getTname() {
            if (bofangHistroyBeanList == null || bofangHistroyBeanList.size() == 0) {
                return "";
            }
            return bofangHistroyBeanList.get(currPosition).getT_tag();
        }

        /**
         * 播放音乐
         */
        public void playMusic(final PlayerBean playerBean) {
            if (playerBean == null) {
                return;
            }
            mPlayerBean = playerBean;
            if (mPlayerBean.getPosition() != 0) {
                //因为有的构造器是不传position的，当播放下一首的时候会再走一遍会变成0
                currPosition = mPlayerBean.getPosition();
            } else {
                currPosition = 0;
            }
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
                    //                    mMediaPlayer.prepareAsync();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            mMediaPlayer.setOnPreparedListener(mPreparedListener);//好像是暂停之后再走这个方法就不会执行了
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
                            mPlayerBean.setBitmap(result);
                            Notifier.showPlay(mPlayerBean);
                        }
                    }
                }.execute(playerBean.getTeacher_head());
            } else {
                if (!mBinder.isPlaying()) {
                    start();
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
                    //                    mMediaPlayer.prepare();
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
            isPlaying = mBinder.isPlaying();
            if (isPlaying) {
                //如果正在播放，就暂停
                mMediaPlayer.pause();
                Notifier.showPause(mPlayerBean);
                unregisterReceiver(mNoisyReceiver);
                isClosed = false;
            }
        }

        /*
        * mainActivity的播放按钮要判断是不是关闭掉了播放器，来决定是开启默认播放还是继续播放
        * 同样的专栏详情的播放界面也是这样
        * */
        public boolean isClosed() {
            return isClosed;
        }

        /**
         * 关闭播放器
         */
        public void closeMedia() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                /*
                * 网络音频有这行没关系，每次都会走prepare的监听，但是本地播放就不一样了，
                * 本地播放在调用了这个方法后，再点击播放就不会再走prepare的监听了，我怀疑是不是异步准备和同步准备有区别的问题
                * */
                //                mMediaPlayer.release();
                isPlaying = false;
                isPrepared = false;
                isClosed = true;
                Notifier.showPause(mPlayerBean);
                currPosition = 0;
                //                mMediaPlayer = null;
            }
        }

        private int change = 0;

        /**
         * 下一首
         */
        public void nextMusic() {
            if (mMediaPlayer != null) {
                //切换歌曲reset()很重要很重要很重要，没有会报IllegalStateException
                if (currPosition >= musicList.size() - 1) {
                    Toast.makeText(MyApplication.getGloableContext(), "已是最后一首", Toast.LENGTH_SHORT).show();
                } else {
                    currPosition++;
                    change = 1;
                    //                    mMediaPlayer.reset();
                    //当播放本地音频的时候我没传url，就通过这个来区分
                    if (TextUtils.isEmpty(musicList.get(currPosition).getVideo_url())) {
                        playLocal(musicList.get(currPosition).getLocalPath());
                    } else {
                        //当播放下一首的时候小型播放器的数据和播放主界面都需要变
                        PlayerBean playerBean = musicList.get(currPosition);
                        playerBean.setPosition(currPosition);
                        playerBean.setMsg("refreshplayer");
                        EventBus.getDefault().postSticky(playerBean);
                        mBinder.setMusicUrl(playerBean.getVideo_url());
                        mBinder.playMusic(playerBean);
                    }
                }
            }
        }

        /**
         * 上一首
         */
        public void preciousMusic() {
            if (mMediaPlayer != null) {
                if (currPosition <= 0) {
                    Toast.makeText(MyApplication.getGloableContext(), "已是第一首", Toast.LENGTH_SHORT).show();
                } else {
                    currPosition--;
                    change = -1;
                    //                    mMediaPlayer.reset();
                    if (TextUtils.isEmpty(musicList.get(currPosition).getVideo_url())) {
                        playLocal(musicList.get(currPosition).getLocalPath());
                    } else {
                        //当播放下一首的时候小型播放器的数据和播放主界面都需要变
                        PlayerBean playerBean = musicList.get(currPosition);
                        playerBean.setPosition(currPosition);
                        playerBean.setMsg("refreshplayer");
                        EventBus.getDefault().postSticky(playerBean);
                        mBinder.setMusicUrl(playerBean.getVideo_url());
                        mBinder.playMusic(playerBean);
                    }
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
                    return mMediaPlayer.getCurrentPosition();
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
         * 准备播放音频
         */
        public void setMusicUrl(String musicUrl) {
            if (mMusicUrl.equals(musicUrl) && isPlaying()) {
                //                Toast.makeText(MyApplication.getGloableContext(), "音频正在播放中", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!mMusicUrl.equals(musicUrl)) {//点击了另外的音频就保存之前的
                //保存记忆播放位置,注意要在MediaPlayer.release()之前
                BofangHistroyBean bofangHistroyBean = mBinder.getBofangHistroyBean();
                if (bofangHistroyBean != null) {
                    HistroyUtils.setOneDuration(bofangHistroyBean);
                    Logger.e("xxxxxxxxxx保存" + bofangHistroyBean.getVideo_name() + "：" + bofangHistroyBean.getDuration());
                }
            }
            mMusicUrl = musicUrl;
            try {
                //此处的两个方法需要捕获IO异常
                //            Uri uri = Uri.parse("android.resource://" + MyApplication.getGloableContext().getPackageName() + "/" + R.raw.music1);
                mMediaPlayer.reset();
                //设置音频文件到MediaPlayer对象中
                mMediaPlayer.setDataSource(mMusicUrl);
                //让MediaPlayer对象准备
                //                mMediaPlayer.prepare();卡顿
                mMediaPlayer.prepareAsync();
            } catch (IllegalStateException e) {
                Logger.e("xxxxxxxxxx" + "setDataSourceError");
                mMediaPlayer = null;
                mMediaPlayer = new MediaPlayer();
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(mMusicUrl);
                    //                    mMediaPlayer.prepare();卡顿
                    mMediaPlayer.prepareAsync();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getMusicType() {
            if (musicTypeBeanList == null || musicTypeBeanList.size() == 0) {
                return "";
            }
            return musicTypeBeanList.get(currPosition).getType();
        }

        public BofangHistroyBean getBofangHistroyBean() {
            if (bofangHistroyBeanList == null || bofangHistroyBeanList.size() == 0) {
                return null;
            }
            BofangHistroyBean bofangHistroyBean = null;
            if (change == 1) {
                bofangHistroyBean = bofangHistroyBeanList.get(currPosition - 1);
            } else if (change == -1) {
                bofangHistroyBean = bofangHistroyBeanList.get(currPosition + 1);
            } else {
                bofangHistroyBean = bofangHistroyBeanList.get(currPosition);
            }
            change = 0;
            //            Toast.makeText(MyApplication.getGloableContext(), "xxx" + getPlayPosition(), Toast.LENGTH_SHORT).show();
            bofangHistroyBean.setDuration(getPlayPosition());
            return bofangHistroyBean;
        }

        public void setMusicLocal(PlayerBean playerBean) {
            try {
                if (mPlayerBean != null && mPlayerBean.getTitle().equals(playerBean.getTitle()) && isPlaying()) {
                    return;
                }
                if (mPlayerBean != null && !mPlayerBean.getTitle().equals(playerBean.getTitle())) {//点击了另外的音频就保存之前的
                    //保存上一首的记忆播放位置,注意要在MediaPlayer.release()之前
                    BofangHistroyBean bofangHistroyBean = mBinder.getBofangHistroyBean();
                    if (bofangHistroyBean != null) {
                        HistroyUtils.setOneDuration(bofangHistroyBean);
                        Logger.e("xxxxxxxxxx保存" + bofangHistroyBean.getVideo_name() + "：" + bofangHistroyBean.getDuration());
                    }
                }
                mMusicUrl = "";
                mPlayerBean = playerBean;
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(playerBean.getLocalPath());
                //                FileInputStream fis = new FileInputStream(new File(playerBean.getLocalPath()));这种也行
                //                mMediaPlayer.setDataSource(fis.getFD());
                //让MediaPlayer对象准备
                mMediaPlayer.prepare();
                mMediaPlayer.setOnPreparedListener(mPreparedListener);
                if (mPlayerBean.getPosition() != 0) {
                    //因为有的构造器是不传position的，当播放下一首的时候会再走一遍会变成0
                    currPosition = mPlayerBean.getPosition();
                } else {
                    currPosition = 0;
                }
            } catch (IllegalStateException e) {
                mMediaPlayer = null;
                mMediaPlayer = new MediaPlayer();
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(playerBean.getLocalPath());
                    mMediaPlayer.prepare();
                    mPlayerBean = playerBean;
                    if (mPlayerBean.getPosition() != 0) {
                        //因为有的构造器是不传position的，当播放下一首的时候会再走一遍会变成0
                        currPosition = mPlayerBean.getPosition();
                    } else {
                        currPosition = 0;
                    }
                    mMediaPlayer.setOnPreparedListener(mPreparedListener);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void refreshhuanchong() {
            /*
            * 这个方法在刚进入
            * */
            EventBus.getDefault().postSticky(new HuanChongBean("huanchong", mPercent));
        }
    }

}
