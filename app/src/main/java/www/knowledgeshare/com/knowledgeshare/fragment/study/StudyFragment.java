package www.knowledgeshare.com.knowledgeshare.fragment.study;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.CollectActivity;
import www.knowledgeshare.com.knowledgeshare.login.MessageActivity;
import www.knowledgeshare.com.knowledgeshare.login.adapter.Studyadapter;
/**
 * Created by Administrator on 2017/11/17.
 */
public class StudyFragment extends BaseFragment {
    public TextView tv_search;
    public ImageView iv_message;
    public LinearLayout ll_download;
    public TextView study_weather;
    public TextView study_wendu;
    public LinearLayout liner;
    public TextView study_city;
    public View line;
    public TextView study_title;
    public TextView study_day;
    public TextView study_date;
    public RelativeLayout relative;
    public TextView study_count;
    public LinearLayout study_liner;
    public View lines;
    public TextView study_titles;
    public TextView study_days;
    public TextView study_dates;
    private RecyclerView study_recycler;
    private ImageView study_xinxin;
     boolean bool;
    private TextView study_collect;

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected View initView() {
        View rootView= View.inflate(mContext, R.layout.fragment_study, null);
        this.tv_search = (TextView) rootView.findViewById(R.id.tv_search);
        this.iv_message = (ImageView) rootView.findViewById(R.id.iv_message);
        this.ll_download = (LinearLayout) rootView.findViewById(R.id.ll_download);
        this.study_weather = (TextView) rootView.findViewById(R.id.study_weather);
        this.study_wendu = (TextView) rootView.findViewById(R.id.study_wendu);
        this.liner = (LinearLayout) rootView.findViewById(R.id.liner);
        this.study_city = (TextView) rootView.findViewById(R.id.study_city);
        this.line = (View) rootView.findViewById(R.id.line);
        this.study_title = (TextView) rootView.findViewById(R.id.study_title);
        this.study_day = (TextView) rootView.findViewById(R.id.study_day);
        this.study_date = (TextView) rootView.findViewById(R.id.study_date);
        this.relative = (RelativeLayout) rootView.findViewById(R.id.relative);
        this.study_count = (TextView) rootView.findViewById(R.id.study_count);
        this.study_liner = (LinearLayout) rootView.findViewById(R.id.study_liner);
        this.lines = (View) rootView.findViewById(R.id.lines);
        this.study_titles = (TextView) rootView.findViewById(R.id.study_titles);
        this.study_dates = (TextView) rootView.findViewById(R.id.study_dates);
        study_recycler = rootView.findViewById(R.id.study_recycle);
        study_collect = rootView.findViewById(R.id.study_collect);
        study_xinxin = rootView.findViewById(R.id.study_xinxin);
        study_recycler.setNestedScrollingEnabled(false);
        /*
           创建适配器
         */
        Studyadapter studyadapter=new Studyadapter(getActivity());
        study_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        study_recycler.setAdapter(studyadapter);

          study_xinxin.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if(bool){
                       study_xinxin.setImageResource(R.drawable.xinxin);

                  }
                  else {
                      study_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                  }
                   bool=!bool;
              }
          });
        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
        //收藏
        study_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    protected void initData() {

    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        MyApplication.stopClearClip();
//    }
}
