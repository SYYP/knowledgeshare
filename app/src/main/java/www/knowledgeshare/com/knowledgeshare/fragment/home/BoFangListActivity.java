package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.db.DownLoadListsBean;
import www.knowledgeshare.com.knowledgeshare.db.DownUtil;
import www.knowledgeshare.com.knowledgeshare.db.HistroyUtils;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DownBean1;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DownBean2;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DownBean3;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.VideoCollectBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.LogDownloadListener;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

import static www.knowledgeshare.com.knowledgeshare.R.id.tv_collect;
import static www.knowledgeshare.com.knowledgeshare.base.UMShareActivity.shareWebUrl;

public class BoFangListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RecyclerView recycler_bofang;
    private LinearLayout activity_my_guanzhu;
    private BaseDialog mDialog;
    private boolean mIsCollected;
    private BaseDialog.Builder mBuilder;
    private boolean mDianzan;
    private List<BofangHistroyBean> mList;
    private LieBiaoAdapter mLieBiaoAdapter;
    private TextView mTv_collect;
    private TextView mTv_dianzan;
    private String mType;
    private TextView mTv_content;
    private boolean nowifiallowdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bo_fang_list);
        initView();
        initDialog();
        initNETDialog();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SlidePopShow();
    }

    private void initDialog() {
        mBuilder = new BaseDialog.Builder(this);
    }

    @Override
    public void finish() {
        //关闭窗体动画显示
        super.finish();
        this.overridePendingTransition(0, R.anim.bottom_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        recycler_bofang = (RecyclerView) findViewById(R.id.recycler_bofang);
        activity_my_guanzhu = (LinearLayout) findViewById(R.id.activity_my_guanzhu);
        recycler_bofang.setLayoutManager(new LinearLayoutManager(this));
        mList = HistroyUtils.search();
        if (mList != null && mList.size() > 0) {
            mLieBiaoAdapter = new LieBiaoAdapter(R.layout.item_free, mList);
            recycler_bofang.setAdapter(mLieBiaoAdapter);
            mLieBiaoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    setISshow(true);
                    BofangHistroyBean item = mList.get(position);
                    if (!item.isLocal()) {
                        PlayerBean playerBean = new PlayerBean(item.getT_header(),
                                item.getVideo_name(), item.getParentName(), item.getVideo_url(), position);
                        gobofang(playerBean);
                    } else {
                        //本地已经下载好的播放
                        PlayerBean playerBean2 = new PlayerBean(item.getT_header(),
                                item.getVideo_name(), item.getParentName(), "", loadFromSDFile(item.getType(),
                                item.getVideo_name() + item.getParentId() + "_" + item.getChildId() + ".mp3"), position);
                        gobofang2(playerBean2);
                    }
                    //设置进入播放主界面的数据
                    List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                    for (int i = 0; i < mList.size(); i++) {
                        BofangHistroyBean bean = mList.get(i);
                        MusicTypeBean musicTypeBean = new MusicTypeBean(bean.getType(),
                                bean.getT_header(), bean.getVideo_name(), bean.getChildId() + "", bean.isCollected());
                        musicTypeBean.setMsg("musicplayertype");
                        musicTypeBeanList.add(musicTypeBean);
                    }
                    MediaService.insertMusicTypeList(musicTypeBeanList);
                    //加入默认的播放列表
                    List<PlayerBean> list = new ArrayList<PlayerBean>();
                    for (int i = 0; i < mList.size(); i++) {
                        BofangHistroyBean entity = mList.get(i);
                        PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_name(), entity.getParentName(), entity.getVideo_url());
                        list.add(playerBean1);
                    }
                    MediaService.insertMusicList(list);
                    //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                    List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                    for (int i = 0; i < mList.size(); i++) {
                        BofangHistroyBean bean = mList.get(i);
                        BofangHistroyBean bofangHistroyBean = new BofangHistroyBean(bean.getType(), bean.getChildId(), bean.getVideo_name(),
                                bean.getCreated_at(), bean.getVideo_url(), bean.getGood_count(),
                                bean.getCollect_count(), bean.getView_count(), bean.isDianzan(), bean.isCollected(),
                                bean.getT_header(), bean.getParentName(),
                                bean.getH5_url(), System.currentTimeMillis(), bean.getParentId(),
                                bean.getParentName(), bean.getTxt_url());
                        histroyBeanList.add(bofangHistroyBean);
                    }
                    MediaService.insertBoFangHistroyList(histroyBeanList);
                }
            });
        }
