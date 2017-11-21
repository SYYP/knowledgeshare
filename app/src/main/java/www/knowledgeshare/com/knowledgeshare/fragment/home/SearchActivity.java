package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.SearchHistoryEntity;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.FluidLayout;

import static com.taobao.accs.ACCSManager.mContext;


public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_search;
    private TextView tv_back;
    private ImageView iv_delete;
    private RecyclerView recycler_lishi;
    private RecyclerView recycler_hot;
    private List<String> hotList = new ArrayList<>();
    private RecyclerHistoryAdapter mHistoryAdapter;
    private RecyclerHotAdapter mHotAdapter;
    private LinearLayout ll_lishi, ll_hot, ll_root_view, ll_result;
    private List<SearchHistoryEntity> mHistoryList = new ArrayList<>();
    private int position;
    private RecyclerView recycler_dashiban;
    private RecyclerView recycler_yinyueke;
    private FluidLayout liushiview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
        recycler_lishi = (RecyclerView) findViewById(R.id.recycler_lishi);
        recycler_hot = (RecyclerView) findViewById(R.id.recycler_hot);
        ll_lishi = (LinearLayout) findViewById(R.id.ll_lishi);
        ll_hot = (LinearLayout) findViewById(R.id.ll_hot);
        ll_result = (LinearLayout) findViewById(R.id.ll_result);
        ll_root_view = (LinearLayout) findViewById(R.id.ll_root_view);
        recycler_dashiban = (RecyclerView) findViewById(R.id.recycler_dashiban);
        recycler_yinyueke = (RecyclerView) findViewById(R.id.recycler_yinyueke);
        recycler_dashiban.setLayoutManager(new GridLayoutManager(mContext, 2));
        recycler_dashiban.setNestedScrollingEnabled(false);
        recycler_yinyueke.setLayoutManager(new GridLayoutManager(mContext, 2));
        recycler_yinyueke.setNestedScrollingEnabled(false);
        liushiview = (FluidLayout) findViewById(R.id.liushiview);
        initData();
        initListener();
    }

    //判断本地数据中有没有存在搜索过的数据，查重
    private boolean isHasSelectData(String content) {
        if (mHistoryList == null || mHistoryList.size() == 0) {
            return false;
        }
        for (int i = 0; i < mHistoryList.size(); i++) {
            if (mHistoryList.get(i).getContent().equals(content)) {
                position = i;
                return true;
            }
        }
        return false;
    }

    private void doSavehistory(String content) {

        if (isHasSelectData(content)) {//查重
            mHistoryList.remove(position);
        }
        //后来搜索的文字放在集合中的第一个位置
        mHistoryList.add(0, new SearchHistoryEntity(content));

        if (mHistoryList.size() == 5) {//实现本地历史搜索记录最多不超过5个
            mHistoryList.remove(4);
        }
        //将这个mHistoryListData保存到sp中，其实sp中保存的就是这个mHistoryListData集合
        saveHistory();
    }

    /**
     * 保存历史查询记录
     */
    private void saveHistory() {
        SpUtils.putString(this, "history",
                new Gson().toJson(mHistoryList));//将java对象转换成json字符串进行保存
    }

    /**
     * 获取历史查询记录
     *
     * @return
     */
    private List<SearchHistoryEntity> getHistory() {
        String historyJson = SpUtils.getString(this, "history", "");
        if (historyJson != null && !historyJson.equals("")) {//必须要加上后面的判断，因为获取的字符串默认值就是空字符串
            //将json字符串转换成list集合
            return new Gson().fromJson(historyJson, new TypeToken<List<SearchHistoryEntity>>() {
            }.getType());
        }
        return new ArrayList<SearchHistoryEntity>();
    }

    private void initListener() {
        //本来下面这几行代码是解决afterTextChanged反应太快，但是并不管用
        //        int inputType= InputType.TYPE_CLASS_TEXT |
        //                InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
        //        et_search.setInputType(inputType);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(et_search.getText().toString())) {
                    doSearch(et_search.getText().toString());
                } else {
                    ll_root_view.setBackgroundResource(R.color.white);
                    ll_lishi.setVisibility(View.VISIBLE);
                    ll_hot.setVisibility(View.VISIBLE);
                    ll_result.setVisibility(View.GONE);
                    mHistoryList = getHistory();//从本地取出来
                    if (mHistoryList != null && mHistoryList.size() > 0) {
                        iv_delete.setVisibility(View.VISIBLE);
                        if (mHistoryAdapter != null) {
                            mHistoryAdapter.setNewData(mHistoryList);
                            mHistoryAdapter.notifyDataSetChanged();//刷新一下界面
                        } else {
                            mHistoryAdapter = new RecyclerHistoryAdapter(R.layout.search_hot_item, mHistoryList);
                            recycler_lishi.setAdapter(mHistoryAdapter);
                        }
                    }
                }
            }
        });
    }

    private void doSearch(final String content) {
        ll_root_view.setBackgroundResource(R.color.huise);
        ll_lishi.setVisibility(View.GONE);
        ll_hot.setVisibility(View.GONE);
        doSavehistory(content);
        ll_result.setVisibility(View.VISIBLE);
        DaShiBanAdapter daShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban, hotList);
        recycler_dashiban.setAdapter(daShiBanAdapter);

        YinYueKeAdapter yinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke, hotList);
        recycler_yinyueke.setAdapter(yinYueKeAdapter);
    }

    private void initData() {
        recycler_lishi.setLayoutManager(new LinearLayoutManager(this));
        recycler_lishi.setNestedScrollingEnabled(false);
        recycler_hot.setLayoutManager(new GridLayoutManager(this, 4));
        recycler_hot.setNestedScrollingEnabled(false);
        mHistoryList = getHistory();//从本地取出来
        if (mHistoryList != null && mHistoryList.size() > 0) {
            mHistoryAdapter = new RecyclerHistoryAdapter(R.layout.search_history_item, mHistoryList);
            recycler_lishi.setAdapter(mHistoryAdapter);
            mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    doSearch(mHistoryAdapter.getData().get(position).getContent());
                }
            });
        } else {
            iv_delete.setVisibility(View.GONE);
        }

        hotList.add("轻松音乐课");
        hotList.add("轻松音乐课");
        hotList.add("轻松音乐课");
        hotList.add("轻松音乐课");
        hotList.add("轻松音乐课");
        hotList.add("轻松音乐课");
        mHotAdapter = new RecyclerHotAdapter(R.layout.search_hot_item, hotList);
        recycler_hot.setAdapter(mHotAdapter);
        mHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        setHot();
    }

    class RecyclerHistoryAdapter extends BaseQuickAdapter<SearchHistoryEntity, BaseViewHolder> {

        public RecyclerHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<SearchHistoryEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, SearchHistoryEntity item) {
            helper.setText(R.id.tv_history, item.getContent());
            helper.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv_history = helper.getView(R.id.tv_history);
                    String history = tv_history.getText().toString();
                    for (int y = 0; y < mHistoryList.size(); y++) {
                        if (mHistoryList.get(y).getContent().equals(history)) {
                            mHistoryList.remove(y);
                        }
                    }
                    saveHistory();
                    initData();
                }
            });
        }
    }

    class RecyclerHotAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public RecyclerHotAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_hot, item);
        }
    }

    private void setHot() {
        liushiview.removeAllViews();
        for (int i = 0; i < hotList.size(); i++) {
            TextView tv = (TextView) View.inflate(this, R.layout.search_hot_item, null);
            tv.setText(hotList.get(i));
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 12, 12, 12);
            liushiview.addView(tv,params);
        }
    }

    private class DaShiBanAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            //            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            //            int www = MyUtils.getScreenWidth(mContext) / 2 - 20;
            //            layoutParams.width=www;
            //            layoutParams.height=www*12/17;
            //            imageView.setLayoutParams(layoutParams);
            Glide.with(mContext).load("https://ss0.baidu.com/73t1b" +
                    "jeh1BF3odCf/it/u=36377501,1487953910&fm=73&s=" +
                    "54BA3ED516335F824A2D777E03005078")
                    .into(imageView);
        }
    }

    private class YinYueKeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public YinYueKeAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int www = MyUtils.getScreenWidth(mContext) / 2 - 20;
            layoutParams.width = www;
            layoutParams.height = www;
            imageView.setLayoutParams(layoutParams);
            Glide.with(mContext).load("https://ss0.baidu.com/73t1b" +
                    "jeh1BF3odCf/it/u=36377501,1487953910&fm=73&s=" +
                    "54BA3ED516335F824A2D777E03005078").into(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_delete:
                showDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                break;
        }
    }

    private void showDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_history)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.show();
        TextView tv_canel = dialog.getView(R.id.tv_canel);
        tv_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭dialog
                dialog.close();
            }
        });
        TextView tv_yes = dialog.getView(R.id.tv_yes);
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoryList.clear();
                saveHistory();
                if (mHistoryAdapter != null) {
                    mHistoryAdapter.getData().clear();//如果不加这句的话第二次删除就会不生效，真不知道为什么
                    mHistoryAdapter.notifyDataSetChanged();
                }
                iv_delete.setVisibility(View.GONE);
                dialog.close();
            }
        });
    }

}
