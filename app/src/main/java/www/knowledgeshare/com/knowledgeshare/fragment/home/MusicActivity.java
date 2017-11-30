package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;

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
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;
    private boolean isCollected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        UltimateBar ultimateBar = new UltimateBar(this);
        //        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_music);
        initView();
        initDialog();
    }

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
            case R.id.tv_liebiao:
                Intent intent11 = new Intent(this, BoFangListActivity.class);
                startActivity(intent11);
                this.overridePendingTransition(R.anim.bottom_in, 0);
                break;
            case R.id.tv_share:
                showShareDialog();
                break;
            case R.id.tv_wengao:
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
                isCollected=!isCollected;
                break;
            case R.id.iv_previous:
                break;
            case R.id.iv_pause:
                break;
            case R.id.iv_next:
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
