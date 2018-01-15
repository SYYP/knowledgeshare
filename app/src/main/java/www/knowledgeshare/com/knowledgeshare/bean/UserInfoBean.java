package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class UserInfoBean extends BaseBean implements Serializable {

    /**
     * id : 7
     * user_name : power
     * user_mobile : 17611225393
     * user_avatar : http://knowledges.oss-cn-qingdao.aliyuncs.com/user/20171221/axEwN18ufaWomXzyXHZx.jpg
     * wx_name :
     * user_birthday : 1990-07-28
     * user_level : 0
     * user_integral : 0
     * user_age : 0
     * user_industry : 0
     * user_sex : 1
     * user_education : 5
     * user_android_balance : 660.00
     * user_ios_balance : 1000.00
     * is_lock : 1
     * level : [{"level_id":1,"level_name":"铜勋章","level_no_img":"","level_get_img":"","level_count":200,"level_discount":0,"level_voucher":3},{"level_id":2,"level_name":"银勋章","level_no_img":"","level_get_img":"","level_count":600,"level_discount":0,"level_voucher":8},{"level_id":3,"level_name":"金勋章","level_no_img":"","level_get_img":"","level_count":1500,"level_discount":98,"level_voucher":0},{"level_id":4,"level_name":"白金勋章","level_no_img":"","level_get_img":"","level_count":3000,"level_discount":95,"level_voucher":0},{"level_id":5,"level_name":"钻石勋章","level_no_img":"","level_get_img":"","level_count":5000,"level_discount":90,"level_voucher":0}]
     */

    private int id;
    private String user_name;
    private String user_mobile;
    private String user_avatar;
    private String wx_name;
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
    private boolean is_sign;
    private List<LevelBean> level;

    public boolean is_sign() {
        return is_sign;
    }

    public void setIs_sign(boolean is_sign) {
        this.is_sign = is_sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<LevelBean> getLevel() {
        return level;
    }

    public void setLevel(List<LevelBean> level) {
        this.level = level;
    }

    public static class LevelBean {
        /**
         * level_id : 1
         * level_name : 铜勋章
         * level_no_img :
         * level_get_img :
         * level_count : 200
         * level_discount : 0
         * level_voucher : 3
         */

        private int level_id;
        private String level_name;
        private String level_no_img;
        private String level_get_img;
        private int level_count;
        private int level_discount;
        private int level_voucher;

        public int getLevel_id() {
            return level_id;
        }

        public void setLevel_id(int level_id) {
            this.level_id = level_id;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getLevel_no_img() {
            return level_no_img;
        }

        public void setLevel_no_img(String level_no_img) {
            this.level_no_img = level_no_img;
        }

        public String getLevel_get_img() {
            return level_get_img;
        }

        public void setLevel_get_img(String level_get_img) {
            this.level_get_img = level_get_img;
        }

        public int getLevel_count() {
            return level_count;
        }

        public void setLevel_count(int level_count) {
            this.level_count = level_count;
        }

        public int getLevel_discount() {
            return level_discount;
        }

        public void setLevel_discount(int level_discount) {
            this.level_discount = level_discount;
        }

        public int getLevel_voucher() {
            return level_voucher;
        }

        public void setLevel_voucher(int level_voucher) {
            this.level_voucher = level_voucher;
        }
    }
}
