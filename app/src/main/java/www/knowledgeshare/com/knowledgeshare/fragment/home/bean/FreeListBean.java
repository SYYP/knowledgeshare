package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */

public class FreeListBean {

    /**
     * data : [{"video_time":"01:40","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音3","share_h5_url":"http://thinks.iask.in/player.html?id=4&type=free","txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"is_collect":1,"good_count_true":3,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":82,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","t_name":"张老师","id":4,"islive":false,"is_good":1,"txt_old_name":"女高音.txt","collect_count":0,"video_type":"free","collect_count_true":0,"send_time":0,"view_count":0},{"video_time":"01:40","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音3","share_h5_url":"http://thinks.iask.in/player.html?id=3&type=free","txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"is_collect":1,"good_count_true":2,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":82,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","t_name":"张老师","id":3,"islive":false,"is_good":1,"txt_old_name":"女高音.txt","collect_count":0,"video_type":"free","collect_count_true":0,"send_time":0,"view_count":0},{"video_time":"02:00","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音2","share_h5_url":"http://thinks.iask.in/player.html?id=2&type=free","txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"is_collect":1,"good_count_true":0,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music2.mp3","view_count_true":15,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","t_name":"张老师","id":2,"islive":false,"is_good":1,"txt_old_name":"女高音.txt","collect_count":0,"video_type":"free","collect_count_true":1,"send_time":0,"view_count":0},{"video_time":"01:40","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音1222","share_h5_url":"http://thinks.iask.in/player.html?id=1&type=free","txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","is_view":1,"created_at":"2018-01-09 08:35:30","video_old_name":"女高音.mp3","isfav":true,"is_collect":1,"good_count_true":2,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":9,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","t_name":"张老师","id":1,"islive":false,"is_good":1,"txt_old_name":"女高音.txt","collect_count":0,"video_type":"free","collect_count_true":2,"send_time":0,"view_count":0}]
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
         * video_time : 01:40
         * good_count : 0
         * t_tag :
         * parent_name : 歌唱家专栏
         * video_name : 女高音，一个神秘又充满魅力的声音3
         * share_h5_url : http://thinks.iask.in/player.html?id=4&type=free
         * txt_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt
         * is_view : 1
         * created_at : 2017-12-08 08:35:30
         * video_old_name : 女高音.mp3
         * isfav : false
         * is_collect : 1
         * good_count_true : 3
         * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
         * view_count_true : 82
         * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png
         * t_name : 张老师
         * id : 4
         * islive : false
         * is_good : 1
         * txt_old_name : 女高音.txt
         * collect_count : 0
         * video_type : free
         * collect_count_true : 0
         * send_time : 0
         * view_count : 0
         */
        private String video_time;
        private int good_count;
        private String t_tag;
        private String parent_name;
        private String video_name;
        private String share_h5_url;
        private String txt_url;
        private int is_view;
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
        private int collect_count;
        private String video_type;
        private int collect_count_true;
        private int send_time;
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

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
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

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public void setCollect_count_true(int collect_count_true) {
            this.collect_count_true = collect_count_true;
        }

        public void setSend_time(int send_time) {
            this.send_time = send_time;
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

        public String getVideo_name() {
            return video_name;
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

        public int getCollect_count() {
            return collect_count;
        }

        public String getVideo_type() {
            return video_type;
        }

        public int getCollect_count_true() {
            return collect_count_true;
        }

        public int getSend_time() {
            return send_time;
        }

        public int getView_count() {
            return view_count;
        }
    }
}
