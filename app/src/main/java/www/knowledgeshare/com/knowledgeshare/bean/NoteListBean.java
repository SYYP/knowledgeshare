package www.knowledgeshare.com.knowledgeshare.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class NoteListBean extends BaseBean implements Serializable {

    private List<GoldBean> gold;
    private List<NoteBean> note;

    public List<GoldBean> getGold() {
        return gold;
    }

    public void setGold(List<GoldBean> gold) {
        this.gold = gold;
    }

    public List<NoteBean> getNote() {
        return note;
    }

    public void setNote(List<NoteBean> note) {
        this.note = note;
    }

    public static class GoldBean {
        /**
         * id : 3
         * display_at : 2017-12-22
         * day : 星期三
         * content : 历史的今天是什么？
         * created_at : 2017-12-20 02:00:00
         * updated_at : 2017-12-20 01:00:00
         * isfav : false
         */

        private int id;
        private String display_at;
        private String day;
        private String content;
        private String created_at;
        private String updated_at;
        private boolean isfav;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDisplay_at() {
            return display_at;
        }

        public void setDisplay_at(String display_at) {
            this.display_at = display_at;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public boolean isIsfav() {
            return isfav;
        }

        public void setIsfav(boolean isfav) {
            this.isfav = isfav;
        }
    }

    public static class NoteBean {
        /**
         * id : 7
         * user_id : 7
         * date : 20171221
         * title : 女高音，一个神秘又充满魅力的声音3
         * content : 年前中国互联网网民成长速度在50%以上，到今天其实已经到了6%左右。事实上，过去的四年，中国互联网网民
         * created_at : 2017-12-21 02:53:20
         * updated_at : 2017-12-21 02:53:20
         */

        private int id;
        private int user_id;
        private int date;
        private String title;
        private String content;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
