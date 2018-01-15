package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class HomeBannerBean {


    /**
     * app_version : {"notes":"","type":"android","version":"1.0"}
     * homeslide : [{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/banner/20171208/banner.png","course_id":3,"name":"和催宗顺一起","link":"","id":1,"sort":0,"state":1,"type":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/banner/20171208/banner.png","course_id":3,"name":"和催宗顺一起","link":"","id":2,"sort":0,"state":1,"type":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/homeslide/20180112/1856.png","course_id":1,"name":"78946132","link":" ","id":4,"sort":0,"state":1,"type":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/homeslide/20180112/T1L8K_Bgd_1RCvBVdK.jpg","course_id":3,"name":"阿萨德","link":"www.sohu.com","id":5,"sort":0,"state":1,"type":1}]
     * ctivity : [{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/activity/146601780027000559.png","course_id":0,"name":"活动1","link":"0","created_at":"2017-12-13 11:05:05","id":1,"state":1,"type":0}]
     * guide : [{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guide/20180111/1856.png","imgurl1":"","imgurl2":"","imgurl3":"","imgurl4":"","description":"2321321","created_at":"2018-01-11 19:35:51","imgurl5":"","id":6,"sort":11111,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guide/20180112/80_20171218212320484050_1.jpg","imgurl1":"","imgurl2":"","imgurl3":"","imgurl4":"","description":"dasd asdas dsasa sa a","created_at":"2018-01-13 01:44:09","imgurl5":"","id":7,"sort":1,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading1.png","imgurl1":"","imgurl2":"","imgurl3":"","imgurl4":"","description":"引导","created_at":"2017-12-07 12:15:31","imgurl5":"","id":1,"sort":0,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading2.png","imgurl1":"","imgurl2":"","imgurl3":"","imgurl4":"","description":"引导","created_at":"2017-12-07 12:15:31","imgurl5":"","id":2,"sort":0,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading3.png","imgurl1":"","imgurl2":"","imgurl3":"","imgurl4":"","description":"引导","created_at":"2017-12-07 12:15:31","imgurl5":"","id":3,"sort":0,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading4.png","imgurl1":"","imgurl2":"","imgurl3":"","imgurl4":"","description":"引导","created_at":"2017-12-07 12:15:31","imgurl5":"","id":4,"sort":0,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading5.png","imgurl1":"","imgurl2":"","imgurl3":"","imgurl4":"","description":"引导","created_at":"2017-12-07 12:15:31","imgurl5":"","id":5,"sort":0,"state":1}]
     */
    private AppVersionEntity app_version;
    private List<HomeslideEntity> homeslide;
    private List<CtivityEntity> ctivity;
    private List<GuideEntity> guide;

    public void setApp_version(AppVersionEntity app_version) {
        this.app_version = app_version;
    }

    public void setHomeslide(List<HomeslideEntity> homeslide) {
        this.homeslide = homeslide;
    }

    public void setCtivity(List<CtivityEntity> ctivity) {
        this.ctivity = ctivity;
    }

    public void setGuide(List<GuideEntity> guide) {
        this.guide = guide;
    }

    public AppVersionEntity getApp_version() {
        return app_version;
    }

    public List<HomeslideEntity> getHomeslide() {
        return homeslide;
    }

    public List<CtivityEntity> getCtivity() {
        return ctivity;
    }

    public List<GuideEntity> getGuide() {
        return guide;
    }

    public static class AppVersionEntity {
        /**
         * notes :
         * type : android
         * version : 1.0
         */
        private String notes;
        private String type;
        private String version;

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getNotes() {
            return notes;
        }

        public String getType() {
            return type;
        }

        public String getVersion() {
            return version;
        }
    }

    public static class HomeslideEntity {
        /**
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/banner/20171208/banner.png
         * course_id : 3
         * name : 和催宗顺一起
         * link :
         * id : 1
         * sort : 0
         * state : 1
         * type : 1
         */
        private String imgurl;
        private int course_id;
        private String name;
        private String link;
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

    public static class CtivityEntity {
        /**
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/activity/146601780027000559.png
         * course_id : 0
         * name : 活动1
         * link : 0
         * created_at : 2017-12-13 11:05:05
         * id : 1
         * state : 1
         * type : 0
         */
        private String imgurl;
        private int course_id;
        private String name;
        private String link;
        private String created_at;
        private int id;
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

        public int getState() {
            return state;
        }

        public int getType() {
            return type;
        }
    }

    public static class GuideEntity {
        /**
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/guide/20180111/1856.png
         * imgurl1 :
         * imgurl2 :
         * imgurl3 :
         * imgurl4 :
         * description : 2321321
         * created_at : 2018-01-11 19:35:51
         * imgurl5 :
         * id : 6
         * sort : 11111
         * state : 1
         */
        private String imgurl;
        private String imgurl1;
        private String imgurl2;
        private String imgurl3;
        private String imgurl4;
        private String description;
        private String created_at;
        private String imgurl5;
        private int id;
        private int sort;
        private int state;

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public void setImgurl1(String imgurl1) {
            this.imgurl1 = imgurl1;
        }

        public void setImgurl2(String imgurl2) {
            this.imgurl2 = imgurl2;
        }

        public void setImgurl3(String imgurl3) {
            this.imgurl3 = imgurl3;
        }

        public void setImgurl4(String imgurl4) {
            this.imgurl4 = imgurl4;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setImgurl5(String imgurl5) {
            this.imgurl5 = imgurl5;
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

        public String getImgurl() {
            return imgurl;
        }

        public String getImgurl1() {
            return imgurl1;
        }

        public String getImgurl2() {
            return imgurl2;
        }

        public String getImgurl3() {
            return imgurl3;
        }

        public String getImgurl4() {
            return imgurl4;
        }

        public String getDescription() {
            return description;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getImgurl5() {
            return imgurl5;
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
    }
}
