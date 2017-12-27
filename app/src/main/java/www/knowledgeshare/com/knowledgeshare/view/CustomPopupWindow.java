package www.knowledgeshare.com.knowledgeshare.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.BoFangListActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.MusicActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;

/**
 * Created by Administrator on 2017/12/5.
 */

public class CustomPopupWindow extends PopupWindow implements View.OnClickListener {
    private final Activity mContext;
    private View mPopView;
    private ImageView iv_delete;
    private CircleImageView iv_bo_head;
    private TextView tv_title;
    private TextView tv_subtitle;
    private ImageView iv_arrow_top;
    private ImageView iv_mulu;
    private RelativeLayout rl_bofang;
    private boolean isBofang;
    private static String title;
    private static String subtitle;
    private static String header;
    private static MusicTypeBean mMusicTypeBean;

    public CustomPopupWindow(Activity context) {
        super(context);
        this.mContext = context;
        init(context);
        setPopupWindow();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(PlayerBean playerBean) {
        if (playerBean.getMsg().equals("refreshplayer")) {//刷新所有具有popupwindow的界面，让popupwindow上显示的图片和文字改变
            title = playerBean.getTitle();
            subtitle = playerBean.getSubtitle();
            header = playerBean.getTeacher_head();
            Glide.with(mContext).load(header).into(iv_bo_head);
            tv_title.setText(title);
            tv_subtitle.setText(subtitle);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(MusicTypeBean musicTypeBean) {
        if (musicTypeBean.getMsg().equals("musicplayertype")) {//把播放的数据封装成bean传来，方便进入播放主界面
            mMusicTypeBean = musicTypeBean;
        }
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.smallmusic, null);
        iv_delete = (ImageView) mPopView.findViewById(R.id.iv_delete);
        iv_delete.setVisibility(View.VISIBLE);
        iv_delete.setOnClickListener(this);
        iv_bo_head = (CircleImageView) mPopView.findViewById(R.id.iv_bo_head);
        tv_title = (TextView) mPopView.findViewById(R.id.tv_title);
        tv_subtitle = (TextView) mPopView.findViewById(R.id.tv_subtitle);
        iv_arrow_top = (ImageView) mPopView.findViewById(R.id.iv_arrow_top);
        iv_arrow_top.setOnClickListener(this);
        iv_mulu = (ImageView) mPopView.findViewById(R.id.iv_mulu);
        iv_mulu.setOnClickListener(this);
        rl_bofang = (RelativeLayout) mPopView.findViewById(R.id.rl_bofang);
        Glide.with(mContext).load(header).into(iv_bo_head);
        tv_title.setText(title);
        tv_subtitle.setText(subtitle);
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(false);//设置为true的话，界面中的滑动控件就不滑动了，所以我取消了焦点，但是并不影响点击事件
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete:
                this.dismiss();
                isBofang = false;
                EventBean eventBean = new EventBean("norotate");
                EventBus.getDefault().postSticky(eventBean);
                EventBean eventBean2 = new EventBean("home_close");
                EventBus.getDefault().postSticky(eventBean2);
                break;
            case R.id.iv_arrow_top:
                Intent intent1 = new Intent(mContext, MusicActivity.class);
                intent1.putExtra("data",mMusicTypeBean);
                mContext.startActivity(intent1);
                mContext.overridePendingTransition(R.anim.bottom_in, 0);
                break;
            case R.id.iv_mulu:
                Intent intent11 = new Intent(mContext, BoFangListActivity.class);
                mContext.startActivity(intent11);
                break;
        }
    }

}
