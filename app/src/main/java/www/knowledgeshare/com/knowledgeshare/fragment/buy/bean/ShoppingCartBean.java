package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ShoppingCartBean implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4
         * xk_name : 如何成为一个合格的歌唱者2
         * xk_price : ￥180/年
         * xk_teacher_tags : 好声音
         * url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png
         */

        private String id;
        private String xk_id;
        private String xk_name;
        private String xk_price;
        private String xk_teacher_tags;
        private String url;
        private boolean isChecked = false;

        public String getXk_id() {
            return xk_id;
        }

        public void setXk_id(String xk_id) {
            this.xk_id = xk_id;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
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

        public String getXk_price() {
            return xk_price;
        }

        public void setXk_price(String xk_price) {
            this.xk_price = xk_price;
        }

        public String getXk_teacher_tags() {
            return xk_teacher_tags;
        }

        public void setXk_teacher_tags(String xk_teacher_tags) {
            this.xk_teacher_tags = xk_teacher_tags;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
