package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class GuDianBean {

    /**
     * h5_url : http://thinks.iask.in/class.html?type=gd
     * xiaoke : [{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","try_video":{"video_time":"04:42","parent_name":"如何成为一个合格的歌唱者1","islive":false,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/music2.mp3","share_h5_url":"http://thinks.iask.in/player.html?id=8&type=xk","t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1x1.png","isfav":false,"video_type":"xk"},"teacher_name":"张老师","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者1","buy_count":"3人","xk_id":8,"xk_teacher_tags":"好声音","time_count":"07:02","xk_price":"￥180/年"}]
     * slide : [{"imgurl":"banner/20171208/banner.png","course_id":1,"name":"和催宗顺在一起","link":"","created_at":"2017-12-08 11:12:24","id":1,"sort":0,"state":1,"type":1}]
     * introduce : 此专栏简单的介绍，一个优秀的音乐老师
     * zhuanlan : [{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音1","zl_update_time":"28天前更新","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","zl_name":"崔宗顺的男低音歌唱家的秘密5","id":5,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"},{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音1","zl_update_time":"28天前更新","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","zl_name":"崔宗顺的男低音歌唱家的秘密1","id":1,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"}]
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
         * try_video : {"video_time":"04:42","parent_name":"如何成为一个合格的歌唱者1","islive":false,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/music2.mp3","share_h5_url":"http://thinks.iask.in/player.html?id=8&type=xk","t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1x1.png","isfav":false,"video_type":"xk"}
         * teacher_name : 张老师
         * nodule_count : 2节
         * xk_name : 如何成为一个合格的歌唱者1
         * buy_count : 3人
         * xk_id : 8
         * xk_teacher_tags : 好声音
         * time_count : 07:02
         * xk_price : ￥180/年
         */
        private String xk_image;
        private TryVideoEntity try_video;
        private String teacher_name;
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

        public void setTry_video(TryVideoEntity try_video) {
            this.try_video = try_video;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
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

        public TryVideoEntity getTry_video() {
            return try_video;
        }

        public String getTeacher_name() {
            return teacher_name;
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

        public static class TryVideoEntity {

            /**
             * video_time : 04:42
             * good_count : 0
             * t_tag :
             * parent_name : 如何成为一个合格的歌唱者1
             * share_h5_url : http://thinks.iask.in/player.html?id=8&type=xk
             * txt_url : xiaoke/20171208/1.txt
             * is_view : 1
             * is_try : 0
             * created_at : 2017-12-25 07:04:02
             * video_old_name : 男低音2.mp3
             * isfav : false
             * is_collect : 1
             * good_count_true : 0
             * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/music2.mp3
             * view_count_true : 0
             * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1x1.png
             * t_name :
             * id : 16
             * islive : false
             * is_good : 1
             * txt_old_name : 男低音2.txt
             * xk_id : 8
             * collect_count : 0
             * video_type : xk
             * collect_count_true : 0
             * name : 男低音，如何通过技巧正确的发音，提高男低音的魅力2
             * view_count : 0
             */
            private String video_time;
            private int good_count;
            private String t_tag;
            private String parent_name;
            private String share_h5_url;
            private String txt_url;
            private int is_view;
            private int is_try;
            private String created_at;
            private String video_old_name;
            private boolean isfav;
            private int is_collect;
            private int good_count_true;
            private String video_url;
            private int view_count_true;
            private String t_header;
            private String t_name;
            private int id;
            private boolean islive;
            private int is_good;
            private String txt_old_name;
            private int xk_id;
            private int collect_count;
            private String video_type;
            private int collect_count_true;
            private String name;
            private int view_count;

            public void setVideo_time(String video_time) {
                this.video_time = video_time;
            }

            public void setGood_count(int good_count) {
                this.good_count = good_count;
            }

            public void setT_tag(String t_tag) {
                this.t_tag = t_tag;
            }

            public void setParent_name(String parent_name) {
                this.parent_name = parent_name;
            }

            public void setShare_h5_url(String share_h5_url) {
                this.share_h5_url = share_h5_url;
            }

            public void setTxt_url(String txt_url) {
                this.txt_url = txt_url;
            }

            public void setIs_view(int is_view) {
                this.is_view = is_view;
            }

            public void setIs_try(int is_try) {
                this.is_try = is_try;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public void setVideo_old_name(String video_old_name) {
                this.video_old_name = video_old_name;
            }

            public void setIsfav(boolean isfav) {
                this.isfav = isfav;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public void setGood_count_true(int good_count_true) {
                this.good_count_true = good_count_true;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }

            public void setView_count_true(int view_count_true) {
                this.view_count_true = view_count_true;
            }

            public void setT_header(String t_header) {
                this.t_header = t_header;
            }

            public void setT_name(String t_name) {
                this.t_name = t_name;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setIslive(boolean islive) {
                this.islive = islive;
            }

            public void setIs_good(int is_good) {
                this.is_good = is_good;
            }

            public void setTxt_old_name(String txt_old_name) {
                this.txt_old_name = txt_old_name;
            }

            public void setXk_id(int xk_id) {
                this.xk_id = xk_id;
            }

            public void setCollect_count(int collect_count) {
                this.collect_count = collect_count;
            }

            public void setVideo_type(String video_type) {
                this.video_type = video_type;
            }

            public void setCollect_count_true(int collect_count_true) {
                this.collect_count_true = collect_count_true;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setView_count(int view_count) {
                this.view_count = view_count;
            }

            public String getVideo_time() {
                return video_time;
            }

            public int getGood_count() {
                return good_count;
            }

            public String getT_tag() {
                return t_tag;
            }

            public String getParent_name() {
                return parent_name;
            }

            public String getShare_h5_url() {
                return share_h5_url;
            }

            public String getTxt_url() {
                return txt_url;
            }

            public int getIs_view() {
                return is_view;
            }

            public int getIs_try() {
                return is_try;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getVideo_old_name() {
                return video_old_name;
            }

            public boolean isIsfav() {
                return isfav;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public int getGood_count_true() {
                return good_count_true;
            }

            public String getVideo_url() {
                return video_url;
            }

            public int getView_count_true() {
                return view_count_true;
            }

            public String getT_header() {
                return t_header;
            }

            public String getT_name() {
                return t_name;
            }

            public int getId() {
                return id;
            }

            public boolean isIslive() {
                return islive;
            }

            public int getIs_good() {
                return is_good;
            }

            public String getTxt_old_name() {
                return txt_old_name;
            }

            public int getXk_id() {
                return xk_id;
            }

            public int getCollect_count() {
                return collect_count;
            }

            public String getVideo_type() {
                return video_type;
            }

            public int getCollect_count_true() {
                return collect_count_true;
            }

            public String getName() {
                return name;
            }

            public int getView_count() {
                return view_count;
            }
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
         * zl_update_time : 28天前更新
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
