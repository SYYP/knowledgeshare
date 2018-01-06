package www.knowledgeshare.com.knowledgeshare.fragment.home.player;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/12/21.
 */

public class PlayerBean {
    private String teacher_head;
    private String title;
    private String subtitle;
    private String video_url;
    private Bitmap mBitmap;
    private String msg;
    private String localPath;//本地路径

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getTeacher_head() {
        return teacher_head;
    }

    public void setTeacher_head(String teacher_head) {
        this.teacher_head = teacher_head;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public PlayerBean(String teacher_head, String title, String subtitle, String video_url) {
        this.teacher_head = teacher_head;
        this.title = title;
        this.subtitle = subtitle;
        this.video_url = video_url;
    }

    public PlayerBean(String teacher_head, String title, String subtitle, String video_url, String localPath) {
        this.teacher_head = teacher_head;
        this.title = title;
        this.subtitle = subtitle;
        this.video_url = video_url;
        this.localPath = localPath;
    }
}
