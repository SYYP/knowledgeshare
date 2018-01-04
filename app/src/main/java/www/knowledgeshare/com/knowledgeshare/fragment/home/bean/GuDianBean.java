package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class GuDianBean {


    /**
     * h5_url : http://thinks.iask.in/class.html?type=gd
     * xiaoke : [{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","teacher_name":"张老师","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/music2.mp3","nodule_count":"节","xk_name":"如何成为一个合格的歌唱者1","buy_count":"人","xk_id":8,"xk_teacher_tags":"好声音","time_count":"00:00","xk_price":"￥180/年"}]
     * slide : [{"imgurl":"banner/20171208/banner.png","course_id":1,"name":"和催宗顺在一起","link":"","created_at":"2017-12-08 11:12:24","id":1,"sort":0,"state":1,"type":1}]
     * introduce : 此专栏简单的介绍，一个优秀的音乐老师
     * zhuanlan : [{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音1","zl_update_time":"26天前更新","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","zl_name":"崔宗顺的男低音歌唱家的秘密5","id":5,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"},{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音1","zl_update_time":"26天前更新","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","zl_name":"崔宗顺的男低音歌唱家的秘密1","id":1,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"}]
     */
    private String h5_url;
    private List<XiaokeEntity> xiaoke;
    private List<SlideEntity> slide;
    private String introduce;
    private List<ZhuanlanEntity> zhuanlan;

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }

    public void setXiaoke(List<XiaokeEntity> xiaoke) {
        this.xiaoke = xiaoke;
    }

    public void setSlide(List<SlideEntity> slide) {
        this.slide = slide;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setZhuanlan(List<ZhuanlanEntity> zhuanlan) {
        this.zhuanlan = zhuanlan;
    }

    public String getH5_url() {
        return h5_url;
    }

    public List<XiaokeEntity> getXiaoke() {
        return xiaoke;
    }

    public List<SlideEntity> getSlide() {
        return slide;
    }

    public String getIntroduce() {
        return introduce;
    }

    public List<ZhuanlanEntity> getZhuanlan() {
        return zhuanlan;
    }

    public static class XiaokeEntity {
        /**
         * xk_image : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png
         * teacher_name : 张老师
         * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/music2.mp3
         * nodule_count : 节
         * xk_name : 如何成为一个合格的歌唱者1
         * buy_count : 人
         * xk_id : 8
         * xk_teacher_tags : 好声音
         * time_count : 00:00
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

    public static class SlideEntity {
        /**
         * imgurl : banner/20171208/banner.png
         * course_id : 1
         * name : 和催宗顺在一起
         * link :
         * created_at : 2017-12-08 11:12:24
         * id : 1
         * sort : 0
         * state : 1
         * type : 1
         */
        private String imgurl;
        private int course_id;
        private String name;
        private String link;
        private String created_at;
        private int id;
        private int sort;
        private int state;
        private int type;

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public void setState(int state) {
            this.state = state;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImgurl() {
            return imgurl;
        }

        public int getCourse_id() {
            return course_id;
        }

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }

        public String getCreated_at() {
            return created_at;
        }

        public int getId() {
            return id;
        }

        public int getSort() {
            return sort;
        }

        public int getState() {
            return state;
        }

        public int getType() {
            return type;
        }
    }

    public static class ZhuanlanEntity {
        /**
         * zl_price : ￥160/年
         * zl_update_name : 专栏小节，如何成为男高音1
         * zl_update_time : 26天前更新
         * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
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
