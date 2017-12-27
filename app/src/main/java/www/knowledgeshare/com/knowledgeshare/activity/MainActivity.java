package www.knowledgeshare.com.knowledgeshare.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.BuyFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.home.HomeFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.home.WebActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.MineFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.study.StudyFragment;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout fl_content;
    private BaseFragment[] mBaseFragments;
    private ImageView iv_home;
    private TextView tv_home;
    private LinearLayout ll_home;
    private ImageView iv_study;
    private TextView tv_study;
    private LinearLayout ll_study;
    private ImageView iv_listen;
    private LinearLayout ll_listen;
    private ImageView iv_buy;
    private TextView tv_buy;
    private LinearLayout ll_buy;
    private ImageView iv_mine;
    private TextView tv_mine;
    private LinearLayout ll_mine;
    private LinearLayout activity_main;
    private int position;
    private boolean isPause = true;
    private Animation mRotate_anim;
    private long preTime;
    private MediaService.MyBinder mMyBinder;
    //“绑定”服务的intent
    private Intent MediaServiceIntent;
    private BaseFragment currentf;

    HomeFragment homeFragment;
    StudyFragment studyFragment;
    BuyFragment buyFragment;
    MineFragment mineFragment;
    private boolean abool;
    private BaseDialog mDialog;
    private BaseDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setISshow(false);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
        homeFragment = new HomeFragment();
        addFragments(homeFragment);
        // initData();
        initListener();
        initAnim();
        initMusic();
        abool = SpUtils.getBoolean(this, "abool", false);
        pop();
    }


    @Override
    protected void onResume() {
        super.onResume();
        abool = SpUtils.getBoolean(this, "abool", false);
    }

    private void pop() {
        mBuilder = new BaseDialog.Builder(this);
        mDialog = mBuilder.setViewId(R.layout.dialog_pop)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.BOTTOM)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.iv_pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("lxk", "lxk");
                startActivity(intent);
            }
        });
    }

    private void initMusic() {
        MediaServiceIntent = new Intent(this, MediaService.class);
        startService(MediaServiceIntent);
        bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void initAnim() {
        mRotate_anim = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        mRotate_anim.setInterpolator(interpolator);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbindService(mServiceConnection);
        stopService(MediaServiceIntent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("rotate")) {
            isPause = false;
            iv_listen.setImageResource(R.drawable.tab_listen_bo);
            if (mRotate_anim != null) {
                iv_listen.startAnimation(mRotate_anim);  //开始动画
            }
            mMyBinder.playMusic();
        } else if (eventBean.getMsg().equals("norotate")) {
            isPause = true;
            iv_listen.clearAnimation();
            iv_listen.setImageResource(R.drawable.tab_listen_pause);
            //homefragment传来这个的时候就是点了叉了
            mMyBinder.closeMedia();
        } else if (eventBean.getMsg().equals("main_pause")) {
            isPause = true;
            iv_listen.clearAnimation();
            iv_listen.setImageResource(R.drawable.tab_listen_pause);
            //播放界面传来的
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //        super.onSaveInstanceState(outState);
    }

    private void initListener() {
        ll_home.setOnClickListener(this);
        ll_study.setOnClickListener(this);
        ll_listen.setOnClickListener(this);
        ll_buy.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
    }

    private void initData() {
        mBaseFragments = new BaseFragment[4];
        mBaseFragments[0] = new HomeFragment();
        mBaseFragments[1] = new StudyFragment();
        mBaseFragments[2] = new BuyFragment();
        mBaseFragments[3] = new MineFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //注意add方法会走fragment的生命周期方法，相当于加载了一遍数据
        fragmentTransaction.add(R.id.fl_content, mBaseFragments[0]);
        fragmentTransaction.add(R.id.fl_content, mBaseFragments[1]);
        fragmentTransaction.add(R.id.fl_content, mBaseFragments[2]);
        fragmentTransaction.add(R.id.fl_content, mBaseFragments[3]);
        fragmentTransaction.show(mBaseFragments[0]);
        fragmentTransaction.hide(mBaseFragments[1]);
        fragmentTransaction.hide(mBaseFragments[2]);
        fragmentTransaction.hide(mBaseFragments[3]);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                //  position = 0;
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                addFragments(homeFragment);
                iv_home.setImageResource(R.drawable.tab_home_red);
                iv_buy.setImageResource(R.drawable.tab_buy_normal);
                iv_study.setImageResource(R.drawable.tab_study_normal);
                iv_mine.setImageResource(R.drawable.tab_mine_normal);
                tv_home.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                tv_buy.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_study.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_mine.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                break;
            case R.id.ll_study:
                if (!abool) {
                    Intent intent = new Intent(this, www.knowledgeshare.com.knowledgeshare.activity.LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_anim, R.anim.close_anim);
                } else {
                    if (studyFragment == null) {
                        studyFragment = new StudyFragment();
                    }
                    addFragments(studyFragment);
                    iv_home.setImageResource(R.drawable.tab_home_normal);
                    iv_buy.setImageResource(R.drawable.tab_buy_normal);
                    iv_study.setImageResource(R.drawable.tab_study_red);
                    iv_mine.setImageResource(R.drawable.tab_mine_normal);
                    tv_home.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                    tv_buy.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                    tv_study.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                    tv_mine.setTextColor(getResources().getColor(R.color.tab_text_normal_color));

                }
                break;
            case R.id.ll_listen:
                if (isPause) {
                    iv_listen.setImageResource(R.drawable.tab_listen_bo);
                    if (mMyBinder.isClosed()) {
                        String musicurl = SpUtils.getString(this, "musicurl", "");
                        if (!TextUtils.isEmpty(musicurl)) {
                            String title = SpUtils.getString(this, "title", "");
                            String subtitle = SpUtils.getString(this, "subtitle", "");
                            String t_head = SpUtils.getString(this, "t_head", "");
                            PlayerBean playerBean = new PlayerBean(t_head, title, subtitle, musicurl);
                            playerBean.setMsg("lastbofang");
                            EventBus.getDefault().postSticky(playerBean);
                        } else {
                            EventBus.getDefault().post(new EventBean("morenbofang"));
                        }
                    } else {
                        if (mRotate_anim != null) {
                            iv_listen.startAnimation(mRotate_anim);  //开始动画
                        }
                        mMyBinder.playMusic();
                        EventBean eventBean = new EventBean("home_bofang");
                        EventBus.getDefault().postSticky(eventBean);
                    }
                } else {
                    iv_listen.clearAnimation();
                    iv_listen.setImageResource(R.drawable.tab_listen_pause);
                    EventBean eventBean = new EventBean("home_pause");
                    EventBus.getDefault().postSticky(eventBean);
                    mMyBinder.pauseMusic();
                }
                isPause = !isPause;
                break;
            case R.id.ll_buy:
                if (!abool) {
                    Intent intent = new Intent(this, www.knowledgeshare.com.knowledgeshare.activity.LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_anim, R.anim.close_anim);
                } else {
                    if (buyFragment == null) {
                        buyFragment = new BuyFragment();
                    }
                    addFragments(buyFragment);
                    iv_home.setImageResource(R.drawable.tab_home_normal);
                    iv_buy.setImageResource(R.drawable.tab_buy_red);
                    iv_study.setImageResource(R.drawable.tab_study_normal);
                    iv_mine.setImageResource(R.drawable.tab_mine_normal);
                    tv_home.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                    tv_buy.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                    tv_study.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                    tv_mine.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                }
                break;
            case R.id.ll_mine:
                if (!abool) {
                    Intent intent = new Intent(this, www.knowledgeshare.com.knowledgeshare.activity.LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.start_anim, R.anim.close_anim);
                } else {
                    if (mineFragment == null) {
                        mineFragment = new MineFragment();
                    }
                    addFragments(mineFragment);
                    iv_home.setImageResource(R.drawable.tab_home_normal);
                    iv_buy.setImageResource(R.drawable.tab_buy_normal);
                    iv_study.setImageResource(R.drawable.tab_study_normal);
                    iv_mine.setImageResource(R.drawable.tab_mine_red);
                    tv_home.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                    tv_buy.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                    tv_study.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                    tv_mine.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                }
                break;
        }
    }


    private void addFragments(BaseFragment f) {
        // 第一步：得到fragment管理类
        FragmentManager manager = getSupportFragmentManager();
        // 第二步：开启一个事务
        FragmentTransaction transaction = manager.beginTransaction();

        if (currentf != null) {
            //每次把前一个fragment给隐藏了
            transaction.hide(currentf);
        }
        //isAdded:判断当前的fragment对象是否被加载过
        if (!f.isAdded()) {
            // 第三步：调用添加fragment的方法 第一个参数：容器的id 第二个参数：要放置的fragment的一个实例对象
            transaction.add(R.id.fl_content, f);
        }
        //显示当前的fragment
        transaction.show(f);
        // 第四步：提交
        transaction.commit();
        currentf = f;
    }

    private void initView() {
        iv_home = (ImageView) findViewById(R.id.iv_home);
        tv_home = (TextView) findViewById(R.id.tv_home);
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        iv_study = (ImageView) findViewById(R.id.iv_study);
        tv_study = (TextView) findViewById(R.id.tv_study);
        ll_study = (LinearLayout) findViewById(R.id.ll_study);
        iv_listen = (ImageView) findViewById(R.id.iv_listen);
        ll_listen = (LinearLayout) findViewById(R.id.ll_listen);
        iv_buy = (ImageView) findViewById(R.id.iv_buy);
        tv_buy = (TextView) findViewById(R.id.tv_buy);
        ll_buy = (LinearLayout) findViewById(R.id.ll_buy);
        iv_mine = (ImageView) findViewById(R.id.iv_mine);
        tv_mine = (TextView) findViewById(R.id.tv_mine);
        ll_mine = (LinearLayout) findViewById(R.id.ll_mine);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > preTime + 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            preTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();//相当于finish()
            realBack();//删除所有引用
        }
    }
}
