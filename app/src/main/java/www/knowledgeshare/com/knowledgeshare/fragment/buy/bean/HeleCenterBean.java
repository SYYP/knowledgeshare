package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class HeleCenterBean implements Serializable{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : 如何购买？
         * h5_url : http://thinks.iask.in/help.html?id=1
         */

        private int id;
        private String title;
        private String h5_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getH5_url() {
            return h5_url;
        }

        public void setH5_url(String h5_url) {
            this.h5_url = h5_url;
        }
    }
}
