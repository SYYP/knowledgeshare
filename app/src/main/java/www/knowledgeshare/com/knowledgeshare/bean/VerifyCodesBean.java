package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/12.
 */

public class VerifyCodesBean implements Serializable {

    /**
     * message : 获取成功
     */

    private String message;
    private String verify;

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
