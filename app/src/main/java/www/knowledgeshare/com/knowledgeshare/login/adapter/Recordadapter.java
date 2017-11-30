package www.knowledgeshare.com.knowledgeshare.login.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.login.bean.StudyRecordbean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Recordadapter extends RecyclerView.Adapter<Recordadapter.Myadapter> {

    private Context context;
    private List<StudyRecordbean> list;

    public Recordadapter(Context context, List<StudyRecordbean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Myadapter onCreateViewHolder(ViewGroup parent, int viewType) {

        Myadapter myadapter = new Myadapter(LayoutInflater.from(context).inflate(R.layout.item_record, parent, false));
        return myadapter;
    }

    @Override
    public void onBindViewHolder(Myadapter holder, final int position) {

           //添加数据
         if(list.get(position).getTime()==null||list.get(position).getTime().equals("")){
            holder.record_time.setVisibility(View.GONE);

         }
        else {
             holder.record_time.setText(list.get(position).getTime());
         }

        holder.item_title.setText(list.get(position).getTitle());
        /*
           设置监听
         */
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  list.remove(position);
                notifyDataSetChanged();
            }
        });
        /*
          编辑
         */
        holder.item_compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myadapter extends RecyclerView.ViewHolder {
        public TextView record_time;
        public TextView item_title;
        public EditText item_count;
        public LinearLayout study_liner;
        public TextView item_compile;
        public ImageView item_imgcompile;
        public TextView item_delete;
        public ImageView item_img_delete;
        public Myadapter(View rootView) {
            super(rootView);
            record_time = (TextView) rootView.findViewById(R.id.record_time);
            this.item_title = (TextView) rootView.findViewById(R.id.item_title);
            this.item_count = (EditText) rootView.findViewById(R.id.item_count);
            this.study_liner = (LinearLayout) rootView.findViewById(R.id.study_liner);
            this.item_compile = (TextView) rootView.findViewById(R.id.item_compile);
            this.item_imgcompile = (ImageView) rootView.findViewById(R.id.item_imgcompile);
            this.item_delete = (TextView) rootView.findViewById(R.id.item_delete);
            this.item_img_delete = (ImageView) rootView.findViewById(R.id.item_img_delete);
        }
    }
}
