package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.view.CircleImageView;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Myclassadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Collectbean> list=new ArrayList<>();
    final static int ONE = 0, TWO = 1, THREE = 2;

    public Myclassadapter(Context context) {
        this.context = context;

        data();
    }

    private void data() {

          Collectbean coll=new Collectbean();
        coll.setTitle("知道我在等你吗");
        list.add(coll);
        Collectbean coll1=new Collectbean();
        coll1.setTitle("其实我很喜欢");
        list.add(coll1);
        Collectbean coll2=new Collectbean();
        coll2.setTitle("知道我在等你吗升水");
        list.add(coll2);
        Collectbean coll3=new Collectbean();
        coll3.setTitle("知道我在等你吗升水");
        list.add(coll3);
        Collectbean coll4=new Collectbean();
        coll4.setTitle("好可惜真的不能");
        list.add(coll4);
        Collectbean coll5=new Collectbean();
        coll5.setTitle("好可惜真的不能在一起");
        list.add(coll5);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        /*
           多条目类型
         */
        switch (viewType) {
            case ONE:
                view = LayoutInflater.from(context).inflate(R.layout.fragment_class_one, parent, false);
                viewHolder = new Myviewholder1(view);
                break;
            case TWO:
                view = LayoutInflater.from(context).inflate(R.layout.fragment_class_two, parent, false);
                viewHolder = new Myviewholder2(view);
                break;
            case THREE:
                view = LayoutInflater.from(context).inflate(R.layout.fragment_class_three, parent, false);
                viewHolder = new Myviewholder3(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {

            case  ONE:
          Myviewholder1 myviewhiodler1= (Myviewholder1) holder;
                myviewhiodler1.class_title.setText(list.get(position).getTitle());
                break;
            case TWO:
                Myviewholder2 myviewhiodler2= (Myviewholder2) holder;
                myviewhiodler2.class_titles.setText(list.get(position).getTitle());
                break;
            case  THREE:
                Myviewholder3 myviewhiodler3= (Myviewholder3) holder;
                myviewhiodler3.class_titles.setText(list.get(position).getTitle());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ONE;
        } else if (position == 1 || position == 4 || position == 5) {
            return TWO;
        } else {
            return THREE;
        }
    }

      /*
        多条目添加布局
       */

    public class Myviewholder1 extends RecyclerView.ViewHolder {
        public ImageView class_bo;
        public TextView class_title;
        public TextView class_time;
        public ImageView class_xinxin;

        public Myviewholder1(View rootView) {
            super(rootView);
            this.class_bo = (ImageView) rootView.findViewById(R.id.class_bo);
            this.class_title = (TextView) rootView.findViewById(R.id.class_title);
            this.class_time = (TextView) rootView.findViewById(R.id.class_time);
            this.class_xinxin = (ImageView) rootView.findViewById(R.id.class_xinxin);
        }
    }

    public class Myviewholder2 extends RecyclerView.ViewHolder {
        public View rootView;
        public View lines;
        public TextView class_titles;
        public TextView class_count;
        public CircleImageView class_pho;
        public TextView class_name;
        public TextView class_date;

        public Myviewholder2(View rootView) {
            super(rootView);
            this.lines = (View) rootView.findViewById(R.id.lines);
            this.class_titles = (TextView) rootView.findViewById(R.id.class_titles);
            this.class_count = (TextView) rootView.findViewById(R.id.class_count);
            this.class_pho = (CircleImageView) rootView.findViewById(R.id.class_pho);
            this.class_name = (TextView) rootView.findViewById(R.id.class_name);
            this.class_date = (TextView) rootView.findViewById(R.id.class_date);
        }
    }

    public class Myviewholder3 extends RecyclerView.ViewHolder {
        public View lines;
        public TextView class_titles;
        public ImageView class_img;
        public TextView class_date;
        public TextView class_dianxin;
        public TextView class_read;
        public Myviewholder3(View rootView) {
            super(rootView);
            this.lines = (View) rootView.findViewById(R.id.lines);
            this.class_titles = (TextView) rootView.findViewById(R.id.class_titles);
            this.class_img = (ImageView) rootView.findViewById(R.id.class_img);
            this.class_date = (TextView) rootView.findViewById(R.id.class_date);
            this.class_dianxin = (TextView) rootView.findViewById(R.id.class_dianxin);
            this.class_read = (TextView) rootView.findViewById(R.id.class_read);
        }
    }

}
