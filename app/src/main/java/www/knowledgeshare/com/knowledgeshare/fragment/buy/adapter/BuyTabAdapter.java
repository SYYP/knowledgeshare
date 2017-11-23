package www.knowledgeshare.com.knowledgeshare.fragment.buy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */

public class BuyTabAdapter extends FragmentPagerAdapter {

    private FragmentManager fm;
    private List<Fragment> fragmentList;
    private List<String> tab_list;

    public BuyTabAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> tab_list) {
        super(fm);
        this.fm = fm;
        this.fragmentList = fragmentList;
        this.tab_list = tab_list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tab_list.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return tab_list.get(position % tab_list.size());
    }
}
