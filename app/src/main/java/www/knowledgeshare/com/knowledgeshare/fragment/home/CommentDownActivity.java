package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.EveryDayBean;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * Created by power on 2017/12/31.
 */

public class CommentDownActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private RecyclerView recycler_list;
    private ImageView iv_quanxuan;
    private TextView tv_quanxuan;
    private TextView tv_download;
    private MyAdapter mMyAdapter;
    private boolean isAllChecked = true;
    private EveryDayBean everyDayBean;
    private List<EveryDayBean.DailysBean> list = new ArrayList<>();
    private BaseDialog mNetDialog;
    private TextView mTv_content;
    private boolean nowifiallowdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_list);
        setISshow(false);
        initView();
        initNETDialog();
    }

    private void initNETDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        mNetDialog = builder.setViewId(R.layout.dialog_iswifi)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mTv_content = mNetDialog.getView(R.id.tv_content);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        recycler_list = (RecyclerView) findViewById(R.id.recycler_list);
        iv_quanxuan = (ImageView) findViewById(R.id.iv_quanxuan);
        iv_quanxuan.setOnClickListener(this);
        tv_quanxuan = (TextView) findViewById(R.id.tv_quanxuan);
        tv_quanxuan.setOnClickListener(this);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        recycler_list.setLayoutManager(new LinearLayoutManager(this));

        getIntentData();

    }

    private void getIntentData() {
        everyDayBean = (EveryDayBean) getIntent().getExtras().getSerializable("model");
        list = everyDayBean.getDailys();
        if (list != null && list.size() > 0) {
            mMyAdapter = new MyAdapter(R.layout.item_download, list);
            recycler_list.setAdapter(mMyAdapter);
            mMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    //                Toast.makeText(DownLoadListActivity.this, "触发", Toast.LENGTH_SHORT).show();
                    list.get(position).setChecked(!list.get(position).isChecked());
                    //                mMyAdapter.setNewData(mList);
                    mMyAdapter.notifyDataSetChanged();
                    if (!isAllChecked()) {
                        iv_quanxuan.setImageResource(R.drawable.noquanxuan);
                    } else {
                        iv_quanxuan.setImageResource(R.drawable.quanxuan_red);
                    }
                }
            });
        }
    }

    private void quanxuan() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(true);
        }
        //        mMyAdapter.setNewData(mList);
        mMyAdapter.notifyDataSetChanged();
        iv_quanxuan.setImageResource(R.drawable.quanxuan_red);
    }

    private boolean isAllChecked() {
        for (int i = 0; i < list.size(); i++) {
            boolean checked = list.get(i).isChecked();
            if (!checked) {
                return false;
            }
        }
        return true;
    }

    private void noquanxuan() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
        //        mMyAdapter.setNewData(mList);
        mMyAdapter.notifyDataSetChanged();
        iv_quanxuan.setImageResource(R.drawable.noquanxuan);
    }

    private class MyAdapter extends BaseQuickAdapter<EveryDayBean.DailysBean, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<EveryDayBean.DailysBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, EveryDayBean.DailysBean item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_ischeck);
            if (item.isChecked()) {
                imageView.setImageResource(R.drawable.quanxuan_red);
            } else {
                imageView.setImageResource(R.drawable.noquanxuan);
            }
            String created_at = item.getCreated_at();
            Logger.e(created_at);
            String[] split = created_at.split(" ");
            helper.setText(R.id.tv_name, item.getVideo_name())
                    .setText(R.id.tv_date, split[0])
                    .setText(R.id.tv_time, item.getVideo_time())
                    .setText(R.id.tv_order, helper.getAdapterPosition() + 1 + "");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_quanxuan:
            case R.id.tv_quanxuan:
                if (isAllChecked) {
                    noquanxuan();
                } else {
                    quanxuan();
                }
                isAllChecked = !isAllChecked;
                break;
            case R.id.tv_download:
                String userid = SpUtils.getString(MyApplication.getGloableContext(), "id", "");
                if (TextUtils.isEmpty(userid)) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                int apnType = NetWorkUtils.getAPNType(CommentDownActivity.this);
                if (apnType == 0) {
                    Toast.makeText(CommentDownActivity.this, "没有网络呢~", Toast.LENGTH_SHORT).show();
                    return;
                } else if (apnType == 2 || apnType == 3 || apnType == 4) {
                    nowifiallowdown = SpUtils.getBoolean(CommentDownActivity.this, "nowifiallowdown", false);
                    if (!nowifiallowdown) {
                        mTv_content.setText("当前无WiFi，是否允许用流量下载");
                        mNetDialog.show();
                        mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mNetDialog.dismiss();
                                return;
                            }
                        });
                        mNetDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//记住用户允许流量下载
                                SpUtils.putBoolean(CommentDownActivity.this, "nowifiallowdown", true);
                                download();
                            }
                        });
                    }else {
                        download();
                    }
                } else {
                    download();
                }

                break;
        }
    }

    private void download(){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                EveryDayBean.DailysBean childEntity = list.get(i);
                String created_at = childEntity.getCreated_at();
                String[] split = created_at.split(" ");

                List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                listBean.setTypeId("commentId");
                listBean.setChildId(childEntity.getId() + "");
                listBean.setName(childEntity.getVideo_name());
                listBean.setVideoTime(childEntity.getVideo_time());
                listBean.setDate(split[0]);
                listBean.setTime(split[1]);
                listBean.setVideoUrl(childEntity.getVideo_url());
                listBean.setTxtUrl(childEntity.getTxt_url());
                listBean.setIconUrl(childEntity.getT_header());
                listBean.settName(childEntity.getT_name());
                listBean.setParentName("");
                listBean.setH5_url(childEntity.getShare_h5_url());
                listBean.setGood_count(childEntity.getGood_count());
                listBean.setCollect_count(childEntity.getCollect_count());
                listBean.setView_count(childEntity.getView_count());
                listBean.setDianzan(childEntity.isIslive());
                listBean.setCollected(childEntity.isIsfav());
                list.add(listBean);
                DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                        "comment", "commentId", "", childEntity.getT_header(), "", "", list.size() + "", list);
                DownUtil.add(downLoadListsBean);

                        /*DownLoadListBean DownLoadListBean = new DownLoadListBean(-2,childEntity.getChildId(),-4,-3,-1,
                                childEntity.getVideo_name(),childEntity.getVideo_time(), split[0], split[1],
                                childEntity.getVideo_url(), childEntity.getTxt_url(),childEntity.getT_header());
                        DownUtils.add(DownLoadListBean);*/

                GetRequest<File> request = OkGo.<File>get(childEntity.getVideo_url());
                OkDownload.request(downLoadListsBean.getTypeId() + "_" + childEntity.getId(), request)
                        .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download")
                        .fileName(childEntity.getVideo_name() + "_" + childEntity.getId() + ".mp3")
                        .extra3(downLoadListsBean)//额外数据
                        .save()
                        .register(new LogDownloadListener())//当前任务的回调监听
                        .start();
                OkGo.<File>get(childEntity.getTxt_url())
                        .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download"
                                , childEntity.getId() + "-" + childEntity.getVideo_name() + ".txt") {
                            @Override
                            public void onSuccess(Response<File> response) {
                                int code = response.code();
                                if (code >= 200 && code <= 204) {
                                    Logger.e("文稿下载完成");
                                }
                            }
                        });
            }
        }
        EventBean eventBean = new EventBean("number");
        EventBus.getDefault().postSticky(eventBean);
        mNetDialog.dismiss();
    }
}
