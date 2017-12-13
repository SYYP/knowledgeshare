package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/13.
 */

public class LoginBean extends BaseBean implements Serializable {

    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6Ly90aGlua3MuaWFzay5pbi9hcGkvdjIvdG9rZW5zIiwiaWF0IjoxNTEyMTE1MTUxLCJleHAiOjE1MTMzMjQ3NTEsIm5iZiI6MTUxMjExNTE1MSwianRpIjoiSVlwZG8xeWgydmd4Y1FMayJ9.EImYPdqDRAPN8g2veJ_jbKWLTDHlX0ZNBhkceyEo3SE
     * user : {"id":1,"user_name":"","user_mobile":"15250735030","user_avatar":"","wx_name":"","user_birthday":"0000-00-00","user_level":0,"user_age":0,"user_industry":"","user_sex":0,"user_education":"","is_lock":1,"lock_note":"","created_at":"2017-12-01 07:04:04","updated_at":"2017-12-01 07:04:04"}
     * ttl : 20160
     * refresh_ttl : 40320
     */

    private String token;
    private UserBean user;
    private Long ttl;
    private int refresh_ttl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public int getRefresh_ttl() {
        return refresh_ttl;
    }

    public void setRefresh_ttl(int refresh_ttl) {
        this.refresh_ttl = refresh_ttl;
    }

    public static class UserBean {
        /**
         * id : 1
         * user_name :
         * user_mobile : 15250735030
         * user_avatar :
         * wx_name :
         * user_birthday : 0000-00-00
         * user_level : 0
         * user_age : 0
         * user_industry :
         * user_sex : 0
         * user_education :
         * is_lock : 1
         * lock_note :
         * created_at : 2017-12-01 07:04:04
         * updated_at : 2017-12-01 07:04:04
         */

        private String id;
        private String user_name;
        private String user_mobile;
        private String user_avatar;
        private String wx_name;
        private String user_birthday;
        private int user_level;
        private int user_age;
        private String user_industry;
        private int user_sex;
        private String user_education;
        private int is_lock;
        private String lock_note;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getWx_name() {
            return wx_name;
        }

        public void setWx_name(String wx_name) {
            this.wx_name = wx_name;
        }

        public String getUser_birthday() {
            return user_birthday;
        }

        public void setUser_birthday(String user_birthday) {
            this.user_birthday = user_birthday;
        }

        public int getUser_level() {
            return user_level;
        }

        public void setUser_level(int user_level) {
            this.user_level = user_level;
        }

        public int getUser_age() {
            return user_age;
        }

        public void setUser_age(int user_age) {
            this.user_age = user_age;
        }

        public String getUser_industry() {
            return user_industry;
        }

        public void setUser_industry(String user_industry) {
            this.user_industry = user_industry;
        }

        public int getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(int user_sex) {
            this.user_sex = user_sex;
        }

        public String getUser_education() {
            return user_education;
        }

        public void setUser_education(String user_education) {
            this.user_education = user_education;
        }

        public int getIs_lock() {
            return is_lock;
        }

        public void setIs_lock(int is_lock) {
            this.is_lock = is_lock;
        }

        public String getLock_note() {
            return lock_note;
        }

        public void setLock_note(String lock_note) {
            this.lock_note = lock_note;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
