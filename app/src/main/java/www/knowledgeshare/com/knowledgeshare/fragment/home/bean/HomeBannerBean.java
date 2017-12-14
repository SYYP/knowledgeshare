package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class HomeBannerBean {

    /**
     * app_version : {"notes":"","type":"android","version":"1.0"}
     * ctivity : []
     * guide : [{"imgurl":"http://thinks.iask.in/storage/guides/leading1.png","description":"引导","created_at":"2017-12-07 04:15:31","id":1,"sort":0,"state":1},{"imgurl":"http://thinks.iask.in/storage/guides/leading2.png","description":"引导","created_at":"2017-12-07 04:15:31","id":2,"sort":0,"state":1},{"imgurl":"http://thinks.iask.in/storage/guides/leading3.png","description":"引导","created_at":"2017-12-07 04:15:31","id":3,"sort":0,"state":1},{"imgurl":"http://thinks.iask.in/storage/guides/leading4.png","description":"引导","created_at":"2017-12-07 04:15:31","id":4,"sort":0,"state":1},{"imgurl":"http://thinks.iask.in/storage/guides/leading5.png","description":"引导","created_at":"2017-12-07 04:15:31","id":5,"sort":0,"state":1}]
     */
    private AppVersionEntity app_version;
    private List<?> ctivity;
    private List<GuideEntity> guide;

    public void setApp_version(AppVersionEntity app_version) {
        this.app_version = app_version;
    }

    public void setCtivity(List<?> ctivity) {
        this.ctivity = ctivity;
    }

    public void setGuide(List<GuideEntity> guide) {
        this.guide = guide;
    }

    public AppVersionEntity getApp_version() {
        return app_version;
    }

    public List<?> getCtivity() {
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

    public static class GuideEntity {
        /**
         * imgurl : http://thinks.iask.in/storage/guides/leading1.png
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
