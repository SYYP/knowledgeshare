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

import org.greenrobot.eventbus.EventBus;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.BoFangListActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.MusicActivity;

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

    public CustomPopupWindow(Activity context) {
        super(context);
        this.mContext=context;
        init(context);
        setPopupWindow();
    }

    /**
     * 初始化
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
        switch (v.getId()){
            case R.id.iv_delete:
                this.dismiss();
                isBofang = false;
                rl_bofang.setVisibility(View.GONE);
                EventBean eventBean = new EventBean("norotate");
                EventBus.getDefault().postSticky(eventBean);
                EventBean eventBean2 = new EventBean("close");
                EventBus.getDefault().postSticky(eventBean2);
                break;
            case R.id.iv_arrow_top:
                Intent intent1 = new Intent(mContext, MusicActivity.class);
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
