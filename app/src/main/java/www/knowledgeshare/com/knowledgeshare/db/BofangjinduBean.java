package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/1/17.
 */

public class BofangjinduBean extends DataSupport {
    private String myId;
    private double jindu;

    public BofangjinduBean(String mid, double jindu){
        this.myId = mid;
        this.jindu = jindu;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public double getJindu() {
        return jindu;
    }

    public void setJindu(double jindu) {
        this.jindu = jindu;
    }
}
