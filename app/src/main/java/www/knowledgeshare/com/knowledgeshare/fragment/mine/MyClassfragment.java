package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.home.SoftMusicDetailActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.WenGaoActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.home.ZhuanLanActivity;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MyClassfragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<Collectbean> list = new ArrayList<>();
    private static Myclassadapter myclassadapter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_myclass, null);
        recyclerView = inflate.findViewById(R.id.class_recycler);
        data();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myclassadapter = new Myclassadapter(getActivity(),list);
       myclassadapter.setOnItemClickListener(new Myclassadapter.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position, int type) {
               Log.d("ccc",type+"");
                   if(type==1){
                       Intent intent=new Intent(getActivity(), SoftMusicDetailActivity.class);
                       startActivity(intent);
                   }
                 else if(type==2){
                       Intent intent=new Intent(getActivity(), ZhuanLanActivity.class);
                       startActivity(intent);
               }
           }
       });
        recyclerView.setAdapter(myclassadapter);
        return inflate;
    }

    private void data() {

        Collectbean coll = new Collectbean();
        coll.setTitle("知道我在等你吗");
        list.add(coll);
        Collectbean coll1 = new Collectbean();
        coll1.setTitle("其实我很喜欢");
        list.add(coll1);
        Collectbean coll2 = new Collectbean();
        coll2.setTitle("好可惜真的不能在一起");
        list.add(coll2);
    }


    @Override
    protected void initData() {

    }

    static class Myclassadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context context;
        private List<Collectbean> list = new ArrayList<>();
        final static int ONE = 0, TWO = 1, THREE = 2;
        private Myclassadapter.OnItemClickListener mOnItemClickListener = null;
        //    private OnClickListener mOnClickListener = null;
        boolean bool;
        private Myclassadapter.Myviewholder1 myviewhiodler1;
        private Myclassadapter.Myviewholder2 myviewhiodler2;
        private Myclassadapter.Myviewholder3 myviewhiodler3;

        public Myclassadapter(Context context, List<Collectbean> list) {
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
                    viewHolder = new Myclassadapter.Myviewholder1(view);
                    break;
                case TWO:
                    view = LayoutInflater.from(context).inflate(R.layout.fragment_class_two, parent, false);
                    viewHolder = new Myclassadapter.Myviewholder2(view);
                    break;
                case THREE:
                    view = LayoutInflater.from(context).inflate(R.layout.fragment_class_three, parent, false);
                    viewHolder = new Myclassadapter.Myviewholder3(view);
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
//                                myviewhiodler1.class_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                                    list.remove(myviewhiodler1.getAdapterPosition());
                                    Logger.e(myviewhiodler1.getAdapterPosition()+"");
                                    myclassadapter.notifyDataSetChanged();
                                    break;
                                case TWO:
//                                myviewhiodler2.class_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                                    list.remove(myviewhiodler2.getAdapterPosition());
                                    Logger.e(myviewhiodler2.getAdapterPosition()+"");
                                    myclassadapter.notifyDataSetChanged();
                                    break;
                                case THREE:
//                                myviewhiodler3.class_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                                    list.remove(myviewhiodler3.getAdapterPosition());
                                    Logger.e(myviewhiodler3.getAdapterPosition()+"");
                                    myclassadapter.notifyDataSetChanged();
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
            switch (itemViewType) {

                case ONE:
                    myviewhiodler1 = (Myclassadapter.Myviewholder1) holder;
                    myviewhiodler1.class_title.setText(list.get(position).getTitle());
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
//                        if (bool) {
                            showTips(ONE,"提示","是否取消收藏？");
//                        }
                        /*else {
                            myviewhiodler1.class_xinxin.setImageResource(R.drawable.xinxin);
                        }
                        bool = !bool;*/
                        }

                    });
                    break;
                case TWO:
                    myviewhiodler2 = (Myclassadapter.Myviewholder2) holder;

                    myviewhiodler2.class_titles.setText(list.get(position).getTitle());
                    myviewhiodler2.class_xinxin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                    if (bool) {
                            showTips(TWO,"提示","是否取消收藏？");
//                    }
                    /*else {
                        myviewhiodler2.class_xinxin.setImageResource(R.drawable.xinxin);
                    }
                    bool = !bool;*/
                        }

                    });
                    break;
                case THREE:
                    myviewhiodler3 = (Myclassadapter.Myviewholder3) holder;
                    myviewhiodler3.class_xinxin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                        if (bool) {
                            showTips(THREE,"提示","是否取消收藏？");
//                        }
                       /* else {
                            myviewhiodler3.class_xinxin.setImageResource(R.drawable.xinxin);
                        }
                        bool = !bool;*/
                        }

                    });
                    myviewhiodler3.class_titles.setText(list.get(position).getTitle());
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (list.get(position).getTitle().equals("知道我在等你吗")) {
                return ONE;
            } else if (position == 1 || position == 4 || position == 5) {
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
            private final ImageView imageView;

            public Myviewholder1(View rootView) {
                super(rootView);
                this.class_bo = (ImageView) rootView.findViewById(R.id.class_bo);
                this.class_title = (TextView) rootView.findViewById(R.id.class_title);
                this.class_time = (TextView) rootView.findViewById(R.id.class_time);
                this.class_xinxin = (ImageView) rootView.findViewById(R.id.class_xinxin);
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

        public void setOnItemClickListener(Myclassadapter.OnItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }
    }
}
