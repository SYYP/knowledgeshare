package www.knowledgeshare.com.knowledgeshare.login.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.NoteListBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.login.bean.Studybean;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SoftKeyboardTool;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Studyadapter extends RecyclerView.Adapter<Studyadapter.MyViewholder> {
    private Context context;
    List<NoteListBean.NoteBean> list = new ArrayList<>();
    boolean bool;

    public Studyadapter(Context context, List<NoteListBean.NoteBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewholder myViewholder = new MyViewholder(LayoutInflater.from(context).inflate(R.layout.recyview_studyitem, parent, false));
        return myViewholder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, final int position) {

        int lastId = list.get(position).getId();
        holder.item_title.setText(list.get(position).getTitle());
        holder.item_count.setText(list.get(position).getContent());

        //删除
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestDelNote(list.get(position).getId(),position);
            }
        });

        //编辑
        holder.item_compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bool) {
                    holder.item_count.setFocusableInTouchMode(false);
                    holder.item_count.setFocusable(false);
                    Drawable drawable= context.getResources().getDrawable(R.drawable.study_bianji);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    holder.item_compile.setCompoundDrawables(drawable,null,null,null);
                    holder.item_compile.setText("编辑");
                    //关闭键盘
                    SoftKeyboardTool.closeKeyboard(holder.item_count);
                    MyApplication.stopClearClip();
                    requesteditNote(list.get(position).getId(),holder.item_count.getText().toString());
                } else {
                    holder.item_count.setFocusableInTouchMode(true);
                    holder.item_count.setFocusable(true);
                    Drawable drawable= context.getResources().getDrawable(R.drawable.study_finish);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    holder.item_compile.setCompoundDrawables(drawable,null,null,null);
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

    private void requestDelNote(int id, final int position) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(context, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id",id);
        OkGo.<BaseBean>post(MyContants.delNote)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            TUtils.showShort(context,response.body().getMessage());
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });
    }

    private void requesteditNote(int id, String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(context, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id",id+"");
        params.put("content",content);
        OkGo.<BaseBean>post(MyContants.editNote)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204){
                            TUtils.showShort(context,response.body().getMessage());
                        }else {
                            TUtils.showShort(context,response.body().getMessage());
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        public TextView item_title;
        public TextView item_delete;
        public TextView item_compile;
        public EditText item_count;
        public LinearLayout study_liner;

        public MyViewholder(View rootView) {
            super(rootView);
            item_title = (TextView) rootView.findViewById(R.id.item_title);
            item_delete = (TextView) rootView.findViewById(R.id.item_delete);
            item_compile = (TextView) rootView.findViewById(R.id.item_compile);
            item_count = (EditText) rootView.findViewById(R.id.item_count);
            study_liner = rootView.findViewById(R.id.study_liner);
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
