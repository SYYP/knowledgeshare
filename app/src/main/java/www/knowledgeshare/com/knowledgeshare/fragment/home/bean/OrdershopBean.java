package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public class OrdershopBean implements Serializable {

    /**
     * message : 提交订单成功
     * order_sn : 2018012211535099868
     * level_discount : 0
     * discounts : 0
     * order_amount : 1000
     * data : [{"id":16,"xk_name":"阿萨达是阿萨斯","xk_teacher_tags":"青蛙说得对","xk_price":"￥1000/年","url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20180119/chiron-003.jpg"}]
     */

    private String message;
    private String order_sn;
    private int level_discount;
    private int discounts;
    private int order_amount;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public int getDiscounts() {
        return discounts;
    }

    public void setDiscounts(int discounts) {
        this.discounts = discounts;
    }

    public int getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(int order_amount) {
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
         * id : 16
         * xk_name : 阿萨达是阿萨斯
         * xk_teacher_tags : 青蛙说得对
         * xk_price : ￥1000/年
         * url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20180119/chiron-003.jpg
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
