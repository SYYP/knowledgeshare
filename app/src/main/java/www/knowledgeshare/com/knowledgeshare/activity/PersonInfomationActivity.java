package www.knowledgeshare.com.knowledgeshare.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

public class PersonInfomationActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_back_iv) ImageView titleBackIv;
    @BindView(R.id.title_content_tv) TextView titleContentTv;
    @BindView(R.id.face_iv) CircleImageView faceIv;
    @BindView(R.id.person_face_rl) RelativeLayout personFaceRl;
    @BindView(R.id.zhye_tv) TextView nameTv;
    @BindView(R.id.person_name_rl) RelativeLayout personNameRl;
    @BindView(R.id.sex_tv) TextView sexTv;
    @BindView(R.id.person_sex_rl) RelativeLayout personSexRl;
    @BindView(R.id.date_tv) TextView dateTv;
    @BindView(R.id.person_date_rl) RelativeLayout personDateRl;
    @BindView(R.id.xueli_tv) TextView xueliTv;
    @BindView(R.id.person_xueli_rl) RelativeLayout personXueliRl;
    @BindView(R.id.hangye_tv) TextView hangyeTv;
    @BindView(R.id.person_hangye_rl) RelativeLayout personHangyeRl;
    @BindView(R.id.zhiye_tv) TextView zhiyeTv;
    @BindView(R.id.person_zhiye_rl) RelativeLayout personZhiyeRl;
    private List<String> cameraList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_infomation);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleBackIv.setVisibility(View.VISIBLE);
        titleBackIv.setOnClickListener(this);
        titleContentTv.setText("个人信息");
        personFaceRl.setOnClickListener(this);
        personNameRl.setOnClickListener(this);
        personSexRl.setOnClickListener(this);
        personDateRl.setOnClickListener(this);
        personXueliRl.setOnClickListener(this);
        personHangyeRl.setOnClickListener(this);
        personZhiyeRl.setOnClickListener(this);
        cameraList = new ArrayList<>();
        cameraList.add("从相册中选择");
        cameraList.add("拍照");
        cameraList.add("取消");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.person_face_rl://头像
                showCamera();
                break;
            case R.id.person_name_rl://昵称
                break;
            case R.id.person_sex_rl://性别
                break;
            case R.id.person_date_rl://出生年月
                break;
            case R.id.person_xueli_rl://学历
                break;
            case R.id.person_hangye_rl://行业
                break;
            case R.id.person_zhiye_rl://职业
                break;
        }
    }

    private void showCamera() {
        new NormalSelectionDialog.Builder(this).setlTitleVisible(true)   //设置是否显示标题
                .setTitleHeight(65)   //设置标题高度
                .setTitleText("please select")  //设置标题提示文本
                .setTitleTextSize(14) //设置标题字体大小 sp
                .setTitleTextColor(R.color.colorPrimary) //设置标题文本颜色
                .setItemHeight(40)  //设置item的高度
                .setItemWidth(0.9f)  //屏幕宽度*0.9
                .setItemTextColor(R.color.colorPrimaryDark)  //设置item字体颜色
                .setItemTextSize(14)  //设置item字体大小
                .setCancleButtonText("Cancle")  //设置最底部“取消”按钮文本
                .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {

                    @Override
                    public void onItemClick(NormalSelectionDialog dialog, View button, int
                            position) {

                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)  //设置是否可点击其他地方取消dialog
                .build()
                .setDatas(cameraList)
                .show();
    }

}
