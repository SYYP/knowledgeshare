package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BrowserHistoryBean implements Serializable {
    private String title;
    private String name;
    private String content;
    private String money;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
