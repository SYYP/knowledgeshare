package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class DownBean3 {

    /**
     * data : [{"t_name":"张老师","count":2,"zl_name":"崔宗顺的男低音歌唱家的秘密1","zl_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png","zl_data":{"video_time":"02:20","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","t_name":"张老师","txt_url":null,"name":"专栏小节，如何成为男高音1","id":1},"id":1,"zl_introduce":"韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。","zl_teacher_tags":"美国音乐艺术家","video_type":"zl"}]
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
         * t_name : 张老师
         * count : 2
         * zl_name : 崔宗顺的男低音歌唱家的秘密1
         * zl_image : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/3x4.png
         * zl_data : {"video_time":"02:20","video_url":"http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3","t_name":"张老师","txt_url":null,"name":"专栏小节，如何成为男高音1","id":1}
         * id : 1
         * zl_introduce : 韩宗顺美国音乐艺术家协会会员，有着丰富的音乐经验，音乐带头人。
         * zl_teacher_tags : 美国音乐艺术家
         * video_type : zl
         */
        private String t_name;
        private int count;
        private String zl_name;
        private String zl_image;
        private ZlDataEntity zl_data;
        private int id;
        private String zl_introduce;
        private String zl_teacher_tags;
        private String video_type;

        public void setT_name(String t_name) {
            this.t_name = t_name;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setZl_name(String zl_name) {
            this.zl_name = zl_name;
        }

        public void setZl_image(String zl_image) {
            this.zl_image = zl_image;
        }

        public void setZl_data(ZlDataEntity zl_data) {
            this.zl_data = zl_data;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setZl_introduce(String zl_introduce) {
            this.zl_introduce = zl_introduce;
        }

        public void setZl_teacher_tags(String zl_teacher_tags) {
            this.zl_teacher_tags = zl_teacher_tags;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public String getT_name() {
            return t_name;
        }

        public int getCount() {
            return count;
        }

        public String getZl_name() {
            return zl_name;
        }

        public String getZl_image() {
            return zl_image;
        }

        public ZlDataEntity getZl_data() {
            return zl_data;
        }

        public int getId() {
            return id;
        }

        public String getZl_introduce() {
            return zl_introduce;
        }

        public String getZl_teacher_tags() {
            return zl_teacher_tags;
        }

        public String getVideo_type() {
            return video_type;
        }

        public static class ZlDataEntity {
            /**
             * video_time : 02:20
             * video_url : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/music1.mp3
             * t_name : 张老师
             * txt_url : null
             * name : 专栏小节，如何成为男高音1
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
