package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */

public class VideoCollectBean {

    /**
     * data : [{"islive":true,"isfav":false}]
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
         * islive : true
         * isfav : false
         */
        private boolean islive;
        private boolean isfav;

        public void setIslive(boolean islive) {
            this.islive = islive;
        }

        public void setIsfav(boolean isfav) {
            this.isfav = isfav;
        }

        public boolean isIslive() {
            return islive;
        }

        public boolean isIsfav() {
            return isfav;
        }
    }
}