//        recycler_bofang.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    setPopHide();
//                } else if (dy < 0) {
//                    SlidePopShow();
//                }
//            }
//        });
    }

    private void gobofang2(final PlayerBean playerBean) {
        mMyBinder.setMusicLocal(playerBean);
        ClickPopShow();
        playerBean.setMsg("refreshplayer");
        EventBus.getDefault().postSticky(playerBean);
    }

    private String loadFromSDFile(String type, String fname) {
        fname = "/" + fname;
        String result = null;
        try {
            switch (type) {
                case "free":
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download" + fname);
                    result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download" + fname;
                    break;
                case "everydaycomment":
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download" + fname);
                    result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download" + fname;
                    break;
                case "softmusicdetail":
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download" + fname);
                    result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download" + fname;
                    break;
                case "zhuanlandetail":
                    Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download" + fname);
                    result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download" + fname;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(BoFangListActivity.this, "没有找到指定文件", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private BaseDialog mNetDialog;

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

    private void gobofang(final PlayerBean playerBean) {
        int apnType = NetWorkUtils.getAPNType(this);
        if (apnType == 0) {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
            if (SpUtils.getBoolean(this, "nowifiallowlisten", false)) {//记住用户允许流量播放
                playerBean.setMsg("refreshplayer");
                EventBus.getDefault().postSticky(playerBean);
                mMyBinder.setMusicUrl(playerBean.getVideo_url());
                mMyBinder.playMusic(playerBean);
                mNetDialog.dismiss();
                ClickPopShow();
                SpUtils.putBoolean(this, "nowifiallowlisten", true);
            } else {
                mNetDialog.show();
                mNetDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playerBean.setMsg("refreshplayer");
                        EventBus.getDefault().postSticky(playerBean);
                        mMyBinder.setMusicUrl(playerBean.getVideo_url());
                        mMyBinder.playMusic(playerBean);
                        mNetDialog.dismiss();
                        ClickPopShow();
                        SpUtils.putBoolean(BoFangListActivity.this, "nowifiallowlisten", true);
                    }
                });
                mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNetDialog.dismiss();
                    }
                });
            }
        } else if (NetWorkUtils.isMobileConnected(BoFangListActivity.this)) {
            Toast.makeText(this, "wifi不可用呢~", Toast.LENGTH_SHORT).show();
        } else {
            playerBean.setMsg("refreshplayer");
            EventBus.getDefault().postSticky(playerBean);
            mMyBinder.setMusicUrl(playerBean.getVideo_url());
            mMyBinder.playMusic(playerBean);
            ClickPopShow();
        }
    }

    private void initCollect(String type, String id) {
        HttpParams params = new HttpParams();
        params.put("userid", SpUtils.getString(this, "id", ""));
        if (type.equals("free")) {
            params.put("type", "free");
        } else if (type.equals("everydaycomment")) {
            params.put("type", "daily");
        } else if (type.equals("softmusicdetail")) {
            params.put("type", "xk");
        } else if (type.equals("zhuanlandetail")) {
            params.put("type", "zl");
        }
        params.put("id", id);
        OkGo.<VideoCollectBean>post(MyContants.LXKURL + "video/fav-live")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<VideoCollectBean>(VideoCollectBean.class) {
                    @Override
                    public void onSuccess(Response<VideoCollectBean> response) {
                        int code = response.code();
                        VideoCollectBean videoCollectBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            VideoCollectBean.DataEntity dataEntity = videoCollectBean.getData().get(0);
                            mDianzan = dataEntity.isIslive();
                            if (mDianzan) {
                                Drawable drawable = getResources().getDrawable(R.drawable.dianzan_shixin);
                                /// 这一步必须要做,否则不会显示.
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
                            } else {
                                Drawable drawable = getResources().getDrawable(R.drawable.dianzan_yellow_big);
                                /// 这一步必须要做,否则不会显示.
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
                            }
                            //是否收藏
                            mIsCollected = dataEntity.isIsfav();
                            if (mIsCollected) {
                                Drawable drawable = getResources().getDrawable(R.drawable.collect_shixin);
                                /// 这一步必须要做,否则不会显示.
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                mTv_collect.setCompoundDrawables(null, drawable, null, null);
                            } else {
                                Drawable drawable = getResources().getDrawable(R.drawable.bofanglist_collect);
                                /// 这一步必须要做,否则不会显示.
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                mTv_collect.setCompoundDrawables(null, drawable, null, null);
                            }
                        } else {
                        }
                    }
                });
    }

    private void showListDialog(final int adapterPosition, final int id, String type) {
        mDialog = mBuilder.setViewId(R.layout.dialog_free)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.BOTTOM)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showShareDialog(adapterPosition);
            }
        });
        mTv_collect = mDialog.getView(tv_collect);
        mTv_dianzan = mDialog.getView(R.id.tv_dianzan);
        initCollect(type, id + "");
        mTv_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDianzan(adapterPosition, id);
            }
        });
        mTv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCollect(adapterPosition, id);
            }
        });
        mDialog.getView(R.id.tv_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDownload(id);
                mDialog.dismiss();
            }
        });
    }

    private void goDownload(final int id) {
        String userid = SpUtils.getString(MyApplication.getGloableContext(), "id", "");
        if (TextUtils.isEmpty(userid)) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        int apnType = NetWorkUtils.getAPNType(this);
        if (apnType == 0) {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
            return;
        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
            nowifiallowdown = SpUtils.getBoolean(this, "nowifiallowdown", false);
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
                        SpUtils.putBoolean(BoFangListActivity.this, "nowifiallowdown", true);
                        download(id);
                    }
                });
            } else {
                download(id);
            }
        } else {
            download(id);
        }
    }

    private void download(int id) {
        HttpParams params = new HttpParams();
        params.put("id", id + "");
        String type = mType;
        if (type.equals("free")) {
            params.put("video_type", "free");
        } else if (type.equals("everydaycomment")) {
            params.put("video_type", "daily");
        } else if (type.equals("softmusicdetail")) {
            params.put("video_type", "xk");
        } else if (type.equals("zhuanlandetail")) {
            params.put("video_type", "zl");
        }

        if (type.equals("free")) {
            OkGo.<DownBean1>post(MyContants.LXKURL + "down")
                    .tag(this)
                    .params(params)
                    .execute(new JsonCallback<DownBean1>(DownBean1.class) {
                                 @Override
                                 public void onSuccess(Response<DownBean1> response) {
                                     int code = response.code();
                                     DownBean1 downBean1 = response.body();
                                     List<DownBean1.DataEntity> data = downBean1.getData();
                                     DownBean1.DataEntity dataEntity = data.get(0);
                                     List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                     DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                     listBean.setTypeId("freeId");
                                     listBean.setChildId(dataEntity.getId() + "");
                                     listBean.setName(dataEntity.getVideo_name());
                                     listBean.setVideoTime(dataEntity.getVideo_time());
                                     listBean.setDate("");
                                     listBean.setTime(dataEntity.getVideo_time());
                                     listBean.setVideoUrl(dataEntity.getVideo_url());
                                     listBean.setTxtUrl(dataEntity.getTxt_url());
                                     listBean.setIconUrl(dataEntity.getImage());
                                     list.add(listBean);
                                     if (MyUtils.isHaveFile("free", dataEntity.getVideo_name() + listBean.getTypeId() + "_" + dataEntity.getId() + ".mp3")) {
                                         Toast.makeText(BoFangListActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                     DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                             "free", listBean.getTypeId() + "", "", dataEntity.getImage(), "", "", list.size() + "", list);
                                     DownUtil.add(downLoadListsBean);
                                     GetRequest<File> request = OkGo.<File>get(dataEntity.getVideo_url());
                                     OkDownload.request(listBean.getTypeId() + "_" + dataEntity.getId(), request)
                                             .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download")
                                             .fileName(dataEntity.getVideo_name() + listBean.getTypeId() + "_" + dataEntity.getId() + ".mp3")
                                             .extra3(downLoadListsBean)//额外数据
                                             .save()
                                             .register(new LogDownloadListener())//当前任务的回调监听
                                             .start();

                                     OkGo.<File>get(dataEntity.getTxt_url())
                                             .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/free_download"
                                                     , listBean.getTypeId() + "-" + dataEntity.getId() + dataEntity.getVideo_name() + ".txt") {
                                                 @Override
                                                 public void onSuccess(Response<File> response) {
                                                     int code = response.code();
                                                     if (code >= 200 && code <= 204) {
                                                         Logger.e("文稿下载完成");
                                                     }
                                                 }
                                             });
                                     EventBean eventBean = new EventBean("number");
                                     EventBus.getDefault().postSticky(eventBean);
                                 }
                             }
                    );
        } else if (type.equals("everydaycomment")) {
            OkGo.<DownBean1>post(MyContants.LXKURL + "down")
                    .tag(this)
                    .params(params)
                    .execute(new JsonCallback<DownBean1>(DownBean1.class) {
                                 @Override
                                 public void onSuccess(Response<DownBean1> response) {
                                     int code = response.code();
                                     DownBean1 downBean1 = response.body();
                                     List<DownBean1.DataEntity> data = downBean1.getData();
                                     DownBean1.DataEntity dataEntity = data.get(0);
                                     List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                     DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                     listBean.setTypeId("commentId");
                                     listBean.setChildId(dataEntity.getId() + "");
                                     listBean.setName(dataEntity.getVideo_name());
                                     listBean.setVideoTime(dataEntity.getVideo_time());
                                     listBean.setDate("");
                                     listBean.setTime(dataEntity.getVideo_time());
                                     listBean.setVideoUrl(dataEntity.getVideo_url());
                                     listBean.setTxtUrl(dataEntity.getTxt_url());
                                     listBean.setIconUrl(dataEntity.getImage());
                                     list.add(listBean);
                                     if (MyUtils.isHaveFile("comment", dataEntity.getVideo_name() + listBean.getTypeId() + "_" + dataEntity.getId() + ".mp3")) {
                                         Toast.makeText(BoFangListActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                     DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                             "comment", listBean.getTypeId() + "", "", dataEntity.getImage(), "", "", list.size() + "", list);
                                     DownUtil.add(downLoadListsBean);
                                     GetRequest<File> request = OkGo.<File>get(dataEntity.getVideo_url());
                                     OkDownload.request(listBean.getTypeId() + "_" + dataEntity.getId(), request)
                                             .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download")
                                             .fileName(dataEntity.getVideo_name() + listBean.getTypeId() + "_" + dataEntity.getId() + ".mp3")
                                             .extra3(downLoadListsBean)//额外数据
                                             .save()
                                             .register(new LogDownloadListener())//当前任务的回调监听
                                             .start();

                                     OkGo.<File>get(dataEntity.getTxt_url())
                                             .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/comment_download"
                                                     , listBean.getTypeId() + "-" + dataEntity.getId() + dataEntity.getVideo_name() + ".txt") {
                                                 @Override
                                                 public void onSuccess(Response<File> response) {
                                                     int code = response.code();
                                                     if (code >= 200 && code <= 204) {
                                                         Logger.e("文稿下载完成");
                                                     }
                                                 }
                                             });
                                     EventBean eventBean = new EventBean("number");
                                     EventBus.getDefault().postSticky(eventBean);
                                 }
                             }
                    );
        } else if (type.equals("softmusicdetail")) {
            OkGo.<DownBean2>post(MyContants.LXKURL + "down")
                    .tag(this)
                    .params(params)
                    .execute(new JsonCallback<DownBean2>(DownBean2.class) {
                                 @Override
                                 public void onSuccess(Response<DownBean2> response) {
                                     int code = response.code();
                                     DownBean2 downBean2 = response.body();
                                     DownBean2.DataEntity dataEntity = downBean2.getData().get(0);
                                     DownBean2.DataEntity.XkDataEntity xk_data = dataEntity.getXk_data();
                                     List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                     DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                     listBean.setTypeId(dataEntity.getId() + "");
                                     listBean.setChildId(xk_data.getId() + "");
                                     listBean.setName(xk_data.getName());
                                     listBean.setVideoTime(xk_data.getVideo_time());
                                     listBean.setDate("");
                                     listBean.setTime(xk_data.getVideo_time());
                                     listBean.setVideoUrl(xk_data.getVideo_url());
                                     listBean.setTxtUrl(xk_data.getTxt_url());
                                     listBean.setIconUrl(dataEntity.getXk_image());
                                     listBean.settName(xk_data.getT_name());
                                     list.add(listBean);
                                     if (MyUtils.isHaveFile("xiaoke", xk_data.getName() + dataEntity.getId() + "_" + xk_data.getId() + ".mp3")) {
                                         Toast.makeText(BoFangListActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                     DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                             "xiaoke", dataEntity.getId() + "", dataEntity.getXk_name(), dataEntity.getXk_image(),
                                             dataEntity.getT_name(), dataEntity.getXk_teacher_tags(), "1", list);
                                     DownUtil.add(downLoadListsBean);
                                     GetRequest<File> request = OkGo.<File>get(xk_data.getVideo_url());
                                     OkDownload.request(dataEntity.getId() + "_" + xk_data.getId(), request)
                                             .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download")
                                             .fileName(xk_data.getName() + dataEntity.getId() + "_" + xk_data.getId() + ".mp3")
                                             .extra3(downLoadListsBean)
                                             .save()
                                             .register(new LogDownloadListener())//当前任务的回调监听
                                             .start();
                                     OkGo.<File>get(xk_data.getTxt_url())
                                             .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/xk_download"
                                                     , dataEntity.getId() + "-" + xk_data.getId() + xk_data.getName() + ".txt") {
                                                 @Override
                                                 public void onSuccess(Response<File> response) {
                                                     int code = response.code();
                                                     if (code >= 200 && code <= 204) {
                                                         Logger.e("文稿下载完成");
                                                     }
                                                 }
                                             });
                                     EventBean eventBean = new EventBean("number");
                                     EventBus.getDefault().postSticky(eventBean);
                                 }
                             }
                    );
        } else if (type.equals("zhuanlandetail")) {
            OkGo.<DownBean3>post(MyContants.LXKURL + "down")
                    .tag(this)
                    .params(params)
                    .execute(new JsonCallback<DownBean3>(DownBean3.class) {
                                 @Override
                                 public void onSuccess(Response<DownBean3> response) {
                                     int code = response.code();
                                     DownBean3 downBean3 = response.body();
                                     DownBean3.DataEntity dataEntity = downBean3.getData().get(0);
                                     DownBean3.DataEntity.ZlDataEntity zl_data = dataEntity.getZl_data();
                                     List<DownLoadListsBean.ListBean> list = new ArrayList<>();
                                     DownLoadListsBean.ListBean listBean = new DownLoadListsBean.ListBean();
                                     listBean.setTypeId(dataEntity.getId() + "");
                                     listBean.setChildId(zl_data.getId() + "");
                                     listBean.setName(zl_data.getName());
                                     listBean.setVideoTime(zl_data.getVideo_time());
                                     listBean.setDate("");
                                     listBean.setTime(zl_data.getVideo_time());
                                     listBean.setVideoUrl(zl_data.getVideo_url());
                                     listBean.setTxtUrl(zl_data.getTxt_url());
                                     listBean.setIconUrl(dataEntity.getZl_image());
                                     listBean.settName(zl_data.getT_name());
                                     list.add(listBean);
                                     if (MyUtils.isHaveFile("zhuanlan", zl_data.getName() + dataEntity.getId() + "_" + zl_data.getId() + ".mp3")) {
                                         Toast.makeText(BoFangListActivity.this, "此音频已下载", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                     DownLoadListsBean downLoadListsBean = new DownLoadListsBean(
                                             "zhuanlan", dataEntity.getId() + "", getIntent().getStringExtra("title"),
                                             dataEntity.getZl_image(),
                                             zl_data.getT_name(), dataEntity.getZl_teacher_tags(), "1", list);
                                     DownUtil.add(downLoadListsBean);
                                     GetRequest<File> request = OkGo.<File>get(zl_data.getVideo_url());
                                     OkDownload.request(dataEntity.getId() + "_" + zl_data.getId(), request)
                                             .folder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/download/zl_download")
                                             .fileName(zl_data.getName() + dataEntity.getId() + "_" + zl_data.getId() + ".mp3")
                                             .extra3(downLoadListsBean)
                                             .save()
                                             .register(new LogDownloadListener())//当前任务的回调监听
                                             .start();
                                     EventBean eventBean = new EventBean("number");
                                     EventBus.getDefault().postSticky(eventBean);
                                 }
                             }
                    );
        }
    }

    private void changeCollect(final int adapterPosition, int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", id + "");
        params.put("type", "1");
        String url = "";
        if (mIsCollected) {
            if (mType.equals("free")) {
                url = MyContants.LXKURL + "free/no-favorite";
            } else if (mType.equals("everydaycomment")) {
                url = MyContants.LXKURL + "daily/no-favorite";
            } else {
                url = MyContants.LXKURL + "xk/no-favorite";
            }
        } else {
            if (mType.equals("free")) {
                url = MyContants.LXKURL + "free/favorite";
            } else if (mType.equals("everydaycomment")) {
                url = MyContants.LXKURL + "daily/favorite";
            } else {
                url = MyContants.LXKURL + "xk/favorite";
            }
        }
        OkGo.<DianZanbean>post(url)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(BoFangListActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 if (mIsCollected) {
                                     Drawable drawable = getResources().getDrawable(R.drawable.bofanglist_collect);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_collect.setCompoundDrawables(null, drawable, null, null);
                                 } else {
                                     Drawable drawable = getResources().getDrawable(R.drawable.collect_shixin);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_collect.setCompoundDrawables(null, drawable, null, null);
                                 }
                                 mIsCollected = !mIsCollected;
                                 mList.get(adapterPosition).setCollected(mIsCollected);
                                 mLieBiaoAdapter.notifyDataSetChanged();
                                 mDialog.dismiss();
                             }
                         }
                );
    }

    private void changeDianzan(final int adapterPosition, int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", id + "");
        String url = "";
        if (mDianzan) {
            if (mType.equals("free")) {
                url = MyContants.LXKURL + "free/no-dianzan";
            } else if (mType.equals("everydaycomment")) {
                url = MyContants.LXKURL + "daily/no-dianzan";
            } else {
                url = MyContants.LXKURL + "xk/no-dianzan";
            }
        } else {
            if (mType.equals("free")) {
                url = MyContants.LXKURL + "free/dianzan";
            } else if (mType.equals("everydaycomment")) {
                url = MyContants.LXKURL + "daily/dianzan";
            } else {
                url = MyContants.LXKURL + "xk/dianzan";
            }
        }
        OkGo.<DianZanbean>post(url)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(BoFangListActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 if (mDianzan) {
                                     Drawable drawable = getResources().getDrawable(R.drawable.dianzan_yellow_big);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
                                 } else {
                                     Drawable drawable = getResources().getDrawable(R.drawable.dianzan_shixin);
                                     /// 这一步必须要做,否则不会显示.
                                     drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                     mTv_dianzan.setCompoundDrawables(null, drawable, null, null);
                                 }
                                 mDianzan = !mDianzan;
                                 mList.get(adapterPosition).setDianzan(mDianzan);
                                 mLieBiaoAdapter.notifyDataSetChanged();
                                 mDialog.dismiss();
                             }
                         }
                );
    }

    private void showShareDialog(final int adapterPosition) {
        mDialog = mBuilder.setViewId(R.layout.dialog_share)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.BOTTOM)
                //设置动画
                .setAnimation(R.style.Bottom_Top_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        mDialog.show();
        mDialog.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        final BofangHistroyBean entity = mLieBiaoAdapter.getData().get(adapterPosition);
        mDialog.getView(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(entity.getH5_url(), entity.getVideo_name(),
                        entity.getT_header(), "", BoFangListActivity.this, SHARE_MEDIA.WEIXIN);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(entity.getH5_url(), entity.getVideo_name(),
                        entity.getT_header(), "", BoFangListActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_zone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(entity.getH5_url(), entity.getVideo_name(),
                        entity.getT_header(), "", BoFangListActivity.this, SHARE_MEDIA.QZONE);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(entity.getH5_url(), entity.getVideo_name(),
                        entity.getT_header(), "", BoFangListActivity.this, SHARE_MEDIA.QQ);
                mDialog.dismiss();
            }
        });
        mDialog.getView(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWebUrl(entity.getH5_url(), entity.getVideo_name(),
                        entity.getT_header(), "", BoFangListActivity.this, SHARE_MEDIA.SINA);
                mDialog.dismiss();
            }
        });
    }

    private class LieBiaoAdapter extends BaseQuickAdapter<BofangHistroyBean, BaseViewHolder> {

        public LieBiaoAdapter(@LayoutRes int layoutResId, @Nullable List<BofangHistroyBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final BofangHistroyBean item) {
            helper.setText(R.id.tv_name, item.getVideo_name())
                    .setText(R.id.tv_time, item.getCreated_at() + "发布")
                    .setText(R.id.tv_look_count, item.getView_count() + "")
                    .setText(R.id.tv_collect_count, item.getCollect_count() + "")
                    .setText(R.id.tv_dianzan_count, item.getGood_count() + "");
            helper.getView(R.id.iv_dian).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mType = item.getType();
                    showListDialog(helper.getAdapterPosition(), item.getChildId(), item.getType());
                }
            });
            helper.getView(R.id.iv_wengao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mType = item.getType();
                    Intent intent = new Intent(BoFangListActivity.this, WenGaoActivity.class);
                    intent.putExtra("type", mType);
                    intent.putExtra("id", item.getChildId() + "");
                    startActivity(intent);
                }
            });
            if (helper.getAdapterPosition() <= 8) {
                helper.setText(R.id.tv_order, "0" + (helper.getAdapterPosition() + 1));
            }else {
                helper.setText(R.id.tv_order, "" + (helper.getAdapterPosition() + 1));
            }
            if (item.getType().equals("zhuanlandetail")) {
                helper.setVisible(R.id.iv_dian, false)
                        .setVisible(R.id.iv_wengao, false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
