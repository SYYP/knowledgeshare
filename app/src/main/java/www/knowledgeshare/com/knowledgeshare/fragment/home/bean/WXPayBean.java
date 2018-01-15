package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private int timestamp;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setPackage(String package1) {
        this.packageX=package1;
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

    public String getPackage() {
        return packageX;
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
