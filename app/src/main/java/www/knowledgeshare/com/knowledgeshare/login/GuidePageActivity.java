package www.knowledgeshare.com.knowledgeshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.GuidePageBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.login.bean.HobbyActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.ViewPagerIndicator;


/**
 * Created by Administrator on 2017/8/24.
 */

public class GuidePageActivity extends BaseActivity {
    private ViewPager vp;
    private GuidePagerAdapter mGuidePagerAdapter;
    private int[] imgurls = {R.drawable.yindao_1, R.drawable.yindao_2, R.drawable.yindao_3,R.drawable.yindao_4
    ,R.drawable.yindao_5};
    private LinearLayout liner;
    private List<GuidePageBean.GuideBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setISshow(false);
        setContentView(R.layout.activity_guidepage);
        vp = (ViewPager) findViewById(R.id.vp);
        liner = (LinearLayout) findViewById(R.id.liner);

        requestGuide();
        vp.setOnPageChangeListener(new ViewPagerIndicator(GuidePageActivity.this,vp,liner,list.size()));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void requestGuide() {
        HttpParams params = new HttpParams();
        params.put("type","true");
        params.put("from","android");

        OkGo.<GuidePageBean>get(MyContants.bootstrappers)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<GuidePageBean>(GuidePageBean.class) {
                    @Override
                    public void onSuccess(Response<GuidePageBean> response) {
                        GuidePageBean guidePageBean = response.body();
                        if ( response.code() >= 200 && response.code() <= 204){
                            list = guidePageBean.getGuide();
                            if (list != null){
                                if (mGuidePagerAdapter == null) {
                                    mGuidePagerAdapter = new GuidePagerAdapter();
                                }
                                vp.setAdapter(mGuidePagerAdapter);
                            }
                        }
                    }
                });
    }

    private class GuidePagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(GuidePageActivity.this, R.layout.item_guide, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_guide);
            View tv_into = view.findViewById(R.id.tv_into);
            if (position == list.size() - 1) {
                tv_into.setVisibility(View.VISIBLE);
            } else {
                tv_into.setVisibility(View.GONE);
            }
            tv_into.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpUtils.putBoolean(GuidePageActivity.this, "guide", true);
                    Intent intent = new Intent(GuidePageActivity.this, HobbyActivity.class);
                    intent.putExtra("flag","0");
                    startActivity(intent);
                    finish();
                }
            });
//            imageView.setImageResource();
            Glide.with(GuidePageActivity.this).load(list.get(position).getImgurl()).into(imageView);
//            Glide.with(GuidePageActivity.this).load(list.get(position).getImgurl()).into(imageView);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
