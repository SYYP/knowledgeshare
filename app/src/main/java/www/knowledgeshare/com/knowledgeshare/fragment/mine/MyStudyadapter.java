package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.login.bean.StudyRecordbean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class MyStudyadapter extends RecyclerView.Adapter<MyStudyadapter.MyViewHolder> {
    private Context context;
    private List<StudyRecordbean> list = new ArrayList();

    public MyStudyadapter(Context context) {
        this.context = context;
        indata();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_record, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
          /*
            设置数据
           */
          holder.item_title.setText(list.get(position).getTitle());
        holder.item_count.setText(list.get(position).getCount());
         if(list.get(position).getTime()!=null){
             holder.record_time.setText(list.get(position).getTime());
         }
         else {
             holder.record_time.setVisibility(View.GONE);
         }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView record_time;
        public TextView item_title;
        public EditText item_count;
        public LinearLayout study_liner;
        public TextView item_compile;
        public ImageView item_imgcompile;
        public TextView item_delete;
        public ImageView item_img_delete;
        public MyViewHolder(View rootView) {
            super(rootView);
            this.record_time = (TextView) rootView.findViewById(R.id.record_time);
            this.item_title = (TextView) rootView.findViewById(R.id.item_title);
            this.item_count = (EditText) rootView.findViewById(R.id.item_count);
            this.study_liner = (LinearLayout) rootView.findViewById(R.id.study_liner);
            this.item_compile = (TextView) rootView.findViewById(R.id.item_compile);
            this.item_imgcompile = (ImageView) rootView.findViewById(R.id.item_imgcompile);
            this.item_delete = (TextView) rootView.findViewById(R.id.item_delete);
            this.item_img_delete = (ImageView) rootView.findViewById(R.id.item_img_delete);
        }
    }

    public void indata() {
        StudyRecordbean studyRecordbean = new StudyRecordbean();
        studyRecordbean.setTitle("10:00 凌晨四点的背景");
        studyRecordbean.setTime("2017-11-24");
        studyRecordbean.setCount("我喜欢你只是不能和你在一起，对不起，原谅我还爱着你，我累了，你自由了");
        list.add(studyRecordbean);
        StudyRecordbean studyRecordbean1 = new StudyRecordbean();
        studyRecordbean1.setTitle("10:00 凌晨四点的北京");
        studyRecordbean1.setCount("我喜欢你只是不能和你在一起，对不起，原谅我还爱着你，我累了，你自由了");
        list.add(studyRecordbean1);
        StudyRecordbean studyRecordbean2 = new StudyRecordbean();
        studyRecordbean2.setTitle("10:00 凌晨四点的背景");
        studyRecordbean2.setTime("2017-11-24");
        studyRecordbean2.setCount("我喜欢你只是不能和你在一起，对不起，原谅我还爱着你，我累了，你自由了");
        list.add(studyRecordbean2);
        StudyRecordbean studyRecordbean3 = new StudyRecordbean();
        studyRecordbean3.setTitle("10:00 凌晨四点的北京");
        studyRecordbean3.setCount("我喜欢你只是不能和你在一起，对不起，原谅我还爱着你，我累了，你自由了");
        list.add(studyRecordbean1);
    }


}
