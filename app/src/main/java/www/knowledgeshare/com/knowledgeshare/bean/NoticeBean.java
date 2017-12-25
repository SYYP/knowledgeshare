package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/25.
 */

public class NoticeBean extends BaseBean implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * zl_id : 1
         * id : 1
         * name : 专栏小节，如何成为男高音1
         * imgurl : http://knowledges.oss-cn-qingdao.aliyuncs.com/zhuanlan/20171208/18x7.png
         * description : 专栏小节介绍，男高音占有十分重要的地位，第一男主角多为男高音歌唱家扮演。当阉人歌手还霸占歌剧舞台时，一位男歌手以真正的声音唱出高音C
         * created_at : 2017-12-08 08:37:36
         */

        private int zl_id;
        private int id;
        private String name;
        private String imgurl;
        private String description;
        private String created_at;

        public int getZl_id() {
            return zl_id;
        }

        public void setZl_id(int zl_id) {
            this.zl_id = zl_id;
        }

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
