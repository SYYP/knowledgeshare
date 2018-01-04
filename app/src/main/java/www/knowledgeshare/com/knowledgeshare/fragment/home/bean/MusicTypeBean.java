package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/25.
 */

public class MusicTypeBean implements Serializable {
    private String type;
    private String t_head;
    private String video_name;
    private String id;
    private boolean isCollected;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getT_head() {
        return t_head;
    }

    public void setT_head(String t_head) {
        this.t_head = t_head;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public MusicTypeBean(String type, String t_head, String video_name, String id, boolean isCollected) {
        this.type = type;
        this.t_head = t_head;
        this.video_name = video_name;
        this.id = id;
        this.isCollected = isCollected;
    }
}
