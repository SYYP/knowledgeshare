package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/25.
 */

public class MusicTypeBean implements Serializable{
    private String type;
    private String t_name;
    private String t_head;
    private String video_name;
    private String id;
    private String teacher_id;
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

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
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

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public MusicTypeBean(String type, String t_name, String t_head, String video_name, String id, String teacher_id, boolean isCollected) {
        this.type = type;
        this.t_name = t_name;
        this.t_head = t_head;
        this.video_name = video_name;
        this.id = id;
        this.teacher_id = teacher_id;
        this.isCollected = isCollected;
    }
}
