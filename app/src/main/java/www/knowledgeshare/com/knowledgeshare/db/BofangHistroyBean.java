package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/19.
 */

public class BofangHistroyBean extends DataSupport {
    private String type;
    private int childId;
    private String parentId;
    private String video_name;
    private String created_at;
    private String video_url;
    private int good_count;
    private int collect_count;
    private int view_count;
    private boolean isDianzan;
    private boolean isCollected;
    private String t_header;
    private String t_tag;
    private String h5_url;
    private long time;//用时间戳来控制排列顺序
    private String txt_url;
    private String parentName;
    private boolean isLocal;//判断是不是本地音乐
    private int duration;//记忆播放的位置

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTxt_url() {
        return txt_url;
    }

    public void setTxt_url(String txt_url) {
        this.txt_url = txt_url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getH5_url() {
        return h5_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }

    public String getT_header() {
        return t_header;
    }

    public void setT_header(String t_header) {
        this.t_header = t_header;
    }

    public String getT_tag() {
        return t_tag;
    }

    public void setT_tag(String t_tag) {
        this.t_tag = t_tag;
    }

    public boolean isDianzan() {
        return isDianzan;
    }

    public void setDianzan(boolean dianzan) {
        isDianzan = dianzan;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public BofangHistroyBean(String type, int childId, String video_name, String created_at, String video_url,
                             int good_count, int collect_count, int view_count,
                             boolean isDianzan, boolean isCollected, String t_header,
                             String t_tag, String h5_url, long time, String parentId, String parentName,String txt_url) {
        this.type = type;
        this.childId = childId;
        this.video_name = video_name;
        this.created_at = created_at;
        this.video_url = video_url;
        this.good_count = good_count;
        this.collect_count = collect_count;
        this.view_count = view_count;
        this.isDianzan = isDianzan;
        this.isCollected = isCollected;
        this.t_header = t_header;
        this.t_tag = t_tag;
        this.h5_url = h5_url;
        this.time = time;
        this.parentId = parentId;
        this.parentName = parentName;
        this.txt_url=txt_url;
    }
}
