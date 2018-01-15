package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

/**
 * Created by Administrator on 2018/1/12.
 */

public class WXPayBean {

    /**
     * appid : wxf33afce9142929dc
     * sign : 0A36B67AE3D57C11FF471CC886284D08
     * package : Sign=WXPay
     * partnerid : 1496250722
     * prepayid : wx201801111805167d541a13610233074315
     * noncestr : brkd2wib7rjhkcekiu0392mdp02tcp1n
     * timestamp : 1515665117
     */
    private String appid;
    private String sign;
    private String package1;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private int timestamp;

    public String getPackage1() {
        return package1;
    }

    public void setPackage1(String package1) {
        this.package1 = package1;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppid() {
        return appid;
    }

    public String getSign() {
        return sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }
}
