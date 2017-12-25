package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.activity.SettingActivity;
import www.knowledgeshare.com.knowledgeshare.bean.FavoriteBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.WenGaoActivity;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

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

    public Myclassadapter(Context context, List<FavoriteBean.DataBean> list) {
        this.context = context;
        this.list = list;
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
                        switch (flag){
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final int itemViewType = getItemViewType(position);
        int type = list.get(position).getType();
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int layoutPosition = holder.getLayoutPosition();
                    Log.d("tag", layoutPosition + "");
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition, itemViewType);
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
                        context.startActivity(intent);
                    }
                });
                myviewhiodler1.class_xinxin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bool) {
                            showTips(ONE,"提示","是否取消收藏？");
                        } else {
                            myviewhiodler1.class_xinxin.setImageResource(R.drawable.xinxin);
                        }
                        bool = !bool;
                    }

                });
                break;
            case 2:
                myviewhiodler2 = (Myviewholder2) holder;
                myviewhiodler2.class_titles.setText(list.get(position).getVideo_name());
                myviewhiodler2.class_count.setText(list.get(position).getContent());
                Glide.with(context).load(list.get(position).getT_header()).into(myviewhiodler2.class_pho);
                myviewhiodler2.class_name.setText(list.get(position).getT_name());
                myviewhiodler2.class_date.setText(list.get(position).getCreate_at()+" "+list.get(position).getDay_week());

                myviewhiodler2.class_xinxin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bool) {
                        showTips(TWO,"提示","是否取消收藏？");
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
                            showTips(THREE,"提示","是否取消收藏？");
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

        public Myviewholder1(View rootView) {
            super(rootView);
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
        void onItemClick(View view, int position, int type);
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
