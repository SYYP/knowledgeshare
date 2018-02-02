package www.knowledgeshare.com.knowledgeshare.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.bean.SearchHistoryEntity;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.db.BofangHistroyBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.MusicTypeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.SearchMusicBean2;
import www.knowledgeshare.com.knowledgeshare.fragment.home.player.PlayerBean;
import www.knowledgeshare.com.knowledgeshare.service.MediaService;
import www.knowledgeshare.com.knowledgeshare.utils.BaseDialog;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.NetWorkUtils;
import www.knowledgeshare.com.knowledgeshare.utils.SoftKeyboardTool;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;


public class SearchMusicActivity2 extends BaseActivity implements View.OnClickListener {//小课进来的，因为bean类不一样，所以用2个类
    private EditText et_search;
    private TextView tv_back;
    private ImageView iv_delete;
//    private ImageView iv_delete_text;
    private RecyclerView recycler_lishi;
    private RecyclerView recycler_search;
    private RecyclerView recycler_result;
    private RecyclerHistoryAdapter mHistoryAdapter;
    private LinearLayout ll_lishi, ll_hot, ll_root_view;
    private List<SearchHistoryEntity> mHistoryList = new ArrayList<>();
    private int position;
    private SearchAdapter mSearchAdapter;
    private List<SearchMusicBean2.DataEntity> mData;
    private ResultAdapter mResultAdapter;
    private BaseDialog mNetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_music);
        setISshow(false);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
//        iv_delete_text = (ImageView) findViewById(R.id.iv_delete_text);
//        iv_delete_text.setOnClickListener(this);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
        recycler_lishi = (RecyclerView) findViewById(R.id.recycler_lishi);
        recycler_search = (RecyclerView) findViewById(R.id.recycler_search);
        recycler_result = (RecyclerView) findViewById(R.id.recycler_result);
        ll_lishi = (LinearLayout) findViewById(R.id.ll_lishi);
        ll_hot = (LinearLayout) findViewById(R.id.ll_hot);
        ll_root_view = (LinearLayout) findViewById(R.id.ll_root_view);
        recycler_search.setLayoutManager(new LinearLayoutManager(this));
        recycler_search.setNestedScrollingEnabled(false);
        recycler_result.setLayoutManager(new LinearLayoutManager(this));
        recycler_result.setNestedScrollingEnabled(false);
        initData();
        initListener();
        initNETDialog();
    }

    private void initNETDialog() {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        mNetDialog = builder.setViewId(R.layout.dialog_iswifi)
                //设置dialogpadding
                .setPaddingdp(10, 0, 10, 0)
                //设置显示位置
                .setGravity(Gravity.CENTER)
                //设置动画
                .setAnimation(R.style.Alpah_aniamtion)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
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
        SpUtils.putString(this, "history_music_xk",
                new Gson().toJson(mHistoryList));//将java对象转换成json字符串进行保存
    }

    /**
     * 获取历史查询记录
     *
     * @return
     */
    private List<SearchHistoryEntity> getHistory() {
        String historyJson = SpUtils.getString(this, "history_music_xk", "");
        if (historyJson != null && !historyJson.equals("")) {//必须要加上后面的判断，因为获取的字符串默认值就是空字符串
            //将json字符串转换成list集合
            return new Gson().fromJson(historyJson, new TypeToken<List<SearchHistoryEntity>>() {
            }.getType());
        }
        return new ArrayList<SearchHistoryEntity>();
    }

    TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //                SoftKeyboardTool.closeKeyboard(mSearchEt);//关闭软键盘
                ll_lishi.setVisibility(View.GONE);
                String content = et_search.getText().toString();
                if (!TextUtils.isEmpty(content))
                    doSavehistory(content);
