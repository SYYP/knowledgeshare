package www.knowledgeshare.com.knowledgeshare.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.login.adapter.Recordadapter;
import www.knowledgeshare.com.knowledgeshare.login.bean.StudyRecordbean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class StudyRecord extends BaseActivity {

    private ImageView iv_back;
    private RecyclerView study_record;
      List <StudyRecordbean> list=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
         data();
    }

    private void data() {

        StudyRecordbean stubean=new StudyRecordbean();
        stubean.setTime("2017-11-28");
        stubean.setTitle("10:00    其实我喜欢你很久了");
        list.add(stubean);
        StudyRecordbean stubean1=new StudyRecordbean();
        stubean1.setTitle("09:00   你不知道我到底在哪里");
        list.add(stubean1);
        StudyRecordbean stubean2=new StudyRecordbean();
        stubean2.setTime("2017-11-29");
        stubean2.setTitle("09:00   微风轻轻起，我...");
        list.add(stubean2);
        //设置适配器模式
        study_record.setLayoutManager(new LinearLayoutManager(this));
        Recordadapter recordadapter=new Recordadapter(StudyRecord.this,list);
        study_record.setAdapter(recordadapter);

    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        study_record = (RecyclerView) findViewById(R.id.study_record);



          iv_back.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  finish();
              }
          });
    }
}
