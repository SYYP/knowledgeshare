package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/24.
 */

public class LearnTimeBean  implements Serializable{

    /**
     * hour : 14:43
     * name : 女高音，一个神秘又充满魅力的声音1
     * id : 1
     * time : 2017-12-27
     */
    private String date;
    private String content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LearnTimeBean(String date, String content) {
        this.date = date;
        this.content = content;
    }
}
