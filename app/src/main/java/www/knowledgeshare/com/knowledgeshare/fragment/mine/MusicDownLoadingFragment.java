package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.graphics.Canvas;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.MusicDownLoadBean;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.adapter.DownloadAdapter;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MusicDownLoadingFragment extends BaseFragment implements View.OnClickListener, XExecutor.OnAllTaskEndListener {

    @BindView(R.id.qbks_ll)
    LinearLayout qbksLl;
    @BindView(R.id.qbsc_ll)
    LinearLayout qbscLl;
    @BindView(R.id.recycler_xzz)
    RecyclerView recyclerXzz;
    @BindView(R.id.kaishi_tv)
    TextView kaishiTv;
    Unbinder unbinder;
    private List<MusicDownLoadBean> apks;
    private static final int REQUEST_PERMISSION_STORAGE = 0x01;
    private DownloadAdapter adapter;
    private OkDownload okDownload;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_downloading, null);
        unbinder = ButterKnife.bind(this, inflate);
        qbscLl.setOnClickListener(this);
        qbksLl.setOnClickListener(this);
        okDownload = OkDownload.getInstance();

        return inflate;
    }

    @Override
    protected void initData() {
        initAdapter();
    }

    private void initAdapter() {
        //从数据库中恢复数据
        List<Progress> progressList = DownloadManager.getInstance().getAll();
        OkDownload.restore(progressList);

        adapter = new DownloadAdapter(getActivity());
        adapter.updateData(DownloadAdapter.TYPE_ING);
        recyclerXzz.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerXzz.setNestedScrollingEnabled(false);
        recyclerXzz.setAdapter(adapter);
        okDownload.addOnAllTaskEndListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        okDownload.removeOnAllTaskEndListener(this);
        adapter.unRegister();
    }

    @Override
    public void onAllTaskEnd() {
//        TUtils.showShort(mContext,"所有下载任务已结束");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qbks_ll:
                if (TextUtils.equals("全部开始",kaishiTv.getText().toString())){
                    kaishiTv.setText("全部暂停");
                    okDownload.startAll();
                }else {
                    okDownload.pauseAll();
                    kaishiTv.setText("全部开始");
                }
                break;
            case R.id.qbsc_ll:
                showTips();
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
                        okDownload.removeAll();
                        adapter.updateData(DownloadAdapter.TYPE_ING);
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
