package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class ZhuanLanBean {

    /**
     * zl_name : 崔宗顺的男低音歌唱家的秘密1
     * created_at : 2017-12-08 08:37:35
     * zl_rss : 订阅者可以通过微信或支付宝付款购买，购买无法退款，请大家仔细看好订阅须知
     * lately : [{"name":"专栏小节，如何成为男高音2","description":"专栏小节介绍，男高音占有十分重要的地位，第一男主角多为男高音歌唱家扮演。当阉人歌手还霸占歌剧舞台时，一位男歌手以真正的声音唱出高音C","created_at":"6天前更新","id":2},{"name":"专栏小节，如何成为男高音1","description":"专栏小节介绍，男高音占有十分重要的地位，第一男主角多为男高音歌唱家扮演。当阉人歌手还霸占歌剧舞台时，一位男歌手以真正的声音唱出高音C","created_at":"6天前更新","id":1}]
     * zl_class_id : 1
     * zl_introduce : 韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。
     * zl_teacher_tags : 美国音乐艺术家
     * zl_suitable : 此课程适合10-15岁的孩子学习，需要提高自己发音的同学，可以购买学习，一周后效果明显提升。
     * zl_price : ￥160/年
     * updated_at : 2017-12-08 08:37:35
     * zl_teacher_id : 1
     * id : 1
     * zl_img : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/17x8.png
     * zl_look : 阅读须知，此课程属于原创，转成请标明出处，如有私自转发，必究！
     * zl_buy_count : 0
     */
    private String zl_name;
    private String created_at;
    private String zl_rss;
    private List<LatelyEntity> lately;
    private int zl_class_id;
    private String zl_introduce;
    private String zl_teacher_tags;
    private String zl_suitable;
    private String zl_price;
    private String updated_at;
    private int zl_teacher_id;
    private int id;
    private String zl_img;
    private String zl_look;
    private int zl_buy_count;

    public void setZl_name(String zl_name) {
        this.zl_name = zl_name;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setZl_rss(String zl_rss) {
        this.zl_rss = zl_rss;
    }

    public void setLately(List<LatelyEntity> lately) {
        this.lately = lately;
    }

    public void setZl_class_id(int zl_class_id) {
        this.zl_class_id = zl_class_id;
    }

    public void setZl_introduce(String zl_introduce) {
        this.zl_introduce = zl_introduce;
    }

    public void setZl_teacher_tags(String zl_teacher_tags) {
        this.zl_teacher_tags = zl_teacher_tags;
    }

    public void setZl_suitable(String zl_suitable) {
        this.zl_suitable = zl_suitable;
    }

    public void setZl_price(String zl_price) {
        this.zl_price = zl_price;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setZl_teacher_id(int zl_teacher_id) {
        this.zl_teacher_id = zl_teacher_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setZl_img(String zl_img) {
        this.zl_img = zl_img;
    }

    public void setZl_look(String zl_look) {
        this.zl_look = zl_look;
    }

    public void setZl_buy_count(int zl_buy_count) {
        this.zl_buy_count = zl_buy_count;
    }

    public String getZl_name() {
        return zl_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getZl_rss() {
        return zl_rss;
    }

    public List<LatelyEntity> getLately() {
        return lately;
    }

    public int getZl_class_id() {
        return zl_class_id;
    }

    public String getZl_introduce() {
        return zl_introduce;
    }

    public String getZl_teacher_tags() {
        return zl_teacher_tags;
    }

    public String getZl_suitable() {
        return zl_suitable;
    }

    public String getZl_price() {
        return zl_price;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getZl_teacher_id() {
        return zl_teacher_id;
    }

    public int getId() {
        return id;
    }

    public String getZl_img() {
        return zl_img;
    }

    public String getZl_look() {
        return zl_look;
    }

    public int getZl_buy_count() {
        return zl_buy_count;
    }

    public static class LatelyEntity {
        /**
         * name : 专栏小节，如何成为男高音2
         * description : 专栏小节介绍，男高音占有十分重要的地位，第一男主角多为男高音歌唱家扮演。当阉人歌手还霸占歌剧舞台时，一位男歌手以真正的声音唱出高音C
         * created_at : 6天前更新
         * id : 2
         */
        private String name;
        private String description;
        private String created_at;
        private int id;

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCreated_at() {
            return created_at;
        }

        public int getId() {
            return id;
        }
    }
}
