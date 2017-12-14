package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class HomeDaShiBanNewBean {

    /**
     * data : [{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"4天前更新","zl_name":"崔宗顺的男低音歌唱家的秘密1","id":1,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"},{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"4天前更新","zl_name":"崔宗顺的男低音歌唱家的秘密2","id":2,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"},{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"4天前更新","zl_name":"崔宗顺的男低音歌唱家的秘密5","id":5,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"},{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"4天前更新","zl_name":"崔宗顺的男低音歌唱家的秘密4","id":4,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"}]
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
         * zl_price : ￥160/年
         * zl_update_name : 专栏小节，如何成为男高音2
         * zl_update_time : 4天前更新
         * zl_name : 崔宗顺的男低音歌唱家的秘密1
         * id : 1
         * zl_img : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png
         * zl_introduce : 韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。
         */
        private String zl_price;
        private String zl_update_name;
        private String zl_update_time;
        private String zl_name;
        private int id;
        private String zl_img;
        private String zl_introduce;

        public void setZl_price(String zl_price) {
            this.zl_price = zl_price;
        }

        public void setZl_update_name(String zl_update_name) {
            this.zl_update_name = zl_update_name;
        }

        public void setZl_update_time(String zl_update_time) {
            this.zl_update_time = zl_update_time;
        }

        public void setZl_name(String zl_name) {
            this.zl_name = zl_name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setZl_img(String zl_img) {
            this.zl_img = zl_img;
        }

        public void setZl_introduce(String zl_introduce) {
            this.zl_introduce = zl_introduce;
        }

        public String getZl_price() {
            return zl_price;
        }

        public String getZl_update_name() {
            return zl_update_name;
        }

        public String getZl_update_time() {
            return zl_update_time;
        }

        public String getZl_name() {
            return zl_name;
        }

        public int getId() {
            return id;
        }

        public String getZl_img() {
            return zl_img;
        }

        public String getZl_introduce() {
            return zl_introduce;
        }
    }
}
