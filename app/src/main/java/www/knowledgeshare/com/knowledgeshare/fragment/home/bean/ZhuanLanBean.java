package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class ZhuanLanBean {


    /**
     * zl_name : 崔宗顺的男低音歌唱家的秘密1
     * created_at : 2017-12-08 08:37:35
     * zl_image : [{"zl_id":1,"updated_at":"2017-12-08 08:37:36","width":340,"created_at":"2017-12-08 08:37:36","id":4,"type":"17:8","url":"zhuanlan/20171208/17x8.png","height":160}]
     * zl_rss : 订阅者可以通过微信或支付宝付款购买，购买无法退款，请大家仔细看好订阅须知
     * lately : [{"name":"专栏小节，如何成为男高音2","description":"专栏小节介绍，男高音占有十分重要的地位，第一男主角多为男高音歌唱家扮演。当阉人歌手还霸占歌剧舞台时，一位男歌手以真正的声音唱出高音C","created_at":"20天前更新","id":2},{"name":"专栏小节，如何成为男高音1","description":"专栏小节介绍，男高音占有十分重要的地位，第一男主角多为男高音歌唱家扮演。当阉人歌手还霸占歌剧舞台时，一位男歌手以真正的声音唱出高音C","created_at":"20天前更新","id":1}]
     * zl_class_id : 1
     * zl_introduce : 韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。
     * zl_teacher_tags : 美国音乐艺术家
     * zl_suitable : 此课程适合10-15岁的孩子学习，需要提高自己发音的同学，可以购买学习，一周后效果明显提升。
     * zl_price : ￥160/年
     * teacher : {"t_tag":"中国好声音","t_header":"user/20171208/1275610854404958.jpg","t_name":"张老师","t_img":"zhuanlan/20171208/17x8.png","t_live":2,"created_at":"2017-12-08 01:08:06","id":1,"t_mobile":"15250735030","t_introduce":"毕业于四川师范大学音乐系,音乐教育本科,现任四川省泸县第二中学一级专职音乐教师。泸县音乐家协会常任理事。"}
     * updated_at : 2017-12-22 02:52:03
     * zl_teacher_id : 1
     * id : 1
     * zl_img : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/17x8.png
     * zl_look : 阅读须知，此课程属于原创，转成请标明出处，如有私自转发，必究！
     * zl_buy_count : 3
     */
    private String zl_name;
    private String created_at;
    private List<ZlImageEntity> zl_image;
    private String zl_rss;
    private List<LatelyEntity> lately;
    private int zl_class_id;
    private String zl_introduce;
    private String zl_teacher_tags;
    private String zl_suitable;
    private String zl_price;
    private TeacherEntity teacher;
    private String updated_at;
    private int zl_teacher_id;
    private int id;
    private String zl_img;
    private String zl_look;
    private int zl_buy_count;
    private String h5_url;

    public String getH5_url() {
        return h5_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }

    public void setZl_name(String zl_name) {
        this.zl_name = zl_name;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setZl_image(List<ZlImageEntity> zl_image) {
        this.zl_image = zl_image;
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

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
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

    public List<ZlImageEntity> getZl_image() {
        return zl_image;
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

    public TeacherEntity getTeacher() {
        return teacher;
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

    public static class ZlImageEntity {
        /**
         * zl_id : 1
         * updated_at : 2017-12-08 08:37:36
         * width : 340
         * created_at : 2017-12-08 08:37:36
         * id : 4
         * type : 17:8
         * url : zhuanlan/20171208/17x8.png
         * height : 160
         */
        private int zl_id;
        private String updated_at;
        private int width;
        private String created_at;
        private int id;
        private String type;
        private String url;
        private int height;

        public void setZl_id(int zl_id) {
            this.zl_id = zl_id;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getZl_id() {
            return zl_id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public int getWidth() {
            return width;
        }

        public String getCreated_at() {
            return created_at;
        }

        public int getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public int getHeight() {
            return height;
        }
    }

    public static class LatelyEntity {
        /**
         * name : 专栏小节，如何成为男高音2
         * description : 专栏小节介绍，男高音占有十分重要的地位，第一男主角多为男高音歌唱家扮演。当阉人歌手还霸占歌剧舞台时，一位男歌手以真正的声音唱出高音C
         * created_at : 20天前更新
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

    public static class TeacherEntity {
        /**
         * t_tag : 中国好声音
         * t_header : user/20171208/1275610854404958.jpg
         * t_name : 张老师
         * t_img : zhuanlan/20171208/17x8.png
         * t_live : 2
         * created_at : 2017-12-08 01:08:06
         * id : 1
         * t_mobile : 15250735030
         * t_introduce : 毕业于四川师范大学音乐系,音乐教育本科,现任四川省泸县第二中学一级专职音乐教师。泸县音乐家协会常任理事。
         */
        private String t_tag;
        private String t_header;
        private String t_name;
        private String t_img;
        private int t_live;
        private String created_at;
        private int id;
        private String t_mobile;
        private String t_introduce;

        public void setT_tag(String t_tag) {
            this.t_tag = t_tag;
        }

        public void setT_header(String t_header) {
            this.t_header = t_header;
        }

        public void setT_name(String t_name) {
            this.t_name = t_name;
        }

        public void setT_img(String t_img) {
            this.t_img = t_img;
        }

        public void setT_live(int t_live) {
            this.t_live = t_live;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setT_mobile(String t_mobile) {
            this.t_mobile = t_mobile;
        }

        public void setT_introduce(String t_introduce) {
            this.t_introduce = t_introduce;
        }

        public String getT_tag() {
            return t_tag;
        }

        public String getT_header() {
            return t_header;
        }

        public String getT_name() {
            return t_name;
        }

        public String getT_img() {
            return t_img;
        }

        public int getT_live() {
            return t_live;
        }

        public String getCreated_at() {
            return created_at;
        }

        public int getId() {
            return id;
        }

        public String getT_mobile() {
            return t_mobile;
        }

        public String getT_introduce() {
            return t_introduce;
        }
    }
}
