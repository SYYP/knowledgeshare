package www.knowledgeshare.com.knowledgeshare.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.callback.DialogCallback;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.LearnTimeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.home.bean.DianZanbean;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.DemoBean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SoftKeyboardTool;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.view.FullyLinearLayoutManager;
import www.knowledgeshare.com.knowledgeshare.view.MyFooter;
import www.knowledgeshare.com.knowledgeshare.view.MyHeader;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class StudyRecordActivity extends BaseActivity {

    private ImageView iv_back;
    private RecyclerView study_record;
    private String after = "";
    private SpringView springview;
    private boolean isLoadMore;
    private List<LearnTimeBean> mdata = new ArrayList<>();
    private Recordadapter mRecordadapter;
    private Recordadapter.DetailAdapter mDetailAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();
        requestNotes();
        initListener();
    }

    private void initListener() {
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                after="";
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestNotes();
                        springview.onFinishFreshAndLoad();
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {
                isLoadMore = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestNotes();
                        springview.onFinishFreshAndLoad();
                    }
                }, 2000);
            }
        });
        springview.setHeader(new MyHeader(this));
        springview.setFooter(new MyFooter(this));
    }

    private void requestNotes() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("after", after);
        OkGo.<String>get(MyContants.notes)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Logger.e(body);
                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            JSONObject data = jsonObject.getJSONObject("data");
                            Iterator keys = data.keys();
                            List<LearnTimeBean> beanList = new ArrayList<LearnTimeBean>();
                            while (keys.hasNext()) {
                                String key = String.valueOf(keys.next());
                                Logger.e(key);
                                JSONArray timeData = data.getJSONArray(key);
                                Logger.e(timeData.toString());
                                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(timeData.toString())) {
                                    LearnTimeBean bean = new LearnTimeBean(key, timeData.toString());
                                    beanList.add(bean);
                                }
                            }
                            if (isLoadMore) {
                                if (beanList == null || beanList.size() == 0) {
                                    Toast.makeText(StudyRecordActivity.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                mdata.addAll(beanList);
                                mRecordadapter.notifyDataSetChanged();
                            } else {
                                mdata = beanList;
                                mRecordadapter = new Recordadapter(R.layout.item_record, mdata);
                                study_record.setAdapter(mRecordadapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private class Recordadapter extends BaseQuickAdapter<LearnTimeBean, BaseViewHolder> {
        boolean bool;
        private List<DemoBean> mBeanList;

        public Recordadapter(@LayoutRes int layoutResId, @Nullable List<LearnTimeBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LearnTimeBean item) {
            TextView record_time = (TextView) helper.getView(R.id.record_time);
            RecyclerView recycler_list = helper.getView(R.id.recycler_list);
            record_time.setText(item.getDate());
            recycler_list.setLayoutManager(new FullyLinearLayoutManager(mContext));
            recycler_list.setNestedScrollingEnabled(false);
            mBeanList = JSON.parseArray(item.getContent(), DemoBean.class);
            mDetailAdapter = new DetailAdapter(R.layout.item_record2, mBeanList);
            recycler_list.setAdapter(mDetailAdapter);
        }

        private class DetailAdapter extends BaseQuickAdapter<DemoBean, BaseViewHolder> {

            public DetailAdapter(@LayoutRes int layoutResId, @Nullable List<DemoBean> data) {
                super(layoutResId, data);
            }

            @Override
            protected void convert(final BaseViewHolder helper, final DemoBean item) {
                after = item.getId() + "";
                helper.setText(R.id.item_title, item.getCreated_at() + "   " + item.getTitle())
                        .setText(R.id.item_count, item.getContent());
                //删除
                TextView item_delete=helper.getView(R.id.item_delete);
                ImageView item_img_delete=helper.getView(R.id.item_img_delete);
                item_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deletenote(item.getId() + "");
                    }
                });
                item_img_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deletenote(item.getId() + "");
                    }
                });
                final EditText item_count = helper.getView(R.id.item_count);
                final ImageView item_imgcompile = helper.getView(R.id.item_imgcompile);
                final TextView item_compile = helper.getView(R.id.item_compile);
                item_compile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bool) {
                            item_count.setFocusableInTouchMode(false);
                            item_count.setFocusable(false);
                            item_imgcompile.setImageResource(R.drawable.study_bianji);
                            item_compile.setText("编辑");
                            //关闭键盘
                            SoftKeyboardTool.closeKeyboard(item_count);
                            MyApplication.stopClearClip();
                        } else {
                            item_count.setFocusableInTouchMode(true);
                            item_count.setFocusable(true);
                            item_imgcompile.setImageResource(R.drawable.study_finish);
                            item_compile.setText("完成");
                            MyApplication.startClearClip(StudyRecordActivity.this);
                            item_count.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    setInsertionDisabled(item_count);
                                    return false;
                                }
                            });
                            item_count.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    editNote(item.getId() + "", item_count.getText().toString());
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                        }
                        bool = !bool;
                        item_count.setTextIsSelectable(false);
                        item_count.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                            @Override
                            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                                return false;
                            }

                            @Override
                            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                                return false;
                            }

                            @Override
                            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                                return false;
                            }

                            @Override
                            public void onDestroyActionMode(ActionMode actionMode) {

                            }
                        });
                    }
                });
                item_imgcompile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bool) {
                            item_count.setFocusableInTouchMode(false);
                            item_count.setFocusable(false);
                            item_imgcompile.setImageResource(R.drawable.study_bianji);
                            item_compile.setText("编辑");
                            //关闭键盘
                            SoftKeyboardTool.closeKeyboard(item_count);
                            MyApplication.stopClearClip();
                        } else {
                            item_count.setFocusableInTouchMode(true);
                            item_count.setFocusable(true);
                            item_imgcompile.setImageResource(R.drawable.study_finish);
                            item_compile.setText("完成");
                            MyApplication.startClearClip(StudyRecordActivity.this);
                            item_count.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    setInsertionDisabled(item_count);
                                    return false;
                                }
                            });
                            item_count.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    editNote(item.getId() + "", item_count.getText().toString());
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                        }
                        bool = !bool;
                        item_count.setTextIsSelectable(false);
                        item_count.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                            @Override
                            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                                return false;
                            }

                            @Override
                            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                                return false;
                            }

                            @Override
                            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                                return false;
                            }

                            @Override
                            public void onDestroyActionMode(ActionMode actionMode) {

                            }
                        });
                    }
                });
            }
        }

        /*
         通过反射禁止弹出粘贴框
        */
        private void setInsertionDisabled(EditText editText) {
            try {
                Field editorField = TextView.class.getDeclaredField("mEditor");
                editorField.setAccessible(true);
                Object editorObject = editorField.get(editText);

                // if this view supports insertion handles
                Class editorClass = Class.forName("android.widget.Editor");
                Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
                mInsertionControllerEnabledField.setAccessible(true);
                mInsertionControllerEnabledField.set(editorObject, false);

                // if this view supports selection handles
                Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
                mSelectionControllerEnabledField.setAccessible(true);
                mSelectionControllerEnabledField.set(editorObject, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void editNote(String id, String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(this, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("content", content);
        OkGo.<DianZanbean>post(MyContants.LXKURL + "note/edit")
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new DialogCallback<DianZanbean>(StudyRecordActivity.this,DianZanbean.class) {
                             @Override
                             public void onSuccess(Response<DianZanbean> response) {
                                 int code = response.code();
                                 DianZanbean dianZanbean = response.body();
                                 Toast.makeText(StudyRecordActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                 //                                 isLoadMore = false;
                                 //                                 requestNotes();
                             }
                         }
                );
    }

    private void deletenote(String id) {
        showTips(2,"提示","确认要删除吗？","确定","取消",false,id);
    }

    private void showTips(final int flag, String title, String content, String left, String right, boolean singleMode, final String id) {
        new NormalAlertDialog.Builder(this)
                .setTitleVisible(true).setTitleText(title)
                .setTitleTextColor(R.color.text_black)
                .setContentText(content)
                .setContentTextColor(R.color.text_black)
                .setLeftButtonText(left)
                .setLeftButtonTextColor(R.color.text_black)
                .setRightButtonText(right)
                .setRightButtonTextColor(R.color.text_black)
                .setSingleMode(singleMode)
                .setSingleButtonText("确定")
                .setSingleButtonTextColor(R.color.text_black)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        switch (flag){
                            case 2:
                                HttpHeaders headers = new HttpHeaders();
                                headers.put("Authorization", "Bearer " + SpUtils.getString(StudyRecordActivity.this, "token", ""));
                                HttpParams params = new HttpParams();
                                params.put("id", id);
                                OkGo.<DianZanbean>post(MyContants.LXKURL + "note/del")
                                        .tag(this)
                                        .headers(headers)
                                        .params(params)
                                        .execute(new JsonCallback<DianZanbean>(DianZanbean.class) {
                                                     @Override
                                                     public void onSuccess(Response<DianZanbean> response) {
                                                         int code = response.code();
                                                         DianZanbean dianZanbean = response.body();
                                                         Toast.makeText(StudyRecordActivity.this, dianZanbean.getMessage(), Toast.LENGTH_SHORT).show();
                                                         isLoadMore = false;
                                                         after="";
                                                         requestNotes();
                                                     }
                                                 }
                                        );
                                break;
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {
                        switch (flag){
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }


    private void initView() {
        springview = (SpringView) findViewById(R.id.springview);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        study_record = (RecyclerView) findViewById(R.id.study_record);
        study_record.setLayoutManager(new LinearLayoutManager(StudyRecordActivity.this));
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
