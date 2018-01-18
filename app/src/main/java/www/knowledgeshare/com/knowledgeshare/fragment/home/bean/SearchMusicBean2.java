package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class SearchMusicBean2 {


    /**
     * data : [{"video_time":"00:00","good_count":0,"t_tag":"中国好声音","parent_name":"测试1-12","is_view":1,"is_try":1,"created_at":"2018-01-15 10:00:27","isfav":false,"is_collect":1,"good_count_true":0,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaokelib/20180105/纯音乐 - 伴奏音乐.mp3","view_count_true":0,"updated_at":"2018-01-15 10:10:03","t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20180112/5a44a0577ca86.png","t_name":"张老师","id":27,"isbuy":false,"islive":false,"is_good":1,"xk_id":9,"collect_count":0,"relevance":10,"video_type":"xk","collect_count_true":0,"name":"精彩强Sir - 故事与她 (Remix)","view_count":0},{"video_time":"00:00","good_count":0,"t_tag":"中国好声音","parent_name":"测试1-12","is_view":1,"is_try":1,"created_at":"2018-01-13 02:27:42","isfav":false,"is_collect":1,"good_count_true":0,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20180115/精彩强Sir - 故事与她 (Remix).mp3","view_count_true":0,"updated_at":"2018-01-15 10:11:22","t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20180112/5a44a0577ca86.png","t_name":"张老师","id":25,"isbuy":false,"islive":false,"is_good":1,"xk_id":9,"collect_count":0,"relevance":60,"video_type":"xk","collect_count_true":0,"name":"music1","view_count":1000}]
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
         * video_time : 00:00
         * good_count : 0
         * t_tag : 中国好声音
         * parent_name : 测试1-12
         * is_view : 1
         * is_try : 1
         * created_at : 2018-01-15 10:00:27
         * isfav : false
         * is_collect : 1
         * good_count_true : 0
         * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaokelib/20180105/纯音乐 - 伴奏音乐.mp3
         * view_count_true : 0
         * updated_at : 2018-01-15 10:10:03
         * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20180112/5a44a0577ca86.png
         * t_name : 张老师
         * id : 27
         * isbuy : false
         * islive : false
         * is_good : 1
         * xk_id : 9
         * collect_count : 0
         * relevance : 10
         * video_type : xk
         * collect_count_true : 0
         * name : 精彩强Sir - 故事与她 (Remix)
         * view_count : 0
         */
        private String video_time;
        private int good_count;
        private String t_tag;
        private String parent_name;
        private int is_view;
        private int is_try;
        private String created_at;
        private boolean isfav;
        private int is_collect;
        private int good_count_true;
        private String video_url;
        private int view_count_true;
        private String updated_at;
        private String t_header;
        private String t_name;
        private int id;
        private boolean isbuy;
        private boolean islive;
        private int is_good;
        private int xk_id;
        private int collect_count;
        private int relevance;
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

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
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

        public void setIsbuy(boolean isbuy) {
            this.isbuy = isbuy;
        }

        public void setIslive(boolean islive) {
            this.islive = islive;
        }

        public void setIs_good(int is_good) {
            this.is_good = is_good;
        }

        public void setXk_id(int xk_id) {
            this.xk_id = xk_id;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public void setRelevance(int relevance) {
            this.relevance = relevance;
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

        public String getUpdated_at() {
            return updated_at;
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

        public boolean isIsbuy() {
            return isbuy;
        }

        public boolean isIslive() {
            return islive;
        }

        public int getIs_good() {
            return is_good;
        }

        public int getXk_id() {
            return xk_id;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public int getRelevance() {
            return relevance;
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
