package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import www.knowledgeshare.com.knowledgeshare.fragment.mine.MineFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.study.StudyFragment;

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
    private boolean isPause=true;
    private Animation mRotate_anim;
    private long preTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initListener();
        initAnim();
    }

    private void initAnim() {
        mRotate_anim = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        mRotate_anim.setInterpolator(interpolator);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("rotate")) {
            isPause=false;
            iv_listen.setImageResource(R.drawable.tab_listen_bo);
            if (mRotate_anim != null) {
                iv_listen.startAnimation(mRotate_anim);  //开始动画
            }
        }else if (eventBean.getMsg().equals("norotate")){
            isPause=true;
            iv_listen.clearAnimation();
            iv_listen.setImageResource(R.drawable.tab_listen_pause);
        }
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.ll_home:
                position = 0;
                iv_home.setImageResource(R.drawable.tab_home_red);
                iv_buy.setImageResource(R.drawable.tab_buy_normal);
                iv_study.setImageResource(R.drawable.tab_study_normal);
                iv_mine.setImageResource(R.drawable.tab_mine_normal);
                tv_home.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                tv_buy.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_study.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_mine.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                fragmentTransaction.show(mBaseFragments[0]);
                fragmentTransaction.hide(mBaseFragments[1]).hide(mBaseFragments[2]).hide(mBaseFragments[3]);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.ll_study:
                position = 1;
                iv_home.setImageResource(R.drawable.tab_home_normal);
                iv_buy.setImageResource(R.drawable.tab_buy_normal);
                iv_study.setImageResource(R.drawable.tab_study_red);
                iv_mine.setImageResource(R.drawable.tab_mine_normal);
                tv_home.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_buy.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_study.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                tv_mine.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                fragmentTransaction.show(mBaseFragments[1]);
                fragmentTransaction.hide(mBaseFragments[0]).hide(mBaseFragments[2]).hide(mBaseFragments[3]);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.ll_listen:
                position = 2;
                if (isPause){
                    iv_listen.setImageResource(R.drawable.tab_listen_bo);
                    if (mRotate_anim != null) {
                        iv_listen.startAnimation(mRotate_anim);  //开始动画
                    }
                    EventBean eventBean = new EventBean("bofang");
                    EventBus.getDefault().postSticky(eventBean);
                }else {
                    iv_listen.clearAnimation();
                    iv_listen.setImageResource(R.drawable.tab_listen_pause);
                    EventBean eventBean = new EventBean("pause");
                    EventBus.getDefault().postSticky(eventBean);
                }
                isPause=!isPause;
                break;
            case R.id.ll_buy:
                position = 3;
                iv_home.setImageResource(R.drawable.tab_home_normal);
                iv_buy.setImageResource(R.drawable.tab_buy_red);
                iv_study.setImageResource(R.drawable.tab_study_normal);
                iv_mine.setImageResource(R.drawable.tab_mine_normal);
                tv_home.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_buy.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                tv_study.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_mine.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                fragmentTransaction.show(mBaseFragments[2]);
                fragmentTransaction.hide(mBaseFragments[1]).hide(mBaseFragments[0]).hide(mBaseFragments[3]);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case R.id.ll_mine:
                position = 4;
                iv_home.setImageResource(R.drawable.tab_home_normal);
                iv_buy.setImageResource(R.drawable.tab_buy_normal);
                iv_study.setImageResource(R.drawable.tab_study_normal);
                iv_mine.setImageResource(R.drawable.tab_mine_red);
                tv_home.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_buy.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_study.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
                tv_mine.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
                fragmentTransaction.show(mBaseFragments[3]);
                fragmentTransaction.hide(mBaseFragments[1]).hide(mBaseFragments[2]).hide(mBaseFragments[0]);
                fragmentTransaction.commitAllowingStateLoss();
                break;
        }
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
