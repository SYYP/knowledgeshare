package www.knowledgeshare.com.knowledgeshare.login.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.bean.NoticeBean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Messageadapter extends RecyclerView.Adapter<Messageadapter.Myviewholder> {
    private Context context;
    private View line;
    private OnItemClickListener mOnItemClickListener = null;
    private List<NoticeBean.DataBean> list = new ArrayList<>();

    public Messageadapter(Context context,List<NoticeBean.DataBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        Myviewholder myviewholder = new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false));
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(final Myviewholder holder, int position) {
                  if(mOnItemClickListener!=null){
                      holder.itemView.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              int layoutPosition = holder.getLayoutPosition();
                              mOnItemClickListener.onItemClick(holder.itemView,layoutPosition);
                          }
                      });
                  }
         holder.createTv.setText(list.get(position).getCreated_at());
        holder.mesg_title.setText(list.get(position).getName());
        holder.mesg_text.setText(list.get(position).getDescription());
        String imgurl = list.get(position).getImgurl();
        Glide.with(context).load(imgurl).into(holder.mesg_img);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public View line;
        public TextView mesg_title;
        public RelativeLayout relative;
        public ImageView mesg_img;
        public TextView mesg_text;
        public LinearLayout mesg_recl;
        public LinearLayout liner_message;
        TextView createTv;
        public Myviewholder(View rootView) {
            super(rootView);
            line = (View) rootView.findViewById(R.id.line);
            mesg_title = (TextView) rootView.findViewById(R.id.mesg_title);
            relative = (RelativeLayout) rootView.findViewById(R.id.relative);
            mesg_img = (ImageView) rootView.findViewById(R.id.mesg_img);
            mesg_text = (TextView) rootView.findViewById(R.id.mesg_text);
            mesg_recl = (LinearLayout) rootView.findViewById(R.id.mesg_recl);
            liner_message = (LinearLayout) rootView.findViewById(R.id.liner_message);
            createTv = rootView.findViewById(R.id.create_at_tv);
        }
    }

/*
  创建接口用来回调
 */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
