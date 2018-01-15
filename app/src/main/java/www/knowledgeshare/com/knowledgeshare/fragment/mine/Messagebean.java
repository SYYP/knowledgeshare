package www.knowledgeshare.com.knowledgeshare.fragment.mine;

import java.util.List;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Messagebean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * created_at : 2017-12-26 08:36:40
         * content : 每天首次评论获得10点
         */
        private String notid;
        private String created_at;
        private String content;
        private boolean aBoolean;

        public String getNotid() {
            return notid;
        }

        public void setNotid(String notid) {
            this.notid = notid;
        }

        public boolean isaBoolean() {
            return aBoolean;
        }

        public void setaBoolean(boolean aBoolean) {
            this.aBoolean = aBoolean;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
