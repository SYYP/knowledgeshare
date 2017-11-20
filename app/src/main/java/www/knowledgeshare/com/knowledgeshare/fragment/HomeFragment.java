package www.knowledgeshare.com.knowledgeshare.fragment;

import android.view.View;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseFragment;

/**
 * Created by Administrator on 2017/11/17.
 */

public class HomeFragment extends BaseFragment {
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_home, null);
        return inflate;
    }

    @Override
    protected void initData() {

    }
}
