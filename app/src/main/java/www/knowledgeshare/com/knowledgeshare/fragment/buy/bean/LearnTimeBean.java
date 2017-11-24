package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class LearnTimeBean  implements Serializable{
    private String date;
    private ContentBean contentBeen;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ContentBean getContentBeen() {
        return contentBeen;
    }

    public void setContentBeen(ContentBean contentBeen) {
        this.contentBeen = contentBeen;
    }

    public static class ContentBean implements Serializable{
        private String time;
        private String content;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
