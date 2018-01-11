package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by lxk on 2017/12/17.
 */

public class FreeTryReadDetailBean {

    /**
     * video_time : 02:20
     * t_tag : 中国好声音
     * teacher_id : 1
     * is_view : 0
     * is_try : 1
     * created_at : 2017-12-08
     * isfav : false
     * content :
     * zl_price : 订阅160/年
     * is_collect : 1
     * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
     * rss_count_true : 0
     * view_count_true : 0
     * is_rss : 1
     * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png
     * t_name : 张老师
     * id : 1
     * zl_id : 1
     * collect_count : 0
     * video_type : zl
     * zl_h5_url : http://thinks.iask.in/zl.html?id=1
     * collect_count_true : -1
     * h5_url : http://thinks.iask.in/player.html?id=1&type=zl
     * rss_count : 0
     * name : 专栏小节，如何成为男高音1
     * comment : [{"user_avatar":"http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171221/H3rbqjNrENreO2du88Je.jpg","islive":false,"user_id":1,"teacher_id":1,"user_name":"王兵","created_at":"2017-12-15 06:57:39","comment":[],"id":18,"state":1,"content":"试读测试留言","live":0}]
     * view_count : 0
     */
    private String video_time;
    private String t_tag;
    private int teacher_id;
    private int is_view;
    private int is_try;
    private String created_at;
    private boolean isfav;
    private String content;
    private String zl_price;
    private int is_collect;
    private String video_url;
    private int rss_count_true;
    private int view_count_true;
    private int is_rss;
    private String t_header;
    private String t_name;
    private int id;
    private int zl_id;
    private int collect_count;
    private String video_type;
    private String zl_h5_url;
    private int collect_count_true;
    private String h5_url;
    private int rss_count;
    private String name;
    private List<CommentEntity> comment;
    private int view_count;
    private String parent_name;

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public void setVideo_time(String video_time) {
        this.video_time = video_time;
    }

    public void setT_tag(String t_tag) {
        this.t_tag = t_tag;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
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

    public void setIsfav(boolean isfav) {
        this.isfav = isfav;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setZl_price(String zl_price) {
        this.zl_price = zl_price;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setRss_count_true(int rss_count_true) {
        this.rss_count_true = rss_count_true;
    }

    public void setView_count_true(int view_count_true) {
        this.view_count_true = view_count_true;
    }

    public void setIs_rss(int is_rss) {
        this.is_rss = is_rss;
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

    public void setZl_id(int zl_id) {
        this.zl_id = zl_id;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public void setZl_h5_url(String zl_h5_url) {
        this.zl_h5_url = zl_h5_url;
    }

    public void setCollect_count_true(int collect_count_true) {
        this.collect_count_true = collect_count_true;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }

    public void setRss_count(int rss_count) {
        this.rss_count = rss_count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(List<CommentEntity> comment) {
        this.comment = comment;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public String getVideo_time() {
        return video_time;
    }

    public String getT_tag() {
        return t_tag;
    }

    public int getTeacher_id() {
        return teacher_id;
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

    public boolean isIsfav() {
        return isfav;
    }

    public String getContent() {
        return content;
    }

    public String getZl_price() {
        return zl_price;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public String getVideo_url() {
        return video_url;
    }

    public int getRss_count_true() {
        return rss_count_true;
    }

    public int getView_count_true() {
        return view_count_true;
    }

    public int getIs_rss() {
        return is_rss;
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

    public int getZl_id() {
        return zl_id;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public String getVideo_type() {
        return video_type;
    }

    public String getZl_h5_url() {
        return zl_h5_url;
    }

    public int getCollect_count_true() {
        return collect_count_true;
    }

    public String getH5_url() {
        return h5_url;
    }

    public int getRss_count() {
        return rss_count;
    }

    public String getName() {
        return name;
    }

    public List<CommentEntity> getComment() {
        return comment;
    }

    public int getView_count() {
        return view_count;
    }

    public static class CommentEntity {
        /**
         * user_avatar : http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171221/H3rbqjNrENreO2du88Je.jpg
         * islive : false
         * user_id : 1
         * teacher_id : 1
         * user_name : 王兵
         * created_at : 2017-12-15 06:57:39
         * comment : []
         * id : 18
         * state : 1
         * content : 试读测试留言
         * live : 0
         */
        private String user_avatar;
        private boolean islive;
        private int user_id;
        private int teacher_id;
        private String user_name;
        private String created_at;
        private List<?> comment;
        private int id;
        private int state;
        private String content;
        private int live;

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public void setIslive(boolean islive) {
            this.islive = islive;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setComment(List<?> comment) {
            this.comment = comment;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setState(int state) {
            this.state = state;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setLive(int live) {
            this.live = live;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public boolean isIslive() {
            return islive;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getTeacher_id() {
            return teacher_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public List<?> getComment() {
            return comment;
        }

        public int getId() {
            return id;
        }

        public int getState() {
            return state;
        }

        public String getContent() {
            return content;
        }

        public int getLive() {
            return live;
        }
    }
}
