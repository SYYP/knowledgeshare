package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MyFollowBean {


    /**
     * data : [{"t_tag":"中国好声音","t_header":"http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg","t_name":"张老师","id":1}]
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
         * t_tag : 中国好声音
         * t_header : http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171208/1275610854404958.jpg
         * t_name : 张老师
         * id : 1
         */
        private String t_tag;
        private String t_header;
        private String t_name;
        private int id;

        public void setT_tag(String t_tag) {
            this.t_tag = t_tag;
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

        public String getT_tag() {
            return t_tag;
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
    }
}
