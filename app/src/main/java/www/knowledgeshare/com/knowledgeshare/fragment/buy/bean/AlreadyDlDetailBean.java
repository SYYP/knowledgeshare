package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/6.
 */

public class AlreadyDlDetailBean implements Serializable {
    private boolean isChecked = false;
    private String title;
    private String size;
    private String name;
    private String time;
    private boolean isVisibility = false;

    public boolean isVisibility() {
        return isVisibility;
    }

    public void setVisibility(boolean visibility) {
        isVisibility = visibility;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
