package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/30.
 */

public class DownLoadListBean extends DataSupport implements Serializable{

    private int id;
    private int xkId;
    private int zlId;
    private int freeId;
    private int tuijianId;
    private String name;
    private String videoTime;
    private String date;
    private String time;
    private String videoUrl;
    private String txtUrl;
    private String iconUrl;
    private boolean save;
    private boolean isChecked=true;

    public int getZlId() {
        return zlId;
    }

    public void setZlId(int zlId) {
        this.zlId = zlId;
    }

    public int getFreeId() {
        return freeId;
    }

    public void setFreeId(int freeId) {
        this.freeId = freeId;
    }

    public int getTuijianId() {
        return tuijianId;
    }

    public void setTuijianId(int tuijianId) {
        this.tuijianId = tuijianId;
    }

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

    /**
     * -------------------------------------小课
     * @param id    小课里的音频ID
     * @param xkId  下课ID
     * @param name  音频名字
     * @param videoTime 音频时长
     * @param date  音频日期
     * @param time  音频时间
     * @param videoUrl  音频URL
     * @param txtUrl    文稿URL
     * @param iconUrl   音频图片URL
     * -------------------------------------
     */
    public DownLoadListBean(int id, int xkId, String name, String videoTime, String date,
                            String time, String videoUrl, String txtUrl, String iconUrl) {
        this.id = id;
        this.xkId = xkId;
        this.name = name;
        this.videoTime = videoTime;
        this.date = date;
        this.time = time;
        this.videoUrl = videoUrl;
        this.txtUrl = txtUrl;
        this.iconUrl = iconUrl;
    }

    /**
     * --------------------------------专栏
     * @param id    专栏里的音频ID
     * @param zlId  专栏ID
     * @param xkId   固定为 -3
     * @param name  音频名字
     * @param videoTime 音频时长
     * @param date  音频日期
     * @param time  音频时间
     * @param videoUrl   音频URL
     * @param txtUrl    文稿URL
     * @param iconUrl   音频图片
     * ---------------------------------
     */
    public DownLoadListBean(int id, int zlId, int xkId, String name, String videoTime, String date,
                            String time, String videoUrl,String txtUrl, String iconUrl) {
        this.id = id;
        this.zlId = zlId;
        this.xkId = -3;
        this.name = name;
        this.videoTime = videoTime;
        this.date = date;
        this.time = time;
        this.videoUrl = videoUrl;
        this.txtUrl = txtUrl;
        this.iconUrl = iconUrl;
    }

    /**
     * -----------------------------------------免费专区
     * @param id    免费专区的音频ID
     * @param freeId    免费专区ID  固定值  -1
     * @param zlId      专栏ID  固定值 -4
     * @param name      音频名字
     * @param videoTime     音频时长
     * @param date      音频日期
     * @param time      音频时间
     * @param videoUrl      音频URL
     * @param txtUrl    文稿URL
     * @param iconUrl   音频图片
     * ---------------------------------------
     */
    public DownLoadListBean(int id, int freeId, int zlId, int xkId, String name, String videoTime, String date,
                            String time, String videoUrl,String txtUrl, String iconUrl) {
        this.id = id;
        this.freeId = freeId;
        this.zlId = -4;
        this.xkId = -3;
        this.name = name;
        this.videoTime = videoTime;
        this.date = date;
        this.time = time;
        this.videoUrl = videoUrl;
        this.iconUrl = iconUrl;
        this.txtUrl = txtUrl;
    }

    /**
     * -------------------------------------每日推荐
     * @param id    音频ID
     * @param tuijianId     每日推荐ID  固定值 -2
     * @param zlId      固定值-4
     * @param xkId      固定值-3
     * @param freeId    固定值-1
     * @param name      音频名字
     * @param videoTime     音频时长
     * @param date      音频日期
     * @param time      音频时间
     * @param videoUrl      音频URL
     * @param txtUrl        文稿URL
     * @param iconUrl       音频图片
     */
    public DownLoadListBean(int id, int tuijianId,int zlId, int xkId, int freeId, String name, String videoTime, String date,
                            String time, String videoUrl,String txtUrl, String iconUrl) {
        this.id = id;
        this.tuijianId = tuijianId;
        this.zlId = -4;
        this.xkId = -3;
        this.freeId = -1;
        this.name = name;
        this.videoTime = videoTime;
        this.date = date;
        this.time = time;
        this.videoUrl = videoUrl;
        this.iconUrl = iconUrl;
        this.txtUrl = txtUrl;
    }
}
