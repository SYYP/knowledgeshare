package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/5.
 */

public class AboutUsBean extends BaseBean implements Serializable {

    /**
     * h5_url : http://thinks.iask.in/about.html
     */

    private String h5_url;

    public String getH5_url() {
        return h5_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }
}
