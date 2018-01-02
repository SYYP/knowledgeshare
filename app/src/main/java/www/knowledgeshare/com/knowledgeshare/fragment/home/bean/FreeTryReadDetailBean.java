package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by lxk on 2017/12/17.
 */

public class FreeTryReadDetailBean {

    /**
     * video_time : 0:2:20
     * zl_price : 订阅160/年
     * zl_id : 1
     * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
     * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg
     * teacher_id : 1
     * t_name : 张老师
     * name : 专栏小节，如何成为男高音1
     * description : 专栏小节介绍，男高音占有十分重要的地位，第一男主角多为男高音歌唱家扮演。当阉人歌手还霸占歌剧舞台时，一位男歌手以真正的声音唱出高音C
     * created_at : 2017-12-08
     * comment : [{"user_avatar":"","islive":false,"user_id":1,"teacher_id":1,"user_name":"123","created_at":"2017-12-15 06:57:39","comment":[],"id":18,"state":1,"content":"试读测试留言","live":0}]
     * id : 1
     */
    private String video_time;
    private String zl_price;
    private int zl_id;
    private String video_url;
    private String t_header;
    private int teacher_id;
    private String t_name;
    private String name;
    private String content;
    private String t_tag;
    private String h5_url;
    private String created_at;
    private List<CommentEntity> comment;
    private int id;
    private boolean isfav;
    private String txt_url;

    public String getH5_url() {
        return h5_url;
    }

    public void setTxt_url(String txt_url) {
        this.txt_url = txt_url;
    }

    public String getTxt_url() {
        return txt_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }

    public String getT_tag() {
        return t_tag;
    }

    public void setT_tag(String t_tag) {
        this.t_tag = t_tag;
    }

    public boolean isfav() {
        return isfav;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsfav(boolean isfav) {
        this.isfav = isfav;
    }

    public void setVideo_time(String video_time) {
        this.video_time = video_time;
    }

    public void setZl_price(String zl_price) {
        this.zl_price = zl_price;
    }

    public void setZl_id(int zl_id) {
        this.zl_id = zl_id;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setT_header(String t_header) {
        this.t_header = t_header;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setComment(List<CommentEntity> comment) {
        this.comment = comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo_time() {
        return video_time;
    }

    public String getZl_price() {
        return zl_price;
    }

    public int getZl_id() {
        return zl_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getT_header() {
        return t_header;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public String getT_name() {
        return t_name;
    }

    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public List<CommentEntity> getComment() {
        return comment;
    }

    public int getId() {
        return id;
    }

    public static class CommentEntity {
        /**
         * user_avatar :
         * islive : false
         * user_id : 1
         * teacher_id : 1
         * user_name : 123
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
