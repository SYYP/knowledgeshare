package www.knowledgeshare.com.knowledgeshare.bean;

/**
 * Created by Administrator on 2018/4/17.
 */

public class NoNetWenGaoBean {
    private String img;
    private String id;
    private String title;
    private String childId;
    private String type;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public NoNetWenGaoBean(String img, String id, String title, String childId, String type) {
        this.img = img;
        this.id = id;
        this.title = title;
        this.childId = childId;
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
