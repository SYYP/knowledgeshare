package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class DownBean1 {

    /**
     * data : [{"video_time":"01:40","video_name":"女高音，一个神秘又充满魅力的声音1222","image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","t_name":"张老师","txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","count":4,"id":1,"video_type":"free"}]
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
         * video_time : 01:40
         * video_name : 女高音，一个神秘又充满魅力的声音1222
         * image : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/1x1.png
         * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
         * t_name : 张老师
         * txt_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt
         * count : 4
         * id : 1
         * video_type : free
         */
        private String video_time;
        private String video_name;
        private String image;
        private String video_url;
        private String t_name;
        private String txt_url;
        private int count;
        private int id;
        private String video_type;

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public void setT_name(String t_name) {
            this.t_name = t_name;
        }

        public void setTxt_url(String txt_url) {
            this.txt_url = txt_url;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public String getVideo_time() {
            return video_time;
        }

        public String getVideo_name() {
            return video_name;
        }

        public String getImage() {
            return image;
        }

        public String getVideo_url() {
            return video_url;
        }

        public String getT_name() {
            return t_name;
        }

        public String getTxt_url() {
            return txt_url;
        }

        public int getCount() {
            return count;
        }

        public int getId() {
            return id;
        }

        public String getVideo_type() {
            return video_type;
        }
    }
}
