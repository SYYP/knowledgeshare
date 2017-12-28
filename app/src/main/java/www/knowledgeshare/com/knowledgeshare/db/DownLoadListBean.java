package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/30.
 */

public class DownLoadListBean extends DataSupport implements Serializable{

    private int id;
    private int xkId;
    private String name;
    private String videoTime;
    private String date;
    private String time;
    private String videoUrl;
    private String txtUrl;
    private String iconUrl;
    private boolean save;
    private boolean isChecked=true;

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getXkId() {
        return xkId;
    }

    public void setXkId(int xkId) {
        this.xkId = xkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTxtUrl() {
        return txtUrl;
    }

    public void setTxtUrl(String txtUrl) {
        this.txtUrl = txtUrl;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public DownLoadListBean(int id, int xkId, String name, String videoTime, String date, String time, String videoUrl, String txtUrl, String iconUrl) {
        this.name = name;
        this.videoTime = videoTime;
        this.time = time;
        this.videoUrl = videoUrl;
        this.txtUrl = txtUrl;
        this.iconUrl = iconUrl;
    }
}
