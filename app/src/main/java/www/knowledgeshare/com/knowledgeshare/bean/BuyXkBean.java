package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class BuyXkBean  extends  BaseBean implements Serializable{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * xk_name : 如何成为一个合格的歌唱者1
         * xk_teacher_tags : 好声音
         * xk_price : ￥180/年
         * xk_image : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png
         * buy_count : 5
         */

        private String id;
        private String xk_id;
        private String zl_id;
        private String t_name;
        private String xk_name;
        private String xk_teacher_tags;
        private String xk_price;
        private String xk_image;
        private int buy_count;
        private double jindu;

        public String getZl_id() {
            return zl_id;
        }

        public void setZl_id(String zl_id) {
            this.zl_id = zl_id;
        }

        public String getT_name() {
            return t_name;
        }

        public void setT_name(String t_name) {
            this.t_name = t_name;
        }

        public String getXk_id() {
            return xk_id;
        }

        public void setXk_id(String xk_id) {
            this.xk_id = xk_id;
        }

        public double getJindu() {
            return jindu;
        }

        public void setJindu(double jindu) {
            this.jindu = jindu;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getXk_image() {
            return xk_image;
        }

        public void setXk_image(String xk_image) {
            this.xk_image = xk_image;
        }

        public int getBuy_count() {
            return buy_count;
        }

        public void setBuy_count(int buy_count) {
            this.buy_count = buy_count;
        }
    }
}
