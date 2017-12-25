package www.knowledgeshare.com.knowledgeshare.login.adapter;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import java.lang.reflect.Field;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.login.bean.StudyRecordbean;
import www.knowledgeshare.com.knowledgeshare.utils.SoftKeyboardTool;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Recordadapter extends RecyclerView.Adapter<Recordadapter.Myadapter> {

    private Context context;
    private List<StudyRecordbean> list;
    boolean bool;
    public Recordadapter(Context context, List<StudyRecordbean> list) {
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

           //添加数据
         if(list.get(position).getTime()==null||list.get(position).getTime().equals("")){
            holder.record_time.setVisibility(View.GONE);

         }
        else {
             holder.record_time.setText(list.get(position).getTime());
         }

        holder.item_title.setText(list.get(position).getTitle());
        /*
           设置监听
         */
        //删除
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTips(position);
            }
        });

        /*
          编辑
         */
        holder.item_compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bool) {
                    holder.item_count.setFocusableInTouchMode(false);
                    holder.item_count.setFocusable(false);
                    holder.item_imgcompile.setImageResource(R.drawable.study_bianji);
                    holder.item_compile.setText("编辑");
                    //关闭键盘
                    SoftKeyboardTool.closeKeyboard(holder.item_count);
                    MyApplication.stopClearClip();

                } else {
                    holder.item_count.setFocusableInTouchMode(true);
                    holder.item_count.setFocusable(true);
                    holder.item_imgcompile.setImageResource(R.drawable.study_finish);
                    holder.item_compile.setText("完成");
                    MyApplication.startClearClip(context);
                    holder.item_count.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            setInsertionDisabled( holder.item_count);
                            return false;
                        }
                    });

                }
                bool = !bool;
                holder.item_count.setTextIsSelectable(false);
                holder.item_count.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
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

    private void showTips(final int position) {
        new NormalAlertDialog.Builder(context)
                .setTitleVisible(true).setTitleText("提示")
                .setTitleTextColor(R.color.text_black)
                .setContentText("是否删除笔记？")
                .setContentTextColor(R.color.text_black)
                .setLeftButtonText("是")
                .setLeftButtonTextColor(R.color.text_black)
                .setRightButtonText("否")
                .setRightButtonTextColor(R.color.text_black)
                .setSingleButtonTextColor(R.color.text_black)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        list.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {

                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myadapter extends RecyclerView.ViewHolder {
        public TextView record_time;
        public TextView item_title;
        public EditText item_count;
        public LinearLayout study_liner;
        public TextView item_compile;
        public ImageView item_imgcompile;
        public TextView item_delete;
        public ImageView item_img_delete;
        public Myadapter(View rootView) {
            super(rootView);
            record_time = (TextView) rootView.findViewById(R.id.record_time);
            this.item_title = (TextView) rootView.findViewById(R.id.item_title);
            this.item_count = (EditText) rootView.findViewById(R.id.item_count);
            this.study_liner = (LinearLayout) rootView.findViewById(R.id.study_liner);
            this.item_compile = (TextView) rootView.findViewById(R.id.item_compile);
            this.item_imgcompile = (ImageView) rootView.findViewById(R.id.item_imgcompile);
            this.item_delete = (TextView) rootView.findViewById(R.id.item_delete);
            this.item_img_delete = (ImageView) rootView.findViewById(R.id.item_img_delete);
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
