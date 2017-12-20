package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class TeacherDetailBean {

    /**
     * xiaoke : [{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","teacher_name":"张老师","video_url":"","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者5","buy_count":"0人","xk_id":7,"xk_teacher_tags":"好声音","time_count":"0:7:2","xk_price":"￥180/年"},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","teacher_name":"张老师","video_url":"","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者4","buy_count":"0人","xk_id":6,"xk_teacher_tags":"好声音","time_count":"0:7:2","xk_price":"￥180/年"}]
     * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg
     * t_name : 张老师
     * t_img : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/17x8.png
     * zhuanlan : [{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"11天前更新","video_url":"","zl_name":"崔宗顺的男低音歌唱家的秘密5","id":5,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"},{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"11天前更新","video_url":"","zl_name":"崔宗顺的男低音歌唱家的秘密4","id":4,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"}]
     * t_introduce : 毕业于四川师范大学音乐系,音乐教育本科,现任四川省泸县第二中学一级专职音乐教师。泸县音乐家协会常任理事。
     */
    private List<XiaokeEntity> xiaoke;
    private String t_header;
    private String t_name;
    private String t_img;
    private List<ZhuanlanEntity> zhuanlan;
    private String t_introduce;

    public void setXiaoke(List<XiaokeEntity> xiaoke) {
        this.xiaoke = xiaoke;
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

    public void setZhuanlan(List<ZhuanlanEntity> zhuanlan) {
        this.zhuanlan = zhuanlan;
    }

    public void setT_introduce(String t_introduce) {
        this.t_introduce = t_introduce;
    }

    public List<XiaokeEntity> getXiaoke() {
        return xiaoke;
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

    public List<ZhuanlanEntity> getZhuanlan() {
        return zhuanlan;
    }

    public String getT_introduce() {
        return t_introduce;
    }

    public static class XiaokeEntity {
        /**
         * xk_image : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png
         * teacher_name : 张老师
         * video_url :
         * nodule_count : 2节
         * xk_name : 如何成为一个合格的歌唱者5
         * buy_count : 0人
         * xk_id : 7
         * xk_teacher_tags : 好声音
         * time_count : 0:7:2
         * xk_price : ￥180/年
         */
        private String xk_image;
        private String teacher_name;
        private String video_url;
        private String nodule_count;
        private String xk_name;
        private String buy_count;
        private int xk_id;
        private String xk_teacher_tags;
        private String time_count;
        private String xk_price;

        public void setXk_image(String xk_image) {
            this.xk_image = xk_image;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public void setNodule_count(String nodule_count) {
            this.nodule_count = nodule_count;
        }

        public void setXk_name(String xk_name) {
            this.xk_name = xk_name;
        }

        public void setBuy_count(String buy_count) {
            this.buy_count = buy_count;
        }

        public void setXk_id(int xk_id) {
            this.xk_id = xk_id;
        }

        public void setXk_teacher_tags(String xk_teacher_tags) {
            this.xk_teacher_tags = xk_teacher_tags;
        }

        public void setTime_count(String time_count) {
            this.time_count = time_count;
        }

        public void setXk_price(String xk_price) {
            this.xk_price = xk_price;
        }

        public String getXk_image() {
            return xk_image;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public String getVideo_url() {
            return video_url;
        }

        public String getNodule_count() {
            return nodule_count;
        }

        public String getXk_name() {
            return xk_name;
        }

        public String getBuy_count() {
            return buy_count;
        }

        public int getXk_id() {
            return xk_id;
        }

        public String getXk_teacher_tags() {
            return xk_teacher_tags;
        }

        public String getTime_count() {
            return time_count;
        }

        public String getXk_price() {
            return xk_price;
        }
    }

    public static class ZhuanlanEntity {
        /**
         * zl_price : ￥160/年
         * zl_update_name : 专栏小节，如何成为男高音2
         * zl_update_time : 11天前更新
         * video_url :
         * zl_name : 崔宗顺的男低音歌唱家的秘密5
         * id : 5
         * zl_img : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png
         * zl_introduce : 韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。
         */
        private String zl_price;
        private String zl_update_name;
        private String zl_update_time;
        private String video_url;
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

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
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

        public String getVideo_url() {
            return video_url;
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
