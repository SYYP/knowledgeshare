package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.bean.FavoriteBean;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.WenGaoActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Myclassadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    //    private List<Collectbean> list = new ArrayList<>();
    final static int ONE = 0, TWO = 1, THREE = 2;
    private OnItemClickListener mOnItemClickListener = null;
    //    private OnClickListener mOnClickListener = null;
    boolean bool;
    private Myviewholder1 myviewhiodler1;
    private Myviewholder2 myviewhiodler2;
    private Myviewholder3 myviewhiodler3;
    private List<FavoriteBean.DataBean> list = new ArrayList<>();
    private BaseDialog mNetDialog;

    public Myclassadapter(Context context, List<FavoriteBean.DataBean> list) {
        this.context = context;
        this.list = list;
        initNETDialog();
        initMusic();
    }

    private void initNETDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(context);
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
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        /*
           多条目类型
         */
        switch (viewType) {
            case ONE:
                view = LayoutInflater.from(context).inflate(R.layout.fragment_class_one, parent, false);
                viewHolder = new Myviewholder1(view);
                break;
            case TWO:
                view = LayoutInflater.from(context).inflate(R.layout.fragment_class_two, parent, false);
                viewHolder = new Myviewholder2(view);
                break;
            case THREE:
                view = LayoutInflater.from(context).inflate(R.layout.fragment_class_three, parent, false);
                viewHolder = new Myviewholder3(view);
                break;
        }
        return viewHolder;
    }

    private void showTips(final int flag, String title, String content) {
        new NormalAlertDialog.Builder(context)
                .setTitleVisible(true).setTitleText(title)
                .setTitleTextColor(R.color.text_black)
                .setContentText(content)
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
                        switch (flag) {
                            case ONE:
                                myviewhiodler1.class_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                                break;
                            case TWO:
                                myviewhiodler2.class_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                                break;
                            case THREE:
                                myviewhiodler3.class_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                                break;
                        }

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

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final int itemViewType = getItemViewType(position);
        int type = list.get(position).getType();
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int layoutPosition = holder.getLayoutPosition();
                    Log.d("tag", layoutPosition + "");
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition, itemViewType, list.get(position).getId());
                }
            });
        }
        switch (type) {

            case 1:
                myviewhiodler1 = (Myviewholder1) holder;
                myviewhiodler1.class_title.setText(list.get(position).getVideo_name());
                myviewhiodler1.class_time.setText(list.get(position).getVideo_time());
                myviewhiodler1.class_content.setText(list.get(position).getName());
                myviewhiodler1.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, WenGaoActivity.class);
                        String type1 = list.get(position).getVideo_type();
                        if (type1.equals("daliy")) {
                            intent.putExtra("type", "everydaycomment");


                        } else if (type1.equals("free")) {
                            intent.putExtra("type", "free");
                        } else if (type1.equals("xk")) {
                            intent.putExtra("type", "softmusicdetail");
                        } else if (type1.equals("zl")) {
                            intent.putExtra("type", "zhuanlandetail");
                        }
                        intent.putExtra("id", list.get(position).getId() + "");
                        context.startActivity(intent);
                    }
                });
                myviewhiodler1.class_xinxin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bool) {
                            showTips(ONE, "提示", "是否取消收藏？");
                        } else {
                            myviewhiodler1.class_xinxin.setImageResource(R.drawable.xinxin);
                        }
                        bool = !bool;
                    }

                });
                myviewhiodler1.ll_root_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().postSticky(new EventBean("collect_show"));
                        FavoriteBean.DataBean dataBean = list.get(position);
                        //刷新小型播放器
                        FavoriteBean.DataBean.Child item = dataBean.getChildBean();
                        PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getParent_name(), item.getT_tag(),
                                item.getVideo_url(), 0);
                        gobofang(playerBean);
                        //设置进入播放主界面的数据
                        List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                        String type1 = list.get(position).getVideo_type();
                        if (type1.equals("daliy")) {
                            type1 = "everydaycomment";
                        } else if (type1.equals("free")) {
                            type1 = "free";
                        } else if (type1.equals("xk")) {
                            type1 = "softmusicdetail";
                        } else if (type1.equals("zl")) {
                            type1 = "zhuanlandetail";
                        }
                        MusicTypeBean musicTypeBean = new MusicTypeBean(type1,
                                item.getT_header(), item.getParent_name(), item.getId() + "",
                                item.isIsfav());
                        musicTypeBean.setMsg("musicplayertype");
                        musicTypeBeanList.add(musicTypeBean);
                        MediaService.insertMusicTypeList(musicTypeBeanList);
                        //加入默认的播放列表
                        List<PlayerBean> mylist = new ArrayList<PlayerBean>();
                        PlayerBean playerBean1 = new PlayerBean(item.getT_header(),
                                item.getVideo_name(), item.getT_tag(), item.getVideo_url());
                        mylist.add(playerBean1);
                        MediaService.insertMusicList(mylist);
                        //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                        List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                        BofangHistroyBean bofangHistroyBean = new BofangHistroyBean(type1, item.getId(), item.getParent_name(),
                                item.getCreated_at(), item.getVideo_url(), item.getGood_count(),
                                item.getCollect_count(), item.getView_count(), item.isIslive(), item.isIsfav(),
                                item.getT_header(), item.getT_tag(),
                                item.getShare_h5_url(), SystemClock.currentThreadTimeMillis());
                        histroyBeanList.add(bofangHistroyBean);
                        MediaService.insertBoFangHistroyList(histroyBeanList);
                    }
                });
                break;
            case 2:
                myviewhiodler2 = (Myviewholder2) holder;
                myviewhiodler2.class_titles.setText(list.get(position).getVideo_name());
                myviewhiodler2.class_count.setText(list.get(position).getContent());
                Glide.with(context).load(list.get(position).getT_header()).into(myviewhiodler2.class_pho);
                myviewhiodler2.class_name.setText(list.get(position).getT_name());
                myviewhiodler2.class_date.setText(list.get(position).getCreate_at() + " " + list.get(position).getDay_week());

                myviewhiodler2.class_xinxin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bool) {
                            showTips(TWO, "提示", "是否取消收藏？");
                        } else {
                            myviewhiodler2.class_xinxin.setImageResource(R.drawable.xinxin);
                        }
                        bool = !bool;
                    }

                });
                break;
            case 0:
                myviewhiodler3 = (Myviewholder3) holder;
                myviewhiodler3.class_xinxin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bool) {
                            showTips(THREE, "提示", "是否取消收藏？");
                        } else {
                            myviewhiodler3.class_xinxin.setImageResource(R.drawable.xinxin);
                        }
                        bool = !bool;
                    }

                });
                myviewhiodler3.class_titles.setText(list.get(position).getVideo_name());
                Glide.with(context).load(list.get(position).getImgurl()).into(myviewhiodler3.class_img);
                break;
        }
    }

    private MediaService.MyBinder mMyBinder;
    //“绑定”服务的intent
    private Intent MediaServiceIntent;

    private void initMusic() {
        MediaServiceIntent = new Intent(context, MediaService.class);
        context.bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
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

    private void gobofang(final PlayerBean playerBean) {
        int apnType = NetWorkUtils.getAPNType(context);
        if (apnType == 0) {
            Toast.makeText(context, "没有网络呢~", Toast.LENGTH_SHORT).show();
        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
            if (SpUtils.getBoolean(context, "nowifiallowlisten", false)) {//记住用户允许流量播放
                playerBean.setMsg("refreshplayer");
                EventBus.getDefault().postSticky(playerBean);
                mMyBinder.setMusicUrl(playerBean.getVideo_url());
                mMyBinder.playMusic(playerBean);
                mNetDialog.dismiss();
                EventBus.getDefault().postSticky(new EventBean("collect_show2"));
                SpUtils.putBoolean(context, "nowifiallowlisten", true);
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
                        EventBus.getDefault().postSticky(new EventBean("collect_show2"));
                        SpUtils.putBoolean(context, "nowifiallowlisten", true);
                    }
                });
                mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNetDialog.dismiss();
                    }
                });
            }
        } else if (NetWorkUtils.isMobileConnected(context)) {
            Toast.makeText(context, "wifi不可用呢~", Toast.LENGTH_SHORT).show();
        } else {
            playerBean.setMsg("refreshplayer");
            EventBus.getDefault().postSticky(playerBean);
            mMyBinder.setMusicUrl(playerBean.getVideo_url());
            mMyBinder.playMusic(playerBean);
            EventBus.getDefault().postSticky(new EventBean("collect_show2"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 1) {
            return ONE;
        } else if (list.get(position).getType() == 2) {
            return TWO;
        } else {
            return THREE;
        }
    }

      /*
        多条目添加布局
       */

    public class Myviewholder1 extends RecyclerView.ViewHolder {
        public ImageView class_bo;
        public TextView class_title;
        public TextView class_time;
        public ImageView class_xinxin;
        public TextView class_content;
        private final ImageView imageView;
        private LinearLayout ll_root_view;

        public Myviewholder1(View rootView) {
            super(rootView);
            this.ll_root_view = (LinearLayout) rootView.findViewById(R.id.ll_root_view);
            this.class_bo = (ImageView) rootView.findViewById(R.id.class_bo);
            this.class_title = (TextView) rootView.findViewById(R.id.class_title);
            this.class_time = (TextView) rootView.findViewById(R.id.class_time);
            this.class_xinxin = (ImageView) rootView.findViewById(R.id.class_xinxin);
            this.class_content = rootView.findViewById(R.id.class_content);
            imageView = rootView.findViewById(R.id.collect_wengao);
        }
    }

    public class Myviewholder2 extends RecyclerView.ViewHolder {
        public View rootView;
        public View lines;
        public TextView class_titles;
        public TextView class_count;
        public CircleImageView class_pho;
        public TextView class_name;
        public TextView class_date;
        public ImageView class_xinxin;

        public Myviewholder2(View rootView) {
            super(rootView);
            this.lines = (View) rootView.findViewById(R.id.lines);
            this.class_titles = (TextView) rootView.findViewById(R.id.class_titles);
            this.class_count = (TextView) rootView.findViewById(R.id.class_count);
            this.class_pho = (CircleImageView) rootView.findViewById(R.id.class_pho);
            this.class_name = (TextView) rootView.findViewById(R.id.class_name);
            this.class_date = (TextView) rootView.findViewById(R.id.class_date);
            this.class_xinxin = (ImageView) rootView.findViewById(R.id.class_xinxin);
        }
    }

    public class Myviewholder3 extends RecyclerView.ViewHolder {
        public View lines;
        public TextView class_titles;
        public ImageView class_img;
        public TextView class_date;
        public TextView class_dianxin;
        public TextView class_read;
        public ImageView class_xinxin;

        public Myviewholder3(View rootView) {
            super(rootView);
            this.lines = (View) rootView.findViewById(R.id.lines);
            this.class_titles = (TextView) rootView.findViewById(R.id.class_titles);
            this.class_img = (ImageView) rootView.findViewById(R.id.class_img);
            this.class_date = (TextView) rootView.findViewById(R.id.class_date);
            this.class_dianxin = (TextView) rootView.findViewById(R.id.class_dianxin);
            this.class_read = (TextView) rootView.findViewById(R.id.class_read);
            this.class_xinxin = (ImageView) rootView.findViewById(R.id.class_xinxin);
        }
    }

    /*
       接口回调用来item点击
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position, int type, int id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
//    public interface OnClickListener {
//        void onItemClick(View view, int position);
//    }
//
//    public void setOnClickListener(OnClickListener listener) {
//        this.mOnClickListener = listener;
//    }
//}
