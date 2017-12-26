package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

/**
 * Created by Administrator on 2017/12/26.
 */

public class HuanChongBean {
    private String msg;
    private int percent;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public HuanChongBean(String msg, int percent) {
        this.msg = msg;
        this.percent = percent;
    }
}
