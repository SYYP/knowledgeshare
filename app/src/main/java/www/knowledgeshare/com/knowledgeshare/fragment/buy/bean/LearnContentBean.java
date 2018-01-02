package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/24.
 */

public class LearnContentBean implements Serializable {

    /**
     * hour : 14:43
     * name : 女高音，一个神秘又充满魅力的声音1
     * id : 1
     * time : 2017-12-27
     */
    private String hour;
    private String name;
    private int id;
    private String time;

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHour() {
        return hour;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
