package www.knowledgeshare.com.knowledgeshare.utils;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class BannerUtils {
    public static void startBanner(Banner banner, List<String> imageurls) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//注意这里的设置，设置不对容易报错
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //        List<Integer> list=new ArrayList<>();
        //        list.add(R.drawable.lunbo);
        //        list.add(R.drawable.lunbo);
        //        list.add(R.drawable.lunbo);
        //        list.add(R.drawable.lunbo);
        //        list.add(R.drawable.lunbo);
        //        banner.setImages(list);
        //设置图片集合
        banner.setImages(imageurls);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //        banner.setBannerTitles(null);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

}
