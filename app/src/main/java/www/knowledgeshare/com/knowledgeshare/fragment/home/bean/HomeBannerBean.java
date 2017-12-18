package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class HomeBannerBean {

    /**
     * app_version : {"notes":"","type":"android","version":"1.0"}
     * homeslide : [{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/banner/20171208/banner.png","course_id":1,"name":"和催宗顺一起","link":"","id":1,"sort":0,"state":1,"type":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/banner/20171208/banner.png","course_id":1,"name":"和催宗顺一起","link":"","id":2,"sort":0,"state":1,"type":1}]
     * ctivity : {"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/activity/146601780027000559.png","course_id":0,"name":"活动","link":"","created_at":"2017-12-13 03:05:05","id":1,"state":1,"type":0}
     * guide : [{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading1.png","description":"引导","created_at":"2017-12-07 04:15:31","id":1,"sort":0,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading2.png","description":"引导","created_at":"2017-12-07 04:15:31","id":2,"sort":0,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading3.png","description":"引导","created_at":"2017-12-07 04:15:31","id":3,"sort":0,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading4.png","description":"引导","created_at":"2017-12-07 04:15:31","id":4,"sort":0,"state":1},{"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading5.png","description":"引导","created_at":"2017-12-07 04:15:31","id":5,"sort":0,"state":1}]
     */
    private AppVersionEntity app_version;
    private List<HomeslideEntity> homeslide;
    private CtivityEntity ctivity;
    private List<GuideEntity> guide;

    public void setApp_version(AppVersionEntity app_version) {
        this.app_version = app_version;
    }

    public void setHomeslide(List<HomeslideEntity> homeslide) {
        this.homeslide = homeslide;
    }

    public void setCtivity(CtivityEntity ctivity) {
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

    public CtivityEntity getCtivity() {
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
         * course_id : 1
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
         * name : 活动
         * link :
         * created_at : 2017-12-13 03:05:05
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
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading1.png
         * description : 引导
         * created_at : 2017-12-07 04:15:31
         * id : 1
         * sort : 0
         * state : 1
         */
        private String imgurl;
        private String description;
        private String created_at;
        private int id;
        private int sort;
        private int state;

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
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

        public String getDescription() {
            return description;
        }

        public String getCreated_at() {
            return created_at;
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
