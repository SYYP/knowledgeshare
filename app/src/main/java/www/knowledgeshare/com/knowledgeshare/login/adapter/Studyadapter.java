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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.login.bean.Studybean;
import www.knowledgeshare.com.knowledgeshare.utils.SoftKeyboardTool;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Studyadapter extends RecyclerView.Adapter<Studyadapter.MyViewholder> {
    private Context context;
    List<Studybean> list = new ArrayList<>();
    boolean bool;

    public Studyadapter(Context context) {
        this.context = context;
        study();
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewholder myViewholder = new MyViewholder(LayoutInflater.from(context).inflate(R.layout.recyview_studyitem, parent, false));
        return myViewholder;
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, final int position) {

        holder.item_title.setText(list.get(position).getTitle());
        holder.item_count.setText(list.get(position).getCount());

          /*
             设置监听
           */
        //删除
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.item_imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        //编辑
        holder.item_imgcompile.setOnClickListener(new View.OnClickListener() {
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
        private final ImageView item_imgcompile;
        private final ImageView item_imgdelete;

        public MyViewholder(View rootView) {
            super(rootView);
            item_title = (TextView) rootView.findViewById(R.id.item_title);
            item_delete = (TextView) rootView.findViewById(R.id.item_delete);
            item_compile = (TextView) rootView.findViewById(R.id.item_compile);
            item_count = (EditText) rootView.findViewById(R.id.item_count);
            study_liner = rootView.findViewById(R.id.study_liner);
            item_imgcompile = rootView.findViewById(R.id.item_imgcompile);
            item_imgdelete = rootView.findViewById(R.id.item_img_delete);
        }
    }

    public void study() {
        Studybean studybean = new Studybean();
        studybean.setTitle("我只喜欢你");
        studybean.setCount("我只喜欢你，我不敢去爱你，喜欢你我可以偷偷地欣赏，远远的阁着玻璃和你打招呼，把你侃得躺在地上起不来只喜欢你，我不敢去爱你，喜欢你我会感激你对我的帮助，喜欢你我对你的短信会发出会心的一笑，喜欢你我可以听你讲你心中的秘密。");
        list.add(studybean);
        Studybean studybean1 = new Studybean();
        studybean1.setTitle("难过是你走了走了走了");
        studybean1.setCount("我想象着你的样子失眠 所以我梦不到你的脸.你是个不错的男生 我是个不错的女生吧 我们都算是不错的人 可不错就只是不错 而不是对的人 或许 我遇见你是对的 你遇见我是错的 所以你爱过我 所以你爱过我之后只留下我。");
        list.add(studybean1);
        Studybean studybean2 = new Studybean();
        studybean2.setTitle("我不后悔暗恋你那么久");
        studybean2.setCount("自从分开后 我觉得单身挺好的 想吃什么就吃什么 想干什么就干什么 走到哪里 也不需要再跟人汇报了 ");
        list.add(studybean2);
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
