package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;
import java.util.List;

import www.knowledgeshare.com.knowledgeshare.bean.BaseBean;

/**
 * Created by Administrator on 2017/12/21.
 */

public class BuyZlBean extends BaseBean implements Serializable {

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
         * zl_name : 崔宗顺的男低音歌唱家的秘密1
         * zl_teacher_tags : 美国音乐艺术家
         * zl_update_name : 专栏小节，如何成为男高音2
         * zl_update_time : 13天前更新
         * zl_img : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png
         */

        private int id;
        private String zl_name;
        private String zl_teacher_tags;
        private String zl_update_name;
        private String zl_update_time;
        private String zl_img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getZl_name() {
            return zl_name;
        }

        public void setZl_name(String zl_name) {
            this.zl_name = zl_name;
        }

        public String getZl_teacher_tags() {
            return zl_teacher_tags;
        }

        public void setZl_teacher_tags(String zl_teacher_tags) {
            this.zl_teacher_tags = zl_teacher_tags;
        }

        public String getZl_update_name() {
            return zl_update_name;
        }

        public void setZl_update_name(String zl_update_name) {
            this.zl_update_name = zl_update_name;
        }

        public String getZl_update_time() {
            return zl_update_time;
        }

        public void setZl_update_time(String zl_update_time) {
            this.zl_update_time = zl_update_time;
        }

        public String getZl_img() {
            return zl_img;
        }

        public void setZl_img(String zl_img) {
            this.zl_img = zl_img;
        }
    }
}
