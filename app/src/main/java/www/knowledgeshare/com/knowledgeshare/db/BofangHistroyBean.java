package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/19.
 */

public class BofangHistroyBean extends DataSupport{
    private int good_count;
    private String video_name;
    private String created_at;
    private int collect_count;
    private String video_url;
    private int view_count;

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

    public BofangHistroyBean(int good_count, String video_name, String created_at, int collect_count, String video_url, int view_count) {
        this.good_count = good_count;
        this.video_name = video_name;
        this.created_at = created_at;
        this.collect_count = collect_count;
        this.video_url = video_url;
        this.view_count = view_count;
    }
}
