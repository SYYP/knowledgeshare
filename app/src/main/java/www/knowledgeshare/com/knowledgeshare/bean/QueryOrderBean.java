package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class QueryOrderBean extends BaseBean implements Serializable {

    /**
     * order_sn : 2017121909551111464
     * level_discount : 0
     * discounts : 0
     * order_amount : 180
     * data : [{"id":7,"xk_name":"如何成为一个合格的歌唱者5","xk_teacher_tags":"好声音","xk_price":"￥180/年","url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png"}]
     */

    private String order_sn;
    private int level_discount;
    private String discounts;
    private String order_amount;
    private List<DataBean> data;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getLevel_discount() {
        return level_discount;
    }

    public void setLevel_discount(int level_discount) {
        this.level_discount = level_discount;
    }

    public String getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String discounts) {
        this.discounts = discounts;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 7
         * xk_name : 如何成为一个合格的歌唱者5
         * xk_teacher_tags : 好声音
         * xk_price : ￥180/年
         * url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png
         */

        private int id;
        private String xk_name;
        private String xk_teacher_tags;
        private String xk_price;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getXk_name() {
            return xk_name;
        }

        public void setXk_name(String xk_name) {
            this.xk_name = xk_name;
        }

        public String getXk_teacher_tags() {
            return xk_teacher_tags;
        }

        public void setXk_teacher_tags(String xk_teacher_tags) {
            this.xk_teacher_tags = xk_teacher_tags;
        }

        public String getXk_price() {
            return xk_price;
        }

        public void setXk_price(String xk_price) {
            this.xk_price = xk_price;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
