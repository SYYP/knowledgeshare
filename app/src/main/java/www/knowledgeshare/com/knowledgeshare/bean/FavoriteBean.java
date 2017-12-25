package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/25.
 */

public class FavoriteBean extends BaseBean implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * video_name : 女高音，一个神秘又充满魅力的声音3
         * video_type : free
         * type : 1
         * name : 歌唱家专栏
         * video_time :
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/18x7.png
         * rss_count_true : 0
         * collect_count_true : 1
         * view_count_true : 0
         * rss_count : 0
         * collect_count : 0
         * view_count : 0
         * is_rss : 1
         * is_collect : 1
         * is_view : 0
         * content : 请大家先看一张图。这张图是过去十年，中国互联网网民的成长速度和中国GDP成长速度的比较图。我们看到，十年前中国互联网网民成长速度在50%以上，到今天其实已经到了6%左右。事实上，过去的四年，中国互联网网民的成长速度都已经慢于中国GDP的成长速度了。 这意味着什么呢？意味着互联网的人口红利没有了。所以去年我在这里说“无线（移动）互联网已经结束了”，大多数人都不同意，很多人讲，没有结束、只是进入了下半场。今年我看到，原来讲“下半场”的人都开始讲人工智能了，所以我的下一页 PPT 也给大家讲一讲人工智能。 人工智能：互联网成长的新动力 网民红利没有了，但是成长的动力还有。这个动力是什么呢？就是人工智能。 人工智能今天处在一个发展非常早期的阶段，非常像十几年前中国互联网的成长。对于中国互联网十几年前的成长，我以前也讲过一个观点，当时它的成长动力有三个。 第一，网民人数的高速增长，大家刚才看到最初一年有50%的增长速度。 第二，上网时间的不断增加，过去大家每个人每天上网几分钟，后来变成十几分钟，现在可能会有好几个小时，每个人上网的时间也在不断增加。 第三，就是网上的信息量在不断增加。 所以当时的中国互联网、包括世界互联网，其增长动力有三个：网民人数的增加，上网时间的增加，以及网上信息量的增加，这些都在推动互联网不断的、快速的繁荣。
         * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg
         * t_name : 张老师
         * create_at : 2017-12-08
         * day_week : 星期五
         */

        private int id;
        private String video_name;
        private String video_type;
        private int type;
        private String name;
        private String video_time;
        private String imgurl;
        private int rss_count_true;
        private int collect_count_true;
        private int view_count_true;
        private int rss_count;
        private int collect_count;
        private int view_count;
        private int is_rss;
        private int is_collect;
        private int is_view;
        private String content;
        private String t_header;
        private String t_name;
        private String create_at;
        private String day_week;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVideo_name() {
            return video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public String getVideo_type() {
            return video_type;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVideo_time() {
            return video_time;
        }

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public int getRss_count_true() {
            return rss_count_true;
        }

        public void setRss_count_true(int rss_count_true) {
            this.rss_count_true = rss_count_true;
        }

        public int getCollect_count_true() {
            return collect_count_true;
        }

        public void setCollect_count_true(int collect_count_true) {
            this.collect_count_true = collect_count_true;
        }

        public int getView_count_true() {
            return view_count_true;
        }

        public void setView_count_true(int view_count_true) {
            this.view_count_true = view_count_true;
        }

        public int getRss_count() {
            return rss_count;
        }

        public void setRss_count(int rss_count) {
            this.rss_count = rss_count;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public int getView_count() {
            return view_count;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
        }

        public int getIs_rss() {
            return is_rss;
        }

        public void setIs_rss(int is_rss) {
            this.is_rss = is_rss;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public int getIs_view() {
            return is_view;
        }

        public void setIs_view(int is_view) {
            this.is_view = is_view;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getT_header() {
            return t_header;
        }

        public void setT_header(String t_header) {
            this.t_header = t_header;
        }

        public String getT_name() {
            return t_name;
        }

        public void setT_name(String t_name) {
            this.t_name = t_name;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getDay_week() {
            return day_week;
        }

        public void setDay_week(String day_week) {
            this.day_week = day_week;
        }
    }
}
