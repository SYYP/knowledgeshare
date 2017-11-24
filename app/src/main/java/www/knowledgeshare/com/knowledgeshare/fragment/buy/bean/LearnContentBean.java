package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/24.
 */

public class LearnContentBean implements Serializable {
    private String time;
    private String content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
