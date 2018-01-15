package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/13.
 */

public class LoginBean extends BaseBean implements Serializable {

    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjcsImlzcyI6Imh0dHA6Ly90aGlua3MuaWFzay5pbi9hcGkvdjIvdG9rZW5zIiwiaWF0IjoxNTE1NzQ0MDM1LCJleHAiOjE1MTY5NTM2MzUsIm5iZiI6MTUxNTc0NDAzNSwianRpIjoicWRzTnZSa3R0RUprc1dvUCJ9.GsyR9rMr2FeDsuXfxtyNUZSTS3RMwmpEmpwx963AA_A
     * user : {"id":7,"user_name":"power","user_mobile":"17611225393","user_avatar":"user/20180110/JYLOSmrhn2HXFB1ZvJam.jpg","wx_name":"","wx_unionid":"okQsr1f5YnS6wQPMJjM_fRSpw-AU","user_birthday":"1990-07-28","user_level":0,"user_integral":2176,"user_age":0,"user_industry":13,"user_sex":1,"user_education":5,"user_android_balance":"483.60","user_ios_balance":"1000.00","is_lock":1,"xk_notice_time":"2017-12-26 03:28:57","study_day":2}
     * ttl : 20160
     * refresh_ttl : 40320
     */

    private String token;
    private UserBean user;
    private int ttl;
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

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
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
         * id : 7
         * user_name : power
         * user_mobile : 17611225393
         * user_avatar : user/20180110/JYLOSmrhn2HXFB1ZvJam.jpg
         * wx_name :
         * wx_unionid : okQsr1f5YnS6wQPMJjM_fRSpw-AU
         * user_birthday : 1990-07-28
         * user_level : 0
         * user_integral : 2176
         * user_age : 0
         * user_industry : 13
         * user_sex : 1
         * user_education : 5
         * user_android_balance : 483.60
         * user_ios_balance : 1000.00
         * is_lock : 1
         * xk_notice_time : 2017-12-26 03:28:57
         * study_day : 2
         */

        private String id;
        private String user_name;
        private String user_mobile;
        private String user_avatar;
        private String wx_name;
        private String wx_unionid;
        private String user_birthday;
        private int user_level;
        private int user_integral;
        private int user_age;
        private int user_industry;
        private int user_sex;
        private int user_education;
        private String user_android_balance;
        private String user_ios_balance;
        private int is_lock;
        private String xk_notice_time;
        private int study_day;

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

        public String getWx_unionid() {
            return wx_unionid;
        }

        public void setWx_unionid(String wx_unionid) {
            this.wx_unionid = wx_unionid;
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

        public int getUser_integral() {
            return user_integral;
        }

        public void setUser_integral(int user_integral) {
            this.user_integral = user_integral;
        }

        public int getUser_age() {
            return user_age;
        }

        public void setUser_age(int user_age) {
            this.user_age = user_age;
        }

        public int getUser_industry() {
            return user_industry;
        }

        public void setUser_industry(int user_industry) {
            this.user_industry = user_industry;
        }

        public int getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(int user_sex) {
            this.user_sex = user_sex;
        }

        public int getUser_education() {
            return user_education;
        }

        public void setUser_education(int user_education) {
            this.user_education = user_education;
        }

        public String getUser_android_balance() {
            return user_android_balance;
        }

        public void setUser_android_balance(String user_android_balance) {
            this.user_android_balance = user_android_balance;
        }

        public String getUser_ios_balance() {
            return user_ios_balance;
        }

        public void setUser_ios_balance(String user_ios_balance) {
            this.user_ios_balance = user_ios_balance;
        }

        public int getIs_lock() {
            return is_lock;
        }

        public void setIs_lock(int is_lock) {
            this.is_lock = is_lock;
        }

        public String getXk_notice_time() {
            return xk_notice_time;
        }

        public void setXk_notice_time(String xk_notice_time) {
            this.xk_notice_time = xk_notice_time;
        }

        public int getStudy_day() {
            return study_day;
        }

        public void setStudy_day(int study_day) {
            this.study_day = study_day;
        }
    }
}
