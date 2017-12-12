package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.DownLoadBean;

/**
 * Created by Administrator on 2017/11/28.
 */

public class DownLoadingFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.qbks_ll)
    LinearLayout qbksLl;
    @BindView(R.id.qbsc_ll)
    LinearLayout qbscLl;
    @BindView(R.id.recycler_xzz)
    RecyclerView recyclerXzz;
    @BindView(R.id.kaishi_tv)
    TextView kaishiTv;
    Unbinder unbinder;
    private List<DownLoadBean> list;
    private DownLoadingAdapter adapter;
    private boolean flag;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_downloading, null);
        unbinder = ButterKnife.bind(this, inflate);
        qbscLl.setOnClickListener(this);
        qbksLl.setOnClickListener(this);
        return inflate;
    }

    @Override
    protected void initData() {
        recyclerXzz.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerXzz.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            DownLoadBean downLoadBean = new DownLoadBean();
            downLoadBean.setContent("如何成为一名合格的歌者");
            downLoadBean.setJindu(i+"");
            downLoadBean.setSize("2M/10M");
            downLoadBean.setZhuangtai("正在下载");
            list.add(downLoadBean);
        }
        adapter = new DownLoadingAdapter(R.layout.item_down_loading, list);
        recyclerXzz.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qbsc_ll:
                showTips();
                break;
            case R.id.qbks_ll:
                if (TextUtils.equals("全部开始",kaishiTv.getText().toString())){
                    kaishiTv.setText("全部暂停");
                    for (int i = 0; i < list.size(); i++) {
                        if (i < list.size()-4){
                            list.get(i).setZhuangtai("已暂停");
                        }else {
                            list.get(i).setZhuangtai("等待中");
                        }
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    kaishiTv.setText("全部开始");
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setZhuangtai("正在下载");
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    private class DownLoadingAdapter extends BaseQuickAdapter<DownLoadBean,BaseViewHolder>{

        public DownLoadingAdapter(@LayoutRes int layoutResId, @Nullable List<DownLoadBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DownLoadBean item) {
            RelativeLayout if_start_rl = helper.getView(R.id.if_start_rl);
            TextView downdload_content_tv = helper.getView(R.id.downdload_content_tv);
            ProgressBar progressBar = helper.getView(R.id.progressBar);
            TextView size_tv = helper.getView(R.id.size_tv);
            TextView zhuangtai_tv = helper.getView(R.id.zhuangtai_tv);
            final ImageView zhuangtai_iv = helper.getView(R.id.xz_zhuangtai_iv);
            flag = true;

            downdload_content_tv.setText(item.getContent());
            progressBar.setProgress(Integer.parseInt(item.getJindu()));
            size_tv.setText(item.getSize());
            zhuangtai_tv.setText(item.getZhuangtai());

            zhuangtai_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (flag){
                        zhuangtai_iv.setImageDrawable(getResources().getDrawable(R.drawable.power_zanting));
                        flag = false;
                    }else {
                        zhuangtai_iv.setImageDrawable(getResources().getDrawable(R.drawable.power_kaishi_iv));
                        flag = true;
                    }
                }
            });
        }
    }

    private void showTips() {
        new NormalAlertDialog.Builder(getActivity())
                .setTitleVisible(true).setTitleText("提示")
                .setTitleTextColor(R.color.text_black)
                .setContentText("确认要全部删除吗？")
                .setContentTextColor(R.color.text_black)
                .setLeftButtonText("是")
                .setLeftButtonTextColor(R.color.text_black)
                .setRightButtonText("否")
                .setRightButtonTextColor(R.color.text_black)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                        list.clear();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }
}
