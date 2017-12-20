package www.knowledgeshare.com.knowledgeshare.login.bean;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.TagBean;
import www.knowledgeshare.com.knowledgeshare.bean.VerifyCodesBean;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.utils.FlowLayout;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

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
    List<TagBean.DataBean> list1 = new ArrayList<>();
    private String password;
    private String verify;
    private String flag;
    StringBuilder sb = new StringBuilder();
    private String tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setISshow(false);
        setContentView(R.layout.avtivity_liushi);
        mFlowLayout = (FlowLayout) findViewById(R.id.hob_liu);
        login_sso = (TextView) findViewById(R.id.login_sso);

        /**
         *  0是导航页传过来，存在本地，跳转首页
         *  1是注册爷传过来，跳转登录
         */
        flag = getIntent().getStringExtra("flag");

        requestTag();

        password = getIntent().getStringExtra("password");
        verify = getIntent().getStringExtra("verify");

        login_sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    for (int i = 0; i < list1.size(); i++) {
                        if (list1.get(i).isaBoolean()){
                            sb.append(list1.get(i).getId()+",");
                        }
                    }
                    if (TextUtils.isEmpty(sb)){
                        TUtils.showShort(HobbyActivity.this,"至少选择一项兴趣");
                    }else {
                        //当循环结束后截取最后一个逗号
                        tag = sb.substring(0,sb.length()-1);
                        SpUtils.putString(HobbyActivity.this,"tag", tag);
                        sb.delete(0,sb.length());
                        if (TextUtils.equals("0",flag)){
                            Intent intent = new Intent(HobbyActivity.this, MainActivity.class);
                            intent.putExtra("tag",tag);
                            startActivity(intent);
                        }else if (TextUtils.equals("1",flag)){
                            requestRegistSetTwo();
                        }
                    }

            }
        });
    }

    private void requestTag() {
        OkGo.<TagBean>get(MyContants.tag)
                 .tag(this)
                .execute(new JsonCallback<TagBean>(TagBean.class) {
                    @Override
                    public void onSuccess(Response<TagBean> response) {
                        list1 = response.body().getData();
                        //动态添加view
                        final ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams
                                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.leftMargin = 20;
                        lp.rightMargin = 20;
                        lp.topMargin = 20;
                        lp.bottomMargin = 20;
                        for (int i = 0; i < list1.size(); i++) {
                            final int e = i;
                            final TextView view = new TextView(HobbyActivity.this);
                            view.setText(list1.get(e).getTag_name());
                            view.setTextColor(Color.BLACK);
                            view.setTextSize(16);
                            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.normal_bg));
                            mFlowLayout.addView(view, lp);

                            view.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                        if (list1.get(e).isaBoolean()) {
                                            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.normal_bg));
                                            list1.get(e).setaBoolean(false);
                                        } else if (!list1.get(e).isaBoolean()) {
                                            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.checked_bg));
                                            list1.get(e).setaBoolean(true);
                                        }
                                }
                            });
                        }
                    }
                });

    }

    private void requestRegistSetTwo() {
        HttpParams params = new HttpParams();
        params.put("mobile", SpUtils.getString(HobbyActivity.this,"mobile",""));
        params.put("password",new StringBuffer(password).reverse().toString());
        params.put("verify",verify);
        params.put("tagid",tag);
        OkGo.<VerifyCodesBean>post(MyContants.registSetTwo)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<VerifyCodesBean>(HobbyActivity.this,VerifyCodesBean.class) {
                    @Override
                    public void onSuccess(Response<VerifyCodesBean> response) {
                        VerifyCodesBean verifyCodesBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            Intent intent = new Intent(HobbyActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            TUtils.showShort(HobbyActivity.this,verifyCodesBean.getMessage());
                        }
                    }
                });
    }

}
