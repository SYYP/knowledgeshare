package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

/**
 * Created by Administrator on 2017/11/17.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.title_message_iv) ImageView titleMessageIv;@BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.title_setting_iv) ImageView titleSettingIv;@BindView(R.id.mine_face_iv) CircleImageView mineFaceIv;
    @BindView(R.id.mine_name_tv) TextView mineNameTv;@BindView(R.id.xz_start_iv) ImageView xzStartIv;
    @BindView(R.id.progressBar) ProgressBar progressBar;@BindView(R.id.xz_end_iv) ImageView xzEndIv;
    @BindView(R.id.qiandao_btn) Button qiandaoBtn;@BindView(R.id.xxsj_rl) RelativeLayout xxsjRl;
    @BindView(R.id.wddy_rl) RelativeLayout wddyRl;@BindView(R.id.xxjl_rl) RelativeLayout xxjlRl;
    @BindView(R.id.zhye_tv) TextView zhyeTv;@BindView(R.id.wdzh_rl) RelativeLayout wdzhRl;
    @BindView(R.id.wdxz_rl) RelativeLayout wdxzRl;@BindView(R.id.zhaq_rl) RelativeLayout zhaqRl;
    Unbinder unbinder;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, inflate);
        titleMessageIv.setOnClickListener(this);
        titleSettingIv.setOnClickListener(this);
        mineFaceIv.setOnClickListener(this);
        qiandaoBtn.setOnClickListener(this);
        xxsjRl.setOnClickListener(this);
        wddyRl.setOnClickListener(this);
        xxjlRl.setOnClickListener(this);
        wdzhRl.setOnClickListener(this);
        wdxzRl.setOnClickListener(this);
        zhaqRl.setOnClickListener(this);
        progressBar.setProgress(60);
        return inflate;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_message_iv:
                TUtils.showShort(getActivity(), "消息");
                Logger.e("消息");
                break;
            case R.id.title_setting_iv:
                TUtils.showShort(getActivity(), "设置");
                Logger.e("设置");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
