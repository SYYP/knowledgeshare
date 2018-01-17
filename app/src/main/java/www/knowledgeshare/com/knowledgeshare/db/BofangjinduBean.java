package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/1/17.
 */

public class BofangjinduBean extends DataSupport {
    private String mid;
    private double jindu;

    public BofangjinduBean(String mid, double jindu){
        this.mid = mid;
        this.jindu = jindu;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public double getJindu() {
        return jindu;
    }

    public void setJindu(double jindu) {
        this.jindu = jindu;
    }
}
