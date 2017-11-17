package www.knowledgeshare.com.knowledgeshare.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/11/18.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected final String TAG = "LazyLoadFragment";
    private View view;
    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();//主要是方便子类调用
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //        System.out.println("onCreateView被调用");
     //   view = inflater.inflate(setContentView(), container, false);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        return initView();
    }
    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }
    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

//    /**
//     * 设置Fragment要显示的布局
//     *
//     * @return 布局的layoutId
//     */
//    protected abstract int setContentView();
//
//    /**
//     * 获取设置的布局
//     *
//     * @return
//     */
//    protected View getContentView() {
//        return view;
//    }

//    /**
//     * 找出对应的控件
//     *
//     * @param id
//     * @param <T>
//     * @return
//     */
//    protected <T extends View> T findViewById(int id) {
//
//        return (T) getContentView().findViewById(id);
//    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }

    public float getpx(int dp){
        return dp*getResources().getDisplayMetrics().density;
    }

    //强制子类实现该方法，展示界面view
    protected abstract View initView();//抽象方法不能私有

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        //        System.out.println("onActivityCreated被调用");
    }

    /**
     * 当孩子需要初始化数据，或者联网请求绑定数据，展示数据的 等等可以重写该方法
     */
    protected abstract void initData();


}
