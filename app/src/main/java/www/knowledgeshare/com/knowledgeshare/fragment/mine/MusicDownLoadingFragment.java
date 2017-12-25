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
        okDownload.setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/");
        okDownload.getThreadPool().setCorePoolSize(5);

        return inflate;
    }

    @Override
    protected void initData() {
        apks = new ArrayList<>();
        MusicDownLoadBean apk1 = new MusicDownLoadBean();
        apk1.name = "爱奇艺";
        apk1.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0c10c4c0155c9adf1282af008ed329378d54112ac";
        apk1.url = "http://121.29.10.1/f5.market.mi-img.com/download/AppStore/0b8b552a1df0a8bc417a5afae3a26b2fb1342a909/com.qiyi.video.apk";
        apks.add(apk1);
        MusicDownLoadBean apk2 = new MusicDownLoadBean();
        apk2.name = "微信";
        apk2.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/00814b5dad9b54cc804466369c8cb18f23e23823f";
        apk2.url = "http://116.117.158.129/f2.market.xiaomi.com/download/AppStore/04275951df2d94fee0a8210a3b51ae624cc34483a/com.tencent.mm.apk";
        apks.add(apk2);
        MusicDownLoadBean apk3 = new MusicDownLoadBean();
        apk3.name = "新浪微博";
        apk3.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/01db44d7f809430661da4fff4d42e703007430f38";
        apk3.url = "http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk";
        apks.add(apk3);
        MusicDownLoadBean apk4 = new MusicDownLoadBean();
        apk4.name = "QQ";
        apk4.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/072725ca573700292b92e636ec126f51ba4429a50";
        apk4.url = "http://121.29.10.1/f3.market.xiaomi.com/download/AppStore/0ff0604fd770f481927d1edfad35675a3568ba656/com.tencent.mobileqq.apk";
        apks.add(apk4);
        MusicDownLoadBean apk5 = new MusicDownLoadBean();
        apk5.name = "陌陌";
        apk5.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/06006948e655c4dd11862d060bd055b4fd2b5c41b";
        apk5.url = "http://121.18.239.1/f4.market.xiaomi.com/download/AppStore/096f34dec955dbde0597f4e701d1406000d432064/com.immomo.momo.apk";
        apks.add(apk5);
        /*MusicDownLoadBean apk6 = new MusicDownLoadBean();
        apk6.name = "手机淘宝";
        apk6.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/017a859792d09d7394108e0a618411675ec43f220";
        apk6.url = "http://121.29.10.1/f3.market.xiaomi.com/download/AppStore/0afc00452eb1a4dc42b20c9351eacacab4692a953/com.taobao.taobao.apk";
        apks.add(apk6);
        MusicDownLoadBean apk7 = new MusicDownLoadBean();
        apk7.name = "酷狗音乐";
        apk7.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0f2f050e21e42f75c7ecca55d01ac4e5e4e40ca8d";
        apk7.url = "http://121.18.239.1/f5.market.xiaomi.com/download/AppStore/053ed49c1545c6eec3e3e23b31568c731f940934f/com.kugou.android.apk";
        apks.add(apk7);
        MusicDownLoadBean apk8 = new MusicDownLoadBean();
        apk8.name = "网易云音乐";
        apk8.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/02374548ac39f3b7cdbf5bea4b0535b5d1f432f23";
        apk8.url = "http://121.18.239.1/f4.market.xiaomi.com/download/AppStore/0f458c5661acb492e30b808a2e3e4c8672e6b55e2/com.netease.cloudmusic.apk";
        apks.add(apk8);
        MusicDownLoadBean apk9 = new MusicDownLoadBean();
        apk9.name = "ofo共享单车";
        apk9.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0fe1a5c6092f3d9fa5c4c1e3158e6ff33f6418152";
        apk9.url = "http://60.28.125.1/f4.market.mi-img.com/download/AppStore/06954949fcd48414c16f726620cf2d52200550f56/so.ofo.labofo.apk";
        apks.add(apk9);
        MusicDownLoadBean apk10 = new MusicDownLoadBean();
        apk10.name = "摩拜单车";
        apk10.iconUrl = "http://file.market.xiaomi.com/thumbnail/PNG/l114/AppStore/0863a058a811148a5174d9784b7be2f1114191f83";
        apk10.url = "http://60.28.125.1/f4.market.xiaomi.com/download/AppStore/00cdeb4865c5a4a7d350fe30b9f812908a569cc8a/com.mobike.mobikeapp.apk";
        apks.add(apk10);*/

        for (MusicDownLoadBean apk : apks) {

            //这里只是演示，表示请求可以传参，怎么传都行，和okgo使用方法一样
            GetRequest<File> request = OkGo.<File>get(apk.url)//
                    .headers("aaa", "111")//
                    .params("bbb", "222");

            //这里第一个参数是tag，代表下载任务的唯一标识，传任意字符串都行，需要保证唯一,我这里用url作为了tag
            OkDownload.request(apk.url, request)//
                    .priority(apk.priority)//
                    .extra1(apk)//
                    .save()//
                    .register(new LogDownloadListener());//
//                    .start();
        }

        initAdapter();
    }

    private void initAdapter() {
        //从数据库中恢复数据
        List<Progress> progressList = DownloadManager.getInstance().getAll();
        OkDownload.restore(progressList);

        adapter = new DownloadAdapter(getActivity());
        adapter.updateData(DownloadAdapter.TYPE_ALL);
        recyclerXzz.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerXzz.setNestedScrollingEnabled(false);
        recyclerXzz.setAdapter(adapter);
//        initBind();
        okDownload.addOnAllTaskEndListener(this);
    }

    /*private void initBind() {
        //为RecycleView绑定触摸事件
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            //限制ImageView长度所能增加的最大值
            private double ICON_MAX_SIZE = 50;
            //ImageView的初始长宽
            private int fixedWidth = 150;

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //侧滑删除
                int swipeFlags = ItemTouchHelper.LEFT;
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//拖拽
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //滑动事件
                *//*Collections.swap(apks, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());*//*
                adapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑事件
                *//*apks.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());*//*
                //onItemDissmiss是接口方法
                adapter.onItemDissmiss(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                *//*if (dY != 0 && dX == 0) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }

                DownloadAdapter.ViewHolder holder = (DownloadAdapter.ViewHolder) viewHolder;

                if (dX < -holder.getShanchuTv().getWidth()) {
                    //最多偏移 mActionContainer 的宽度
                    dX =- holder.getShanchuTv().getWidth();
                }
                holder.getContentLl().setTranslationX(dX);*//*
                //仅对侧滑状态下的效果做出改变
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    //如果dX小于等于删除方块的宽度，那么我们把该方块滑出来
                    if (Math.abs(dX) <= getSlideLimitation(viewHolder)){
                        viewHolder.itemView.scrollTo(-(int) dX,0);
                    }

                }else {
                    //拖拽状态下不做改变，需要调用父类的方法
                    super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
                }

            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //重置改变，防止由于复用而导致的显示问题
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setScrollX(0);
                DownloadAdapter.ViewHolder holder = (DownloadAdapter.ViewHolder) viewHolder;
                holder.getShanchuTv().setText("左滑删除");

            }

            *//**
             * 获取删除方块的宽度
             *//*
            public int getSlideLimitation(RecyclerView.ViewHolder viewHolder){
                ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
                return viewGroup.getChildAt(1).getLayoutParams().width;
            }
        });
        helper.attachToRecyclerView(recyclerXzz);
    }*/

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
                        adapter.updateData(DownloadAdapter.TYPE_ALL);
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
