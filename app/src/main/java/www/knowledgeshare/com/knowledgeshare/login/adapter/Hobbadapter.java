package www.knowledgeshare.com.knowledgeshare.login.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.knowledgeshare.com.knowledgeshare.R;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Hobbadapter extends RecyclerView.Adapter<Hobbadapter.Myadapter> {
    private Context context;
    private String[] mVals = new String[]
            {"美声", "爵士", "摇滚 ", "音乐剧", "交响乐", "R&B",
                    "乡村风格", "轻音乐", "民谣", "蓝调", "hiphop",
                    "乐器", "艺术管理", "编曲", "中西方音乐史","其他"};
    public Hobbadapter(Context context) {
        this.context = context;

    }

    @Override
    public Myadapter onCreateViewHolder(ViewGroup parent, int viewType) {


        Myadapter myadapter=new Myadapter(LayoutInflater.from(context).inflate(R.layout.tv,parent,false));
        return myadapter;
    }

    @Override
    public void onBindViewHolder(Myadapter holder, int position) {
          holder.iv_text.setText(mVals[position]);

    }

    @Override
    public int getItemCount() {
        return mVals.length;
    }

    public class Myadapter extends RecyclerView.ViewHolder {
        private TextView iv_text;
        public Myadapter(View itemView) {
            super(itemView);
              iv_text= itemView.findViewById(R.id.iv_text);
        }
    }
}
