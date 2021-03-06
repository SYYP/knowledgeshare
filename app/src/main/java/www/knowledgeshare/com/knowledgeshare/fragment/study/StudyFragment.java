package www.knowledgeshare.com.knowledgeshare.fragment.study;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.NoticeContentActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.bean.NoteListBean;
import www.knowledgeshare.com.knowledgeshare.bean.WeatherBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.BoFangListActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.MusicActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SearchActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.CollectActivity;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.login.MessageActivity;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TimeUtils;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

import static android.content.Context.BIND_AUTO_CREATE;
import static www.knowledgeshare.com.knowledgeshare.R.id.springview;

/**
 * Created by Administrator on 2017/11/17.
 */
public class StudyFragment extends BaseFragment implements View.OnClickListener, AMapLocationListener {
    public TextView tv_search;
    public ImageView iv_message;
    public LinearLayout ll_download;
    public TextView study_weather;
    public TextView study_wendu;
    public LinearLayout liner;
    public TextView study_city;
    public View line;
    public TextView study_title;
    public TextView study_day;
    public TextView study_date;
    public RelativeLayout relative;
    public TextView study_count;
    public LinearLayout study_liner;
    public View lines;
    public TextView study_titles;
    public TextView study_days;
    public TextView study_dates;
    private RecyclerView study_recycler;
    private ImageView study_xinxin;
    boolean bool;
    private TextView study_collect;
    private FrameLayout fram_layout;
    private View rootView;
    int i;
    List<NoteListBean.NoteBean> list = new ArrayList<>();

