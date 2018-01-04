package www.knowledgeshare.com.knowledgeshare.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import www.knowledgeshare.com.knowledgeshare.base.BaseActivity;

import static com.tencent.mm.sdk.constants.ConstantsAPI.COMMAND_PAY_BY_WX;


/**
 * Created by Administrator on 2017/1/14.
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;// IWXAPI 是第三方app和微信通信的openapi接口
    private static final String APP_ID = "wx76c60c8c929e5061";
    private String mPayprice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.pay_result);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID);//第二个参数是微信appid
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Toast.makeText(this, "正在请求微信支付", Toast.LENGTH_SHORT).show();
    }

    /**
     * 得到支付结果回调
     */
    @Override
    public void onResp(BaseResp baseResp) {
 /*
        *  0 支付成功
        * -1 发生错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
        * -2 用户取消 发生场景：用户不支付了，点击取消，返回APP。
        * */
        //        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);// 支付结果码
        if (baseResp.getType() == COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                Toast.makeText(this, "微信支付成功", Toast.LENGTH_SHORT).show();
            }
            if (baseResp.errCode == -1) {
                Toast.makeText(this, "微信支付失败", Toast.LENGTH_SHORT).show();
            }
            if (baseResp.errCode == -2) {
                Toast.makeText(this, "微信支付取消", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

}