//                iv_delete_text.setVisibility(View.VISIBLE);
                recycler_search.setVisibility(View.GONE);
                recycler_result.setVisibility(View.VISIBLE);
                mResultAdapter = new ResultAdapter(R.layout.item_search_music, mData);
                recycler_result.setAdapter(mResultAdapter);
                SoftKeyboardTool.closeKeyboard(SearchMusicActivity2.this);
                mResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        setISshow(true);
                        SearchMusicBean2.DataEntity item = mData.get(position);
                        //刷新小型播放器
                        PlayerBean playerBean = new PlayerBean(item.getT_header(), item.getName(), item.getParent_name(),
                                item.getVideo_url(), position);
                        gobofang(playerBean);
                        //设置进入播放主界面的数据
                        List<MusicTypeBean> musicTypeBeanList = new ArrayList<MusicTypeBean>();
                        for (int i = 0; i < mData.size(); i++) {
                            SearchMusicBean2.DataEntity childEntity = mData.get(i);
                            MusicTypeBean musicTypeBean = new MusicTypeBean("softmusicdetail",
                                    childEntity.getT_header(), childEntity.getName(), childEntity.getId() + "",
                                    false);
                            musicTypeBean.setMsg("musicplayertype");
                            musicTypeBeanList.add(musicTypeBean);
                        }
                        MediaService.insertMusicTypeList(musicTypeBeanList);
                        //加入默认的播放列表
                        List<PlayerBean> list = new ArrayList<PlayerBean>();
                        for (int i = 0; i < mData.size(); i++) {
                            SearchMusicBean2.DataEntity entity = mData.get(i);
                            PlayerBean playerBean1 = new PlayerBean(entity.getT_header(), entity.getName(), entity.getParent_name(), entity.getVideo_url());
                            list.add(playerBean1);
                        }
                        MediaService.insertMusicList(list);
                        //还要传递播放列表的浏览历史list到service中，播放下一首上一首的时候控制浏览历史的增加
                        List<BofangHistroyBean> histroyBeanList = new ArrayList<BofangHistroyBean>();
                        for (int i = 0; i < mData.size(); i++) {
                            SearchMusicBean2.DataEntity childEntity = mData.get(i);
                            BofangHistroyBean bofangHistroyBean = new BofangHistroyBean("softmusicdetail", childEntity.getId(), childEntity.getName(),
                                    childEntity.getCreated_at(), childEntity.getVideo_url(), childEntity.getGood_count(),
                                    childEntity.getCollect_count(), childEntity.getView_count(), false, false,
                                    childEntity.getT_header(), childEntity.getParent_name(),
                                    childEntity.getShare_h5_url(), SystemClock.currentThreadTimeMillis()
                                    , childEntity.getXk_id()+"", childEntity.getParent_name(), childEntity.getTxt_url());
                            histroyBeanList.add(bofangHistroyBean);
                        }
                        MediaService.insertBoFangHistroyList(histroyBeanList);
                    }
                });
                return true;
            }
            return false;
        }
    };

    private void gobofang(final PlayerBean playerBean) {
        int apnType = NetWorkUtils.getAPNType(this);
        if (apnType == 0) {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
        } else if (apnType == 2 || apnType == 3 || apnType == 4) {
            if (SpUtils.getBoolean(this, "nowifiallowlisten", false)) {//记住用户允许流量播放
                playerBean.setMsg("refreshplayer");
                EventBus.getDefault().postSticky(playerBean);
                mMyBinder.setMusicUrl(playerBean.getVideo_url());
                mMyBinder.playMusic(playerBean);
                mNetDialog.dismiss();
                ClickPopShow();
                SpUtils.putBoolean(this, "nowifiallowlisten", true);
            } else {
                mNetDialog.show();
                mNetDialog.getView(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playerBean.setMsg("refreshplayer");
                        EventBus.getDefault().postSticky(playerBean);
                        mMyBinder.setMusicUrl(playerBean.getVideo_url());
                        mMyBinder.playMusic(playerBean);
                        mNetDialog.dismiss();
                        ClickPopShow();
                        SpUtils.putBoolean(SearchMusicActivity2.this, "nowifiallowlisten", true);
                    }
                });
                mNetDialog.getView(R.id.tv_canel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNetDialog.dismiss();
                    }
                });
            }
        } else if (NetWorkUtils.isMobileConnected(SearchMusicActivity2.this)) {
            Toast.makeText(this, "wifi不可用呢~", Toast.LENGTH_SHORT).show();
        } else {
            playerBean.setMsg("refreshplayer");
            EventBus.getDefault().postSticky(playerBean);
            mMyBinder.setMusicUrl(playerBean.getVideo_url());
            mMyBinder.playMusic(playerBean);
            ClickPopShow();
        }
    }

    private void initListener() {
        et_search.setOnEditorActionListener(editorActionListener);
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
//                    iv_delete_text.setVisibility(View.GONE);
                    ll_lishi.setVisibility(View.VISIBLE);
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
    }


    private class ResultAdapter extends BaseQuickAdapter<SearchMusicBean2.DataEntity, BaseViewHolder> {

        public ResultAdapter(@LayoutRes int layoutResId, @Nullable List<SearchMusicBean2.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final SearchMusicBean2.DataEntity item) {
            String created_at = item.getCreated_at();
            String[] split = created_at.split(" ");
            helper.setText(R.id.tv_name, item.getName())
                    .setText(R.id.tv_time, split[0])
                    .setText(R.id.tv_tname, item.getT_name())
                    .setText(R.id.tv_look_count, item.getIs_view() == 0 ? item.getView_count() + "" : item.getView_count_true() + "")
                    .setText(R.id.tv_collect_count, item.getIs_collect() == 0 ? item.getCollect_count() + "" : item.getCollect_count_true() + "")
                    .setText(R.id.tv_dianzan_count, item.getIs_good() == 0 ? item.getGood_count() + "" : item.getGood_count_true() + "");
            helper.getView(R.id.iv_wengao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchMusicActivity2.this, WenGaoActivity.class);
                    intent.putExtra("id", item.getId() + "");
                    intent.putExtra("type", "softmusicdetail");
                    startActivity(intent);
                }
            });
            if (helper.getAdapterPosition() <= 8) {
                helper.setText(R.id.tv_order, "0" + (helper.getAdapterPosition() + 1));
            } else {
                helper.setText(R.id.tv_order, "" + (helper.getAdapterPosition() + 1));
            }
        }
    }

    private class SearchAdapter extends BaseQuickAdapter<SearchMusicBean2.DataEntity, BaseViewHolder> {

        public SearchAdapter(@LayoutRes int layoutResId, @Nullable List<SearchMusicBean2.DataEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SearchMusicBean2.DataEntity item) {
            helper.setText(R.id.tv_content, item.getName());
        }
    }

    private void doSearch(final String content) {
        HttpParams params = new HttpParams();
        params.put("keyword", content);
        params.put("userid", SpUtils.getString(this, "id", ""));
        params.put("type", "xk");
        params.put("id", getIntent().getStringExtra("id"));
        OkGo.<SearchMusicBean2>post(MyContants.LXKURL + "search-video")
                .tag(this)
                .params(params)
                .execute(new JsonCallback<SearchMusicBean2>(SearchMusicBean2.class) {
                    @Override
                    public void onSuccess(Response<SearchMusicBean2> response) {
                        int code = response.code();
                        if (response.code() >= 200 && response.code() <= 204) {
                            Logger.e(code + "");
                            SearchMusicBean2 searchMusicBean = response.body();
                            mData = searchMusicBean.getData();
                            if (mData != null && mData.size() > 0) {
                                recycler_search.setVisibility(View.VISIBLE);
                                mSearchAdapter = new SearchAdapter(R.layout.item_search, mData);
                                recycler_search.setAdapter(mSearchAdapter);
                                mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        int id = mData.get(position).getId();
                                        EventBus.getDefault().postSticky(new EventBean("refresh_xk", id + ""));
                                        finish();
                                    }
                                });
                            } else {
                                Toast.makeText(SearchMusicActivity2.this, "抱歉，没有该课程~", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SearchMusicActivity2.this, "抱歉，没有该课程~", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initData() {
        recycler_lishi.setLayoutManager(new LinearLayoutManager(this));
        recycler_lishi.setNestedScrollingEnabled(false);
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
