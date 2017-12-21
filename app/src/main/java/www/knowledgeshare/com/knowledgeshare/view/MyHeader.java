package www.knowledgeshare.com.knowledgeshare.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.liaoinstan.springview.container.BaseHeader;

import www.knowledgeshare.com.knowledgeshare.R;

/**
 * Created by Administrator on 2017/11/30.
 */

public class MyHeader extends BaseHeader {

    private ImageView mIv_anim_refresh;
    private Context mContext;
    private RotateAnimation mRotateUpAnim;
    private RotateAnimation mRotateDownAnim;
    private final int ROTATE_ANIM_DURATION = 180;
    private int[] refreshAnimSrcs = new int[]{R.drawable.refresh1, R.drawable.refresh2, R.drawable.refresh3,
            R.drawable.refresh4, R.drawable.refresh5, R.drawable.refresh6,
            R.drawable.refresh7, R.drawable.refresh8, R.drawable.refresh9,
            R.drawable.refresh10, R.drawable.refresh11, R.drawable.refresh12,
            R.drawable.refresh13, R.drawable.refresh14, R.drawable.refresh15,
            R.drawable.refresh16, R.drawable.refresh17, R.drawable.refresh18,
            R.drawable.refresh19, R.drawable.refresh20, R.drawable.refresh21,
            R.drawable.refresh22, R.drawable.refresh23, R.drawable.refresh24};
    private int[] pullAnimSrcs = new int[]{R.drawable.refresh25, R.drawable.refresh24};
    private AnimationDrawable animationRefresh;
    private long freshTime;
    private TextView headerTitle;
    private TextView headerTime;

    public MyHeader(Context context) {
        this.mContext = context;
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        animationRefresh = new AnimationDrawable();
        for (int src : this.refreshAnimSrcs) {
            animationRefresh.addFrame(ContextCompat.getDrawable(mContext, src), 100);
            animationRefresh.setOneShot(false);
        }
    }

    //获取Header
    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View inflate = inflater.inflate(R.layout.refresh_layout, viewGroup, true);
        mIv_anim_refresh = inflate.findViewById(R.id.iv_refresh);
        headerTitle = (TextView) inflate.findViewById(R.id.default_header_title);
        headerTime = (TextView) inflate.findViewById(R.id.default_header_time);
        if (pullAnimSrcs != null && pullAnimSrcs.length > 0)
            mIv_anim_refresh.setImageResource(pullAnimSrcs[0]);
        return inflate;
    }

    //拖拽开始前回调
    @Override
    public void onPreDrag(View rootView) {
        if (freshTime == 0) {
            freshTime = System.currentTimeMillis();
        } else {
            int m = (int) ((System.currentTimeMillis() - freshTime) / 1000 / 60);
            if (m >= 1 && m < 60) {
                headerTime.setText(m + "分钟前");
            } else if (m >= 60) {
                int h = m / 60;
                headerTime.setText(h + "小时前");
            } else if (m > 60 * 24) {
                int d = m / (60 * 24);
                headerTime.setText(d + "天前");
            } else if (m == 0) {
                headerTime.setText("刚刚");
            }
        }
    }

    //手指拖拽过程中不断回调，dy为拖拽的距离，可以根据拖动的距离添加拖动过程动画
    @Override
    public void onDropAnim(View rootView, int dy) {
        System.out.println("xxxxxxxxxxxx" + dy);
    }

    //手指拖拽过程中每次经过临界点时回调，upORdown是向上经过还是向下经过
    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        if (!upORdown) {
            headerTitle.setText("松开刷新数据");
            mIv_anim_refresh.startAnimation(mRotateUpAnim);
        } else {
            headerTitle.setText("下拉刷新");
            mIv_anim_refresh.startAnimation(mRotateDownAnim);
        }
    }

    //拉动超过临界点后松开时回调
    @Override
    public void onStartAnim() {
        freshTime = System.currentTimeMillis();
        headerTitle.setText("正在刷新");
        mIv_anim_refresh.clearAnimation();
        mIv_anim_refresh.setImageDrawable(animationRefresh);
        animationRefresh.start();
    }

    //头部已经全部弹回时回调
    @Override
    public void onFinishAnim() {
        //        headerTitle.setText("刷新完成");
        animationRefresh.stop();
        if (pullAnimSrcs != null && pullAnimSrcs.length > 0)
            mIv_anim_refresh.setImageResource(pullAnimSrcs[0]);
    }
}