    private TextView study_name, tv_suishenting;
    private int favoriteId;
    private boolean isfav;
    private SpringView springView;
    private int lastId;
    private RelativeLayout historyRl;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mapLocationClient;
    private LinearLayout noteLl;
    private ImageView study_newNotice;
    private String note_count;
    private TextView messageTv;
    private ImageView iv_delete, iv_bo_head, iv_arrow_top, iv_mulu;
    private TextView tv_title, tv_subtitle;
    private RelativeLayout rl_bofang;
    private MusicTypeBean mMusicTypeBean;
    private LinearLayout ll_root_view;
    private NestedScrollView nestView;
    private Animation delAnimation;

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            String id = SpUtils.getString(mContext, "id", "");
            if (TextUtils.isEmpty(id)) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                mActivity.overridePendingTransition(R.anim.start_anim, R.anim.close_anim);
                return;
            }
        }
        springView.callFresh();
        requestNoteList("");
        study_name.setText("Hi  " + SpUtils.getString(mContext, "name", ""));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent2(EventBean eventBean) {
        if (eventBean.getMsg().equals("home_bofang")) {
            rl_bofang.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.GONE);
        } else if (eventBean.getMsg().equals("home_pause")) {
            iv_delete.setVisibility(View.VISIBLE);
        } else if (eventBean.getMsg().equals("home_close")) {
            rl_bofang.setVisibility(View.GONE);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ll_root_view.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            ll_root_view.setLayoutParams(layoutParams);
        } else if (eventBean.getMsg().equals("norotate")) {
            rl_bofang.setVisibility(View.GONE);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ll_root_view.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            ll_root_view.setLayoutParams(layoutParams);
        } else if (eventBean.getMsg().equals("allmusiccomplete")) {
            iv_delete.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent3(PlayerBean playerBean) {
        if (playerBean.getMsg().equals("refreshplayer")) {
            if (playerBean == null) {
                return;
            }
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_in);
            rl_bofang.startAnimation(animation);
            rl_bofang.setVisibility(View.VISIBLE);
            tv_title.setText(playerBean.getTitle());
            tv_subtitle.setText(playerBean.getSubtitle());
            if (!NetWorkUtils.isNetworkConnected(mContext)) {
                iv_bo_head.setImageResource(R.drawable.home_default_xk);
            } else {
                Glide.with(MyApplication.getGloableContext()).load(playerBean.getTeacher_head()).into(iv_bo_head);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(MusicTypeBean musicTypeBean) {
        if (musicTypeBean.getMsg().equals("musicplayertype")) {
            mMusicTypeBean = musicTypeBean;
        }
    }

    @Override
    protected View initView() {
        rootView = View.inflate(mContext, R.layout.fragment_study, null);
        iv_delete = rootView.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
        iv_delete.setVisibility(View.GONE);
        iv_arrow_top = rootView.findViewById(R.id.iv_arrow_top);
        iv_arrow_top.setOnClickListener(this);
        iv_mulu = rootView.findViewById(R.id.iv_mulu);
        iv_mulu.setOnClickListener(this);
        iv_bo_head = rootView.findViewById(R.id.iv_bo_head);
        tv_title = rootView.findViewById(R.id.tv_title);
        tv_subtitle = rootView.findViewById(R.id.tv_subtitle);
        rl_bofang = rootView.findViewById(R.id.rl_bofang);
        rl_bofang.setOnClickListener(this);
        nestView = rootView.findViewById(R.id.nestView);
        rl_bofang.setVisibility(View.GONE);
        ll_root_view = rootView.findViewById(R.id.ll_root_view);
        tv_search = (TextView) rootView.findViewById(R.id.tv_search);
        fram_layout = rootView.findViewById(R.id.frame_layout);
        this.iv_message = (ImageView) rootView.findViewById(R.id.iv_message);
        messageTv = rootView.findViewById(R.id.message_tv);
        this.ll_download = (LinearLayout) rootView.findViewById(R.id.ll_download);
        this.study_name = rootView.findViewById(R.id.study_name);
        this.study_weather = (TextView) rootView.findViewById(R.id.study_weather);
        this.study_wendu = (TextView) rootView.findViewById(R.id.study_wendu);
        this.liner = (LinearLayout) rootView.findViewById(R.id.liner);
        this.study_city = (TextView) rootView.findViewById(R.id.study_city);
        this.line = (View) rootView.findViewById(R.id.line);
        this.study_title = (TextView) rootView.findViewById(R.id.study_title);
        this.study_day = (TextView) rootView.findViewById(R.id.study_day);
        this.study_date = (TextView) rootView.findViewById(R.id.study_date);
        this.relative = (RelativeLayout) rootView.findViewById(R.id.relative);
        this.study_count = (TextView) rootView.findViewById(R.id.study_count);
        this.study_liner = (LinearLayout) rootView.findViewById(R.id.study_liner);
        this.lines = (View) rootView.findViewById(R.id.lines);
        this.study_titles = (TextView) rootView.findViewById(R.id.study_titles);
        this.study_dates = (TextView) rootView.findViewById(R.id.study_dates);
        this.tv_suishenting = (TextView) rootView.findViewById(R.id.tv_suishenting);
        tv_suishenting.setOnClickListener(this);
        this.springView = rootView.findViewById(springview);
        this.historyRl = rootView.findViewById(R.id.history_rl);
        this.noteLl = rootView.findViewById(R.id.note_ll);
        study_recycler = rootView.findViewById(R.id.study_recycle);
        study_collect = rootView.findViewById(R.id.study_collect);
        study_xinxin = rootView.findViewById(R.id.study_xinxin);
        study_newNotice = rootView.findViewById(R.id.study_new_notice);
        study_recycler.setNestedScrollingEnabled(false);
        study_name.setText("Hi  " + SpUtils.getString(mContext, "name", ""));
        EventBus.getDefault().register(this);
        initMap();
        initLoadMore();
        requestNoteList("");
        initMusic();
        study_xinxin.setOnClickListener(this);
        fram_layout.setOnClickListener(this);
        //搜索
        tv_search.setOnClickListener(this);
        //收藏
        study_collect.setOnClickListener(this);
        //新增笔记
        study_newNotice.setOnClickListener(this);
        initListener();
        delAnimation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_out);
        return rootView;
    }

    private void initListener() {
        nestView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!mMyBinder.isClosed()) {
                    if (scrollY - oldScrollY > 0) {
                        //                        Animation animation=AnimationUtils.loadAnimation(mContext, R.anim.bottom_out);
                        //                        rl_bofang.startAnimation(animation);
                        rl_bofang.setVisibility(View.GONE);
                    } else if (scrollY - oldScrollY < 0) {
                        //                        Animation animation=AnimationUtils.loadAnimation(mContext, R.anim.bottom_in);
                        //                        rl_bofang.startAnimation(animation);
                        rl_bofang.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }


    private void initMap() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        AMapLocationClientOption option = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，
        // setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        mapLocationClient = new AMapLocationClient(mContext);
        //给定位客户端对象设置定位参数
        mapLocationClient.setLocationOption(mLocationOption);
        mapLocationClient.setLocationListener(this);
        //启动定位
        mapLocationClient.startLocation();


    }

    private void initLoadMore() {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (!NetWorkUtils.isNetworkConnected(mContext)) {
                    Toast.makeText(mContext, "当前无网络连接，请检查设置", Toast.LENGTH_SHORT).show();
                    springView.onFinishFreshAndLoad();
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestNoteList("");
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }

            @Override
            public void onLoadmore() {
                if (!NetWorkUtils.isNetworkConnected(mContext)) {
                    Toast.makeText(mContext, "当前无网络连接，请检查设置", Toast.LENGTH_SHORT).show();
                    springView.onFinishFreshAndLoad();
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestNoteList("");
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }
        });
        springView.setHeader(new MyHeader(mContext));
        springView.setFooter(new MyFooter(mContext));
    }

    private void requestDayFavorite() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", favoriteId);
        OkGo.<BaseBean>post(MyContants.dayFavorite)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204) {
                            study_xinxin.setImageResource(R.drawable.xinxin);
                            TUtils.showShort(mContext, response.body().getMessage());
                            isfav = true;
                        } else {
                            TUtils.showShort(mContext, response.body().getMessage());
                        }
                    }
                });
    }

    private void requestDayNoFav() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", favoriteId);

        OkGo.<BaseBean>post(MyContants.dayNoFacorite)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204) {
                            study_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                            TUtils.showShort(mContext, response.body().getMessage());
                            isfav = false;
                        } else {
                            TUtils.showShort(mContext, response.body().getMessage());
                        }
                    }
                });
    }

    private void requestNoteList(String after) {
        if (!NetWorkUtils.isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
            historyRl.setVisibility(View.GONE);
            return;
        }else {
            historyRl.setVisibility(View.VISIBLE);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        HttpParams params = new HttpParams();
        //        params.put("after", after);
        Logger.e(after);
        OkGo.<NoteListBean>post(MyContants.noteList)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<NoteListBean>(mActivity, NoteListBean.class) {
                    @Override
                    public void onSuccess(Response<NoteListBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204) {
                            note_count = response.body().getNote_count();
                            if (!note_count.equals("0")) {
                                messageTv.setVisibility(View.VISIBLE);
                                messageTv.setText(note_count);
                            } else {
                                messageTv.setVisibility(View.GONE);
                                messageTv.setText("");
                            }
                            list = response.body().getNote();
                            List<NoteListBean.GoldBean> gold = response.body().getGold();
                            if (list.size() > 0) {
                                study_recycler.setVisibility(View.VISIBLE);
                            } else {
                                study_recycler.setVisibility(View.GONE);
                            }
                            Studyadapter studyadapter = new Studyadapter(getActivity(), list);
                            study_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                            study_recycler.setAdapter(studyadapter);

                            if (gold.size() > 0) {
                                historyRl.setVisibility(View.VISIBLE);
                                study_day.setText(gold.get(0).getDay());
                                study_date.setText(gold.get(0).getDisplay_at());
                                study_count.setText(gold.get(0).getContent());
                                favoriteId = gold.get(0).getId();
                                isfav = gold.get(0).isIsfav();
                                if (isfav) {
                                    study_xinxin.setImageResource(R.drawable.xinxin);
                                } else {
                                    study_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                                }
                            } else {
                                historyRl.setVisibility(View.GONE);
                            }

                            study_dates.setText(TimeUtils.getNowTime());
                            springView.onFinishFreshAndLoad();
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void myEvent(EventBean eventBean) {
        if (eventBean.getMsg().equals("noticerefrash")) {
            requestNoteList("");
        }
        if (eventBean.getMsg().equals("studycount")) {
            requestNoteList("");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_suishenting:
                if (!NetWorkUtils.isNetworkConnected(mContext)) {
                    Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                suishenting();
                break;
            case R.id.iv_delete:
                rl_bofang.startAnimation(delAnimation);
                rl_bofang.setVisibility(View.GONE);
                EventBean eventBean = new EventBean("norotate");
                EventBus.getDefault().postSticky(eventBean);
                break;
            case R.id.iv_arrow_top:
                rl_bofang.startAnimation(delAnimation);
                rl_bofang.setVisibility(View.GONE);
                Intent intent1 = new Intent(mContext, MusicActivity.class);
                intent1.putExtra("data", mMusicTypeBean);
                intent1.putExtra("title", mMusicTypeBean.getVideo_name());
                startActivity(intent1);
                mActivity.overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                break;
            case R.id.iv_mulu:
                Intent intent11 = new Intent(mContext, BoFangListActivity.class);
                startActivity(intent11);
                break;
            case R.id.study_xinxin:
                if (!NetWorkUtils.isNetworkConnected(mContext)) {
                    Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isfav) {
                    requestDayFavorite();
                } else {
                    requestDayNoFav();
                }
                break;
            case R.id.frame_layout:
                if (!NetWorkUtils.isNetworkConnected(mContext)) {
                    Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.tv_search:
                if (!NetWorkUtils.isNetworkConnected(mContext)) {
                    Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
            case R.id.study_collect:
                if (!NetWorkUtils.isNetworkConnected(mContext)) {
                    Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.study_new_notice:
                if (!NetWorkUtils.isNetworkConnected(mContext)) {
                    Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(mContext, EditNoticeActivity.class));
                break;
        }
    }

    //“绑定”服务的intent
    private Intent MediaServiceIntent;
    private MediaService.MyBinder mMyBinder;

    private void initMusic() {
        MediaServiceIntent = new Intent(mContext, MediaService.class);
        //        mContext.startService(MediaServiceIntent);
        mContext.bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MediaService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (mMyBinder != null && !mMyBinder.isClosed()) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_in);
            rl_bofang.startAnimation(animation);
            rl_bofang.setVisibility(View.VISIBLE);
        }
    }

    private void suishenting() {
        EventBus.getDefault().post(new EventBean("suishenting"));
        /*HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        OkGo.<SuiShenTingBean>post(MyContants.LXKURL + "user/walkman")
                .tag(this)
                .headers(headers)
                .execute(new JsonCallback<SuiShenTingBean>(SuiShenTingBean.class) {
                             @Override
                             public void onSuccess(Response<SuiShenTingBean> response) {
                                 int code = response.code();
                                 SuiShenTingBean tingBean = response.body();
                                 SuiShenTingBean.DataEntity data = tingBean.getData();
                                 List<SuiShenTingBean.DataEntity.DailyEntity> daily = data.getDaily();
                                 List<SuiShenTingBean.DataEntity.FreeEntity> free = data.getFree();

                                 SuiShenTingBean.DataEntity.FreeEntity item = free.get(0);//先播放免费专区的第一首
                                 PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getVideo_name(), item.getT_tag(), item.getVideo_url());
                                 gobofang(playerBean);
                                 MusicTypeBean musicTypeBean = new MusicTypeBean("free",
                                         item.getT_header(), item.getVideo_name(), item.getChildId() + "", item.isIsfav());
                                 musicTypeBean.setMsg("musicplayertype");
                                 EventBus.getDefault().postSticky(musicTypeBean);
                                 List<PlayerBean> list = new ArrayList<PlayerBean>();
                                 for (int i = 0; i < daily.size(); i++) {
                                     SuiShenTingBean.DataEntity.DailyEntity entity = daily.get(i);
                                     PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getVideo_name(), entity.getT_tag(), entity.getVideo_url());
                                     list.add(playerBean1);
                                 }
                                 MediaService.insertMusicList(list);
                                 if (!HistroyUtils.isInserted(item.getVideo_name())) {
                                     BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("free", item.getChildId(), item.getVideo_name(),
                                             item.getCreated_at(), item.getVideo_url(), item.getGood_count(),
                                             item.getCollect_count(), item.getView_count(), item.getIs_good() == 1 ? true : false,
                                             item.isIsfav(), item.getT_header(), item.getT_tag(),item.getShare_h5_url());
                                     HistroyUtils.add(bofangHistroyBean);
                                 }
                             }
                         }
                );*/
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                study_city.setText(aMapLocation.getCity() + aMapLocation.getDistrict());
                requsetWeather(aMapLocation.getCity());
                //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
                /*WeatherSearchQuery mquery = new WeatherSearchQuery(aMapLocation.getCity(), WeatherSearchQuery.WEATHER_TYPE_LIVE);
                mweathersearch = new WeatherSearch(mContext);
                mweathersearch.setOnWeatherSearchListener(this);
                mweathersearch.setQuery(mquery);
                mweathersearch.searchWeatherAsyn(); //异步搜索*/

                Logger.e(aMapLocation.getLocationType() + "\n" + aMapLocation.getLatitude() + "\n" + aMapLocation.getLongitude() + "\n"
                        + aMapLocation.getAccuracy() + "\n" + aMapLocation.getAddress() + "\n" + aMapLocation.getProvince() + "\n"
                        + aMapLocation.getCity() + "\n" + aMapLocation.getDistrict() + "\n" + aMapLocation.getStreet() + "\n"
                        + aMapLocation.getCityCode() + "\n" + aMapLocation.getAdCode() + "\n" + aMapLocation.getAoiName() + "\n"
                        + aMapLocation.getBuildingId() + "\n" + aMapLocation.getFloor() + "\n" + aMapLocation.getGpsAccuracyStatus() + "\n");
                mapLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    private void requsetWeather(String city) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "APPCODE 7142db13615540c4896b7963bb8c78be");
        HttpParams params = new HttpParams();
        params.put("city", city);
        OkGo.<WeatherBean>get("http://jisutqybmf.market.alicloudapi.com/weather/query")
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<WeatherBean>(WeatherBean.class) {
                    @Override
                    public void onSuccess(Response<WeatherBean> response) {
                        WeatherBean body = response.body();
                        if (body != null && body.getStatus() != null) {
                            if (body.getStatus().equals("0")) {
                                study_weather.setText(body.getResult().getWeather());
                                study_wendu.setText(body.getResult().getTemp() + "℃");
                            } else {
                                Logger.e(body.getMsg());
                            }
                        }
                    }
                });
    }

    class Studyadapter extends RecyclerView.Adapter<Studyadapter.MyViewholder> {
        private Context context;
        List<NoteListBean.NoteBean> list = new ArrayList<>();
        boolean bool;

        public Studyadapter(Context context, List<NoteListBean.NoteBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewholder myViewholder = new MyViewholder(LayoutInflater.from(context).inflate(R.layout.recyview_studyitem, parent, false));
            return myViewholder;
        }

        @Override
        public void onBindViewHolder(final MyViewholder holder, final int position) {

            lastId = list.get(position).getId();
            holder.item_title.setText(list.get(position).getTitle());
            holder.item_count.setText(list.get(position).getContent());
            holder.item_count.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    holder.item_count.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (holder.item_count.getLineCount() == 5) {
                        holder.item_look.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
            //删除
            holder.item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTips(list.get(position).getId(), position);
                }
            });

            //编辑
            holder.item_compile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NoticeContentActivity.class);
                    intent.putExtra("content", holder.item_count.getText().toString());
                    intent.putExtra("id", list.get(position).getId());
                    startActivity(intent);
                }
            });

            //查看更多
            holder.item_look.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NoticeContentActivity.class);
                    intent.putExtra("content", holder.item_count.getText().toString());
                    intent.putExtra("id", list.get(position).getId());
                    startActivity(intent);
                }
            });
        }

        private void showTips(final int id, final int position) {
            new NormalAlertDialog.Builder(context)
                    .setTitleVisible(true).setTitleText("提示")
                    .setTitleTextColor(R.color.text_black)
                    .setContentText("是否删除笔记？")
                    .setContentTextColor(R.color.text_black)
                    .setLeftButtonText("是")
                    .setLeftButtonTextColor(R.color.text_black)
                    .setRightButtonText("否")
                    .setRightButtonTextColor(R.color.text_black)
                    .setSingleButtonTextColor(R.color.text_black)
                    .setCanceledOnTouchOutside(false)
                    .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                        @Override
                        public void clickLeftButton(NormalAlertDialog dialog, View view) {
                            requestDelNote(id, position);
                            dialog.dismiss();
                        }

                        @Override
                        public void clickRightButton(NormalAlertDialog dialog, View view) {

                            dialog.dismiss();
                        }
                    })
                    .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                        @Override
                        public void clickSingleButton(NormalAlertDialog dialog, View view) {
                            dialog.dismiss();
                        }
                    })
                    .build()
                    .show();
        }

        private void requestDelNote(int id, final int position) {
            HttpHeaders headers = new HttpHeaders();
            headers.put("Authorization", "Bearer " + SpUtils.getString(context, "token", ""));
            HttpParams params = new HttpParams();
            params.put("id", id);
            OkGo.<BaseBean>post(MyContants.delNote)
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                        @Override
                        public void onSuccess(Response<BaseBean> response) {
                            int code = response.code();
                            if (code >= 200 && code <= 204) {
                                TUtils.showShort(context, response.body().getMessage());
                                list.remove(position);
                                notifyDataSetChanged();
                            }
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return list != null ? list.size() : 0;
        }

        class MyViewholder extends RecyclerView.ViewHolder {
            TextView item_title;
            TextView item_delete;
            TextView item_compile;
            TextView item_count;
            TextView item_look;
            RelativeLayout study_liner;

            MyViewholder(View rootView) {
                super(rootView);
                item_title = (TextView) rootView.findViewById(R.id.item_title);
                item_delete = (TextView) rootView.findViewById(R.id.item_delete);
                item_compile = (TextView) rootView.findViewById(R.id.item_compile);
                item_count = (TextView) rootView.findViewById(R.id.item_count);
                item_look = rootView.findViewById(R.id.item_look);
                study_liner = rootView.findViewById(R.id.study_liner);
            }
        }

        /*
          通过反射禁止弹出粘贴框
         */
        private void setInsertionDisabled(EditText editText) {
            try {
                Field editorField = TextView.class.getDeclaredField("mEditor");
                editorField.setAccessible(true);
                Object editorObject = editorField.get(editText);

                // if this view supports insertion handles
                Class editorClass = Class.forName("android.widget.Editor");
                Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
                mInsertionControllerEnabledField.setAccessible(true);
                mInsertionControllerEnabledField.set(editorObject, false);

                // if this view supports selection handles
                Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
                mSelectionControllerEnabledField.setAccessible(true);
                mSelectionControllerEnabledField.set(editorObject, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
