package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class EveryDayBean {


    /**
     * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png
     * dailys : [{"good_count":0,"video_name":"女高音，一个神秘又充满魅力的声音1","islive":false,"is_good":0,"teacher_id":1,"teacher_to":{"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg","t_name":"张老师","id":1},"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":0,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"collect_count_true":0,"is_collect":0,"good_count_true":0,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":0,"send_time":0,"updated_at":"2017-12-08 08:35:30","id":1,"view_count":0},{"good_count":0,"video_name":"女高音，一个神秘又充满魅力的声音2","islive":false,"is_good":0,"teacher_id":1,"teacher_to":{"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg","t_name":"张老师","id":1},"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":0,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"collect_count_true":0,"is_collect":0,"good_count_true":0,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music2.mp3","view_count_true":0,"send_time":0,"updated_at":"2017-12-08 08:35:30","id":2,"view_count":0},{"good_count":0,"video_name":"女高音，一个神秘又充满魅力的声音3","islive":false,"is_good":0,"teacher_id":1,"teacher_to":{"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg","t_name":"张老师","id":1},"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":0,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"collect_count_true":0,"is_collect":0,"good_count_true":0,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":0,"send_time":0,"updated_at":"2017-12-08 08:35:30","id":3,"view_count":0}]
     * update_count : 3
     * view_count : 0
     * collect_count : 0
     */
    private String imgurl;
    private List<DailysEntity> dailys;
    private int update_count;
    private int view_count;
    private int collect_count;

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setDailys(List<DailysEntity> dailys) {
        this.dailys = dailys;
    }

    public void setUpdate_count(int update_count) {
        this.update_count = update_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getImgurl() {
        return imgurl;
    }

    public List<DailysEntity> getDailys() {
        return dailys;
    }

    public int getUpdate_count() {
        return update_count;
    }

    public int getView_count() {
        return view_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public static class DailysEntity {
        /**
         * good_count : 0
         * video_name : 女高音，一个神秘又充满魅力的声音1
         * islive : false
         * is_good : 0
         * teacher_id : 1
         * teacher_to : {"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg","t_name":"张老师","id":1}
         * txt_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt
         * txt_old_name : 女高音.txt
         * is_view : 0
         * created_at : 2017-12-08 08:35:30
         * video_old_name : 女高音.mp3
         * isfav : false
         * collect_count : 0
         * collect_count_true : 0
         * is_collect : 0
         * good_count_true : 0
         * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
         * view_count_true : 0
         * send_time : 0
         * updated_at : 2017-12-08 08:35:30
         * id : 1
         * view_count : 0
         */
        private int good_count;
        private String video_name;
        private boolean islive;
        private int is_good;
        private int teacher_id;
        private TeacherToEntity teacher_to;
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
        private String updated_at;
        private int id;
        private int view_count;

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

        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
        }

        public void setTeacher_to(TeacherToEntity teacher_to) {
            this.teacher_to = teacher_to;
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

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
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

        public int getTeacher_id() {
            return teacher_id;
        }

        public TeacherToEntity getTeacher_to() {
            return teacher_to;
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

        public String getUpdated_at() {
            return updated_at;
        }

        public int getId() {
            return id;
        }

        public int getView_count() {
            return view_count;
        }

        public static class TeacherToEntity {
            /**
             * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg
             * t_name : 张老师
             * id : 1
             */
            private String t_header;
            private String t_name;
            private int id;

            public void setT_header(String t_header) {
                this.t_header = t_header;
            }

            public void setT_name(String t_name) {
                this.t_name = t_name;
            }

            public void setId(int id) {
                this.id = id;
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
        }
    }
}
