package www.knowledgeshare.com.knowledgeshare.fragment.buy.bean;

import java.io.Serializable;
import java.io.StringReader;

/**
 * Created by Administrator on 2017/12/4.
 */

public class DownLoadBean implements Serializable {
    private String content;
    private String jindu;
    private String size;
    private String zhuangtai;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJindu() {
        return jindu;
    }

    public void setJindu(String jindu) {
        this.jindu = jindu;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai;
    }
}
