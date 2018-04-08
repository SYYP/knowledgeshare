package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by lxk on 2017/12/17.
 */

public class FreeTryReadListBean {


    /**
     * data : [{"is_view":1,"description":"《床边故事》是由周杰伦作曲，方文山作词，黄雨勋编曲，周杰伦演唱的一首歌曲","collect_count":10,"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20180404/17：8详情页.png","collect_count_true":1,"is_collect":1,"rss_count":"2018-04-04","rss_count_true":"2018-04-04","view_count_true":20,"is_rss":1,"name":"床边故事","id":2,"view_count":10},{"is_view":1,"description":"《说走就走》是收录在2016年发行的音乐专辑 《周杰伦的床边故事》中的一首摇滚rap歌曲，由方文山填词、黄雨勋谱曲、周杰伦作曲演唱。","collect_count":0,"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20180404/17：8.png","collect_count_true":0,"is_collect":1,"rss_count":"2018-04-04","rss_count_true":"2018-04-04","view_count_true":4,"is_rss":1,"name":"说走就走","id":3,"view_count":0}]
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
         * is_view : 1
         * description : 《床边故事》是由周杰伦作曲，方文山作词，黄雨勋编曲，周杰伦演唱的一首歌曲
         * collect_count : 10
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20180404/17：8详情页.png
         * collect_count_true : 1
         * is_collect : 1
         * rss_count : 2018-04-04
         * rss_count_true : 2018-04-04
         * view_count_true : 20
         * is_rss : 1
         * name : 床边故事
         * id : 2
         * view_count : 10
         */
        private int is_view;
        private String description;
        private int collect_count;
        private String imgurl;
        private int collect_count_true;
        private int is_collect;
        private String rss_count;
        private String rss_count_true;
        private int view_count_true;
        private int is_rss;
        private String name;
        private int id;
        private int view_count;

        public void setIs_view(int is_view) {
            this.is_view = is_view;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public void setCollect_count_true(int collect_count_true) {
            this.collect_count_true = collect_count_true;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public void setRss_count(String rss_count) {
            this.rss_count = rss_count;
        }

        public void setRss_count_true(String rss_count_true) {
            this.rss_count_true = rss_count_true;
        }

        public void setView_count_true(int view_count_true) {
            this.view_count_true = view_count_true;
        }

        public void setIs_rss(int is_rss) {
            this.is_rss = is_rss;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
        }

        public int getIs_view() {
            return is_view;
        }

        public String getDescription() {
            return description;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public String getImgurl() {
            return imgurl;
        }

        public int getCollect_count_true() {
            return collect_count_true;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public String getRss_count() {
            return rss_count;
        }

        public String getRss_count_true() {
            return rss_count_true;
        }

        public int getView_count_true() {
            return view_count_true;
        }

        public int getIs_rss() {
            return is_rss;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public int getView_count() {
            return view_count;
        }
    }
}
