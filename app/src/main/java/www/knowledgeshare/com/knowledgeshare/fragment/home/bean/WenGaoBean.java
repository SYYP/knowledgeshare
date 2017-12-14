package www.knowledgeshare.com.knowledgeshare.fragment.home.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */

public class WenGaoBean {
    /**
     * comment : [{"user_avatar":"","islive":false,"user_id":1,"teacher_id":0,"user_name":"123","created_at":"2017-12-13 06:37:32","comment":[],"id":3,"content":"文稿评论","live":0}]
     * isfav : false
     * content :
     */
    private List<CommentEntity> comment;
    private boolean isfav;
    private String content;

    public void setComment(List<CommentEntity> comment) {
        this.comment = comment;
    }

    public void setIsfav(boolean isfav) {
        this.isfav = isfav;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentEntity> getComment() {
        return comment;
    }

    public boolean isIsfav() {
        return isfav;
    }

    public String getContent() {
        return content;
    }

    public static class CommentEntity {
        /**
         * user_avatar :
         * islive : false
         * user_id : 1
         * teacher_id : 0
         * user_name : 123
         * created_at : 2017-12-13 06:37:32
         * comment : []
         * id : 3
         * content : 文稿评论
         * live : 0
         */
        private String user_avatar;
        private boolean islive;
        private int user_id;
        private int teacher_id;
        private String user_name;
        private String created_at;
        private List<?> comment;
        private int id;
        private String content;
        private int live;

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

        public void setComment(List<?> comment) {
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

        public List<?> getComment() {
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
