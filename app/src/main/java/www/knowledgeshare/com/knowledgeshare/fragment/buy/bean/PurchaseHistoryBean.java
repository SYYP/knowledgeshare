package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PurchaseHistoryBean implements Serializable {
    private String bianhao;
    private String date;
    private String name;
    private String money;

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
