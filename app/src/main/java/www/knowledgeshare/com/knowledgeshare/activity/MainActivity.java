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

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.StudyTimeBean;
import www.knowledgeshare.com.knowledgeshare.db.StudyTimeUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.BuyFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.home.HomeFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.WebActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HomeBannerBean;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.MineFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.study.StudyFragment;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
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
        // initData();
        initListener();
        initAnim();
        initMusic();
        abool = SpUtils.getBoolean(this, "abool", false);
        pop();
        setStudyTime();
        String gobuy = getIntent().getStringExtra("gobuy");
        if (gobuy!=null && gobuy.equals("gobuy")){
            buyFragment = new BuyFragment();
            addFragments(buyFragment);
        }else {
            homeFragment = new HomeFragment();
            addFragments(homeFragment);
        }
    }


    //每次应用启动的时候上传一下学习时间
    private void setStudyTime() {
        List<StudyTimeBean> search = StudyTimeUtils.search();
        if (search != null && search.size() > 0) {
            String userid = SpUtils.getString(this, "id", "");
            if (!TextUtils.isEmpty(userid)) {
                HttpHeaders headers = new HttpHeaders();
                headers.put("Authorization", "Bearer " + SpUtils.getString(MyApplication.getGloableContext(), "token", ""));
                HttpParams params = new HttpParams();
                params.put("date", StudyTimeUtils.getDate());
                params.put("time", StudyTimeUtils.getTotalTime());
                OkGo.<DianZanbean>post(MyContants.LXKURL + "user/add-time")
                        .tag(this)
                        .headers(headers)
                        .params(params)
                        .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                                     @Override
                                     public void onSuccess(Response<DianZanbean> response) {
                                         int code = response.code();
                                         if (response.code() >= 200 && response.code() <= 204) {
                                             StudyTimeUtils.deleteAll();//成功的话就删除掉上次启动保存的学习时间数据表
                                         }
                                     }
                                 }
                        );
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        abool = SpUtils.getBoolean(this, "abool", false);
    }

    private void pop() {
        HttpParams params2 = new HttpParams();
        params2.put("type", false);
        params2.put("from", "android");
        OkGo.<HomeBannerBean>get(MyContants.LXKURL + "bootstrappers")
                .tag(this)
                .params(params2)
                .execute(new JsonCallback<HomeBannerBean>(HomeBannerBean.class) {
                    @Override
                    public void onSuccess(Response<HomeBannerBean> response) {
                        int code = response.code();
                        HomeBannerBean bannerBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            final List<HomeBannerBean.CtivityEntity> ctivity = bannerBean.getCtivity();
                            if (ctivity != null && ctivity.size() > 0) {
                                mBuilder = new BaseDialog.Builder(MainActivity.this);
                                mDialog = mBuilder.setViewId(R.layout.dialog_pop)
                                        //设置dialogpadding
                                        .setPaddingdp(10, 0, 10, 0)
                                        //设置显示位置
                                        .setGravity(Gravity.CENTER)
                                        //设置动画
                                        .setAnimation(R.style.Bottom_Top_aniamtion)
                                        //设置dialog的宽高
                                        .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                        //设置触摸dialog外围是否关闭
                                        .isOnTouchCanceled(true)
                                        //设置监听事件
                                        .builder();
                                mDialog.getView(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDialog.dismiss();
                                    }
                                });
                                final ImageView iv_pop = mDialog.getView(R.id.iv_pop);
                                final HomeBannerBean.CtivityEntity ctivityEntity = ctivity.get(0);
                                if (ctivityEntity != null) {
                                    Glide.with(MainActivity.this).load(ctivityEntity.getImgurl()).into(iv_pop);
                                    mDialog.show();
                                }
                                iv_pop.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDialog.dismiss();
                                        if (ctivityEntity != null) {
                                            if (ctivityEntity.getCourse_id() == 0) {
                                                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                                                intent.putExtra("url", ctivityEntity.getLink());
                                                startActivity(intent);
                                            } else {
                                                if (ctivityEntity.getType() == 1) {
                                                    Intent intent = new Intent(MainActivity.this, SoftMusicDetailActivity.class);
                                                    intent.putExtra("id", ctivityEntity.getCourse_id() + "");
                                                    startActivity(intent);
                                                } else if (ctivityEntity.getType() == 2) {
                                                    Intent intent = new Intent(MainActivity.this, ZhuanLanActivity.class);
                                                    intent.putExtra("id", ctivityEntity.getCourse_id() + "");
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        } else {

                        }
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
                    Intent intent = new Intent(this, LoginActivity.class);
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
                        EventBus.getDefault().post(new EventBean("morenbofang"));
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
                    Intent intent = new Intent(this, LoginActivity.class);
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
                    Intent intent = new Intent(this, LoginActivity.class);
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
            EventBean eventBean = new EventBean("home_pause");//发一个保证正在播放音频的学习时长的终止
            EventBus.getDefault().postSticky(eventBean);
            super.onBackPressed();//相当于finish()
            realBack();//删除所有引用
        }
    }
}
