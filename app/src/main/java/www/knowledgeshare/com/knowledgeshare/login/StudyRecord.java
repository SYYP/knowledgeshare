package www.knowledgeshare.com.knowledgeshare.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.DemoBean;
import www.knowledgeshare.com.knowledgeshare.login.adapter.Recordadapter;
import www.knowledgeshare.com.knowledgeshare.login.bean.StudyRecordbean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class StudyRecord extends BaseActivity {

    private ImageView iv_back;
    private RecyclerView study_record;
    List <StudyRecordbean> list = new ArrayList<>();
    private List<DemoBean> listDate;
    List<DemoBean> listAll = new ArrayList<DemoBean>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
        data();
    }

    private void data() {
        requestNotes();

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


    }

    private void requestNotes() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("after","");

        OkGo.<String>get(MyContants.notes)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Logger.e(body);
                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            JSONObject data = jsonObject.getJSONObject("data");

                            Iterator keys = data.keys();
                            while (keys.hasNext()){
                                String key = String.valueOf(keys.next());
                                Logger.e(key);
                                JSONArray timeData = data.getJSONArray(key);
                                Logger.e(timeData.toString());
                                listDate = JSON.parseArray(timeData.toString(),DemoBean.class);
                                listAll.addAll(listDate);
                            }
                            study_record.setLayoutManager(new LinearLayoutManager(StudyRecord.this));
                            Recordadapter recordadapter=new Recordadapter(StudyRecord.this,listAll);
                            study_record.setAdapter(recordadapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

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
