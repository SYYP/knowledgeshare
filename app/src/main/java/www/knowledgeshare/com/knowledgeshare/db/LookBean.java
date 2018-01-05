package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/28.
 */

public class LookBean extends DataSupport{
    private int id;
    private String type;
    private String title;
    private String t_name;
    private String t_tag;
    private String price;
    private long time;//用时间戳来控制排列顺序

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_tag() {
        return t_tag;
    }

    public void setT_tag(String t_tag) {
        this.t_tag = t_tag;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LookBean(int id, String type, String title, String t_name, String t_tag, String price,long time) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.t_name = t_name;
        this.t_tag = t_tag;
        this.price = price;
        this.time=time;
    }

    public LookBean(int id, String type, String title, String t_tag, String price,long time) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.t_tag = t_tag;
        this.price = price;
        this.time=time;
    }
}
