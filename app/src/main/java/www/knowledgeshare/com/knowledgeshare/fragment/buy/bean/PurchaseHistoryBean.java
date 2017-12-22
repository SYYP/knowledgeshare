package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PurchaseHistoryBean extends BaseBean implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * order_sn : 2017122003442499570
         * order_id : 6
         * name : 如何成为一个合格的歌唱者1
         * price : 180.00
         * payment_time : 2017-12-20 03:44:35
         * order_amount : ￥176.40
         */

        private int id;
        private String order_sn;
        private int order_id;
        private String name;
        private String price;
        private String payment_time;
        private String order_amount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPayment_time() {
            return payment_time;
        }

        public void setPayment_time(String payment_time) {
            this.payment_time = payment_time;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }
    }
}
