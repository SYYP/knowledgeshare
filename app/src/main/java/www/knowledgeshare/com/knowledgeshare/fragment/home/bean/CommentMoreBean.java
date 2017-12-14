package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class CommentMoreBean {

    /**
     * data : [{"user_avatar":"","islive":false,"user_id":1,"teacher_id":1,"user_name":"123","created_at":"2017-12-12 03:17:39","comment":[],"id":2,"content":"测试评论","live":0},{"user_avatar":"","islive":true,"user_id":1,"teacher_id":1,"user_name":"123","created_at":"2017-12-11 09:13:46","comment":[],"id":1,"content":"测试评论","live":2}]
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
         * user_avatar :
         * islive : false
         * user_id : 1
         * teacher_id : 1
         * user_name : 123
         * created_at : 2017-12-12 03:17:39
         * comment : []
         * id : 2
         * content : 测试评论
         * live : 0
         */
        private String user_avatar;
        private boolean islive;
        private int user_id;
        private int teacher_id;
        private String user_name;
        private String created_at;
        private List<ChildCommentEntity> comment;
        private int id;
        private String content;
        private int live;

        public static class ChildCommentEntity {
            /**
             * id : 2
             * comment_id : 6
             * content : 测试回复666
             */
            private int id;
            private int comment_id;
            private String content;

            public void setId(int id) {
                this.id = id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getId() {
                return id;
            }

            public int getComment_id() {
                return comment_id;
            }

            public String getContent() {
                return content;
            }
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public void setIslive(boolean islive) {
            this.islive = islive;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setComment(List<ChildCommentEntity> comment) {
            this.comment = comment;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setLive(int live) {
            this.live = live;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public boolean isIslive() {
            return islive;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getTeacher_id() {
            return teacher_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public List<ChildCommentEntity> getComment() {
            return comment;
        }

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public int getLive() {
            return live;
        }
    }
}
