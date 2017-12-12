package www.knowledgeshare.com.knowledgeshare.login.bean;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.utils.FlowLayout;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class HobbyActivity extends BaseActivity {
    private FlowLayout mFlowLayout;
    private String[] mVals = new String[]
            {"美声", "爵士", "摇滚 ", "音乐剧", "交响乐", "R&B",
                    "乡村风格", "轻音乐", "民谣", "蓝调", "hiphop",
                    "乐器", "艺术管理", "编曲", "中西方音乐史", "其他"};
    private TextView login_sso;
    List<Honnybean> list = new ArrayList<>();
    Honnybean honnybean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setISshow(false);
        setContentView(R.layout.avtivity_liushi);
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.hob_liu);
        login_sso = (TextView) findViewById(R.id.login_sso);
        //添加数据
        inindata();
        login_sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HobbyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                finish();
            }
        });

    }

    private void inindata() {
        honnybean = new Honnybean();
        honnybean.setName("美声");
        list.add(honnybean);
        Honnybean honnybean2 = new Honnybean();
        honnybean2.setName("音乐剧");
        list.add(honnybean2);
        Honnybean honnybean3 = new Honnybean();
        honnybean3.setName("交响乐");
        list.add(honnybean3);
        Honnybean honnybean4 = new Honnybean();
        honnybean4.setName("R&B");
        list.add(honnybean4);
        Honnybean honnybean5 = new Honnybean();
        honnybean5.setName("乡村风格");
        list.add(honnybean5);
        Honnybean honybean8 = new Honnybean();
        honybean8.setName("轻音乐");
        list.add(honybean8);
        Honnybean honnybean6 = new Honnybean();
        honnybean6.setName("民谣");
        list.add(honnybean6);
        Honnybean honnybean7 = new Honnybean();
        honnybean7.setName("蓝调");
        list.add(honnybean7);
        Honnybean honnybean11 = new Honnybean();
        honnybean11.setName("hiphop");
        list.add(honnybean11);
        Honnybean honybean9 = new Honnybean();
        honybean9.setName("乐器");
        list.add(honybean9);
        Honnybean honybean12 = new Honnybean();
        honybean12.setName("摇滚");
        list.add(honybean12);
        Honnybean honybean13 = new Honnybean();
        honybean13.setName("音乐治疗");
        list.add(honybean13);
        Honnybean honybean14= new Honnybean();
        honybean14.setName("艺术管理");
        list.add(honybean14);
        Honnybean honybean15= new Honnybean();
        honybean15.setName("艺术管理");
        list.add(honybean9);
        /*Honnybean honybean16 = new Honnybean();
        honybean16.setName("hiphop");*/
        list.add(honybean9);
        Honnybean honybean17 = new Honnybean();
        honybean17.setName("编曲");
        list.add(honybean17);
        Honnybean honybean18 = new Honnybean();
        honybean18.setName("艺考");
        list.add(honybean18);
        Honnybean honybean19 = new Honnybean();
        honybean19.setName("中西方音乐史");
        list.add(honybean19);
        Honnybean honnybean1 = new Honnybean();
        honnybean1.setName("爵士");
        list.add(honnybean1);
        Honnybean honybean10 = new Honnybean();
        honybean10.setName("其他");
        list.add(honybean10);
        //动态添加view
        final ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 20;
        lp.rightMargin = 20;
        lp.topMargin = 20;
        lp.bottomMargin = 20;
        for (int i = 0; i < list.size(); i++) {
            final int e = i;
            final TextView view = new TextView(this);
            view.setText(list.get(e).getName());
            view.setTextColor(Color.BLACK);
            view.setTextSize(16);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.normal_bg));
            mFlowLayout.addView(view, lp);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub

                    if (list.get(e).isaBoolean() == true) {
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.normal_bg));
                        list.get(e).setaBoolean(false);
                    } else if (list.get(e).isaBoolean() == false) {
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.checked_bg));
                        list.get(e).setaBoolean(true);
                    }

                }
            });

        }

    }


}
