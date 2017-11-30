package www.knowledgeshare.com.knowledgeshare.login.bean;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class HobbyActivity extends BaseActivity {
   private TagFlowLayout mFlowLayout;
    private String[] mVals = new String[]
            {"美声", "爵士", "摇滚 ", "音乐剧", "交响乐", "R&B",
                    "乡村风格", "轻音乐", "民谣", "蓝调", "hiphop",
                    "乐器", "艺术管理", "编曲", "中西方音乐史","其他"};
    private TextView login_sso;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.avtivity_liushi);
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout = (TagFlowLayout)findViewById(R.id.id_flowlayout);
        login_sso = (TextView) findViewById(R.id.login_sso);

        mFlowLayout.setAdapter(new TagAdapter<String>(mVals)
        {

            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowLayout, false);
                tv.setText(s);
                return  tv;
            }
            @Override

            public boolean setSelected(int position, String s)
            {
                return false;
            }
        });
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                Toast.makeText(HobbyActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                                            @Override
                                            public void onSelected(Set<Integer> selectPosSet) {
                                                setTitle("choose:" + selectPosSet.toString());
                                            }
                                        });

        login_sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HobbyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
