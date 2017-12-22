package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/13.
 */

public class BaseBean implements Serializable {
    private String message;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
