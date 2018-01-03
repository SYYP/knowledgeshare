package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/1/3.
 */

public class StudyTimeBean extends DataSupport{
    private int id;
    private String type;
    private String date;
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public StudyTimeBean(int id, String type, String date, long time) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.time = time;
    }
}
