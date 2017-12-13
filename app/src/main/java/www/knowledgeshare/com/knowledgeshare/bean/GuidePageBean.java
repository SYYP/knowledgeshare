package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class GuidePageBean extends BaseBean implements Serializable {

    /**
     * guide : [{"id":1,"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading1.png","description":"引导","sort":0,"state":1,"created_at":"2017-12-07 04:15:31"},{"id":2,"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading2.png","description":"引导","sort":0,"state":1,"created_at":"2017-12-07 04:15:31"},{"id":3,"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading3.png","description":"引导","sort":0,"state":1,"created_at":"2017-12-07 04:15:31"},{"id":4,"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading4.png","description":"引导","sort":0,"state":1,"created_at":"2017-12-07 04:15:31"},{"id":5,"imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading5.png","description":"引导","sort":0,"state":1,"created_at":"2017-12-07 04:15:31"}]
     * ctivity : {"id":1,"imgurl":"activity/146601780027000559.png","name":"活动","link":"","type":0,"course_id":0,"state":1,"created_at":"2017-12-13 03:05:05"}
     * homeslide : [{"id":1,"name":"和催宗顺一起","imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/banner/20171208/banner.png","link":"","type":1,"course_id":1,"sort":0,"state":1},{"id":2,"name":"和催宗顺一起","imgurl":"http://knowledges.oss-cn-qingdao.aliyuncs.com/banner/20171208/banner.png","link":"","type":1,"course_id":1,"sort":0,"state":1}]
     * app_version : {"version":"1.0","type":"android","notes":""}
     */

    private CtivityBean ctivity;
    private AppVersionBean app_version;
    private List<GuideBean> guide;
    private List<HomeslideBean> homeslide;

    public CtivityBean getCtivity() {
        return ctivity;
    }

    public void setCtivity(CtivityBean ctivity) {
        this.ctivity = ctivity;
    }

    public AppVersionBean getApp_version() {
        return app_version;
    }

    public void setApp_version(AppVersionBean app_version) {
        this.app_version = app_version;
    }

    public List<GuideBean> getGuide() {
        return guide;
    }

    public void setGuide(List<GuideBean> guide) {
        this.guide = guide;
    }

    public List<HomeslideBean> getHomeslide() {
        return homeslide;
    }

    public void setHomeslide(List<HomeslideBean> homeslide) {
        this.homeslide = homeslide;
    }

    public static class CtivityBean {
        /**
         * id : 1
         * imgurl : activity/146601780027000559.png
         * name : 活动
         * link :
         * type : 0
         * course_id : 0
         * state : 1
         * created_at : 2017-12-13 03:05:05
         */

        private int id;
        private String imgurl;
        private String name;
        private String link;
        private int type;
        private int course_id;
        private int state;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

    public static class AppVersionBean {
        /**
         * version : 1.0
         * type : android
         * notes :
         */

        private String version;
        private String type;
        private String notes;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }

    public static class GuideBean {
        /**
         * id : 1
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/guides/leading1.png
         * description : 引导
         * sort : 0
         * state : 1
         * created_at : 2017-12-07 04:15:31
         */

        private int id;
        private String imgurl;
        private String description;
        private int sort;
        private int state;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

    public static class HomeslideBean {
        /**
         * id : 1
         * name : 和催宗顺一起
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/banner/20171208/banner.png
         * link :
         * type : 1
         * course_id : 1
         * sort : 0
         * state : 1
         */

        private int id;
        private String name;
        private String imgurl;
        private String link;
        private int type;
        private int course_id;
        private int sort;
        private int state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
