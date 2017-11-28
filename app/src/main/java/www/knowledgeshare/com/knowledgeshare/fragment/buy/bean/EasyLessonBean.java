package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/23.
 */

public class EasyLessonBean implements Serializable {
    private String title;
    private String name;
    private String desc;
    private String ybf;
    private String gmrs;
    private String zlmc;
    private String updata;
    private String money;
    private String wendang;
    private String huancun;

    public String getWendang() {
        return wendang;
    }

    public void setWendang(String wendang) {
        this.wendang = wendang;
    }

    public String getHuancun() {
        return huancun;
    }

    public void setHuancun(String huancun) {
        this.huancun = huancun;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getZlmc() {
        return zlmc;
    }

    public void setZlmc(String zlmc) {
        this.zlmc = zlmc;
    }

    public String getUpdata() {
        return updata;
    }

    public void setUpdata(String updata) {
        this.updata = updata;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getYbf() {
        return ybf;
    }

    public void setYbf(String ybf) {
        this.ybf = ybf;
    }

    public String getGmrs() {
        return gmrs;
    }

    public void setGmrs(String gmrs) {
        this.gmrs = gmrs;
    }
}
