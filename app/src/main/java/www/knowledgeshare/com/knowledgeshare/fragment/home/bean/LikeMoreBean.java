package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class LikeMoreBean {

    /**
     * data : [{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/5x7.png","xk_name":"如何成为一个合格的歌唱者1","xk_id":3},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/5x7.png","xk_name":"如何成为一个合格的歌唱者4","xk_id":6},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/5x7.png","xk_name":"如何成为一个合格的歌唱者2","xk_id":4}]
     */
    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * xk_image : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/5x7.png
         * xk_name : 如何成为一个合格的歌唱者1
         * xk_id : 3
         */
        private String xk_image;
        private String xk_name;
        private int xk_id;

        public void setXk_image(String xk_image) {
            this.xk_image = xk_image;
        }

        public void setXk_name(String xk_name) {
            this.xk_name = xk_name;
        }

        public void setXk_id(int xk_id) {
            this.xk_id = xk_id;
        }

        public String getXk_image() {
            return xk_image;
        }

        public String getXk_name() {
            return xk_name;
        }

        public int getXk_id() {
            return xk_id;
        }
    }
}
