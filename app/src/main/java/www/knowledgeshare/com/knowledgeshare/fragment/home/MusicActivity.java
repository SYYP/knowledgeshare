package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

public class MusicActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        UltimateBar ultimateBar = new UltimateBar(this);
        //        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_music);
        initView();
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
        iv_pause = (ImageView) findViewById(R.id.iv_pause);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        tv_liebiao = (TextView) findViewById(R.id.tv_liebiao);
        tv_wengao = (TextView) findViewById(R.id.tv_wengao);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        activity_music = (LinearLayout) findViewById(R.id.activity_music);
        play_seek.setProgress(0);
        play_seek.setMax(100);
        play_seek.setOnSeekBarChangeListener(this);
        //模拟播放
        new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    currentProgress++;
                    if (currentProgress > 100)
                        break;
                    SystemClock.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            play_seek.setProgress(currentProgress);
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
