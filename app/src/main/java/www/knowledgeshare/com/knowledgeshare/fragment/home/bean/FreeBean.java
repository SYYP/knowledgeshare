package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class FreeBean {


    /**
     * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/18x7.png
     * teacher_id : 1
     * name : 歌唱家专栏
     * created_at : 2017-12-08 10:08:07
     * suitable : 此课程适合10-15岁的孩子学习，需要提高自己发音的同学，可以购买学习，一周后效果明显提升
     * comment : [{"user_avatar":"","islive":false,"user_id":8,"teacher_id":1,"user_name":"","created_at":"2017-12-12 03:17:39","comment":[{"id":2,"comment_id":6,"content":"测试回复666"}],"id":6,"content":"测试评论","live":1},{"user_avatar":"","islive":false,"user_id":2,"teacher_id":1,"user_name":"","created_at":"2017-12-13 09:19:47","comment":[],"id":5,"content":"在于他们在自己心中是多么","live":1},{"user_avatar":"","islive":false,"user_id":1,"teacher_id":1,"user_name":"123","created_at":"2017-12-12 03:17:39","comment":[],"id":2,"content":"测试评论","live":1},{"user_avatar":"","islive":false,"user_id":1,"teacher_id":1,"user_name":"123","created_at":"2017-12-11 09:13:46","comment":[{"id":1,"comment_id":1,"content":"测试回复"}],"id":1,"content":"测试评论","live":3}]
     * id : 1
     * teacher_has : {"t_tag":"中国好声音","islive":false,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg","t_name":"张老师","t_img":"","t_live":1,"created_at":"2017-12-08 01:08:06","id":1,"isfollow":false,"t_mobile":"15250735030","t_introduce":"毕业于四川师范大学音乐系,音乐教育本科,现任四川省泸县第二中学一级专职音乐教师。泸县音乐家协会常任理事。"}
     * look : 阅读须知，此课程属于原创，转成请标明出处，如有私自转发，必究！
     * child : [{"good_count":0,"video_name":"女高音，一个神秘又充满魅力的声音3","islive":false,"is_good":1,"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"collect_count_true":1,"is_collect":1,"good_count_true":1,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":0,"send_time":0,"id":3,"view_count":0},{"good_count":0,"video_name":"女高音，一个神秘又充满魅力的声音2","islive":false,"is_good":1,"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"collect_count_true":1,"is_collect":1,"good_count_true":1,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music2.mp3","view_count_true":0,"send_time":0,"id":2,"view_count":0},{"good_count":0,"video_name":"女高音，一个神秘又充满魅力的声音1","islive":false,"is_good":1,"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"collect_count_true":2,"is_collect":1,"good_count_true":1,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":0,"send_time":0,"id":1,"view_count":0}]
     */
    private String imgurl;
    private int teacher_id;
    private String name;
    private String created_at;
    private String suitable;
    private List<CommentEntity> comment;
    private int id;
    private TeacherHasEntity teacher_has;
    private String look;
    private List<ChildEntity> child;

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setSuitable(String suitable) {
        this.suitable = suitable;
    }

    public void setComment(List<CommentEntity> comment) {
        this.comment = comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeacher_has(TeacherHasEntity teacher_has) {
        this.teacher_has = teacher_has;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public void setChild(List<ChildEntity> child) {
        this.child = child;
    }

    public String getImgurl() {
        return imgurl;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getSuitable() {
        return suitable;
    }

    public List<CommentEntity> getComment() {
        return comment;
    }

    public int getId() {
        return id;
    }

    public TeacherHasEntity getTeacher_has() {
        return teacher_has;
    }

    public String getLook() {
        return look;
    }

    public List<ChildEntity> getChild() {
        return child;
    }

    public static class CommentEntity {
        /**
         * user_avatar :
         * islive : false
         * user_id : 8
         * teacher_id : 1
         * user_name :
         * created_at : 2017-12-12 03:17:39
         * comment : [{"id":2,"comment_id":6,"content":"测试回复666"}]
         * id : 6
         * content : 测试评论
         * live : 1
         */
        private String user_avatar;
        private boolean islive;
        private int user_id;
        private int teacher_id;
        private String user_name;
        private String created_at;
        private List<ChildCommentEntity> comment;
        private int id;
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

        public void setComment(List<ChildCommentEntity> comment) {
            this.comment = comment;
        }

        public void setId(int id) {
            this.id = id;
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

        public List<ChildCommentEntity> getComment() {
            return comment;
        }

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public int getLive() {
            return live;
        }

        public static class ChildCommentEntity {
            /**
             * id : 2
             * comment_id : 6
             * content : 测试回复666
             */
            private int id;
            private int comment_id;
            private String content;

            public void setId(int id) {
                this.id = id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getId() {
                return id;
            }

            public int getComment_id() {
                return comment_id;
            }

            public String getContent() {
                return content;
            }
        }
    }

    public static class TeacherHasEntity {
        /**
         * t_tag : 中国好声音
         * islive : false
         * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg
         * t_name : 张老师
         * t_img :
         * t_live : 1
         * created_at : 2017-12-08 01:08:06
         * id : 1
         * isfollow : false
         * t_mobile : 15250735030
         * t_introduce : 毕业于四川师范大学音乐系,音乐教育本科,现任四川省泸县第二中学一级专职音乐教师。泸县音乐家协会常任理事。
         */
        private String t_tag;
        private boolean islive;
        private String t_header;
        private String t_name;
        private String t_img;
        private int t_live;
        private String created_at;
        private int id;
        private boolean isfollow;
        private String t_mobile;
        private String t_introduce;

        public void setT_tag(String t_tag) {
            this.t_tag = t_tag;
        }

        public void setIslive(boolean islive) {
            this.islive = islive;
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

        public void setIsfollow(boolean isfollow) {
            this.isfollow = isfollow;
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

        public boolean isIslive() {
            return islive;
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

        public boolean isIsfollow() {
            return isfollow;
        }

        public String getT_mobile() {
            return t_mobile;
        }

        public String getT_introduce() {
            return t_introduce;
        }
    }

    public static class ChildEntity {
        /**
         * good_count : 0
         * video_name : 女高音，一个神秘又充满魅力的声音3
         * islive : false
         * is_good : 1
         * txt_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt
         * txt_old_name : 女高音.txt
         * is_view : 1
         * created_at : 2017-12-08 08:35:30
         * video_old_name : 女高音.mp3
         * isfav : false
         * collect_count : 0
         * collect_count_true : 1
         * is_collect : 1
         * good_count_true : 1
         * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
         * view_count_true : 0
         * send_time : 0
         * id : 3
         * view_count : 0
         */
        private int good_count;
        private String video_name;
        private boolean islive;
        private int is_good;
        private String txt_url;
        private String txt_old_name;
        private int is_view;
        private String created_at;
        private String video_old_name;
        private boolean isfav;
        private int collect_count;
        private int collect_count_true;
        private int is_collect;
        private int good_count_true;
        private String video_url;
        private int view_count_true;
        private int send_time;
        private int id;
        private int view_count;
        private String t_header;
        private String t_tag;

        public String getT_header() {
            return t_header;
        }

        public void setT_header(String t_header) {
            this.t_header = t_header;
        }

        public String getT_tag() {
            return t_tag;
        }

        public void setT_tag(String t_tag) {
            this.t_tag = t_tag;
        }

        public void setGood_count(int good_count) {
            this.good_count = good_count;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public void setIslive(boolean islive) {
            this.islive = islive;
        }

        public void setIs_good(int is_good) {
            this.is_good = is_good;
        }

        public void setTxt_url(String txt_url) {
            this.txt_url = txt_url;
        }

        public void setTxt_old_name(String txt_old_name) {
            this.txt_old_name = txt_old_name;
        }

        public void setIs_view(int is_view) {
            this.is_view = is_view;
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

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public void setCollect_count_true(int collect_count_true) {
            this.collect_count_true = collect_count_true;
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

        public void setSend_time(int send_time) {
            this.send_time = send_time;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
        }

        public int getGood_count() {
            return good_count;
        }

        public String getVideo_name() {
            return video_name;
        }

        public boolean isIslive() {
            return islive;
        }

        public int getIs_good() {
            return is_good;
        }

        public String getTxt_url() {
            return txt_url;
        }

        public String getTxt_old_name() {
            return txt_old_name;
        }

        public int getIs_view() {
            return is_view;
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

        public int getCollect_count() {
            return collect_count;
        }

        public int getCollect_count_true() {
            return collect_count_true;
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

        public int getSend_time() {
            return send_time;
        }

        public int getId() {
            return id;
        }

        public int getView_count() {
            return view_count;
        }
    }

}
