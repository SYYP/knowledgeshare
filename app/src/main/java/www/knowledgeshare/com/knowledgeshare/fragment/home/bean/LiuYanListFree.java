package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class LiuYanListFree {

    /**
     * data : [{"user_id":1,"teacher_id":1,"comment_reply":{"id":1,"comment_id":1,"content":"测试回复"},"created_at":"2017-12-11 09:13:46","id":1,"content":"测试评论","live":3}]
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
         * user_id : 1
         * teacher_id : 1
         * comment_reply : {"id":1,"comment_id":1,"content":"测试回复"}
         * created_at : 2017-12-11 09:13:46
         * id : 1
         * content : 测试评论
         * live : 3
         */
        private int user_id;
        private int teacher_id;
        private CommentReplyEntity comment_reply;
        private String created_at;
        private int id;
        private String content;
        private int live;

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
        }

        public void setComment_reply(CommentReplyEntity comment_reply) {
            this.comment_reply = comment_reply;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
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

        public int getUser_id() {
            return user_id;
        }

        public int getTeacher_id() {
            return teacher_id;
        }

        public CommentReplyEntity getComment_reply() {
            return comment_reply;
        }

        public String getCreated_at() {
            return created_at;
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

        public static class CommentReplyEntity {
            /**
             * id : 1
             * comment_id : 1
             * content : 测试回复
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
    }
}
