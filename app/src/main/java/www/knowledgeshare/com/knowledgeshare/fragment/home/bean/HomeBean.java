package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class HomeBean {

    /**
     * xiaoke : [{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1x1.png","teacher_name":"张老师","nodule_count":"节","xk_name":"如何成为一个合格的歌唱者1","buy_count":"人","xk_id":8,"xk_teacher_tags":"好声音","time_count":"00:00","xk_price":"￥180/年"},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1x1.png","teacher_name":"张老师","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者5","buy_count":"3人","xk_id":7,"xk_teacher_tags":"好声音","time_count":"07:02","xk_price":"￥180/年"},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1x1.png","teacher_name":"张老师","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者4","buy_count":"1人","xk_id":6,"xk_teacher_tags":"好声音","time_count":"07:02","xk_price":"￥180/年"},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1x1.png","teacher_name":"张老师","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者3","buy_count":"0人","xk_id":5,"xk_teacher_tags":"好声音","time_count":"07:02","xk_price":"￥180/年"}]
     * daily : [{"video_time":"01:40","good_count":0,"t_tag":"中国好声音","parent_name":"每日推荐","video_name":"女高音，一个神秘又充满魅力的声音3","teacher_id":1,"txt_url":"xiaoke/20171208/1.txt","is_view":0,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"is_collect":0,"good_count_true":0,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":0,"updated_at":"2017-12-08 08:35:30","t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","t_name":"张老师","id":3,"is_good":0,"txt_old_name":"女高音.txt","collect_count":0,"video_type":"daily","collect_count_true":0,"send_time":0,"view_count":0},{"video_time":"02:00","good_count":0,"t_tag":"中国好声音","parent_name":"每日推荐","video_name":"女高音，一个神秘又充满魅力的声音2","teacher_id":1,"txt_url":"xiaoke/20171208/1.txt","is_view":0,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"is_collect":0,"good_count_true":2,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music2.mp3","view_count_true":0,"updated_at":"2017-12-17 05:47:18","t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","t_name":"张老师","id":2,"is_good":0,"txt_old_name":"女高音.txt","collect_count":0,"video_type":"daily","collect_count_true":1,"send_time":0,"view_count":0},{"video_time":"01:40","good_count":0,"t_tag":"中国好声音","parent_name":"每日推荐","video_name":"女高音，一个神秘又充满魅力的声音1","teacher_id":1,"txt_url":"xiaoke/20171208/1.txt","is_view":0,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":true,"is_collect":0,"good_count_true":2,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":0,"updated_at":"2017-12-26 03:41:35","t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","t_name":"张老师","id":1,"is_good":0,"txt_old_name":"女高音.txt","collect_count":0,"video_type":"daily","collect_count_true":3,"send_time":0,"view_count":0}]
     * free : {"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/18x7.png","imgurl_1":"zhuanlan/20171208/1x1.png","t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg","teacher_id":1,"name":"歌唱家专栏","id":1,"child":[{"video_time":"01:40","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音3","is_good":1,"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"video_type":"free","collect_count_true":0,"is_collect":1,"good_count_true":1,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":82,"send_time":0,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","id":3,"view_count":0},{"video_time":"02:00","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音2","is_good":1,"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"video_type":"free","collect_count_true":2,"is_collect":1,"good_count_true":0,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music2.mp3","view_count_true":15,"send_time":0,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","id":2,"view_count":0},{"video_time":"01:40","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音1","is_good":1,"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":true,"collect_count":0,"video_type":"free","collect_count_true":2,"is_collect":1,"good_count_true":2,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":9,"send_time":0,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","id":1,"view_count":0}]}
     * zhuanlan : [{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"4天前更新","zl_name":"崔宗顺的男低音歌唱家的秘密2","id":6,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/17x12.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"},{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"21天前更新","zl_name":"崔宗顺的男低音歌唱家的秘密5","id":5,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/17x12.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"},{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"21天前更新","zl_name":"崔宗顺的男低音歌唱家的秘密4","id":4,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/17x12.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"},{"zl_price":"￥160/年","zl_update_name":"专栏小节，如何成为男高音2","zl_update_time":"21天前更新","zl_name":"崔宗顺的男低音歌唱家的秘密3","id":3,"zl_img":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/17x12.png","zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。"}]
     * live : [{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/5x7.png","xk_name":"如何成为一个合格的歌唱者5","xk_id":7},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/5x7.png","xk_name":"如何成为一个合格的歌唱者4","xk_id":6},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/5x7.png","xk_name":"如何成为一个合格的歌唱者1","xk_id":8}]
     */
    private List<XiaokeEntity> xiaoke;
    private List<DailyEntity> daily;
    private FreeEntity free;
    private List<ZhuanlanEntity> zhuanlan;
    private List<LiveEntity> live;

    public void setXiaoke(List<XiaokeEntity> xiaoke) {
        this.xiaoke = xiaoke;
    }

    public void setDaily(List<DailyEntity> daily) {
        this.daily = daily;
    }

    public void setFree(FreeEntity free) {
        this.free = free;
    }

    public void setZhuanlan(List<ZhuanlanEntity> zhuanlan) {
        this.zhuanlan = zhuanlan;
    }

    public void setLive(List<LiveEntity> live) {
        this.live = live;
    }

    public List<XiaokeEntity> getXiaoke() {
        return xiaoke;
    }

    public List<DailyEntity> getDaily() {
        return daily;
    }

    public FreeEntity getFree() {
        return free;
    }

    public List<ZhuanlanEntity> getZhuanlan() {
        return zhuanlan;
    }

    public List<LiveEntity> getLive() {
        return live;
    }

    public static class XiaokeEntity {
        /**
         * xk_image : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1x1.png
         * teacher_name : 张老师
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

    public static class DailyEntity {
        /**
         * video_time : 01:40
         * good_count : 0
         * t_tag : 中国好声音
         * parent_name : 每日推荐
         * video_name : 女高音，一个神秘又充满魅力的声音3
         * teacher_id : 1
         * txt_url : xiaoke/20171208/1.txt
         * is_view : 0
         * created_at : 2017-12-08 08:35:30
         * video_old_name : 女高音.mp3
         * isfav : false
         * is_collect : 0
         * good_count_true : 0
         * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
         * view_count_true : 0
         * updated_at : 2017-12-08 08:35:30
         * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png
         * t_name : 张老师
         * id : 3
         * is_good : 0
         * txt_old_name : 女高音.txt
         * collect_count : 0
         * video_type : daily
         * collect_count_true : 0
         * send_time : 0
         * view_count : 0
         */
        private String video_time;
        private int good_count;
        private String t_tag;
        private String parent_name;
        private String video_name;
        private int teacher_id;
        private String txt_url;
        private int is_view;
        private String created_at;
        private String video_old_name;
        private boolean isfav;
        private int is_collect;
        private int good_count_true;
        private String video_url;
        private int view_count_true;
        private String updated_at;
        private String t_header;
        private String t_name;
        private int id;
        private int is_good;
        private String txt_old_name;
        private int collect_count;
        private String video_type;
        private int collect_count_true;
        private int send_time;
        private int view_count;
        private boolean isChecked;
        private String share_h5_url;

        public String getShare_h5_url() {
            return share_h5_url;
        }

        public void setShare_h5_url(String share_h5_url) {
            this.share_h5_url = share_h5_url;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

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

        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
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

        public int getTeacher_id() {
            return teacher_id;
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

    public static class FreeEntity {
        /**
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/18x7.png
         * imgurl_1 : zhuanlan/20171208/1x1.png
         * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg
         * teacher_id : 1
         * name : 歌唱家专栏
         * id : 1
         * child : [{"video_time":"01:40","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音3","is_good":1,"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"video_type":"free","collect_count_true":0,"is_collect":1,"good_count_true":1,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":82,"send_time":0,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","id":3,"view_count":0},{"video_time":"02:00","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音2","is_good":1,"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":false,"collect_count":0,"video_type":"free","collect_count_true":2,"is_collect":1,"good_count_true":0,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music2.mp3","view_count_true":15,"send_time":0,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","id":2,"view_count":0},{"video_time":"01:40","good_count":0,"t_tag":"","parent_name":"歌唱家专栏","video_name":"女高音，一个神秘又充满魅力的声音1","is_good":1,"txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","txt_old_name":"女高音.txt","is_view":1,"created_at":"2017-12-08 08:35:30","video_old_name":"女高音.mp3","isfav":true,"collect_count":0,"video_type":"free","collect_count_true":2,"is_collect":1,"good_count_true":2,"video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","view_count_true":9,"send_time":0,"t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","id":1,"view_count":0}]
         */
        private String imgurl;
        private String imgurl_1;
        private String t_header;
        private int teacher_id;
        private String name;
        private int id;
        private List<ChildEntity> child;

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public void setImgurl_1(String imgurl_1) {
            this.imgurl_1 = imgurl_1;
        }

        public void setT_header(String t_header) {
            this.t_header = t_header;
        }

        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setChild(List<ChildEntity> child) {
            this.child = child;
        }

        public String getImgurl() {
            return imgurl;
        }

        public String getImgurl_1() {
            return imgurl_1;
        }

        public String getT_header() {
            return t_header;
        }

        public int getTeacher_id() {
            return teacher_id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public List<ChildEntity> getChild() {
            return child;
        }

        public static class ChildEntity {
            /**
             * video_time : 01:40
             * good_count : 0
             * t_tag :
             * parent_name : 歌唱家专栏
             * video_name : 女高音，一个神秘又充满魅力的声音3
             * is_good : 1
             * txt_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt
             * txt_old_name : 女高音.txt
             * is_view : 1
             * created_at : 2017-12-08 08:35:30
             * video_old_name : 女高音.mp3
             * isfav : false
             * collect_count : 0
             * video_type : free
             * collect_count_true : 0
             * is_collect : 1
             * good_count_true : 1
             * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
             * view_count_true : 82
             * send_time : 0
             * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png
             * id : 3
             * view_count : 0
             */
            private String video_time;
            private int good_count;
            private String t_tag;
            private String parent_name;
            private String video_name;
            private int is_good;
            private String txt_url;
            private String txt_old_name;
            private int is_view;
            private String created_at;
            private String video_old_name;
            private boolean isfav;
            private int collect_count;
            private String video_type;
            private int collect_count_true;
            private int is_collect;
            private int good_count_true;
            private String video_url;
            private int view_count_true;
            private int send_time;
            private String t_header;
            private int id;
            private int view_count;
            private boolean isChecked;
            private String share_h5_url;

            public String getShare_h5_url() {
                return share_h5_url;
            }

            public void setShare_h5_url(String share_h5_url) {
                this.share_h5_url = share_h5_url;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

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

            public void setVideo_type(String video_type) {
                this.video_type = video_type;
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

            public void setT_header(String t_header) {
                this.t_header = t_header;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getVideo_type() {
                return video_type;
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

            public String getT_header() {
                return t_header;
            }

            public int getId() {
                return id;
            }

            public int getView_count() {
                return view_count;
            }
        }
    }

    public static class ZhuanlanEntity {
        /**
         * zl_price : ￥160/年
         * zl_update_name : 专栏小节，如何成为男高音2
         * zl_update_time : 4天前更新
         * zl_name : 崔宗顺的男低音歌唱家的秘密2
         * id : 6
         * zl_img : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/17x12.png
         * zl_introduce : 韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。
         */
        private String zl_price;
        private String zl_update_name;
        private String zl_update_time;
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

    public static class LiveEntity {
        /**
         * xk_image : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/5x7.png
         * xk_name : 如何成为一个合格的歌唱者5
         * xk_id : 7
         */
        private String xk_image;
        private String xk_name;
        private int xk_id;

        public void setXk_image(String xk_image) {
            this.xk_image = xk_image;
        }

        public void setXk_name(String xk_name) {
            this.xk_name = xk_name;
        }

        public void setXk_id(int xk_id) {
            this.xk_id = xk_id;
        }

        public String getXk_image() {
            return xk_image;
        }

        public String getXk_name() {
            return xk_name;
        }

        public int getXk_id() {
            return xk_id;
        }
    }
}
