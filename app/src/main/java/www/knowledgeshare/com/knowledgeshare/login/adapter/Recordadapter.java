package www.knowledgeshare.com.knowledgeshare.login.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.reflect.Field;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.fragment.buy.bean.LearnTimeBean;
import www.knowledgeshare.com.knowledgeshare.fragment.mine.DemoBean;
import www.knowledgeshare.com.knowledgeshare.utils.SoftKeyboardTool;
import www.knowledgeshare.com.knowledgeshare.view.FullyLinearLayoutManager;

import static com.taobao.accs.ACCSManager.mContext;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Recordadapter extends RecyclerView.Adapter<Recordadapter.Myadapter> {

    private Context context;
    private List<LearnTimeBean> list;
    boolean bool;
    private List<DemoBean> mBeanList;

    public Recordadapter(Context context, List<LearnTimeBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Myadapter onCreateViewHolder(ViewGroup parent, int viewType) {
        Myadapter myadapter = new Myadapter(LayoutInflater.from(context).inflate(R.layout.item_record, parent, false));
        return myadapter;
    }

    @Override
    public void onBindViewHolder(final Myadapter holder, final int position) {
        holder.record_time.setText(list.get(position).getDate());
        holder.recycler_list.setLayoutManager(new FullyLinearLayoutManager(mContext));
        holder.recycler_list.setNestedScrollingEnabled(false);
        mBeanList = JSON.parseArray(list.get(position).getContent(), DemoBean.class);
        DetailAdapter adapter = new DetailAdapter(R.layout.item_record2, mBeanList);
        holder.recycler_list.setAdapter(adapter);
    }

    private class DetailAdapter extends BaseQuickAdapter<DemoBean, BaseViewHolder> {

        public DetailAdapter(@LayoutRes int layoutResId, @Nullable List<DemoBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, DemoBean item) {
            helper.setText(R.id.item_title, item.getCreated_at() + "   " + item.getTitle())
                    .setText(R.id.item_count, item.getContent());
            //删除
            helper.getView(R.id.item_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBeanList.remove(helper.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
            final EditText item_count = helper.getView(R.id.item_count);
            final ImageView item_imgcompile = helper.getView(R.id.item_imgcompile);
            final TextView item_compile = helper.getView(R.id.item_compile);
            /*
            编辑
            */
            helper.getView(R.id.item_compile).setOnClickListener(new View.OnClickListener() {
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
                        MyApplication.startClearClip(context);
                        item_count.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                setInsertionDisabled(item_count);
                                return false;
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myadapter extends RecyclerView.ViewHolder {
        public TextView record_time;
        public RecyclerView recycler_list;

        public Myadapter(View rootView) {
            super(rootView);
            record_time = (TextView) rootView.findViewById(R.id.record_time);
            recycler_list = rootView.findViewById(R.id.recycler_list);
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
