package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/27.
 */

public class DemoBean implements Serializable {

    /**
     * id : 5
     * created_at : 02:53
     * title : 女高音，一个神秘又充满魅力的声音3
     * content : 年前中国互联网网民成长速度在50%以上，到今天其实已经到了6%左右。事实上，过去的四年，中国互联网网民
     */

    private int id;
    private String created_at;
    private String title;
    private String content;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
