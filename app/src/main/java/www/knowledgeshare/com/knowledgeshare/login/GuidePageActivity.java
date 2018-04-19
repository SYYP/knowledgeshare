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

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.login.bean.HobbyActivity;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.ViewPagerIndicator;


/**
 * Created by Administrator on 2017/8/24.
 */

public class GuidePageActivity extends BaseActivity {
    private ViewPager vp;
    private GuidePagerAdapter mGuidePagerAdapter;
    private LinearLayout liner;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setISshow(false);
        setContentView(R.layout.activity_guidepage);
        vp = (ViewPager) findViewById(R.id.vp);
        liner = (LinearLayout) findViewById(R.id.liner);
        //        requestGuide();
        list = (List<String>) getIntent().getSerializableExtra("data");
        vp.setOffscreenPageLimit(list.size());
        if (list != null) {
            if (mGuidePagerAdapter == null) {
                vp.setOnPageChangeListener(new ViewPagerIndicator(GuidePageActivity.this, vp, liner, list.size()));
                mGuidePagerAdapter = new GuidePagerAdapter();
            }
            vp.setAdapter(mGuidePagerAdapter);
        }
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
                    intent.putExtra("flag", "0");
                    startActivity(intent);
                    finish();
                }
            });
            Glide.with(MyApplication.getGloableContext()).load(list.get(position)).into(imageView);
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
