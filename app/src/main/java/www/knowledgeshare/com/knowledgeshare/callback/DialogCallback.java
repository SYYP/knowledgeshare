package www.knowledgeshare.com.knowledgeshare.callback;

import android.app.Activity;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;
import www.knowledgeshare.com.knowledgeshare.MyApplication;
import www.knowledgeshare.com.knowledgeshare.R;
import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;
import www.knowledgeshare.com.knowledgeshare.login.LoginActivity;
import www.knowledgeshare.com.knowledgeshare.utils.SpUtils;

/**
 * Created by Administrator on 2017/10/11.
 */

public abstract class DialogCallback<T> extends JsonCallback<T> {

    private LoadingDialog dialog;
    private Class<T> clazz;
    private Type type;
    private int code;


    private void initDialog(Activity activity) {
        dialog = new LoadingDialog(activity, R.style.dialog);
    }

    public DialogCallback(Class<T> clazz) {
        super();
        this.clazz = clazz;
        initDialog(activity);
    }

    public DialogCallback(Activity activity, Type type) {
        super();
        this.type = type;
        this.activity = activity;
        initDialog(activity);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        /*request.headers("header1", "HeaderValue1")//
                .params("params1", "ParamsValue1")//
                .params("token", "3215sdf13ad1f65asd4f3ads1f");*/
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        code = response.code();
        T data = null;
        if (body == null)
            return null;

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        if (type != null)
            data = gson.fromJson(jsonReader, type);
        if (clazz != null)
            data = gson.fromJson(jsonReader, clazz);
        return data;
    }


    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            if (code == 410) {
                BaseActivity.removeAllActivitys();
                Intent intent = new Intent(MyApplication.getGloableContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("lxk", "lxk");
                SpUtils.putBoolean(MyApplication.getGloableContext(), "abool", false);
                SpUtils.putString(MyApplication.getGloableContext(), "id", "");
                SpUtils.putBoolean(MyApplication.getGloableContext(), "wengaowindow", false);
                SpUtils.putString(MyApplication.getGloableContext(), "token", "");
                MyApplication.getGloableContext().startActivity(intent);
            }
        }
    }
}