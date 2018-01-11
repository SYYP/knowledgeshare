package www.knowledgeshare.com.knowledgeshare.fragment.study;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.bean.NoteListBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SearchActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.CollectActivity;
import www.knowledgeshare.com.knowledgeshare.login.MessageActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SoftKeyboardTool;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TimeUtils;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

/**
 * Created by Administrator on 2017/11/17.
 */
public class StudyFragment extends BaseFragment implements View.OnClickListener, AMapLocationListener, WeatherSearch.OnWeatherSearchListener {
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
    private WeatherSearch mweathersearch;
    private LinearLayout noteLl;

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

        }
    }

    @Override
    protected View initView() {
        rootView = View.inflate(mContext, R.layout.fragment_study, null);
        tv_search = (TextView) rootView.findViewById(R.id.tv_search);
        fram_layout = rootView.findViewById(R.id.frame_layout);
        this.iv_message = (ImageView) rootView.findViewById(R.id.iv_message);
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
        this.springView = rootView.findViewById(R.id.springview);
        this.historyRl = rootView.findViewById(R.id.history_rl);
        this.noteLl = rootView.findViewById(R.id.note_ll);
        study_recycler = rootView.findViewById(R.id.study_recycle);
        study_collect = rootView.findViewById(R.id.study_collect);
        study_xinxin = rootView.findViewById(R.id.study_xinxin);
        study_recycler.setNestedScrollingEnabled(false);
        study_name.setText("Hi  "+SpUtils.getString(mContext,"name",""));
        initMap();
        initLoadMore();
        requestNoteList("");

        study_xinxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isfav) {
                    requestDayFavorite();
                } else {
                    requestDayNoFav();
                }
            }

        });

        fram_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
        //搜索
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, SearchActivity.class));
            }
        });
        //收藏
        study_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestNoteList("");
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestNoteList("");
                        springView.onFinishFreshAndLoad();
                    }
                }, 2000);
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
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(mContext, "token", ""));
        HttpParams params = new HttpParams();
        params.put("after", after);
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
                            list = response.body().getNote();
                            List<NoteListBean.GoldBean> gold = response.body().getGold();
                            if (list.size() > 0) {
                                noteLl.setVisibility(View.VISIBLE);
                            } else {
                                noteLl.setVisibility(View.GONE);
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

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_suishenting:
                suishenting();
                break;
        }
    }

    private void suishenting() {
        EventBus.getDefault().post(new EventBean("morenbofang"));
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
                study_city.setText(aMapLocation.getCity()+aMapLocation.getDistrict());
                //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
                WeatherSearchQuery mquery = new WeatherSearchQuery(aMapLocation.getCity(),WeatherSearchQuery.WEATHER_TYPE_LIVE);
                mweathersearch = new WeatherSearch(mContext);
                mweathersearch.setOnWeatherSearchListener(this);
                mweathersearch.setQuery(mquery);
                mweathersearch.searchWeatherAsyn(); //异步搜索

                Logger.e(aMapLocation.getLocationType()+"\n" +aMapLocation.getLatitude()+"\n" +aMapLocation.getLongitude()+"\n"
                        +aMapLocation.getAccuracy()+"\n" +aMapLocation.getAddress()+"\n" +aMapLocation.getProvince()+"\n"
                        +aMapLocation.getCity()+"\n" +aMapLocation.getDistrict()+"\n" +aMapLocation.getStreet()+"\n"
                        +aMapLocation.getCityCode()+"\n" +aMapLocation.getAdCode()+"\n" +aMapLocation.getAoiName()+"\n"
                        +aMapLocation.getBuildingId()+"\n" +aMapLocation.getFloor()+"\n" +aMapLocation.getGpsAccuracyStatus()+"\n");
                mapLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult , int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                study_weather.setText(weatherlive.getWeather());
                study_wendu.setText(weatherlive.getTemperature()+"°");
//                wind.setText(weatherlive.getWindDirection()+"风     "+weatherlive.getWindPower()+"级");
//                humidity.setText("湿度         "+weatherlive.getHumidity()+"%");
            }else {
                TUtils.showShort(mContext,"没有天气信息");
            }
        }else {
            TUtils.showShort(mContext,"获取天气信息失败");
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

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
                    if (bool) {
                        holder.item_count.setFocusableInTouchMode(false);
                        holder.item_count.setFocusable(false);
                        Drawable drawable = context.getResources().getDrawable(R.drawable.study_bianji);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        holder.item_compile.setCompoundDrawables(drawable, null, null, null);
                        holder.item_compile.setText("编辑");
                        //关闭键盘
                        SoftKeyboardTool.closeKeyboard(holder.item_count);
                        MyApplication.stopClearClip();
                        requesteditNote(list.get(position).getId(), holder.item_count.getText().toString());
                    } else {
                        holder.item_count.setFocusableInTouchMode(true);
                        holder.item_count.setFocusable(true);
                        Drawable drawable = context.getResources().getDrawable(R.drawable.study_finish);
                        /// 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        holder.item_compile.setCompoundDrawables(drawable, null, null, null);
                        holder.item_compile.setText("完成");
                        MyApplication.startClearClip(context);
                        holder.item_count.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                setInsertionDisabled(holder.item_count);
                                return false;
                            }
                        });
                    }
                    bool = !bool;
                    holder.item_count.setTextIsSelectable(false);
                    holder.item_count.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                            return false;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                            return false;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                            return false;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode actionMode) {

                        }
                    });
                }
            });
        }

        private void showTips(final int id, final int position) {
            new NormalAlertDialog.Builder(context)
                    .setTitleVisible(true).setTitleText("提示")
                    .setTitleTextColor(R.color.text_black)
                    .setContentText("是否取消收藏？")
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

        private void requesteditNote(int id, String content) {
            HttpHeaders headers = new HttpHeaders();
            headers.put("Authorization", "Bearer " + SpUtils.getString(context, "token", ""));
            HttpParams params = new HttpParams();
            params.put("id", id + "");
            params.put("content", content);
            OkGo.<BaseBean>post(MyContants.editNote)
                    .tag(this)
                    .headers(headers)
                    .params(params)
                    .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                        @Override
                        public void onSuccess(Response<BaseBean> response) {
                            int code = response.code();
                            if (code >= 200 && code <= 204) {
                                TUtils.showShort(context, response.body().getMessage());
                            } else {
                                TUtils.showShort(context, response.body().getMessage());
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
            EditText item_count;
            LinearLayout study_liner;

            MyViewholder(View rootView) {
                super(rootView);
                item_title = (TextView) rootView.findViewById(R.id.item_title);
                item_delete = (TextView) rootView.findViewById(R.id.item_delete);
                item_compile = (TextView) rootView.findViewById(R.id.item_compile);
                item_count = (EditText) rootView.findViewById(R.id.item_count);
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

}
