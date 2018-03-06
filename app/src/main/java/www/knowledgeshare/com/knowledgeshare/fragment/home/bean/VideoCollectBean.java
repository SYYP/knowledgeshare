package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */

public class VideoCollectBean {

    /**
     * data : [{"islive":false,"share_h5_url":"http://mop.xfkeji.cn/player.html?id=18&type=free","isfav":false}]
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
         * islive : false
         * share_h5_url : http://mop.xfkeji.cn/player.html?id=18&type=free
         * isfav : false
         */
        private boolean islive;
        private String share_h5_url;
        private boolean isfav;

        public void setIslive(boolean islive) {
            this.islive = islive;
        }

        public void setShare_h5_url(String share_h5_url) {
            this.share_h5_url = share_h5_url;
        }

        public void setIsfav(boolean isfav) {
            this.isfav = isfav;
        }

        public boolean isIslive() {
            return islive;
        }

        public String getShare_h5_url() {
            return share_h5_url;
        }

        public boolean isIsfav() {
            return isfav;
        }
    }
}
