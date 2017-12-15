package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class SoftMusicMoreBean {

    /**
     * data : [{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","teacher_name":"张老师","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者5","buy_count":"0人","xk_id":7,"xk_teacher_tags":"好声音","time_count":"0:7:2","xk_price":"￥180/年"},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","teacher_name":"张老师","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者4","buy_count":"0人","xk_id":6,"xk_teacher_tags":"好声音","time_count":"0:7:2","xk_price":"￥180/年"},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","teacher_name":"张老师","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者3","buy_count":"0人","xk_id":5,"xk_teacher_tags":"好声音","time_count":"0:7:2","xk_price":"￥180/年"},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","teacher_name":"张老师","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者2","buy_count":"0人","xk_id":4,"xk_teacher_tags":"好声音","time_count":"0:7:2","xk_price":"￥180/年"},{"xk_image":"http://knowledges.oss-cn-qingdao.aliyuncs.com/xiaoke/20171208/3x4.png","teacher_name":"张老师","nodule_count":"2节","xk_name":"如何成为一个合格的歌唱者1","buy_count":"0人","xk_id":3,"xk_teacher_tags":"好声音","time_count":"0:7:2","xk_price":"￥180/年"}]
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
         * teacher_name : 张老师
         * nodule_count : 2节
         * xk_name : 如何成为一个合格的歌唱者5
         * buy_count : 0人
         * xk_id : 7
         * xk_teacher_tags : 好声音
         * time_count : 0:7:2
         * xk_price : ￥180/年
         */
        private String xk_image;
        private String teacher_name;
        private String nodule_count;
        private String xk_name;
        private String buy_count;
        private int xk_id;
        private String xk_teacher_tags;
        private String time_count;
        private String xk_price;

        public void setXk_image(String xk_image) {
            this.xk_image = xk_image;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public void setNodule_count(String nodule_count) {
            this.nodule_count = nodule_count;
        }

        public void setXk_name(String xk_name) {
            this.xk_name = xk_name;
        }

        public void setBuy_count(String buy_count) {
            this.buy_count = buy_count;
        }

        public void setXk_id(int xk_id) {
            this.xk_id = xk_id;
        }

        public void setXk_teacher_tags(String xk_teacher_tags) {
            this.xk_teacher_tags = xk_teacher_tags;
        }

        public void setTime_count(String time_count) {
            this.time_count = time_count;
        }

        public void setXk_price(String xk_price) {
            this.xk_price = xk_price;
        }

        public String getXk_image() {
            return xk_image;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public String getNodule_count() {
            return nodule_count;
        }

        public String getXk_name() {
            return xk_name;
        }

        public String getBuy_count() {
            return buy_count;
        }

        public int getXk_id() {
            return xk_id;
        }

        public String getXk_teacher_tags() {
            return xk_teacher_tags;
        }

        public String getTime_count() {
            return time_count;
        }

        public String getXk_price() {
            return xk_price;
        }
    }
}
