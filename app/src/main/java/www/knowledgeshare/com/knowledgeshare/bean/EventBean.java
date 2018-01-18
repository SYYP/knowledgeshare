package www.knowledgeshare.com.knowledgeshare.bean;

/**
 * Created by Administrator on 2017/11/23.
 */

public class EventBean {
    private String msg;
    private String msg2;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public EventBean(String msg) {
        this.msg = msg;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    public EventBean(String msg, String msg2) {
        this.msg = msg;
        this.msg2 = msg2;
    }
}
