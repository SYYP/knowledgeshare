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

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Messageadapter extends RecyclerView.Adapter<Messageadapter.Myviewholder> {
    private Context context;
    private List<String> list = new ArrayList<>();
    private View line;


    public Messageadapter(Context context) {
        this.context = context;
        initdata();
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        Myviewholder myviewholder = new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false));
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, int position) {

         holder.mesg_text.setText(list.get(position).toString());
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
        public Myviewholder(View rootView) {
            super(rootView);
            line = (View) rootView.findViewById(R.id.line);
            mesg_title = (TextView) rootView.findViewById(R.id.mesg_title);
            relative = (RelativeLayout) rootView.findViewById(R.id.relative);
            mesg_img = (ImageView) rootView.findViewById(R.id.mesg_img);
            mesg_text = (TextView) rootView.findViewById(R.id.mesg_text);
            mesg_recl = (LinearLayout) rootView.findViewById(R.id.mesg_recl);
            liner_message = (LinearLayout) rootView.findViewById(R.id.liner_message);
        }
    }

    public void initdata() {
        list.add("我大概是没救了，但我不需要解药。我那些没由来的情绪低落、闷闷不乐、沉默，我也不知道它从哪里来，什么时候走。");
        list.add("听到你的名字会突然变得沉默，独自一人在夜里时会想你想到失眠。");
        list.add("我总在问自己为什么还坚持，可能没有答案，但我只知道，放下你、我做不到。");
    }

}
