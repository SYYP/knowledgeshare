package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Mymessageadapter extends RecyclerView.Adapter<Mymessageadapter.Myviewholder> {
    private Context context;
    private List<Messagebean> list = new ArrayList<>();
    private Messagebean msssagebean;

    public Mymessageadapter(Context context) {
        this.context = context;
        data();

    }

    /*
       添加数据
     */
    private void data() {
        for (int i = 0; i < 6; i++) {
            msssagebean = new Messagebean();
            msssagebean.setCount("系统消息： 恭喜行走的肉夹馍获得金牌用户勋章，再接再厉成为砖石用户");
            msssagebean.setTime("2017-10-10  10:10");
            list.add(msssagebean);

        }


    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        Myviewholder myviewholder = new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_my_message, parent, false));

        return myviewholder;
    }

    @Override
    public void onBindViewHolder(final Myviewholder holder, final int position) {
         //绑定数据
         holder.message_count.setText(list.get(position).getCount());
        holder.message_date.setText(list.get(position).getTime());
        holder.msg_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                holder.msg_check.setChecked(list.get(position).isaBoolean());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public CheckBox msg_check;
        public TextView message_date;
        public TextView message_count;
        public Myviewholder(View rootView) {
            super(rootView);
            this.msg_check = (CheckBox) rootView.findViewById(R.id.msg_check);
            this.message_date = (TextView) rootView.findViewById(R.id.message_date);
            this.message_count = (TextView) rootView.findViewById(R.id.message_count);

        }
    }

}
