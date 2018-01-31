package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;
import www.knowledgeshare.com.knowledgeshare.bean.EventBean;
import www.knowledgeshare.com.knowledgeshare.bean.GoldBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;
import www.knowledgeshare.com.knowledgeshare.utils.TUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Myclassjinjuadapter extends RecyclerView.Adapter<Myclassjinjuadapter.Myviewholder> {
    private Context context;
    private List<GoldBean.DataBean> list = new ArrayList<>();
    boolean bool;

    public Myclassjinjuadapter(Context context,List<GoldBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jinju, parent, false);
        Myviewholder myviewholder = new Myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(final Myviewholder holder, final int position) {
      holder.jin_date.setText(list.get(position).getDisplay_at()+" "+list.get(position).getDay());
        holder.study_count.setText(list.get(position).getContent());
        holder.jin_xinxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bool) {
                    showTips(holder,position);
                } else {
                    holder.jin_xinxin.setImageResource(R.drawable.xinxin);
                }
                bool = !bool;
            }
        });
    }

    private void showTips(final Myviewholder holder, final int positon) {
        new NormalAlertDialog.Builder(context)
                .setTitleVisible(true).setTitleText("提示")
                .setTitleTextColor(R.color.text_black)
                .setContentText("是否取消收藏？")
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
                        requestDayNoFav(holder,positon);
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {

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

    private void requestDayNoFav(final Myviewholder holder, final int positon) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Bearer " + SpUtils.getString(context, "token", ""));
        HttpParams params = new HttpParams();
        params.put("id", list.get(positon).getId());

        OkGo.<BaseBean>post(MyContants.dayNoFacorite)
                .tag(this)
                .headers(headers)
                .params(params)
                .execute(new JsonCallback<BaseBean>(BaseBean.class) {
                    @Override
                    public void onSuccess(Response<BaseBean> response) {
                        int code = response.code();
                        if (code >= 200 && code <= 204) {
                            holder.jin_xinxin.setImageResource(R.drawable.weiguanzhuxin);
                            EventBean eventBean = new EventBean("jinju");
                            EventBus.getDefault().postSticky(eventBean);
                        } else {
                            TUtils.showShort(context, response.body().getMessage());
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public View rootView;
        public View line;
        public TextView jin_date;
        public RelativeLayout relative;
        public TextView study_count;
        public LinearLayout study_liner;
        ImageView jin_xinxin;
        public Myviewholder(View rootView) {
            super(rootView);
            this.line = (View) rootView.findViewById(R.id.line);
            this.jin_date = (TextView) rootView.findViewById(R.id.jin_date);
            this.relative = (RelativeLayout) rootView.findViewById(R.id.relative);
            this.study_count = (TextView) rootView.findViewById(R.id.study_count);
            this.study_liner = (LinearLayout) rootView.findViewById(R.id.study_liner);
               jin_xinxin=  rootView.findViewById(R.id.jin_xinxin);
        }
    }

}
