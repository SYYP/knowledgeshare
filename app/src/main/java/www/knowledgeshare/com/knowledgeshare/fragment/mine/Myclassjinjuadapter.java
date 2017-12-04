package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Myclassjinjuadapter extends RecyclerView.Adapter<Myclassjinjuadapter.Myviewholder> {
    private Context context;
    private List<String> list = new ArrayList<>();

    public Myclassjinjuadapter(Context context) {
        this.context = context;
        data();
    }

    private void data() {
         list.add("2017-11-30 星期一");
        list.add("2017-11-25 星期三");
        for (int i = 0; i <5 ; i++) {
            list.add("2017-11-22 星期四");
        }
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jinju, parent, false);
        Myviewholder myviewholder = new Myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, int position) {
      holder.jin_date.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public View rootView;
        public View line;
        public TextView jin_date;
        public RelativeLayout relative;
        public TextView study_count;
        public LinearLayout study_liner;
        public Myviewholder(View rootView) {
            super(rootView);
            this.line = (View) rootView.findViewById(R.id.line);
            this.jin_date = (TextView) rootView.findViewById(R.id.jin_date);
            this.relative = (RelativeLayout) rootView.findViewById(R.id.relative);
            this.study_count = (TextView) rootView.findViewById(R.id.study_count);
            this.study_liner = (LinearLayout) rootView.findViewById(R.id.study_liner);
        }
    }

}