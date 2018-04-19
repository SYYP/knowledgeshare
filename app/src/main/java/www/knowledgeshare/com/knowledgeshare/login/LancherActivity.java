package www.knowledgeshare.com.knowledgeshare.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.activity.MainActivity;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.bean.GuidePageBean;
import www.knowledgeshare.com.knowledgeshare.callback.JsonCallback;
import www.knowledgeshare.com.knowledgeshare.login.bean.HobbyActivity;
import www.knowledgeshare.com.knowledgeshare.utils.MyContants;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * Created by Administrator on 2017/8/24.
 */

public class LancherActivity extends BaseActivity {

    private int time = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                boolean isguide = SpUtils.getBoolean(LancherActivity.this, "guide", false);
                if (isguide) {
                    time--;
                } else {
                    requestGuide();
                    return;
                }
                if (time <= 0) {
                    if (isguide) {
                        boolean abool = SpUtils.getBoolean(LancherActivity.this, "abool", false);
                        if (!abool) {
                            Intent intent = new Intent(LancherActivity.this, HobbyActivity.class);
                            intent.putExtra("flag", "0");
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LancherActivity.this, MainActivity.class);
                            intent.putExtra("homewindow", true);
                            startActivity(intent);
                        }
                        finish();
                    }
                } else {
                    Message message = mHandler.obtainMessage(1);
                    mHandler.sendMessageDelayed(message, 1000);
                }
            }
        }
    };
    private List<GuidePageBean.GuideEntity> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        UltimateBar ultimateBar = new UltimateBar(this);
        //        ultimateBar.setImmersionBar();
        setISshow(false);
        setContentView(R.layout.activity_launcher);
        mHandler.sendEmptyMessage(1);
    }

    private void requestGuide() {
        HttpParams params = new HttpParams();
        params.put("type", "true");
        params.put("from", "android");
        OkGo.<GuidePageBean>get(MyContants.bootstrappers)
                .tag(this)
                .params(params)
                .execute(new JsonCallback<GuidePageBean>(GuidePageBean.class) {
                    @Override
                    public void onSuccess(Response<GuidePageBean> response) {
                        GuidePageBean guidePageBean = response.body();
                        if (response.code() >= 200 && response.code() <= 204) {
                            list = guidePageBean.getGuide();
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    downLoadPhoto(list.get(i).getImgurl(), "guide" + i);
                                }
                            }
                        }
                    }
                });
    }

    private int position;
    private List<String> imgList = new ArrayList<>();

    private void downLoadPhoto(String url, final String imgname) {
        OkGo.<File>get(url)
                .tag(this)
                .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/", imgname) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        //                        Toast.makeText(LancherActivity.this, "下载完成"+imgname, Toast.LENGTH_SHORT).show();
                        imgList.add(Environment.getExternalStorageDirectory().getAbsolutePath() + "/boyue/" + imgname);
                        position++;
                        if (position == list.size()) {
                            Intent intent = new Intent(LancherActivity.this, GuidePageActivity.class);
                            intent.putExtra("data", (Serializable) imgList);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
    }
}
