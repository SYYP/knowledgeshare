package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.SearchHistoryEntity;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.HotBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.SearchBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.SearchBean2;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.MyUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SoftKeyboardTool;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.FluidLayout;


public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_search;
    private TextView tv_back;
    private ImageView iv_delete, iv_delete_text;
    private RecyclerView recycler_lishi;
    private RecyclerView recycler_hot;
    private RecyclerView recycler_search;
    private List<String> hotNameList = new ArrayList<>();
    private List<String> hotIdsList = new ArrayList<>();
    private RecyclerHistoryAdapter mHistoryAdapter;
    private LinearLayout ll_lishi, ll_hot, ll_root_view, ll_result;
    private List<SearchHistoryEntity> mHistoryList = new ArrayList<>();
    private int position;
    private RecyclerView recycler_dashiban;
    private RecyclerView recycler_yinyueke;
    private FluidLayout liushiview;
    private List<SearchBean.XiaokeEntity> mXiaoke;
    private List<SearchBean.ZhuanlanEntity> mZhuanlan;
    private DaShiBanAdapter mDaShiBanAdapter;
    private YinYueKeAdapter mYinYueKeAdapter;
    private DaShiBanAdapter mMDaShiBanAdapter;
    private SearchAdapter mSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        iv_delete_text = (ImageView) findViewById(R.id.iv_delete_text);
        iv_delete_text.setOnClickListener(this);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
        recycler_lishi = (RecyclerView) findViewById(R.id.recycler_lishi);
        recycler_search = (RecyclerView) findViewById(R.id.recycler_search);
        recycler_hot = (RecyclerView) findViewById(R.id.recycler_hot);
        ll_lishi = (LinearLayout) findViewById(R.id.ll_lishi);
        ll_hot = (LinearLayout) findViewById(R.id.ll_hot);
        ll_result = (LinearLayout) findViewById(R.id.ll_result);
        ll_root_view = (LinearLayout) findViewById(R.id.ll_root_view);
        recycler_dashiban = (RecyclerView) findViewById(R.id.recycler_dashiban);
        recycler_yinyueke = (RecyclerView) findViewById(R.id.recycler_yinyueke);
        recycler_dashiban.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_dashiban.setNestedScrollingEnabled(false);
        recycler_yinyueke.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_yinyueke.setNestedScrollingEnabled(false);
        recycler_search.setLayoutManager(new LinearLayoutManager(this));
        recycler_search.setNestedScrollingEnabled(false);
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

        if (mHistoryList.size() == 6) {//实现本地历史搜索记录最多不超过5个
            mHistoryList.remove(5);
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
                    iv_delete_text.setVisibility(View.GONE);
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
                            mHistoryAdapter = new RecyclerHistoryAdapter(R.layout.search_history_item, mHistoryList);
                            recycler_lishi.setAdapter(mHistoryAdapter);
                        }
                    }
                }
            }
        });
        et_search.setOnEditorActionListener(editorActionListener);
    }


    TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //                SoftKeyboardTool.closeKeyboard(mSearchEt);//关闭软键盘
                recycler_search.setVisibility(View.GONE);
                ll_lishi.setVisibility(View.GONE);
                ll_hot.setVisibility(View.GONE);
                String content = et_search.getText().toString();
                if (!TextUtils.isEmpty(content))
                    doSavehistory(content);
                ll_result.setVisibility(View.VISIBLE);
                mMDaShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban1, mZhuanlan);
                recycler_dashiban.setAdapter(mMDaShiBanAdapter);
                mMDaShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(SearchActivity.this, ZhuanLanActivity.class);
                        intent.putExtra("id", mMDaShiBanAdapter.getData().get(position).getId() + "");
                        startActivity(intent);
                    }
                });
                mYinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke, mXiaoke);
                recycler_yinyueke.setAdapter(mYinYueKeAdapter);
                mYinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(SearchActivity.this, SoftMusicDetailActivity.class);
                        intent.putExtra("id", mYinYueKeAdapter.getData().get(position).getXk_id() + "");
                        startActivity(intent);
                    }
                });
                SoftKeyboardTool.closeKeyboard(SearchActivity.this);
                return true;
            }
            return false;
        }
    };


    private class SearchAdapter extends BaseQuickAdapter<SearchBean2, BaseViewHolder> {

        public SearchAdapter(@LayoutRes int layoutResId, @Nullable List<SearchBean2> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SearchBean2 item) {
            helper.setText(R.id.tv_content, item.getName());
        }
    }

    private void doSearch(final String content) {
        HttpParams params = new HttpParams();
        params.put("keyword", content);
        OkGo.<SearchBean>get(MyContants.LXKURL + "search")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<SearchBean>(SearchBean.class) {
                    @Override
                    public void onSuccess(Response<SearchBean> response) {
                        int code = response.code();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            SearchBean searchBean = response.body();
                            mXiaoke = searchBean.getXiaoke();
                            mZhuanlan = searchBean.getZhuanlan();
                            if (mZhuanlan.size() > 0 || mXiaoke.size() > 0) {
                                recycler_search.setVisibility(View.VISIBLE);
                                final List<SearchBean2> list = new ArrayList<SearchBean2>();
                                if (mZhuanlan != null && mZhuanlan.size() > 0) {
                                    for (int i = 0; i < mZhuanlan.size(); i++) {
                                        SearchBean.ZhuanlanEntity entity = mZhuanlan.get(i);
                                        String zl_name = entity.getZl_name();
                                        int id = entity.getId();
                                        list.add(new SearchBean2(zl_name, "zl", id + ""));
                                    }
                                }
                                if (mXiaoke != null && mXiaoke.size() > 0) {
                                    for (int i = 0; i < mXiaoke.size(); i++) {
                                        SearchBean.XiaokeEntity entity = mXiaoke.get(i);
                                        String xk_name = entity.getXk_name();
                                        int id = entity.getXk_id();
                                        list.add(new SearchBean2(xk_name, "xk", id + ""));
                                    }
                                }
                                mSearchAdapter = new SearchAdapter(R.layout.item_search, list);
                                recycler_search.setAdapter(mSearchAdapter);
                                mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        if (list.get(position).getType().equals("zl")) {
                                            Intent intent = new Intent(SearchActivity.this, ZhuanLanActivity.class);
                                            intent.putExtra("id", list.get(position).getId() + "");
                                            startActivity(intent);
                                            SoftKeyboardTool.closeKeyboard(SearchActivity.this);
                                        } else {
                                            Intent intent = new Intent(SearchActivity.this, SoftMusicDetailActivity.class);
                                            intent.putExtra("id", list.get(position).getId() + "");
                                            startActivity(intent);
                                            SoftKeyboardTool.closeKeyboard(SearchActivity.this);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(SearchActivity.this, "抱歉，没有该课程~", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SearchActivity.this, "抱歉，没有该课程~", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<SearchBean> response) {
                        super.onError(response);
                    }
                });
    }

    private void doSearch2(final String content) {
        HttpParams params = new HttpParams();
        params.put("keyword", content);
        OkGo.<SearchBean>get(MyContants.LXKURL + "search")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<SearchBean>(SearchBean.class) {
                    @Override
                    public void onSuccess(Response<SearchBean> response) {
                        int code = response.code();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            SearchBean searchBean = response.body();
                            mXiaoke = searchBean.getXiaoke();
                            mZhuanlan = searchBean.getZhuanlan();
                            if (mZhuanlan.size() > 0 || mXiaoke.size() > 0) {
                                recycler_search.setVisibility(View.VISIBLE);
                                final List<SearchBean2> list = new ArrayList<SearchBean2>();
                                if (mZhuanlan != null && mZhuanlan.size() > 0) {
                                    for (int i = 0; i < mZhuanlan.size(); i++) {
                                        SearchBean.ZhuanlanEntity entity = mZhuanlan.get(i);
                                        String zl_name = entity.getZl_name();
                                        int id = entity.getId();
                                        list.add(new SearchBean2(zl_name, "zl", id + ""));
                                    }
                                }
                                if (mXiaoke != null && mXiaoke.size() > 0) {
                                    for (int i = 0; i < mXiaoke.size(); i++) {
                                        SearchBean.XiaokeEntity entity = mXiaoke.get(i);
                                        String xk_name = entity.getXk_name();
                                        int id = entity.getXk_id();
                                        list.add(new SearchBean2(xk_name, "xk", id + ""));
                                    }
                                }
                                recycler_search.setVisibility(View.GONE);
                                ll_lishi.setVisibility(View.GONE);
                                ll_hot.setVisibility(View.GONE);
                                String content = et_search.getText().toString();
                                if (!TextUtils.isEmpty(content))
                                    doSavehistory(content);
                                ll_result.setVisibility(View.VISIBLE);
                                mMDaShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban1, mZhuanlan);
                                recycler_dashiban.setAdapter(mMDaShiBanAdapter);
                                mMDaShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(SearchActivity.this, ZhuanLanActivity.class);
                                        intent.putExtra("id", mMDaShiBanAdapter.getData().get(position).getId() + "");
                                        startActivity(intent);
                                    }
                                });
                                mYinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke, mXiaoke);
                                recycler_yinyueke.setAdapter(mYinYueKeAdapter);
                                mYinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(SearchActivity.this, SoftMusicDetailActivity.class);
                                        intent.putExtra("id", mYinYueKeAdapter.getData().get(position).getXk_id() + "");
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(SearchActivity.this, "抱歉，没有该课程~", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SearchActivity.this, "抱歉，没有该课程~", Toast.LENGTH_SHORT).show();
                        }
                        SoftKeyboardTool.closeKeyboard(SearchActivity.this);
                    }

                    @Override
                    public void onError(Response<SearchBean> response) {
                        super.onError(response);
                    }
                });
    }

    private void doSearchHot(final String content) {
        HttpParams params = new HttpParams();
        params.put("keyword", content);
        OkGo.<SearchBean>get(MyContants.LXKURL + "search")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<SearchBean>(SearchBean.class) {
                    @Override
                    public void onSuccess(Response<SearchBean> response) {
                        int code = response.code();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            SearchBean searchBean = response.body();
                            mXiaoke = searchBean.getXiaoke();
                            mZhuanlan = searchBean.getZhuanlan();
                            if (mZhuanlan.size() > 0 || mXiaoke.size() > 0) {
                                recycler_search.setVisibility(View.GONE);
                                ll_lishi.setVisibility(View.GONE);
                                ll_hot.setVisibility(View.GONE);
                                String content = et_search.getText().toString();
                                if (!TextUtils.isEmpty(content)) {
                                    doSavehistory(content);
                                    doSearch(content);
                                }
                                ll_result.setVisibility(View.VISIBLE);
                                mMDaShiBanAdapter = new DaShiBanAdapter(R.layout.item_dashiban1, mZhuanlan);
                                recycler_dashiban.setAdapter(mMDaShiBanAdapter);
                                mMDaShiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(SearchActivity.this, ZhuanLanActivity.class);
                                        intent.putExtra("id", mMDaShiBanAdapter.getData().get(position).getId() + "");
                                        startActivity(intent);
                                    }
                                });
                                mYinYueKeAdapter = new YinYueKeAdapter(R.layout.item_yinyueke, mXiaoke);
                                recycler_yinyueke.setAdapter(mYinYueKeAdapter);
                                mYinYueKeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(SearchActivity.this, SoftMusicDetailActivity.class);
                                        intent.putExtra("id", mYinYueKeAdapter.getData().get(position).getXk_id() + "");
                                        startActivity(intent);
                                    }
                                });
                                SoftKeyboardTool.closeKeyboard(SearchActivity.this);
                            } else {
                                Toast.makeText(SearchActivity.this, "抱歉，没有该课程~", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SearchActivity.this, "抱歉，没有该课程~", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<SearchBean> response) {
                        super.onError(response);
                    }
                });
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
                    doSearch2(mHistoryAdapter.getData().get(position).getContent());
                }
            });
        } else {
            iv_delete.setVisibility(View.GONE);
        }
        OkGo.<HotBean>get(MyContants.LXKURL + "hot")
                .tag(this)
                .execute(new JsonCallback<HotBean>(HotBean.class) {
                    @Override
                    public void onSuccess(Response<HotBean> response) {
                        int code = response.code();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            HotBean hotBean = response.body();
                            List<HotBean.DataEntity> hotBeanData = hotBean.getData();
                            if (hotBeanData != null && hotBeanData.size() > 0) {
                                for (int i = 0; i < hotBeanData.size(); i++) {
                                    String name = hotBeanData.get(i).getName();
                                    String id = hotBeanData.get(i).getId() + "";
                                    hotNameList.add(name);
                                    hotIdsList.add(id);
                                }
                                setHot();
                            }
                        } else {
                        }
                    }
                });
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

    private void setHot() {
        liushiview.removeAllViews();
        for (int i = 0; i < hotNameList.size(); i++) {
            final TextView tv = (TextView) View.inflate(this, R.layout.search_hot_item, null);
            tv.setText(hotNameList.get(i));
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 12, 12, 12);
            liushiview.addView(tv, params);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doSearchHot(tv.getText().toString());
                }
            });
        }
    }

    private class DaShiBanAdapter extends BaseQuickAdapter<SearchBean.ZhuanlanEntity, BaseViewHolder> {

        public DaShiBanAdapter(@LayoutRes int layoutResId, @Nullable List<SearchBean.ZhuanlanEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SearchBean.ZhuanlanEntity item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            //            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            //            layoutParams.height = MyUtils.dip2px(SearchActivity.this, 90);
            //            imageView.setLayoutParams(layoutParams);
            Glide.with(MyApplication.getGloableContext()).load(item.getZl_img()).into(imageView);
            helper.setText(R.id.tv_name, item.getZl_name())
                    .setText(R.id.tv_introduce, item.getZl_introduce())
                    .setText(R.id.tv_update_name, item.getZl_update_name())
                    .setText(R.id.tv_update_time, item.getZl_update_time())
                    .setText(R.id.tv_price, item.getZl_price());
        }
    }

    private class YinYueKeAdapter extends BaseQuickAdapter<SearchBean.XiaokeEntity, BaseViewHolder> {

        public YinYueKeAdapter(@LayoutRes int layoutResId, @Nullable List<SearchBean.XiaokeEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SearchBean.XiaokeEntity item) {
            ImageView imageView = (ImageView) helper.getView(R.id.iv_tupian);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.height = MyUtils.dip2px(SearchActivity.this, 130);
            imageView.setLayoutParams(layoutParams);
            Glide.with(MyApplication.getGloableContext()).load(item.getXk_image()).into(imageView);
            helper.setText(R.id.tv_buy_count, item.getBuy_count())
                    .setText(R.id.tv_name, item.getXk_name())
                    .setText(R.id.tv_jie_count, item.getNodule_count())
                    .setText(R.id.tv_teacher_name, item.getTeacher_name())
                    .setText(R.id.tv_price, item.getXk_price())
                    .setText(R.id.tv_time, item.getTime_count())
                    .setText(R.id.tv_teacher_tag, item.getXk_teacher_tags());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                SoftKeyboardTool.closeKeyboard(this);
                break;
            case R.id.iv_delete:
                showDialog(Gravity.CENTER, R.style.Alpah_aniamtion);
                break;
            case R.id.iv_delete_text:
                et_search.setText("");
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
