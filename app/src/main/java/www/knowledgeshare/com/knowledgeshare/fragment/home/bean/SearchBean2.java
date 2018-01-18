package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

/**
 * Created by Administrator on 2018/1/18.
 */

public class SearchBean2 {
    private String name;
    private String type;
    private String id;

    public SearchBean2(String name, String type, String id) {
        this.name = name;
        this.type = type;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
