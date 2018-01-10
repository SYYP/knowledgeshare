package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class DownBean2 {

    /**
     * data : [{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","t_name":"张老师","xk_name":"如何成为一个合格的歌唱者1","count":2,"id":3,"xk_teacher_tags":"好声音","xk_data":{"video_time":"02:20","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/music1.mp3","t_name":"张老师","txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","name":"男低音，如何通过技巧正确的发音，提高男低音的魅力","id":1},"video_type":"xk"}]
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
         * xk_image : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png
         * t_name : 张老师
         * xk_name : 如何成为一个合格的歌唱者1
         * count : 2
         * id : 3
         * xk_teacher_tags : 好声音
         * xk_data : {"video_time":"02:20","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/music1.mp3","t_name":"张老师","txt_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt","name":"男低音，如何通过技巧正确的发音，提高男低音的魅力","id":1}
         * video_type : xk
         */
        private String xk_image;
        private String t_name;
        private String xk_name;
        private int count;
        private int id;
        private String xk_teacher_tags;
        private XkDataEntity xk_data;
        private String video_type;

        public void setXk_image(String xk_image) {
            this.xk_image = xk_image;
        }

        public void setT_name(String t_name) {
            this.t_name = t_name;
        }

        public void setXk_name(String xk_name) {
            this.xk_name = xk_name;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setXk_teacher_tags(String xk_teacher_tags) {
            this.xk_teacher_tags = xk_teacher_tags;
        }

        public void setXk_data(XkDataEntity xk_data) {
            this.xk_data = xk_data;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public String getXk_image() {
            return xk_image;
        }

        public String getT_name() {
            return t_name;
        }

        public String getXk_name() {
            return xk_name;
        }

        public int getCount() {
            return count;
        }

        public int getId() {
            return id;
        }

        public String getXk_teacher_tags() {
            return xk_teacher_tags;
        }

        public XkDataEntity getXk_data() {
            return xk_data;
        }

        public String getVideo_type() {
            return video_type;
        }

        public static class XkDataEntity {
            /**
             * video_time : 02:20
             * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/music1.mp3
             * t_name : 张老师
             * txt_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/1.txt
             * name : 男低音，如何通过技巧正确的发音，提高男低音的魅力
             * id : 1
             */
            private String video_time;
            private String video_url;
            private String t_name;
            private String txt_url;
            private String name;
            private int id;

            public void setVideo_time(String video_time) {
                this.video_time = video_time;
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

            public void setName(String name) {
                this.name = name;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getVideo_time() {
                return video_time;
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

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }
        }
    }
}
